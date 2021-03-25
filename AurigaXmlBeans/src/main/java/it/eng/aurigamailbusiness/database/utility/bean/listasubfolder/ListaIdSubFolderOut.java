package it.eng.aurigamailbusiness.database.utility.bean.listasubfolder;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListaIdSubFolderOut implements Serializable {

	private static final long serialVersionUID = -4049296661887779505L;
	private List<String> folders;

	public void setFolders(List<String> folders) {
		this.folders = folders;
	}

	public List<String> getFolders() {
		return folders;
	}
}
