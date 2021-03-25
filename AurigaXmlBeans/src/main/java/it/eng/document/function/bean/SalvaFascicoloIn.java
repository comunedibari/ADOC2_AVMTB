package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SalvaFascicoloIn extends AllegatiBean implements Serializable{

	private XmlFascicoloIn xmlFascicolo;

	public void setXmlFascicolo(XmlFascicoloIn xmlFascicolo) {
		this.xmlFascicolo = xmlFascicolo;
	}

	public XmlFascicoloIn getXmlFascicolo() {
		return xmlFascicolo;
	}
	
	
}
