package it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author cristiano
 *
 */

public class EsitoOperazioneSuRichAccessoAttiXmlBean {

	@NumeroColonna(numero = "1")
	private String idUd;

	@NumeroColonna(numero = "2")
	private String datiPrincipaliRichiesta;

	@NumeroColonna(numero = "3")
	private String esitoOperazione;

	@NumeroColonna(numero = "4")
	private String messaggioErrore;

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getDatiPrincipaliRichiesta() {
		return datiPrincipaliRichiesta;
	}

	public void setDatiPrincipaliRichiesta(String datiPrincipaliRichiesta) {
		this.datiPrincipaliRichiesta = datiPrincipaliRichiesta;
	}

	public String getEsitoOperazione() {
		return esitoOperazione;
	}

	public void setEsitoOperazione(String esitoOperazione) {
		this.esitoOperazione = esitoOperazione;
	}

	public String getMessaggioErrore() {
		return messaggioErrore;
	}

	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}

}
