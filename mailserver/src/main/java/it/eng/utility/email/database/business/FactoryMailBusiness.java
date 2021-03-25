package it.eng.utility.email.database.business;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
import it.eng.aurigamailbusiness.database.mail.MailboxAccountConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxConfigId;
import it.eng.aurigamailbusiness.database.mail.MailboxInfo;
import it.eng.aurigamailbusiness.database.mail.MailboxInfoId;
import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageId;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperationId;
import it.eng.aurigamailbusiness.database.mail.MailboxOperation;
import it.eng.aurigamailbusiness.database.mail.MailboxOperationConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxOperationConfigId;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.core.business.HibernateUtil;
import it.eng.utility.database.MailboxConfigDefaultValue;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.bean.MailConfigurator;
import it.eng.utility.email.bean.MailUiConfigurator;
import it.eng.utility.email.operation.impl.archiveoperation.utils.AccountUtils;
import it.eng.utility.email.process.ProcessOperationFlow;
import it.eng.utility.email.reader.FileMimeMessage;
import it.eng.utility.email.reader.MailBoxStatus;
import it.eng.utility.email.reader.MessageStatus;
import it.eng.utility.email.reader.OperationStatus;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.reader.info.MailBoxInfoKey;
import it.eng.utility.email.storage.StorageCenter;
import it.eng.utility.email.util.statusMessage.StatusMessage;
import it.eng.utility.email.util.statusMessage.StatusMessage.TryNum;
import it.eng.utility.email.util.xml.XmlUtil;
import it.eng.utility.storageutil.StorageService;

/**
 * 
 * @author michele
 * 
 */
public class FactoryMailBusiness {

	static Logger log = LogManager.getLogger(FactoryMailBusiness.class);

	private FactoryMailBusiness() {
		// TODO Auto-generated constructor stub
	}

	public static FactoryMailBusiness getInstance() {
		return new FactoryMailBusiness();
	}

	/**
	 * Cancella il messaggio dalla mailbox
	 */
	public void deleteMessage(String idmailbox, String messageid) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(idmailbox);
			transaction = session.beginTransaction();

			// Recupero le operazioni e le cancello
			MailboxMessageId id = new MailboxMessageId(messageid, idmailbox);

			MailboxMessage message = (MailboxMessage) session.get(MailboxMessage.class, id);

			if (message != null) {
				Set<MailboxMessageOperation> operations = message.getMailboxMessageOperations();
				for (MailboxMessageOperation operation : operations) {
					session.delete(operation);
				}
			}
			session.delete(message);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			log.error("Errore nella cancellazione del messaggio " + messageid + " dalla mailbox " + idmailbox, e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Ritorna tutte le informazioni dell'account in input
	 * 
	 * @return
	 */
	public MailboxAccount getMailboxAccount(String account) {
		Session session = null;
		MailboxAccount mailboxaccount = null;
		try {
			session = SubjectInitializer.getSession(null);
			Criteria criteria = session.createCriteria(MailboxAccount.class).add(Restrictions.eq("account", account).ignoreCase());
			mailboxaccount = (MailboxAccount) criteria.uniqueResult();
			if (mailboxaccount == null) {
				throw new Exception("Nessun Account trovato " + account);
			}
			// Inizializzo le configurazioni
			Hibernate.initialize(mailboxaccount.getMailboxAccountConfigs());

			// Inizializzo le mailbox
			Hibernate.initialize(mailboxaccount.getMailboxes());

		} catch (Throwable e) {
			log.error("Errore recupero configurazioni dell'account " + account, e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return mailboxaccount;
	}

	/**
	 * Recupera le configurazioni dell'account per l'invio tramite SMTP delle mail
	 * 
	 * @param idMailbox
	 * @param configKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Properties getAccountSendProperties(String idMailbox, String account) {
		Session session = null;
		Properties ret = new Properties();
		try {
			session = SubjectInitializer.getSession(idMailbox);

			Criteria criteria = session.createCriteria(MailboxConfig.class).add(Restrictions.like("id.configKey", account, MatchMode.START))
					.add(Restrictions.eq("id.idMailbox", idMailbox));
			List<MailboxConfig> propertieslist = criteria.list();
			if (propertieslist != null) {
				ret = new Properties();
				for (MailboxConfig properties : propertieslist) {
					if (StringUtils.isEmpty(properties.getConfigValue())) {
						throw new AurigaMailBusinessException("Valore non impostato per il parametro " + properties.getId().getConfigKey());
					}
					ret.put(StringUtils.remove(properties.getId().getConfigKey(), account + "."), properties.getConfigValue());
				}
			}
		} catch (Throwable e) {
			log.error("Errore recupero configurazioni dell'account " + account + " per l'invio tramite SMTP delle mail della mailbox " + idMailbox, e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return ret;
	}

	/**
	 * Recupera tutte le configurazioni della mailbox dalla tabella MAILBOX_CONFIG
	 * 
	 * @param idMailbox
	 * @param configKey
	 * @return
	 */

	public Properties getMailBoxConfigParameter(String idMailbox) {

		Session session = null;
		Properties ret = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);

			Mailbox mailbox = (Mailbox) session.get(Mailbox.class, idMailbox);

			Set<MailboxConfig> configs = mailbox.getMailboxConfigs();
			ret = new Properties();
			for (MailboxConfig config : configs) {
				if (StringUtils.isEmpty(config.getConfigValue())) {
					throw new AurigaMailBusinessException("Valore non impostato per il parametro " + config.getId().getConfigKey());
				}
				ret.put(config.getId().getConfigKey(), config.getConfigValue());
			}
		} catch (Throwable e) {
			log.error("Errore recupero configurazioni della mailbox " + idMailbox, e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return ret;
	}

	/**
	 * Recupera tutte le configurazioni della mailbox dalla tabella MAILBOX_INFO
	 * 
	 * @param idMailbox
	 * @param configKey
	 * @return
	 */

	public Properties getMailBoxInfo(String idMailbox) {

		Session session = null;
		Properties ret = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);

			Mailbox mailbox = (Mailbox) session.get(Mailbox.class, idMailbox);

			Set<MailboxInfo> infos = mailbox.getMailboxInfos();
			ret = new Properties();
			for (MailboxInfo info : infos) {
				if (!StringUtils.isEmpty(info.getInfoValue())) {
					ret.put(info.getId().getInfoKey(), info.getInfoValue());
				}
			}
		} catch (Throwable e) {
			log.error("Errore recupero informazioni della mailbox " + idMailbox, e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return ret;
	}

	/**
	 * Inizializza i messaggi della mailbox che hanno stato in elaborazione; Cancella i messaggi della mailbox che hanno stato di discharged e discharged error
	 * in modo che vengano riscaricati
	 * 
	 * @param idMailbox
	 * @param clearDischarged
	 */

	@SuppressWarnings("unchecked")
	public void initializeMailboxMessage(String idMailbox, Boolean clearDischarged) {
		Session session = null;
		Transaction transaction = null;
		Boolean resetUID = false;

		try {
			log.debug("Inizializzazione mailbox " + idMailbox);
			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();
			// Recupero tutte le mail in stato progress e discharged le metto in
			// stato in stato force operation
			// Recupero anche le email in stato discharged error

			List<String> status = new ArrayList<String>();
			status.add(MessageStatus.MESSAGE_OPERATION_IN_PROGRESS.status());
			status.add(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status());

			if (clearDischarged) {
				status.add(MessageStatus.MESSAGE_DISCHARGED.status());
			}

			List<MailboxMessage> listmail = session.createCriteria(MailboxMessage.class).add(Restrictions.eq("id.idMailbox", idMailbox))
					.add(Restrictions.in("status", status)).list();

			for (MailboxMessage mail : listmail) {
				if (mail.getStatus().equalsIgnoreCase(MessageStatus.MESSAGE_OPERATION_IN_PROGRESS.status())) {
					mail.setStatus(MessageStatus.MESSAGE_FORCE_OPERATION.status());
					session.update(mail);
					session.flush();
				} else if (mail.getStatus().equalsIgnoreCase(MessageStatus.MESSAGE_DISCHARGED.status())) {
					// cancello le mail in stato discharged e il relativo file,
					// la mail sarà poi riprocessata e quindi il file sarà
					// riscaricato
					// solo se non ci sono messaggi in coda di scaricamento, in
					// questo caso prima li devo scaricare tutti
					String url = mail.getUrlMime();
					Set<MailboxMessageOperation> operations = mail.getMailboxMessageOperations();
					for (MailboxMessageOperation operation : operations) {
						session.delete(operation);
					}
					// aggiungo l'id ai messaggi da ri-processare
					session.delete(mail);
					session.flush();
					// resetto l'uid salvato, visto che devo riscaricare
					// messaggi precedentemente scaricati
					try {
						// Risolvo lo storage della casella
						StorageCenter storageCenter = new StorageCenter();
						storageCenter.delete(idMailbox, url);
						session.flush();
					} catch (Exception e) {
						// Errore di cancellazione, vado avanti comunque
						log.error("Errore nella cancellazione del file associato alla mail in stato discharged: " + e.getMessage(), e);
					}
					resetUID = true;
				} else if (mail.getStatus().equalsIgnoreCase(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status()) && mail.getUrlMime() == null) {
					// cancello le mail in stato di discharged error e senza
					// URL Mime, la mail sarà poi riprocessata
					// nessuna informazioni sull'eventuale file associato,
					Set<MailboxMessageOperation> operations = mail.getMailboxMessageOperations();
					for (MailboxMessageOperation operation : operations) {
						session.delete(operation);
					}
					session.delete(mail);
					session.flush();
					// resetto l'uid salvato, visto che devo riscaricare
					// messaggi precedentemente scaricati
					resetUID = true;
				}
			}

			// Recupero poi i messaggi in error operation o operation in
			// progress
			// senza nessuna operazione associata, è un caso che può capitare
			// con il riavvio dell'applicativo
			// gestisco anche questi messaggi altrimenti non sarebbero più
			// riprocessati

			Criteria criteriaMessage = session.createCriteria(MailboxMessage.class, "message")
					.createAlias("mailboxMessageOperations", "operation", CriteriaSpecification.LEFT_JOIN).add(Restrictions.eq("id.idMailbox", idMailbox))
					.add(Restrictions.in("status",
							new String[] { MessageStatus.MESSAGE_OPERATION_IN_PROGRESS.status(), MessageStatus.MESSAGE_IN_ERROR_OPERATION.status() }))
					.add(Restrictions.isEmpty("mailboxMessageOperations"));

			List<MailboxMessage> listmailInError = criteriaMessage.list();

			for (MailboxMessage mail : listmailInError) {
				if (mail.getUrlMime() != null) {
					// inserisco lo stato di discharged error se l'URI è
					// presente, in modo che sia correttamente ri-processata
					mail.setStatus(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status());
					session.update(mail);
					session.flush();
				} else {
					// cancello la mail per riscaricarla
					session.delete(mail);
					session.flush();
					// resetto l'uid salvato, visto che devo riscaricare
					// messaggi precedentemente scaricati
					resetUID = true;
				}
			}

			if (resetUID) {
				resetUIDMailboxInSession(idMailbox, session);
			}

			session.flush();
			transaction.commit();
		} catch (

		Throwable e) {
			log.error("Errore inizializzazione della mailbox " + idMailbox, e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

	}

	public MessageStatus getMessageStatus(String idMailbox, String messageId) throws Exception {
		Session session = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);

			MailboxMessageId mailboxMessageId = new MailboxMessageId(messageId, idMailbox);
			MailboxMessage message = (MailboxMessage) session.get(MailboxMessage.class, mailboxMessageId);

			if (message == null) {
				throw new Exception("Messaggio inesistente!");
			}

			return MessageStatus.getForValue(message.getStatus());

		} catch (Throwable e) {
			log.error("Errore get status message " + messageId + " della mailbox " + idMailbox, e);
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

	}

	/**
	 * Inizializza i messaggi della mailbox che hanno stato in elaborazione;
	 * 
	 * @param idMailbox
	 * @throws Throwable
	 */
	public void forceToMessageElaborate(String idMailbox, String messageId) throws Throwable {
		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();
			MailboxMessageId mailboxmessageid = new MailboxMessageId(messageId, idMailbox);
			MailboxMessage message = (MailboxMessage) session.get(MailboxMessage.class, mailboxmessageid);
			if (message != null) {
				message.setStatus(MessageStatus.MESSAGE_FORCE_OPERATION.status());

				// MailboxConfigId confid = new
				// MailboxConfigId(MailBoxConfigKey.MAILBOX_DIRECTORY.keyname(),
				// idmailbox);
				// MailboxConfig config =
				// (MailboxConfig)session.get(MailboxConfig.class, confid);
				//
				// String dirstring = config.getConfigValue();

				// Porto a 0 tutti i try num delle operazioni se esistono
				Set<MailboxMessageOperation> operations = message.getMailboxMessageOperations();
				if (operations != null) {
					for (MailboxMessageOperation operation : operations) {
						operation.setOperationTryNum((short) 0);
						session.update(operation);
					}
				}

				// Prendo il blob e lo salvo su file system
				byte[] data = new byte[] {};// message.getDatamail();

				// Prendo lo storage globale
				StorageCenter lStorageCenter = new StorageCenter();
				StorageService lStorageService = lStorageCenter.getGlobalStorage(idMailbox);
				ByteArrayInputStream lByteArrayInputStream = new ByteArrayInputStream(data);
				String name = lStorageService.storeStream(lByteArrayInputStream);

				// String name = UUID.randomUUID().toString();
				// File toprocess = new File(dirstring,name);
				// FileUtils.writeByteArrayToFile(toprocess, data);

				message.setDatamail(null);
				message.setUrlMime(name);

			} else {
				throw new Exception("Il MessageID " + messageId + " non è presente nella mailbox " + idMailbox);
			}
			session.update(message);

			transaction.commit();
		} catch (Throwable e) {
			log.error("Errore update mailbox message", e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Recupera una configurazione della mailbox dalla tabella MAILBOX_CONFIG
	 * 
	 * @param idMailbox
	 * @param configKey
	 * @return
	 */

	public String getMailBoxConfigParameter(String idMailbox, MailBoxConfigKey configKey) {

		Session session = null;
		String ret = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);

			MailboxConfigId id = new MailboxConfigId(configKey.keyname(), idMailbox);
			MailboxConfig config = (MailboxConfig) session.get(MailboxConfig.class, id);
			if (config != null) {
				ret = config.getConfigValue();
			}

		} catch (Throwable e) {
			log.error("Errore recupero configurazioni della mailbox", e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return ret;
	}

	/**
	 * Recupera una informazione della mailbox dalla tabella MAILBOX_INFO
	 * 
	 * @param idMailbox
	 * @param infoKey
	 * @return
	 */

	public String getMailBoxInfo(String idMailbox, MailBoxInfoKey infoKey) {

		Session session = null;
		String ret = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);

			MailboxInfoId id = new MailboxInfoId(infoKey.keyname(), idMailbox);
			MailboxInfo config = (MailboxInfo) session.get(MailboxInfo.class, id);
			if (config != null) {
				ret = config.getInfoValue();
			}

		} catch (Throwable e) {
			log.error("Errore recupero informazioni della mailbox", e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return ret;
	}

	/**
	 * Recupera il messaggio
	 * 
	 * @param messageId
	 * @return
	 */
	public MailboxMessage getMailBoxMessage(String messageId, String idMailbox) throws Exception {
		Session session = null;
		MailboxMessage message = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);
			MailboxMessageId id = new MailboxMessageId(messageId, idMailbox);
			message = (MailboxMessage) session.get(MailboxMessage.class, id);
		} catch (Exception e) {
			log.error("Errore recupero messaggio dalla mailbox", e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return message;
	}

	/**
	 * Metodo che restituisce una lista delle operazioni di una mailbox con il nome in input ed eventualmente un insieme di stati
	 * 
	 * @param idMailBox
	 *            id della mailbox
	 * @param operationName
	 *            nome dell'operazione
	 * @param operationStatus
	 *            stati dell'operazione
	 * @param limit
	 *            numero massimo di operazioni da recuperare
	 * @return
	 * @throws MessagingException
	 */

	@SuppressWarnings("unchecked")
	public List<MailboxMessageOperation> getMailboxMessageOperationByName(String idMailBox, String operationName, String[] operationStatus, Integer limit)
			throws Exception {
		Session session = null;
		try {
			session = SubjectInitializer.getSession(idMailBox);
			Criteria criteria = session.createCriteria(MailboxMessageOperation.class).createAlias("mailboxOperation", "mailboxOperation");
			criteria.add(Restrictions.eq("id.idMailbox", idMailBox));
			if (operationStatus != null && operationStatus.length > 0) {
				criteria.add(Restrictions.in("operationStatus", operationStatus));
			}
			if (limit != null && limit > 0) {
				// limito il numero di risultati
				criteria.setMaxResults(limit);
			}
			criteria.add(Restrictions.eq("mailboxOperation.operationName", operationName));
			return (List<MailboxMessageOperation>) criteria.list();

		} catch (Exception e) {
			log.error("Eccezione nel metodo getMailboxMessageOperationByName", e);
			throw e;
		}

		finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Metodo che restituisce la lista di messaggi che hanno un'operazione di cancellazione pianificata
	 * 
	 * @param idMailBox
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public List<MailboxMessage> getListaMessaggiDaCancellare(String idMailBox) throws Exception {

		Session session = null;
		try {

			session = SubjectInitializer.getSession(idMailBox);
			Criteria criteria = session.createCriteria(MailboxMessageOperation.class).createAlias("mailboxOperation", "mailboxOperation");
			criteria.add(Restrictions.eq("id.idMailbox", idMailBox));
			criteria.add(Restrictions.eq("operationStatus", OperationStatus.OPERATION_PLANNED.status()));
			criteria.add(Restrictions.eq("mailboxOperation.operationName", "DeleteMessageOperation"));
			return (List<MailboxMessage>) criteria.setProjection(Projections.property("mailboxMessage")).list();

		} catch (Exception e) {
			log.error("Eccezione nel metodo getListaMessaggiDaCancellare", e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

	}

	/**
	 * Metodo che verifica se un messaggio possa essere rimosso dalla mailbox verificando se è presente l'operazione di cancellazione già completata (vecchia
	 * versione del deleteScheduler) o pianificata (nuova versione del deleteScheduler) o forzata (significa che il messaggio inizialmente non era stato
	 * trovato)
	 * 
	 * @param messageId
	 * @param idMailBox
	 * @return
	 * @throws MessagingException
	 */

	public Boolean canBeDeleted(String messageId, String idMailBox) throws Exception {

		Session session = null;
		Boolean result = false;

		try {

			session = SubjectInitializer.getSession(idMailBox);
			Criteria criteria = session.createCriteria(MailboxMessageOperation.class).createAlias("mailboxOperation", "mailboxOperation");
			criteria.add(Restrictions.eq("id.messageId", messageId));
			criteria.add(Restrictions.eq("id.idMailbox", idMailBox));
			// operazioni con cancellazione completata o pianificata
			criteria.add(Restrictions.in("operationStatus", new String[] { OperationStatus.OPERATION_PLANNED.status(),
					OperationStatus.OPERATION_FINISH.status(), OperationStatus.OPERATION_FORCED.status() }));
			criteria.add(Restrictions.eq("mailboxOperation.operationName", "DeleteMessageOperation"));
			String id = (String) criteria.setProjection(Projections.property("messageId")).uniqueResult();

			result = id != null;

		} catch (Exception e) {
			log.error("Eccezione nel metodo canBeDeleted", e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		return result;
	}

	/**
	 * Metodo che verifica se un messaggio possa essere rimosso dalla mailbox verificando se è presente l'operazione di cancellazione già completata (vecchia
	 * versione del deleteScheduler) o forzata (il messaggio non era più presente nella mailbox) nell'oggetto MailboxMessage in input
	 * 
	 * @param mailboxMessage
	 * @return
	 * @throws Exception
	 */

	public Boolean canBeDeletedMailboxMessage(MailboxMessage mailboxMessage) throws Exception {

		Boolean result = false;
		Boolean existDeleteOperation = false;
		Boolean archiveValideDate = false;

		try {

			Set<MailboxMessageOperation> operations = mailboxMessage.getMailboxMessageOperations();
			for (Iterator<MailboxMessageOperation> iterator = operations.iterator(); iterator.hasNext() && !result;) {
				MailboxMessageOperation mailboxMessageOperation = (MailboxMessageOperation) iterator.next();
				if ((mailboxMessageOperation.getMailboxOperation().getOperationName().equalsIgnoreCase("DeleteMessageOperation")
						&& (mailboxMessageOperation.getOperationStatus().equals(OperationStatus.OPERATION_FINISH.status())
								|| mailboxMessageOperation.getOperationStatus().equals(OperationStatus.OPERATION_FORCED.status())))) {
					existDeleteOperation = true;
				} else if ((!archiveValideDate && mailboxMessageOperation.getMailboxOperation().getOperationName().equals("MessageArchiveOperation")
						)) {
					archiveValideDate = true;
				}
				result = existDeleteOperation && archiveValideDate;
			}

		} catch (Exception e) {
			log.error("Eccezione nel metodo canBeDeletedMailboxMessage", e);
			throw e;
		}

		return result;
	}

	/**
	 * Recupera il messaggio e relative operazioni
	 * 
	 * @param messageid
	 * @return
	 */
	public MailboxMessage getMailBoxMessageWithOperations(String messageid, String idMailbox) {
		Session session = null;
		MailboxMessage message = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);
			MailboxMessageId id = new MailboxMessageId(messageid, idMailbox);
			message = (MailboxMessage) session.get(MailboxMessage.class, id);
			if (message != null) {
				Set<MailboxMessageOperation> operations = message.getMailboxMessageOperations();
				// inizializzo le operazioni del messaggio
				Hibernate.initialize(operations);
				for (Iterator<MailboxMessageOperation> iterator = operations.iterator(); iterator.hasNext();) {
					MailboxMessageOperation mailboxMessageOperation = (MailboxMessageOperation) iterator.next();
					Hibernate.initialize(mailboxMessageOperation.getMailboxOperation());
				}
			}

		} catch (Throwable e) {
			log.error("Errore recupero messaggio e operazioni", e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return message;
	}

	/**
	 * Recupero le configurazioni dell'operazione
	 * 
	 * @param idMailbox
	 * @return
	 */
	public Properties getMessageConfiguration(String idoperation) {
		Session session = null;
		Properties properties = new Properties();
		try {
			session = SubjectInitializer.getSession(null);

			MailboxOperation mailboxoperation = (MailboxOperation) session.get(MailboxOperation.class, idoperation);

			Set<MailboxOperationConfig> configurations = mailboxoperation.getMailboxOperationConfigs();

			if (configurations != null) {
				for (MailboxOperationConfig configuration : configurations) {
					if (StringUtils.isEmpty(configuration.getConfigValue())) {
						throw new AurigaMailBusinessException("Valore non impostato per il parametro " + configuration.getId().getConfigKey());
					}
					properties.put(configuration.getId().getConfigKey(), configuration.getConfigValue());

				}
			}
		} catch (Throwable e) {
			log.error("Errore recupero configurazioni del messaggio", e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return properties;
	}

	/**
	 * Operation Status
	 * 
	 * @param idmailbox
	 * @param messageid
	 * @return
	 */
	public boolean updateTryNumStateOperationAndExecuteControl(String operationid, String messageid, String idmailbox, Short maxtrynum) {

		Session session = null;
		Transaction transaction = null;
		boolean elaborate = false;

		try {
			session = SubjectInitializer.getSession(idmailbox);
			transaction = session.beginTransaction();

			MailboxMessageOperationId messageOperationId = new MailboxMessageOperationId(operationid, messageid, idmailbox);
			MailboxMessageOperation messageOperation = (MailboxMessageOperation) session.get(MailboxMessageOperation.class, messageOperationId);
			if (messageOperation == null) {
				elaborate = true;
			} else {
				// Se il maxtrynum non è nullo (ovvero uguale a -1, allora provo
				// all'infinito
				if (maxtrynum != -1 && messageOperation.getOperationTryNum() + 1 > maxtrynum) {
					// Blocco il processo
					MailboxMessage mailboxmessage = messageOperation.getMailboxMessage();
					mailboxmessage.setStatus(MessageStatus.MESSAGE_OPERATION_LOCK.status());
					session.update(mailboxmessage);
				} else {
					// Aggiorno il try num e lancio il processo
					short trynum = messageOperation.getOperationTryNum();
					messageOperation.setOperationTryNum((short) (trynum + 1));
					messageOperation.setOperationStatus(OperationStatus.OPERATION_IN_PROGRESS.status());
					session.update(messageOperation);
					elaborate = true;
				}
			}

			transaction.commit();

		} catch (Exception e) {
			log.error("Errore controllo trynum", e);
			elaborate = false;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return elaborate;
	}

	/**
	 * Restituisce il numero di tentativi effettuati per l'operazione, 1 come valore di default
	 * 
	 * @param operationid
	 * @param messageid
	 * @param idmailbox
	 * @return
	 */

	public Short getCurrentTryNumOperation(String operationid, String messageid, String idmailbox) {

		Short result = 1;
		Session session = null;

		try {
			session = SubjectInitializer.getSession(idmailbox);

			MailboxMessageOperationId messageOperationId = new MailboxMessageOperationId(operationid, messageid, idmailbox);
			MailboxMessageOperation messageOperation = (MailboxMessageOperation) session.get(MailboxMessageOperation.class, messageOperationId);
			if (messageOperation != null) {
				result = messageOperation.getOperationTryNum();
			}
		} catch (Exception e) {
			log.error("Errore controllo trynum", e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		return result;

	}

	/**
	 * Operation Status
	 * 
	 * @param idmailbox
	 * @param messageid
	 * @return
	 */
	public MailboxMessageOperation getMessageOperation(String operationid, String messageid, String idmailbox) {
		Session session = null;
		try {
			session = SubjectInitializer.getSession(idmailbox);

			MailboxMessageOperationId messageoperationid = new MailboxMessageOperationId(operationid, messageid, idmailbox);

			MailboxMessageOperation messageoperation = (MailboxMessageOperation) session.get(MailboxMessageOperation.class, messageoperationid);

			return messageoperation;
		} catch (Throwable e) {
			log.error("Errore recupero Message Operation", e);
			return null;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	public String getMessageOperationConfigValue(String operationid, String key) {
		Session session = null;
		try {
			session = SubjectInitializer.getSession(null);

			MailboxOperationConfigId messageoperationid = new MailboxOperationConfigId(key, operationid);

			MailboxOperationConfig messageoperation = (MailboxOperationConfig) session.get(MailboxOperationConfig.class, messageoperationid);

			if (messageoperation == null) {
				return null;
			} else {
				return messageoperation.getConfigValue();
			}

		} catch (Throwable e) {
			log.error("Errore recupero Message Operation", e);
			return null;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Ritorna tutte le configurazione della mailbox
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Mailbox> getAllMailBox() throws Exception {
		Session session = null;
		try {
			session = SubjectInitializer.getSession(null);
			return session.createCriteria(Mailbox.class).list();
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Metodo che restituisce la lista di mailbox attive e relative configurazioni
	 * 
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public List<Mailbox> getAllActiveMailBox() throws Exception {
		Session session = null;
		try {
			session = SubjectInitializer.getSession(null);
			Criteria criteria = session.createCriteria(Mailbox.class);
			criteria.add(Restrictions.eq("status", MailBoxStatus.MAILBOX_ACTIVE.status()));
			if (MailConfigurator.getCaselle() != null && MailConfigurator.getCaselle().size() > 0) {
				criteria.add(Restrictions.in("idMailbox", MailConfigurator.getCaselle()));
			}

			List<Mailbox> lista = criteria.addOrder(Order.asc("name")).list();
			for (Mailbox mailbox : lista) {
				// Inizializzo le configurazioni della mailbox
				Hibernate.initialize(mailbox.getMailboxConfigs());

				// Inizializzo il mailboxaccount
				Hibernate.initialize(mailbox.getMailboxAccount());

				// Inizializzo le configurazione
				Hibernate.initialize(mailbox.getMailboxAccount().getMailboxAccountConfigs());
			}

			return lista;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Metodo che restituisce la lista di mailbox attive e relative configurazioni, aventi mailconnect id configurato per il server
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Mailbox> getAllActiveMailBoxForConnectId() throws Exception {

		Session session = null;

		try {
			session = SubjectInitializer.getSession(null);
			Criteria criteria = session.createCriteria(Mailbox.class, "mbox");
			criteria.add(Restrictions.eq("status", MailBoxStatus.MAILBOX_ACTIVE.status()));
			if (MailConfigurator.getCaselle() != null && MailConfigurator.getCaselle().size() > 0) {
				criteria.add(Restrictions.in("idMailbox", MailConfigurator.getCaselle()));
			}

			// la mailbox deve essere associata al server
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MailboxConfig.class, "config")
					.add(Restrictions.eq("config.id.configKey", MailBoxConfigKey.MAILBOX_MAILCONNECT_ID.keyname()))
					.add(Restrictions.eq("config.configValue", MailUiConfigurator.getMailConnectId()))
					.add(Property.forName("config.id.idMailbox").eqProperty("mbox.idMailbox"));

			criteria.add(Subqueries.exists(detachedCriteria.setProjection(Projections.property("config.id.configKey"))));

			List<Mailbox> lista = criteria.addOrder(Order.asc("name")).list();

			for (Mailbox mailbox : lista) {
				// Inizializzo le configurazioni della mailbox
				Hibernate.initialize(mailbox.getMailboxConfigs());

				// Inizializzo il mailboxaccount
				Hibernate.initialize(mailbox.getMailboxAccount());

				// Inizializzo le configurazione
				Hibernate.initialize(mailbox.getMailboxAccount().getMailboxAccountConfigs());
			}

			return lista;

		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Metodo che restituisce la lista di mailbox e relative configurazioni, aventi mailconnect id configurato per il server
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Mailbox> getAllMailBoxForConnectId() throws Exception {

		Session session = null;

		try {
			session = SubjectInitializer.getSession(null);
			Criteria criteria = session.createCriteria(Mailbox.class, "mbox");
			if (MailConfigurator.getCaselle() != null && !MailConfigurator.getCaselle().isEmpty()) {
				criteria.add(Restrictions.in("idMailbox", MailConfigurator.getCaselle()));
			}

			// la mailbox deve essere associata al server
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MailboxConfig.class, "config")
					.add(Restrictions.eq("config.id.configKey", MailBoxConfigKey.MAILBOX_MAILCONNECT_ID.keyname()))
					.add(Restrictions.eq("config.configValue", MailUiConfigurator.getMailConnectId()))
					.add(Property.forName("config.id.idMailbox").eqProperty("mbox.idMailbox"));

			criteria.add(Subqueries.exists(detachedCriteria.setProjection(Projections.property("config.id.configKey"))));

			List<Mailbox> lista = criteria.addOrder(Order.asc("name")).list();

			for (Mailbox mailbox : lista) {
				// Inizializzo le configurazioni della mailbox
				Hibernate.initialize(mailbox.getMailboxConfigs());

				// Inizializzo il mailboxaccount
				Hibernate.initialize(mailbox.getMailboxAccount());

				// Inizializzo le configurazione
				Hibernate.initialize(mailbox.getMailboxAccount().getMailboxAccountConfigs());
			}

			return lista;

		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Metodo che restituisce una mailbox, se attiva e configurata per il server, e relative configurazioni
	 * 
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public Mailbox getMailBoxIfActive(String idMailbox) throws Exception {

		Session session = null;
		Mailbox result = null;

		try {
			session = SubjectInitializer.getSession(null);
			Criteria criteria = session.createCriteria(Mailbox.class, "mbox");
			criteria.add(Restrictions.eq("status", MailBoxStatus.MAILBOX_ACTIVE.status()));
			criteria.add(Restrictions.eq("idMailbox", idMailbox));

			// la mailbox deve essere associata al server
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MailboxConfig.class, "config")
					.add(Restrictions.eq("config.id.configKey", MailBoxConfigKey.MAILBOX_MAILCONNECT_ID.keyname()))
					.add(Restrictions.eq("config.configValue", MailUiConfigurator.getMailConnectId()))
					.add(Property.forName("config.id.idMailbox").eqProperty("mbox.idMailbox"));

			criteria.add(Subqueries.exists(detachedCriteria.setProjection(Projections.property("config.id.configKey"))));

			List<Mailbox> lista = criteria.list();

			if (lista != null && !lista.isEmpty()) {

				result = lista.get(0);

				// Inizializzo le configurazioni della mailbox
				Hibernate.initialize(result.getMailboxConfigs());

				// Inizializzo il mailboxaccount
				Hibernate.initialize(result.getMailboxAccount());

				// Inizializzo le configurazione
				Hibernate.initialize(result.getMailboxAccount().getMailboxAccountConfigs());
			}

			return result;

		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Controlla se un MimeMessage è già stato scaricato, le mail in stato Discharged error sono escluse e quindi saranno riscaricate
	 * 
	 * @param messageid
	 * @param idmailbox
	 * @return
	 * @throws Exception
	 */

	public boolean isMimeMessageElaborate(String messageid, String idmailbox) throws Exception {
		return this.isMimeMessageElaborate(messageid, idmailbox, false);
	}

	/**
	 * Controlla se un MimeMessage è già stato scaricato per la casella, specificando se includere o meno le mail in Discharged error salvata in database
	 * Restituisce false se è un nuovo messaggio o è un messaggio da riscaricare
	 * 
	 * @param messageId
	 * @param idMailbox
	 * @param uid
	 * @param includiDischargedErrorSalvate:
	 *            true per includere le mail in discharged error, false per escluderle e quindi riscaricarle
	 * @return
	 * @throws Exception
	 */

	public boolean isMimeMessageElaborate(String messageId, String idMailbox, Boolean includiDischargedErrorSalvate) throws Exception {
		Session session = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);
			if (messageId == null) {
				return true;
			}

			MailboxMessageId messageIdMailbox = new MailboxMessageId(messageId, idMailbox);
			MailboxMessage messageMailbox = (MailboxMessage) session.get(MailboxMessage.class, messageIdMailbox);
			if (messageMailbox == null) {
				// nuovo messaggio
				return false;
			} else {
				// messaggio già presente in database
				// Se in error discharged lo riscarico sole se le mail in
				// discharged error sono da escludere
				if (messageMailbox.getStatus().equalsIgnoreCase(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status()) && !includiDischargedErrorSalvate) {
					return false;
				} else {
					return true;
				}
			}
		} catch (Exception exc) {
			log.error("Errore nel metodo isMimeMessageElaborate", exc);
			throw exc;
		} finally {
			if (session != null) {
				try {
					session.close();
					session = null;
				} catch (Exception e) {
					log.error("Errore nel rilascio della sessione", e);
				}
			}
		}
	}

	/**
	 * Inserisce un nuovo mimemessage, verifico comunque che non sia presente in database, in questo caso si elimina prima di procedere
	 * 
	 * @param mime
	 * @throws Throwable
	 */
	public void addMimeMessage(String msgId, MessageStatus status, String idMailbox, Long uid, Long uidValidity, String filename, Throwable error)
			throws Throwable {

		MailboxMessage mailboxmessage = getMailBoxMessage(msgId, idMailbox);
		if (mailboxmessage != null) {
			throw new Exception("Messaggio con id " + msgId + " già presente in database per la mailbox " + idMailbox);
		}
		addMimeMessage(msgId, status, idMailbox, uid, uidValidity, filename, error, null);
	}

	/**
	 * Inserisce un nuovo mimemessage
	 * 
	 * @param mime
	 * @throws Throwable
	 */
	public void addMimeMessage(String msgId, MessageStatus status, String idMailbox, Long uid, Long uidValidity, String filename, Throwable error,
			MimeMessage datamail) throws Throwable {
		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();

			Mailbox mailbox = (Mailbox) session.get(Mailbox.class, idMailbox);

			// Inserisco il messaggio
			final MailboxMessage message = new MailboxMessage();
			message.setMailbox(mailbox);
			message.setUrlMime(filename);
			MailboxMessageId messageid = new MailboxMessageId(msgId, idMailbox);
			message.setId(messageid);
			message.setStatus(status.status());
			message.setMessageUid(uid);
			message.setMailboxUidValidity(uidValidity);

			// valorizzaro il messaggio di stato impostando a zero il numero di
			// tentativi di processamento
			// e l'eventuale stacktrace dell'errore
			insertOrUpdateStatusMessage(message, error, 0);

			message.setDateDischarged(new Date());
			if (datamail != null) {
				// Inserisco la mail come blob
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				datamail.writeTo(stream);
				stream.close();
				message.setDatamail(null);// (stream.toByteArray());
			}

			session.save(message);
			session.flush();
			transaction.commit();
		} catch (Throwable e) {
			log.error("Eccezione nell'inserimento del messaggio nella mailbox: " + e.getMessage());
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Effettua un update dell'uid del messaggio
	 * 
	 * @param messageId
	 *            id del messaggio
	 * @param idMailbox
	 *            id della mailbox
	 * @param uid
	 *            uid associato nella INBOX
	 * @param uidValidity
	 *            validità degli uid nella INBOX
	 * @param error
	 * @throws Exception
	 */

	public void updateUidMessage(String messageId, String idMailbox, Long uid, Long uidValidity) throws Exception {

		Session session = null;
		Transaction transaction = null;

		try {
			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();
			MailboxMessageId messageIdMailbox = new MailboxMessageId(messageId, idMailbox);
			MailboxMessage messaggio = (MailboxMessage) session.get(MailboxMessage.class, messageIdMailbox);

			if (messaggio == null) {
				throw new Exception("Messaggio inesistente!");
			}

			messaggio.setMessageUid(uid);
			messaggio.setMailboxUidValidity(uidValidity);

			session.update(messaggio);
			session.flush();
			transaction.commit();
			log.error("Aggiornati uid e uidValidity per il messaggio avente id " + messageId);

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Effettua un update dello stato del messaggio
	 * 
	 * @param messageId
	 * @param idMailbox
	 * @param status
	 * @param error
	 * @throws Exception
	 */

	public void updateStatusMimeMessage(String messageId, String idMailbox, MessageStatus status, Throwable error) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();
			MailboxMessageId messageidmailbox = new MailboxMessageId(messageId, idMailbox);
			MailboxMessage messaggio = (MailboxMessage) session.get(MailboxMessage.class, messageidmailbox);

			if (messaggio == null) {
				throw new Exception("Messaggio inesistente!");
			}

			messaggio.setStatus(status.status());

			// aggiorno il messaggio di stato lasciando inalterato il numero di
			// tentativi
			insertOrUpdateStatusMessage(messaggio, error, null);

			session.update(messaggio);
			session.flush();
			transaction.commit();
			log.error("Messaggio con id " + messageId + " impostato allo status di: " + status.status());
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Controlla ed effettua un update dello stato del messaggio alla fine di tutte le operazioni
	 * 
	 * @param messageId
	 * @param idMailbox
	 * @throws Exception
	 */

	public void updateStatusFinishMimeMessageProcess(String messageId, String idMailbox) throws Throwable {
		Session session = null;
		Transaction transaction = null;
		String urlmime = null;
		Boolean enabledDeleteMimeFile = false;
		MailboxMessage messaggio = null;
		try {
			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();
			MailboxMessageId messageIdMailbox = new MailboxMessageId(messageId, idMailbox);
			messaggio = (MailboxMessage) session.get(MailboxMessage.class, messageIdMailbox);

			if (messaggio == null) {
				throw new Exception("Nessun messaggio trovato con id " + messageId + " per la mailbox " + idMailbox);
			}
			// Se il messaggio è in lock non faccio nessun controllo
			if (!messaggio.getStatus().equalsIgnoreCase(MessageStatus.MESSAGE_OPERATION_LOCK.status())) {

				// Recupero il parametro di cancellazione da filesystem
				MailboxConfigId configDelete = new MailboxConfigId(MailBoxConfigKey.MAILBOX_DELETE_TO_FILE_SYSTEM.keyname(),
						messaggio.getMailbox().getIdMailbox());
				MailboxConfig configMailboxDelete = (MailboxConfig) session.get(MailboxConfig.class, configDelete);

				enabledDeleteMimeFile = Boolean.valueOf(configMailboxDelete.getConfigValue());

				Set<MailboxMessageOperation> messageOperations = messaggio.getMailboxMessageOperations();

				if (messageOperations != null && !messageOperations.isEmpty()) {
					messaggio.setStatus(MessageStatus.MESSAGE_OPERATION_FINISH.status());
					for (MailboxMessageOperation operation : messageOperations) {
						if (operation.getOperationStatus().equals(OperationStatus.OPERATION_ERROR.status())) {
							// un'operazione è in errore, occorre riprocessare
							// il messaggio
							messaggio.setStatus(MessageStatus.MESSAGE_IN_ERROR_OPERATION.status());
							// indico l'operazione in errore nel messaggio di
							// stato, visto che se tutto è andato a buon fine
							// eventuali errori sono stati
							// resettati
							insertOrUpdateStatusMessage(messaggio, new Exception("Esiste un'operazione in errore: " + operation.getId()), null);
							// esco dal ciclo al primo errore trovato
							break;
						} else if (operation.getOperationStatus().equals(OperationStatus.OPERATION_PLANNED.status())) {
							// il messaggio deve essere fermato, esiste
							// un'operazione pianificata da completare
							messaggio.setStatus(MessageStatus.MESSAGE_OPERATION_PLANNED.status());
							// in questo caso non occorre aggiornare il
							// messaggio di errore
						}
					}
				}

				if (messaggio.getStatus().equalsIgnoreCase(MessageStatus.MESSAGE_OPERATION_FINISH.status())) {
					log.error("Messaggio con id " + messageId + " processato con successo");
					// anche quelli cancellati possono essere eliminati visto
					// che l'elaborazione è conclusa e va eseguita solamente la
					// cancellazione
					// sul server della mailbox
					urlmime = messaggio.getUrlMime();
					if (enabledDeleteMimeFile) {
						messaggio.setUrlMime(null);
					}
					// resetto eventuale messaggio di errore mantenendo
					// inalterato il numero di tentativi
					resetStatusMessageError(messaggio);
				}

				session.update(messaggio);
				session.flush();
				transaction.commit();
				log.error("Messaggio con id " + messageId + " impostato allo status finale di: " + messaggio.getStatus());
			}
		} catch (Throwable e) {
			if (transaction != null && !transaction.wasCommitted()) {
				transaction.rollback();
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		// Provo a cancellare il file
		if (enabledDeleteMimeFile) {
			if (messaggio.getStatus().equalsIgnoreCase(MessageStatus.MESSAGE_OPERATION_FINISH.status()) && StringUtils.isNotEmpty(urlmime)) {
				try {
					StorageCenter lStorageCenter = new StorageCenter();
					lStorageCenter.delete(idMailbox, urlmime);
				} catch (Exception e) {
					// Errore di cancellazione, vado avanti comunque
					log.error("Errore nella cancellazione del file associato alla mail in stato operation finish: " + e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * Cancella un'operazione dalla mailbox
	 * 
	 * @param operationum
	 * @param idmailbox
	 * @throws Exception
	 */
	public void deleteMailBoxOperation(String operationum, String idmailbox) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(idmailbox);
			transaction = session.beginTransaction();

			MailboxOperation mailboxoperation = (MailboxOperation) session.get(MailboxOperation.class, operationum);

			// Cancello configurazioni
			if (mailboxoperation != null) {
				if (mailboxoperation.getMailboxOperationConfigs() != null) {
					for (MailboxOperationConfig config : mailboxoperation.getMailboxOperationConfigs()) {
						session.delete(config);
					}
				}
				session.delete(mailboxoperation);
			}
			transaction.commit();
		} catch (Throwable e) {
			log.error("Eccezione deleteMailBoxOperation: " + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Salva un'operazione nella mailbox
	 * 
	 * @param operationnum
	 * @param operationname
	 * @param idmailbox
	 * @param configuration
	 * @throws Exception
	 */
	public void saveMailBoxOperation(String operationnum, String operationname, String idmailbox, HashMap<String, String> configuration) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(idmailbox);
			transaction = session.beginTransaction();

			Mailbox mailbox = (Mailbox) session.get(Mailbox.class, idmailbox);

			MailboxOperation operation = new MailboxOperation(operationnum, mailbox, operationname);

			// Salvo l'operazione
			session.save(operation);

			// Salvo le configurazioni
			if (configuration != null) {
				Set<String> keys = configuration.keySet();
				for (String key : keys) {
					if (configuration.get(key) != null) {

						MailboxOperationConfig configoperation = new MailboxOperationConfig();
						configoperation.setConfigValue(configuration.get(key));

						MailboxOperationConfigId id = new MailboxOperationConfigId(key, operationnum);
						configoperation.setId(id);

						session.save(configoperation);
					}
				}
			}
			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Effettua un salvataggio o un update dell'operazione
	 * 
	 * @param operationname
	 * @param messageId
	 * @param status
	 * @param operationvalue
	 * @throws Throwable
	 */
	public void saveOrUpdateOperation(String operationidname, String messageId, OperationStatus status, String operationvalue, String idmailbox)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(idmailbox);
			transaction = session.beginTransaction();

			// Recupero l'operazione
			MailboxMessageOperationId operationid = new MailboxMessageOperationId(operationidname, messageId, idmailbox);

			MailboxMessageOperation mailboxmessageoperation = (MailboxMessageOperation) session.get(MailboxMessageOperation.class, operationid);

			MailboxMessageOperation lMailboxMessageOperationToSave = null;

			if (mailboxmessageoperation == null) {
				lMailboxMessageOperationToSave = new MailboxMessageOperation();
				lMailboxMessageOperationToSave.setId(operationid);
				lMailboxMessageOperationToSave.setOperationTryNum((short) 1);
			} else {
				lMailboxMessageOperationToSave = mailboxmessageoperation;
			}

			lMailboxMessageOperationToSave.setOperationStatus(status.status());
			if (operationvalue != null) {
				lMailboxMessageOperationToSave.setOperationValue(operationvalue);
			}
			lMailboxMessageOperationToSave.setDateOperation(new Date());

			MailboxMessageOperation lMailboxMessageOperation = (MailboxMessageOperation) session.get(MailboxMessageOperation.class, operationid);
			if (lMailboxMessageOperation != null) {
				session.saveOrUpdate(lMailboxMessageOperationToSave);
			} else {
				session.persist(lMailboxMessageOperationToSave);
			}
			log.debug("Salvata l'operazione: " + operationidname + " per il messaggio con id: " + messageId);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Ritorna il il processo di flow
	 * 
	 * @return
	 * @throws Exception
	 */
	public ProcessOperationFlow getProcessFlow(String mailboxid) throws Exception {
		Session session = null;
		try {
			session = SubjectInitializer.getSession(mailboxid);

			// Recupero il MsgID
			Mailbox mailbox = (Mailbox) session.get(Mailbox.class, mailboxid);

			// Flow di processo
			ProcessOperationFlow flow = (ProcessOperationFlow) XmlUtil.xmlToObject(mailbox.getProcessFlow());

			return flow;
		} catch (Throwable e) {
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Ritorna il il processo di flow
	 * 
	 * @return
	 * @throws Exception
	 */
	public void saveProcessFlow(String mailboxid, ProcessOperationFlow flow) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(mailboxid);
			transaction = session.beginTransaction();

			// Recupero il MsgID
			Mailbox mailbox = (Mailbox) session.get(Mailbox.class, mailboxid);

			// Flow di processo
			String xml = XmlUtil.objectToXml(flow);
			mailbox.setProcessFlow(xml);
			session.update(mailbox);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Update di un'operazione sulla mailbox
	 * 
	 * @param operationnum
	 * @param idmailbox
	 * @param configuration
	 * @throws Exception
	 */
	public void updateMailBoxOperation(String operationnum, String idmailbox, HashMap<String, String> configuration) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(idmailbox);
			transaction = session.beginTransaction();

			// Salvo le configurazioni
			Set<String> keys = configuration.keySet();
			for (String key : keys) {

				MailboxOperationConfigId id = new MailboxOperationConfigId(key, operationnum);
				if (StringUtils.isNotBlank(configuration.get(key))) {
					MailboxOperationConfig conf = new MailboxOperationConfig();
					conf.setConfigValue(configuration.get(key));
					conf.setId(id);
					session.saveOrUpdate(conf);
				} else {

					MailboxOperationConfig confg = (MailboxOperationConfig) session.get(MailboxOperationConfig.class, id);

					if (confg != null) {
						session.delete(confg);
					}

				}
			}

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Recupero il nome dell'operazione
	 * 
	 * @param messageoperationid
	 * @return
	 * @throws Exception
	 */
	public String getOperationName(String operationid) throws Exception {
		Session session = null;
		try {
			session = SubjectInitializer.getSession(null);

			// Recupero il MsgID
			MailboxOperation mailboxoperation = (MailboxOperation) session.get(MailboxOperation.class, operationid);

			if (mailboxoperation == null) {
				return null;
			} else {
				return mailboxoperation.getOperationName();
			}
		} catch (Throwable e) {
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

	}

	/**
	 * Ritorna i messaggi ancora in elaborazione o in errore per riprocessare le operazioni ancora non eseguite in caso di eccezioni non le rilancio ma le
	 * gestisco, altrimenti si blocca lo scarico dei nuovi messaggi
	 * 
	 * @param idmailbox
	 * @param limit:
	 *            limite dei messaggi da considerare in fase di riprocessamento, di default è 10 se non è impostato
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FileMimeMessage> getMimeMessageToElaborate(String idmailbox, Integer limit) {

		Session session = null;
		List<FileMimeMessage> messages = new ArrayList<FileMimeMessage>();
		Transaction transaction = null;

		if (limit == null || limit < 0) {
			limit = MailboxConfigDefaultValue.DEFAULT_MAX_NUM_MESSAGE_ERROR_TO_PROCESS;
		}

		try {
			session = SubjectInitializer.getSession(idmailbox);
			transaction = session.beginTransaction();
			// Recupero i messaggi che
			// hanno lo stato in operation_error oppure
			// in force_operation o in discharged error con URL mime, significa
			// che si è verificato un errore prima di iniziare il processamento

			// Recupero prima i messaggi con force_operation
			// limito il numero di risultati

			List<MailboxMessage> mailmessageboxesInForce = session.createCriteria(MailboxMessage.class).add(Restrictions.eq("id.idMailbox", idmailbox))
					.add(Restrictions.eq("status", MessageStatus.MESSAGE_FORCE_OPERATION.status())).addOrder(Order.asc("dateDischarged")).setMaxResults(limit)
					.list();

			limit = limit - mailmessageboxesInForce.size();

			if (limit > 0) {
				// se non ho superato il limite procedo con i messaggi da
				// ri-processare
				// Recupero poi i messaggi in error operation
				ProjectionList lList = Projections.projectionList().add(Projections.max("dateOperation"), "operationTime")
						.add(Projections.groupProperty("mailboxMessage"), "message");

				List<Object[]> mailmessageboxesOperationError = session.createCriteria(MailboxMessageOperation.class)
						.createAlias("mailboxMessage", "mailboxMessage").add(Restrictions.eq("id.idMailbox", idmailbox))
						.add(Restrictions.eq("mailboxMessage.status", MessageStatus.MESSAGE_IN_ERROR_OPERATION.status())).addOrder(Order.asc("operationTime"))
						.setProjection(lList).setMaxResults(limit).list();

				for (Object[] bean : mailmessageboxesOperationError) {
					MailboxMessage lMailboxMessage = (MailboxMessage) bean[1];
					mailmessageboxesInForce.add(lMailboxMessage);
					limit--;
				}
			}

			if (limit > 0) {
				// se non ho superato il limite procedo con i messaggi da
				// ri-processare
				// Recupero poi i messaggi in error operation
				// Infine recupero i messaggi in discharged error che hanno
				// l'url
				// correttamente valorizzato
				List<MailboxMessage> mailmessageboxesDischargedError = session.createCriteria(MailboxMessage.class)
						.add(Restrictions.eq("id.idMailbox", idmailbox)).add(Restrictions.eq("status", MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status()))
						.add(Restrictions.isNotNull("urlMime")).addOrder(Order.asc("dateDischarged")).setMaxResults(limit).list();

				mailmessageboxesInForce.addAll(mailmessageboxesDischargedError);
			}

			MailboxConfigId configid = new MailboxConfigId();
			configid.setConfigKey(MailBoxConfigKey.MAILBOX_DIRECTORY.keyname());
			configid.setIdMailbox(idmailbox);
			for (MailboxMessage message : mailmessageboxesInForce) {
				try {
					// singolo try catch per messaggio
					if (StringUtils.isBlank(message.getUrlMime())) {
						// Provo a riscaricarlo
						try {
							message.setStatus(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status());
							session.update(message);
						} catch (Throwable e) {
							log.error("Errore update message " + message.getId() + " allo stato ERROR DISCHARGED", e);
							throw e;
						}
					} else {
						StorageCenter lStorageCenter = new StorageCenter();
						InputStream lInputStream = lStorageCenter.extract(idmailbox, message.getUrlMime());
						if (lInputStream == null) {
							// Provo a rimetterlo in processamento
							try {
								message.setStatus(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status());
								session.update(message);
							} catch (Throwable e) {
								log.error("Errore update message " + message.getId() + " allo stato ERROR DISCHARGED", e);
								throw e;
							}
						} else {
							try {
								// Controllo se è un mime message

								FileMimeMessage mimemessage = new FileMimeMessage(null);
								mimemessage.setFilemime(lInputStream);
								messages.add(mimemessage);
							} catch (Throwable e) {
								// Lo rimetto in processamento il file potrebbe
								// essere corrotto
								try {
									message.setStatus(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status());
									session.update(message);
								} catch (Throwable e1) {
									log.error("Errore update message " + message.getId() + " allo stato ERROR DISCHARGED", e1);
									throw e1;
								}
							}
						}
					}
				} catch (Exception exc) {
					log.error("Eccezione nel recupero del messaggio in errore", exc);
					// non processo il messaggio
					if (messages.contains(message)) {
						messages.remove(message);
					}
					// annullo le modifiche fatte al singolo oggetto, ma
					// mantengo le altre che sono andate a buon fine
					session.evict(message);
				}
			}
			transaction.commit();
		} catch (Throwable e) {
			// Se arrivo fino a qui gli eventuali messaggi in errore devono
			// essere riprocessati includendoli manualmente dalla console
			log.error("Eccezione nel recupero dei messaggi in errore", e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return messages;
	}

	/**
	 * Ritorna i messaggi in stato discharged per avviare il processo di elaborazione
	 * 
	 * @param idmailbox
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FileMimeMessage> getMimeMessageDischargedToElaborate(String idmailbox) {
		Session session = null;
		List<FileMimeMessage> messages = new ArrayList<FileMimeMessage>();
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(idmailbox);
			transaction = session.beginTransaction();

			// Recupero prima i messaggi con force_operation
			List<MailboxMessage> messageDischarged = session.createCriteria(MailboxMessage.class).add(Restrictions.eq("id.idMailbox", idmailbox))
					.add(Restrictions.eq("status", MessageStatus.MESSAGE_DISCHARGED.status())).addOrder(Order.asc("dateDischarged")).list();

			MailboxConfigId configid = new MailboxConfigId();
			configid.setConfigKey(MailBoxConfigKey.MAILBOX_DIRECTORY.keyname());
			configid.setIdMailbox(idmailbox);
			for (MailboxMessage message : messageDischarged) {
				try {
					// singolo try catch per messaggio
					if (StringUtils.isBlank(message.getUrlMime())) {
						// Provo a riscaricarlo
						try {
							message.setStatus(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status());
							session.update(message);
						} catch (Throwable e) {
							log.error("Errore update message " + message.getId() + " allo stato ERROR DISCHARGED", e);
							throw e;
						}
					} else {
						StorageCenter lStorageCenter = new StorageCenter();
						InputStream lInputStream = lStorageCenter.extract(idmailbox, message.getUrlMime());
						if (lInputStream == null) {
							// Provo a rimetterlo in processamento
							try {
								message.setStatus(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status());
								session.update(message);
							} catch (Throwable e) {
								log.error("Errore update message " + message.getId() + " allo stato ERROR DISCHARGED", e);
								throw e;
							}
						} else {
							try {
								// Controllo se è un mime message

								FileMimeMessage mimemessage = new FileMimeMessage(null);
								mimemessage.setFilemime(lInputStream);
								messages.add(mimemessage);
							} catch (Throwable e) {
								// Lo rimetto in processamento il file potrebbe
								// essere corrotto
								try {
									message.setStatus(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status());
									session.update(message);
								} catch (Throwable e1) {
									log.error("Errore update message " + message.getId() + " allo stato ERROR DISCHARGED", e1);
									throw e1;
								}
							}
						}
					}
				} catch (Exception exc) {
					log.error("Eccezione nel recupero del messaggio discharged", exc);
					// non processo il messaggio
					if (messages.contains(message)) {
						messages.remove(message);
					}
					// annullo le modifiche fatte al singolo oggetto, ma
					// mantengo le altre che sono andate a buon fine
					session.evict(message);
				}
			}
			transaction.commit();
		} catch (

		Throwable e) {
			// Se arrivo fino a qui gli eventuali messaggi in errore devono
			// essere riprocessati includendoli manualmente dalla console
			log.error("Eccezione nel recupero dei messaggi discharged", e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return messages;
	}

	@SuppressWarnings("unchecked")
	public void deleteDischargedMessage(String idMailbox) {
		Session lSession = null;
		Transaction lTransaction = null;
		try {
			lSession = HibernateUtil.begin();
			lTransaction = lSession.beginTransaction();
			// DetachedCriteria lDetachedCriteria =
			// DetachedCriteria.forClass(TEmailMgo.class).setProjection(Projections.property("messageId"));
			List<String> lList = lSession.createCriteria(MailboxMessage.class).add(Restrictions.eq("status", MessageStatus.MESSAGE_DISCHARGED.status()))
					.add(Restrictions.eq("mailbox.idMailbox", idMailbox)).// .add(Subqueries.propertyNotIn("id.messageId",
																			// lDetachedCriteria)).
					setProjection(Projections.property("id.messageId")).list();
			for (String lStrMessageId : lList) {
				MailboxMessage lMailboxMessage = (MailboxMessage) lSession.get(MailboxMessage.class, new MailboxMessageId(lStrMessageId, idMailbox));
				lSession.delete(lMailboxMessage);
			}
			lSession.flush();
			lTransaction.commit();
		} catch (Throwable e) {
			log.error("Errore nella cancellazione dei  messaggi in stato discharged", e);
			if (lTransaction != null) {
				lTransaction.rollback();
			}
		} finally {
			if (lSession != null) {
				lSession.close();
				lSession = null;
			}
		}

	}

	/**
	 * Metodo che verifica che tutte le mailbox abbiano il mailboxconnect id configurato
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public boolean checkAllMailBoxesAreIdConfigured() {

		Session lSession = null;

		Boolean result = false;

		try {

			lSession = SubjectInitializer.getSession(null);

			Criteria criteria = lSession.createCriteria(Mailbox.class, "mbox").add(Restrictions.eq("mbox.status", "active"));

			// ogni mailbox attiva deve avere associato un mailconnectid, la
			// config key è univoca quindi non possono esistere duplicati
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MailboxConfig.class, "config")
					.add(Restrictions.eq("config.id.configKey", MailBoxConfigKey.MAILBOX_MAILCONNECT_ID.keyname()))
					.add(Restrictions.isNotNull("config.configValue")).add(Property.forName("config.id.idMailbox").eqProperty("mbox.idMailbox"));

			criteria.add(Subqueries.notExists(detachedCriteria.setProjection(Projections.property("config.id.configKey"))));
			List<Mailbox> mailboxNoConnectId = criteria.list();

			if (mailboxNoConnectId != null) {
				log.debug("Numero mailbox senza connectId: " + mailboxNoConnectId.size());
				result = mailboxNoConnectId.size() == 0;
			}

		} catch (Throwable e) {
			log.error("Errore metodo checkAllMailBoxesAreIdConfigured", e);
			result = false;
		} finally {
			if (lSession != null) {
				lSession.close();
			}
		}

		return result;
	}

	/**
	 * Metodo per l'aggiornamento o il primo inserimento del messaggio di stato in formato XML che riporti eventualmente il numero di tentativi di processamento
	 * effettuati per il messaggio e lo stacktrace di un eventuale errore. Impostare a NULL il valore degli oggetti che si vuole lasciare inalterati nella
	 * versione corrente. Se lo status non è impostato in database in formato XML o non è valorizzato si procede con l'inizializzazione XML. Attenzione che il
	 * contatore riguarda il processamento del messaggio e non delle singole operazioni che vanno aggiornate nella relativa tabella
	 * 
	 * @param message
	 * @param error
	 * @param numTry
	 * @throws Exception
	 */

	private void insertOrUpdateStatusMessage(MailboxMessage message, Throwable error, Integer numTry) throws Exception {

		if (error != null || numTry != null) {

			// inserisco o aggiorno lo status messagge solo se se errore o
			// numero di tentativi
			// sono valorizzati, in caso contrario manteniamo il precedente
			// valore o lasciamo NULL

			String idMessaggio = message.getId().getMessageId();

			StatusMessage statusMessage = null;
			try {
				// recupero l'eventuale xml con lo stato attuale del messaggio
				if (message.getStatusMessage() != null) {
					statusMessage = (StatusMessage) XmlUtil.xmlToObject(message.getStatusMessage());
				}
			} catch (Exception exc) {
				log.warn("Errore nel recupero del XML status del messaggio con id: " + idMessaggio);
			}

			if (statusMessage == null) {
				// creo un nuovo status message
				statusMessage = new StatusMessage();
			}

			if (numTry != null) {
				// salvo l'eventuale numero di tentativi
				TryNum tentativi = new TryNum();
				tentativi.setValue(numTry);
				statusMessage.setTryNum(tentativi);
			}

			// salvo anche l'eventuale stacktrace dell'errore
			if (error != null) {
				it.eng.utility.email.util.statusMessage.StatusMessage.Error errorXML = new it.eng.utility.email.util.statusMessage.StatusMessage.Error();
				String errorMessage = ExceptionUtils.getStackTrace(error);
				errorXML.setMessage(errorMessage);
				statusMessage.setError(errorXML);
			}

			// creo con jaxb la stringa xml a partire dall'oggetto
			JAXBContext jaxbContext = JAXBContext.newInstance(StatusMessage.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter writer = new StringWriter();
			marshaller.marshal(statusMessage, writer);
			message.setStatusMessage(writer.toString());

		}

	}

	/**
	 * Metodo per incrementare in database il numero di tentativi di processamento del messaggio. Se non è valorizzato lo imposta a 1, generando il relativo XML
	 * L'eventuale errore è rimosso, visto che si procede con un nuovo processamento del messaggio che presuppone che non si verificheranno errori, che
	 * eventualmente saranno registrati successivamente. Attenzione che il contatore riguarda il processamento del messaggio e non delle singole operazioni che
	 * vanno aggiornate nella relativa tabella
	 * 
	 * @param message
	 * @throws Exception
	 */

	public void incrementOrInitializeNumTryStatusMessageAndResetError(String mailboxId, MailboxMessage message) throws Exception {

		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(mailboxId);
			transaction = session.beginTransaction();

			// inserisco o aggiorno lo status messagge solo se il numero di
			// tentativi è
			// valorizzato,
			// in caso contrario manteniamo il precedente valore o lasciamo NULL

			String idMessaggio = message.getId().getMessageId();

			StatusMessage statusMessage = null;
			try {
				// recupero l'eventuale xml con lo stato attuale del messaggio
				if (message.getStatusMessage() != null) {
					statusMessage = (StatusMessage) XmlUtil.xmlToObject(message.getStatusMessage());
				}
			} catch (Exception exc) {
				log.warn("Errore nel recupero del XML status del messaggio con id: " + idMessaggio);
			}

			if (statusMessage == null) {
				// creo un nuovo status message
				statusMessage = new StatusMessage();
			}

			TryNum actual = statusMessage.getTryNum();

			if (actual != null) {
				// aumento l'attuale valore
				actual.setValue(actual.getValue() + 1);
			} else {
				// setto a 1 il numero dei tentativi
				actual = new TryNum();
				actual.setValue(1);
			}

			statusMessage.setTryNum(actual);
			// resetto l'eventuale errore
			statusMessage.setError(null);

			// creo con jaxb la stringa xml a partire dall'oggetto
			JAXBContext jaxbContext = JAXBContext.newInstance(StatusMessage.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter writer = new StringWriter();
			marshaller.marshal(statusMessage, writer);
			message.setStatusMessage(writer.toString());

			session.update(message);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Metodo per incrementare in database il numero di tentativi di processamento del messaggio. Se non è valorizzato lo imposta a 1, generando il relativo XML
	 * L'eventuale errore è mantenuto. Attenzione che il contatore riguarda il processamento del messaggio e non delle singole operazioni che vanno aggiornate
	 * nella relativa tabella
	 * 
	 * @param message
	 * @throws Exception
	 */

	public void incrementOrInitializeNumTryStatus(String mailboxId, MailboxMessage message) throws Exception {

		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(mailboxId);
			transaction = session.beginTransaction();

			// inserisco o aggiorno lo status messagge solo se il numero di
			// tentativi è
			// valorizzato,
			// in caso contrario manteniamo il precedente valore o lasciamo NULL

			String idMessaggio = message.getId().getMessageId();

			StatusMessage statusMessage = null;

			try {
				// recupero l'eventuale xml con lo stato attuale del messaggio
				if (message.getStatusMessage() != null) {
					statusMessage = (StatusMessage) XmlUtil.xmlToObject(message.getStatusMessage());
				}
			} catch (Exception exc) {
				log.warn("Errore nel recupero del XML status del messaggio con id: " + idMessaggio);
			}

			if (statusMessage == null) {
				// creo un nuovo status message
				statusMessage = new StatusMessage();
			}

			TryNum actual = statusMessage.getTryNum();

			if (actual != null) {
				// aumento l'attuale valore
				actual.setValue(actual.getValue() + 1);
			} else {
				// setto a 1 il numero dei tentativi
				actual = new TryNum();
				actual.setValue(1);
			}

			statusMessage.setTryNum(actual);

			// creo con jaxb la stringa xml a partire dall'oggetto
			JAXBContext jaxbContext = JAXBContext.newInstance(StatusMessage.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter writer = new StringWriter();
			marshaller.marshal(statusMessage, writer);
			message.setStatusMessage(writer.toString());

			session.update(message);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	/**
	 * Metodo che ritorna il messaggio di stato del messaggio in input in formato XML
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */

	public StatusMessage getStatusMessage(MailboxMessage message) throws Exception {

		StatusMessage result = null;

		String idMessaggio = message.getId().getMessageId();

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(StatusMessage.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			// recupero l'eventuale xml con lo stato attuale del messaggio
			result = (StatusMessage) unmarshaller.unmarshal(new StringReader(message.getStatusMessage()));
		} catch (Exception exc) {
			log.warn("Errore nel recupero del XML status del messaggio con id: " + idMessaggio);
		}

		return result;

	}

	/**
	 * Metodo per resettare l'eventuale errore presente nello staus del messaggio, lasciando inalterato l'eventuale numero di tentativi effettuati
	 * 
	 * @param message
	 * @throws Exception
	 */

	private void resetStatusMessageError(MailboxMessage message) throws Exception {

		String idMessaggio = message.getId().getMessageId();

		try {
			// recupero l'eventuale xml con lo stato attuale del messaggio
			JAXBContext jaxbContext = JAXBContext.newInstance(StatusMessage.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			// recupero l'eventuale xml con lo stato attuale del messaggio
			StatusMessage statusMessage = (StatusMessage) unmarshaller.unmarshal(new StringReader(message.getStatusMessage()));
			if (statusMessage != null) {
				statusMessage.setError(null);
			}
		} catch (Exception exc) {
			log.warn("Errore nel recupero del XML status del messaggio con id: " + idMessaggio);
		}

	}

	/**
	 * Metodo che salva o aggiorna l'ultimo UID associato alla mailbox
	 * 
	 * @param idMailbox
	 * @param uid
	 * @param validity
	 * @throws Exception
	 */

	public void saveOrUpdateUIDMailbox(String idMailbox, Long uid, Long validity) throws Exception {

		Session session = null;
		Transaction transaction = null;

		try {
			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();

			Mailbox mailbox = (Mailbox) session.get(Mailbox.class, idMailbox);
			// mailbox.setIdMailbox(idMailbox);

			// salvo l'ultimo UID presente in database per la mailbox

			MailboxInfoId infoId = new MailboxInfoId();
			infoId.setInfoKey(MailBoxInfoKey.MAILBOX_LAST_MESSAGE_UID.keyname());
			infoId.setIdMailbox(idMailbox);

			MailboxInfo info = (MailboxInfo) session.get(MailboxInfo.class, infoId); 
			MailboxInfo infoToSave = null;
			if (info == null) {
				infoToSave = new MailboxInfo();
			} else {
				infoToSave = info;
			}
			infoToSave.setId(infoId);
			infoToSave.setMailbox(mailbox);
			infoToSave.setInfoValue(uid.toString());

			if (info != null) {
				session.saveOrUpdate(infoToSave);
			} else {
				session.persist(infoToSave);
			}

			session.flush();

			// salvo la validità dell'UID della mailbox

			infoId = new MailboxInfoId();
			infoId.setInfoKey(MailBoxInfoKey.MAILBOX_UID_VALIDITY.keyname());
			infoId.setIdMailbox(idMailbox);

			info = (MailboxInfo) session.get(MailboxInfo.class, infoId); 
			infoToSave = null;
			if (info == null) {
				infoToSave = new MailboxInfo();
			} else {
				infoToSave = info;
			}
			infoToSave.setMailbox(mailbox);
			infoToSave.setId(infoId);
			infoToSave.setInfoValue(validity.toString());

			info = (MailboxInfo) session.get(MailboxInfo.class, infoId);
			if (info != null) {
				session.saveOrUpdate(infoToSave);	
			} else {
				session.persist(infoToSave);
			}
			
			session.flush();

			transaction.commit();

		} catch (Throwable e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

	}

	/**
	 * Metodo che salva o aggiorna l'informazione per la mailbox
	 * 
	 * @param idMailbox
	 * @param infoKey
	 * @param value
	 * @throws Exception
	 */

	public void saveOrUpdateInfoMailbox(String idMailbox, MailBoxInfoKey infoKey, String value) throws Exception {

		Session session = null;
		Transaction transaction = null;

		try {
			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();

			Mailbox mailbox = new Mailbox();
			mailbox.setIdMailbox(idMailbox);

			// inserisco o aggiorno l'informazione

			MailboxInfoId infoId = new MailboxInfoId();
			infoId.setInfoKey(infoKey.keyname());
			infoId.setIdMailbox(idMailbox);

			
			MailboxInfo info = (MailboxInfo) session.get(MailboxInfo.class, infoId); 
			MailboxInfo infoToSave = null;
			if (info == null) {
				infoToSave = new MailboxInfo();
			} else {
				infoToSave = info;
			}
			infoToSave.setId(infoId);
			infoToSave.setMailbox(mailbox);
			infoToSave.setInfoValue(value);

			if (info != null) {
				session.saveOrUpdate(infoToSave);
			} else {
				session.persist(infoToSave);
			}
			
			session.flush();

			transaction.commit();

		} catch (Throwable e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

	}

	/**
	 * Metodo per resettare UID salvato per la mailbox
	 * 
	 * @param idMailbox
	 * @param uid
	 * @param validity
	 * @throws Exception
	 */

	public void resetUIDMailbox(String idMailbox) throws Exception {

		Session session = null;
		Transaction transaction = null;

		try {
			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();

			Mailbox mailbox = new Mailbox();
			mailbox.setIdMailbox(idMailbox);

			// cancello l'ultimo UID presente in database per la mailbox

			MailboxInfoId id = new MailboxInfoId(MailBoxInfoKey.MAILBOX_LAST_MESSAGE_UID.keyname(), idMailbox);
			MailboxInfo info = (MailboxInfo) session.get(MailboxInfo.class, id);
			if (info != null) {
				session.delete(info);
				session.flush();
			}

			// cancello la validità associata alla mailbox

			id = new MailboxInfoId(MailBoxInfoKey.MAILBOX_UID_VALIDITY.keyname(), idMailbox);
			info = (MailboxInfo) session.get(MailboxInfo.class, id);
			if (info != null) {
				session.delete(info);
				session.flush();
			}

			transaction.commit();
			log.info("Resettati UIDValidity e ultimo UID");

		} catch (Throwable e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

	}

	/**
	 * Metodo per resettare UID salvato per la mailbox
	 * 
	 * @param idMailbox
	 * @param session
	 * @throws Exception
	 */

	public void resetUIDMailboxInSession(String idMailbox, Session session) throws Exception {

		Mailbox mailbox = new Mailbox();
		mailbox.setIdMailbox(idMailbox);

		// cancello l'ultimo UID presente in database per la mailbox

		MailboxInfoId id = new MailboxInfoId(MailBoxInfoKey.MAILBOX_LAST_MESSAGE_UID.keyname(), idMailbox);
		MailboxInfo info = (MailboxInfo) session.get(MailboxInfo.class, id);
		if (info != null) {
			session.delete(info);
			session.flush();
		}

		// cancello la validità associata alla mailbox

		id = new MailboxInfoId(MailBoxInfoKey.MAILBOX_UID_VALIDITY.keyname(), idMailbox);
		info = (MailboxInfo) session.get(MailboxInfo.class, id);
		if (info != null) {
			session.delete(info);
			session.flush();
		}

		log.info("Resettati UIDValidity e ultimo UID");

	}

	/**
	 * Metodo che verifica se è stata inviata una mail automatica allo stesso destinatario in un determinato periodo
	 * 
	 * @param idAccount
	 * @param destinatario
	 * @param oggetto
	 * @param giorniIntervallo
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public Boolean exixstMailAlreadyReplied(String idMailbox, String idOperazione, String destinatario, Integer giorniIntervallo) throws Exception {

		Boolean result = false;

		org.hibernate.Session session = null;
		try {
			session = HibernateUtil.begin();
		} catch (Exception e) {
			throw new AurigaMailBusinessException("Impossibile aprire la connessione ", e);
		}

		try {

			Calendar now = Calendar.getInstance();
			if (giorniIntervallo > 0) {
				now.add(Calendar.DATE, -giorniIntervallo);
			}

			Criteria criteria = session.createCriteria(MailboxMessageOperation.class).createAlias("mailboxOperation", "mailboxOperation");
			criteria.add(Restrictions.eq("id.idMailboxoperation", idOperazione));
			criteria.add(Restrictions.eq("id.idMailbox", idMailbox));
			criteria.add(Restrictions.ge("dateOperation", now.getTime()));
			// operazioni con invio effetttuato
			criteria.add(
					Restrictions.in("operationStatus", new String[] { OperationStatus.OPERATION_FINISH.status(), OperationStatus.OPERATION_FORCED.status() }));
			criteria.add(Restrictions.like("operationValue", "<replyto>" + destinatario.toLowerCase() + "</replyto>", MatchMode.ANYWHERE).ignoreCase());
			criteria.add(Restrictions.like("operationValue", "<alreadyreply>false</alreadyreply>", MatchMode.ANYWHERE).ignoreCase());

			Long numeroRisultati = (Long) criteria.setProjection(Projections.count("id.messageId")).uniqueResult();

			if (numeroRisultati != null && numeroRisultati > 0) {
				result = true;
			}

		}

		catch (Exception e) {
			log.error("Eccezione exixstMailAlreadyReplied", e);
			throw e;
		} finally {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				log.error("Eccezione nel rilascio della sessione post exixstMailAlreadyReplied", e);
				throw e;
			}
		}

		return result;

	}

	
	/**
	 * Resetta i contatori, elimina le MailboxMessageOperation e mette la MailboxMessage in stato discharged
	 * 
	 * @param idMailbox
	 * @param messageId
	 * @throws Exception
	 */
	public void riprocessaMailboxMessage(String idMailbox, String messageId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = SubjectInitializer.getSession(null);
			transaction = session.beginTransaction();

			// Recupero le operazioni e le cancello
			log.debug("riprocessaMailboxMessage.idMailbox: "+idMailbox);
			log.debug("riprocessaMailboxMessage.messageId: "+messageId);
			MailboxMessageId id = new MailboxMessageId(messageId, idMailbox);

			MailboxMessage message = (MailboxMessage) session.get(MailboxMessage.class, id);

			if (message != null) {
				Set<MailboxMessageOperation> operations = message.getMailboxMessageOperations();
				for (MailboxMessageOperation operation : operations) {
					session.delete(operation);
				}
				message.setStatus(MessageStatus.MESSAGE_DISCHARGED.status());
				session.update(message);
			}			
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			log.error("Errore nella cancellazione del messaggio " + messageId + " dalla mailbox " + idMailbox, e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}
	
	/**
	 * Recupera le mail delle uo a cui mandare le notifiche di assegnazione
	 * 
	 * @param idSpAoo
	 * @param idCasella
	 * @return
	 * @throws Exception
	 */
	public List<String> getNotificationEmails(String idCasella, String casellaMittente, String accountmittente) throws Exception {
		String idAccount = AccountUtils.retrieveIdAccount(idCasella);
		ArrayList<String> emails = new ArrayList<String>();
		org.hibernate.Session session = null;
		try {
			session = HibernateUtil.begin();
		} catch (Exception e) {
			throw new AurigaMailBusinessException("Impossibile aprire la connessione ", e);
		}
		try {
			SQLQuery query = session.createSQLQuery("select nt.* from dmt_struttura_org o , dmt_rubrica_soggetti s, table(s.e_mail) nt "
					+ "where o.id_uo in( "
					+ "                  select A.ID_PROV_FRUITORE from t_anag_fruitori_caselle a,  T_REL_CASELLE_FRUITORI r, T_PROFILI_FRUITORI_MGO p "
					+ "                  where a.TIPO = 'UO' and R.ID_FRUITORE_CASELLA = A.ID_FRUITORE_CASELLA"
					+ "                    and r.id_casella = '"+idAccount+"'"
					+ "                    and r.FLG_ANN=0 "
					+ "                    and R.FLG_UTILIZZO_X_RICEZIONE = 1 "
					+ "                    and r.ID_REL_FRUITORE_CASELLA=p.ID_REL_FRUITORE_CASELLA"
					+ "                    and p.FLG_ANN=0 and p.PROFILO='smistatore')"
					+ "                    and O.ID_UO_IN_RUBRICA = S.ID_SOGG_RUBRICA");
			List<Object[]> rows = query.list();
			// se è presente la casella mittente, o non inviamo a quell'indirizzo o non notifichiamo proprio 
			for(Object[] row : rows) {
				if ( !casellaMittente.trim().equalsIgnoreCase(row[0].toString().trim()) &&
						!accountmittente.trim().equalsIgnoreCase(row[0].toString().trim())) {
					emails.add(row[0].toString());
				}
			}
		}
	
		catch (Exception e) {
			log.error("Eccezione exixstMailAlreadyReplied", e);
			throw e;
		} finally {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				log.error("Eccezione nel rilascio della sessione post exixstMailAlreadyReplied", e);
				throw e;
			}
		}
		return emails;
	}
	
	public static  String getMailboxPassword(String idMailbox) throws Exception {
		Session lSession = null;
		String password = "";
		try {

			lSession = SubjectInitializer.getSession(idMailbox);
			String idAccount = AccountUtils.retrieveIdAccount(idMailbox);

			Criteria criteria = lSession.createCriteria(MailboxAccount.class, "MailboxAccount")
					.add(Restrictions.eq("MailboxAccount.idAccount", idAccount));

			List<MailboxAccount> mailboxAccounts = criteria.list();
			if (mailboxAccounts == null || mailboxAccounts.size() != 1) {
				throw new Exception("Imposibile recuperare l'idAccount per la casella: " + idMailbox);
			}

			criteria = lSession.createCriteria(MailboxAccountConfig.class, "mailboxAccountConfig")
					.add(Restrictions.eq("mailboxAccountConfig.mailboxAccount", mailboxAccounts.get(0)))
					.add(Restrictions.eq("mailboxAccountConfig.id.configKey", AccountConfigKey.ACCOUNT_PASSWORD.keyname()));

			List<MailboxAccountConfig> mailboxAccountConfigs = criteria.list();

			if (mailboxAccountConfigs != null) {
				password = mailboxAccountConfigs.get(0).getConfigValue();
			}
			return password;
		} catch (Throwable e) {
			log.error("Errore metodo getMailboxPassword: " + ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			if (lSession != null) {
				lSession.close();
				lSession = null;
			}				
		}
	}
	
}