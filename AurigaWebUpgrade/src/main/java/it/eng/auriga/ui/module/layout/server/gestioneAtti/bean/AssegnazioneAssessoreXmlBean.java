package it.eng.auriga.ui.module.layout.server.gestioneAtti.bean;

import it.eng.document.NumeroColonna;

public class AssegnazioneAssessoreXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idUser;
	
	@NumeroColonna(numero = "2")
	private String cognomeNome;
	
	@NumeroColonna(numero = "3")
	private String username;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
