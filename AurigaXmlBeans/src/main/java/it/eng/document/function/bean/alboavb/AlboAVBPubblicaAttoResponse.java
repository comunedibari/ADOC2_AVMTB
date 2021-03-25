package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBPubblicaAttoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBPubblicaAttoResponseReturn alboAvbPubblicaAttoResponseReturn;

	public AlboAVBPubblicaAttoResponse() {
	}

	public AlboAVBPubblicaAttoResponse(AlboAVBPubblicaAttoResponseReturn alboAvbPubblicaAttoResponseReturn) {
		this.alboAvbPubblicaAttoResponseReturn = alboAvbPubblicaAttoResponseReturn;
	}

	public AlboAVBPubblicaAttoResponseReturn getAlboAvbPubblicaAttoResponseReturn() {
		return alboAvbPubblicaAttoResponseReturn;
	}

	public void setAlboAvbPubblicaAttoResponseReturn(AlboAVBPubblicaAttoResponseReturn alboAvbPubblicaAttoResponseReturn) {
		this.alboAvbPubblicaAttoResponseReturn = alboAvbPubblicaAttoResponseReturn;
	}
}
