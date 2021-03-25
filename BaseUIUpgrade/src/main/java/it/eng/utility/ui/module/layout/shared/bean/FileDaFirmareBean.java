package it.eng.utility.ui.module.layout.shared.bean;

import java.io.File;

import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class FileDaFirmareBean extends IdFileBean {
	
	private Boolean firmaEseguita;
	private String uriVerPreFirma;
	private String nomeFileVerPreFirma;
	private MimeTypeFirmaBean infoFileVerPreFirma;

	// Tengo l'uri dell'odt originale del file generato da modello e un flag per sapere se si tratta del dispositivo di NuovaPropostaAtto2Completa
	private String uriFileOdtGenerato;
	private Boolean isDispositivoNuovaPropostaAtto2Completa = false;
	
	// numero progressivo dell'eventuale allegato
	private Integer nroProgAllegato;
	
	private File file;
	
	public Boolean getFirmaEseguita() {
		return firmaEseguita;
	}	
	public void setFirmaEseguita(Boolean firmaEseguita) {
		this.firmaEseguita = firmaEseguita;
	}	
	public String getUriVerPreFirma() {
		return uriVerPreFirma;
	}
	public void setUriVerPreFirma(String uriVerPreFirma) {
		this.uriVerPreFirma = uriVerPreFirma;
	}
	public String getNomeFileVerPreFirma() {
		return nomeFileVerPreFirma;
	}
	public void setNomeFileVerPreFirma(String nomeFileVerPreFirma) {
		this.nomeFileVerPreFirma = nomeFileVerPreFirma;
	}
	public MimeTypeFirmaBean getInfoFileVerPreFirma() {
		return infoFileVerPreFirma;
	}
	public void setInfoFileVerPreFirma(MimeTypeFirmaBean infoFileVerPreFirma) {
		this.infoFileVerPreFirma = infoFileVerPreFirma;
	}
	public String getUriFileOdtGenerato() {
		return uriFileOdtGenerato;
	}
	public void setUriFileOdtGenerato(String uriFileOdtGenerato) {
		this.uriFileOdtGenerato = uriFileOdtGenerato;
	}
	public Boolean getIsDispositivoNuovaPropostaAtto2Completa() {
		return isDispositivoNuovaPropostaAtto2Completa;
	}
	public void setIsDispositivoNuovaPropostaAtto2Completa(Boolean isDispositivoNuovaPropostaAtto2Completa) {
		this.isDispositivoNuovaPropostaAtto2Completa = isDispositivoNuovaPropostaAtto2Completa;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Integer getNroProgAllegato() {
		return nroProgAllegato;
	}
	public void setNroProgAllegato(Integer nroProgAllegato) {
		this.nroProgAllegato = nroProgAllegato;
	}	
}
