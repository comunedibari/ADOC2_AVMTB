package it.eng.aurigamailbusiness.database.mail;

// Generated 2-feb-2017 16.02.31 by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TValDizionario generated by hbm2java
 */
@Entity
@Table(name = "T_VAL_DIZIONARIO", uniqueConstraints = @UniqueConstraint(columnNames = { "ID_SEZ_DIZ", "VALORE" }))
public class TValDizionario implements java.io.Serializable {

	private static final long serialVersionUID = -712312197249331997L;
	private String idRecDiz;
	private TValDizionario TValDizionario;
	private TSezioniDiz TSezioniDiz;
	private String valore;
	private String codice;
	private boolean flgDismesso;
	private boolean flgDiSistema;
	private boolean flgAnn;
	private Date tsIns;
	private String idUteIns;
	private Date tsUltimoAgg;
	private String idUteUltimoAgg;
	private Set<TEmailVsUtenti> TEmailVsUtentis = new HashSet<TEmailVsUtenti>(0);
	private Set<TEmailMgo> TEmailMgosForIdRecDizContrassegno = new HashSet<TEmailMgo>(0);
	private Set<TEmailMgo> TEmailMgosForIdRecDizOperLock = new HashSet<TEmailMgo>(0);
	private Set<TRelEmailMgo> TRelEmailMgosForIdRecDizRuolo1Vs2 = new HashSet<TRelEmailMgo>(0);
	private Set<TRelEmailMgo> TRelEmailMgosForIdRecDizCategRel = new HashSet<TRelEmailMgo>(0);
	private Set<TValDizionario> TValDizionarios = new HashSet<TValDizionario>(0);

	public TValDizionario() {
	}

	public TValDizionario(String idRecDiz, TSezioniDiz TSezioniDiz, String valore, boolean flgDismesso, boolean flgDiSistema, boolean flgAnn, Date tsIns,
			Date tsUltimoAgg) {
		this.idRecDiz = idRecDiz;
		this.TSezioniDiz = TSezioniDiz;
		this.valore = valore;
		this.flgDismesso = flgDismesso;
		this.flgDiSistema = flgDiSistema;
		this.flgAnn = flgAnn;
		this.tsIns = tsIns;
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public TValDizionario(String idRecDiz, TValDizionario TValDizionario, TSezioniDiz TSezioniDiz, String valore, String codice, boolean flgDismesso,
			boolean flgDiSistema, boolean flgAnn, Date tsIns, String idUteIns, Date tsUltimoAgg, String idUteUltimoAgg, Set<TEmailVsUtenti> TEmailVsUtentis,
			Set<TEmailMgo> TEmailMgosForIdRecDizContrassegno, Set<TEmailMgo> TEmailMgosForIdRecDizOperLock, Set<TRelEmailMgo> TRelEmailMgosForIdRecDizRuolo1Vs2,
			Set<TRelEmailMgo> TRelEmailMgosForIdRecDizCategRel, Set<TValDizionario> TValDizionarios) {
		this.idRecDiz = idRecDiz;
		this.TValDizionario = TValDizionario;
		this.TSezioniDiz = TSezioniDiz;
		this.valore = valore;
		this.codice = codice;
		this.flgDismesso = flgDismesso;
		this.flgDiSistema = flgDiSistema;
		this.flgAnn = flgAnn;
		this.tsIns = tsIns;
		this.idUteIns = idUteIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.idUteUltimoAgg = idUteUltimoAgg;
		this.TEmailVsUtentis = TEmailVsUtentis;
		this.TEmailMgosForIdRecDizContrassegno = TEmailMgosForIdRecDizContrassegno;
		this.TEmailMgosForIdRecDizOperLock = TEmailMgosForIdRecDizOperLock;
		this.TRelEmailMgosForIdRecDizRuolo1Vs2 = TRelEmailMgosForIdRecDizRuolo1Vs2;
		this.TRelEmailMgosForIdRecDizCategRel = TRelEmailMgosForIdRecDizCategRel;
		this.TValDizionarios = TValDizionarios;
	}

	@Id

	@Column(name = "ID_REC_DIZ", unique = true, nullable = false, length = 64)
	public String getIdRecDiz() {
		return this.idRecDiz;
	}

	public void setIdRecDiz(String idRecDiz) {
		this.idRecDiz = idRecDiz;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIALIZZA_VAL_DIZ")
	public TValDizionario getTValDizionario() {
		return this.TValDizionario;
	}

	public void setTValDizionario(TValDizionario TValDizionario) {
		this.TValDizionario = TValDizionario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SEZ_DIZ", nullable = false)
	public TSezioniDiz getTSezioniDiz() {
		return this.TSezioniDiz;
	}

	public void setTSezioniDiz(TSezioniDiz TSezioniDiz) {
		this.TSezioniDiz = TSezioniDiz;
	}

	@Column(name = "VALORE", nullable = false, length = 250)
	public String getValore() {
		return this.valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	@Column(name = "CODICE", length = 50)
	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Column(name = "FLG_DISMESSO", nullable = false, precision = 1, scale = 0)
	public boolean isFlgDismesso() {
		return this.flgDismesso;
	}

	public void setFlgDismesso(boolean flgDismesso) {
		this.flgDismesso = flgDismesso;
	}

	@Column(name = "FLG_DI_SISTEMA", nullable = false, precision = 1, scale = 0)
	public boolean isFlgDiSistema() {
		return this.flgDiSistema;
	}

	public void setFlgDiSistema(boolean flgDiSistema) {
		this.flgDiSistema = flgDiSistema;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TValDizionario")
	public Set<TEmailVsUtenti> getTEmailVsUtentis() {
		return this.TEmailVsUtentis;
	}

	public void setTEmailVsUtentis(Set<TEmailVsUtenti> TEmailVsUtentis) {
		this.TEmailVsUtentis = TEmailVsUtentis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TValDizionarioByIdRecDizContrassegno")
	public Set<TEmailMgo> getTEmailMgosForIdRecDizContrassegno() {
		return this.TEmailMgosForIdRecDizContrassegno;
	}

	public void setTEmailMgosForIdRecDizContrassegno(Set<TEmailMgo> TEmailMgosForIdRecDizContrassegno) {
		this.TEmailMgosForIdRecDizContrassegno = TEmailMgosForIdRecDizContrassegno;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TValDizionarioByIdRecDizOperLock")
	public Set<TEmailMgo> getTEmailMgosForIdRecDizOperLock() {
		return this.TEmailMgosForIdRecDizOperLock;
	}

	public void setTEmailMgosForIdRecDizOperLock(Set<TEmailMgo> TEmailMgosForIdRecDizOperLock) {
		this.TEmailMgosForIdRecDizOperLock = TEmailMgosForIdRecDizOperLock;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TValDizionarioByIdRecDizRuolo1Vs2")
	public Set<TRelEmailMgo> getTRelEmailMgosForIdRecDizRuolo1Vs2() {
		return this.TRelEmailMgosForIdRecDizRuolo1Vs2;
	}

	public void setTRelEmailMgosForIdRecDizRuolo1Vs2(Set<TRelEmailMgo> TRelEmailMgosForIdRecDizRuolo1Vs2) {
		this.TRelEmailMgosForIdRecDizRuolo1Vs2 = TRelEmailMgosForIdRecDizRuolo1Vs2;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TValDizionarioByIdRecDizCategRel")
	public Set<TRelEmailMgo> getTRelEmailMgosForIdRecDizCategRel() {
		return this.TRelEmailMgosForIdRecDizCategRel;
	}

	public void setTRelEmailMgosForIdRecDizCategRel(Set<TRelEmailMgo> TRelEmailMgosForIdRecDizCategRel) {
		this.TRelEmailMgosForIdRecDizCategRel = TRelEmailMgosForIdRecDizCategRel;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TValDizionario")
	public Set<TValDizionario> getTValDizionarios() {
		return this.TValDizionarios;
	}

	public void setTValDizionarios(Set<TValDizionario> TValDizionarios) {
		this.TValDizionarios = TValDizionarios;
	}

}
