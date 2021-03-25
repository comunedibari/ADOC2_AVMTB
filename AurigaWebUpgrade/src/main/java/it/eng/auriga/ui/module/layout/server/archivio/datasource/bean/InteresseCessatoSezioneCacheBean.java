package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.document.XmlVariabile;

public class InteresseCessatoSezioneCacheBean {
	
	 @XmlVariabile(nome="#FlgInteresseCessato", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgInteresseCessato;

	public String getFlgInteresseCessato() {
		return flgInteresseCessato;
	}

	public void setFlgInteresseCessato(String flgInteresseCessato) {
		this.flgInteresseCessato = flgInteresseCessato;
	}
}
