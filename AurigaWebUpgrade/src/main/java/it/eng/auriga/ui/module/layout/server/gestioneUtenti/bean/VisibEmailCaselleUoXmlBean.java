package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import it.eng.document.NumeroColonna;

public class VisibEmailCaselleUoXmlBean  {
	
	@NumeroColonna(numero="1")
	private String idUo;

	@NumeroColonna(numero="2")
	private String flgIncludiSottoUO;

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getFlgIncludiSottoUO() {
		return flgIncludiSottoUO;
	}

	public void setFlgIncludiSottoUO(String flgIncludiSottoUO) {
		this.flgIncludiSottoUO = flgIncludiSottoUO;
	}





}