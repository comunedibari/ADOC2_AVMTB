package it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class EventoCustomXmlBean extends EventoCustomBean {

	// NOTE
	@XmlVariabile(nome = "NOTE", tipo = TipoVariabile.SEMPLICE)
	private String aggiungiNote;
	
	public String getAggiungiNote() {
		return aggiungiNote;
	}
	public void setAggiungiNote(String aggiungiNote) {
		this.aggiungiNote = aggiungiNote;
	}
	
}
