package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PttCopieConoscAnnId generated by hbm2java
 */
@Embeddable
public class PttCopieConoscAnnId implements java.io.Serializable {

	private int idUoAss;
	private int idDoc;
	private Date dtAnn;

	public PttCopieConoscAnnId() {
	}

	public PttCopieConoscAnnId(int idUoAss, int idDoc, Date dtAnn) {
		this.idUoAss = idUoAss;
		this.idDoc = idDoc;
		this.dtAnn = dtAnn;
	}

	@Column(name = "ID_UO_ASS", nullable = false, precision = 8, scale = 0)
	public int getIdUoAss() {
		return this.idUoAss;
	}

	public void setIdUoAss(int idUoAss) {
		this.idUoAss = idUoAss;
	}

	@Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)
	public int getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	@Column(name = "DT_ANN", nullable = false, length = 7)
	public Date getDtAnn() {
		return this.dtAnn;
	}

	public void setDtAnn(Date dtAnn) {
		this.dtAnn = dtAnn;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PttCopieConoscAnnId))
			return false;
		PttCopieConoscAnnId castOther = (PttCopieConoscAnnId) other;

		return (this.getIdUoAss() == castOther.getIdUoAss())
				&& (this.getIdDoc() == castOther.getIdDoc())
				&& ((this.getDtAnn() == castOther.getDtAnn()) || (this
						.getDtAnn() != null && castOther.getDtAnn() != null && this
						.getDtAnn().equals(castOther.getDtAnn())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdUoAss();
		result = 37 * result + this.getIdDoc();
		result = 37 * result
				+ (getDtAnn() == null ? 0 : this.getDtAnn().hashCode());
		return result;
	}

}
