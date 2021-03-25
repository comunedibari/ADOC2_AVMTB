
package it.eng.auriga.repository2.jaxws.webservices.delunitadoc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSDeleteUdBean implements Serializable {

	private String idUd;
	private String flgTipoDel;
	
	public String getIdUd() {
		return idUd;
	}
	public String getFlgTipoDel() {
		return flgTipoDel;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public void setFlgTipoDel(String flgTipoDel) {
		this.flgTipoDel = flgTipoDel;
	}
	}