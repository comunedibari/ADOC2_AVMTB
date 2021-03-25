package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.document.NumeroColonna;
import java.io.Serializable;

public class StatoRichAnnullamentoReg implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String stato;

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
}
