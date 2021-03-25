package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

public class KeyValueBean {
	
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