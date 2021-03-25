package it.eng.aurigamailbusiness.bean.restrepresentation.input;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaAssegnatariXMLAssegnaEmail;

@XmlRootElement(name="richiestaAssegnazioneEmail")
public class AssignRequest extends MutualInput {
		
	@XmlElement(name="assegnatariEmail",required=true)
	@ApiModelProperty(name="assegnatariEmail")
	private ListaAssegnatariXMLAssegnaEmail listaAssegnatariXML = new ListaAssegnatariXMLAssegnaEmail();
	
	@XmlElement(name="idEmail", required=true)
	private String idEmail;
	
	public ListaAssegnatariXMLAssegnaEmail getListaAssegnatariXML() {
		return listaAssegnatariXML;
	}
	public void setListaAssegnatariXML(ListaAssegnatariXMLAssegnaEmail listaAssegnatariXML) {
		this.listaAssegnatariXML = listaAssegnatariXML;
	}
	
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
}//AssignRequest
