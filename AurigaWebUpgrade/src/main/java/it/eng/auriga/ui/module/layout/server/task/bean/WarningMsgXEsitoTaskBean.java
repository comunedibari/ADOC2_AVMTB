package it.eng.auriga.ui.module.layout.server.task.bean;

import it.eng.document.NumeroColonna;

public class WarningMsgXEsitoTaskBean {

	@NumeroColonna(numero = "1")
	private String esito;
	
	@NumeroColonna(numero = "2")
	private String warningMsg;

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getWarningMsg() {
		return warningMsg;
	}

	public void setWarningMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}
	
}