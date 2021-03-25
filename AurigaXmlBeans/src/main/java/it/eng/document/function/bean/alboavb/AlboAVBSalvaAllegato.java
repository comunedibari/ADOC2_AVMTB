package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBSalvaAllegato implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBSalvaAllegatoIn alboAvbSalvaAllegatoIn;

	public AlboAVBSalvaAllegato() {
	}

	public AlboAVBSalvaAllegato(AlboAVBSalvaAllegatoIn alboAvbSalvaAllegatoIn) {
		this.alboAvbSalvaAllegatoIn = alboAvbSalvaAllegatoIn;
	}

	public AlboAVBSalvaAllegatoIn getAlboAvbSalvaAllegatoIn() {
		return alboAvbSalvaAllegatoIn;
	}

	public void setAlboAvbSalvaAllegatoIn(AlboAVBSalvaAllegatoIn alboAvbSalvaAllegatoIn) {
		this.alboAvbSalvaAllegatoIn = alboAvbSalvaAllegatoIn;
	}
}
