package it.eng.document.function.bean.alboavb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBElencoAttiResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBElencoAttiResponseReturn alboAVBElencoAttiResponseReturn;

	public AlboAVBElencoAttiResponse() {
	}

	public AlboAVBElencoAttiResponse(AlboAVBElencoAttiResponseReturn alboAVBElencoAttiResponseReturn) {
		this.alboAVBElencoAttiResponseReturn = alboAVBElencoAttiResponseReturn;
	}

	public AlboAVBElencoAttiResponseReturn getAlboAVBElencoAttiResponseReturn() {
		return alboAVBElencoAttiResponseReturn;
	}

	public void setAlboAVBElencoAttiResponseReturn(AlboAVBElencoAttiResponseReturn alboAVBElencoAttiResponseReturn) {
		this.alboAVBElencoAttiResponseReturn = alboAVBElencoAttiResponseReturn;
	}
}
