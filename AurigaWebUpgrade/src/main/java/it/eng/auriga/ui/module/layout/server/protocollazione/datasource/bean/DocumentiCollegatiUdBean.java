package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import java.util.List;

public class DocumentiCollegatiUdBean {
	
	private String idUd;
	private List<DocCollegatoBean> listaDocumentiCollegati;
	
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public List<DocCollegatoBean> getListaDocumentiCollegati() {
		return listaDocumentiCollegati;
	}
	public void setListaDocumentiCollegati(List<DocCollegatoBean> listaDocumentiCollegati) {
		this.listaDocumentiCollegati = listaDocumentiCollegati;
	}
	
}
