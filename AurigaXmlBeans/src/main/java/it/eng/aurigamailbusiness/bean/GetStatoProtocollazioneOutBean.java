package it.eng.aurigamailbusiness.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetStatoProtocollazioneOutBean implements Serializable {

	private static final long serialVersionUID = 1594585953160848803L;

	private String flagStatoProtocollazione;

	public void setFlagStatoProtocollazione(String flagStatoProtocollazione) {
		this.flagStatoProtocollazione = flagStatoProtocollazione;
	}

	public String getFlagStatoProtocollazione() {
		return flagStatoProtocollazione;
	}
}
