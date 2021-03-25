package it.eng.utility.email.operation.impl.archiveoperation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean contenente le infomazioni dell'inserimento in archivio
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class MessageArchiveBean {

	private Boolean isok;

	private String uri;

	private Boolean duplicated;

	public MessageArchiveBean() {
	}

	public MessageArchiveBean(Boolean ok, String uri) {
		setIsok(ok);
		setUri(uri);
	}

	public Boolean getIsok() {
		return isok;
	}

	public void setIsok(Boolean isok) {
		this.isok = isok;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Boolean getDuplicated() {
		return duplicated;
	}

	public void setDuplicated(Boolean duplicated) {
		this.duplicated = duplicated;
	}

}
