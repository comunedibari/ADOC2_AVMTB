package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import it.eng.document.NumeroColonna;

public class MansioneBean {
	

	@NumeroColonna(numero="1")
	private String idMansione;
	
	@NumeroColonna(numero="2")
	private String descrizioneMansione;

	public String getIdMansione() {
		return idMansione;
	}

	public void setIdMansione(String idMansione) {
		this.idMansione = idMansione;
	}

	public String getDescrizioneMansione() {
		return descrizioneMansione;
	}

	public void setDescrizioneMansione(String descrizioneMansione) {
		this.descrizioneMansione = descrizioneMansione;
	}
}


