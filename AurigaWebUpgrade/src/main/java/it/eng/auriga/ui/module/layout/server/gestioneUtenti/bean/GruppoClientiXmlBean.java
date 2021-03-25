package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import it.eng.document.NumeroColonna;



public class GruppoClientiXmlBean {

	@NumeroColonna(numero="1")
	private String idGruppoClienti;

	public String getIdGruppoClienti() {
		return idGruppoClienti;
	}

	public void setIdGruppoClienti(String idGruppoClienti) {
		this.idGruppoClienti = idGruppoClienti;
	}

	
	
}
