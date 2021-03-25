package it.eng.auriga.ui.module.layout.server.scrivania.datasource.bean;

import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;

public class ScrivaniaTreeNodeBean extends TreeNodeBean {	
	
	private String azione;
	private String idUtenteModPec;
	private String parametri;
	private String criteriAvanzati;
	private Boolean flgMultiselezione;
	private String codSezione;
	private Boolean flgContenuti;
	private String nroContenuti;
	
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	public String getParametri() {
		return parametri;
	}
	public void setParametri(String parametri) {
		this.parametri = parametri;
	}
	public String getIdUtenteModPec() {
		return idUtenteModPec;
	}
	public void setIdUtenteModPec(String idUtenteModPec) {
		this.idUtenteModPec = idUtenteModPec;
	}
	public String getCriteriAvanzati() {
		return criteriAvanzati;
	}
	public void setCriteriAvanzati(String criteriAvanzati) {
		this.criteriAvanzati = criteriAvanzati;
	}
	public Boolean getFlgMultiselezione() {
		return flgMultiselezione;
	}
	public void setFlgMultiselezione(Boolean flgMultiselezione) {
		this.flgMultiselezione = flgMultiselezione;
	}
	public String getCodSezione() {
		return codSezione;
	}
	public void setCodSezione(String codSezione) {
		this.codSezione = codSezione;
	}
	public Boolean getFlgContenuti() {
		return flgContenuti;
	}
	public void setFlgContenuti(Boolean flgContenuti) {
		this.flgContenuti = flgContenuti;
	}
	public String getNroContenuti() {
		return nroContenuti;
	}
	public void setNroContenuti(String nroContenuti) {
		this.nroContenuti = nroContenuti;
	}	
	
}
