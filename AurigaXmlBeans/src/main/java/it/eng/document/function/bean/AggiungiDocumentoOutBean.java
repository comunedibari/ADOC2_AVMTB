package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AggiungiDocumentoOutBean implements Serializable {

	private static final long serialVersionUID = 1627081482511256712L;

	private String uri;
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
