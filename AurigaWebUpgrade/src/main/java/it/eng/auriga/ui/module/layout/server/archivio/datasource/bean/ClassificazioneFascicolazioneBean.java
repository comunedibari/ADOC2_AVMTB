package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ClassificazioneFascicoloBean;

import java.io.Serializable;
import java.util.List;

public class ClassificazioneFascicolazioneBean extends OperazioneMassivaArchivioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String livelloRiservatezza;
	private List<ClassificazioneFascicoloBean> listaClassFasc;
		
	public List<ClassificazioneFascicoloBean> getListaClassFasc() {
		return listaClassFasc;
	}
	public void setListaClassFasc(List<ClassificazioneFascicoloBean> listaClassFasc) {
		this.listaClassFasc = listaClassFasc;
	}
	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}
	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}
		
}
