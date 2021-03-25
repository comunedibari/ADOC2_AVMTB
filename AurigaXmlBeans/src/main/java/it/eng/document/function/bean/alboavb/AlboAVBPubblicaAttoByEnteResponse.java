package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBPubblicaAttoByEnteResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBPubblicaAttoByEnteResponseReturn alboAVBPubblicaAttoByEnteResponseReturn;

	public AlboAVBPubblicaAttoByEnteResponse() {
	}

	public AlboAVBPubblicaAttoByEnteResponse(
			AlboAVBPubblicaAttoByEnteResponseReturn alboAVBPubblicaAttoByEnteResponseReturn) {
		this.alboAVBPubblicaAttoByEnteResponseReturn = alboAVBPubblicaAttoByEnteResponseReturn;
	}

	public AlboAVBPubblicaAttoByEnteResponseReturn getAlboAVBPubblicaAttoByEnteResponseReturn() {
		return alboAVBPubblicaAttoByEnteResponseReturn;
	}

	public void setAlboAVBPubblicaAttoByEnteResponseReturn(
			AlboAVBPubblicaAttoByEnteResponseReturn alboAVBPubblicaAttoByEnteResponseReturn) {
		this.alboAVBPubblicaAttoByEnteResponseReturn = alboAVBPubblicaAttoByEnteResponseReturn;
	}
}
