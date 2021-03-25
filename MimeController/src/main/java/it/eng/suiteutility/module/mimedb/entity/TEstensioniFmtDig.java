package it.eng.suiteutility.module.mimedb.entity;

// Generated 29-gen-2013 12.50.59 by Hibernate Tools 3.4.0.CR1

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TEstensioniFmtDig generated by hbm2java
 */
@Entity
@Table(name = "T_ESTENSIONI_FMT_DIG")
public class TEstensioniFmtDig implements java.io.Serializable {

	private TEstensioniFmtDigId id;
	private TAnagFormatiDig TAnagFormatiDig;
	private Calendar tsIns;
	private String idUteIns;
	private Calendar tsUltimoAgg;
	private String idUteUltimoAgg;
	private Boolean flgAnn;

	public TEstensioniFmtDig() {
	}

	public TEstensioniFmtDig(TEstensioniFmtDigId id,
			TAnagFormatiDig TAnagFormatiDig, Calendar tsIns,
			Calendar tsUltimoAgg, Boolean flgAnn) {
		this.id = id;
		this.TAnagFormatiDig = TAnagFormatiDig;
		this.tsIns = tsIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.flgAnn = flgAnn;
	}

	public TEstensioniFmtDig(TEstensioniFmtDigId id,
			TAnagFormatiDig TAnagFormatiDig, Calendar tsIns, String idUteIns,
			Calendar tsUltimoAgg, String idUteUltimoAgg, Boolean flgAnn) {
		this.id = id;
		this.TAnagFormatiDig = TAnagFormatiDig;
		this.tsIns = tsIns;
		this.idUteIns = idUteIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.idUteUltimoAgg = idUteUltimoAgg;
		this.flgAnn = flgAnn;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDigFormat", column = @Column(name = "ID_DIG_FORMAT", nullable = false, length = 64)),
			@AttributeOverride(name = "estensione", column = @Column(name = "ESTENSIONE", nullable = false, length = 5)) })
	public TEstensioniFmtDigId getId() {
		return this.id;
	}

	public void setId(TEstensioniFmtDigId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIG_FORMAT", nullable = false, insertable = false, updatable = false)
	public TAnagFormatiDig getTAnagFormatiDig() {
		return this.TAnagFormatiDig;
	}

	public void setTAnagFormatiDig(TAnagFormatiDig TAnagFormatiDig) {
		this.TAnagFormatiDig = TAnagFormatiDig;
	}

	@Column(name = "TS_INS", nullable = false)
	public Calendar getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Calendar tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "ID_UTE_INS", length = 64)
	public String getIdUteIns() {
		return this.idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	@Column(name = "TS_ULTIMO_AGG", nullable = false)
	public Calendar getTsUltimoAgg() {
		return this.tsUltimoAgg;
	}

	public void setTsUltimoAgg(Calendar tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	@Column(name = "ID_UTE_ULTIMO_AGG", length = 64)
	public String getIdUteUltimoAgg() {
		return this.idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	@Column(name = "FLG_ANN", nullable = false, precision = 1, scale = 0)
	public Boolean getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(Boolean flgAnn) {
		this.flgAnn = flgAnn;
	}

}
