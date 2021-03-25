package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CvtTempiProc generated by hbm2java
 */
@Entity
@Table(name = "CVT_TEMPI_PROC")
public class CvtTempiProc implements java.io.Serializable {

	private CvtTempiProcId id;
	private CvtProcedimenti cvtProcedimenti;
	private CvtUo cvtUo;
	private CvtAnagPd cvtAnagPd;
	private CvtFasiproc cvtFasiproc;
	private String flgChiusura;
	private String note;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private BigDecimal flgInvioEmail;
	private String testoEmail;

	public CvtTempiProc() {
	}

	public CvtTempiProc(CvtTempiProcId id, CvtProcedimenti cvtProcedimenti,
			CvtFasiproc cvtFasiproc) {
		this.id = id;
		this.cvtProcedimenti = cvtProcedimenti;
		this.cvtFasiproc = cvtFasiproc;
	}

	public CvtTempiProc(CvtTempiProcId id, CvtProcedimenti cvtProcedimenti,
			CvtUo cvtUo, CvtAnagPd cvtAnagPd, CvtFasiproc cvtFasiproc,
			String flgChiusura, String note, Date dtIns, Integer uteIns,
			Date dtUltMod, Integer uteUltMod, BigDecimal flgInvioEmail,
			String testoEmail) {
		this.id = id;
		this.cvtProcedimenti = cvtProcedimenti;
		this.cvtUo = cvtUo;
		this.cvtAnagPd = cvtAnagPd;
		this.cvtFasiproc = cvtFasiproc;
		this.flgChiusura = flgChiusura;
		this.note = note;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.flgInvioEmail = flgInvioEmail;
		this.testoEmail = testoEmail;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idProc", column = @Column(name = "ID_PROC", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "codFase", column = @Column(name = "COD_FASE", nullable = false, length = 2)),
			@AttributeOverride(name = "data", column = @Column(name = "DATA", nullable = false, length = 7)),
			@AttributeOverride(name = "flgTpDt", column = @Column(name = "FLG_TP_DT", nullable = false, length = 1)) })
	public CvtTempiProcId getId() {
		return this.id;
	}

	public void setId(CvtTempiProcId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROC", nullable = false, insertable = false, updatable = false)
	public CvtProcedimenti getCvtProcedimenti() {
		return this.cvtProcedimenti;
	}

	public void setCvtProcedimenti(CvtProcedimenti cvtProcedimenti) {
		this.cvtProcedimenti = cvtProcedimenti;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UO")
	public CvtUo getCvtUo() {
		return this.cvtUo;
	}

	public void setCvtUo(CvtUo cvtUo) {
		this.cvtUo = cvtUo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ANAG")
	public CvtAnagPd getCvtAnagPd() {
		return this.cvtAnagPd;
	}

	public void setCvtAnagPd(CvtAnagPd cvtAnagPd) {
		this.cvtAnagPd = cvtAnagPd;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "ID_TP_PROC", referencedColumnName = "ID_TP_PROC", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "COD_FASE", referencedColumnName = "COD_FASE", nullable = false, insertable = false, updatable = false) })
	public CvtFasiproc getCvtFasiproc() {
		return this.cvtFasiproc;
	}

	public void setCvtFasiproc(CvtFasiproc cvtFasiproc) {
		this.cvtFasiproc = cvtFasiproc;
	}

	@Column(name = "FLG_CHIUSURA", length = 1)
	public String getFlgChiusura() {
		return this.flgChiusura;
	}

	public void setFlgChiusura(String flgChiusura) {
		this.flgChiusura = flgChiusura;
	}

	@Column(name = "NOTE", length = 250)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
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

	@Column(name = "FLG_INVIO_EMAIL", precision = 22, scale = 0)
	public BigDecimal getFlgInvioEmail() {
		return this.flgInvioEmail;
	}

	public void setFlgInvioEmail(BigDecimal flgInvioEmail) {
		this.flgInvioEmail = flgInvioEmail;
	}

	@Column(name = "TESTO_EMAIL", length = 2000)
	public String getTestoEmail() {
		return this.testoEmail;
	}

	public void setTestoEmail(String testoEmail) {
		this.testoEmail = testoEmail;
	}

}
