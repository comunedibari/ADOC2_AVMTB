package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PttAllegdocId generated by hbm2java
 */
@Embeddable
public class PttAllegdocId implements java.io.Serializable {

	private int idDoc;
	private short numAlleg;

	public PttAllegdocId() {
	}

	public PttAllegdocId(int idDoc, short numAlleg) {
		this.idDoc = idDoc;
		this.numAlleg = numAlleg;
	}

	@Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)
	public int getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	@Column(name = "NUM_ALLEG", nullable = false, precision = 4, scale = 0)
	public short getNumAlleg() {
		return this.numAlleg;
	}

	public void setNumAlleg(short numAlleg) {
		this.numAlleg = numAlleg;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PttAllegdocId))
			return false;
		PttAllegdocId castOther = (PttAllegdocId) other;

		return (this.getIdDoc() == castOther.getIdDoc())
				&& (this.getNumAlleg() == castOther.getNumAlleg());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdDoc();
		result = 37 * result + this.getNumAlleg();
		return result;
	}

}
