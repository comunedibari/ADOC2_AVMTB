package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import java.io.Serializable;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class SubProfiloBean implements Serializable {

	@NumeroColonna(numero = "1")
	private String idSubProfilo;
	
	@NumeroColonna(numero = "2")
	private String nomeSubProfilo;

	public String getIdSubProfilo() {
		return idSubProfilo;
	}

	public void setIdSubProfilo(String idSubProfilo) {
		this.idSubProfilo = idSubProfilo;
	}

	public String getNomeSubProfilo() {
		return nomeSubProfilo;
	}

	public void setNomeSubProfilo(String nomeSubProfilo) {
		this.nomeSubProfilo = nomeSubProfilo;
	}

}