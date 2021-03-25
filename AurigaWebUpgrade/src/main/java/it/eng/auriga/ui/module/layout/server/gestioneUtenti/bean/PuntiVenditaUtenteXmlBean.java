package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import it.eng.document.NumeroColonna;

public class PuntiVenditaUtenteXmlBean  {
	
	@NumeroColonna(numero="1")
	private String idPuntoVendita;

	public String getIdPuntoVendita() {
		return idPuntoVendita;
	}

	public void setIdPuntoVendita(String idPuntoVendita) {
		this.idPuntoVendita = idPuntoVendita;
	}


}