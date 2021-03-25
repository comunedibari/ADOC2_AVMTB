package it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean;

import it.eng.document.NumeroColonna;

public class FirmatarioXmlBean {
	
	@NumeroColonna(numero = "1")
	private String firmatario;

	public String getFirmatario() {
		return firmatario;
	}
	
	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}
	
}