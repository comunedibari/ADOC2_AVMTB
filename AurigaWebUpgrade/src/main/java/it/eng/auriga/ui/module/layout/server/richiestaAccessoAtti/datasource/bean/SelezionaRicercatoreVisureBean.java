package it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean;

import java.io.Serializable;

import it.eng.auriga.ui.module.layout.server.pratiche.nuovarichaccessoatti.datasource.bean.OperazioneMassivaNuovaRichiestaAccessoAttiBean;

public class SelezionaRicercatoreVisureBean extends OperazioneMassivaVisureBean implements Serializable {
			
	private static final long serialVersionUID = 1L;
		
	private String idUtenteRicercatore;
	
	private String cognomeNomeRicercatore;
	
	public String getIdUtenteRicercatore() {
		return idUtenteRicercatore;
	}

	public void setIdUtenteRicercatore(String idUtenteRicercatore) {
		this.idUtenteRicercatore = idUtenteRicercatore;
	}

	public String getCognomeNomeRicercatore() {
		return cognomeNomeRicercatore;
	}

	public void setCognomeNomeRicercatore(String cognomeNomeRicercatore) {
		this.cognomeNomeRicercatore = cognomeNomeRicercatore;
	}

}