package it.eng.auriga.ui.module.layout.server.stampaRegProt.bean;

import it.eng.document.NumeroColonna;

public class XmlLIstaStampaPropostaAttoBean {
	
	@NumeroColonna(numero="6")
	private String sigla;
	@NumeroColonna(numero="5")
	private String descrizione;
	 
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	 
}
