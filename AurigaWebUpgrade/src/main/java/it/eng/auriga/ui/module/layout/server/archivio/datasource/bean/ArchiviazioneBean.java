package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import java.io.Serializable;

public class ArchiviazioneBean extends OperazioneMassivaArchivioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String flgInteresseCessato;
		
	public String getFlgInteresseCessato() {
		return flgInteresseCessato;
	}
	public void setFlgInteresseCessato(String flgInteresseCessato) {
		this.flgInteresseCessato = flgInteresseCessato;
	}		
		
}
