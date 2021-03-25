package it.eng.utility.email.util.synchro;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.FolderClosedException;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.StoreClosedException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxAccountConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperationId;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.aurigamailbusiness.utility.cryptography.CryptographyUtil;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.operation.impl.deletemessageoperation.DeleteMessageBean;
import it.eng.utility.email.process.exception.MailServerException;
import it.eng.utility.email.reader.MessageStatus;
import it.eng.utility.email.reader.OperationStatus;
import it.eng.utility.email.reader.config.AccountConfigBean;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.reader.config.MailConfiguratorBean;
import it.eng.utility.email.reader.info.MailBoxInfoKey;
import it.eng.utility.email.scheduler.DeleteScheduler;
import it.eng.utility.email.util.xml.XmlUtil;

public class SynchroMail {

	private List<String> listIdMessageDeletedFromFolder; // lista id messaggi rimossi dalla folder
	private List<String> listIdMessageBackuped; // lista id messaggi copiati nella folder di backup
	private Integer countMessageFolder; // totale dei messaggi nella folder
	private Integer counter; // contatore dei messaggi processati prima dell'interruzione
	private Boolean enableBackup; // abilito il backup del messaggio in una folder di appoggio prima di effettuare la cancellazione
	private String folderBackup; // nome della folder di backup, se non è presente la crea

	private static Logger mLogger = LogManager.getLogger(SynchroMail.class);

	private static final Integer MAX_TRY_CONNECTION = 3;

	/**
	 * Metodo che si collega alla mailbox in input e sincronizza i messaggi fra la mailbox e il database, eliminando i messaggi aventi l'operazione di
	 * cancellazione pianificata o completata, se {@link enableDelete} è true. Se {@link forceDelete} è true allora si procede con l'eliminazione dei messaggi
	 * anche se questi non hanno la relativa operazione di cancellazione configurata. In questo caso l'unica condizione è che il messaggio sia stato
	 * correttamente archiviato <br>
	 * 
	 * @param idMailbox
	 * @throws MessagingException
	 */

	public void synchro(String idMailbox, Boolean forceDelete, Boolean forceDeleteWithDelay, Integer limit, Boolean enableBackup, String folderBackup) throws Exception {

		ThreadContext.put("DELETE-ROUTINGKEY", idMailbox);

		// verifico se esiste già un thread che sta effettuando la cancellazione
		Boolean stateDeleteMailbox = DeleteScheduler.getThreadStateDeleteForMailbox(idMailbox);
		if (!stateDeleteMailbox) {
			// indico che è in corso una cancellazione per la mailbox
			DeleteScheduler.setThreadStateDeleteForMailbox(idMailbox, true);
		} else {
			mLogger.info("E' già in corso una cancellazione per la mailbox avente id " + idMailbox);
			throw new Exception("E' già in corso una cancellazione per la mailbox");
		}

		try {

			mLogger.info("Avvio la cancellazione per la mailbox " + idMailbox);

			Mailbox lMailbox = getMailBox(idMailbox);

			final MailConfiguratorBean config = getMailConfiguratorBean(lMailbox);
			String hostPop = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.POP3_HOST.keyname());
			String urlFolder = "";
			if (StringUtils.isBlank(hostPop)) {
				urlFolder = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_STORE_PROTOCOL.keyname()) + "://"
						+ config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_HOST.keyname()) + ":"
						+ config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_PORT.keyname()) + "/" + config.getFolder();

			} else {
				urlFolder = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.POP3_STORE_PROTOCOL.keyname()) + "://"
						+ config.getAccount().getAccountconfig().getProperty(AccountConfigKey.POP3_HOST.keyname()) + ":"
						+ config.getAccount().getAccountconfig().getProperty(AccountConfigKey.POP3_PORT.keyname()) + "/" + config.getFolder();
			}
					
			Integer hourDelay = 0; // differenza in ore fra la data di inserimento della mail e il tentativo di cancellazione della mail dalla INBOX
			if (StringUtils.isNotBlank(config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DELETE_DELAY.keyname()))) {
				hourDelay = Integer.parseInt(config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DELETE_DELAY.keyname()));
			}

			Boolean forceExpunge = false; // forzo la cancellazione di messaggi con flag deleted precedenti nella folder
			if (StringUtils.isNotBlank(config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DELETE_FORCE_EXPUNGE.keyname()))) {
				forceExpunge = Boolean.parseBoolean(config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DELETE_FORCE_EXPUNGE.keyname()));
			}
			// 02.02.2021
			// DiegoL: cancellazione POP3... fatta in modo semplice... 
			if (!StringUtils.isBlank(hostPop)) {
				this.gestioneDeletePop3(urlFolder, idMailbox, lMailbox, config, limit, hourDelay, forceDelete, forceExpunge, enableBackup, folderBackup);
				return ;
			}

			DeleteMailReceiver mailReceiver = new DeleteMailReceiver(urlFolder);
			mailReceiver.setIdMailbox(idMailbox);
			FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();

			// configurazioni cartella sul server
			mailReceiver.setConfigBean(config);
			mailReceiver.setShouldMarkMessagesAsRead(false);
			mailReceiver.setShouldDeleteMessages(false);

			// configurazioni del bean
			mailReceiver.setIdAccount(lMailbox.getMailboxAccount().getIdAccount());
			mailReceiver.setForceDeleteMessageFinished(forceDelete); // abilitazione della pulizia dei messaggi anche non esiste la relativa operazione di
			mailReceiver.setForceDeleteMessageWithDelay(forceDeleteWithDelay);
			mailReceiver.setLimit(limit); // numero massimo di messaggi da cancellare per schedulazione
			mailReceiver.setEnableBackup(enableBackup);
			mailReceiver.setFolderBackup(folderBackup);
			mailReceiver.setHourDelay(hourDelay);
			mailReceiver.setForceExpunge(forceExpunge);

			// Controllo se devo inserire l'authenticator
			mailReceiver.setJavaMailProperties(config.getAccount().getAccountconfig());
			mailReceiver.setProtocol(config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_STORE_PROTOCOL.keyname()));
			mailReceiver.setJavaMailAuthenticator(new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					String password = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname());
					try {
						password = CryptographyUtil.decryptionAccountPasswordWithAES(config.getAccount().getAccountconfig());
					} catch (Exception e) {
						mLogger.warn("Errore decrittazione password: " + e.getMessage(), e);
					}
					String username = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
					// in alcuni casi potrebbe essere specificato un username specifico per imap
					if (config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_USERNAME.keyname()) != null) {
						username = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_USERNAME.keyname());
					}
					return new PasswordAuthentication(username, password);

				}
			});

			List<MailboxMessageOperation> listaOperazioni = new ArrayList<MailboxMessageOperation>();
			if (!forceDelete) {
				// recupero le operazioni di cancellazione in stato "PLANNED", solo se non vanno cancellati i messaggi indipendentemente dalla cancellazione
				String[] listaStati = new String[] { OperationStatus.OPERATION_PLANNED.status() };
				listaOperazioni = getMailboxMessageOperationDelete(idMailbox, lMailbox.getMailboxAccount().getIdAccount(), listaStati, limit, hourDelay);
				List<MailboxMessage> listaMessaggi = new ArrayList<MailboxMessage>();
				for (Iterator<MailboxMessageOperation> iterator = listaOperazioni.iterator(); iterator.hasNext();) {
					MailboxMessageOperation mailboxMessageOperation = (MailboxMessageOperation) iterator.next();
					listaMessaggi.add(mailboxMessageOperation.getMailboxMessage());
				}
				mailReceiver.setListMessageToDeleteInDb(listaMessaggi);
			}
			// sincronizzo la mailbox in modo da richiamare il metodo sovrascritto searchNewMessages, che si collega alla mailbox e elimina i messaggi da
			// cancellare
			// registro che inizio la cancellazione schedulata dei messaggi
			try {
				mailBusiness.saveOrUpdateInfoMailbox(idMailbox, MailBoxInfoKey.MAILBOX_LAST_DELETE_SCHEDULED,
						new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
			} catch (Exception e1) {
				mLogger.warn("Errore nel salvataggio dell'informazione della cancellazione in database");
			}

			try {
				mailReceiver.receive();
			} catch (Exception exc) {
				mLogger.error("Errore SynchroMailReceiver", exc);
			}
			// anche in caso di errore eseguo le operazioni di chiusura
			countMessageFolder = mailReceiver.getCountMessageFolder();
			counter = mailReceiver.getCounterMessageVerified();
			// il metodo receive in automatico apre e chiude la connessione alla mailbox
			mLogger.info("Messaggi iniziali nella folder: " + countMessageFolder);
			mLogger.info("Messaggi verificati della folder: " + counter);
			listIdMessageDeletedFromFolder = mailReceiver.getListIdMessageDeletedFromFolder();
			if (enableBackup) {
				listIdMessageBackuped = mailReceiver.getListIdMessageBackup();
				if (listIdMessageBackuped == null) {
					listIdMessageBackuped = new ArrayList<String>();
				}
				mLogger.info("Messaggi copiati in backup " + listIdMessageBackuped.size() + " : " + listIdMessageBackuped);
			}
			if (listIdMessageDeletedFromFolder == null) {
				listIdMessageDeletedFromFolder = new ArrayList<String>();
			}
			mLogger.info("Messaggi cancellati " + listIdMessageDeletedFromFolder.size() + " : " + listIdMessageDeletedFromFolder);
			// aggiorno lo stato in database dei messaggi in stato PLANNED

			Integer numTryConnection = 0; // numero di tentativi di connessione alla folder prima di lanciare l'errore

			for (Iterator<MailboxMessageOperation> iterator = listaOperazioni.iterator(); iterator.hasNext();) {

				MailboxMessageOperation mailboxMessageOperation = (MailboxMessageOperation) iterator.next();
				MailboxMessageOperationId idOperation = mailboxMessageOperation.getId();
				MailboxMessage mailboxMessage = mailboxMessageOperation.getMailboxMessage();
				String messageId = idOperation.getMessageId();
				if (listIdMessageDeletedFromFolder.contains(messageId)) {
					if (mailboxMessageOperation.getOperationStatus().equalsIgnoreCase(OperationStatus.OPERATION_PLANNED.status())) {
						// mail rimossa con successo, aggiorno lo stato dell'operazione
						DeleteMessageBean bean = new DeleteMessageBean();
						bean.setDeleteok(true);
						bean.setPlanned(true);
						String xmlResultOperation = XmlUtil.objectToXml(bean);
						mailBusiness.saveOrUpdateOperation(idOperation.getIdMailboxoperation(), messageId, OperationStatus.OPERATION_FINISH, xmlResultOperation,
								idMailbox);
						// aggiorno lo status del messaggio a FORCE_OPERATION in modo che sia rielaborato dalla coda e si possa procedere con le successive
						// operazioni
						mailBusiness.updateStatusMimeMessage(messageId, idMailbox, MessageStatus.MESSAGE_FORCE_OPERATION, null);
						mLogger.info("Messaggio con id " + messageId + " della mailbox " + idMailbox + " impostato allo stato di "
								+ MessageStatus.MESSAGE_FORCE_OPERATION);
					}
				} else {

					Boolean checkMessage = false;

					try {
						mLogger.info("Ricerco il Messaggio con id " + messageId + " nella folder" + " della mailbox " + idMailbox);
						// verifico se il messaggio non è presente nella mailbox
						if (!mailReceiver.checkMessageInFolder(messageId, mailboxMessage.getMessageUid(), mailboxMessage.getMailboxUidValidity(),
								config.getFolder())) {
							mLogger.info("Messaggio con id " + messageId + " non presente nella folder" + " della mailbox " + idMailbox);
							// se non esiste allora probabilmente è già stato rimosso, imposto a finish l'operazione
							DeleteMessageBean bean = new DeleteMessageBean();
							bean.setDeleteok(true);
							// indico che non ho trovato il messaggio
							bean.setNotFound(true);
							String xmlResultOperation = XmlUtil.objectToXml(bean);
							mailBusiness.saveOrUpdateOperation(idOperation.getIdMailboxoperation(), messageId, OperationStatus.OPERATION_FORCED,
									xmlResultOperation, idMailbox);
							// aggiorno lo status del messaggio a FORCE_OPERATION in modo che sia rielaborato dalla coda e si possa procedere con le
							// successive
							// operazioni
							mailBusiness.updateStatusMimeMessage(messageId, idMailbox, MessageStatus.MESSAGE_FORCE_OPERATION, null);
							mLogger.info("Messaggio con id " + messageId + " della mailbox " + idMailbox + " impostato allo stato di "
									+ MessageStatus.MESSAGE_FORCE_OPERATION);
							checkMessage = true;

						}
					} catch (Exception e) {
						if (e instanceof FolderClosedException || e instanceof StoreClosedException) {
							if (numTryConnection < MAX_TRY_CONNECTION) {
								numTryConnection++;
							} else {
								// superato il numero di tentativi di connessione
								throw new MessagingException("Interrotta la connessione alla folder");
							}
						}
						mLogger.error("Errore nella verifica del messaggio nella folder", e);
					}

					// negli altri casi setto l'errore
					if (!checkMessage) {

						DeleteMessageBean bean = new DeleteMessageBean();
						bean.setDeleteok(false);
						String xmlResultOperation = XmlUtil.objectToXml(bean);
						mailBusiness.saveOrUpdateOperation(idOperation.getIdMailboxoperation(), messageId, OperationStatus.OPERATION_ERROR, xmlResultOperation,
								idMailbox);
						// aggiorno lo stato del messaggio a OPERATION_ERROR in modo da provare a ri-eseguire l'operazione
						mailBusiness.updateStatusMimeMessage(messageId, idMailbox, MessageStatus.MESSAGE_IN_ERROR_OPERATION,
								new Exception("Errore in fase di cancellazione"));
						mLogger.info("Messaggio con id " + messageId + " della mailbox " + idMailbox + " impostato allo stato di "
								+ MessageStatus.MESSAGE_IN_ERROR_OPERATION);
					}

				}
			}

		} catch (Exception e) {
			mLogger.error("Errore SynchroMail", e);
			throw e;
		} finally {
			// indico che è in corso una cancellazione per la mailbox
			try {
				DeleteScheduler.setThreadStateDeleteForMailbox(idMailbox, false);
			} catch (Exception e) {
				mLogger.error("Eccezione impostazione cancellazione in corso per il thread", e);
			}
		}
	}

	private MailConfiguratorBean getMailConfiguratorBean(Mailbox lMailbox) throws Exception {

		Properties properties = new Properties();

		Set<MailboxConfig> configs = lMailbox.getMailboxConfigs();
		for (MailboxConfig config : configs) {
			properties.put(config.getId().getConfigKey(), config.getConfigValue());
		}
		MailConfiguratorBean config = new MailConfiguratorBean();
		config.setMailconfig(properties);
		config.setMailboxid(lMailbox.getIdMailbox());
		config.setFolder(lMailbox.getFolder());

		// Recupero le configurazioni dell'account
		AccountConfigBean account = new AccountConfigBean();
		account.setAccountAddress(lMailbox.getMailboxAccount().getAccount());
		// configurazioni comuni a tutte le mailbox
		Properties propertiesAccount = Util.getJavaMailDefaultProperties();

		for (MailboxAccountConfig configaccount : lMailbox.getMailboxAccount().getMailboxAccountConfigs()) {
			if (StringUtils.isEmpty(configaccount.getConfigValue())) {
				throw new MailServerException("Valore non impostato per il parametro " + configaccount.getId().getConfigKey());
			}
			propertiesAccount.put(configaccount.getId().getConfigKey(), configaccount.getConfigValue());
		}
		account.setAccountconfig(propertiesAccount);
		config.setAccount(account);
		return config;
	}

	/**
	 * Metodo per recuperare da database la mailbox associata all'id
	 * 
	 * @param idMailbox
	 * @return
	 * @throws MessagingException
	 */

	private Mailbox getMailBox(String idMailbox) throws MessagingException {
		Session session = null;
		try {
			try {
				session = SubjectInitializer.getSession(null);
			} catch (Exception e) {
				throw new MessagingException(e.getMessage(), e);
			}
			Criteria criteria = session.createCriteria(Mailbox.class);
			criteria.add(Restrictions.idEq(idMailbox));
			Mailbox mailbox = (Mailbox) criteria.uniqueResult();
			// Inizializzo le confgiurazioni della mailbox
			Hibernate.initialize(mailbox.getMailboxConfigs());
			// Inizializzo il mailboxaccount
			Hibernate.initialize(mailbox.getMailboxAccount());
			// Inizializzo le configurazione
			Hibernate.initialize(mailbox.getMailboxAccount().getMailboxAccountConfigs());
			return mailbox;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * 
	 * @param idMailBox
	 * @param operationStatus
	 * @param limit
	 * @param delay
	 * @return
	 * @throws Exception
	 */

	public List<MailboxMessageOperation> getMailboxMessageOperationDelete(String idMailBox, String idAccount, String[] operationStatus, Integer limit,
			Integer delay) throws Exception {

		Session session = null;

		try {

			session = SubjectInitializer.getSession(idMailBox);

			Criteria criteria = session.createCriteria(MailboxMessageOperation.class, "mailboxMessageOperation").createAlias("mailboxMessage", "mailboxMessage")
					.createAlias("mailboxOperation", "mailboxOperation");
			criteria.add(Restrictions.eq("id.idMailbox", idMailBox));
			if (operationStatus != null && operationStatus.length > 0) {
				criteria.add(Restrictions.in("operationStatus", operationStatus));
			}
			if (limit != null && limit > 0) {
				// limito il numero di risultati
				criteria.setMaxResults(limit);
			}

			criteria.add(Restrictions.eq("mailboxOperation.operationName", "DeleteMessageOperation"));

			if (delay > 0) {
				Calendar now = Calendar.getInstance();
				now.add(Calendar.HOUR, -delay);

				// devo considerare il delay per effettuare la cancellazione
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TEmailMgo.class, "tEmailMgo");
				detachedCriteria.add(Property.forName("tEmailMgo.mailboxAccount.idAccount").eq(idAccount));
				detachedCriteria.add(Property.forName("tEmailMgo.messageId").eqProperty("mailboxMessage.id.messageId"));
				detachedCriteria.add(Property.forName("tEmailMgo.tsInvio").le(now.getTime()));
				criteria.add(Subqueries.exists(detachedCriteria.setProjection(Projections.property("idEmail"))));
			}
			return (List<MailboxMessageOperation>) criteria.list();

		} catch (Exception e) {
			mLogger.error("Eccezione nel metodo getMailboxMessageOperationByName", e);
			throw e;
		}

		finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}
	
	private  void gestioneDeletePop3(String urlFolder, String idMailbox, Mailbox lMailbox, final MailConfiguratorBean config, Integer limit, int hourDelay, boolean forceDelete, boolean forceExpunge, Boolean enableBackup, String folderBackup) throws Exception {
		try {
			mLogger.info("gestione cancellazione mail tramite protocollo pop3 per idMailbox: "+idMailbox);
			DeletePopMailReceiver mailReceiver = new DeletePopMailReceiver(urlFolder);
			mailReceiver.setIdMailbox(idMailbox);
			FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();
	
			// configurazioni cartella sul server
			mailReceiver.setConfigBean(config);
			// mailReceiver.setShouldMarkMessagesAsRead(false);
			mailReceiver.setShouldDeleteMessages(false);
	
			// configurazioni del bean
			mailReceiver.setIdAccount(lMailbox.getMailboxAccount().getIdAccount());
			mailReceiver.setForceDeleteMessageFinished(forceDelete); // abilitazione della pulizia dei messaggi anche non esiste la relativa operazione di
			mailReceiver.setLimit(limit); // numero massimo di messaggi da cancellare per schedulazione
			mailReceiver.setEnableBackup(enableBackup);
			mailReceiver.setFolderBackup(folderBackup);
			mailReceiver.setHourDelay(hourDelay);
			mailReceiver.setForceExpunge(forceExpunge);
	
			// Controllo se devo inserire l'authenticator
			mailReceiver.setJavaMailProperties(config.getAccount().getAccountconfig());
			mailReceiver.setProtocol(config.getAccount().getAccountconfig().getProperty(AccountConfigKey.POP3_STORE_PROTOCOL.keyname()));
			mailReceiver.setJavaMailAuthenticator(new Authenticator() {
	
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					String password = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname());
					try {
						password = CryptographyUtil.decryptionAccountPasswordWithAES(config.getAccount().getAccountconfig());
					} catch (Exception e) {
						mLogger.warn("Errore decrittazione password: " + e.getMessage(), e);
					}
					String username = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
					// in alcuni casi potrebbe essere specificato un username specifico per imap
//					if (config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_USERNAME.keyname()) != null) {
//						username = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.IMAP_USERNAME.keyname());
//					}
					return new PasswordAuthentication(username, password);
	
				}
			});
	
			List<MailboxMessageOperation> listaOperazioni = new ArrayList<MailboxMessageOperation>();
			if (!forceDelete) {
				// recupero le operazioni di cancellazione in stato "PLANNED", solo se non vanno cancellati i messaggi indipendentemente dalla cancellazione
				String[] listaStati = new String[] { OperationStatus.OPERATION_PLANNED.status() };
				listaOperazioni = getMailboxMessageOperationDelete(idMailbox, lMailbox.getMailboxAccount().getIdAccount(), listaStati, limit, hourDelay);
				List<MailboxMessage> listaMessaggi = new ArrayList<MailboxMessage>();
				for (Iterator<MailboxMessageOperation> iterator = listaOperazioni.iterator(); iterator.hasNext();) {
					MailboxMessageOperation mailboxMessageOperation = (MailboxMessageOperation) iterator.next();
					listaMessaggi.add(mailboxMessageOperation.getMailboxMessage());
				}
				mailReceiver.setListMessageToDeleteInDb(listaMessaggi);
			}
			// sincronizzo la mailbox in modo da richiamare il metodo sovrascritto searchNewMessages, che si collega alla mailbox e elimina i messaggi da
			// cancellare
			// registro che inizio la cancellazione schedulata dei messaggi
			try {
				mailBusiness.saveOrUpdateInfoMailbox(idMailbox, MailBoxInfoKey.MAILBOX_LAST_DELETE_SCHEDULED,
						new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
			} catch (Exception e1) {
				mLogger.warn("Errore nel salvataggio dell'informazione della cancellazione in database");
			}
	
			try {
				mailReceiver.receive();
			} catch (Exception exc) {
				mLogger.error("Errore SynchroMailReceiver", exc);
			}
			// anche in caso di errore eseguo le operazioni di chiusura
			countMessageFolder = mailReceiver.getCountMessageFolder();
			counter = mailReceiver.getCounterMessageVerified();
			// il metodo receive in automatico apre e chiude la connessione alla mailbox
			mLogger.info("Messaggi iniziali nella folder: " + countMessageFolder);
			mLogger.info("Messaggi verificati della folder: " + counter);
			listIdMessageDeletedFromFolder = mailReceiver.getListIdMessageDeletedFromFolder();
			if (enableBackup) {
				listIdMessageBackuped = mailReceiver.getListIdMessageBackup();
				if (listIdMessageBackuped == null) {
					listIdMessageBackuped = new ArrayList<String>();
				}
				mLogger.info("Messaggi copiati in backup " + listIdMessageBackuped.size() + " : " + listIdMessageBackuped);
			}
			if (listIdMessageDeletedFromFolder == null) {
				listIdMessageDeletedFromFolder = new ArrayList<String>();
			}
			mLogger.info("Messaggi cancellati " + listIdMessageDeletedFromFolder.size() + " : " + listIdMessageDeletedFromFolder);
			// aggiorno lo stato in database dei messaggi in stato PLANNED
	
			Integer numTryConnection = 0; // numero di tentativi di connessione alla folder prima di lanciare l'errore
	
			for (Iterator<MailboxMessageOperation> iterator = listaOperazioni.iterator(); iterator.hasNext();) {
	
				MailboxMessageOperation mailboxMessageOperation = (MailboxMessageOperation) iterator.next();
				MailboxMessageOperationId idOperation = mailboxMessageOperation.getId();
				MailboxMessage mailboxMessage = mailboxMessageOperation.getMailboxMessage();
				String messageId = idOperation.getMessageId();
				if (listIdMessageDeletedFromFolder.contains(messageId)) {
					if (mailboxMessageOperation.getOperationStatus().equalsIgnoreCase(OperationStatus.OPERATION_PLANNED.status())) {
						// mail rimossa con successo, aggiorno lo stato dell'operazione
						DeleteMessageBean bean = new DeleteMessageBean();
						bean.setDeleteok(true);
						bean.setPlanned(true);
						String xmlResultOperation = XmlUtil.objectToXml(bean);
						mailBusiness.saveOrUpdateOperation(idOperation.getIdMailboxoperation(), messageId, OperationStatus.OPERATION_FINISH, xmlResultOperation,
								idMailbox);
						// aggiorno lo status del messaggio a FORCE_OPERATION in modo che sia rielaborato dalla coda e si possa procedere con le successive
						// operazioni
						mailBusiness.updateStatusMimeMessage(messageId, idMailbox, MessageStatus.MESSAGE_FORCE_OPERATION, null);
						mLogger.info("Messaggio con id " + messageId + " della mailbox " + idMailbox + " impostato allo stato di "
								+ MessageStatus.MESSAGE_FORCE_OPERATION);
					}
				} else {
	
					Boolean checkMessage = false;
	
					try {
						mLogger.info("Ricerco il Messaggio con id " + messageId + " nella folder" + " della mailbox " + idMailbox);
						// verifico se il messaggio non è presente nella mailbox
						if (!mailReceiver.checkMessageInFolder(messageId, mailboxMessage.getMessageUid(), mailboxMessage.getMailboxUidValidity(),
								config.getFolder())) {
							mLogger.info("Messaggio con id " + messageId + " non presente nella folder" + " della mailbox " + idMailbox);
							// se non esiste allora probabilmente è già stato rimosso, imposto a finish l'operazione
							DeleteMessageBean bean = new DeleteMessageBean();
							bean.setDeleteok(true);
							// indico che non ho trovato il messaggio
							bean.setNotFound(true);
							String xmlResultOperation = XmlUtil.objectToXml(bean);
							mailBusiness.saveOrUpdateOperation(idOperation.getIdMailboxoperation(), messageId, OperationStatus.OPERATION_FORCED,
									xmlResultOperation, idMailbox);
							// aggiorno lo status del messaggio a FORCE_OPERATION in modo che sia rielaborato dalla coda e si possa procedere con le
							// successive
							// operazioni
							mailBusiness.updateStatusMimeMessage(messageId, idMailbox, MessageStatus.MESSAGE_FORCE_OPERATION, null);
							mLogger.info("Messaggio con id " + messageId + " della mailbox " + idMailbox + " impostato allo stato di "
									+ MessageStatus.MESSAGE_FORCE_OPERATION);
							checkMessage = true;
	
						}
					} catch (Exception e) {
						if (e instanceof FolderClosedException || e instanceof StoreClosedException) {
							if (numTryConnection < MAX_TRY_CONNECTION) {
								numTryConnection++;
							} else {
								// superato il numero di tentativi di connessione
								throw new MessagingException("Interrotta la connessione alla folder");
							}
						}
						mLogger.error("Errore nella verifica del messaggio nella folder", e);
					}
	
					// negli altri casi setto l'errore
					if (!checkMessage) {
	
						DeleteMessageBean bean = new DeleteMessageBean();
						bean.setDeleteok(false);
						String xmlResultOperation = XmlUtil.objectToXml(bean);
						mailBusiness.saveOrUpdateOperation(idOperation.getIdMailboxoperation(), messageId, OperationStatus.OPERATION_ERROR, xmlResultOperation,
								idMailbox);
						// aggiorno lo stato del messaggio a OPERATION_ERROR in modo da provare a ri-eseguire l'operazione
						mailBusiness.updateStatusMimeMessage(messageId, idMailbox, MessageStatus.MESSAGE_IN_ERROR_OPERATION,
								new Exception("Errore in fase di cancellazione"));
						mLogger.info("Messaggio con id " + messageId + " della mailbox " + idMailbox + " impostato allo stato di "
								+ MessageStatus.MESSAGE_IN_ERROR_OPERATION);
					}
	
				}
			}
	
		} catch (Exception e) {
			mLogger.error("Errore SynchroMail", e);
			throw e;
		} finally {
			// indico che è in corso una cancellazione per la mailbox
			try {
				DeleteScheduler.setThreadStateDeleteForMailbox(idMailbox, false);
			} catch (Exception e) {
				mLogger.error("Eccezione impostazione cancellazione in corso per il thread", e);
			}
		}
	}

	public List<String> getListIdMessageDeletedFromFolder() {
		return listIdMessageDeletedFromFolder;
	}

	public void setListIdMessageDeletedFromFolder(List<String> listIdMessageDeletedFromFolder) {
		this.listIdMessageDeletedFromFolder = listIdMessageDeletedFromFolder;
	}

	public Integer getCountMessageFolder() {
		return countMessageFolder;
	}

	public void setCountMessageFolder(Integer countMessageFolder) {
		this.countMessageFolder = countMessageFolder;
	}

	public Boolean getEnableBackup() {
		return enableBackup;
	}

	public void setEnableBackup(Boolean enableBackup) {
		this.enableBackup = enableBackup;
	}

	public String getFolderBackup() {
		return folderBackup;
	}

	public void setFolderBackup(String folderBackup) {
		this.folderBackup = folderBackup;
	}

	public List<String> getListIdMessageBackuped() {
		return listIdMessageBackuped;
	}

	public void setListIdMessageBackuped(List<String> listIdMessageBackuped) {
		this.listIdMessageBackuped = listIdMessageBackuped;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

}
