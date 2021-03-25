package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import it.eng.document.NumeroColonna;


public class GruppoClientiBean {

	@NumeroColonna(numero="1")
	private String idGruppoClienti;
	
	@NumeroColonna(numero="2")
	private String denominazioneGruppoClienti;

	public String getIdGruppoClienti() {
		return idGruppoClienti;
	}

	public void setIdGruppoClienti(String idGruppoClienti) {
		this.idGruppoClienti = idGruppoClienti;
	}

	public String getDenominazioneGruppoClienti() {
		return denominazioneGruppoClienti;
	}

	public void setDenominazioneGruppoClienti(String denominazioneGruppoClienti) {
		this.denominazioneGruppoClienti = denominazioneGruppoClienti;
	}

	
}
