package it.eng.utility.email.process.exception;

public class MailServerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -532043390552771444L;

	protected String errMessage;

	protected Exception errException;

	public MailServerException(String message, Exception e) {
		super(message + " " + e.getMessage(), e);
		this.errMessage = message;
		this.errException = e;
	}

	public MailServerException(String message) {
		super(message);
		this.errMessage = message;
	}

}
