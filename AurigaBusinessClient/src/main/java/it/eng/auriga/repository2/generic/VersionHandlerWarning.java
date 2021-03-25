package it.eng.auriga.repository2.generic;

public class VersionHandlerWarning extends VersionHandlerException {

	public VersionHandlerWarning(String message) {
		super(message);
	}

	public VersionHandlerWarning(int innerCode, String message) {
		super(innerCode, message);
	}

}
