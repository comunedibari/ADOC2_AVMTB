package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PtvRepertorioId generated by hbm2java
 */
@Embeddable
public class PtvRepertorioId implements java.io.Serializable {

	private int idFascicolo;
	private String codEnte;
	private short anno;
	private BigDecimal idTitolazione;
	private int progrFasc;
	private BigDecimal numSottofasc;
	private String txtOgg;
	private Date dtApertura;
	private Date dtChiusura;
	private Date dtArch;
	private Date dtVersamento;
	private Date dtScarto;
	private String note;
	private String noteStoria;
	private String noteScarto;
	private Integer idProc;
	private int uoInCarico;
	private Integer uoAcc;
	private String flgAcc;
	private String flgProcObbl;
	private String flgAnn;
	private Integer uoConsult;
	private Date dtPrelievo;
	private Date dtRestituzione;
	private String flgProcInterr;

	public PtvRepertorioId() {
	}

	public PtvRepertorioId(int idFascicolo, String codEnte, short anno,
			BigDecimal idTitolazione, int progrFasc, String txtOgg,
			int uoInCarico) {
		this.idFascicolo = idFascicolo;
		this.codEnte = codEnte;
		this.anno = anno;
		this.idTitolazione = idTitolazione;
		this.progrFasc = progrFasc;
		this.txtOgg = txtOgg;
		this.uoInCarico = uoInCarico;
	}

	public PtvRepertorioId(int idFascicolo, String codEnte, short anno,
			BigDecimal idTitolazione, int progrFasc, BigDecimal numSottofasc,
			String txtOgg, Date dtApertura, Date dtChiusura, Date dtArch,
			Date dtVersamento, Date dtScarto, String note, String noteStoria,
			String noteScarto, Integer idProc, int uoInCarico, Integer uoAcc,
			String flgAcc, String flgProcObbl, String flgAnn,
			Integer uoConsult, Date dtPrelievo, Date dtRestituzione,
			String flgProcInterr) {
		this.idFascicolo = idFascicolo;
		this.codEnte = codEnte;
		this.anno = anno;
		this.idTitolazione = idTitolazione;
		this.progrFasc = progrFasc;
		this.numSottofasc = numSottofasc;
		this.txtOgg = txtOgg;
		this.dtApertura = dtApertura;
		this.dtChiusura = dtChiusura;
		this.dtArch = dtArch;
		this.dtVersamento = dtVersamento;
		this.dtScarto = dtScarto;
		this.note = note;
		this.noteStoria = noteStoria;
		this.noteScarto = noteScarto;
		this.idProc = idProc;
		this.uoInCarico = uoInCarico;
		this.uoAcc = uoAcc;
		this.flgAcc = flgAcc;
		this.flgProcObbl = flgProcObbl;
		this.flgAnn = flgAnn;
		this.uoConsult = uoConsult;
		this.dtPrelievo = dtPrelievo;
		this.dtRestituzione = dtRestituzione;
		this.flgProcInterr = flgProcInterr;
	}

	@Column(name = "ID_FASCICOLO", nullable = false, precision = 8, scale = 0)
	public int getIdFascicolo() {
		return this.idFascicolo;
	}

	public void setIdFascicolo(int idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	@Column(name = "COD_ENTE", nullable = false, length = 3)
	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	@Column(name = "ANNO", nullable = false, precision = 4, scale = 0)
	public short getAnno() {
		return this.anno;
	}

	public void setAnno(short anno) {
		this.anno = anno;
	}

	@Column(name = "ID_TITOLAZIONE", nullable = false, precision = 25, scale = 0)
	public BigDecimal getIdTitolazione() {
		return this.idTitolazione;
	}

	public void setIdTitolazione(BigDecimal idTitolazione) {
		this.idTitolazione = idTitolazione;
	}

	@Column(name = "PROGR_FASC", nullable = false, precision = 8, scale = 0)
	public int getProgrFasc() {
		return this.progrFasc;
	}

	public void setProgrFasc(int progrFasc) {
		this.progrFasc = progrFasc;
	}

	@Column(name = "NUM_SOTTOFASC", precision = 22, scale = 0)
	public BigDecimal getNumSottofasc() {
		return this.numSottofasc;
	}

	public void setNumSottofasc(BigDecimal numSottofasc) {
		this.numSottofasc = numSottofasc;
	}

	@Column(name = "TXT_OGG", nullable = false, length = 250)
	public String getTxtOgg() {
		return this.txtOgg;
	}

	public void setTxtOgg(String txtOgg) {
		this.txtOgg = txtOgg;
	}

	@Column(name = "DT_APERTURA", length = 7)
	public Date getDtApertura() {
		return this.dtApertura;
	}

	public void setDtApertura(Date dtApertura) {
		this.dtApertura = dtApertura;
	}

	@Column(name = "DT_CHIUSURA", length = 7)
	public Date getDtChiusura() {
		return this.dtChiusura;
	}

	public void setDtChiusura(Date dtChiusura) {
		this.dtChiusura = dtChiusura;
	}

	@Column(name = "DT_ARCH", length = 7)
	public Date getDtArch() {
		return this.dtArch;
	}

	public void setDtArch(Date dtArch) {
		this.dtArch = dtArch;
	}

	@Column(name = "DT_VERSAMENTO", length = 7)
	public Date getDtVersamento() {
		return this.dtVersamento;
	}

	public void setDtVersamento(Date dtVersamento) {
		this.dtVersamento = dtVersamento;
	}

	@Column(name = "DT_SCARTO", length = 7)
	public Date getDtScarto() {
		return this.dtScarto;
	}

	public void setDtScarto(Date dtScarto) {
		this.dtScarto = dtScarto;
	}

	@Column(name = "NOTE", length = 75)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "NOTE_STORIA", length = 250)
	public String getNoteStoria() {
		return this.noteStoria;
	}

	public void setNoteStoria(String noteStoria) {
		this.noteStoria = noteStoria;
	}

	@Column(name = "NOTE_SCARTO", length = 250)
	public String getNoteScarto() {
		return this.noteScarto;
	}

	public void setNoteScarto(String noteScarto) {
		this.noteScarto = noteScarto;
	}

	@Column(name = "ID_PROC", precision = 8, scale = 0)
	public Integer getIdProc() {
		return this.idProc;
	}

	public void setIdProc(Integer idProc) {
		this.idProc = idProc;
	}

	@Column(name = "UO_IN_CARICO", nullable = false, precision = 8, scale = 0)
	public int getUoInCarico() {
		return this.uoInCarico;
	}

	public void setUoInCarico(int uoInCarico) {
		this.uoInCarico = uoInCarico;
	}

	@Column(name = "UO_ACC", precision = 8, scale = 0)
	public Integer getUoAcc() {
		return this.uoAcc;
	}

	public void setUoAcc(Integer uoAcc) {
		this.uoAcc = uoAcc;
	}

	@Column(name = "FLG_ACC", length = 1)
	public String getFlgAcc() {
		return this.flgAcc;
	}

	public void setFlgAcc(String flgAcc) {
		this.flgAcc = flgAcc;
	}

	@Column(name = "FLG_PROC_OBBL", length = 1)
	public String getFlgProcObbl() {
		return this.flgProcObbl;
	}

	public void setFlgProcObbl(String flgProcObbl) {
		this.flgProcObbl = flgProcObbl;
	}

	@Column(name = "FLG_ANN", length = 1)
	public String getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(String flgAnn) {
		this.flgAnn = flgAnn;
	}

	@Column(name = "UO_CONSULT", precision = 8, scale = 0)
	public Integer getUoConsult() {
		return this.uoConsult;
	}

	public void setUoConsult(Integer uoConsult) {
		this.uoConsult = uoConsult;
	}

	@Column(name = "DT_PRELIEVO", length = 7)
	public Date getDtPrelievo() {
		return this.dtPrelievo;
	}

	public void setDtPrelievo(Date dtPrelievo) {
		this.dtPrelievo = dtPrelievo;
	}

	@Column(name = "DT_RESTITUZIONE", length = 7)
	public Date getDtRestituzione() {
		return this.dtRestituzione;
	}

	public void setDtRestituzione(Date dtRestituzione) {
		this.dtRestituzione = dtRestituzione;
	}

	@Column(name = "FLG_PROC_INTERR", length = 1)
	public String getFlgProcInterr() {
		return this.flgProcInterr;
	}

	public void setFlgProcInterr(String flgProcInterr) {
		this.flgProcInterr = flgProcInterr;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PtvRepertorioId))
			return false;
		PtvRepertorioId castOther = (PtvRepertorioId) other;

		return (this.getIdFascicolo() == castOther.getIdFascicolo())
				&& ((this.getCodEnte() == castOther.getCodEnte()) || (this
						.getCodEnte() != null && castOther.getCodEnte() != null && this
						.getCodEnte().equals(castOther.getCodEnte())))
				&& (this.getAnno() == castOther.getAnno())
				&& ((this.getIdTitolazione() == castOther.getIdTitolazione()) || (this
						.getIdTitolazione() != null
						&& castOther.getIdTitolazione() != null && this
						.getIdTitolazione()
						.equals(castOther.getIdTitolazione())))
				&& (this.getProgrFasc() == castOther.getProgrFasc())
				&& ((this.getNumSottofasc() == castOther.getNumSottofasc()) || (this
						.getNumSottofasc() != null
						&& castOther.getNumSottofasc() != null && this
						.getNumSottofasc().equals(castOther.getNumSottofasc())))
				&& ((this.getTxtOgg() == castOther.getTxtOgg()) || (this
						.getTxtOgg() != null && castOther.getTxtOgg() != null && this
						.getTxtOgg().equals(castOther.getTxtOgg())))
				&& ((this.getDtApertura() == castOther.getDtApertura()) || (this
						.getDtApertura() != null
						&& castOther.getDtApertura() != null && this
						.getDtApertura().equals(castOther.getDtApertura())))
				&& ((this.getDtChiusura() == castOther.getDtChiusura()) || (this
						.getDtChiusura() != null
						&& castOther.getDtChiusura() != null && this
						.getDtChiusura().equals(castOther.getDtChiusura())))
				&& ((this.getDtArch() == castOther.getDtArch()) || (this
						.getDtArch() != null && castOther.getDtArch() != null && this
						.getDtArch().equals(castOther.getDtArch())))
				&& ((this.getDtVersamento() == castOther.getDtVersamento()) || (this
						.getDtVersamento() != null
						&& castOther.getDtVersamento() != null && this
						.getDtVersamento().equals(castOther.getDtVersamento())))
				&& ((this.getDtScarto() == castOther.getDtScarto()) || (this
						.getDtScarto() != null
						&& castOther.getDtScarto() != null && this
						.getDtScarto().equals(castOther.getDtScarto())))
				&& ((this.getNote() == castOther.getNote()) || (this.getNote() != null
						&& castOther.getNote() != null && this.getNote()
						.equals(castOther.getNote())))
				&& ((this.getNoteStoria() == castOther.getNoteStoria()) || (this
						.getNoteStoria() != null
						&& castOther.getNoteStoria() != null && this
						.getNoteStoria().equals(castOther.getNoteStoria())))
				&& ((this.getNoteScarto() == castOther.getNoteScarto()) || (this
						.getNoteScarto() != null
						&& castOther.getNoteScarto() != null && this
						.getNoteScarto().equals(castOther.getNoteScarto())))
				&& ((this.getIdProc() == castOther.getIdProc()) || (this
						.getIdProc() != null && castOther.getIdProc() != null && this
						.getIdProc().equals(castOther.getIdProc())))
				&& (this.getUoInCarico() == castOther.getUoInCarico())
				&& ((this.getUoAcc() == castOther.getUoAcc()) || (this
						.getUoAcc() != null && castOther.getUoAcc() != null && this
						.getUoAcc().equals(castOther.getUoAcc())))
				&& ((this.getFlgAcc() == castOther.getFlgAcc()) || (this
						.getFlgAcc() != null && castOther.getFlgAcc() != null && this
						.getFlgAcc().equals(castOther.getFlgAcc())))
				&& ((this.getFlgProcObbl() == castOther.getFlgProcObbl()) || (this
						.getFlgProcObbl() != null
						&& castOther.getFlgProcObbl() != null && this
						.getFlgProcObbl().equals(castOther.getFlgProcObbl())))
				&& ((this.getFlgAnn() == castOther.getFlgAnn()) || (this
						.getFlgAnn() != null && castOther.getFlgAnn() != null && this
						.getFlgAnn().equals(castOther.getFlgAnn())))
				&& ((this.getUoConsult() == castOther.getUoConsult()) || (this
						.getUoConsult() != null
						&& castOther.getUoConsult() != null && this
						.getUoConsult().equals(castOther.getUoConsult())))
				&& ((this.getDtPrelievo() == castOther.getDtPrelievo()) || (this
						.getDtPrelievo() != null
						&& castOther.getDtPrelievo() != null && this
						.getDtPrelievo().equals(castOther.getDtPrelievo())))
				&& ((this.getDtRestituzione() == castOther.getDtRestituzione()) || (this
						.getDtRestituzione() != null
						&& castOther.getDtRestituzione() != null && this
						.getDtRestituzione().equals(
								castOther.getDtRestituzione())))
				&& ((this.getFlgProcInterr() == castOther.getFlgProcInterr()) || (this
						.getFlgProcInterr() != null
						&& castOther.getFlgProcInterr() != null && this
						.getFlgProcInterr()
						.equals(castOther.getFlgProcInterr())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdFascicolo();
		result = 37 * result
				+ (getCodEnte() == null ? 0 : this.getCodEnte().hashCode());
		result = 37 * result + this.getAnno();
		result = 37
				* result
				+ (getIdTitolazione() == null ? 0 : this.getIdTitolazione()
						.hashCode());
		result = 37 * result + this.getProgrFasc();
		result = 37
				* result
				+ (getNumSottofasc() == null ? 0 : this.getNumSottofasc()
						.hashCode());
		result = 37 * result
				+ (getTxtOgg() == null ? 0 : this.getTxtOgg().hashCode());
		result = 37
				* result
				+ (getDtApertura() == null ? 0 : this.getDtApertura()
						.hashCode());
		result = 37
				* result
				+ (getDtChiusura() == null ? 0 : this.getDtChiusura()
						.hashCode());
		result = 37 * result
				+ (getDtArch() == null ? 0 : this.getDtArch().hashCode());
		result = 37
				* result
				+ (getDtVersamento() == null ? 0 : this.getDtVersamento()
						.hashCode());
		result = 37 * result
				+ (getDtScarto() == null ? 0 : this.getDtScarto().hashCode());
		result = 37 * result
				+ (getNote() == null ? 0 : this.getNote().hashCode());
		result = 37
				* result
				+ (getNoteStoria() == null ? 0 : this.getNoteStoria()
						.hashCode());
		result = 37
				* result
				+ (getNoteScarto() == null ? 0 : this.getNoteScarto()
						.hashCode());
		result = 37 * result
				+ (getIdProc() == null ? 0 : this.getIdProc().hashCode());
		result = 37 * result + this.getUoInCarico();
		result = 37 * result
				+ (getUoAcc() == null ? 0 : this.getUoAcc().hashCode());
		result = 37 * result
				+ (getFlgAcc() == null ? 0 : this.getFlgAcc().hashCode());
		result = 37
				* result
				+ (getFlgProcObbl() == null ? 0 : this.getFlgProcObbl()
						.hashCode());
		result = 37 * result
				+ (getFlgAnn() == null ? 0 : this.getFlgAnn().hashCode());
		result = 37 * result
				+ (getUoConsult() == null ? 0 : this.getUoConsult().hashCode());
		result = 37
				* result
				+ (getDtPrelievo() == null ? 0 : this.getDtPrelievo()
						.hashCode());
		result = 37
				* result
				+ (getDtRestituzione() == null ? 0 : this.getDtRestituzione()
						.hashCode());
		result = 37
				* result
				+ (getFlgProcInterr() == null ? 0 : this.getFlgProcInterr()
						.hashCode());
		return result;
	}

}
