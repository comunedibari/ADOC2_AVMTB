package it.eng.auriga.ui.module.layout.server.report.bean;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class CaselleMailStoricizzateXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idAccount;
	
	@NumeroColonna(numero = "2")
	private String indirizzo;

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
}