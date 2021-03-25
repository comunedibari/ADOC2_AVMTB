package it.eng.suiteutility.module.mimedb.entity;

// Generated 29-gen-2013 12.50.59 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TMimetypeFmtDigId generated by hbm2java
 */
@Embeddable
public class TMimetypeFmtDigId implements java.io.Serializable {

	private String idDigFormat;
	private String mimetype;

	public TMimetypeFmtDigId() {
	}

	public TMimetypeFmtDigId(String idDigFormat, String mimetype) {
		this.idDigFormat = idDigFormat;
		this.mimetype = mimetype;
	}

	@Column(name = "ID_DIG_FORMAT", nullable = false, length = 64)
	public String getIdDigFormat() {
		return this.idDigFormat;
	}

	public void setIdDigFormat(String idDigFormat) {
		this.idDigFormat = idDigFormat;
	}

	@Column(name = "MIMETYPE", nullable = false, length = 100)
	public String getMimetype() {
		return this.mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TMimetypeFmtDigId))
			return false;
		TMimetypeFmtDigId castOther = (TMimetypeFmtDigId) other;

		return ((this.getIdDigFormat() == castOther.getIdDigFormat()) || (this
				.getIdDigFormat() != null && castOther.getIdDigFormat() != null && this
				.getIdDigFormat().equals(castOther.getIdDigFormat())))
				&& ((this.getMimetype() == castOther.getMimetype()) || (this
						.getMimetype() != null
						&& castOther.getMimetype() != null && this
						.getMimetype().equals(castOther.getMimetype())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdDigFormat() == null ? 0 : this.getIdDigFormat()
						.hashCode());
		result = 37 * result
				+ (getMimetype() == null ? 0 : this.getMimetype().hashCode());
		return result;
	}

}
