package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import it.eng.document.NumeroColonna;


public class AreeCommercialiXmlBean {

	@NumeroColonna(numero="1")
	private String idAreaCommerciali;
	
	
	public String getIdAreaCommerciali() {
		return idAreaCommerciali;
	}

	public void setIdAreaCommerciali(String idAreaCommerciali) {
		this.idAreaCommerciali = idAreaCommerciali;
	}

	

	
}
