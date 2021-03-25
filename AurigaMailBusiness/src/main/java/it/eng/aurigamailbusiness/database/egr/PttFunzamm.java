package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PttFunzamm generated by hbm2java
 */
@Entity
@Table(name = "PTT_FUNZAMM")
public class PttFunzamm implements java.io.Serializable {

	private PttFunzammId id;
	private PttFunzioni pttFunzioni;
	private CvtUo cvtUo;
	private String flgModifica;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;

	public PttFunzamm() {
	}

	public PttFunzamm(PttFunzammId id, PttFunzioni pttFunzioni, CvtUo cvtUo) {
		this.id = id;
		this.pttFunzioni = pttFunzioni;
		this.cvtUo = cvtUo;
	}

	public PttFunzamm(PttFunzammId id, PttFunzioni pttFunzioni, CvtUo cvtUo,
			String flgModifica, Date dtIns, Integer uteIns, Date dtUltMod,
			Integer uteUltMod) {
		this.id = id;
		this.pttFunzioni = pttFunzioni;
		this.cvtUo = cvtUo;
		this.flgModifica = flgModifica;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codFunz", column = @Column(name = "COD_FUNZ", nullable = false, length = 5)),
			@AttributeOverride(name = "idUo", column = @Column(name = "ID_UO", nullable = false, precision = 8, scale = 0)) })
	public PttFunzammId getId() {
		return this.id;
	}

	public void setId(PttFunzammId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_FUNZ", nullable = false, insertable = false, updatable = false)
	public PttFunzioni getPttFunzioni() {
		return this.pttFunzioni;
	}

	public void setPttFunzioni(PttFunzioni pttFunzioni) {
		this.pttFunzioni = pttFunzioni;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UO", nullable = false, insertable = false, updatable = false)
	public CvtUo getCvtUo() {
		return this.cvtUo;
	}

	public void setCvtUo(CvtUo cvtUo) {
		this.cvtUo = cvtUo;
	}

	@Column(name = "FLG_MODIFICA", length = 1)
	public String getFlgModifica() {
		return this.flgModifica;
	}

	public void setFlgModifica(String flgModifica) {
		this.flgModifica = flgModifica;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS", length = 7)
	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	@Column(name = "UTE_INS", precision = 6, scale = 0)
	public Integer getUteIns() {
		return this.uteIns;
	}

	public void setUteIns(Integer uteIns) {
		this.uteIns = uteIns;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ULT_MOD", length = 7)
	public Date getDtUltMod() {
		return this.dtUltMod;
	}

	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	@Column(name = "UTE_ULT_MOD", precision = 6, scale = 0)
	public Integer getUteUltMod() {
		return this.uteUltMod;
	}

	public void setUteUltMod(Integer uteUltMod) {
		this.uteUltMod = uteUltMod;
	}

}
