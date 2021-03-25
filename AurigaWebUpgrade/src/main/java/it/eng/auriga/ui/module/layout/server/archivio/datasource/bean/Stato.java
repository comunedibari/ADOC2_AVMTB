package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.document.NumeroColonna;
import java.io.Serializable;

public class Stato implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String codice;
	@NumeroColonna(numero = "2")
	private String descrizione;
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
