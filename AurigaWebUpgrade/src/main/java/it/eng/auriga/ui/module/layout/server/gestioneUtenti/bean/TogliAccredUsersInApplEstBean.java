package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import java.util.List;

public class TogliAccredUsersInApplEstBean {
		
	private String codiceApplEst;
	private String codiceIstAppl;
	
	private List<UtentiAccredInApplEstBean> listaUtentiXml;
	
	public List<UtentiAccredInApplEstBean> getListaUtentiXml() {
		return listaUtentiXml;
	}
	public void setListaUtentiXml(List<UtentiAccredInApplEstBean> listaUtentiXml) {
		this.listaUtentiXml = listaUtentiXml;
	}
	public String getCodiceApplEst() {
		return codiceApplEst;
	}
	public void setCodiceApplEst(String codiceApplEst) {
		this.codiceApplEst = codiceApplEst;
	}
	public String getCodiceIstAppl() {
		return codiceIstAppl;
	}
	public void setCodiceIstAppl(String codiceIstAppl) {
		this.codiceIstAppl = codiceIstAppl;
	}
	
	
}
