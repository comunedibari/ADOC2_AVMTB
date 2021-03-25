package it.eng.auriga.repository2.jaxws.webservices.common;

import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class LogMessageHandler implements SOAPHandler<SOAPMessageContext> {

	public boolean handleMessage(SOAPMessageContext messageContext) {
		log(messageContext);
		return true;
	}

	public Set<QName> getHeaders() {
		return Collections.EMPTY_SET;
	}

	public boolean handleFault(SOAPMessageContext messageContext) {
		return true;
	}

	public void close(MessageContext context) {
	}

	private void log(SOAPMessageContext messageContext) {
		
	}
}