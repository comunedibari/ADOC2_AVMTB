package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;


public class PeriziaComboXmlBean extends VisualBean {

    @NumeroColonna(numero = "1")
    private String codice;
	
	@NumeroColonna(numero = "2")
	private String descrizione;

	
	private String idCodice;
	
	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIdCodice() {
		return idCodice;
	}

	public void setIdCodice(String idCodice) {
		this.idCodice = idCodice;
	}
	
}
