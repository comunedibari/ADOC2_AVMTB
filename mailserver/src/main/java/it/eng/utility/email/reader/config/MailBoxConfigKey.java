package it.eng.utility.email.reader.config;

public enum MailBoxConfigKey {

	// MAILBOX
	MAILBOX_DELAY("mail.mailbox.delay"), MAILBOX_DELETE_TO_FILE_SYSTEM("mail.mailbox.deletetofilesystem"),
	// Questo resterà per 2 motivi:
	// 1- Retrocompatibilità per il recupero delle mail con le versioni
	// precedenti
	// che non utilizzano lo storage
	// 2- Indica la directory di partenza in cui creare la WorkDir per
	// parcheggiare le mail che vengono processate.
	MAILBOX_DIRECTORY("mail.mailbox.directory"), MAILBOX_FETCH("mail.mailbox.fetch"), MAILBOX_SENT_FOLDER_NAME(
			"mail.mailbox.sentfolder.name"), MAILBOX_AUTOSTART("mail.mailbox.autostart"), MAILBOX_MAX_TRY_NUM_OPERATION(
					"mail.mailbox.maxtrynumoperation"), MAILBOX_MAILCONNECT_ID("mail.mailbox.mailconnectid"), MAILBOX_SEARCH_BY_UID(
							"mail.mailbox.searchbyuid"), MAILBOX_CLEAR_DISCHARGED("mail.mailbox.cleardischarged"), MAILBOX_DELETE_DELAY(
									"mail.mailbox.delete.delay.hour"), MAILBOX_DELETE_FORCE_EXPUNGE(
											"mail.mailbox.delete.force.expunge"), MAILBOX_MAX_MESSAGE_ERROR_TO_PROCESS(
													"mail.mailbox.max.message.error.to.process"), MAILBOX_DELETE_SENT_PEC("mail.mailbox.delete.sent.pec"),
	// 31.07.2017
	// DiegoL: serve per caricare le mail dalla inbox
	MAILBOX_CARICADAFS("mail.mailbox.caricadafs"), MAILBOX_CARICADAFS_DIR("mail.mailbox.caricadafsdir"),
	MAILBOX_AUTO_PROTOCOL("mail.mailbox.auto.protocol");

	private String name;

	private MailBoxConfigKey(String name) {
		this.name = name;
	}

	public String keyname() {
		return this.name;
	}

	public static MailBoxConfigKey getForValue(String name) {
		for (MailBoxConfigKey mess : MailBoxConfigKey.values()) {
			if (mess.name.equals(name)) {
				return mess;
			}
		}
		return null;
	}
}