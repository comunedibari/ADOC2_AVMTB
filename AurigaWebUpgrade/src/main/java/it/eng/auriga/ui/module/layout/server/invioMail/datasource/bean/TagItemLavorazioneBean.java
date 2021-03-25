package it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean;

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class TagItemLavorazioneBean extends VisualBean {

	@NumeroColonna(numero = "1")
	private String key;
	@NumeroColonna(numero = "2")
	private String value;
	@NumeroColonna(numero = "4")
	private Integer flgNoteObbl;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getFlgNoteObbl() {
		return flgNoteObbl;
	}

	public void setFlgNoteObbl(Integer flgNoteObbl) {
		this.flgNoteObbl = flgNoteObbl;
	}

}
