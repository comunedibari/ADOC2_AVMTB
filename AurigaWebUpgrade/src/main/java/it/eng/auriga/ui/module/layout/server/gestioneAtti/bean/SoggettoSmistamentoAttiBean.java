package it.eng.auriga.ui.module.layout.server.gestioneAtti.bean;

import it.eng.document.NumeroColonna;

import java.io.Serializable;

public class SoggettoSmistamentoAttiBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NumeroColonna(numero = "4")
	private String flgUoSv;
	
	@NumeroColonna(numero = "5")
	private String idUoSv;
	
	public String getFlgUoSv() {
		return flgUoSv;
	}
	public void setFlgUoSv(String flgUoSv) {
		this.flgUoSv = flgUoSv;
	}
	public String getIdUoSv() {
		return idUoSv;
	}
	public void setIdUoSv(String idUoSv) {
		this.idUoSv = idUoSv;
	}
		
}
