package it.eng.utility.email.util.xml;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class SegnaturaNewXmlValidationHandler extends XmlValidationHandler {

	private static String XSD_FILE = "segnatura_con_agg_2013.xsd";

	public SegnaturaNewXmlValidationHandler() throws IOException {
		String lString = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(XSD_FILE));
		setXsd(lString);
	}
}
