package it.eng.document.function.bean.editororganigramma;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class RuoloOrganigrammaXmlBean {
	
	@NumeroColonna(numero = "1")
	private String key;

	@NumeroColonna(numero = "2")
	private String value;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}