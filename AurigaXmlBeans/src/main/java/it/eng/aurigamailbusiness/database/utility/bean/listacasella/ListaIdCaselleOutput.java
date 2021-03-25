package it.eng.aurigamailbusiness.database.utility.bean.listacasella;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean di output per il metodo getListaCaselleUtente della classe {@link CasellaUtility}.
 * 
 * @author Rametta
 * 
 */
@XmlRootElement
public class ListaIdCaselleOutput implements Serializable {

	private static final long serialVersionUID = -3899500581805898158L;

	private List<InfoCasella> accounts;

	public List<InfoCasella> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<InfoCasella> accounts) {
		this.accounts = accounts;
	}

}
