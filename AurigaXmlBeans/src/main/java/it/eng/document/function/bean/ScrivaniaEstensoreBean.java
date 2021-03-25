package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

public class ScrivaniaEstensoreBean {

	@NumeroColonna(numero = "1")
	private String idSV;
	
	@NumeroColonna(numero = "2")
	private String codUO;
			
	@NumeroColonna(numero = "3")
	private String descrizione;
	
	
	public String getIdSV() {
		return idSV;
	}

	public void setIdSV(String idSV) {
		this.idSV = idSV;
	}

	public String getCodUO() {
		return codUO;
	}

	public void setCodUO(String codUO) {
		this.codUO = codUO;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
