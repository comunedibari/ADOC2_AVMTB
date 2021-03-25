package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.auriga.ui.module.layout.server.common.OperazioneMassivaBean;

import java.io.Serializable;

public class OperazioneMassivaArchivioBean extends OperazioneMassivaBean<ArchivioBean> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String mezziTrasmissione;

	public String getMezziTrasmissione() {
		return mezziTrasmissione;
	}

	public void setMezziTrasmissione(String mezziTrasmissione) {
		this.mezziTrasmissione = mezziTrasmissione;
	}
	
	
}
