package it.eng.auriga.ui.module.layout.server.classifiche.datasource.bean;

import java.util.List;


public class ListaClassificheBean {

	private String idUo;
	private List<ClassificheBean> listaClassifiche;
	
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public List<ClassificheBean> getListaClassifiche() {
		return listaClassifiche;
	}
	public void setListaClassifiche(List<ClassificheBean> listaClassifiche) {
		this.listaClassifiche = listaClassifiche;
	}
	
}
