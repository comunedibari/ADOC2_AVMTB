package it.eng.auriga.module.business.dao.exception;

/**
 * Classe specifica che indica credenziali non valide
 * 
 * @author Mattia Zanetti
 *
 */

public class DmvConsumerInvalidCredentialException extends Exception {

	public DmvConsumerInvalidCredentialException() {
	}

	public DmvConsumerInvalidCredentialException(String message) {
		super(message);
	}

	public DmvConsumerInvalidCredentialException(Throwable cause) {
		super(cause);
	}

	public DmvConsumerInvalidCredentialException(String message, Throwable cause) {
		super(message, cause);
	}

}
