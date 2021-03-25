package it.eng.aurigamailbusiness.bean.restrepresentation.xmllist;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaXMLTagTagEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="tagEmails")
public class ListaXMLTagTagEmail {
	
	@XmlElement(name="tagEmail")
	private List<RigaXMLTagTagEmail> items = new ArrayList<>();

	public List<RigaXMLTagTagEmail> getItems() {
		return items;
	}
	public void setItems(List<RigaXMLTagTagEmail> items) {
		this.items = items;
	}

}
