package it.eng.aurigamailbusiness.bean.restrepresentation.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaEsitiTagEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rispostaTagEmail")
public class TagResponse {
	
	@XmlElement(name="esitiTagEmail")
	@ApiModelProperty(name="esitiTagEmail")
	private ListaEsitiTagEmail esitiTagEmailList;

	public ListaEsitiTagEmail getEsitiTagEmailList() {
		return esitiTagEmailList;
	}
	public void setEsitiTagEmailList(ListaEsitiTagEmail esitiTagEmailList) {
		this.esitiTagEmailList = esitiTagEmailList;
	}
	
}//TagResponse
