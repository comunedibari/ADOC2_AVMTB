package it.eng.document.function.bean.acta;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ActaOutputGetClassificazioneEstesa implements Serializable {

	private static final long serialVersionUID = 1L;

	private ActaEsitoChiamata esitoChiamata;
	private Boolean presenzaIndiceClassificazione;

	public ActaEsitoChiamata getEsitoChiamata() {
		return esitoChiamata;
	}

	public void setEsitoChiamata(ActaEsitoChiamata esitoChiamata) {
		this.esitoChiamata = esitoChiamata;
	}

	public Boolean getPresenzaIndiceClassificazione() {
		return presenzaIndiceClassificazione;
	}

	public void setPresenzaIndiceClassificazione(Boolean presenzaIndiceClassificazione) {
		this.presenzaIndiceClassificazione = presenzaIndiceClassificazione;
	}

	
}
