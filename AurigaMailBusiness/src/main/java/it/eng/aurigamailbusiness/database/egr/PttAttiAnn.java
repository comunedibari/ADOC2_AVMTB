package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * PttAttiAnn generated by hbm2java
 */
@Entity
@Table(name = "PTT_ATTI_ANN")
public class PttAttiAnn implements java.io.Serializable {

	private int idAttoAnn;
	private PttDocumenti pttDocumenti;
	private String desAtto;
	private Date dtAtto;
	private String flgTpAnn;
	private String note;
	private String flgAnn;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private String codEnte;
	private Set<PttNomdocAnn> pttNomdocAnns = new HashSet<PttNomdocAnn>(0);
	private Set<PttDocAnnullabili> pttDocAnnullabilis = new HashSet<PttDocAnnullabili>(
			0);
	private Set<PttInformAnn> pttInformAnns = new HashSet<PttInformAnn>(0);
	private Set<PttDocumenti> pttDocumentis = new HashSet<PttDocumenti>(0);

	public PttAttiAnn() {
	}

	public PttAttiAnn(PttDocumenti pttDocumenti, String desAtto, Date dtAtto,
			String flgTpAnn) {
		this.pttDocumenti = pttDocumenti;
		this.desAtto = desAtto;
		this.dtAtto = dtAtto;
		this.flgTpAnn = flgTpAnn;
	}

	public PttAttiAnn(PttDocumenti pttDocumenti, String desAtto, Date dtAtto,
			String flgTpAnn, String note, String flgAnn, Date dtIns,
			Integer uteIns, Date dtUltMod, Integer uteUltMod, String codEnte,
			Set<PttNomdocAnn> pttNomdocAnns,
			Set<PttDocAnnullabili> pttDocAnnullabilis,
			Set<PttInformAnn> pttInformAnns, Set<PttDocumenti> pttDocumentis) {
		this.pttDocumenti = pttDocumenti;
		this.desAtto = desAtto;
		this.dtAtto = dtAtto;
		this.flgTpAnn = flgTpAnn;
		this.note = note;
		this.flgAnn = flgAnn;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.codEnte = codEnte;
		this.pttNomdocAnns = pttNomdocAnns;
		this.pttDocAnnullabilis = pttDocAnnullabilis;
		this.pttInformAnns = pttInformAnns;
		this.pttDocumentis = pttDocumentis;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "pttDocumenti"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID_ATTO_ANN", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdAttoAnn() {
		return this.idAttoAnn;
	}

	public void setIdAttoAnn(int idAttoAnn) {
		this.idAttoAnn = idAttoAnn;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public PttDocumenti getPttDocumenti() {
		return this.pttDocumenti;
	}

	public void setPttDocumenti(PttDocumenti pttDocumenti) {
		this.pttDocumenti = pttDocumenti;
	}

	@Column(name = "DES_ATTO", nullable = false, length = 500)
	public String getDesAtto() {
		return this.desAtto;
	}

	public void setDesAtto(String desAtto) {
		this.desAtto = desAtto;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ATTO", nullable = false, length = 7)
	public Date getDtAtto() {
		return this.dtAtto;
	}

	public void setDtAtto(Date dtAtto) {
		this.dtAtto = dtAtto;
	}

	@Column(name = "FLG_TP_ANN", nullable = false, length = 1)
	public String getFlgTpAnn() {
		return this.flgTpAnn;
	}

	public void setFlgTpAnn(String flgTpAnn) {
		this.flgTpAnn = flgTpAnn;
	}

	@Column(name = "NOTE", length = 250)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "FLG_ANN", length = 1)
	public String getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(String flgAnn) {
		this.flgAnn = flgAnn;
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

	@Column(name = "COD_ENTE", length = 3)
	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttAttiAnn")
	public Set<PttNomdocAnn> getPttNomdocAnns() {
		return this.pttNomdocAnns;
	}

	public void setPttNomdocAnns(Set<PttNomdocAnn> pttNomdocAnns) {
		this.pttNomdocAnns = pttNomdocAnns;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttAttiAnn")
	public Set<PttDocAnnullabili> getPttDocAnnullabilis() {
		return this.pttDocAnnullabilis;
	}

	public void setPttDocAnnullabilis(Set<PttDocAnnullabili> pttDocAnnullabilis) {
		this.pttDocAnnullabilis = pttDocAnnullabilis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttAttiAnn")
	public Set<PttInformAnn> getPttInformAnns() {
		return this.pttInformAnns;
	}

	public void setPttInformAnns(Set<PttInformAnn> pttInformAnns) {
		this.pttInformAnns = pttInformAnns;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttAttiAnn")
	public Set<PttDocumenti> getPttDocumentis() {
		return this.pttDocumentis;
	}

	public void setPttDocumentis(Set<PttDocumenti> pttDocumentis) {
		this.pttDocumentis = pttDocumentis;
	}

}
