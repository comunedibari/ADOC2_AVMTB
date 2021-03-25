package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class CIGBean {
	
	@NumeroColonna(numero = "1")
	private String codiceCIG;

	public String getCodiceCIG() {
		return codiceCIG;
	}

	public void setCodiceCIG(String codiceCIG) {
		this.codiceCIG = codiceCIG;
	}
	
}