package it.eng.auriga.repository2.jaxws.webservices.lockfolder;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSLockFolderBean implements Serializable {

	private String xml;
	private String idFolder;
	
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	

	
	
	
}
