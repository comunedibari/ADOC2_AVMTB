package it.eng.auriga.repository2.jaxws.webservices.unlockud;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSUnlockUdBean implements Serializable {

	private String xml;
	private String idUd;
	
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	
	
	
}
