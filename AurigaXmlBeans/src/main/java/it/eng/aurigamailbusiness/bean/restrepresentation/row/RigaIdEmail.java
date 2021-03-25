package it.eng.aurigamailbusiness.bean.restrepresentation.row;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="datiEmail")
public class RigaIdEmail {

	@NumeroColonna(numero="1")
	@XmlElement(name="idEmail")
	private String idEmail;
	
//	@NumeroColonna(numero="2")
//	@XmlElement(name="progressivoMessaggio")
//	private String progressivoMessaggio;
	
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
//	public String getProgressivoMessaggio() {
//		return progressivoMessaggio;
//	}
//	public void setProgressivoMessaggio(String progressivoMessaggio) {
//		this.progressivoMessaggio = progressivoMessaggio;
//	}
	
}//RigaIdEmail
