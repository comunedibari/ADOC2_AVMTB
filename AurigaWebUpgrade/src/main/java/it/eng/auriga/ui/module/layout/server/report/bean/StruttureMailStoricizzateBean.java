package it.eng.auriga.ui.module.layout.server.report.bean;

/**
 * 
 * @author DANCRIST
 *
 */

public class StruttureMailStoricizzateBean {
	
	private String idUo;
	private String descrizione;
	private boolean flgIncludiSottoUO;
	
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
	public boolean getFlgIncludiSottoUO() {
		return flgIncludiSottoUO;
	}
	public void setFlgIncludiSottoUO(boolean flgIncludiSottoUO) {
		this.flgIncludiSottoUO = flgIncludiSottoUO;
	}
}