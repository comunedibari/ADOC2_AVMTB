package it.eng.auriga.ui.module.layout.server.librofirmamassiva.datasource.bean;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class FileToLibroFirma {

	@NumeroColonna(numero = "1")
	private String idDoc;
	@NumeroColonna(numero = "2")
	private String uriFile;
	@NumeroColonna(numero = "3")
	private String tipoFirma;
	@NumeroColonna(numero = "4")
	private String mimetype;
	@NumeroColonna(numero = "5")
	private String dimensione;
	@NumeroColonna(numero = "6")
	private String impronta;
	@NumeroColonna(numero = "7")
	private String algoritmoImpronta;
	@NumeroColonna(numero = "8")
	private String encodingImpronta;
	@NumeroColonna(numero = "9")
	private String firmatari;
	
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getUriFile() {
		return uriFile;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public String getTipoFirma() {
		return tipoFirma;
	}
	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getDimensione() {
		return dimensione;
	}
	public void setDimensione(String dimensione) {
		this.dimensione = dimensione;
	}
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}
	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}
	public String getEncodingImpronta() {
		return encodingImpronta;
	}
	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}
	public String getFirmatari() {
		return firmatari;
	}
	public void setFirmatari(String firmatari) {
		this.firmatari = firmatari;
	}
}
