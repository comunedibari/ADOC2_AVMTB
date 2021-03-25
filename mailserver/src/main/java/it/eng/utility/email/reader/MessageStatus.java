package it.eng.utility.email.reader;

public enum MessageStatus {

	MESSAGE_DISCHARGED("discharged"), MESSAGE_IN_ERROR_DISCHARGED("discharged_error"), MESSAGE_OPERATION_IN_PROGRESS(
			"operation_in_progress"), MESSAGE_OPERATION_FINISH("operation_finish"), MESSAGE_OPERATION_PLANNED(
					"operation_planned"), MESSAGE_IN_ERROR_OPERATION("operation_error"), MESSAGE_OPERATION_LOCK(
							"operation_lock"), MESSAGE_FORCE_OPERATION("force_operation");

	private String status;

	private MessageStatus(String status) {
		this.status = status;
	}

	public String status() {
		return this.status;
	}

	public static MessageStatus getForValue(String value) {
		for (MessageStatus mess : MessageStatus.values()) {
			if (mess.status.equals(value)) {
				return mess;
			}
		}
		return null;
	}

}
