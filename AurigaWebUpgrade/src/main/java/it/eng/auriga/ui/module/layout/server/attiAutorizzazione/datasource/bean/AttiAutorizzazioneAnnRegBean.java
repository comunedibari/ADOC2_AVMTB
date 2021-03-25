package it.eng.auriga.ui.module.layout.server.attiAutorizzazione.datasource.bean;

import java.util.Date;
import java.util.List;


public class AttiAutorizzazioneAnnRegBean {
	
	private String idAtto;	
	private String nroBozza;
	private Date tsRegBozza;
	private String desUteBozza;
	private String nroAtto;
	private Date tsRegAtto;
	private String desUteAtto;
	private String oggetto;	
	private Integer flgAttoChiuso;		
	private List<RegDaAnnullareBean> listaRegDaAnnullare;
	
	public String getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(String idAtto) {
		this.idAtto = idAtto;
	}

	public String getNroBozza() {
		return nroBozza;
	}

	public void setNroBozza(String nroBozza) {
		this.nroBozza = nroBozza;
	}

	public Date getTsRegBozza() {
		return tsRegBozza;
	}

	public void setTsRegBozza(Date tsRegBozza) {
		this.tsRegBozza = tsRegBozza;
	}

	public String getDesUteBozza() {
		return desUteBozza;
	}

	public void setDesUteBozza(String desUteBozza) {
		this.desUteBozza = desUteBozza;
	}

	public String getNroAtto() {
		return nroAtto;
	}

	public void setNroAtto(String nroAtto) {
		this.nroAtto = nroAtto;
	}

	public Date getTsRegAtto() {
		return tsRegAtto;
	}

	public void setTsRegAtto(Date tsRegAtto) {
		this.tsRegAtto = tsRegAtto;
	}

	public String getDesUteAtto() {
		return desUteAtto;
	}

	public void setDesUteAtto(String desUteAtto) {
		this.desUteAtto = desUteAtto;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Integer getFlgAttoChiuso() {
		return flgAttoChiuso;
	}

	public void setFlgAttoChiuso(Integer flgAttoChiuso) {
		this.flgAttoChiuso = flgAttoChiuso;
	}

	public List<RegDaAnnullareBean> getListaRegDaAnnullare() {
		return listaRegDaAnnullare;
	}

	public void setListaRegDaAnnullare(List<RegDaAnnullareBean> listaRegDaAnnullare) {
		this.listaRegDaAnnullare = listaRegDaAnnullare;
	}
	
	
}
