package it.eng.database.store.exception;

public class StoredProcException extends Exception {

	private static final long serialVersionUID = -6261512583619064923L;

	public StoredProcException() {
		super();
	}

	public StoredProcException(String message) {
		super(message);
	}

	public StoredProcException(Throwable cause) {
		super(cause);
	}

	public StoredProcException(String message, Throwable cause) {
		super(message, cause);
	}

	public StoredProcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
