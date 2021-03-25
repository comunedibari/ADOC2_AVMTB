package it.eng.auriga.ui.module.layout.server.configurazioneFlussi.datasource.bean;

import it.eng.document.NumeroColonna;

public class AttributiAddEditabiliXmlBean {

	@NumeroColonna(numero = "1")
	private String nomeAttributo;

	public String getNomeAttributo() {
		return nomeAttributo;
	}

	public void setNomeAttributo(String nomeAttributo) {
		this.nomeAttributo = nomeAttributo;
	}
	
}
