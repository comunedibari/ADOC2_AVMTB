package it.eng.aurigamailbusiness.database.mail;
// Generated 14-apr-2017 14.35.28 by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MailboxInfoId generated by hbm2java
 */
@Embeddable
public class MailboxInfoId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4608107077349563133L;
	private String infoKey;
	private String idMailbox;

	public MailboxInfoId() {
	}

	public MailboxInfoId(String infoKey, String idMailbox) {
		this.infoKey = infoKey;
		this.idMailbox = idMailbox;
	}

	@Column(name = "INFO_KEY", nullable = false, length = 100)
	public String getInfoKey() {
		return this.infoKey;
	}

	public void setInfoKey(String infoKey) {
		this.infoKey = infoKey;
	}

	@Column(name = "ID_MAILBOX", nullable = false, length = 40)
	public String getIdMailbox() {
		return this.idMailbox;
	}

	public void setIdMailbox(String idMailbox) {
		this.idMailbox = idMailbox;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MailboxInfoId))
			return false;
		MailboxInfoId castOther = (MailboxInfoId) other;

		return ((this.getInfoKey() == castOther.getInfoKey())
				|| (this.getInfoKey() != null && castOther.getInfoKey() != null && this.getInfoKey().equals(castOther.getInfoKey())))
				&& ((this.getIdMailbox() == castOther.getIdMailbox())
						|| (this.getIdMailbox() != null && castOther.getIdMailbox() != null && this.getIdMailbox().equals(castOther.getIdMailbox())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getInfoKey() == null ? 0 : this.getInfoKey().hashCode());
		result = 37 * result + (getIdMailbox() == null ? 0 : this.getIdMailbox().hashCode());
		return result;
	}

}