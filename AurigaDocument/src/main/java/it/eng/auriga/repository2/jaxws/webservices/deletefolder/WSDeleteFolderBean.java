package it.eng.auriga.repository2.jaxws.webservices.deletefolder;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSDeleteFolderBean implements Serializable {

	
	private String idFolder;
	private String flgTipoDel;
	
	

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getFlgTipoDel() {
		return flgTipoDel;
	}

	public void setFlgTipoDel(String flgTipoDel) {
		this.flgTipoDel = flgTipoDel;
	}
	

	}
