package it.eng.document.function.bean.restrepresentation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DatiCertificato implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String agibilita;

	public String getAgibilita() {
		return agibilita;
	}

	public void setAgibilita(String agibilita) {
		this.agibilita = agibilita;
	}

}
