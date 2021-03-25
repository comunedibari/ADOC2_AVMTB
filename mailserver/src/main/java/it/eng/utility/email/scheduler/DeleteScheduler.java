package it.eng.utility.email.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.quartz.CronExpression;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxConfig;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.bean.MailConfigurator;
import it.eng.utility.email.bean.MailUiConfigurator;
import it.eng.utility.email.reader.MailBoxStatus;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.scheduler.delete.DeleteService;
import it.eng.utility.email.scheduler.delete.MailboxDeleteConfig;

public class DeleteScheduler {

	private static Logger mLogger = LogManager.getLogger(DeleteScheduler.class);

	// configurazioni generali per l'operazione di cancellazione asincrona
	// valide per tutte le mailbox attive

	private String cronExpression;
	private Boolean forceDelete; // forzo la cancellazione della mail anche se non è prevista una operazione di cancellazione specifica per la mailbox o per la
									// signola mail

	private Integer limit; // numero massimo di messaggi da cancellare
	private Boolean enableBackup; // abilito il backup del messaggio in una folder di appoggio prima di effettuare la cancellazione
	private String folderBackup; // nome della folder di backup, se non è presente la crea

	// configurazioni specifiche per le singole mailbox, che sovrascrivono quelle generali
	private List<MailboxDeleteConfig> mailboxDeleteConfig;

	// mappa concorrente per gestire i thread di cancellazione attualmente attivi e evitare di avviarne altri se sono già in corso per una mailbox
	private static ConcurrentHashMap<String, Boolean> mailboxThreadDeleteState = new ConcurrentHashMap<String, Boolean>();

	/**
	 * Verifica se è attivo (in corso) il thread dedicato alla cancellazione dell mailbox avente id {@link idMailbox}
	 * 
	 * @param idMailbox
	 * @return
	 * @throws Exception
	 */
	public static Boolean getThreadStateDeleteForMailbox(String idMailbox) throws Exception {

		Boolean result = false;

		if (mailboxThreadDeleteState != null && idMailbox != null && mailboxThreadDeleteState.get(idMailbox) != null) {
			result = mailboxThreadDeleteState.get(idMailbox);
		}

		return result;

	}

	/**
	 * Aggiorna lo stato del thread dedicato alla cancellazione dell mailbox avente id {@link idMailbox}
	 * 
	 * @param idMailbox
	 * @param value
	 * @throws Exception
	 */

	public static void setThreadStateDeleteForMailbox(String idMailbox, Boolean value) throws Exception {
		if (mailboxThreadDeleteState == null) {
			mailboxThreadDeleteState = new ConcurrentHashMap<String, Boolean>();
		}
		mailboxThreadDeleteState.put(idMailbox, value);

	}

	/**
	 * Metodo che avvia i thread per la cancellazione delle mailbox in modalità asincrona
	 */

	public void startDeleteScheduler() {

		if (mailboxThreadDeleteState == null) {
			mailboxThreadDeleteState = new ConcurrentHashMap<String, Boolean>();
		}

		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize();
		// recupero la lista di id mailbox con configurazione specifica

		if (getMailboxDeleteConfig() != null && !getMailboxDeleteConfig().isEmpty()) {

			for (MailboxDeleteConfig mailBoxConfig : getMailboxDeleteConfig()) {

				try {

					// il thread non è ancora attivo
					setThreadStateDeleteForMailbox(mailBoxConfig.getIdMailbox(), false);

					// configurazione specifica per la mailbox
					// se le proprietà specifiche non sono impostate valgono quelle generali
					DeleteService lDeleteService = new DeleteService();
					lDeleteService.setIdMailbox(mailBoxConfig.getIdMailbox());
					lDeleteService.setForceDelete(mailBoxConfig.getForceDelete() != null ? mailBoxConfig.getForceDelete() : this.getForceDelete());
					lDeleteService.setLimit(mailBoxConfig.getLimit() != null ? mailBoxConfig.getLimit() : this.getLimit());
					lDeleteService.setEnableBackup(mailBoxConfig.getEnableBackup() != null ? mailBoxConfig.getEnableBackup() : this.getEnableBackup());
					lDeleteService.setFolderBackup(
							StringUtils.isNotBlank(mailBoxConfig.getFolderBackup()) ? mailBoxConfig.getFolderBackup() : this.getFolderBackup());

					// orario specifico per la cancellazione
					String cronExpression = StringUtils.isNotBlank(mailBoxConfig.getCronExpression()) ? mailBoxConfig.getCronExpression()
							: this.getCronExpression();
					if (StringUtils.isBlank(cronExpression)) {
						throw new Exception("Cron expression non valorizzata per la cancellazone della mailbox " + mailBoxConfig.getIdMailbox());
					}
					mLogger.info("Schedulata la cancellazione della mailbox " + mailBoxConfig.getIdMailbox());
					mLogger.info("CronExpression: " + cronExpression);
					CronExpression cronExpr = new CronExpression(cronExpression);
					Date nextScheduled = cronExpr.getNextValidTimeAfter(new Date());
					mLogger.info("Prima schedulazione: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(nextScheduled));
					CronTrigger cronTrigger = new CronTrigger(cronExpression);
					scheduler.schedule(lDeleteService, cronTrigger);
				} catch (Exception e) {
					mLogger.error("Eccezione creazione thread", e);
				}

			}
		} else {

			try {

				// setto la dimensione del pool
				scheduler.setPoolSize(this.getIdMailBoxToDelete().size());

				// recupero tutte le mailbox attive e schedulo un thread per ognuna

				List<String> listaIdMailbox = this.getIdMailBoxToDelete();

				for (String idMailbox : listaIdMailbox) {

					try {

						setThreadStateDeleteForMailbox(idMailbox, false);

						// configurazioni generali per tutte le mailbox
						DeleteService lDeleteService = new DeleteService();
						lDeleteService.setForceDelete(getForceDelete());
						lDeleteService.setLimit(getLimit());
						lDeleteService.setEnableBackup(getEnableBackup());
						lDeleteService.setFolderBackup(getFolderBackup());
						lDeleteService.setIdMailbox(idMailbox);

						if (StringUtils.isBlank(getCronExpression())) {
							throw new Exception("Cron expression non valorizzata per la cancellazone delle mailbox");
						}
						scheduler.schedule(lDeleteService, new CronTrigger(getCronExpression()));
						mLogger.info("Schedulata la cancellazione della mailbox " + idMailbox);
						mLogger.info("CronExpression: " + cronExpression);
						CronExpression cronExpr = new CronExpression(cronExpression);
						Date nextScheduled = cronExpr.getNextValidTimeAfter(new Date());
						mLogger.info("Prima schedulazione: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(nextScheduled));
					} catch (Exception e) {
						mLogger.error("Eccezione DeleteService per la mailbox " + idMailbox + ":", e);
					}
				}

			} catch (Exception e) {
				mLogger.error("Eccezione DeleteService: " + e.getMessage(), e);

			}

		}
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Boolean getForceDelete() {
		return forceDelete;
	}

	public void setForceDelete(Boolean forceDelete) {
		this.forceDelete = forceDelete;
	}

	public List<MailboxDeleteConfig> getMailboxDeleteConfig() {
		return mailboxDeleteConfig;
	}

	public void setMailboxDeleteConfig(List<MailboxDeleteConfig> mailboxDeleteConfig) {
		this.mailboxDeleteConfig = mailboxDeleteConfig;
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

	/**
	 * Metodo che restituisce le caselle attive e configurate per il server
	 * 
	 * @return
	 * @throws MessagingException
	 */

	public List<String> getIdMailBoxToDelete() throws Exception {

		Session session = null;

		List<String> listaIdMailbox = new ArrayList<String>();

		try {
			try {
				session = SubjectInitializer.getSession(null); // HibernateUtil.begin();
			} catch (Exception e) {
				throw new MessagingException(e.getMessage(), e);
			}

			ProjectionList projection = Projections.projectionList().add(Projections.groupProperty("mbox.idMailbox"), "idMailbox");

			Criteria criteria = session.createCriteria(Mailbox.class, "mbox");

			criteria.add(Restrictions.eq("mbox.status", MailBoxStatus.MAILBOX_ACTIVE.status()));
			if (MailConfigurator.getCaselle() != null && MailConfigurator.getCaselle().size() > 0) {
				criteria.add(Restrictions.in("mbox.idMailbox", MailConfigurator.getCaselle()));
			}

			// la mailbox deve essere associata al server
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MailboxConfig.class, "config")
					.add(Restrictions.eq("config.id.configKey", MailBoxConfigKey.MAILBOX_MAILCONNECT_ID.keyname()))
					.add(Restrictions.eq("config.configValue", MailUiConfigurator.getMailConnectId()))
					.add(Property.forName("config.id.idMailbox").eqProperty("mbox.idMailbox"));

			criteria.add(Subqueries.exists(detachedCriteria.setProjection(Projections.property("config.id.configKey"))));

			listaIdMailbox = criteria.setProjection(projection).list();

		} catch (Exception e) {
			mLogger.error("Eccezione getIdMailBoxToDelete", e);
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		return listaIdMailbox;
	}

}
