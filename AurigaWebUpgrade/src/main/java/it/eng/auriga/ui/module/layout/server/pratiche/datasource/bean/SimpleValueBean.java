package it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean;

import it.eng.document.NumeroColonna;

public class SimpleValueBean {
	
	@NumeroColonna(numero = "1")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}