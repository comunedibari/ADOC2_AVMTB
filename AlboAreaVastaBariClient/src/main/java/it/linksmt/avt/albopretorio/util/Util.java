package it.linksmt.avt.albopretorio.util;

import java.io.StringWriter;
import java.net.SocketTimeoutException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import it.linksmt.avt.albopretorio.data.Esito;

public class Util {
	
	public static final void aggiornaEsito(final Esito output, Exception e) {
		final Throwable cause = e.getCause();
		output.setRispostaNonRicevuta(true);
		output.setTimeout(cause instanceof SocketTimeoutException);
		output.setMessaggio(cause != null ? cause.getLocalizedMessage() : e.getLocalizedMessage());
		output.setOk(false);
	}
	
	public static String convertBeanToXml(Object object) throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Print XML String to Console
			StringWriter sw = new StringWriter();
			// Write XML to StringWriter
			jaxbMarshaller.marshal(object, sw);
			// Verify XML Content
			String xmlContent = sw.toString();

			return xmlContent;
		} catch (Exception e) {
			throw new Exception("Errore nella conversione dell'bean in xml");
		}
	}
	
}
