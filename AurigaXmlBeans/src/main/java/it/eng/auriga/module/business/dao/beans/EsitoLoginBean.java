package it.eng.auriga.module.business.dao.beans;

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import it.eng.core.business.beans.AbstractBean;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class EsitoLoginBean extends AbstractBean implements Serializable {

	public enum EsitoLogin { OK, USERNAME_ERROR, PASSWORD_ERROR, GENERIC_PROBLEM}
	
	private EsitoLogin esito;

	public EsitoLogin getEsito() {
		return esito;
	}

	public void setEsito(EsitoLogin esito) {
		this.esito = esito;
		this.getUpdatedProperties().add("esito");
	}

}
