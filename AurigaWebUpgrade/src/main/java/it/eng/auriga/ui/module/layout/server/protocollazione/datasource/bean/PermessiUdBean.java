package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import java.util.List;

public class PermessiUdBean {
	
	private String idUd;
	private List<ACLBean> listaACL;
	
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public List<ACLBean> getListaACL() {
		return listaACL;
	}
	public void setListaACL(List<ACLBean> listaACL) {
		this.listaACL = listaACL;
	}
	
}
