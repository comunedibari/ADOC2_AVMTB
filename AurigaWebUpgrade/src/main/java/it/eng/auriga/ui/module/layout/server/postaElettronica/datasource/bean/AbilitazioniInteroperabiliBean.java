package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean;

public class AbilitazioniInteroperabiliBean {

	private boolean conferma;
	private boolean eccezione;
	private boolean aggiornamento;
	private boolean annullamento;
	private boolean protocolla;
	public boolean isConferma() {
		return conferma;
	}
	public void setConferma(boolean conferma) {
		this.conferma = conferma;
	}
	public boolean isEccezione() {
		return eccezione;
	}
	public void setEccezione(boolean eccezione) {
		this.eccezione = eccezione;
	}
	public boolean isAggiornamento() {
		return aggiornamento;
	}
	public void setAggiornamento(boolean aggiornamento) {
		this.aggiornamento = aggiornamento;
	}
	public boolean isAnnullamento() {
		return annullamento;
	}
	public void setAnnullamento(boolean annullamento) {
		this.annullamento = annullamento;
	}
	public boolean isProtocolla() {
		return protocolla;
	}
	public void setProtocolla(boolean protocolla) {
		this.protocolla = protocolla;
	}
}
