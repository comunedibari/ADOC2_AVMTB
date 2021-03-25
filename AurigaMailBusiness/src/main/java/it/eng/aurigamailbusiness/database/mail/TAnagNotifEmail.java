package it.eng.aurigamailbusiness.database.mail;

// Generated 2-feb-2017 16.02.31 by Hibernate Tools 3.5.0.Final

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TAnagNotifEmail generated by hbm2java
 */
@Entity
@Table(name = "T_ANAG_NOTIF_EMAIL", uniqueConstraints = @UniqueConstraint(columnNames = { "TIPO_NOTIFICA", "ID_SP_AOO", "IPA_DEST_FATTURA" }))
public class TAnagNotifEmail implements java.io.Serializable {

	private static final long serialVersionUID = -3931700383933990368L;
	private String idSchedulazione;
	private String tipoNotifica;
	private BigDecimal idSpAoo;
	private String ipaDestFattura;
	private String destNotifica;
	private String destCcNotifica;
	private String idAccountMitt;
	private String accountMittente;
	private String smtpEndpointAccountMitt;
	private String smtpPortAccountMitt;
	private String subject;
	private String body;
	private boolean flgAnn;
	private Date tsIns;
	private String idUteIns;
	private Date tsUltimoAgg;
	private String idUteUltimoAgg;

	public TAnagNotifEmail() {
	}

	public TAnagNotifEmail(String idSchedulazione, String tipoNotifica, BigDecimal idSpAoo, String destNotifica, String subject, boolean flgAnn, Date tsIns,
			Date tsUltimoAgg) {
		this.idSchedulazione = idSchedulazione;
		this.tipoNotifica = tipoNotifica;
		this.idSpAoo = idSpAoo;
		this.destNotifica = destNotifica;
		this.subject = subject;
		this.flgAnn = flgAnn;
		this.tsIns = tsIns;
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public TAnagNotifEmail(String idSchedulazione, String tipoNotifica, BigDecimal idSpAoo, String ipaDestFattura, String destNotifica, String destCcNotifica,
			String idAccountMitt, String accountMittente, String smtpEndpointAccountMitt, String smtpPortAccountMitt, String subject, String body,
			boolean flgAnn, Date tsIns, String idUteIns, Date tsUltimoAgg, String idUteUltimoAgg) {
		this.idSchedulazione = idSchedulazione;
		this.tipoNotifica = tipoNotifica;
		this.idSpAoo = idSpAoo;
		this.ipaDestFattura = ipaDestFattura;
		this.destNotifica = destNotifica;
		this.destCcNotifica = destCcNotifica;
		this.idAccountMitt = idAccountMitt;
		this.accountMittente = accountMittente;
		this.smtpEndpointAccountMitt = smtpEndpointAccountMitt;
		this.smtpPortAccountMitt = smtpPortAccountMitt;
		this.subject = subject;
		this.body = body;
		this.flgAnn = flgAnn;
		this.tsIns = tsIns;
		this.idUteIns = idUteIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	@Id
	@Column(name = "ID_SCHEDULAZIONE", unique = true, nullable = false, length = 30)
	public String getIdSchedulazione() {
		return this.idSchedulazione;
	}

	public void setIdSchedulazione(String idSchedulazione) {
		this.idSchedulazione = idSchedulazione;
	}

	@Column(name = "TIPO_NOTIFICA", nullable = false, length = 30)
	public String getTipoNotifica() {
		return this.tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

	@Column(name = "ID_SP_AOO", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	@Column(name = "IPA_DEST_FATTURA", length = 50)
	public String getIpaDestFattura() {
		return this.ipaDestFattura;
	}

	public void setIpaDestFattura(String ipaDestFattura) {
		this.ipaDestFattura = ipaDestFattura;
	}

	@Column(name = "DEST_NOTIFICA", nullable = false, length = 4000)
	public String getDestNotifica() {
		return this.destNotifica;
	}

	public void setDestNotifica(String destNotifica) {
		this.destNotifica = destNotifica;
	}

	@Column(name = "DEST_CC_NOTIFICA", length = 4000)
	public String getDestCcNotifica() {
		return this.destCcNotifica;
	}

	public void setDestCcNotifica(String destCcNotifica) {
		this.destCcNotifica = destCcNotifica;
	}

	@Column(name = "ID_ACCOUNT_MITT", length = 64)
	public String getIdAccountMitt() {
		return this.idAccountMitt;
	}

	public void setIdAccountMitt(String idAccountMitt) {
		this.idAccountMitt = idAccountMitt;
	}

	@Column(name = "ACCOUNT_MITTENTE", length = 150)
	public String getAccountMittente() {
		return this.accountMittente;
	}

	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}

	@Column(name = "SMTP_ENDPOINT_ACCOUNT_MITT", length = 500)
	public String getSmtpEndpointAccountMitt() {
		return this.smtpEndpointAccountMitt;
	}

	public void setSmtpEndpointAccountMitt(String smtpEndpointAccountMitt) {
		this.smtpEndpointAccountMitt = smtpEndpointAccountMitt;
	}

	@Column(name = "SMTP_PORT_ACCOUNT_MITT", length = 5)
	public String getSmtpPortAccountMitt() {
		return this.smtpPortAccountMitt;
	}

	public void setSmtpPortAccountMitt(String smtpPortAccountMitt) {
		this.smtpPortAccountMitt = smtpPortAccountMitt;
	}

	@Column(name = "SUBJECT", nullable = false, length = 1000)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "BODY")
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Column(name = "FLG_ANN", nullable = false, precision = 1, scale = 0)
	public boolean isFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(boolean flgAnn) {
		this.flgAnn = flgAnn;
	}

	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
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
	public Date getTsUltimoAgg() {
		return this.tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	@Column(name = "ID_UTE_ULTIMO_AGG", length = 64)
	public String getIdUteUltimoAgg() {
		return this.idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

}
