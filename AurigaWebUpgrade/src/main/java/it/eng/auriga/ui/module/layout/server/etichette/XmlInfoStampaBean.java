package it.eng.auriga.ui.module.layout.server.etichette;

import java.io.Serializable;

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

public class XmlInfoStampaBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String nroEtichetta;
	
	@NumeroColonna(numero = "2")
	private String nroAllegato;
	
	@NumeroColonna(numero = "3")
	private String notazioneCopiaOriginale;

	@NumeroColonna(numero = "4")
	private String flgRicevutaXMittente;

	public String getNroEtichetta() {
		return nroEtichetta;
	}

	public void setNroEtichetta(String nroEtichetta) {
		this.nroEtichetta = nroEtichetta;
	}

	public String getNroAllegato() {
		return nroAllegato;
	}

	public void setNroAllegato(String nroAllegato) {
		this.nroAllegato = nroAllegato;
	}

	public String getNotazioneCopiaOriginale() {
		return notazioneCopiaOriginale;
	}

	public void setNotazioneCopiaOriginale(String notazioneCopiaOriginale) {
		this.notazioneCopiaOriginale = notazioneCopiaOriginale;
	}
	
	public String getFlgRicevutaXMittente() {
		return flgRicevutaXMittente;
	}

	public void setFlgRicevutaXMittente(String flgRicevutaXMittente) {
		this.flgRicevutaXMittente = flgRicevutaXMittente;
	}

}
