package it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean;

import java.util.List;

public class AssociaUdVsProcBean {
	
	private String idProcess;
	private List<AllegatoProcBean> listaAllegatiProc;
	
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	public List<AllegatoProcBean> getListaAllegatiProc() {
		return listaAllegatiProc;
	}
	public void setListaAllegatiProc(List<AllegatoProcBean> listaAllegatiProc) {
		this.listaAllegatiProc = listaAllegatiProc;
	}
	
}
