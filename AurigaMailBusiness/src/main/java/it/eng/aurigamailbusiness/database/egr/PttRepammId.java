package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PttRepammId generated by hbm2java
 */
@Embeddable
public class PttRepammId implements java.io.Serializable {

	private int idUo;
	private String codEnte;
	private String tpRepertorio;

	public PttRepammId() {
	}

	public PttRepammId(int idUo, String codEnte, String tpRepertorio) {
		this.idUo = idUo;
		this.codEnte = codEnte;
		this.tpRepertorio = tpRepertorio;
	}

	@Column(name = "ID_UO", nullable = false, precision = 8, scale = 0)
	public int getIdUo() {
		return this.idUo;
	}

	public void setIdUo(int idUo) {
		this.idUo = idUo;
	}

	@Column(name = "COD_ENTE", nullable = false, length = 3)
	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	@Column(name = "TP_REPERTORIO", nullable = false, length = 5)
	public String getTpRepertorio() {
		return this.tpRepertorio;
	}

	public void setTpRepertorio(String tpRepertorio) {
		this.tpRepertorio = tpRepertorio;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PttRepammId))
			return false;
		PttRepammId castOther = (PttRepammId) other;

		return (this.getIdUo() == castOther.getIdUo())
				&& ((this.getCodEnte() == castOther.getCodEnte()) || (this
						.getCodEnte() != null && castOther.getCodEnte() != null && this
						.getCodEnte().equals(castOther.getCodEnte())))
				&& ((this.getTpRepertorio() == castOther.getTpRepertorio()) || (this
						.getTpRepertorio() != null
						&& castOther.getTpRepertorio() != null && this
						.getTpRepertorio().equals(castOther.getTpRepertorio())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdUo();
		result = 37 * result
				+ (getCodEnte() == null ? 0 : this.getCodEnte().hashCode());
		result = 37
				* result
				+ (getTpRepertorio() == null ? 0 : this.getTpRepertorio()
						.hashCode());
		return result;
	}

}