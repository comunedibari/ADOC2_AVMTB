package it.eng.utility.email.reader.info;

public enum MailBoxInfoKey {

	MAILBOX_LAST_MESSAGE_UID("mail.mailbox.info.lastmessageuid"), MAILBOX_UID_VALIDITY("mail.mailbox.info.uidvalidity"), MAILBOX_LAST_SEARCH_NEW_MESSAGE_OK(
			"mail.mailbox.info.last.receive.ok"), MAILBOX_LAST_CONNECTION_RECEIVE_ERROR(
					"mail.mailbox.info.receive.last.error.date"), MAILBOX_LAST_DELETE_SCHEDULED(
							"mail.mailbox.info.delete.last.scheduled"), MAILBOX_LAST_CONNECTION_RECEIVE_ERROR_TYPE(
									"mail.mailbox.info.receive.last.error.type"), MAILBOX_QUOTA_USAGE("mail.mailbox.info.quota.usage");

	private String name;

	private MailBoxInfoKey(String name) {
		this.name = name;
	}

	public String keyname() {
		return this.name;
	}

	public static MailBoxInfoKey getForValue(String name) {
		for (MailBoxInfoKey mess : MailBoxInfoKey.values()) {
			if (mess.name.equals(name)) {
				return mess;
			}
		}
		return null;
	}
}