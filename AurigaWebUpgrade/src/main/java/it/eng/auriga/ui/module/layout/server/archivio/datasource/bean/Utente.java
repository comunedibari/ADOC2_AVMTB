package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.document.NumeroColonna;
import java.io.Serializable;


public class Utente implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String idUser;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
}
