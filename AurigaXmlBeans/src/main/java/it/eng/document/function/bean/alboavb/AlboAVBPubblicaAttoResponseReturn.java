package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBPubblicaAttoResponseReturn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBAtto alboAvbAtto;
	
	private AlboAVBAttoError alboAvbAttoError;

	public AlboAVBPubblicaAttoResponseReturn() {

	}

	public AlboAVBPubblicaAttoResponseReturn(AlboAVBAtto alboAvbAtto, AlboAVBAttoError alboAvbAttoError) {

		this.alboAvbAtto = alboAvbAtto;
		this.alboAvbAttoError = alboAvbAttoError;
	}

	public AlboAVBAtto getAlboAvbAtto() {
		return alboAvbAtto;
	}

	public void setAlboAvbAtto(AlboAVBAtto alboAvbAtto) {
		this.alboAvbAtto = alboAvbAtto;
	}

	public AlboAVBAttoError getAlboAvbAttoError() {
		return alboAvbAttoError;
	}

	public void setAlboAvbAttoError(AlboAVBAttoError alboAvbAttoError) {
		this.alboAvbAttoError = alboAvbAttoError;
	}
}
