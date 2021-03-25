package it.eng.utility.email.process.exception;

public class ManyDeletedException extends MailServerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5246604835448887070L;

	public ManyDeletedException(String message) {
		super(message);
	}

	public ManyDeletedException(String message, Exception e) {
		super(message, e);
	}

}
