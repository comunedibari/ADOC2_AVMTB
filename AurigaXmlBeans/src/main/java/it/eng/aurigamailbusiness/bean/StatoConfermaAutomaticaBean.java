package it.eng.aurigamailbusiness.bean;

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe di ingresso ai metodi di riconoscimento fruitore casella
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class StatoConfermaAutomaticaBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 2219977861218598219L;

	private String stato;

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


}
