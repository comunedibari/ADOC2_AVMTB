package it.eng.utility.email.operation.impl.checkmessageinfolderoperation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean contenente l'informazione di riusciuta verifica del messaggio
 * 
 * @author mzanetti
 *
 */
@XmlRootElement
public class CheckMessageInFolderOperationBean {

	private Boolean checkok;

	public Boolean getCheckok() {
		return checkok;
	}

	public void setCheckok(Boolean checkok) {
		this.checkok = checkok;
	}

}