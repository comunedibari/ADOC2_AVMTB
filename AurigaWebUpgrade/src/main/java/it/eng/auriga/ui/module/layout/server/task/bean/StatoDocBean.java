package it.eng.auriga.ui.module.layout.server.task.bean;

import it.eng.document.NumeroColonna;

public class StatoDocBean {

	@NumeroColonna(numero = "1")
	private String codStato;

	@NumeroColonna(numero = "2")
	private String nomeStato;
	
	@NumeroColonna(numero = "3")
	private String flgGeneraFileUnione; 

	public String getCodStato() {
		return codStato;
	}

	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}

	public String getNomeStato() {
		return nomeStato;
	}

	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}

	public String getFlgGeneraFileUnione() {
		return flgGeneraFileUnione;
	}

	public void setFlgGeneraFileUnione(String flgGeneraFileUnione) {
		this.flgGeneraFileUnione = flgGeneraFileUnione;
	}
	
}