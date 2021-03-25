package it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class CompilaModelloAttivitaBean extends ModelloAttivitaBean {
	
	private String uriFileGenerato;
	private MimeTypeFirmaBean infoFileGenerato;
	
	public String getUriFileGenerato() {
		return uriFileGenerato;
	}
	public void setUriFileGenerato(String uriFileGenerato) {
		this.uriFileGenerato = uriFileGenerato;
	}
	public MimeTypeFirmaBean getInfoFileGenerato() {
		return infoFileGenerato;
	}
	public void setInfoFileGenerato(MimeTypeFirmaBean infoFileGenerato) {
		this.infoFileGenerato = infoFileGenerato;
	}

}
