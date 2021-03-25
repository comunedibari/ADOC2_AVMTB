package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CvtRelUoId generated by hbm2java
 */
@Embeddable
public class CvtRelUoId implements java.io.Serializable {

	private int idUo;
	private int idUorel;
	private Date dtInizioVld;

	public CvtRelUoId() {
	}

	public CvtRelUoId(int idUo, int idUorel, Date dtInizioVld) {
		this.idUo = idUo;
		this.idUorel = idUorel;
		this.dtInizioVld = dtInizioVld;
	}

	@Column(name = "ID_UO", nullable = false, precision = 8, scale = 0)
	public int getIdUo() {
		return this.idUo;
	}

	public void setIdUo(int idUo) {
		this.idUo = idUo;
	}

	@Column(name = "ID_UOREL", nullable = false, precision = 8, scale = 0)
	public int getIdUorel() {
		return this.idUorel;
	}

	public void setIdUorel(int idUorel) {
		this.idUorel = idUorel;
	}

	@Column(name = "DT_INIZIO_VLD", nullable = false, length = 7)
	public Date getDtInizioVld() {
		return this.dtInizioVld;
	}

	public void setDtInizioVld(Date dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CvtRelUoId))
			return false;
		CvtRelUoId castOther = (CvtRelUoId) other;

		return (this.getIdUo() == castOther.getIdUo())
				&& (this.getIdUorel() == castOther.getIdUorel())
				&& ((this.getDtInizioVld() == castOther.getDtInizioVld()) || (this
						.getDtInizioVld() != null
						&& castOther.getDtInizioVld() != null && this
						.getDtInizioVld().equals(castOther.getDtInizioVld())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdUo();
		result = 37 * result + this.getIdUorel();
		result = 37
				* result
				+ (getDtInizioVld() == null ? 0 : this.getDtInizioVld()
						.hashCode());
		return result;
	}

}
