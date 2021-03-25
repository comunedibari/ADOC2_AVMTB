package it.eng.aurigamailbusiness.bean.restrepresentation.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rispostaCollegaRegToEmail")
public class CollegaRegToEmailRequestResponse {
	
	@XmlElement(name="esito")
	private String esito;

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}
	
}
