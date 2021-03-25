package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import it.eng.document.NumeroColonna;


public class AreeCommercialiBean {

	@NumeroColonna(numero="1")
	private String idAreaCommerciali;
	
	@NumeroColonna(numero="2")
	private String denominazioneAreaCommerciali;

	public String getIdAreaCommerciali() {
		return idAreaCommerciali;
	}

	public void setIdAreaCommerciali(String idAreaCommerciali) {
		this.idAreaCommerciali = idAreaCommerciali;
	}

	public String getDenominazioneAreaCommerciali() {
		return denominazioneAreaCommerciali;
	}

	public void setDenominazioneAreaCommerciali(String denominazioneAreaCommerciali) {
		this.denominazioneAreaCommerciali = denominazioneAreaCommerciali;
	}


	
}
