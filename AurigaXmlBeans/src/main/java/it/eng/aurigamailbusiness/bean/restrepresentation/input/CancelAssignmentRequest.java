package it.eng.aurigamailbusiness.bean.restrepresentation.input;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="richiestaAnnullaAssegnazioneEmail")
public class CancelAssignmentRequest extends MutualInput {
	
	@XmlElement(name="idEmail", required=true)
	private String idEmail;
	
	@XmlElement(name="motivi")
	private String motivi;
	
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
	public String getMotivi() {
		return motivi;
	}
	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}
	
}//CancelAssignmentRequest
