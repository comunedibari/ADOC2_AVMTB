package it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.avvioProcedimento.bean;

import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class SelezionaProcedimentoBean extends VisualBean {

	private String idProcessType;
	private String nome;
	public String getIdProcessType() {
		return idProcessType;
	}
	public void setIdProcessType(String idProcessType) {
		this.idProcessType = idProcessType;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
