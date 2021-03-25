package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PtvFascDaVersareId generated by hbm2java
 */
@Embeddable
public class PtvFascDaVersareId implements java.io.Serializable {

	private int idFascicolo;
	private short anno;
	private BigDecimal idTitolazione;
	private int progrFasc;
	private Date dtArch;
	private String txtOgg;
	private Date dtApertura;
	private Date dtChiusura;
	private String note;
	private String noteStoria;
	private Integer idProc;
	private int uoInCarico;
	private Integer uoConsult;
	private Date dtPrelievo;
	private Date dtRestituzione;

	public PtvFascDaVersareId() {
	}

	public PtvFascDaVersareId(int idFascicolo, short anno,
			BigDecimal idTitolazione, int progrFasc, String txtOgg,
			int uoInCarico) {
		this.idFascicolo = idFascicolo;
		this.anno = anno;
		this.idTitolazione = idTitolazione;
		this.progrFasc = progrFasc;
		this.txtOgg = txtOgg;
		this.uoInCarico = uoInCarico;
	}

	public PtvFascDaVersareId(int idFascicolo, short anno,
			BigDecimal idTitolazione, int progrFasc, Date dtArch,
			String txtOgg, Date dtApertura, Date dtChiusura, String note,
			String noteStoria, Integer idProc, int uoInCarico,
			Integer uoConsult, Date dtPrelievo, Date dtRestituzione) {
		this.idFascicolo = idFascicolo;
		this.anno = anno;
		this.idTitolazione = idTitolazione;
		this.progrFasc = progrFasc;
		this.dtArch = dtArch;
		this.txtOgg = txtOgg;
		this.dtApertura = dtApertura;
		this.dtChiusura = dtChiusura;
		this.note = note;
		this.noteStoria = noteStoria;
		this.idProc = idProc;
		this.uoInCarico = uoInCarico;
		this.uoConsult = uoConsult;
		this.dtPrelievo = dtPrelievo;
		this.dtRestituzione = dtRestituzione;
	}

	@Column(name = "ID_FASCICOLO", nullable = false, precision = 8, scale = 0)
	public int getIdFascicolo() {
		return this.idFascicolo;
	}

	public void setIdFascicolo(int idFascicolo) {
		this.idFascicolo = idFascicolo;
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

	@Column(name = "DT_ARCH", length = 7)
	public Date getDtArch() {
		return this.dtArch;
	}

	public void setDtArch(Date dtArch) {
		this.dtArch = dtArch;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PtvFascDaVersareId))
			return false;
		PtvFascDaVersareId castOther = (PtvFascDaVersareId) other;

		return (this.getIdFascicolo() == castOther.getIdFascicolo())
				&& (this.getAnno() == castOther.getAnno())
				&& ((this.getIdTitolazione() == castOther.getIdTitolazione()) || (this
						.getIdTitolazione() != null
						&& castOther.getIdTitolazione() != null && this
						.getIdTitolazione()
						.equals(castOther.getIdTitolazione())))
				&& (this.getProgrFasc() == castOther.getProgrFasc())
				&& ((this.getDtArch() == castOther.getDtArch()) || (this
						.getDtArch() != null && castOther.getDtArch() != null && this
						.getDtArch().equals(castOther.getDtArch())))
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
				&& ((this.getNote() == castOther.getNote()) || (this.getNote() != null
						&& castOther.getNote() != null && this.getNote()
						.equals(castOther.getNote())))
				&& ((this.getNoteStoria() == castOther.getNoteStoria()) || (this
						.getNoteStoria() != null
						&& castOther.getNoteStoria() != null && this
						.getNoteStoria().equals(castOther.getNoteStoria())))
				&& ((this.getIdProc() == castOther.getIdProc()) || (this
						.getIdProc() != null && castOther.getIdProc() != null && this
						.getIdProc().equals(castOther.getIdProc())))
				&& (this.getUoInCarico() == castOther.getUoInCarico())
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
								castOther.getDtRestituzione())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdFascicolo();
		result = 37 * result + this.getAnno();
		result = 37
				* result
				+ (getIdTitolazione() == null ? 0 : this.getIdTitolazione()
						.hashCode());
		result = 37 * result + this.getProgrFasc();
		result = 37 * result
				+ (getDtArch() == null ? 0 : this.getDtArch().hashCode());
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
				+ (getNote() == null ? 0 : this.getNote().hashCode());
		result = 37
				* result
				+ (getNoteStoria() == null ? 0 : this.getNoteStoria()
						.hashCode());
		result = 37 * result
				+ (getIdProc() == null ? 0 : this.getIdProc().hashCode());
		result = 37 * result + this.getUoInCarico();
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
		return result;
	}

}
