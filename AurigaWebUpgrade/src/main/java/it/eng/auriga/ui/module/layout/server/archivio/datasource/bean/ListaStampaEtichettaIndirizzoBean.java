package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.OperazioneMassivaPostaElettronicaBean;

import java.util.ArrayList;

public class ListaStampaEtichettaIndirizzoBean extends OperazioneMassivaPostaElettronicaBean{
	
	private ArrayList<String> etichette;
	// numero di fascicoli selezionati
	private int numFascicoli;

	public ArrayList<String> getEtichette() {
		return etichette;
	}

	public void setEtichette(ArrayList<String> etichette) {
		this.etichette = etichette;
	}

	public int getNumFascicoli() {
		return numFascicoli;
	}

	public void setNumFascicoli(int numFascicoli) {
		this.numFascicoli = numFascicoli;
	}
	
	
	
}
