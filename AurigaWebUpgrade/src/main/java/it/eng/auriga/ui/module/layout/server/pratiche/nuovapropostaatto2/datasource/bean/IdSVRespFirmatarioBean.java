package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

import it.eng.document.NumeroColonna;

public class IdSVRespFirmatarioBean {

	@NumeroColonna(numero = "1")
	private String idSV;

	@NumeroColonna(numero = "2")
	private Boolean flgFirmatario;

	public String getIdSV() {
		return idSV;
	}

	public void setIdSV(String idSV) {
		this.idSV = idSV;
	}

	public Boolean getFlgFirmatario() {
		return flgFirmatario;
	}

	public void setFlgFirmatario(Boolean flgFirmatario) {
		this.flgFirmatario = flgFirmatario;
	}
		
}
