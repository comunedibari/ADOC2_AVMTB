package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import java.util.Date;

public class AltroRifBean {

	private String registroTipoRif;
	private String numero;
	private String anno;
	private Date data;
	private String note;

	public String getRegistroTipoRif() {
		return registroTipoRif;
	}

	public void setRegistroTipoRif(String registroTipoRif) {
		this.registroTipoRif = registroTipoRif;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
