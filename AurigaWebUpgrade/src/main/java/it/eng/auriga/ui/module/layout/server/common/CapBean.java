package it.eng.auriga.ui.module.layout.server.common;

import it.eng.document.NumeroColonna;

public class CapBean {

	@NumeroColonna(numero = "1")
	private String cap;	
	private String frazione;	
	private String nomeComune;
	
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getFrazione() {
		return frazione;
	}
	public void setFrazione(String frazione) {
		this.frazione = frazione;
	}
	public String getNomeComune() {
		return nomeComune;
	}
	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}
	
}
