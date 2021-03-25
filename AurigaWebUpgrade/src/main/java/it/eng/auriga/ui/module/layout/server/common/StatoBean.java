package it.eng.auriga.ui.module.layout.server.common;

import it.eng.document.NumeroColonna;

public class StatoBean {

	@NumeroColonna(numero = "1")
	private String codIstatStato;
	@NumeroColonna(numero = "2")
	private String nomeStato;

	public String getCodIstatStato() {
		return codIstatStato;
	}

	public void setCodIstatStato(String codIstatStato) {
		this.codIstatStato = codIstatStato;
	}	
	
	public String getNomeStato() {
		return nomeStato;
	}

	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}
	
}
