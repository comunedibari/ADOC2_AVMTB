package it.eng.utility.email.scheduler.delete;

import java.util.Calendar;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.utility.MailUtil;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.reader.OperationStatus;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.util.synchro.SynchroMail;

public class DeleteService implements Runnable {

	private String idMailbox; // id mailbox specifica da considerare
	private Boolean forceDelete; // forzo la cancellazione della mail anche se non è prevista una operazione di cancellazione specifica per la mailbox o per la
									// singola mail
	private Boolean forceDeleteWithDelay;
	private Integer limit; // numero massimo di cancellazioni da effettuare
	private Boolean enableBackup; // abilito il backup del messaggio in una folder di appoggio prima di effettuare la cancellazione
	private String folderBackup; // nome della folder di backup, se non è presente la crea
	private Integer hourDelay = 0; // attesa prima di cancellare un messaggio da quanto il messaggio è stato processato

	private static Logger mLogger = LogManager.getLogger(DeleteService.class);

	@Override
	public void run() {

		try {

			// lo stato del thread viene impostato effettivamente quando si invoca il relativo metodo

			// inserisco la routingkey per scrivere il log in quello specifico della mailbox
			ThreadContext.put("DELETE-ROUTINGKEY", idMailbox);
			// setto il nome del thread
			Thread.currentThread().setName("Thread-Delete-" + idMailbox);

			Boolean activeDelete = false;

			if (forceDelete) {
				activeDelete = true;
			} else {
				FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();
				String value = mailBusiness.getMailBoxConfigParameter(idMailbox, MailBoxConfigKey.MAILBOX_DELETE_DELAY);
				if (StringUtils.isNotBlank(value)) {
					hourDelay = Integer.parseInt(value);
				}
				// verifico se esistono messaggi da cancellare in stato PLANNED per la mailbox
				activeDelete = checkExistMessageToDeleteMailbox(hourDelay);
			}

			if (activeDelete) {

				// considero solo le mailbox associate al mailconnectid configurato per il server
				SynchroMail lSynchroMail = new SynchroMail();
				lSynchroMail.synchro(idMailbox, forceDelete,forceDeleteWithDelay,  limit, enableBackup, folderBackup);
				if (lSynchroMail.getListIdMessageBackuped() != null && lSynchroMail.getListIdMessageBackuped().size() > 0) {
					mLogger.info("Per la casella " + idMailbox + " sono state copiate le mail aventi id:");
					for (String id : lSynchroMail.getListIdMessageBackuped()) {
						mLogger.info(id);
					}
				}
				if (lSynchroMail.getListIdMessageDeletedFromFolder() != null && lSynchroMail.getListIdMessageDeletedFromFolder().size() > 0) {
					mLogger.info("Per la casella " + idMailbox + " sono state eliminate le mail aventi id:");
					for (String id : lSynchroMail.getListIdMessageDeletedFromFolder()) {
						mLogger.info(id);
					}
					// dopo la cancellazione non occorre resettare l'UID salvato in database, visto che ho cancellato messaggi già scaricati e
					// processati
					// la cancellazione inoltre non modifica la validità degli UID
				}

			} else {
				mLogger.info("Nessun messaggio con operazione di cancellazione pianificata per la mailbox " + idMailbox);
			}

		} catch (Exception e) {
			mLogger.error("Eccezione DeleteService", e);
		}
	}

	/**
	 * Metodo che restituisce le caselle con almeno una mail con cancellazione schedulata, escludendo la lista di mailbox in input, che sono gestite a parte
	 * 
	 * @return
	 * @throws MessagingException
	 */

	public Boolean checkExistMessageToDeleteMailbox(Integer delay) throws Exception {

		Session session = null;
		Boolean result = false;

		try {
			try {
				session = SubjectInitializer.getSession(null);
			} catch (Exception e) {
				throw new MessagingException(e.getMessage(), e);
			}

			Criteria criteria = session.createCriteria(MailboxMessageOperation.class, "messageOperation").createAlias("mailboxOperation", "operation");

			criteria.add(Restrictions.eq("messageOperation.operationStatus", OperationStatus.OPERATION_PLANNED.status()));
			criteria.add(Restrictions.eq("messageOperation.id.idMailbox", idMailbox));
			criteria.add(Restrictions.eq("operation.operationName", "DeleteMessageOperation"));

			if (hourDelay > 0) {

				Calendar now = Calendar.getInstance();
				now.add(Calendar.HOUR, -hourDelay);
				
				// devo considerare il delay per effettuare la cancellazione
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TEmailMgo.class, "tEmailMgo");
				detachedCriteria.add(Property.forName("tEmailMgo.mailboxAccount.idAccount").eq(getIdCasella(idMailbox)));
				detachedCriteria.add(Property.forName("tEmailMgo.messageId").eqProperty("messageOperation.id.messageId"));
				detachedCriteria.add(Property.forName("tEmailMgo.tsInvio").le(now.getTime()));
				criteria.add(Subqueries.exists(detachedCriteria.setProjection(Projections.property("idEmail"))));
			}

			Long countMessageOperation = (Long) criteria.setProjection(Projections.count("messageOperation.id.messageId")).uniqueResult();

			// esiste almeno un messaggio in stato PLANNED
			result = (countMessageOperation != null && countMessageOperation > 0);

		} catch (Exception e) {
			mLogger.error("Eccezione checkExistMessageToDeleteMailbox", e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return result;
	}

	private String getIdCasella (String idMailbox) {
		Session session = null;
		String result = "";

		try {
			try {
				session = SubjectInitializer.getSession(null);
			} catch (Exception e) {
				throw new MessagingException(e.getMessage(), e);
			}

			Criteria criteria = session.createCriteria(Mailbox.class, "mailbox");

			criteria.add(Restrictions.eq("mailbox.idMailbox", idMailbox));
			
			Mailbox mailbox = (Mailbox) criteria.uniqueResult();

			// esiste almeno un messaggio in stato PLANNED
			result = mailbox.getMailboxAccount().getIdAccount();

		} catch (Exception e) {
			mLogger.error("Eccezione checkExistMessageToDeleteMailbox", e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return result;
	}
	
	
	public String getIdMailbox() {
		return idMailbox;
	}

	public void setIdMailbox(String idMailbox) {
		this.idMailbox = idMailbox;
	}

	public Boolean getForceDelete() {
		return forceDelete;
	}

	public void setForceDelete(Boolean forceDelete) {
		this.forceDelete = forceDelete;
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

	public String getFolderBackup() {
		return folderBackup;
	}

	public void setFolderBackup(String folderBackup) {
		this.folderBackup = folderBackup;
	}

}
