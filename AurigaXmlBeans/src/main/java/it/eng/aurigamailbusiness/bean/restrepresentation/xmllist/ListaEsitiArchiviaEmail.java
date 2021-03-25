package it.eng.aurigamailbusiness.bean.restrepresentation.xmllist;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaEsitiArchiviaEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="esitiArchiviaEmail")
public class ListaEsitiArchiviaEmail {
	
	@XmlElement(name="esitoArchiviaEmail")
	private List<RigaEsitiArchiviaEmail> items = new ArrayList<>();

	public List<RigaEsitiArchiviaEmail> getItems() {
		return items;
	}
	public void setItems(List<RigaEsitiArchiviaEmail> items) {
		this.items = items;
	}

}
