package it.eng.utility.email.reader;

/**
 * Stato delle operazioni
 * 
 * @author michele
 *
 */
public enum OperationStatus {

	OPERATION_IN_PROGRESS("In_progress"), OPERATION_ERROR("error"), OPERATION_FINISH("finish"), OPERATION_PLANNED("planned"), OPERATION_FORCED("forced");

	private String status;

	private OperationStatus(String status) {
		this.status = status;
	}

	public String status() {
		return this.status;
	}

	public static OperationStatus getForValue(String value) {
		for (OperationStatus mess : OperationStatus.values()) {
			if (mess.status.equals(value)) {
				return mess;
			}
		}
		return null;
	}
}
