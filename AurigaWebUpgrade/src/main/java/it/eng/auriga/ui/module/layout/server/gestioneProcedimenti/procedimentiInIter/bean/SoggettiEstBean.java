package it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean;

import it.eng.document.NumeroColonna;

public class SoggettiEstBean {
	
	@NumeroColonna(numero="1")
	private String richiedente;
	@NumeroColonna(numero="3")
	private String denominazione;
	@NumeroColonna(numero="4")
	private String codiceFiscale;
	
	public String getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
}