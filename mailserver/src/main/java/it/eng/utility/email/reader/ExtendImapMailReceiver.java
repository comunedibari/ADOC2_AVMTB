package it.eng.utility.email.reader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.StoreClosedException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.SharedByteArrayInputStream;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.util.ExceptionUtils;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.MailTransportUtils;
import org.springframework.util.Assert;

import com.sun.mail.imap.IMAPFolder;

import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.aurigamailbusiness.utility.MailUtil;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.aurigamailbusiness.utility.cryptography.CryptographyUtil;
import it.eng.utility.FileUtil;
import it.eng.utility.database.MailboxConfigDefaultValue;
import it.eng.utility.email.LogUtility;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.operation.impl.deletemessageoperation.DeleteMessageBean;
import it.eng.utility.email.process.ProcessOrchestrator;
import it.eng.utility.email.process.exception.MailServerException;
import it.eng.utility.email.process.exception.ManyDeletedException;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.reader.config.MailConfiguratorBean;
import it.eng.utility.email.reader.info.MailBoxInfoKey;
import it.eng.utility.email.reader.search.MessageIdSearchTerm;
import it.eng.utility.email.storage.StorageCenter;
import it.eng.utility.email.util.caricamaildafs.CaricaMailDaFs;
import it.eng.utility.milano.searchemail.SearchMailImpl;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.filesystem.util.EnvironmentVariableConfigManager;

/**
 * Estensione dell'imap mailrecevier di spring
 * 
 * @author michele
 * 
 */

public class ExtendImapMailReceiver extends ImapMailReceiver {

	private MailConfiguratorBean configbean;

	private static final String INBOX = "INBOX";

	private StorageService storage;

	private Long uidValidity = null;

	private Map<String, Long> uidMap = null;

	/**
	 * Costruttore
	 * 
	 * @param props
	 */
	public ExtendImapMailReceiver(final MailConfiguratorBean config) {
		// Configurazione
		super(config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_STORE_PROTOCOL.keyname()) + "://"
				+ config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_HOST.keyname()) + ":"
				+ config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_PORT.keyname()) + "/" + config.getFolder());
		this.configbean = config;
		// Controllo se devo inserire l'authenticator
		Properties properties = config.getAccount().getAccountconfig();
		setJavaMailProperties(properties);

		setProtocol(properties.getProperty(AccountConfigKey.IMAP_STORE_PROTOCOL.keyname()));
		setJavaMailAuthenticator(new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String password = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname());
				try {
					password = CryptographyUtil.decryptionAccountPasswordWithAES(config.getAccount().getAccountconfig());
				} catch (Exception e) {
					logger.error("Costruttore ExtendImapMailReceiver - Eccezione decrittazione password: " + e.getMessage(), e);
				}
				String username = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
				// in alcuni casi potrebbe essere specificato un username
				// specifico per imap
				if (config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_USERNAME.keyname()) != null) {
					username = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_USERNAME.keyname());
				}
				return new PasswordAuthentication(username, password);
			}
		});
		// Di default non cancello i messaggi alla lettura e non li marco come
		// letti
		// lascio il compito alle varie operation
		setShouldDeleteMessages(false);
		setShouldMarkMessagesAsRead(false);
		
		// Fetch del numero di mail per processo di scarico
		Integer maxFetchSize = MailboxConfigDefaultValue.DEFAULT_FETCH;
		if (config.getMailconfig().containsKey(MailBoxConfigKey.MAILBOX_FETCH.keyname())) {
			maxFetchSize = Integer.parseInt(config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_FETCH.keyname()));
		}
		setMaxFetchSize(maxFetchSize);
		afterPropertiesSet();
		StorageCenter lStorageCenter = new StorageCenter();
		storage = lStorageCenter.getGlobalStorage(getConfigbean().getMailboxid());
	}

	private ImapIdleChannelAdapter channel;

	public ImapIdleChannelAdapter getChannel() {
		return channel;
	}

	public void setChannel(ImapIdleChannelAdapter channel) {
		this.channel = channel;
	}

	@Override
	public void waitForNewMessages() throws MessagingException {
		FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();
		try {
			boolean caricadafs = false;
			try {
				caricadafs = this.getConfigbean().getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_CARICADAFS.keyname(), "false")
						.equalsIgnoreCase(Boolean.TRUE.toString());
				// Diego: su indicazione della vale, se metto delle configurazioni
				// sulla casella carico delle mail da filesystem
				if (caricadafs) {
					String directoryConfigValue = getConfigbean().getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_CARICADAFS_DIR.keyname());
					File directory = new File(EnvironmentVariableConfigManager.replaceEnvironmentVariable(directoryConfigValue));
					if (directory.exists() && !directory.isDirectory()) {
						throw new Exception(directory.getAbsolutePath() + " non esiste o non è una directory.");
					}
					CaricaMailDaFs caricaMailDaFs = new CaricaMailDaFs(this.configbean.getMailboxid(), directory, storage,
							Boolean.parseBoolean(this.getConfigbean().getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DELETE_TO_FILE_SYSTEM.keyname())), this.getConfigbean());
					caricaMailDaFs.elaborate();
					// carico i messaggi che devo elavorare (la receive non viene
					// eseguita)
					addMimeMessageToElaborate();
				}
			} catch (Exception ex) {
				logger.warn(ExtendImapMailReceiver.class.toString() + " errore non bloccante sul caricamentodafs");
				logger.warn("Eccezione: " + ex.getMessage(), ex);
			} catch (Throwable e) {
				logger.warn(ExtendImapMailReceiver.class.toString() + " errore non bloccante sul caricamentodafs");
				logger.warn("Eccezione: " + e.getMessage(), e);
			}
			
			while (hasMessageToSend()) {
				try {
					logger.info("Resto in attesa che i messaggi nella coda siano stati processati");
					Thread.sleep(120000);
				} catch (InterruptedException e) {
					logger.error("Eccezione thread interrotto: " + e.getMessage(), e);
				}
			}
			if (!hasMoreMessage) {
				new MailboxWait(this).waitRead();
			}

			if (!caricadafs && (this.getFolder() == null || !this.getFolder().isOpen())) {
				try
				{ 
					// riprendo la ricerca di nuovi messaggi
					initTask();
				} catch (AuthenticationFailedException ex) {
					// aggiorno la pwd dal db... e riprovo a connettermi
					String password =  FactoryMailBusiness.getInstance().getMailboxPassword(configbean.getMailboxid());
					configbean.getAccount().getAccountconfig().setProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname(),password);
					setJavaMailAuthenticator(new Authenticator() {

						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							String password = configbean.getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname());
							try {
								password = CryptographyUtil.decryptionAccountPasswordWithAES(configbean.getAccount().getAccountconfig());
							} catch (Exception e) {
								logger.error("Costruttore ExtendImapMailReceiver - Eccezione decrittazione password: " + e.getMessage(), e);
							}
							String username = configbean.getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
							// in alcuni casi potrebbe essere specificato un username
							// specifico per imap
							if (configbean.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_USERNAME.keyname()) != null) {
								username = configbean.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_USERNAME.keyname());
							}
							return new PasswordAuthentication(username, password);
						}
					});
					// mi riconnetto
					openFolder();
				}
				
			}


		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			try {
				mailBusiness.saveOrUpdateInfoMailbox(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_LAST_CONNECTION_RECEIVE_ERROR,
						new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
				mailBusiness.saveOrUpdateInfoMailbox(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_LAST_CONNECTION_RECEIVE_ERROR_TYPE,
						(ex.getLocalizedMessage().length() > 400 ? ex.getLocalizedMessage().substring(0, 400) : ex.getLocalizedMessage())
						);
			} catch (Exception e1) {
				logger.warn("Errore nel salvataggio dell'informazione di errore in database", e1);
			}
			throw new MessagingException(ex.getLocalizedMessage(), ex);
		}
		
	}

	/**
	 * Apre il folder
	 * 
	 * @throws Exception
	 */
	public void initTask() throws MessagingException {
		openFolder();
	}

	@Override
	public Message[] receive() throws MessagingException {

		String idMailBox = this.configbean.getMailboxid();
		logger.error("Ricerco nuovi messaggi da scaricare");
		FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();
		// pulizia dei precedenti messaggi in database prima di scaricare i
		// messaggi dalla casella
		// non cancello i messaggi discharged scaricati precedentemente
		mailBusiness.initializeMailboxMessage(idMailBox, false);
		// Lista dei messaggi in ritorno da elaborare
		List<Message> messageToReturn = new ArrayList<>();
		// istanzio una nuova sessione con le proprietà comuni
		Session mailSession = null;
		// inizializzo mappa uid
		uidMap = new HashMap<>();
		try {
			mailSession = Session.getInstance(getJavaMailProperties() != null ? getJavaMailProperties() : Util.getJavaMailDefaultProperties());
		} catch (Exception e) {
			logger.error("Eccezione inizializzazione sessione mail", e);
		}
		Exception receiveException = null;

		List<FileMimeMessage> messaggiDaProcessare = new ArrayList<>();

		// recupero i messaggi in stato discharged precedentemente scaricati e
		// non ancora elaborati
		try {
			messaggiDaProcessare.addAll(mailBusiness.getMimeMessageDischargedToElaborate(idMailBox));
		} catch (Throwable e) {
			logger.error("Errore nel recupero dei messaggi discharged", e); // non
																			// rilancio
																			// l'eccezione
																			// in
																			// modo
																			// da
																			// cercare
																			// i
																			// nuovi
																			// messaggi
		}

		// scarico i nuovi messaggi, che avranno poi la priorità nella coda di
		// processamento
		try {

			// Sposto in uno specifico try il download di nuovi messaggi
			// in questo modo in caso di errore posso provare comunque a
			// processare i messaggi
			// precedentemente in errore o da ri-elaborare
			Message[] messages = new Message[0];
			boolean failedImapEvelop = false;
			try {
				messages = (Message[]) super.receive();
				// registro che la ricerca di messaggi è stata eseguita con
				// successo
				try {
					mailBusiness.saveOrUpdateInfoMailbox(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_LAST_SEARCH_NEW_MESSAGE_OK,
							new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
				} catch (Exception e1) {
					logger.warn("Errore nel salvataggio dell'informazione di errore in database");
				}
				logger.error("Nuovi messaggi da scaricare: " + messages.length);
			} catch (MessagingException e) {
				// si sono verificati errori lato server IMAP, registro data e
				// problematica dell'errore
				String error = (e.getLocalizedMessage().length() > 400 ? e.getLocalizedMessage().substring(0, 400) : e.getLocalizedMessage());
				try {
					mailBusiness.saveOrUpdateInfoMailbox(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_LAST_CONNECTION_RECEIVE_ERROR,
							new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
					mailBusiness.saveOrUpdateInfoMailbox(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_LAST_CONNECTION_RECEIVE_ERROR_TYPE,
							(e.getLocalizedMessage().length() > 400 ? e.getLocalizedMessage().substring(0, 400) : e.getLocalizedMessage())
							);
				} catch (Exception e1) {
					logger.warn("Errore nel salvataggio dell'informazione di errore in database");
				}
				// se ho un autenticationFailed , provo a ricaricare la password e mi riconnetto 
				if (e instanceof AuthenticationFailedException) {
					// aggiorno la pwd dal db... e riprovo a connettermi
					String password =  FactoryMailBusiness.getInstance().getMailboxPassword(configbean.getMailboxid());
					configbean.getAccount().getAccountconfig().setProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname(),password);
					setJavaMailAuthenticator(new Authenticator() {

						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							String password = configbean.getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname());
							try {
								password = CryptographyUtil.decryptionAccountPasswordWithAES(configbean.getAccount().getAccountconfig());
							} catch (Exception e) {
								logger.error("Costruttore ExtendImapMailReceiver - Eccezione decrittazione password: " + e.getMessage(), e);
							}
							String username = configbean.getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
							// in alcuni casi potrebbe essere specificato un username
							// specifico per imap
							if (configbean.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_USERNAME.keyname()) != null) {
								username = configbean.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_USERNAME.keyname());
							}
							return new PasswordAuthentication(username, password);
						}
					});
					openFolder();
				}
				// DiegoL: se c'è questo errore, viene impedito lo scarico, quindi carico i messaggi come stream
				if (error.contains("Failed to load IMAP envelope")) {
					failedImapEvelop = true;
					Folder imapFolder = super.getFolder();
					if (!imapFolder.isOpen()) {
						openFolder();
					}
					Message[] messagesHelp = imapFolder.getMessages();
					Message[] messagesAux = new Message[messagesHelp.length];
					int i =0;
					for (Message msg: messagesHelp) {
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						msg.writeTo(bos);
					    bos.close();
					    SharedByteArrayInputStream bis =
							    new SharedByteArrayInputStream(bos.toByteArray());
					    MimeMessage cmsg = new MimeMessage(mailSession, bis);
					    bis.close();
					    messagesAux[i] = cmsg;
					    i++;
					}
					messages = messagesAux;
				} else
					throw e;
			} catch (Exception e) {
				logger.error("Eccezione nello scarico di nuovi messaggi ", e);
				throw e;
			}
			
			// avendo effettuato la pulizia tutti i messaggi ricevuti sono
			// sicuramente assenti nel database
			// posso procedere con l'insert del messaggio con il metodo
			// addMimeMessage senza effettuare controlli su chiavi duplicate
			for (Message message : messages) {
				String idMessage = MailUtil.getInstance(((MimeMessage) message)).getMessageID();
				Long uid = null;
				if (uidMap != null && uidMap.get(idMessage) != null) {
					uid = uidMap.get(idMessage);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Provo a scaricare il messaggio con id: " + idMessage);
				}
				String filename = null;
				try {
					if (storage == null) {
						throw new MailServerException("Utilizzatore Storage 'MailConnect' non configurato per la mailbox");
					}
					// Copio il messaggio nella temporanea
					filename = storage.writeTo(message);
					// Inserisco il messaggio a database
					mailBusiness.addMimeMessage(idMessage, MessageStatus.MESSAGE_DISCHARGED, idMailBox, uid, uidValidity, filename, null);
					// Lo aggiungo alla lista dei messaggi da processare
					messageToReturn.add(message);
					logger.info("Messaggio avente id: " + idMessage + " scaricato e inserito in database");
				} catch (Throwable e) {
					logger.error("Errore durante lo scarico della mail avente id: " + idMessage, e);
					// Inserisco il messaggio a database con stato di errore
					try {
						mailBusiness.addMimeMessage(idMessage, MessageStatus.MESSAGE_IN_ERROR_DISCHARGED, idMailBox, uid, uidValidity, null, e);
					} catch (Throwable t) {
						logger.error("Errore nell'inserimento del messaggio con status di " + MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status(), t);
					}
					// cancellazione (senza eccezioni in caso di errore) dallo
					// storage del file scaricato, verrà riscaricato
					if (StringUtils.isNotBlank(filename)) {
						if (storage.extractFile(filename) != null) {
							storage.delete(filename);
						}
					}
				}
			}
		} catch (Exception e) {
			LogUtility.logHibernateException(e);
			logger.error("Errore metodo receive di ExtendImapMailReceiver", e);
			receiveException = e;
		}

		Integer messaggiInErroreAggiunti = 0;

		// Aggiungo i messaggi da elaborare andati in errore.
		try {

			// anche qui imposto un numero massimo di messaggi da ri-processare
			// per iterazione in modo da ottimizzare le risorse
			Integer limit = MailboxConfigDefaultValue.DEFAULT_MAX_NUM_MESSAGE_ERROR_TO_PROCESS;
			if (configbean.getMailconfig().containsKey(MailBoxConfigKey.MAILBOX_MAX_MESSAGE_ERROR_TO_PROCESS.keyname())
					&& configbean.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_MAX_MESSAGE_ERROR_TO_PROCESS.keyname()) != null) {
				limit = Integer.parseInt(configbean.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_MAX_MESSAGE_ERROR_TO_PROCESS.keyname()));
			}

			messaggiDaProcessare.addAll(mailBusiness.getMimeMessageToElaborate(idMailBox, limit));

			for (FileMimeMessage messaggio : messaggiDaProcessare) {
				try {
					// Aggiungo all'array i messaggi da rieseguire
					if (messaggio.getFilemime() == null) {
						logger.warn("Nessuna referenziazione file/byte[] per il messaggio!");
					} else {
						if (messaggio.getData() != null) {
							messageToReturn.add(new MimeMessage(mailSession, new ByteArrayInputStream(messaggio.getData())));
							messaggiInErroreAggiunti++;
						} else {
							File lFile = null;
							FileInputStream fileStream = null;
							try {

								lFile = File.createTempFile("errorMessage", ".tmp");
								FileUtil.writeStreamToFile(messaggio.getFilemime(), lFile);
								fileStream = new FileInputStream(lFile);
								messageToReturn.add(new MimeMessage(mailSession, fileStream));
								messaggiInErroreAggiunti++;
							} catch (Exception e) {
								if (lFile != null) {
									FileUtil.deleteFile(lFile);
								}
							} finally {
								if (lFile != null) {
									FileUtil.deleteFile(lFile);
								}
								if (fileStream != null) {
									try {
										fileStream.close();
									} catch (Exception e) {
										logger.warn("Errore nella chiusura dello stream", e);
									}
								}
							}
						}

					}
				} catch (Exception exc) {
					// gestione dell'eccezione per il singolo messaggio in modo
					// da provare a processare quelli successivi
					LogUtility.logHibernateException(exc);
					logger.error("Errore nel recupero del messaggio in errore", exc);
				}
			}

			logger.error("Messaggi da ri-processare: " + messaggiInErroreAggiunti);
		} catch (Throwable e) {
			logger.error("Errore nel recupero dei messaggi in errore", e); // non
																			// rilancio
																			// l'eccezione
																			// in
																			// modo
																			// da
																			// processare
																			// comunque
																			// i
																			// nuovi
																			// messaggi,
																			// o
																			// quelli
																			// da
																			// ri-processare
																			// aggiunti
		}

		if (receiveException != null && messaggiInErroreAggiunti == 0) {
			// non ho nessun messaggio da processare, quindi ri-schedulo
			// immediatamente la connessione alla mailbox per ricercare nuovi
			// messaggi
			// lanciando MessaggingException
			// nella classe IdleTask di Spring si gestirà la nuova schedulazione
			throw new MessagingException("Errore metodo receive di ExtendImapMailReceiver", receiveException);
		}

		logger.error("Totale messaggi da processare: " + messageToReturn.size());
		for (Message lMimeMessage : messageToReturn) {
			String messageId;
			try {
				messageId = MailUtil.getInstance((MimeMessage) lMimeMessage).getMessageID();
				if (StringUtils.isNotBlank(messageId)) {
					actualMessages.add(messageId);
				}
			} catch (Exception e) {
				logger.error("Errore nel recupero del message id", e);
			}
		}
		return messageToReturn.toArray(new MimeMessage[0]);
	}

	private List<String> actualMessages = new Vector<String>();

	public void messageProcessed(String messageId) {
		modifyActualMessages(messageId);
	}

	public void clearActualMessages() {
		actualMessages.clear();
	}

	public int modifyActualMessages(String messageId) {
		int size = actualMessages.size();
		if (StringUtils.isEmpty(messageId)) {
			logger.debug("Voglio sapere se ci sono messaggi ancora non processati - rimasti nella coda di processamento: " + size);
		} else {
			logger.debug("Voglio eliminare dai messaggi da processare il messaggio con id: " + messageId);
			int index = actualMessages.indexOf(messageId);
			actualMessages.remove(index);
			size = actualMessages.size();
			logger.error(
					"Rimosso dalla lista dei messaggi da processare il messaggio con id: " + messageId + " - rimasti nella coda di processamento: " + size);
		}
		return size;
	}

	public boolean hasMessageToSend() {
		Folder folder = this.getFolder();
		FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();
//		try {
//			if (folder == null || !folder.isOpen()) {
//				// riapro la connesione se fosse chiusa
//				openFolder();
//			}
			// mi sono ricconnesso
			try {
				mailBusiness.saveOrUpdateInfoMailbox(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_LAST_SEARCH_NEW_MESSAGE_OK,
						new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
			} catch (Exception e1) {
				logger.warn("Errore nel salvataggio dell'informazione di errore in database");
			}
//		} catch (MessagingException e) {
//			logger.error(e.getLocalizedMessage(), e);
//			try {
//				mailBusiness.saveOrUpdateInfoMailbox(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_LAST_CONNECTION_RECEIVE_ERROR,
//						new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
//				mailBusiness.saveOrUpdateInfoMailbox(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_LAST_CONNECTION_RECEIVE_ERROR_TYPE,
//						(e.getLocalizedMessage().length() > 400 ? e.getLocalizedMessage().substring(0, 400) : e.getLocalizedMessage())
//						);
//			} catch (Exception e1) {
//				logger.warn("Errore nel salvataggio dell'informazione di errore in database");
//			}
//		}
		
		int size = modifyActualMessages(null);
		if (size > 0) {
			logger.error("Processamento di un messaggio in corso - rimasti nella coda di processamento: " + size);
			return true;
		} else {
			logger.error(
					"Nessun messaggio presente nella coda di processamento - schedulo nuova connessione alla mailbox per ricercare nuovi messaggi da scaricare");
			return false;
		}
	}

	/**
	 * Copia il messaggio da una cartella ad un'altra: se {@link foldernameFrom} è null allora è la INBOX o la cartella impostata nell'url iniziale Per
	 * velocizzare il controllo si effettua la \ricerca per {@link uid} se {@link uidValidity} coincide con quella della INBOX. Secondariamente se non si trova
	 * il messaggio per uid si procede con la ricerca per message id, che è più lenta
	 * 
	 * @param messageId
	 * @param uid
	 * @param uidValidity
	 * @param folderNameFrom
	 * @param folderNameTo
	 * @throws Exception
	 */

	public void copy(final String messageId, final Long uid, final Long uidValidity, final String folderNameFrom, final String folderNameTo) throws Exception {

		IMAPFolder folderFrom = null;
		IMAPFolder folderTo = null;

		try {

			folderFrom = (IMAPFolder) openFolder(folderNameFrom, Folder.READ_WRITE);
			Message[] originalMsg = null;
			Message message = null;

			Long uidValidityCurrent = folderFrom.getUIDValidity();
			if ((folderNameFrom == null || folderNameFrom.equals(INBOX)) && uid != null && uidValidity != null && uidValidityCurrent != null
					&& uidValidityCurrent.equals(uidValidity)) {
				// ricerco per uid, se sono nella INBOX
				message = folderFrom.getMessageByUID(uid);
			}
			if (message == null) {
				originalMsg = folderFrom.search(new MessageIdSearchTerm(messageId));
				if (originalMsg == null || originalMsg.length == 0) {
					throw new Exception("Messaggio da copiare non trovato");
				}
			} else {
				originalMsg = new Message[1];
				originalMsg[0] = message;
			}

			try {
				folderTo = (IMAPFolder) openFolder(folderNameTo, Folder.READ_WRITE, true);
			} catch (FolderNotFoundException e) {
				if (logger.isDebugEnabled()) {
					logger.debug("Copia: fallita apertura cartella per il seguente errore: " + e.getMessage()
							+ ", provo ad aprire la cartella come sottocartella della root", e);
				}

				folderTo = (IMAPFolder) openFolderInRootFolder(folderNameTo, Folder.READ_WRITE);
			}

			// non conosco la validità della folder di destinazione, nè
			// l'eventuale uid per cui occorre effettuare la ricerca per
			// message-id
			// se però ci sono troppi messaggi nella folder di destinazione non
			// ha senso fare la ricerca perchè ci impiegherebbe troppo
			// in questo caso conviene effettuare direttamente la copia

			Boolean executeCopy = false;

			if (folderTo.getMessageCount() < 2000) {
				Message[] checkMsg = folderTo.search(new MessageIdSearchTerm(messageId));
				executeCopy = (checkMsg == null || checkMsg.length == 0);
			} else {
				executeCopy = true;
			}

			// procedo con la copia
			if (executeCopy) {
				// evito duplicazioni del messaggio
				// il messaggio non esiste già nella cartella di destinazione
				// la folder può essere chiusa prima di effettuare la copia
				MailTransportUtils.closeFolder(folderTo, false);
				folderFrom.copyMessages(originalMsg, folderTo);
			}

		} finally

		{
			MailTransportUtils.closeFolder(folderFrom, false);
			MailTransportUtils.closeFolder(folderTo, false);
		}
	}

	/**
	 * Creo l'alberatura specifica
	 * 
	 * @param name
	 * @throws Exception
	 */
	public void createFolder(String name) throws Exception {

		Folder parent = null;
		Folder newfolder = null;
		try {
			this.openFolder();
			parent = getFolder().getStore().getDefaultFolder().getFolder(INBOX);
			if (!parent.isOpen())
				parent.open(Folder.READ_WRITE);
			try {
				newfolder = parent.getFolder(name);
			} catch (Exception e) {
				newfolder = parent.getFolder(name);
			}
			try {
				if (!newfolder.exists()) {
					newfolder.create(Folder.HOLDS_MESSAGES);
				}
			} catch (FolderNotFoundException e) {
				// Creo il folder
				boolean create = newfolder.create(Folder.HOLDS_MESSAGES);
				if (!create) {
					newfolder = parent.getFolder(name);
					try {
						newfolder.open(Folder.READ_ONLY);
					} catch (FolderNotFoundException e1) {
						boolean recreate = newfolder.create(Folder.HOLDS_MESSAGES);
						if (!recreate) {
							throw new Exception("FOLDER NON CREATO!");
						}
					}
				}
			}
		} finally {
			MailTransportUtils.closeFolder(newfolder, false);
		}
	}

	/**
	 * Creo l'alberatura specifica
	 * 
	 * @param name
	 * @throws Exception
	 */
	public void createFolder(String name, boolean topLevel) throws Exception {

		if (!topLevel) {
			createFolder(name);
		} else {
			Folder parent = null;
			Folder newfolder = null;
			try {
				this.openFolder();
				parent = getFolder().getStore().getDefaultFolder().getFolder(INBOX);
				if (!parent.isOpen()) {
					parent.open(Folder.READ_WRITE);
				} else {
					MailTransportUtils.closeFolder(parent, false);
					parent.open(Folder.READ_WRITE);
				}
				try {
					newfolder = parent.getFolder(name);
				} catch (Exception e) {
					newfolder = parent.getFolder(name);
				}
				try {
					if (!newfolder.exists()) {
						newfolder.create(Folder.HOLDS_MESSAGES);
					}
				} catch (FolderNotFoundException e) {
					// Creo il folder
					boolean create = newfolder.create(Folder.HOLDS_MESSAGES);
					if (!create) {
						newfolder = parent.getFolder(name);
						try {
							newfolder.open(Folder.READ_ONLY);
						} catch (FolderNotFoundException e1) {
							boolean recreate = newfolder.create(Folder.HOLDS_MESSAGES);
							if (!recreate) {
								throw new Exception("FOLDER NON CREATO!");
							}
						}
					}
				}
			} finally {
				MailTransportUtils.closeFolder(newfolder, false);
			}
		}
	}

	/**
	 * Effettua una cancellazione immediata del messaggio, restituendo l'esito nel relativo bean
	 * 
	 * @param messageId
	 * @param folderName
	 * @throws Exception
	 */

	public DeleteMessageBean deleteMsg(final String messageId, final Long uid, final Long uidValidity, final String folderName) throws Exception {

		DeleteMessageBean result = new DeleteMessageBean();
		IMAPFolder folder = null;

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione
			// corrente
			throw new InterruptedException();
		}

		try {
			folder = (IMAPFolder) openFolder(folderName, Folder.READ_WRITE);
			// ottengo un riferimento al messaggio sul server
			// Attenzione: fino a quando la connessione aperta qualsiasi
			// modifica è visibile dai successivi metodi, non è una copia locale
			Message[] msgToDelete = null;
			Message message = null;

			Long uidValidityCurrent = folder.getUIDValidity();
			if ((folderName == null || folderName.equals(INBOX)) && uid != null && uidValidity != null && uidValidityCurrent != null
					&& uidValidityCurrent.equals(uidValidity)) {
				// ricerco per uid
				message = folder.getMessageByUID(uid);
			}
			// ricerca per messageId
			if (message == null) {
				msgToDelete = folder.search(new MessageIdSearchTerm(messageId));
			} else {
				msgToDelete = new Message[1];
				msgToDelete[0] = message;
			}
			if (msgToDelete != null) {
				if (msgToDelete.length == 0) {
					logger.warn(String.format("[msgToDelete.length == 0]Nessun messaggio da cancellare con id %1$s", messageId));
					result.setDeleteok(false);
					result.setNotFound(true);
				} else if (msgToDelete.length > 1) {
					logger.warn(String.format("Trovato più di un messaggio corrispondente all'id %1$s", messageId));
					result.setDeleteok(false);
					result.setDuplicate(true);
				} else {
					// setto il flag in modo che eventuali notifiche di lettura
					// siano inviate ai mittenti, invece che arrivino le
					// notifiche di
					// messaggio non letto cancellato
					msgToDelete[0].setFlag(Flag.SEEN, true);
					// setto il flag per cancellare il messaggio dalla folder
					msgToDelete[0].setFlag(Flag.DELETED, true);
					Integer numberFlagDeleted = folder.getDeletedMessageCount(); // numero
																					// di
																					// messaggi
																					// con
																					// flag
																					// deleted
																					// nella
																					// folder
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Numero dei messaggi con flag deleted nella casella %1$d", numberFlagDeleted));
					}
					if (numberFlagDeleted == 1) {
						folder.expunge();
						// Verifico di aver rimosso il messaggio: è sufficiente
						// verificare il risultato del metodo isExpunged
						// il messaggio originale in questo punto è stato del
						// tutto rimosso dal server, qualsiasi metodo invocato
						// genera un'eccezione
						// MessageRemovedException
						// gli unici metodi ammessi sono isExpunged e
						// getMessageNumber (posizione originale del messaggio
						// nella folder prima di essere
						// rimosso)
						Boolean deleted = false;
						if (!msgToDelete[0].isExpunged()) {
							// in alcuni casi il messaggio non viene settato
							// come Expunged se la cancellazione è effettuata
							// esternamente da un'altra
							// sorgente
							// provo a invocare un metodo, per vedere se la
							// cancellazione è stata effettuata
							try {
								msgToDelete[0].getAllHeaders();
							} catch (MessageRemovedException exc) {
								// il messaggio è stato effettivamente rimosso
								// visto che è stata sollevata la relativa
								// eccezione
								deleted = true;
							}
						} else {
							deleted = true;
						}
						if (deleted) {
							logger.info(String.format("Messaggio con id %1$s rimosso con successo dalla folder %2$s", messageId, folderName));
							result.setDeleteok(true);
						} else {
							throw new Exception(String.format("Messaggio con id %1$s non rimosso", messageId));
						}
					} else {
						throw new ManyDeletedException("Sono presenti altri messaggi con flag DELETED nella folder. Blocco la cancellazione");
					}
				}
			} else {
				logger.warn(String.format("Nessun messaggio da cancellare con id %1$s", messageId));
				result.setDeleteok(false);
				result.setNotFound(true);
			}
		} catch (Exception exc) {
			logger.error(String.format("Errore nella cancellazione del messaggio con id %1$s dalla folder %2$s", messageId, folderName), exc);
			throw exc;
		} finally {
			MailTransportUtils.closeFolder(folder, true);
		}

		return result;
	}

	/**
	 * Recupero e apro il il folder il cui nome è fornito in input, insieme alla modalità di apertura
	 * 
	 * @param name
	 * @return
	 */
	private Folder openFolder(String name, int mode) throws Exception {
		// parsing della stringa
		if (getFolder() == null) {
			openFolder();
		}
		Folder folder = getFolder();
		if (name != null && !name.equalsIgnoreCase(INBOX)) {
			try {
				folder = folder.getFolder(name);
				if (!folder.exists()) {
					folder = folder.getStore().getDefaultFolder().getFolder(INBOX).getFolder(name);
				}
			} catch (Exception e) {
				folder = folder.getStore().getDefaultFolder().getFolder(INBOX).getFolder(name);
			}
		}
		if (!folder.isOpen()) {
			folder.open(mode);
		} else {
			if (folder.getMode() != mode) {
				MailTransportUtils.closeFolder(folder, false);
				folder.open(mode);
			}
		}
		return folder;
	}

	/**
	 * Recupero il folder se presente al livello della inbox
	 * 
	 * @param name
	 * @return
	 * 
	 * @author leone
	 * 
	 */
	private Folder openFolder(String name, int mode, boolean topFolder) throws Exception {
		if (!topFolder) {
			return openFolder(name, mode);
		} else {
			// parsing della stringa
			if (getFolder() == null) {
				openFolder();
			}
			Folder folderInbox = getFolder();
			Folder foderToOpen = null;
			if (name != null && !name.equalsIgnoreCase(INBOX)) {
				foderToOpen = folderInbox.getStore().getDefaultFolder().getFolder(INBOX).getFolder(name);
				if (!foderToOpen.exists()) {
					createFolder(foderToOpen.getName());
				}
			}
			if (!foderToOpen.isOpen()) {
				foderToOpen.open(mode);
			} else {
				if (foderToOpen.getMode() != mode) {
					MailTransportUtils.closeFolder(foderToOpen, false);
					foderToOpen.open(mode);
				}
			}
			return foderToOpen;
		}

	}

	// MOD_2016-10-21_RDM2193 - INIZIO
	private Folder openFolderInRootFolder(String name, int mode) throws Exception {
		// parsing della stringa
		if (getFolder() == null) {
			openFolder();
		}
		Folder folder = getFolder();
		if (name != null && !name.equalsIgnoreCase(INBOX)) {
			try {
				folder = folder.getFolder(name);
				if (!folder.exists()) {
					folder = folder.getStore().getDefaultFolder().getFolder(name);
				}
			} catch (Exception e) {
				folder = folder.getStore().getDefaultFolder().getFolder(name);
			}
		}
		if (!folder.isOpen()) {
			folder.open(mode);
		} else {
			if (folder.getMode() != mode) {
				MailTransportUtils.closeFolder(folder, false);
				folder.open(mode);
			}
		}
		return folder;
	}
	// MOD_2016-10-21_RDM2193 - FINE

	/**
	 * Appendo un MimeMessage al folder specificato in ingresso
	 * 
	 * @param folderform
	 * @param folderto
	 * @throws Exception
	 */
	public void appendMessageToFolder(MimeMessage mimemessage, String folderstr) throws Exception {
		Folder folder = null;
		try {
			// Creo il folder di destinazione se non esiste;
			createFolder(folderstr);
			// Apro il folder
			folder = openFolder(folderstr, Folder.READ_WRITE);
			// Appendo il messaggio
			folder.appendMessages(new Message[] { mimemessage });
		} finally {
			MailTransportUtils.closeFolder(folder, false);
		}
	}

	/**
	 * Salava la mail nella cartella delle inviate configurata nella mailbox
	 * 
	 * @param mimemessage
	 * @param folderstr
	 * @throws Exception
	 */
	public void saveToSentFolder(MimeMessage mimemessage) throws Exception {
		appendMessageToFolder(mimemessage, this.configbean.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_SENT_FOLDER_NAME.keyname()));
	}

	private boolean hasMoreMessage = false;

	/**
	 * Metodo che ritorna i nuovi messaggi da leggere escludendo quelli già letti e salvati in database
	 */

	@Override
	protected Message[] searchForNewMessages() throws MessagingException {

		// inizializzo lista dei messaggi da processare
		actualMessages = new Vector<String>();
		hasMoreMessage = false;
		Folder folder = null;
		FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();

		folder = this.getFolder();

		if (!folder.isOpen()) {
			folder.open(Folder.READ_WRITE); // modalità read-write anchese devo
											// solo leggere i nuovi messaggi,
											// altrimenti si ottiene un errore
											// nel metodo
											// setFlags invocato successivamente
			// utilizzo la modalità peek impostata nelle proprietà PEEK di
			// JavaMail
		}

		// ogni messaggio della folder è associato ad un UID univoco a 32 bit, i
		// successivi messaggi hanno un valore maaggiore
		// durante una sessione gli UID rimangono stabili ma possono variare nel
		// corso del tempo, ad esempio se la folder è indicizzata
		// per garantire la validità di un UID si utilizza il parametro
		// UIDVALIDITY. Per identificare un messaggio univocamente nella folder
		// quindi si può usare
		// la combinazione UIDVALIDITY-UID-FOLDERNAME
		// i message-id invece non è univoco in una folder perchè potrebbero
		// esistere duplicati nella folder

		Boolean searchByUID = Boolean.parseBoolean(configbean.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_SEARCH_BY_UID.keyname()));

		Long uidValiditySaved = null;
		Long uidSaved = null;

		if (searchByUID) {
			// è attiva la ricerca per UID
			// Recupero l'eventuale UIDValidity salvata in MAILBOX_CONFIG e la
			// confronto con quella corrente - occorre recuperare sempre i
			// valori da database
			// visto che potrebbero essere aggiornati rispetto ai valori salvati
			// in fase di avvio del server
			if (StringUtils.isNotBlank(mailBusiness.getMailBoxInfo(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_UID_VALIDITY))) {
				uidValiditySaved = Long.parseLong(mailBusiness.getMailBoxInfo(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_UID_VALIDITY));
			}

			// Recupero l'eventuale ultimo UID del messaggio salvato in
			// MAILBOX_CONFIG e lo utilizzo per iniziare la ricerca di nuovi
			// messaggi
			if (StringUtils.isNotBlank(mailBusiness.getMailBoxInfo(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_LAST_MESSAGE_UID))) {
				uidSaved = Long.parseLong(mailBusiness.getMailBoxInfo(configbean.getMailboxid(), MailBoxInfoKey.MAILBOX_LAST_MESSAGE_UID));
			}
			logger.info("E' attiva la ricerca per UID");
		}

		if (folder.isOpen()) {

			logger.info("Connessione attiva - inizio ricerca dei nuovi messaggi");

			IMAPFolder imapFolder = (IMAPFolder) folder;

			if (searchByUID) {
				logger.info("UIDValidity salvata in database: " + uidValiditySaved);
				logger.info("Ultimo UID salvato in database: " + uidSaved);
			}

			uidValidity = imapFolder.getUIDValidity();

			logger.info("UIDValidity corrente: " + uidValidity);

			Integer folderMessageCount = imapFolder.getMessageCount();

			if (folderMessageCount == null || folderMessageCount < 1) {
				logger.error("Nessun messaggio presente nella folder");
				// non ci sono messaggi, resetto uid e validità salvate in modo
				// da partire da 1 quando ci saranno nuovi messaggi, anche se il
				// nuovo UID sarà
				// sicuramente
				// maggiore di quello salvato
				if (uidValiditySaved != null || uidSaved != null) {
					try {
						mailBusiness.resetUIDMailbox(configbean.getMailboxid());
					} catch (Exception e) {
						logger.warn("Eccezione durante il reset di UID e UIDValidity salvati in database");
					}
					uidValiditySaved = null;
					uidSaved = null;
				}
				return new Message[0];
			}

			logger.error("Messaggi presenti nella folder: " + folderMessageCount);

			Long startUID = 1L;

			// se la validità degli UID della folder non è variata allora posso
			// considerare l'ultimo UID salvato per iniziare la ricerca dei
			// nuovi messaggi,
			// quelli precedenti si presume che siano già stati processati

			// Fetch del numero di mail per processo di scarico
			int fetchSize = MailboxConfigDefaultValue.DEFAULT_FETCH;
			if (this.configbean.getMailconfig().containsKey(MailBoxConfigKey.MAILBOX_FETCH.keyname())) {
				fetchSize = Integer.parseInt(this.configbean.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_FETCH.keyname()));
			}

			if (searchByUID) {
				Long nextUID = imapFolder.getUIDNext();
				// verifico anche che l'uid salvato sia inferiore al prossimo
				// uid che sarà assegnato alla casella
				if (uidValidity != null && uidValiditySaved != null && uidSaved != null && uidValidity.equals(uidValiditySaved) && uidSaved <= nextUID) {
					startUID = uidSaved;
				} else {
					logger.warn("UIDValididity non più valida o ultimo UID non presente");
					if (uidValiditySaved != null || uidSaved != null) {
						try {
							mailBusiness.resetUIDMailbox(configbean.getMailboxid());
						} catch (Exception e) {
							logger.warn("Eccezione durante il reset di UID e UIDValidity salvati in database");
						}
						uidValiditySaved = null;
						uidSaved = null;
					}
				}
			}

			// scarico i messaggi a partire da un determinato indice e fino
			// all'ultimo UID inserito nella folder (-1) se non presente
			Message[] messages = imapFolder.getMessagesByUID(startUID, IMAPFolder.LASTUID);

			logger.info("Inizio la ricerca da: " + startUID);
			if (messages == null || messages.length == 0) {
				logger.error("Nessun messaggio trovato partendo da UID: " + startUID + " - " + IMAPFolder.LASTUID);
				return new Message[0];
			}
			logger.error("Messaggi trovati partendo da UID " + startUID + " :" + messages.length);

			List<Message> newMessages = new ArrayList<Message>();

			for (int index = 0; index < messages.length && !hasMoreMessage; index++) {

				Message message = messages[index];

				// il messaggio potrebbe essere stato rimosso nel frattempo per
				// cui non occorre verificarlo
				if (!message.isExpunged()) {

					uidValidity = imapFolder.getUIDValidity();
					if (searchByUID && uidValidity != null && uidValiditySaved != null && !uidValidity.equals(uidValiditySaved)) {
						// uid validity variata, ri-effettuo connessione
						hasMoreMessage = true;
						newMessages.clear();
						throw new MessagingException("UIDValidity variata: rieffettuo connessione");
					}

					// Controllo se il messagggio è già stato scaricato per
					// la
					// stessa mailbox e quindi non lo riscarico
					try {

						String messageId = null;

						try {
							// ricavo message id, la connessione alla folder
							// deve essere aperta
							messageId = MailUtil.getMessageID(message);
						} catch (FolderClosedException e) {
							// probabilmente si è perso la connessione alla
							// folder, interrompo il processo, verrà subito
							// ri-schedulato
							throw e;
						} catch (StoreClosedException e) {
							// probabilmente si è perso la connessione alla
							// folder, interrompo il processo, verrà subito
							// ri-schedulato
							throw e;
						} catch (Exception e) {
							// errore sulla singola mail, provo a procedere
							// con
							// i messaggi successivi
							logger.error("Errore nel recupero del messageId", e);
						}

						logger.info("MessageID: " + messageId);
						Long currentUID = imapFolder.getUID(message);
						logger.info("UID: " + currentUID);

						try {

							if (StringUtils.isBlank(messageId)) {

								logger.error("Scarto la mail visto che il messageId non è presente");

							} else {

								uidMap.put(messageId, currentUID);

								if (!mailBusiness.isMimeMessageElaborate(messageId, configbean.getMailboxid(), true)) {
									logger.info("Nuovo messaggio");
									newMessages.add(message);
									if (newMessages.size() == fetchSize && fetchSize != -1) {
										// Ho superato di uno i messaggi
										// fetchabili e ho altri messaggi da
										// scaricare, significa che devo
										// saltare
										// il delay
										hasMoreMessage = true;
									}
									// in questo punto non salvo ancora
									// l'UID
									// del messaggio, visto che non l'ho
									// ancora
									// salvato in database e quindi in caso
									// di
									// errore nel salvataggio potrei correre
									// il
									// rischio di saltare poi il messaggio
									// alla
									// successiva connessione
								} else {
									logger.info("Messaggio già presente in db");
									// messaggio già scaricato, salvo l'uid
									// del
									// messaggio in modo da partire da qui
									// alla
									// successiva iterazione
									// solo se non ho trovato alcun nuovo
									// messaggio
									if (searchByUID && newMessages.isEmpty() && (uidSaved == null || uidValiditySaved == null)
											|| (uidValidity != null && !uidValidity.equals(uidValiditySaved))
											|| (currentUID != null && !currentUID.equals(uidSaved))) {
										mailBusiness.saveOrUpdateUIDMailbox(configbean.getMailboxid(), currentUID, uidValidity);
										logger.info("UID salvato per la mailbox: " + currentUID);
										logger.info("UID Validity salvata per la mailbox: " + uidValidity);
										uidValiditySaved = uidValidity;
										uidSaved = currentUID;
									}
									// salvo in tutti i casi l'ultimo UID
									// aggiorno l'uid del messaggio
									mailBusiness.updateUidMessage(messageId, configbean.getMailboxid(), currentUID, uidValidity);
								}
							}

						} catch (Exception e) {
							logger.error("Errore nella ricerca del messaggio in database", e);
						}

					} catch (FolderClosedException e) {
						throw e; // lancio subito l'eccezione in modo che lo
						// scarico dei nuovi messaggi sia subito
						// ri-schedulato
					} catch (StoreClosedException e) {
						throw e; // lancio subito l'eccezione in modo che lo
						// scarico dei nuovi messaggi sia subito
						// ri-schedulato
					}
				}

			}
			return newMessages.toArray(new Message[0]);
		} else

		{
			throw new MessagingException("Interrotta la connessione alla folder");
		}

	}

	/**
	 * Metodo che verifica la presenza del messaggio nella folder ricercando prima per uid, se la validità coincide con quella della INBOX, in modo più
	 * efficiente e se non trova riscontri per messageId
	 * 
	 * @param messageId
	 * @param uid
	 * @param uidValidity
	 * @param folderName
	 * @return
	 * @throws Exception
	 */

	public Boolean checkMessageInFolder(final String messageId, final Long uid, final Long uidValidity, final String folderName) throws Exception {

		IMAPFolder folder = null;
		Boolean result = false;

		try {
			folder = (IMAPFolder) openFolder(folderName, Folder.READ_ONLY);
			Message message = null;

			Long uidValidityCurrent = folder.getUIDValidity();
			if ((folderName == null || folderName.equals(INBOX)) && uid != null && uidValidity != null && uidValidityCurrent != null
					&& uidValidityCurrent.equals(uidValidity)) {
				// ricerco per uid
				message = folder.getMessageByUID(uid);
			}
			if (message == null) {
				// ricerco per message id
				Message[] found = folder.search(new MessageIdSearchTerm(messageId));
				if (found == null || found.length == 0) {
					result = false;
				} else {
					message = found[0];
				}
			}

			if (message != null) {
				result = !message.isExpunged();
			} else {
				result = false;
			}

		}

		catch (Exception e) {
			logger.error("Errore metodo checkMessageInFolder", e);
			throw e;
		}

		finally {
			MailTransportUtils.closeFolder(folder, false);
		}

		return result;

	}

	public MailConfiguratorBean getConfigbean() {
		return configbean;
	}

	public void syncroAndDeleteBean(final String msgid, String foldername) throws Exception {
		Folder folder = null;
		try {
			folder = openFolder(foldername, Folder.READ_WRITE);
			Message[] msg = folder.search(new MessageIdSearchTerm(msgid));
			if (msg != null && msg.length > 0) {
				// Ho trovato il file
				SearchMailImpl lSearchMailImpl = new SearchMailImpl();
				boolean result = lSearchMailImpl.checkIfExistEmail(msgid, configbean.getAccount().getAccountAddress());
				if (logger.isDebugEnabled()) {
					logger.debug("Il risultato di syncroAndDeleteBean è: " + result);
				}
				if (result) {
					msg[0].setFlag(Flag.DELETED, true);
				} else {
					throw new Exception("Mail non trovata");
				}
			}
		} finally {
			MailTransportUtils.closeFolder(folder, true);
		}

	}

	public void caricaMailDaFs() {

	}

	/**
	 * 03.08.2017 Diego: metodo che serve per recuperare i messaggi in errore quando si caricadafs
	 * 
	 * @throws Throwable
	 * @throws Exception
	 * 
	 */
	public void addMimeMessageToElaborate() throws Exception, Throwable {
		String idMailBox = this.configbean.getMailboxid();
		logger.error("Ricerco nuovi messaggi da scaricare");
		FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();
		// pulizia dei precedenti messaggi in database prima di scaricare i
		// messaggi dalla casella
		// non cancello i messaggi discharged scaricati precedentemente
		mailBusiness.initializeMailboxMessage(idMailBox, false);
		// Lista dei messaggi in ritorno da elaborare
		List<Message> messageToReturn = new ArrayList<Message>();
		// istanzio una nuova sessione con le proprietà comuni
		Session mailSession = null;
		// inizializzo mappa uid
		uidMap = new HashMap<String, Long>();
		try {
			mailSession = Session.getInstance(Util.getJavaMailDefaultProperties());
		} catch (Exception e) {
			logger.error("Eccezione inizializzazione sessione mail", e);
		}

		List<FileMimeMessage> messaggiDaProcessare = new ArrayList<FileMimeMessage>();

		// recupero i messaggi in stato discharged precedentemente scaricati e
		// non ancora elaborati
		try {
			messaggiDaProcessare.addAll(mailBusiness.getMimeMessageDischargedToElaborate(idMailBox));
		} catch (Throwable e) {
			logger.error("Errore nel recupero dei messaggi discharged", e); // non
																			// rilancio
																			// l'eccezione
																			// in
																			// modo
																			// da
																			// cercare
																			// i
																			// nuovi
																			// messaggi
		}

		Integer messaggiInErroreAggiunti = 0;

		// Aggiungo i messaggi da elaborare andati in errore.
		try {

			// anche qui imposto un numero massimo di messaggi da ri-processare
			// per iterazione in modo da ottimizzare le risorse
			Integer fetchSize = MailboxConfigDefaultValue.DEFAULT_FETCH;
			if (configbean.getMailconfig().containsKey(MailBoxConfigKey.MAILBOX_FETCH.keyname())) {
				fetchSize = Integer.parseInt(configbean.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_FETCH.keyname()));
			}

			messaggiDaProcessare.addAll(mailBusiness.getMimeMessageToElaborate(idMailBox, fetchSize));

			for (FileMimeMessage messaggio : messaggiDaProcessare) {
				try {
					// Aggiungo all'array i messaggi da rieseguire
					if (messaggio.getFilemime() == null) {
						logger.warn("Nessuna referenziazione file/byte[] per il messaggio!");
					} else {
						if (messaggio.getData() != null) {
							messageToReturn.add(new MimeMessage(mailSession, new ByteArrayInputStream(messaggio.getData())));
							messaggiInErroreAggiunti++;
						} else {
							File lFile = null;
							FileInputStream fileStream = null;
							try {

								lFile = File.createTempFile("errorMessage", ".tmp");
								FileUtil.writeStreamToFile(messaggio.getFilemime(), lFile);
								fileStream = new FileInputStream(lFile);
								messageToReturn.add(new MimeMessage(mailSession, fileStream));
								messaggiInErroreAggiunti++;
							} catch (Exception e) {
								if (lFile != null) {
									FileUtil.deleteFile(lFile);
								}
							} finally {
								if (lFile != null) {
									FileUtil.deleteFile(lFile);
								}
								if (fileStream != null) {
									try {
										fileStream.close();
									} catch (Exception e) {
										logger.warn("Errore nella chiusura dello stream", e);
									}
								}
							}
						}

					}
				} catch (Exception exc) {
					// gestione dell'eccezione per il singolo messaggio in modo
					// da provare a processare quelli successivi
					LogUtility.logHibernateException(exc);
					logger.error("Errore nel recupero del messaggio in errore", exc);
				}
			}

			logger.error("Messaggi da ri-processare: " + messaggiInErroreAggiunti);
		} catch (Throwable e) {
			logger.error("Errore nel recupero dei messaggi in errore", e); // non
																			// rilancio
																			// l'eccezione
																			// in
																			// modo
																			// da
																			// processare
																			// comunque
																			// i
																			// nuovi
																			// messaggi,
																			// o
																			// quelli
																			// da
																			// ri-processare
																			// aggiunti
		}

		logger.error("Totale messaggi da processare: " + messageToReturn.size());
		for (Message lMimeMessage : messageToReturn) {
			String messageId;
			try {
				messageId = MailUtil.getInstance((MimeMessage) lMimeMessage).getMessageID();
				if (StringUtils.isNotBlank(messageId)) {
					// actualMessages.add(messageId);
					ProcessOrchestrator.newInstance(idMailBox).processing((MimeMessage) lMimeMessage);
				}
			} catch (Exception e) {
				logger.error("Errore nel recupero del message id", e);
			}
		}
	}

}