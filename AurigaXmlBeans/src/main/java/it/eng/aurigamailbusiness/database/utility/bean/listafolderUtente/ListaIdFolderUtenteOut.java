package it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean di output per il metodo ListaIdFolderUtente
 * @author Rametta
 *
 */
@XmlRootElement
public class ListaIdFolderUtenteOut implements Serializable{

	private static final long serialVersionUID = 2692928925710435540L;
	private List<String> listIdFolders;

	public void setListIdFolders(List<String> listIdFolders) {
		this.listIdFolders = listIdFolders;
	}

	public List<String> getListIdFolders() {
		return listIdFolders;
	}
}
