package it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class PresenzeOdgXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idUser;
	
	@NumeroColonna(numero = "2")
	private String denominazione;

	@NumeroColonna(numero = "3")
	private String incarico;
	
	@NumeroColonna(numero = "4")
	private String ruolo;
	
	@NumeroColonna(numero = "5")
	private String flgPresenza;
	
	@NumeroColonna(numero = "6")
	private String voto;   //Valori possibili: astenuto, favorevole, contrario.
	
	@NumeroColonna(numero = "7")
	private String destinatari;   //Valori possibili: astenuto, favorevole, contrario.
	
	@NumeroColonna(numero = "8")
	private String idRubrica;  
	
	// Valori utilizzati solamente per creare la SezioneCache da passare al modello (non vengono utilizati da store)
	private String presenzaCodificata;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getIncarico() {
		return incarico;
	}

	public void setIncarico(String incarico) {
		this.incarico = incarico;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getFlgPresenza() {
		return flgPresenza;
	}

	public void setFlgPresenza(String flgPresenza) {
		this.flgPresenza = flgPresenza;
	}
	
	public String getPresenzaCodificata() {
		return presenzaCodificata;
	}
	
	public void setPresenzaCodificata(String presenzaCodificata) {
		this.presenzaCodificata = presenzaCodificata;
	}

	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	public String getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}

	public String getIdRubrica() {
		return idRubrica;
	}

	public void setIdRubrica(String idRubrica) {
		this.idRubrica = idRubrica;
	}
	
}