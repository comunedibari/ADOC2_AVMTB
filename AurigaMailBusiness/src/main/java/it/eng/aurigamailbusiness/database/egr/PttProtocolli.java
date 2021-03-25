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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PttProtocolli generated by hbm2java
 */
@Entity
@Table(name = "PTT_PROTOCOLLI")
public class PttProtocolli implements java.io.Serializable {

	private PttProtocolliId id;
	private String desTpProt;
	private Short anno;
	private Integer progr;
	private String flgGenerale;
	private Date dtInizioVld;
	private Date dtFineVld;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private Set<PttDocumenti> pttDocumentis = new HashSet<PttDocumenti>(0);

	public PttProtocolli() {
	}

	public PttProtocolli(PttProtocolliId id, String desTpProt, Date dtInizioVld) {
		this.id = id;
		this.desTpProt = desTpProt;
		this.dtInizioVld = dtInizioVld;
	}

	public PttProtocolli(PttProtocolliId id, String desTpProt, Short anno,
			Integer progr, String flgGenerale, Date dtInizioVld,
			Date dtFineVld, Date dtIns, Integer uteIns, Date dtUltMod,
			Integer uteUltMod, Set<PttDocumenti> pttDocumentis) {
		this.id = id;
		this.desTpProt = desTpProt;
		this.anno = anno;
		this.progr = progr;
		this.flgGenerale = flgGenerale;
		this.dtInizioVld = dtInizioVld;
		this.dtFineVld = dtFineVld;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.pttDocumentis = pttDocumentis;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codEnte", column = @Column(name = "COD_ENTE", nullable = false, length = 3)),
			@AttributeOverride(name = "tpProt", column = @Column(name = "TP_PROT", nullable = false, length = 5)) })
	public PttProtocolliId getId() {
		return this.id;
	}

	public void setId(PttProtocolliId id) {
		this.id = id;
	}

	@Column(name = "DES_TP_PROT", nullable = false, length = 150)
	public String getDesTpProt() {
		return this.desTpProt;
	}

	public void setDesTpProt(String desTpProt) {
		this.desTpProt = desTpProt;
	}

	@Column(name = "ANNO", precision = 4, scale = 0)
	public Short getAnno() {
		return this.anno;
	}

	public void setAnno(Short anno) {
		this.anno = anno;
	}

	@Column(name = "PROGR", precision = 7, scale = 0)
	public Integer getProgr() {
		return this.progr;
	}

	public void setProgr(Integer progr) {
		this.progr = progr;
	}

	@Column(name = "FLG_GENERALE", length = 1)
	public String getFlgGenerale() {
		return this.flgGenerale;
	}

	public void setFlgGenerale(String flgGenerale) {
		this.flgGenerale = flgGenerale;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttProtocolli")
	public Set<PttDocumenti> getPttDocumentis() {
		return this.pttDocumentis;
	}

	public void setPttDocumentis(Set<PttDocumenti> pttDocumentis) {
		this.pttDocumentis = pttDocumentis;
	}

}
