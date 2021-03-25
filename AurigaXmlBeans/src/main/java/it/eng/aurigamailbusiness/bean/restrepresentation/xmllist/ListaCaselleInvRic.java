package it.eng.aurigamailbusiness.bean.restrepresentation.xmllist;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaCaselleInvRic;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "caselleEmail")
public class ListaCaselleInvRic {

	@XmlElement(name = "casellaEmail")
	private List<RigaCaselleInvRic> items;

	public List<RigaCaselleInvRic> getItems() {
		if (items == null) {
			items = new ArrayList<>();
		}
		return items;
	}

	public void setItems(List<RigaCaselleInvRic> items) {
		this.items = items;
	}

}
