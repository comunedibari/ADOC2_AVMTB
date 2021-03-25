package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBPubblicaAtto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBPubblicaAttoIn alboAvbPubblicaAttoIn;

	public AlboAVBPubblicaAtto() {
	}

	public AlboAVBPubblicaAtto(AlboAVBPubblicaAttoIn alboAvbPubblicaAttoIn) {
		this.alboAvbPubblicaAttoIn = alboAvbPubblicaAttoIn;
	}

	public AlboAVBPubblicaAttoIn getAlboAvbPubblicaAttoIn() {
		return alboAvbPubblicaAttoIn;
	}

	public void setAlboAvbPubblicaAttoIn(AlboAVBPubblicaAttoIn alboAvbPubblicaAttoIn) {
		this.alboAvbPubblicaAttoIn = alboAvbPubblicaAttoIn;
	}
}
