package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */


public class DettaglioPostaElettronicaEstremiDoc {
	
	@NumeroColonna(numero = "1")
	private String idUD;
	@NumeroColonna(numero = "2")
	private String estremiProt;
	
	public String getIdUD() {
		return idUD;
	}
	public void setIdUD(String idUD) {
		this.idUD = idUD;
	}
	public String getEstremiProt() {
		return estremiProt;
	}
	public void setEstremiProt(String estremiProt) {
		this.estremiProt = estremiProt;
	}	
	
}