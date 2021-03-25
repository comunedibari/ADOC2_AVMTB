package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

public class FirmatariModelloDispositivoOutBean {

	@NumeroColonna(numero = "1")
	private String denominazione;

	@NumeroColonna(numero = "2")
	private String ruolo;

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

}
