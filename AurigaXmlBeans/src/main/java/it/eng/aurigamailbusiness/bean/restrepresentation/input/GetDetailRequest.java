package it.eng.aurigamailbusiness.bean.restrepresentation.input;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="getDetailRequest")
public class GetDetailRequest extends MutualInput {

	private static final long serialVersionUID = 1L;
	
	@XmlElement(required=true)
	private String idEmail;

	
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
}//GetDetailRequest
