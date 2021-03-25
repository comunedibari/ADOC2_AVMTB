package it.eng.gd.lucenemanager.exception;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class DocumentReaderException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5232942496988301062L;
	protected String errMessage;
	protected Exception errException;

	public DocumentReaderException(String message, Exception e) {
		super(message + " " + e.getMessage(), e);
		this.errMessage = message;
		this.errException = e;
	}

	public DocumentReaderException(String message) {
		super(message);
		this.errMessage = message;
	}

}
