package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class LegendaOrganigrammaDinamicoBean extends VisualBean {

	@NumeroColonna(numero = "1")
	private String nomeIcona;

	@NumeroColonna(numero = "2")
	private String descrizione;

	public String getNomeIcona() {
		return nomeIcona;
	}

	public void setNomeIcona(String nomeIcona) {
		this.nomeIcona = nomeIcona;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}