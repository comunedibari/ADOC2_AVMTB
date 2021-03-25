package it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean;

import it.eng.utility.ui.module.core.shared.bean.VisualBean;

/**
 * Contiene le informazioni utilizzate dalla maschera di filtraggio degli atti in lavorazione
 * @author massimo malvestio
 *
 */
public class SelezionaAttoBean extends VisualBean {

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
