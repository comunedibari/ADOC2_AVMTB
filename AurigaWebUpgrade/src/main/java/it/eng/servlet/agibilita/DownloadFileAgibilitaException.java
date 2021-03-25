package it.eng.servlet.agibilita;

import javax.servlet.ServletException;

public class DownloadFileAgibilitaException extends ServletException {

	public DownloadFileAgibilitaException() {
		super();
	}

	public DownloadFileAgibilitaException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

	public DownloadFileAgibilitaException(String message) {
		super(message);
	}

	public DownloadFileAgibilitaException(Throwable rootCause) {
		super(rootCause);
	}

}
