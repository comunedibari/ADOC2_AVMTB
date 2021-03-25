package it.eng.utility;

import javax.servlet.http.HttpSession;

public interface FormatConverterInterface {

	public boolean isValidFormat(String mimetype, HttpSession session);
	
}
