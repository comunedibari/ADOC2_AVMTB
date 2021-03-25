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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PttAttributiOpzDoc generated by hbm2java
 */
@Entity
@Table(name = "PTT_ATTRIBUTI_OPZ_DOC")
public class PttAttributiOpzDoc implements java.io.Serializable {

	private PttAttributiOpzDocId id;
	private PttDocumenti pttDocumenti;
	private PttDefAttributiOpz pttDefAttributiOpz;
	private String valore;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private BigDecimal valorenum;
	private Date valoredata;
	private String valoretext;

	public PttAttributiOpzDoc() {
	}

	public PttAttributiOpzDoc(PttAttributiOpzDocId id,
			PttDocumenti pttDocumenti, PttDefAttributiOpz pttDefAttributiOpz,
			String valore) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
		this.pttDefAttributiOpz = pttDefAttributiOpz;
		this.valore = valore;
	}

	public PttAttributiOpzDoc(PttAttributiOpzDocId id,
			PttDocumenti pttDocumenti, PttDefAttributiOpz pttDefAttributiOpz,
			String valore, Date dtIns, Integer uteIns, Date dtUltMod,
			Integer uteUltMod, BigDecimal valorenum, Date valoredata,
			String valoretext) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
		this.pttDefAttributiOpz = pttDefAttributiOpz;
		this.valore = valore;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.valorenum = valorenum;
		this.valoredata = valoredata;
		this.valoretext = valoretext;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDoc", column = @Column(name = "ID_DOC", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "idAttr", column = @Column(name = "ID_ATTR", nullable = false, precision = 22, scale = 0)) })
	public PttAttributiOpzDocId getId() {
		return this.id;
	}

	public void setId(PttAttributiOpzDocId id) {
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
	@JoinColumn(name = "ID_ATTR", nullable = false, insertable = false, updatable = false)
	public PttDefAttributiOpz getPttDefAttributiOpz() {
		return this.pttDefAttributiOpz;
	}

	public void setPttDefAttributiOpz(PttDefAttributiOpz pttDefAttributiOpz) {
		this.pttDefAttributiOpz = pttDefAttributiOpz;
	}

	@Column(name = "VALORE", nullable = false, length = 4000)
	public String getValore() {
		return this.valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
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

	@Column(name = "VALORENUM", precision = 22, scale = 0)
	public BigDecimal getValorenum() {
		return this.valorenum;
	}

	public void setValorenum(BigDecimal valorenum) {
		this.valorenum = valorenum;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VALOREDATA", length = 7)
	public Date getValoredata() {
		return this.valoredata;
	}

	public void setValoredata(Date valoredata) {
		this.valoredata = valoredata;
	}

	@Column(name = "VALORETEXT", length = 4000)
	public String getValoretext() {
		return this.valoretext;
	}

	public void setValoretext(String valoretext) {
		this.valoretext = valoretext;
	}

}
