package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBDettaglioAtto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AlboAVBDettaglioAttoIn alboAVBDettaglioAttoIn;

	public AlboAVBDettaglioAtto() {
	}

	public AlboAVBDettaglioAtto(AlboAVBDettaglioAttoIn alboAVBDettaglioAttoIn) {
		this.alboAVBDettaglioAttoIn = alboAVBDettaglioAttoIn;
	}

	public AlboAVBDettaglioAttoIn getAlboAVBDettaglioAttoIn() {
		return alboAVBDettaglioAttoIn;
	}

	public void setAlboAVBDettaglioAttoIn(AlboAVBDettaglioAttoIn alboAVBDettaglioAttoIn) {
		this.alboAVBDettaglioAttoIn = alboAVBDettaglioAttoIn;
	}
}
