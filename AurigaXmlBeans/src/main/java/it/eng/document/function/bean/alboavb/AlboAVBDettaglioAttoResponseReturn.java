package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBDettaglioAttoResponseReturn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBDettaglioAttoDto alboAVBDettaglioAttoDto;
	
	private AlboAVBAttoError alboAvbAttoError;

	public AlboAVBDettaglioAttoResponseReturn() {
	}

	public AlboAVBDettaglioAttoResponseReturn(AlboAVBDettaglioAttoDto alboAVBDettaglioAttoDto,
			AlboAVBAttoError alboAvbAttoError) {
		this.alboAVBDettaglioAttoDto = alboAVBDettaglioAttoDto;
		this.alboAvbAttoError = alboAvbAttoError;
	}

	public AlboAVBDettaglioAttoDto getAlboAVBDettaglioAttoDto() {
		return alboAVBDettaglioAttoDto;
	}

	public void setAlboAVBDettaglioAttoDto(AlboAVBDettaglioAttoDto alboAVBDettaglioAttoDto) {
		this.alboAVBDettaglioAttoDto = alboAVBDettaglioAttoDto;
	}

	public AlboAVBAttoError getAlboAvbAttoError() {
		return alboAvbAttoError;
	}

	public void setAlboAvbAttoError(AlboAVBAttoError alboAvbAttoError) {
		this.alboAvbAttoError = alboAvbAttoError;
	}
}
