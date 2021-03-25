package it.eng.auriga.ui.module.layout.server.tipologieDocumentali.datasource.bean;

import java.util.List;

public class ListaTipologieDocBean 
{
	private String idUo;
	private List<TipologieDocumentaliBean> listaTipologieDoc;
	
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public List<TipologieDocumentaliBean> getListaTipologieDoc() {
		return listaTipologieDoc;
	}
	public void setListaTipologieDoc(List<TipologieDocumentaliBean> listaTipologieDoc) {
		this.listaTipologieDoc = listaTipologieDoc;
	}
	

	
}
