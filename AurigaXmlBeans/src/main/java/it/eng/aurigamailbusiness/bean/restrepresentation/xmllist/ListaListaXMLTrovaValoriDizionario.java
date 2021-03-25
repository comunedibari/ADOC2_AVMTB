package it.eng.aurigamailbusiness.bean.restrepresentation.xmllist;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaListaXMLTrovaValoriDizionario;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="valoriDizionario")
public class ListaListaXMLTrovaValoriDizionario {
	
	@XmlElement(name="valoreDizionario")
	private List<RigaListaXMLTrovaValoriDizionario> items = new ArrayList<>();

	public List<RigaListaXMLTrovaValoriDizionario> getItems() {
		return items;
	}
	public void setItems(List<RigaListaXMLTrovaValoriDizionario> items) {
		this.items = items;
	}

}
