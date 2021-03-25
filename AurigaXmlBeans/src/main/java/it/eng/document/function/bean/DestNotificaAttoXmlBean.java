package it.eng.document.function.bean;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class DestNotificaAttoXmlBean {
	
	@NumeroColonna(numero = "1")
	private String indirizzoEmail;

	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}

	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}

}
