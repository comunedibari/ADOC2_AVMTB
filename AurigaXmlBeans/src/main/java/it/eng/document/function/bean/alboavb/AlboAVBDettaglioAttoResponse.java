package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBDettaglioAttoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBDettaglioAttoResponseReturn alboAVBDettaglioAttoResponseReturn;

	public AlboAVBDettaglioAttoResponse() {
	}

	public AlboAVBDettaglioAttoResponse(AlboAVBDettaglioAttoResponseReturn alboAVBDettaglioAttoResponseReturn) {
		this.alboAVBDettaglioAttoResponseReturn = alboAVBDettaglioAttoResponseReturn;
	}

	public AlboAVBDettaglioAttoResponseReturn getAlboAVBDettaglioAttoResponseReturn() {
		return alboAVBDettaglioAttoResponseReturn;
	}

	public void setAlboAVBDettaglioAttoResponseReturn(
			AlboAVBDettaglioAttoResponseReturn alboAVBDettaglioAttoResponseReturn) {
		this.alboAVBDettaglioAttoResponseReturn = alboAVBDettaglioAttoResponseReturn;
	}
}
