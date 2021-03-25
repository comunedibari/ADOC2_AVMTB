
package it.eng.auriga.repository2.jaxws.webservices.deleteversion;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSDeleteVersionBean implements Serializable {

	
	private String idDoc;
	private String nroProgrVer;
	private String flgTipoDel;
	private String flgTipoTarget;	
	
	
	
	public String getIdDoc() {
		return idDoc;
	}
	public String getNroProgrVer() {
		return nroProgrVer;
	}
	
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public void setNroProgrVer(String nroProgrVer) {
		this.nroProgrVer = nroProgrVer;
	}
	public String getFlgTipoDel() {
		return flgTipoDel;
	}
	public void setFlgTipoDel(String flgTipoDel) {
		this.flgTipoDel = flgTipoDel;
	}
	public String getFlgTipoTarget() {
		return flgTipoTarget;
	}
	public void setFlgTipoTarget(String flgTipoTarget) {
		this.flgTipoTarget = flgTipoTarget;
	}
	

	

	}
