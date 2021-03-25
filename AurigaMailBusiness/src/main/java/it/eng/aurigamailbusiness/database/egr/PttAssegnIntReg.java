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
 * PttAssegnIntReg generated by hbm2java
 */
@Entity
@Table(name = "PTT_ASSEGN_INT_REG")
public class PttAssegnIntReg implements java.io.Serializable {

	private PttAssegnIntRegId id;
	private PttDocumenti pttDocumenti;
	private PttIndici pttIndici;
	private CvtUo cvtUo;
	private Integer idAnag;
	private Integer idFascicolo;
	private Integer numSottofasc;
	private Boolean flgCc;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private Short idTpFis;
	private String desAnag;

	public PttAssegnIntReg() {
	}

	public PttAssegnIntReg(PttAssegnIntRegId id, PttDocumenti pttDocumenti) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
	}

	public PttAssegnIntReg(PttAssegnIntRegId id, PttDocumenti pttDocumenti,
			PttIndici pttIndici, CvtUo cvtUo, Integer idAnag,
			Integer idFascicolo, Integer numSottofasc, Boolean flgCc,
			Date dtIns, Integer uteIns, Date dtUltMod, Integer uteUltMod,
			Short idTpFis, String desAnag) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
		this.pttIndici = pttIndici;
		this.cvtUo = cvtUo;
		this.idAnag = idAnag;
		this.idFascicolo = idFascicolo;
		this.numSottofasc = numSottofasc;
		this.flgCc = flgCc;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.idTpFis = idTpFis;
		this.desAnag = desAnag;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDoc", column = @Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "nroPosiz", column = @Column(name = "NRO_POSIZ", nullable = false, precision = 4, scale = 0)) })
	public PttAssegnIntRegId getId() {
		return this.id;
	}

	public void setId(PttAssegnIntRegId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOC", nullable = false, insertable = false, updatable = false)
	public PttDocumenti getPttDocumenti() {
		return this.pttDocumenti;
	}

	public void setPttDocumenti(PttDocumenti pttDocumenti) {
		this.pttDocumenti = pttDocumenti;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INDICE")
	public PttIndici getPttIndici() {
		return this.pttIndici;
	}

	public void setPttIndici(PttIndici pttIndici) {
		this.pttIndici = pttIndici;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UO")
	public CvtUo getCvtUo() {
		return this.cvtUo;
	}

	public void setCvtUo(CvtUo cvtUo) {
		this.cvtUo = cvtUo;
	}

	@Column(name = "ID_ANAG", precision = 8, scale = 0)
	public Integer getIdAnag() {
		return this.idAnag;
	}

	public void setIdAnag(Integer idAnag) {
		this.idAnag = idAnag;
	}

	@Column(name = "ID_FASCICOLO", precision = 8, scale = 0)
	public Integer getIdFascicolo() {
		return this.idFascicolo;
	}

	public void setIdFascicolo(Integer idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	@Column(name = "NUM_SOTTOFASC", precision = 5, scale = 0)
	public Integer getNumSottofasc() {
		return this.numSottofasc;
	}

	public void setNumSottofasc(Integer numSottofasc) {
		this.numSottofasc = numSottofasc;
	}

	@Column(name = "FLG_CC", precision = 1, scale = 0)
	public Boolean getFlgCc() {
		return this.flgCc;
	}

	public void setFlgCc(Boolean flgCc) {
		this.flgCc = flgCc;
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

	@Column(name = "ID_TP_FIS", precision = 4, scale = 0)
	public Short getIdTpFis() {
		return this.idTpFis;
	}

	public void setIdTpFis(Short idTpFis) {
		this.idTpFis = idTpFis;
	}

	@Column(name = "DES_ANAG", length = 150)
	public String getDesAnag() {
		return this.desAnag;
	}

	public void setDesAnag(String desAnag) {
		this.desAnag = desAnag;
	}

}
