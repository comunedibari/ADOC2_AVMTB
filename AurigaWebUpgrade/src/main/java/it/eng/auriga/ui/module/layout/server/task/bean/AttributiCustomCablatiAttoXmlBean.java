package it.eng.auriga.ui.module.layout.server.task.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class AttributiCustomCablatiAttoXmlBean implements Serializable {

	@NumeroColonna(numero = "1")
	private String attrName;

	@NumeroColonna(numero = "2")
	private String attrLabel;

	@NumeroColonna(numero = "3")
	private String flgObbligatorio;

	@NumeroColonna(numero = "4")
	private String maxNumValori;

	@NumeroColonna(numero = "5")
	private String valoriPossibili;

	@NumeroColonna(numero = "6")
	private String tipoLoadCombo;

	@NumeroColonna(numero = "7")
	private String altriParametriLoadCombo;

	@NumeroColonna(numero = "8")
	private String flgSoloVldLoadCombo;

	@NumeroColonna(numero = "9")
	private String valoreFisso;

	@NumeroColonna(numero = "10")
	private String flgEditabile;
	
	@NumeroColonna(numero = "11")
	private String flgMostraFirmatario;

	@NumeroColonna(numero = "12")
	private String flgMostraMotivi;
	
	@NumeroColonna(numero = "13")
	private String altezzaInRighe;
	
	@NumeroColonna(numero = "14")
	private String decodificheValoriPossibili;
	
	@NumeroColonna(numero = "16")
	private String flgMostraVistoInRitornoIter;
	
	
	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrLabel() {
		return attrLabel;
	}

	public void setAttrLabel(String attrLabel) {
		this.attrLabel = attrLabel;
	}

	public String getFlgObbligatorio() {
		return flgObbligatorio;
	}

	public void setFlgObbligatorio(String flgObbligatorio) {
		this.flgObbligatorio = flgObbligatorio;
	}

	public String getMaxNumValori() {
		return maxNumValori;
	}

	public void setMaxNumValori(String maxNumValori) {
		this.maxNumValori = maxNumValori;
	}

	public String getValoriPossibili() {
		return valoriPossibili;
	}

	public void setValoriPossibili(String valoriPossibili) {
		this.valoriPossibili = valoriPossibili;
	}

	public String getTipoLoadCombo() {
		return tipoLoadCombo;
	}

	public void setTipoLoadCombo(String tipoLoadCombo) {
		this.tipoLoadCombo = tipoLoadCombo;
	}

	public String getAltriParametriLoadCombo() {
		return altriParametriLoadCombo;
	}

	public void setAltriParametriLoadCombo(String altriParametriLoadCombo) {
		this.altriParametriLoadCombo = altriParametriLoadCombo;
	}

	public String getFlgSoloVldLoadCombo() {
		return flgSoloVldLoadCombo;
	}

	public void setFlgSoloVldLoadCombo(String flgSoloVldLoadCombo) {
		this.flgSoloVldLoadCombo = flgSoloVldLoadCombo;
	}

	public String getValoreFisso() {
		return valoreFisso;
	}

	public void setValoreFisso(String valoreFisso) {
		this.valoreFisso = valoreFisso;
	}
	
	public String getFlgEditabile() {
		return flgEditabile;
	}

	public void setFlgEditabile(String flgEditabile) {
		this.flgEditabile = flgEditabile;
	}

	public String getFlgMostraFirmatario() {
		return flgMostraFirmatario;
	}

	public void setFlgMostraFirmatario(String flgMostraFirmatario) {
		this.flgMostraFirmatario = flgMostraFirmatario;
	}

	public String getFlgMostraMotivi() {
		return flgMostraMotivi;
	}

	public void setFlgMostraMotivi(String flgMostraMotivi) {
		this.flgMostraMotivi = flgMostraMotivi;
	}

	public String getAltezzaInRighe() {
		return altezzaInRighe;
	}

	public void setAltezzaInRighe(String altezzaInRighe) {
		this.altezzaInRighe = altezzaInRighe;
	}

	public String getDecodificheValoriPossibili() {
		return decodificheValoriPossibili;
	}

	public void setDecodificheValoriPossibili(String decodificheValoriPossibili) {
		this.decodificheValoriPossibili = decodificheValoriPossibili;
	}

	public String getFlgMostraVistoInRitornoIter() {
		return flgMostraVistoInRitornoIter;
	}

	public void setFlgMostraVistoInRitornoIter(String flgMostraVistoInRitornoIter) {
		this.flgMostraVistoInRitornoIter = flgMostraVistoInRitornoIter;
	}
	
}