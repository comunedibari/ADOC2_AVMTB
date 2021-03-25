package it.eng.auriga.ui.module.layout.server.registrazioniEmergenza.datasource.bean;

import it.eng.document.NumeroColonna;
import java.io.Serializable;

public class SiglaRegistro implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String sigla;

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
}
