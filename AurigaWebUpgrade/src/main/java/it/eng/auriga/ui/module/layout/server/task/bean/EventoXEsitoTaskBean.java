package it.eng.auriga.ui.module.layout.server.task.bean;

import it.eng.document.NumeroColonna;

public class EventoXEsitoTaskBean {

	@NumeroColonna(numero = "1")
	private String esito;
	
	@NumeroColonna(numero = "2")
	private String evento;

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}
	
}