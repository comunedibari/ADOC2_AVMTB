package it.eng.auriga.module.business.entity;

// Generated 29-feb-2016 12.30.48 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TRichEmailToSend generated by hbm2java
 */
@Entity
@Table(name = "T_RICH_EMAIL_TO_SEND", uniqueConstraints = @UniqueConstraint(columnNames = { "PROV_ID_RICHIESTA", "COD_SENDER_APPL" }))
public class TRichEmailToSend implements java.io.Serializable {

	private String idRichiesta;
	private String codSenderAppl;
	private String provIdRichiesta;
	private BigDecimal idSpAoo;
	private String doctype;
	private Boolean flgPec;
	private String xmlRequest;
	private String stato;
	private String idEmail;
	private String errMsg;
	private Date tsIns;
	private Date tsUltimoAgg;
	private Set<TOperRichEmailToSend> TOperRichEmailToSends = new HashSet<TOperRichEmailToSend>(0);
	private Set<TDocsRichEmailToSend> TDocsRichEmailToSends = new HashSet<TDocsRichEmailToSend>(0);

	public TRichEmailToSend() {
	}

	public TRichEmailToSend(String idRichiesta, String codSenderAppl, String provIdRichiesta, BigDecimal idSpAoo, Boolean flgPec,
			String xmlRequest, String stato, Date tsIns, Date tsUltimoAgg) {
		this.idRichiesta = idRichiesta;
		this.codSenderAppl = codSenderAppl;
		this.provIdRichiesta = provIdRichiesta;
		this.idSpAoo = idSpAoo;
		this.flgPec = flgPec;
		this.xmlRequest = xmlRequest;
		this.stato = stato;
		this.tsIns = tsIns;
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public TRichEmailToSend(String idRichiesta, String codSenderAppl, String provIdRichiesta, BigDecimal idSpAoo, String doctype,
			Boolean flgPec, String xmlRequest, String stato, String idEmail, String errMsg, Date tsIns, Date tsUltimoAgg,
			Set<TOperRichEmailToSend> TOperRichEmailToSends, Set<TDocsRichEmailToSend> TDocsRichEmailToSends) {
		this.idRichiesta = idRichiesta;
		this.codSenderAppl = codSenderAppl;
		this.provIdRichiesta = provIdRichiesta;
		this.idSpAoo = idSpAoo;
		this.doctype = doctype;
		this.flgPec = flgPec;
		this.xmlRequest = xmlRequest;
		this.stato = stato;
		this.idEmail = idEmail;
		this.errMsg = errMsg;
		this.tsIns = tsIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.TOperRichEmailToSends = TOperRichEmailToSends;
		this.TDocsRichEmailToSends = TDocsRichEmailToSends;
	}

	@Id
	@Column(name = "ID_RICHIESTA", unique = true, nullable = false, length = 64)
	public String getIdRichiesta() {
		return this.idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	@Column(name = "COD_SENDER_APPL", nullable = false, length = 150)
	public String getCodSenderAppl() {
		return this.codSenderAppl;
	}

	public void setCodSenderAppl(String codSenderAppl) {
		this.codSenderAppl = codSenderAppl;
	}

	@Column(name = "PROV_ID_RICHIESTA", nullable = false, length = 64)
	public String getProvIdRichiesta() {
		return this.provIdRichiesta;
	}

	public void setProvIdRichiesta(String provIdRichiesta) {
		this.provIdRichiesta = provIdRichiesta;
	}

	@Column(name = "ID_SP_AOO", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	@Column(name = "DOCTYPE", length = 150)
	public String getDoctype() {
		return this.doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	@Column(name = "FLG_PEC", nullable = false, precision = 1, scale = 0)
	public Boolean getFlgPec() {
		return this.flgPec;
	}

	public void setFlgPec(Boolean flgPec) {
		this.flgPec = flgPec;
	}

	@Column(name = "XML_REQUEST", nullable = false)
	public String getXmlRequest() {
		return this.xmlRequest;
	}

	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
	}

	@Column(name = "STATO", nullable = false, length = 50)
	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name = "ID_EMAIL", length = 64)
	public String getIdEmail() {
		return this.idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	@Column(name = "ERR_MSG", length = 4000)
	public String getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "TS_ULTIMO_AGG", nullable = false)
	public Date getTsUltimoAgg() {
		return this.tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TRichEmailToSend")
	public Set<TOperRichEmailToSend> getTOperRichEmailToSends() {
		return this.TOperRichEmailToSends;
	}

	public void setTOperRichEmailToSends(Set<TOperRichEmailToSend> TOperRichEmailToSends) {
		this.TOperRichEmailToSends = TOperRichEmailToSends;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TRichEmailToSend")
	public Set<TDocsRichEmailToSend> getTDocsRichEmailToSends() {
		return this.TDocsRichEmailToSends;
	}

	public void setTDocsRichEmailToSends(Set<TDocsRichEmailToSend> TDocsRichEmailToSends) {
		this.TDocsRichEmailToSends = TDocsRichEmailToSends;
	}

}
