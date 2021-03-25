package it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean;

import it.eng.document.NumeroColonna;

public class OrganigrammaSuccessoreXmlBean {

	@NumeroColonna(numero = "1")
	private String tipoAzione;

	@NumeroColonna(numero = "2")
	private String idUo;

	@NumeroColonna(numero = "3")
	private String codice;

	@NumeroColonna(numero = "4")
	private String descrizione;

	public String getTipoAzione() {
		return tipoAzione;
	}

	public void setTipoAzione(String tipoAzione) {
		this.tipoAzione = tipoAzione;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

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