package it.eng.aurigamailbusiness.bean.restrepresentation.xmllist;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaEsitiLockEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="esitiLockEmail")
public class ListaEsitiLockEmail {
	
	@XmlElement(name="esitoLockEmail")
	private List<RigaEsitiLockEmail> items = new ArrayList<>();

	public List<RigaEsitiLockEmail> getItems() {
		return items;
	}
	public void setItems(List<RigaEsitiLockEmail> items) {
		this.items = items;
	}

}
