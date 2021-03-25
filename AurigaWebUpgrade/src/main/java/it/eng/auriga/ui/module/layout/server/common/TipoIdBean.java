package it.eng.auriga.ui.module.layout.server.common;

import it.eng.document.NumeroColonna;

public class TipoIdBean {

	@NumeroColonna(numero = "1")
	private String tipo;
	@NumeroColonna(numero = "2")
	private String id;
	
	private Boolean flgMandaNotificaMail;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getFlgMandaNotificaMail() {
		return flgMandaNotificaMail;
	}
	public void setFlgMandaNotificaMail(Boolean flgMandaNotificaMail) {
		this.flgMandaNotificaMail = flgMandaNotificaMail;
	}
	
}