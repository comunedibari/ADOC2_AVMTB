package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PttUoassmodId generated by hbm2java
 */
@Embeddable
public class PttUoassmodId implements java.io.Serializable {

	private int idModello;
	private String codEnte;
	private int idUoAss;

	public PttUoassmodId() {
	}

	public PttUoassmodId(int idModello, String codEnte, int idUoAss) {
		this.idModello = idModello;
		this.codEnte = codEnte;
		this.idUoAss = idUoAss;
	}

	@Column(name = "ID_MODELLO", nullable = false, precision = 8, scale = 0)
	public int getIdModello() {
		return this.idModello;
	}

	public void setIdModello(int idModello) {
		this.idModello = idModello;
	}

	@Column(name = "COD_ENTE", nullable = false, length = 3)
	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	@Column(name = "ID_UO_ASS", nullable = false, precision = 8, scale = 0)
	public int getIdUoAss() {
		return this.idUoAss;
	}

	public void setIdUoAss(int idUoAss) {
		this.idUoAss = idUoAss;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PttUoassmodId))
			return false;
		PttUoassmodId castOther = (PttUoassmodId) other;

		return (this.getIdModello() == castOther.getIdModello())
				&& ((this.getCodEnte() == castOther.getCodEnte()) || (this
						.getCodEnte() != null && castOther.getCodEnte() != null && this
						.getCodEnte().equals(castOther.getCodEnte())))
				&& (this.getIdUoAss() == castOther.getIdUoAss());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdModello();
		result = 37 * result
				+ (getCodEnte() == null ? 0 : this.getCodEnte().hashCode());
		result = 37 * result + this.getIdUoAss();
		return result;
	}

}
