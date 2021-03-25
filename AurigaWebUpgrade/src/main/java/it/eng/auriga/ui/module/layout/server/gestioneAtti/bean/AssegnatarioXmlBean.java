package it.eng.auriga.ui.module.layout.server.gestioneAtti.bean;

import it.eng.document.NumeroColonna;

public class AssegnatarioXmlBean {
	
	@NumeroColonna(numero = "1")
	private String ruolo;
	
	@NumeroColonna(numero = "2")
	private String flgUoSv;
	
	@NumeroColonna(numero = "3")
	private String idUoSv;
	
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
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
