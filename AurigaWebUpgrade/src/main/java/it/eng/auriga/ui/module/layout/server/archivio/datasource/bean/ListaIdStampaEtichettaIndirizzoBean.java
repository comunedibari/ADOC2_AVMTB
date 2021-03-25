package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.document.NumeroColonna;

public class ListaIdStampaEtichettaIndirizzoBean {
	
	@NumeroColonna(numero = "1")
	private String idDocumento;

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	

}
