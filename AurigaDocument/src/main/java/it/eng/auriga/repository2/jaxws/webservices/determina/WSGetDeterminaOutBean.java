
package it.eng.auriga.repository2.jaxws.webservices.determina;


import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


public class WSGetDeterminaOutBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.w3c.dom.Document document;
	private List<File> extracted;
	private String xml;
	private String warning;
	
	
	
	public WSGetDeterminaOutBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public org.w3c.dom.Document getDocument() {
		return document;
	}
	public void setDocument(org.w3c.dom.Document document) {
		this.document = document;
	}
	public List<File> getExtracted() {
		return extracted;
	}
	public void setExtracted(List<File> extracted) {
		this.extracted = extracted;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getWarning() {
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}

    
	
	
	}
