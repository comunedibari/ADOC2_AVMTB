package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class GruppoSoggettiBean extends VisualBean{

	private String idGruppo;	
	@NumeroColonna(numero = "2")
	private String codiceRapidoGruppo;
	@NumeroColonna(numero = "3")
	private String nomeGruppo;
	@NumeroColonna(numero = "4")
	private String flagSoggettiGruppo;
	@NumeroColonna(numero = "5")
	private String idSoggettiInterni;
	@NumeroColonna(numero = "1")
	private String idSoggettiNonInterni;
	
	
	public String getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}
	public String getCodiceRapidoGruppo() {
		return codiceRapidoGruppo;
	}
	public void setCodiceRapidoGruppo(String codiceRapidoGruppo) {
		this.codiceRapidoGruppo = codiceRapidoGruppo;
	}
	public String getNomeGruppo() {
		return nomeGruppo;
	}
	public void setNomeGruppo(String nomeGruppo) {
		this.nomeGruppo = nomeGruppo;
	}	

	public String getFlagSoggettiGruppo() {
		return flagSoggettiGruppo;
	}
	public void setFlagSoggettiGruppo(String flagSoggettiGruppo) {
		this.flagSoggettiGruppo = flagSoggettiGruppo;
	}
	public String getIdSoggettiInterni() {
		return idSoggettiInterni;
	}
	public void setIdSoggettiInterni(String idSoggettiInterni) {
		this.idSoggettiInterni = idSoggettiInterni;
	}
	public String getIdSoggettiNonInterni() {
		return idSoggettiNonInterni;
	}
	public void setIdSoggettiNonInterni(String idSoggettiNonInterni) {
		this.idSoggettiNonInterni = idSoggettiNonInterni;
	}	
}
