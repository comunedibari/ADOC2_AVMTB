package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class FatturazioneDescrizioneLineaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@NumeroColonna(numero = "1")
	private String identificativoMittente;

	public String getIdentificativoMittente() {
		return identificativoMittente;
	}

	public void setIdentificativoMittente(String identificativoMittente) {
		this.identificativoMittente = identificativoMittente;
	}

}
