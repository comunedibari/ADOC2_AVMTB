package it.eng.document.function.bean.acta;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ActaOutputGetFascicoloDossierInVoceTitolario implements Serializable {

	private static final long serialVersionUID = 1L;

	private ActaEsitoChiamata esitoChiamata;
	private String idFascicoloDossier;

	public String getIdFascicoloDossier() {
		return idFascicoloDossier;
	}

	public void setIdFascicoloDossier(String idFascicoloDossier) {
		this.idFascicoloDossier = idFascicoloDossier;
	}

	public ActaEsitoChiamata getEsitoChiamata() {
		return esitoChiamata;
	}

	public void setEsitoChiamata(ActaEsitoChiamata esitoChiamata) {
		this.esitoChiamata = esitoChiamata;
	}

}
