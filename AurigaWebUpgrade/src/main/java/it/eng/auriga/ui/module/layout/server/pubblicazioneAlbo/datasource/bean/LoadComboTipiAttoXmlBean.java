package it.eng.auriga.ui.module.layout.server.pubblicazioneAlbo.datasource.bean;

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class LoadComboTipiAttoXmlBean extends VisualBean {

	@NumeroColonna(numero = "1")
	private String key;
	@NumeroColonna(numero = "2")
	private String value;
	@NumeroColonna(numero = "12")
	private String giorniPubblicazione;

	public String getGiorniPubblicazione() {
		return giorniPubblicazione;
	}

	public void setGiorniPubblicazione(String giorniPubblicazione) {
		this.giorniPubblicazione = giorniPubblicazione;
	}

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
}