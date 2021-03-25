package it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean;

import it.eng.auriga.ui.module.layout.server.common.OperazioneMassivaBean;

import java.io.Serializable;

public class OperazioneMassivaArgomentiOdgXmlBean extends OperazioneMassivaBean<ArgomentiOdgXmlBean> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String organoCollegiale;

	public String getOrganoCollegiale() {
		return organoCollegiale;
	}

	public void setOrganoCollegiale(String organoCollegiale) {
		this.organoCollegiale = organoCollegiale;
	}	
	
}
