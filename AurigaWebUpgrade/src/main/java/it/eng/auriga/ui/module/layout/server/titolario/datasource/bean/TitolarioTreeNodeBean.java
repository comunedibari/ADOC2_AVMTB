package it.eng.auriga.ui.module.layout.server.titolario.datasource.bean;


import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;

public class TitolarioTreeNodeBean extends TreeNodeBean {	
	
	private String indice;
	
	private String flgAttiva;

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public String getFlgAttiva() {
		return flgAttiva;
	}

	public void setFlgAttiva(String flgAttiva) {
		this.flgAttiva = flgAttiva;
	}
		
}
