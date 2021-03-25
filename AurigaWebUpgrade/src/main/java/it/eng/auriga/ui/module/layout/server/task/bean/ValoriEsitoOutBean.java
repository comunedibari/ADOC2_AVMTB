package it.eng.auriga.ui.module.layout.server.task.bean;

import it.eng.document.NumeroColonna;

public class ValoriEsitoOutBean {

	@NumeroColonna(numero = "1")
	private String valore;

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}
	
}