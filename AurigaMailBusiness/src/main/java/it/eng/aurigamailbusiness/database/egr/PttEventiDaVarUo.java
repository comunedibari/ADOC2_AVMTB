package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PttEventiDaVarUo generated by hbm2java
 */
@Entity
@Table(name = "PTT_EVENTI_DA_VAR_UO")
public class PttEventiDaVarUo implements java.io.Serializable {

	private BigDecimal progrEvento;
	private PttVarUo pttVarUo;
	private String tipoEvento;
	private Clob xmlDatiEvento;
	private Clob xmlOpzioniRichSinc;
	private String stato;
	private Date tsUltAggStato;
	private Clob xmlOutputSincr;
	private BigDecimal tryNum;
	private Serializable tsIns;

	public PttEventiDaVarUo() {
	}

	public PttEventiDaVarUo(BigDecimal progrEvento, PttVarUo pttVarUo,
			String tipoEvento, Clob xmlDatiEvento, String stato,
			Date tsUltAggStato, Serializable tsIns) {
		this.progrEvento = progrEvento;
		this.pttVarUo = pttVarUo;
		this.tipoEvento = tipoEvento;
		this.xmlDatiEvento = xmlDatiEvento;
		this.stato = stato;
		this.tsUltAggStato = tsUltAggStato;
		this.tsIns = tsIns;
	}

	public PttEventiDaVarUo(BigDecimal progrEvento, PttVarUo pttVarUo,
			String tipoEvento, Clob xmlDatiEvento, Clob xmlOpzioniRichSinc,
			String stato, Date tsUltAggStato, Clob xmlOutputSincr,
			BigDecimal tryNum, Serializable tsIns) {
		this.progrEvento = progrEvento;
		this.pttVarUo = pttVarUo;
		this.tipoEvento = tipoEvento;
		this.xmlDatiEvento = xmlDatiEvento;
		this.xmlOpzioniRichSinc = xmlOpzioniRichSinc;
		this.stato = stato;
		this.tsUltAggStato = tsUltAggStato;
		this.xmlOutputSincr = xmlOutputSincr;
		this.tryNum = tryNum;
		this.tsIns = tsIns;
	}

	@Id
	@Column(name = "PROGR_EVENTO", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getProgrEvento() {
		return this.progrEvento;
	}

	public void setProgrEvento(BigDecimal progrEvento) {
		this.progrEvento = progrEvento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VARIAZIONE", nullable = false)
	public PttVarUo getPttVarUo() {
		return this.pttVarUo;
	}

	public void setPttVarUo(PttVarUo pttVarUo) {
		this.pttVarUo = pttVarUo;
	}

	@Column(name = "TIPO_EVENTO", nullable = false, length = 30)
	public String getTipoEvento() {
		return this.tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	@Column(name = "XML_DATI_EVENTO", nullable = false)
	public Clob getXmlDatiEvento() {
		return this.xmlDatiEvento;
	}

	public void setXmlDatiEvento(Clob xmlDatiEvento) {
		this.xmlDatiEvento = xmlDatiEvento;
	}

	@Column(name = "XML_OPZIONI_RICH_SINC")
	public Clob getXmlOpzioniRichSinc() {
		return this.xmlOpzioniRichSinc;
	}

	public void setXmlOpzioniRichSinc(Clob xmlOpzioniRichSinc) {
		this.xmlOpzioniRichSinc = xmlOpzioniRichSinc;
	}

	@Column(name = "STATO", nullable = false, length = 30)
	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TS_ULT_AGG_STATO", nullable = false, length = 7)
	public Date getTsUltAggStato() {
		return this.tsUltAggStato;
	}

	public void setTsUltAggStato(Date tsUltAggStato) {
		this.tsUltAggStato = tsUltAggStato;
	}

	@Column(name = "XML_OUTPUT_SINCR")
	public Clob getXmlOutputSincr() {
		return this.xmlOutputSincr;
	}

	public void setXmlOutputSincr(Clob xmlOutputSincr) {
		this.xmlOutputSincr = xmlOutputSincr;
	}

	@Column(name = "TRY_NUM", precision = 22, scale = 0)
	public BigDecimal getTryNum() {
		return this.tryNum;
	}

	public void setTryNum(BigDecimal tryNum) {
		this.tryNum = tryNum;
	}

	@Column(name = "TS_INS", nullable = false)
	public Serializable getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Serializable tsIns) {
		this.tsIns = tsIns;
	}

}
