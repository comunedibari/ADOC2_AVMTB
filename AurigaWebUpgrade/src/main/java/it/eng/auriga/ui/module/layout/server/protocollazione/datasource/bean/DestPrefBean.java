package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;

public class DestPrefBean {

	// 1: UO o SV + ID_UO/ID_SCRIVANIA
	@NumeroColonna(numero = "1")
	private String idDestPref;

	// 2: Livelli
	@NumeroColonna(numero = "2")
	private String codRapido;

	// 3: Denominazione
	@NumeroColonna(numero = "3")
	private String denominazione;

	public String getIdDestPref() {
		return idDestPref;
	}

	public void setIdDestPref(String idDestPref) {
		this.idDestPref = idDestPref;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

}
