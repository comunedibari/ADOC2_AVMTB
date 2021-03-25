package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import it.eng.document.NumeroColonna;

public class LinguaBean {
	

	@NumeroColonna(numero="1")
	private String idLingua;
	
	@NumeroColonna(numero="2")
	private String descrizioneLingua;
	
	

	public String getIdLingua() {
		return idLingua;
	}


	public void setIdLingua(String idLingua) {
		this.idLingua = idLingua;
	}


	public String getDescrizioneLingua() {
		return descrizioneLingua;
	}


	public void setDescrizioneLingua(String descrizioneLingua) {
		this.descrizioneLingua = descrizioneLingua;
	}




}


