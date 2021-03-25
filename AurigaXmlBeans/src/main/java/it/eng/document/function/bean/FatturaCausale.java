package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FatturaCausale implements Serializable {

	private static final long serialVersionUID = 2L;

	@NumeroColonna(numero = "1")
	private String causale;

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

}
