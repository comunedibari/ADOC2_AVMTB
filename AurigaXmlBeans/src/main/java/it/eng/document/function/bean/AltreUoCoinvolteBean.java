package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AltreUoCoinvolteBean implements Serializable{

	@NumeroColonna(numero = "1")
	private String idUo;
	
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	
	
}
