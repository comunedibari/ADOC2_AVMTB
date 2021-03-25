package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;

public class UoProtocollanteBean {

	@NumeroColonna(numero = "1")
	private String idUo;
	
	@NumeroColonna(numero = "2")
	private String descrizione; // livelli - descrizione
	
	//idRubrica?
	
	@NumeroColonna(numero = "7")
	private String flgPreimpDestProtEntrata;	
	
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getFlgPreimpDestProtEntrata() {
		return flgPreimpDestProtEntrata;
	}
	public void setFlgPreimpDestProtEntrata(String flgPreimpDestProtEntrata) {
		this.flgPreimpDestProtEntrata = flgPreimpDestProtEntrata;
	}
	
}
