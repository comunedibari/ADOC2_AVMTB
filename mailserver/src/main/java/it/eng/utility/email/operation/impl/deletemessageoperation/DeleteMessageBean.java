package it.eng.utility.email.operation.impl.deletemessageoperation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean contenente l'informazione di riusciuta copia del messaggio
 * 
 * @author michele
 *
 */
@XmlRootElement
public class DeleteMessageBean {

	private Boolean deleteok;

	private Boolean notFound = false;

	private Boolean duplicate = false;

	private Boolean planned = false;

	public Boolean getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(Boolean duplicate) {
		this.duplicate = duplicate;
	}

	public Boolean getDeleteok() {
		return deleteok;
	}

	public void setDeleteok(Boolean deleteok) {
		this.deleteok = deleteok;
	}

	public Boolean getNotFound() {
		return notFound;
	}

	public void setNotFound(Boolean notFound) {
		this.notFound = notFound;
	}

	public Boolean getPlanned() {
		return planned;
	}

	public void setPlanned(Boolean planned) {
		this.planned = planned;
	}

}