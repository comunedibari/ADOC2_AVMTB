package it.eng.workflow.service.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompletaTaskServiceOutBean implements Serializable {
	
	private static final long serialVersionUID = 5862803870999282542L;
	private Boolean esito;
	private String error;
	public Boolean getEsito() {
		return esito;
	}
	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
