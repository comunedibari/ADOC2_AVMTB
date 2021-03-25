package it.eng.utility.email.operation.impl.copyfilesystemoperation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean contenente l'informazione di riusciuta copia del messaggio
 * @author michele
 *
 */
@XmlRootElement
public class CopyFileSystemMessageBean {

	private Boolean copyok;
	private String uri;

	public Boolean getCopyok() {
		return copyok;
	}

	public void setCopyok(Boolean copyok) {
		this.copyok = copyok;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}	
}