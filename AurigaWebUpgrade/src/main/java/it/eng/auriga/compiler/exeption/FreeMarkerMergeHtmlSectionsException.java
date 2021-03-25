package it.eng.auriga.compiler.exeption;


public class FreeMarkerMergeHtmlSectionsException extends Exception {

	private static final long serialVersionUID = -4532202906096679697L;

	public FreeMarkerMergeHtmlSectionsException(String message) {
		super(message);
	}

	public FreeMarkerMergeHtmlSectionsException(String message, Throwable throwable) {
		super(message, throwable);
	}

}