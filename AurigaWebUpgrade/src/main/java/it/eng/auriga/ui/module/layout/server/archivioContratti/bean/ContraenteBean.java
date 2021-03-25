package it.eng.auriga.ui.module.layout.server.archivioContratti.bean;

import it.eng.document.NumeroColonna;
import java.io.Serializable;


public class ContraenteBean implements Serializable{

	@NumeroColonna(numero = "4")
	private String denominazione;
	@NumeroColonna(numero = "6")
	private String codiceFiscale;
	@NumeroColonna(numero = "7")
	private String partitaIva;
	@NumeroColonna(numero = "17")
	private String tipocontraente;
	
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getTipocontraente() {
		return tipocontraente;
	}
	public void setTipocontraente(String tipocontraente) {
		this.tipocontraente = tipocontraente;
	}
	
}
