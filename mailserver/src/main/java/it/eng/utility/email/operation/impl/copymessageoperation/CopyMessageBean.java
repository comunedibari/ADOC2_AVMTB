package it.eng.utility.email.operation.impl.copymessageoperation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean contenente l'informazione di riusciuta copia del messaggio
 * @author michele
 *
 */
@XmlRootElement
public class CopyMessageBean {

	private Boolean copyok;

	public Boolean getCopyok() {
		return copyok;
	}

	public void setCopyok(Boolean copyok) {
		this.copyok = copyok;
	}	
}