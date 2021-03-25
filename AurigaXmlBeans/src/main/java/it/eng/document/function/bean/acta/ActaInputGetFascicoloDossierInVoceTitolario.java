package it.eng.document.function.bean.acta;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActaInputGetFascicoloDossierInVoceTitolario implements Serializable {

	private static final long serialVersionUID = 1L;

	private String aooCode;
	private String structurCode;
	private String descrizioneVoceTitolario;
	private String codiceFascicoloDossier;
	private String suffissoCodiceFascicoloDossier;

	public String getAooCode() {
		return aooCode;
	}

	public void setAooCode(String aooCode) {
		this.aooCode = aooCode;
	}

	public String getStructurCode() {
		return structurCode;
	}

	public void setStructurCode(String structurCode) {
		this.structurCode = structurCode;
	}

	public String getDescrizioneVoceTitolario() {
		return descrizioneVoceTitolario;
	}

	public void setDescrizioneVoceTitolario(String descrizioneVoceTitolario) {
		this.descrizioneVoceTitolario = descrizioneVoceTitolario;
	}

	public String getCodiceFascicoloDossier() {
		return codiceFascicoloDossier;
	}

	public void setCodiceFascicoloDossier(String codiceFascicoloDossier) {
		this.codiceFascicoloDossier = codiceFascicoloDossier;
	}

	public String getSuffissoCodiceFascicoloDossier() {
		return suffissoCodiceFascicoloDossier;
	}

	public void setSuffissoCodiceFascicoloDossier(String suffissoCodiceFascicoloDossier) {
		this.suffissoCodiceFascicoloDossier = suffissoCodiceFascicoloDossier;
	}

}
