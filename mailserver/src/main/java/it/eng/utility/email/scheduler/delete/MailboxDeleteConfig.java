package it.eng.utility.email.scheduler.delete;

public class MailboxDeleteConfig {

	/**
	 * Bean di configurazione specifico della cancellazione asincrona
	 */

	private String idMailbox;
	private String cronExpression; // schedulazione specifica della mailbox, se si vuole disattivare momentaneamente la cancellazione è sufficiente impostare
									// una cron expression particolare

	private Boolean forceDelete; // forzo la cancellazione della mail anche se non è prevista una operazione di cancellazione specifica per la mailbox o per la
									// signola mail

	private Integer limit; // numero massimo di cancellazioni da effettuare

	private Boolean enableBackup; // abilito il backup del messaggio in una folder di appoggio prima di effettuare la cancellazione
	private String folderBackup; // nome della folder di backup, se non è presente la crea

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
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
