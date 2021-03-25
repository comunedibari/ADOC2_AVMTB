package it.eng.auriga.repository2.jaxws.webservices.updunitadoc;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSUpdUdOutBean implements Serializable {

	
	private String xmlRegOut;
	private String warnRegOut;
	public String getXmlRegOut() {
		return xmlRegOut;
	}
	public void setXmlRegOut(String xmlRegOut) {
		this.xmlRegOut = xmlRegOut;
	}
	public String getWarnRegOut() {
		return warnRegOut;
	}
	public void setWarnRegOut(String warnRegOut) {
		this.warnRegOut = warnRegOut;
	}
		
	}
