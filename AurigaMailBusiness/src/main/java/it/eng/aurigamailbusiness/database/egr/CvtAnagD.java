package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * CvtAnagD generated by hbm2java
 */
@Entity
@Table(name = "CVT_ANAG_D")
public class CvtAnagD implements java.io.Serializable {

	private int idAnag;
	private CvtAnagPd cvtAnagPd;
	private String flgStato;
	private Date dtCost;
	private Date dtCess;
	private Date dtFal;
	private String prvRea;
	private Integer numRea;
	private Date dtRea;
	private String prvRi;
	private Integer numRi;
	private Date dtRi;
	private String prvRec;
	private Integer numRec;
	private Date dtRec;
	private String codCausCess;
	private Integer codTpDit;
	private String prvRecAmb;
	private String prvRecSom;
	private Integer numRecAmb;
	private Date dtRecAmb;
	private Integer numRecSom;
	private Date dtRecSom;
	private String prvRecOpe;
	private Integer numRecOpe;
	private Date dtRecOpe;
	private String prvRecTur;
	private Integer numRecTur;
	private Date dtRecTur;
	private String prvRecPrep;
	private Integer numRecPrep;
	private Date dtRecPrep;

	public CvtAnagD() {
	}

	public CvtAnagD(CvtAnagPd cvtAnagPd) {
		this.cvtAnagPd = cvtAnagPd;
	}

	public CvtAnagD(CvtAnagPd cvtAnagPd, String flgStato, Date dtCost,
			Date dtCess, Date dtFal, String prvRea, Integer numRea, Date dtRea,
			String prvRi, Integer numRi, Date dtRi, String prvRec,
			Integer numRec, Date dtRec, String codCausCess, Integer codTpDit,
			String prvRecAmb, String prvRecSom, Integer numRecAmb,
			Date dtRecAmb, Integer numRecSom, Date dtRecSom, String prvRecOpe,
			Integer numRecOpe, Date dtRecOpe, String prvRecTur,
			Integer numRecTur, Date dtRecTur, String prvRecPrep,
			Integer numRecPrep, Date dtRecPrep) {
		this.cvtAnagPd = cvtAnagPd;
		this.flgStato = flgStato;
		this.dtCost = dtCost;
		this.dtCess = dtCess;
		this.dtFal = dtFal;
		this.prvRea = prvRea;
		this.numRea = numRea;
		this.dtRea = dtRea;
		this.prvRi = prvRi;
		this.numRi = numRi;
		this.dtRi = dtRi;
		this.prvRec = prvRec;
		this.numRec = numRec;
		this.dtRec = dtRec;
		this.codCausCess = codCausCess;
		this.codTpDit = codTpDit;
		this.prvRecAmb = prvRecAmb;
		this.prvRecSom = prvRecSom;
		this.numRecAmb = numRecAmb;
		this.dtRecAmb = dtRecAmb;
		this.numRecSom = numRecSom;
		this.dtRecSom = dtRecSom;
		this.prvRecOpe = prvRecOpe;
		this.numRecOpe = numRecOpe;
		this.dtRecOpe = dtRecOpe;
		this.prvRecTur = prvRecTur;
		this.numRecTur = numRecTur;
		this.dtRecTur = dtRecTur;
		this.prvRecPrep = prvRecPrep;
		this.numRecPrep = numRecPrep;
		this.dtRecPrep = dtRecPrep;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "cvtAnagPd"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID_ANAG", unique = true, nullable = false, precision = 9, scale = 0)
	public int getIdAnag() {
		return this.idAnag;
	}

	public void setIdAnag(int idAnag) {
		this.idAnag = idAnag;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public CvtAnagPd getCvtAnagPd() {
		return this.cvtAnagPd;
	}

	public void setCvtAnagPd(CvtAnagPd cvtAnagPd) {
		this.cvtAnagPd = cvtAnagPd;
	}

	@Column(name = "FLG_STATO", length = 1)
	public String getFlgStato() {
		return this.flgStato;
	}

	public void setFlgStato(String flgStato) {
		this.flgStato = flgStato;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_COST", length = 7)
	public Date getDtCost() {
		return this.dtCost;
	}

	public void setDtCost(Date dtCost) {
		this.dtCost = dtCost;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_CESS", length = 7)
	public Date getDtCess() {
		return this.dtCess;
	}

	public void setDtCess(Date dtCess) {
		this.dtCess = dtCess;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FAL", length = 7)
	public Date getDtFal() {
		return this.dtFal;
	}

	public void setDtFal(Date dtFal) {
		this.dtFal = dtFal;
	}

	@Column(name = "PRV_REA", length = 2)
	public String getPrvRea() {
		return this.prvRea;
	}

	public void setPrvRea(String prvRea) {
		this.prvRea = prvRea;
	}

	@Column(name = "NUM_REA", precision = 8, scale = 0)
	public Integer getNumRea() {
		return this.numRea;
	}

	public void setNumRea(Integer numRea) {
		this.numRea = numRea;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_REA", length = 7)
	public Date getDtRea() {
		return this.dtRea;
	}

	public void setDtRea(Date dtRea) {
		this.dtRea = dtRea;
	}

	@Column(name = "PRV_RI", length = 2)
	public String getPrvRi() {
		return this.prvRi;
	}

	public void setPrvRi(String prvRi) {
		this.prvRi = prvRi;
	}

	@Column(name = "NUM_RI", precision = 8, scale = 0)
	public Integer getNumRi() {
		return this.numRi;
	}

	public void setNumRi(Integer numRi) {
		this.numRi = numRi;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_RI", length = 7)
	public Date getDtRi() {
		return this.dtRi;
	}

	public void setDtRi(Date dtRi) {
		this.dtRi = dtRi;
	}

	@Column(name = "PRV_REC", length = 2)
	public String getPrvRec() {
		return this.prvRec;
	}

	public void setPrvRec(String prvRec) {
		this.prvRec = prvRec;
	}

	@Column(name = "NUM_REC", precision = 8, scale = 0)
	public Integer getNumRec() {
		return this.numRec;
	}

	public void setNumRec(Integer numRec) {
		this.numRec = numRec;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_REC", length = 7)
	public Date getDtRec() {
		return this.dtRec;
	}

	public void setDtRec(Date dtRec) {
		this.dtRec = dtRec;
	}

	@Column(name = "COD_CAUS_CESS", length = 2)
	public String getCodCausCess() {
		return this.codCausCess;
	}

	public void setCodCausCess(String codCausCess) {
		this.codCausCess = codCausCess;
	}

	@Column(name = "COD_TP_DIT", precision = 5, scale = 0)
	public Integer getCodTpDit() {
		return this.codTpDit;
	}

	public void setCodTpDit(Integer codTpDit) {
		this.codTpDit = codTpDit;
	}

	@Column(name = "PRV_REC_AMB", length = 2)
	public String getPrvRecAmb() {
		return this.prvRecAmb;
	}

	public void setPrvRecAmb(String prvRecAmb) {
		this.prvRecAmb = prvRecAmb;
	}

	@Column(name = "PRV_REC_SOM", length = 2)
	public String getPrvRecSom() {
		return this.prvRecSom;
	}

	public void setPrvRecSom(String prvRecSom) {
		this.prvRecSom = prvRecSom;
	}

	@Column(name = "NUM_REC_AMB", precision = 8, scale = 0)
	public Integer getNumRecAmb() {
		return this.numRecAmb;
	}

	public void setNumRecAmb(Integer numRecAmb) {
		this.numRecAmb = numRecAmb;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_REC_AMB", length = 7)
	public Date getDtRecAmb() {
		return this.dtRecAmb;
	}

	public void setDtRecAmb(Date dtRecAmb) {
		this.dtRecAmb = dtRecAmb;
	}

	@Column(name = "NUM_REC_SOM", precision = 8, scale = 0)
	public Integer getNumRecSom() {
		return this.numRecSom;
	}

	public void setNumRecSom(Integer numRecSom) {
		this.numRecSom = numRecSom;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_REC_SOM", length = 7)
	public Date getDtRecSom() {
		return this.dtRecSom;
	}

	public void setDtRecSom(Date dtRecSom) {
		this.dtRecSom = dtRecSom;
	}

	@Column(name = "PRV_REC_OPE", length = 2)
	public String getPrvRecOpe() {
		return this.prvRecOpe;
	}

	public void setPrvRecOpe(String prvRecOpe) {
		this.prvRecOpe = prvRecOpe;
	}

	@Column(name = "NUM_REC_OPE", precision = 8, scale = 0)
	public Integer getNumRecOpe() {
		return this.numRecOpe;
	}

	public void setNumRecOpe(Integer numRecOpe) {
		this.numRecOpe = numRecOpe;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_REC_OPE", length = 7)
	public Date getDtRecOpe() {
		return this.dtRecOpe;
	}

	public void setDtRecOpe(Date dtRecOpe) {
		this.dtRecOpe = dtRecOpe;
	}

	@Column(name = "PRV_REC_TUR", length = 2)
	public String getPrvRecTur() {
		return this.prvRecTur;
	}

	public void setPrvRecTur(String prvRecTur) {
		this.prvRecTur = prvRecTur;
	}

	@Column(name = "NUM_REC_TUR", precision = 8, scale = 0)
	public Integer getNumRecTur() {
		return this.numRecTur;
	}

	public void setNumRecTur(Integer numRecTur) {
		this.numRecTur = numRecTur;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_REC_TUR", length = 7)
	public Date getDtRecTur() {
		return this.dtRecTur;
	}

	public void setDtRecTur(Date dtRecTur) {
		this.dtRecTur = dtRecTur;
	}

	@Column(name = "PRV_REC_PREP", length = 2)
	public String getPrvRecPrep() {
		return this.prvRecPrep;
	}

	public void setPrvRecPrep(String prvRecPrep) {
		this.prvRecPrep = prvRecPrep;
	}

	@Column(name = "NUM_REC_PREP", precision = 8, scale = 0)
	public Integer getNumRecPrep() {
		return this.numRecPrep;
	}

	public void setNumRecPrep(Integer numRecPrep) {
		this.numRecPrep = numRecPrep;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_REC_PREP", length = 7)
	public Date getDtRecPrep() {
		return this.dtRecPrep;
	}

	public void setDtRecPrep(Date dtRecPrep) {
		this.dtRecPrep = dtRecPrep;
	}

}
