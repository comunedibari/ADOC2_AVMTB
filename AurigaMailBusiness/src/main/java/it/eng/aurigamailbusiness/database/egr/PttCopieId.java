package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PttCopieId generated by hbm2java
 */
@Embeddable
public class PttCopieId implements java.io.Serializable {

	private int idDoc;
	private short numCopia;

	public PttCopieId() {
	}

	public PttCopieId(int idDoc, short numCopia) {
		this.idDoc = idDoc;
		this.numCopia = numCopia;
	}

	@Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)
	public int getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	@Column(name = "NUM_COPIA", nullable = false, precision = 4, scale = 0)
	public short getNumCopia() {
		return this.numCopia;
	}

	public void setNumCopia(short numCopia) {
		this.numCopia = numCopia;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PttCopieId))
			return false;
		PttCopieId castOther = (PttCopieId) other;

		return (this.getIdDoc() == castOther.getIdDoc())
				&& (this.getNumCopia() == castOther.getNumCopia());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdDoc();
		result = 37 * result + this.getNumCopia();
		return result;
	}

}
