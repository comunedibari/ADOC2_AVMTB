package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CvtFasiproc generated by hbm2java
 */
@Entity
@Table(name = "CVT_FASIPROC")
public class CvtFasiproc implements java.io.Serializable {

	private CvtFasiprocId id;
	private CvtUo cvtUoByIdUoRsp;
	private CvtDecProc cvtDecProc;
	private CvtUo cvtUoByIdUoRspm;
	private String desFase;
	private Short ggDurata;
	private String attInizio;
	private String attFine;
	private String flgNoVis;
	private Date dtInizioVld;
	private Date dtFineVld;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private Set<CvtTempiProc> cvtTempiProcs = new HashSet<CvtTempiProc>(0);

	public CvtFasiproc() {
	}

	public CvtFasiproc(CvtFasiprocId id, CvtDecProc cvtDecProc, String desFase,
			String attInizio, String attFine, Date dtInizioVld) {
		this.id = id;
		this.cvtDecProc = cvtDecProc;
		this.desFase = desFase;
		this.attInizio = attInizio;
		this.attFine = attFine;
		this.dtInizioVld = dtInizioVld;
	}

	public CvtFasiproc(CvtFasiprocId id, CvtUo cvtUoByIdUoRsp,
			CvtDecProc cvtDecProc, CvtUo cvtUoByIdUoRspm, String desFase,
			Short ggDurata, String attInizio, String attFine, String flgNoVis,
			Date dtInizioVld, Date dtFineVld, Date dtIns, Integer uteIns,
			Date dtUltMod, Integer uteUltMod, Set<CvtTempiProc> cvtTempiProcs) {
		this.id = id;
		this.cvtUoByIdUoRsp = cvtUoByIdUoRsp;
		this.cvtDecProc = cvtDecProc;
		this.cvtUoByIdUoRspm = cvtUoByIdUoRspm;
		this.desFase = desFase;
		this.ggDurata = ggDurata;
		this.attInizio = attInizio;
		this.attFine = attFine;
		this.flgNoVis = flgNoVis;
		this.dtInizioVld = dtInizioVld;
		this.dtFineVld = dtFineVld;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.cvtTempiProcs = cvtTempiProcs;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idTpProc", column = @Column(name = "ID_TP_PROC", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "codFase", column = @Column(name = "COD_FASE", nullable = false, length = 2)) })
	public CvtFasiprocId getId() {
		return this.id;
	}

	public void setId(CvtFasiprocId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UO_RSP")
	public CvtUo getCvtUoByIdUoRsp() {
		return this.cvtUoByIdUoRsp;
	}

	public void setCvtUoByIdUoRsp(CvtUo cvtUoByIdUoRsp) {
		this.cvtUoByIdUoRsp = cvtUoByIdUoRsp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TP_PROC", nullable = false, insertable = false, updatable = false)
	public CvtDecProc getCvtDecProc() {
		return this.cvtDecProc;
	}

	public void setCvtDecProc(CvtDecProc cvtDecProc) {
		this.cvtDecProc = cvtDecProc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UO_RSPM")
	public CvtUo getCvtUoByIdUoRspm() {
		return this.cvtUoByIdUoRspm;
	}

	public void setCvtUoByIdUoRspm(CvtUo cvtUoByIdUoRspm) {
		this.cvtUoByIdUoRspm = cvtUoByIdUoRspm;
	}

	@Column(name = "DES_FASE", nullable = false, length = 200)
	public String getDesFase() {
		return this.desFase;
	}

	public void setDesFase(String desFase) {
		this.desFase = desFase;
	}

	@Column(name = "GG_DURATA", precision = 3, scale = 0)
	public Short getGgDurata() {
		return this.ggDurata;
	}

	public void setGgDurata(Short ggDurata) {
		this.ggDurata = ggDurata;
	}

	@Column(name = "ATT_INIZIO", nullable = false, length = 30)
	public String getAttInizio() {
		return this.attInizio;
	}

	public void setAttInizio(String attInizio) {
		this.attInizio = attInizio;
	}

	@Column(name = "ATT_FINE", nullable = false, length = 30)
	public String getAttFine() {
		return this.attFine;
	}

	public void setAttFine(String attFine) {
		this.attFine = attFine;
	}

	@Column(name = "FLG_NO_VIS", length = 1)
	public String getFlgNoVis() {
		return this.flgNoVis;
	}

	public void setFlgNoVis(String flgNoVis) {
		this.flgNoVis = flgNoVis;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INIZIO_VLD", nullable = false, length = 7)
	public Date getDtInizioVld() {
		return this.dtInizioVld;
	}

	public void setDtInizioVld(Date dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FINE_VLD", length = 7)
	public Date getDtFineVld() {
		return this.dtFineVld;
	}

	public void setDtFineVld(Date dtFineVld) {
		this.dtFineVld = dtFineVld;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cvtFasiproc")
	public Set<CvtTempiProc> getCvtTempiProcs() {
		return this.cvtTempiProcs;
	}

	public void setCvtTempiProcs(Set<CvtTempiProc> cvtTempiProcs) {
		this.cvtTempiProcs = cvtTempiProcs;
	}

}
