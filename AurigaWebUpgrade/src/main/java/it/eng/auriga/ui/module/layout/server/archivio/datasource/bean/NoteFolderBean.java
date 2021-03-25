package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.List;

public class NoteFolderBean {
	
	@XmlVariabile(nome="#Note", tipo = TipoVariabile.SEMPLICE)
	private String note;

	@XmlVariabile(nome="CAUSALE_AGG_NOTE", tipo = TipoVariabile.LISTA)
	private List<CausaleAggNoteBean> listaCausaleAggNote;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<CausaleAggNoteBean> getListaCausaleAggNote() {
		return listaCausaleAggNote;
	}

	public void setListaCausaleAggNote(List<CausaleAggNoteBean> listaCausaleAggNote) {
		this.listaCausaleAggNote = listaCausaleAggNote;
	}

}
