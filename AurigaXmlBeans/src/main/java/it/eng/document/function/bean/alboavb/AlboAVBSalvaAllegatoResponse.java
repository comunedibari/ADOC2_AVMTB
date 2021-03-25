package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBSalvaAllegatoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBSalvaAllegatoResponseReturn alboAVBSalvaAllegatoResponseReturn;

	public AlboAVBSalvaAllegatoResponse() {
	}

	public AlboAVBSalvaAllegatoResponse(AlboAVBSalvaAllegatoResponseReturn alboAVBSalvaAllegatoResponseReturn) {
		this.alboAVBSalvaAllegatoResponseReturn = alboAVBSalvaAllegatoResponseReturn;
	}

	public AlboAVBSalvaAllegatoResponseReturn getAlboAVBSalvaAllegatoResponseReturn() {
		return alboAVBSalvaAllegatoResponseReturn;
	}

	public void setAlboAVBSalvaAllegatoResponseReturn(
			AlboAVBSalvaAllegatoResponseReturn alboAVBSalvaAllegatoResponseReturn) {
		this.alboAVBSalvaAllegatoResponseReturn = alboAVBSalvaAllegatoResponseReturn;
	}
}
