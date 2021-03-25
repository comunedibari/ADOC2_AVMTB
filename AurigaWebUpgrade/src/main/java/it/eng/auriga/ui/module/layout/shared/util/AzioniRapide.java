package it.eng.auriga.ui.module.layout.shared.util;


public enum AzioniRapide {
	
	// Azioni specifiche
	METTI_IN_CARICO("MIC"),
	MANDA_IN_APPROVAZIONE("MIA"),
	ASSEGNA_UO_COMPETENTE("AUO"),
	ASSEGNA_DOC("AD"),
	ASSEGNA_FOLDER("AF"),
	INVIO_CC_DOC("ICCD"),
	INVIO_CC_FOLDER("ICCF");
	
	
	public String getValue() {
		return value;
	}

	private AzioniRapide(String value) {
		this.value = value;
	}

	private String value;
	
}
