package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class UoAssociatoUtenteBean extends VisualBean{

	@NumeroColonna(numero = "1")
	private String idUo;
	
	@NumeroColonna(numero = "2")
	private String descrizione;
	
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
	
	
}
