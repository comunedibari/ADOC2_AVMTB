package it.eng.aurigamailbusiness.utility.cryptography;

public class InvalidEncryptionKeyException extends Exception {

	private static final long serialVersionUID = -4532202906096679697L;

	public InvalidEncryptionKeyException(String message) {
		super(message);
	}

	public InvalidEncryptionKeyException(String message, Throwable throwable) {
		super(message, throwable);
	}

}