package it.eng.auriga.ui.module.layout.server.common;

import it.eng.document.NumeroColonna;

public class DominioBean {
	@NumeroColonna(numero = "1")
	private String tipoIdSpAoo;
	@NumeroColonna(numero = "2")
	private String denominazione;
	public void setTipoIdSpAoo(String tipoIdSpAoo) {
		this.tipoIdSpAoo = tipoIdSpAoo;
	}
	public String getTipoIdSpAoo() {
		return tipoIdSpAoo;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getDenominazione() {
		return denominazione;
	}
	
}
