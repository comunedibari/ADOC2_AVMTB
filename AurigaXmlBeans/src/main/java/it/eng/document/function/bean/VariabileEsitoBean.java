package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

public class VariabileEsitoBean {
	
	@NumeroColonna(numero = "1")
	private String nome;
	
	@NumeroColonna(numero = "2")
	private String esito;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

}
