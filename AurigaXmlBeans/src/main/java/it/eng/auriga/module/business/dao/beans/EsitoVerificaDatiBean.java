package it.eng.auriga.module.business.dao.beans;

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import it.eng.core.business.beans.AbstractBean;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class EsitoVerificaDatiBean extends AbstractBean implements Serializable {

	private boolean esito;	
	private String xmlDettagli;
	
	
	public boolean isEsito() {
		return esito;
	}
	public void setEsito(boolean esito) {
		this.esito = esito;
		this.getUpdatedProperties().add("esito");
	}
	public String getXmlDettagli() {
		return xmlDettagli;
	}
	public void setXmlDettagli(String xmlDettagli) {
		this.xmlDettagli = xmlDettagli;
		this.getUpdatedProperties().add("xmlDettagli");
	}


}
