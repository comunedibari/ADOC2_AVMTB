package it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean;

import it.eng.document.NumeroColonna;
import java.io.Serializable;


public class CasellaBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String idCasella;
	
	@NumeroColonna(numero = "2")
	private String indirizzoCasella;

	public String getIdCasella() {
		return idCasella;
	}

	public void setIdCasella(String idCasella) {
		this.idCasella = idCasella;
	}

	public String getIndirizzoCasella() {
		return indirizzoCasella;
	}

	public void setIndirizzoCasella(String indirizzoCasella) {
		this.indirizzoCasella = indirizzoCasella;
	}

	
}
