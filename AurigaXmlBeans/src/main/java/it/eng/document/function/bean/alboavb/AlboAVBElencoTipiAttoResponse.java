package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBElencoTipiAttoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBElencoTipiAttoResponseReturn alboAVBElencoTipiAttoResponseReturn;

	public AlboAVBElencoTipiAttoResponse() {
	}

	public AlboAVBElencoTipiAttoResponse(AlboAVBElencoTipiAttoResponseReturn alboAVBElencoTipiAttoResponseReturn) {
		this.alboAVBElencoTipiAttoResponseReturn = alboAVBElencoTipiAttoResponseReturn;
	}

	public AlboAVBElencoTipiAttoResponseReturn getAlboAVBElencoTipiAttoResponseReturn() {
		return alboAVBElencoTipiAttoResponseReturn;
	}

	public void setAlboAVBElencoTipiAttoResponseReturn(
			AlboAVBElencoTipiAttoResponseReturn alboAVBElencoTipiAttoResponseReturn) {
		this.alboAVBElencoTipiAttoResponseReturn = alboAVBElencoTipiAttoResponseReturn;
	}
}
