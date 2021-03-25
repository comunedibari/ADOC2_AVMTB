package it.eng.auriga.ui.module.layout.server.attributiCustom.datasource;

import it.eng.document.NumeroColonna;

public class OpzioniListaSceltaBean {
	
	@NumeroColonna(numero = "1")
	private String opzione;

	public String getOpzione() {
		return opzione;
	}

	public void setOpzione(String opzione) {
		this.opzione = opzione;
	}

}
