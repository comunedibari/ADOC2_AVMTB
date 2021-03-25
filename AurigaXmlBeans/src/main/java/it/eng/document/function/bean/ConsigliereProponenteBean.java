package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

public class ConsigliereProponenteBean {

	@NumeroColonna(numero = "1")
	private String idSvUt;

	@NumeroColonna(numero = "3")
	private String descrizione;

	@NumeroColonna(numero = "4")
	private Boolean flgFirmatario;

	@NumeroColonna(numero = "5")
	private String cognomeNome;

	public String getIdSvUt() {
		return idSvUt;
	}

	public void setIdSvUt(String idSvUt) {
		this.idSvUt = idSvUt;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Boolean getFlgFirmatario() {
		return flgFirmatario;
	}

	public void setFlgFirmatario(Boolean flgFirmatario) {
		this.flgFirmatario = flgFirmatario;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}

}
