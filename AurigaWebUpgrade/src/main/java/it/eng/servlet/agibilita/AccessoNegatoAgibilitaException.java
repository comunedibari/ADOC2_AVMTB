package it.eng.servlet.agibilita;

import javax.servlet.ServletException;

public class AccessoNegatoAgibilitaException extends ServletException {

	public AccessoNegatoAgibilitaException() {
		super();
	}

	public AccessoNegatoAgibilitaException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

	public AccessoNegatoAgibilitaException(String message) {
		super(message);
	}

	public AccessoNegatoAgibilitaException(Throwable rootCause) {
		super(rootCause);
	}

}
