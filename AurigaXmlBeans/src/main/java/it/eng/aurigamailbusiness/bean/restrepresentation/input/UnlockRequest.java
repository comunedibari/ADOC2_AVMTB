package it.eng.aurigamailbusiness.bean.restrepresentation.input;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaIdEmail;

@XmlRootElement(name="unlockRequest")
public class UnlockRequest extends MutualInput {
	
	@XmlElement(name="emails",required=true)
	@ApiModelProperty(name="emails", required=true)
	private ListaIdEmail listaIdEmail = new ListaIdEmail();
	
	@XmlElement(name="motivi")
	private String motivi;
	
	public ListaIdEmail getListaIdEmail() {
		return listaIdEmail;
	}
	public void setListaIdEmail(ListaIdEmail listaIdEmail) {
		this.listaIdEmail = listaIdEmail;
	}
	
	public String getMotivi() {
		return motivi;
	}
	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}

}//UnlockRequest
