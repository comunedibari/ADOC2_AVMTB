package it.eng.auriga.repository2.jaxws.webservices.updfolder;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSUpdFolderBean implements Serializable {

	
	private String idFolder;
	private String xml;
	
	public String getIdFolder() {
		return idFolder;
	}
	public String getXml() {
		return xml;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	

	}
