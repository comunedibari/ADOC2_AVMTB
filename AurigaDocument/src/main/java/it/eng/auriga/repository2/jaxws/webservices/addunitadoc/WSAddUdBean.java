package it.eng.auriga.repository2.jaxws.webservices.addunitadoc;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSAddUdBean implements Serializable {

	
	private String xml;
	private String docAttachXML;
	private String regNumDaRichASistEst;
	public String getXml() {
		return xml;
	}
	public String getDocAttachXML() {
		return docAttachXML;
	}
	public String getRegNumDaRichASistEst() {
		return regNumDaRichASistEst;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public void setDocAttachXML(String docAttachXML) {
		this.docAttachXML = docAttachXML;
	}
	public void setRegNumDaRichASistEst(String regNumDaRichASistEst) {
		this.regNumDaRichASistEst = regNumDaRichASistEst;
	}
	


	

	}
