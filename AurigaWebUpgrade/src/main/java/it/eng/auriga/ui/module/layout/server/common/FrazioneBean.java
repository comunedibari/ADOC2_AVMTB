package it.eng.auriga.ui.module.layout.server.common;

import it.eng.document.NumeroColonna;

public class FrazioneBean {

	@NumeroColonna(numero = "1")
	private String frazione;	
	@NumeroColonna(numero = "2")
	private String nomeComune;

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
