package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IndirizzoEmailDest implements Serializable{

	private static final long serialVersionUID = 7436731644962491928L;
	
	@NumeroColonna(numero = "1")
	private String indirizzoEmail;
	
	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}
	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}
}
