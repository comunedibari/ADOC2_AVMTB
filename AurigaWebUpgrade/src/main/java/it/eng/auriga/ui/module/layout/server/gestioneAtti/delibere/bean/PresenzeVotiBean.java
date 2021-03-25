package it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean;

import java.util.List;

public class PresenzeVotiBean {
	
	private String idSeduta;
	private String idUdArgomentoOdG;
	
	private List<PresenzeOdgXmlBean> listaPresenzeVoti;
	private int totaliAstenutiVoto;
	private int totaleVotiEspressi;
	private int totaleVotiFavorevoli;
	private int totaleVotiContrari;
	private List<PresenzeOdgXmlBean> listaAltriPresenti; // vengono considerati solo idUser e denominazione, quest'ultimo nella forma "Congnome e nome - Incarico"
	
	public String getIdSeduta() {
		return idSeduta;
	}
	public void setIdSeduta(String idSeduta) {
		this.idSeduta = idSeduta;
	}
	public String getIdUdArgomentoOdG() {
		return idUdArgomentoOdG;
	}
	public void setIdUdArgomentoOdG(String idUdArgomentoOdG) {
		this.idUdArgomentoOdG = idUdArgomentoOdG;
	}
	public List<PresenzeOdgXmlBean> getListaPresenzeVoti() {
		return listaPresenzeVoti;
	}
	public void setListaPresenzeVoti(List<PresenzeOdgXmlBean> listaPresenzeVoti) {
		this.listaPresenzeVoti = listaPresenzeVoti;
	}
	public int getTotaliAstenutiVoto() {
		return totaliAstenutiVoto;
	}
	public void setTotaliAstenutiVoto(int totaliAstenutiVoto) {
		this.totaliAstenutiVoto = totaliAstenutiVoto;
	}
	public int getTotaleVotiEspressi() {
		return totaleVotiEspressi;
	}
	public void setTotaleVotiEspressi(int totaleVotiEspressi) {
		this.totaleVotiEspressi = totaleVotiEspressi;
	}
	public int getTotaleVotiFavorevoli() {
		return totaleVotiFavorevoli;
	}
	public void setTotaleVotiFavorevoli(int totaleVotiFavorevoli) {
		this.totaleVotiFavorevoli = totaleVotiFavorevoli;
	}
	public int getTotaleVotiContrari() {
		return totaleVotiContrari;
	}
	public void setTotaleVotiContrari(int totaleVotiContrari) {
		this.totaleVotiContrari = totaleVotiContrari;
	}
	public List<PresenzeOdgXmlBean> getListaAltriPresenti() {
		return listaAltriPresenti;
	}
	public void setListaAltriPresenti(List<PresenzeOdgXmlBean> listaAltriPresenti) {
		this.listaAltriPresenti = listaAltriPresenti;
	}	
}