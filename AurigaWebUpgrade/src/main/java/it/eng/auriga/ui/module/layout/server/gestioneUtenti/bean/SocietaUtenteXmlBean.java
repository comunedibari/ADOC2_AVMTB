package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import it.eng.document.NumeroColonna;


public class SocietaUtenteXmlBean {

	@NumeroColonna(numero="1")
	private String idSocieta;
	

	public String getIdSocieta() {
		return idSocieta;
	}


	public void setIdSocieta(String idSocieta) {
		this.idSocieta = idSocieta;
	}

	
	
}
