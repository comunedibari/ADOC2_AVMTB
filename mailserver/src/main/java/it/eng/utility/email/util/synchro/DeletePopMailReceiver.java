package it.eng.utility.email.util.synchro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.StoreClosedException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.integration.mail.MailTransportUtils;
import org.springframework.integration.mail.Pop3MailReceiver;

import com.sun.mail.pop3.POP3Folder;

import it.eng.aurigamailbusiness.database.egr.PttEmail;
import it.eng.aurigamailbusiness.database.egr.PttEmailNotifiche;
import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.utility.send.InputOutput;
import it.eng.aurigamailbusiness.utility.MailUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.util.ListUtil;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.bean.MailUiConfigurator;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.process.exception.ManyDeletedException;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.reader.config.MailConfiguratorBean;
import it.eng.utility.email.reader.info.MailBoxInfoKey;
import it.eng.utility.email.reader.search.MessageIdSearchTerm;
import it.eng.utility.session.SessionFileConfigurator;

public class DeletePopMailReceiver extends Pop3MailReceiver {

	public static final String INBOX = "INBOX";
	private static final Integer MAX_TRY_CONNECTION = 3;

	// forzatura della cancellazione dei messaggi anche se l'operazione di cancellazione non è abilitata
	private Boolean forceDeleteMessageFinished = false;
	private String enteForSession;
	private String idMailbox;
	private String idAccount;
	private Integer countMessageFolder; // totale dei messaggi nella folder
	private List<MailboxMessage> listMessageToDeleteInDb; // lista degli messaggi da cancellare in database

	public List<MailboxMessage> getListMessageToDeleteInDb() {
		return listMessageToDeleteInDb;
	}

	public void setListMessageToDeleteInDb(List<MailboxMessage> listMessageToDeleteInDb) {
		this.listMessageToDeleteInDb = listMessageToDeleteInDb;
	}

	private List<String> listIdMessageDeletedFromFolder; // lista degli id dei messaggi rimossi dalla folder
	private List<String> listIdMessageBackup; // lista degli id dei messaggi inseriti nella cartella di backup
	private Integer limit; // numero massimo dei messaggi da cancellare
	private Boolean enableBackup; // abilito il backup prima di cancellare il messaggio
	private String folderBackup; // folder di backup temporanea
	private Integer counterMessageVerified; // contatore dei messaggi verificati
	private Integer hourDelay; // intervallo minimo in ore da attendere prima di effettuare la cancellazione da quando la mail è entrata
	private Boolean forceExpunge; // forzo l'eliminazione del messaggio anche se esistono altri messaggi con flag deleted nella folder

	public String getFolderBackup() {
		return folderBackup;
	}

	public void setFolderBackup(String folderBackup) {
		this.folderBackup = folderBackup;
	}

	public List<String> getListIdMessageBackup() {
		return listIdMessageBackup;
	}

	public void setListIdMessageBackup(List<String> listIdMessageBackup) {
		this.listIdMessageBackup = listIdMessageBackup;
	}

	public List<String> getListIdMessageDeletedFromFolder() {
		return listIdMessageDeletedFromFolder;
	}

	public void setListIdMessageDeletedFromFolder(List<String> listIdMessageDeletedFromFolder) {
		this.listIdMessageDeletedFromFolder = listIdMessageDeletedFromFolder;
	}

	public MailConfiguratorBean getConfigBean() {
		return configBean;
	}

	public void setConfigBean(MailConfiguratorBean configBean) {
		this.configBean = configBean;
	}

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	public String getEnteForSession() {
		return enteForSession;
	}

	public void setEnteForSession(String enteForSession) {
		this.enteForSession = enteForSession;
	}

	public String getIdMailbox() {
		return idMailbox;
	}

	public void setIdMailbox(String idMailbox) {
		this.idMailbox = idMailbox;
	}

	public DeletePopMailReceiver(String url) {
		super(url);
		folderOpenMode = Folder.READ_WRITE;
		SessionFileConfigurator lSessionFileConfigurator = (SessionFileConfigurator) SpringAppContext.getContext().getBean("SessionFileConfigurator");
		if (ListUtil.isNotEmpty(lSessionFileConfigurator.getSessions())) {
			enteForSession = lSessionFileConfigurator.getSessions().get(0).getSessionName();
		} else {
			enteForSession = "DEFAULT";
		}
		countMessageFolder = 0;
		listMessageToDeleteInDb = new ArrayList<MailboxMessage>();
		listIdMessageDeletedFromFolder = new ArrayList<String>();
		listIdMessageBackup = new ArrayList<String>();

	}

	public DeletePopMailReceiver(String url, String idMailbox) {
		this(url);
		this.idMailbox = idMailbox;
	}

	private MailConfiguratorBean configBean;

	private static Logger mLogger = LogManager.getLogger(DeleteMailReceiver.class);
	
	@Override
	public Message[] receive() throws MessagingException {
		try {
			Folder folder = this.getFolder();
			if (folder == null || !folder.isOpen()) {
				openFolder();
			}
			return (Message[]) super.receive();
		} catch (Exception ex) {
			throw new MessagingException(ex.getLocalizedMessage(), ex);
		}
	}

	@Override
	protected Message[] searchForNewMessages() throws MessagingException {

		Folder folder = null;
		openFolder();
		folder = this.getFolder();
		if (!folder.isOpen()) {
			folder.open(Folder.READ_WRITE);
		}
		// mappa con id messaggio e messaggio che è da cancellare (o è già stato cancellato dalla webmail)
		FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();
		String consoleType = MailUiConfigurator.getConsoleType();

		Boolean searchByUID = Boolean.parseBoolean(mailBusiness.getMailBoxConfigParameter(this.idMailbox, MailBoxConfigKey.MAILBOX_SEARCH_BY_UID));

		Long uidValiditySaved = null;
		Long uidSaved = null;

		// Recupero l'eventuale UIDValidity salvata in MAILBOX_CONFIG e la confronto con quella corrente - occorre recuperare sempre i valori da database
		// visto che potrebbero essere aggiornati rispetto ai valori salvati in fase di avvio del server
		if (StringUtils.isNotBlank(mailBusiness.getMailBoxInfo(this.idMailbox, MailBoxInfoKey.MAILBOX_UID_VALIDITY))) {
			uidValiditySaved = Long.parseLong(mailBusiness.getMailBoxInfo(this.idMailbox, MailBoxInfoKey.MAILBOX_UID_VALIDITY));
			mLogger.info("UIDValidity salvata: " + uidValiditySaved);
		}

		// Recupero l'eventuale ultimo UID del messaggio salvato in MAILBOX_CONFIG e lo utilizzo per iniziare la ricerca di nuovi messaggi
		if (StringUtils.isNotBlank(mailBusiness.getMailBoxInfo(this.idMailbox, MailBoxInfoKey.MAILBOX_LAST_MESSAGE_UID))) {
			uidSaved = Long.parseLong(mailBusiness.getMailBoxInfo(this.idMailbox, MailBoxInfoKey.MAILBOX_LAST_MESSAGE_UID));
		}

		if (folder.isOpen()) {

			mLogger.info(String.format("Connessione attiva - Inizio cancellazione dei messaggi dalla mailbox %1$s", this.idMailbox));

			POP3Folder pop3Folder = (POP3Folder) folder;

			countMessageFolder = pop3Folder.getMessageCount();

			mLogger.debug("Messaggi presenti inizialmente nella folder: " + pop3Folder.getMessageCount());

			// creo la folder di backup
			if (enableBackup && StringUtils.isNotBlank(folderBackup)) {
				try {
					createFolder(folderBackup, true);
					mLogger.info(String.format("Creo folder di backup %1$s (se non presente)", folderBackup));
				} catch (Exception e) {
					mLogger.error("Errore nella creazione della folder di backup", e);
					throw new MessagingException("Errore nella creazione della folder di backup", e);
				}
			}

			Integer numTryConnection = 0; // numero di tentativi di connessione alla folder prima di lanciare l'errore

			counterMessageVerified = 0; // numero di messaggi verificati

			Boolean limitOver = false;

			// cerco di cancellare prima i messaggi con stato PLANNED in input, se invece è attiva la modalità di forzatura procedo direttamente al
			// controllo di tutti i messaggi nella mailbox

			List<String> listIdMessageToDeleteInDb = new ArrayList<String>();
			for (Integer index = 0; index < listMessageToDeleteInDb.size(); index++) {
				listIdMessageToDeleteInDb.add(listMessageToDeleteInDb.get(index).getId().getMessageId());
			}

			if (!forceDeleteMessageFinished) {

				for (Integer index = 0; index < listMessageToDeleteInDb.size() && !limitOver; index++) {

					MailboxMessage mailboxMessage = listMessageToDeleteInDb.get(index);

					Long uid = mailboxMessage.getMessageUid();
					Long uidValidity = mailboxMessage.getMailboxUidValidity();

					String messageId = mailboxMessage.getId().getMessageId();

					mLogger.info("Ricerco messageID da cancellare dalla folder: " + messageId);

					try {

						Message[] msgToDelete = null;
						Message messageUID = null;

//						if (uid != null && uidValidity != null && uidValiditySaved != null && uidValidity.equals(uidValiditySaved)) {
//							mLogger.info("Ricerco per UID: " + uid);
//							// cerco prima per uid
//							messageUID = pop3Folder.getMessageByUID(uid);
//						}

						if (messageUID == null) {
							// ricerco per messageid
							mLogger.info("Ricerco per MessageID");
							msgToDelete = pop3Folder.search(new MessageIdSearchTerm(messageId));
							uid = null;
							uidValidity = null;
						} else {
							msgToDelete = new Message[1];
							msgToDelete[0] = messageUID;
						}

						if (msgToDelete != null) {

							if (msgToDelete.length == 0) {

								mLogger.warn(String.format("Nessun messaggio da cancellare con id %1$s", messageId));

							} else if (msgToDelete.length > 1) {

								mLogger.warn(String.format("Trovato più di un messaggio corrispondente all'id %1$s", messageId));

							} else {

								counterMessageVerified++;

								Boolean deleted = this.deleteMsg(msgToDelete[0], messageId, uid, uidValidity, pop3Folder);

								if (deleted) {

									// messaggio cancellato con successo
									listIdMessageDeletedFromFolder.add(messageId);

									if (limit != null && listIdMessageDeletedFromFolder.size() >= limit) {

										// raggiunto il limite di messaggi da cancellare per questa schedulazione
										limitOver = true;
									}

									mLogger.info(String.format("Rimosso con successo messaggio con id %1$s dalla mailbox %2$s", messageId, idMailbox));

								} else {

									throw new Exception(String.format("Messaggio con id %1$s non rimosso dalla mailbox %2$s", messageId, idMailbox));

								}

							}
						}

					}

					catch (Exception e) {

						mLogger.error("Eccezione nella sincronizzazione del messaggio avente id: " + messageId, e);
						// verifico se ci sono errori bloccanti o posso provare a cancellare i successivi messaggi
						if (e instanceof FolderClosedException || e instanceof StoreClosedException) {
							// provo ad aprire la casella, effettuando vari tentativi
							if (numTryConnection < MAX_TRY_CONNECTION) {
								numTryConnection++;
								if (!pop3Folder.isOpen()) {
									pop3Folder.open(Folder.READ_WRITE);
								}
							} else {
								// superato il numero di tentativi di connessione
								throw new MessagingException("Interrotta la connessione alla folder");
							}
						} else if (e instanceof ManyDeletedException) {
							throw new MessagingException("Sono presenti altri messaggi con flag DELETED nella folder. Interrompo la cancellazione");
						}
					}

				}

				mLogger.debug("Messaggi presenti nella folder dopo la prima fase di cancellazione: " + pop3Folder.getMessageCount());

			} else {
				mLogger.debug("Messaggi presenti nella folder: " + pop3Folder.getMessageCount());
			}

			if (!limitOver && pop3Folder.getMessageCount() > 0) {

				// posso procedere con la cancellazione partendo dalla mailbox visto che ci sono altri messaggi nella mailbox, che potrebbero essere da
				// cancellare

				int endUID = pop3Folder.getMessageCount(); // vado fino all'ultimo messaggio presente, -1 se non valorizzato

				Message[] messagesFolder = pop3Folder.getMessages(1, endUID);

				mLogger.info("Messaggi ricavati nell'intervallo 1 - " + endUID + ": " + messagesFolder.length);

				for (int index = 0; index < messagesFolder.length && !limitOver; index++) {

					Message message = messagesFolder[index];

					if (message != null) {

						counterMessageVerified++;

						String messageId = null;
						try {
							messageId = MailUtil.getMessageID(message);
						} catch (FolderClosedException e) {
							mLogger.error("Errore di connessione alla mailbox " + this.idMailbox, e);
							break; // esco dal ciclo e provo a procedere con le operazioni attuali
						} catch (StoreClosedException e) {
							mLogger.error("Errore di connessione alla mailbox " + this.idMailbox, e);
							break; // esco dal ciclo e provo a procedere con le operazioni attuali
						} catch (Exception e) {
							mLogger.error("Errore nel recupero del message id", e);
						}
						if (StringUtils.isNotEmpty(messageId)) {

							try {

								MailboxMessage mailBoxMessage = null;
								if ((mailBoxMessage = mailBusiness.getMailBoxMessageWithOperations(messageId, this.idMailbox)) != null) {

									// cancello il messaggio se è nella lista dei messaggi con cancellazione pianificata o conclusa (flusso delle operazioni)
									// oppure se è abilitata la cancellazione di tutti i messaggi processati, indipendentemente dal flusso delle operazioni, in
									// questo caso il messaggio deve essere presente in T_EMAIL_MGO o nella relativa tabella di EGR ma considero sempre il delay

									if ((listIdMessageToDeleteInDb.contains(messageId) || mailBusiness.canBeDeletedMailboxMessage(mailBoxMessage)
											|| (forceDeleteMessageFinished && isPresentInTemailMgo(consoleType, messageId,
													configBean.getAccount().getAccountAddress(), idMailbox, idAccount) && mailBusiness.canBeDeletedMailboxMessage(mailBoxMessage)))) {

										Boolean deleted = this.deleteMsg(message, messageId, mailBoxMessage.getMessageUid(),
												mailBoxMessage.getMailboxUidValidity(), pop3Folder);

										if (deleted) {

											// messaggio cancellato con successo
											listIdMessageDeletedFromFolder.add(messageId);
											if (limit != null && listIdMessageDeletedFromFolder.size() >= limit) {
												// raggiunto il limite di messaggi da cancellare per questa schedulazione
												limitOver = true;
											}

											mLogger.info(String.format("Rimosso con successo messaggio con id %1$s dalla mailbox %2$s", messageId, idMailbox));

										} else {

											throw new Exception(String.format("Messaggio con id %1$s non rimosso dalla mailbox %2$s", messageId, idMailbox));

										}

									}

								}
							} catch (Exception e) {
								mLogger.error("Eccezione nella sincronizzazione del messaggio avente id: " + messageId, e);
								// verifico se ci sono errori bloccanti o posso provare a cancellare i successivi messaggi
								if (e instanceof FolderClosedException || e instanceof StoreClosedException) {
									// provo ad aprire la casella, effettuando vari tentativi
									if (numTryConnection < MAX_TRY_CONNECTION) {
										numTryConnection++;
										if (!pop3Folder.isOpen()) {
											pop3Folder.open(Folder.READ_WRITE);
										}
									} else {
										// superato il numero di tentativi di connessione
										throw new MessagingException("Interrotta la connessione alla folder");
									}
								} else if (e instanceof ManyDeletedException) {
									throw new MessagingException("Sono presenti altri messaggi con flag DELETED nella folder. Interrompo la cancellazione");
								}
							}

						}

					}

				}

			}
			return new Message[] {};
		} else

		{
			throw new MessagingException("Interrotta la connessione alla folder");
		}

	}

	private Boolean isPresentInTemailMgo(String consoleType, String messageId, String accountAddress, String idMailBox, String idAccount)
			throws MessagingException {
		Session session = null;
		Session sessionEgr = null;
		try {

			Calendar now = Calendar.getInstance();
			if (hourDelay > 0) {
				now.add(Calendar.HOUR, -hourDelay);
			}

			// devo tener conto del delay per la cancellazione

			try {
				session = SubjectInitializer.getSession(idMailBox);
			} catch (Exception e) {
				throw new MessagingException("Impossibile aprire la connessione ", e);
			}
			if (messageId == null) {
				throw new MessagingException("Messaggio senza idMessage");
			}
			String lString = null;
			// DIEGO L.: modifica per essere multiente...
			if (consoleType != null && consoleType.equalsIgnoreCase("egr")) {
				List<String> connectionStrings = session.createSQLQuery("SELECT config.config_Value" + "  FROM Mailbox_Operation_Config config"
						+ " WHERE config.config_Key = 'egrammatamessageoperation.databaseurl'" + "       AND config.id_mailbox_operation in"
						+ "              (select oper.id_mailbox_operation" + "              from MailBox_Operation oper"
						+ "              where oper.OPERATION_NAME = 'EGrammataMessageOperation'" + "               AND oper.id_Mailbox ="
						+ "                      ?)").setString(0, idMailBox).list();
				String user = "";
				String password = "";
				for (String connectionString : connectionStrings) {
					if (connectionString != null && !connectionString.equalsIgnoreCase("")) {
						Pattern pattern = Pattern.compile("\\:[\\w]*\\/");
						Matcher matcher = pattern.matcher(connectionString);
						if (matcher.find()) {
							// user
							user = matcher.group(0);
							user = user.substring(1, user.length() - 1);
						}
						pattern = Pattern.compile("\\/[\\w]*@");
						matcher = pattern.matcher(connectionString);
						if (matcher.find()) {
							// password
							password = matcher.group(0);
							password = password.substring(1, password.length() - 1);
						}
					}
					// se sono sotto auriga per essere multiente devo recuperare la connessione dell'ENTEXX e poi eseguire la query da li...
					try {
						sessionEgr = SubjectInitializer.getSession(connectionString, user, password);
					} catch (Exception e) {
						throw new MessagingException("Impossibile aprire la connessione: " + connectionString, e);
					}
					// nell'idAccount per egr viene passato l'indirizzo dell'account di scarico
					Criteria criteria = sessionEgr.createCriteria(PttEmail.class, "pttEmail").add(Restrictions.eq("messageId", messageId))
							.add(Restrictions.eq("pecRif", accountAddress)).add(Restrictions.eq("entrataUscita", "E"));
					if (hourDelay > 0) {
						// devo considerare il delay per effettuare la cancellazione
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MailboxMessageOperation.class, "mailboxMessageOperation");
						detachedCriteria.createAlias("mailboxOperation", "mailboxOperation");
						detachedCriteria.add(Restrictions.eq("id.idMailbox", idMailBox));
						detachedCriteria.add(Property.forName("id.messageId").eqProperty("pttEmail.messageId"));
						detachedCriteria.add(Restrictions.eq("mailboxOperation.operationName", "MessageArchiveOperation"));
						detachedCriteria.add(Property.forName("dateOperation").le(now.getTime()));
						criteria.add(Subqueries.exists(detachedCriteria.setProjection(Projections.property("id"))));
					}
					lString = (String) criteria.setProjection(Projections.property("messageId")).uniqueResult();
					// controllo se è dalla PEI
					if (lString == null) {
						lString = (String) sessionEgr.createCriteria(PttEmail.class).add(Restrictions.eq("messageId", messageId))
								.add(Restrictions.eq("entrataUscita", "E")).setProjection(Projections.property("messageId")).uniqueResult();
					}
					// controllo se è una notifica
					if (lString == null) {
						lString = (String) sessionEgr.createCriteria(PttEmailNotifiche.class).add(Restrictions.eq("messageId", messageId))
								.setProjection(Projections.property("messageId")).uniqueResult();
					}
					if (sessionEgr != null) {
						sessionEgr.close();
					}
				}
			} else {

				Criteria criteria = session.createCriteria(TEmailMgo.class, "emailMgo").add(Restrictions.eq("mailboxAccount.idAccount", idAccount))
						.add(Restrictions.eq("flgIo", InputOutput.INGRESSO.getValue())).add(Restrictions.eq("messageId", messageId));

				if (hourDelay > 0) {
					// devo considerare il delay per effettuare la cancellazione
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MailboxMessageOperation.class, "mailboxMessageOperation");
					detachedCriteria.createAlias("mailboxOperation", "mailboxOperation");
					detachedCriteria.add(Restrictions.eq("id.idMailbox", idMailBox));
					detachedCriteria.add(Property.forName("id.messageId").eqProperty("emailMgo.messageId"));
					detachedCriteria.add(Restrictions.eq("mailboxOperation.operationName", "MessageArchiveOperation"));
					detachedCriteria.add(Property.forName("dateOperation").le(now.getTime()));
					criteria.add(Subqueries.exists(detachedCriteria.setProjection(Projections.property("id"))));
				}

				criteria.setMaxResults(1);

				List<TEmailMgo> listaEmails = criteria.list();
				if (ListUtil.isNotEmpty(listaEmails) && listaEmails.get(0) != null) {
					lString = listaEmails.get(0).getMessageId();
				}
			}
			return lString != null;
		} catch (Exception e) {
			mLogger.error("Error: " + e.getLocalizedMessage(), e);
			throw new MessagingException("Error: " + e.getLocalizedMessage());
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
			if (sessionEgr != null) {
				sessionEgr.close();
				sessionEgr = null;
			}
		}
	}

	/**
	 * Creo la folder con nome in input e la restituisco
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
	 * Creo la folder con nome in input e la restituisco, specificando se è a livello della root
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
	 * Copia il messaggio da una cartella ad un'altra: se {@link foldernameFrom} è null allora è la INBOX o la cartella impostata nell'url iniziale
	 * 
	 * @param messageId
	 * @param folderNameFrom
	 * @param folderNameTo
	 * @throws Exception
	 */
	public void copy(final Message originalMsg, final String messageId, final Folder folderFrom, final String folderNameTo) throws Exception {

		Folder folderTo = null;

		try {

			try {
				folderTo = openFolder(folderNameTo, Folder.READ_WRITE, true);
			} catch (FolderNotFoundException e) {
				mLogger.debug("Copy: fallita apertura cartella per il seguente errore: " + e.getMessage()
						+ ", provo ad aprire la cartella come sottocartella della root");

				folderTo = openFolderInRootFolder(folderNameTo, Folder.READ_WRITE);
			}

			Message[] checkMsg = folderTo.search(new MessageIdSearchTerm(messageId));

			// evito duplicazioni del messaggio

			if (checkMsg == null || checkMsg.length == 0) {

				// il messaggio non esiste già nella cartella di destinazione
				// la folder può essere chiusa prima di effettuare la copia
				MailTransportUtils.closeFolder(folderTo, false);

				Message[] toCopy = new Message[1];
				toCopy[0] = originalMsg;

				folderFrom.copyMessages(toCopy, folderTo);

			}

		} finally {
			MailTransportUtils.closeFolder(folderTo, false);
		}
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
					throw new Exception("Folder non presente");
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

	/**
	 * Apre la connessione al folder
	 * 
	 * @param name
	 * @param mode
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
	 * Apre la connessione alla folder nella modalità specificata, cercandola nella root
	 * 
	 * @param name
	 * @param mode
	 * @return
	 * @throws Exception
	 */

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

		POP3Folder folder = null;
		Boolean result = false;

		try {
			folder = (POP3Folder) openFolder(folderName, Folder.READ_ONLY);
			Message message = null;

//			Long uidValidityCurrent = folder.getUIDValidity();
//			if ((folderName == null || folderName.equals(INBOX)) && uid != null && uidValidity != null && uidValidityCurrent != null
//					&& uidValidityCurrent.equals(uidValidity)) {
//				// ricerco per uid
//				message = folder.getMessageByUID(uid);
//			}
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
			mLogger.error("Errore metodo checkMessageInFolder", e);
			throw e;
		}

		finally {
			MailTransportUtils.closeFolder(folder, false);
		}

		return result;

	}

	/**
	 * 
	 */

	private Boolean deleteMsg(final Message message, final String messageId, final Long uid, final Long uidValidity, final Folder folder) throws Exception {

		Boolean deleted = false;

		// effettuo un backup prima di eseguire la cancellazione, se attivo

		if (enableBackup && StringUtils.isNotBlank(folderBackup)) {
			copy(message, messageId, folder, folderBackup);
			if (checkMessageInFolder(messageId, uid, uidValidity, folderBackup)) {
				listIdMessageBackup.add(messageId); // non ho la garanzia che il messaggio sia effettivamente copiato, a volte la
													// copia
													// viene effettuata successivamente
				mLogger.info(String.format("Copiato con successo messaggio con id %1$s dalla mailbox %2$s nella folder di backup %3$s", messageId, idMailbox,
						folderBackup));
			} else {
				// in questo momento lancio l'eccezione in modo da interrompere la cancellazione, che sarà ri-eseguita
				// successivamente
				// quando il messaggio si trova nella cartella
				throw new Exception("Messaggio non copiato nella cartella di destinazione");
			}
		}

		// cancellazione è abilitata, imposto il flag deleted per il messaggio da cancellare
		// inoltre setto il flag "Visto" in modo che eventuali notifiche di lettura siano inviate ai mittenti, invece che arrivino le notifiche di
		// messaggio non letto cancellato
		message.setFlag(Flag.SEEN, true);
		message.setFlag(Flag.DELETED, true);
		Integer numberFlagDeleted = folder.getDeletedMessageCount(); // numero di messaggi con flag deleted nella folder
		if (numberFlagDeleted == 1 || forceExpunge) {
			if (numberFlagDeleted > 1 && forceExpunge) {
				mLogger.info(String.format("Numero dei messaggi con flag deleted nella casella %1$d", numberFlagDeleted));
				mLogger.info("Elimino anche gli altri messaggi con flag deleted nella folder");
			}
			// pulizia della folder
			// folder.expunge();
			// nel pop3 bisgna chiudere la folder
			folder.close(true);
			if (folder == null || !folder.isOpen()) {
				openFolder();
			}
			// il messaggio originale in questo punto è stato del tutto rimosso dal server, qualsiasi metodo invocato genera
			// un'eccezione
			// MessageRemovedException
			// gli unici metodi ammessi sono isExpunged e getMessageNumber (posizione originale del messaggio nella folder prima di
			// essere
			// rimosso)
			if (!message.isExpunged()) {
				// in alcuni casi il messaggio non viene settato come Expunged se la cancellazione è effettuata esternamente da
				// un'altra
				// sorgente
				// provo a invocare un metodo, per vedere se la cancellazione è stata effettuata
				try {
					message.getAllHeaders();
				} catch (MessageRemovedException exc) {
					// il messaggio è stato effettivamente rimosso visto che è stata sollevata la relativa eccezione
					deleted = true;
				}
			} else {
				deleted = true;
			}
		} else {
			mLogger.error(String.format("Numero dei messaggi con flag deleted nella casella %1$d", numberFlagDeleted));
			throw new ManyDeletedException("Sono presenti altri messaggi con flag DELETED nella folder. Interrompo la cancellazione");
		}

		return deleted;

	}

	public void getListFolder() {

	}

	public Integer getCountMessageFolder() {
		return countMessageFolder;
	}

	public void setCountMessageFolder(Integer countMessageFolder) {
		this.countMessageFolder = countMessageFolder;
	}

	public Boolean getForceDeleteMessageFinished() {
		return forceDeleteMessageFinished;
	}

	public void setForceDeleteMessageFinished(Boolean forceDeleteMessageFinished) {
		this.forceDeleteMessageFinished = forceDeleteMessageFinished;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Boolean getEnableBackup() {
		return enableBackup;
	}

	public void setEnableBackup(Boolean enableBackup) {
		this.enableBackup = enableBackup;
	}

	public Integer getCounterMessageVerified() {
		return counterMessageVerified;
	}

	public void setCounterMessageVerified(Integer counterMessageVerified) {
		this.counterMessageVerified = counterMessageVerified;
	}

	public Integer getHourDelay() {
		return hourDelay;
	}

	public void setHourDelay(Integer hourDelay) {
		this.hourDelay = hourDelay;
	}

	public Boolean getForceExpunge() {
		return forceExpunge;
	}

	public void setForceExpunge(Boolean forceExpunge) {
		this.forceExpunge = forceExpunge;
	}

}