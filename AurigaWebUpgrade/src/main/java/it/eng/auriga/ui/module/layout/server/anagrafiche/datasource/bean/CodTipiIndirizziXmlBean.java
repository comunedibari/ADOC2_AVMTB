package it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author cristiano
 *
 */

public class CodTipiIndirizziXmlBean {

	@NumeroColonna(numero = "1")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
