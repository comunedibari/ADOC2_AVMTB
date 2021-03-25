package it.eng.utility.cryptosigner.storage.impl.database.hibernate;

// Generated 29-Apr-2010 14:57:22 by Hibernate Tools 3.2.4.GA

import java.util.Date;

/**
 * Certificate generated by hbm2java
 */
public class Certificate implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subjectdn;
	private Date expirationDate;
	private byte[] certificate;
	private Boolean active;

	public Certificate() {
	}

	public Certificate(String subjectdn, Date expirationDate, byte[] certificate) {
		this.subjectdn = subjectdn;
		this.expirationDate = expirationDate;
		this.certificate = certificate;
	}

	public Certificate(String subjectdn, Date expirationDate,
			byte[] certificate, Boolean active) {
		this.subjectdn = subjectdn;
		this.expirationDate = expirationDate;
		this.certificate = certificate;
		this.active = active;
	}

	public String getSubjectdn() {
		return this.subjectdn;
	}

	public void setSubjectdn(String subjectdn) {
		this.subjectdn = subjectdn;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public byte[] getCertificate() {
		return this.certificate;
	}

	public void setCertificate(byte[] certificate) {
		this.certificate = certificate;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
