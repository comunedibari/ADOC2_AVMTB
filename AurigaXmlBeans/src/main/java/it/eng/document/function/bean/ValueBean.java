package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

public class ValueBean {
	
	@NumeroColonna(numero = "1")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}