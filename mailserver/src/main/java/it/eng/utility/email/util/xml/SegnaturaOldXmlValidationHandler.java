package it.eng.utility.email.util.xml;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class SegnaturaOldXmlValidationHandler extends XmlValidationHandler {

	private static String XSD_FILE = "segnatura_old.xsd";
	
	public SegnaturaOldXmlValidationHandler() throws IOException{
		String lString = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(XSD_FILE));
		setXsd(lString);
	}
}
