package it.eng.utility.storageutil.exception;

public class FileHelperException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileHelperException() {
		super();
	}

	public FileHelperException(String message) {
		super(message);
	}

	public FileHelperException(Throwable cause) {
		super(cause);
	}

	public FileHelperException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileHelperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
