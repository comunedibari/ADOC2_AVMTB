package it.eng.aurigamailbusiness.bean;

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jravagnan
 *
 */
@XmlRootElement
public class InfoRelazioneProtocolloBean extends AbstractBean implements Serializable{

	private static final long serialVersionUID = 7811255048616558080L;
	
	private TRegProtVsEmailBean relazione;

	public TRegProtVsEmailBean getRelazione() {
		return relazione;
	}

	public void setRelazione(TRegProtVsEmailBean relazione) {
		this.relazione = relazione;
	}

}
