package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PtvCopieConoscId generated by hbm2java
 */
@Embeddable
public class PtvCopieConoscId implements java.io.Serializable {

	private int idDoc;
	private String codEnte;
	private String tpPtg;
	private Short annoPtg;
	private Integer numPtg;
	private String tpRpr;
	private Short annoRpr;
	private Integer numRpr;
	private String txtOgg;
	private Date dtProt;
	private Date dtRpr;
	private Date dtArrivo;
	private String flgTpProt;
	private String flgAnn;
	private String flgEvd;
	private String flgRsv;
	private int uoInCarico;
	private int idUoAss;
	private int idUoSped;
	private Date dtSped;
	private Integer idIndice;
	private BigDecimal idTitolazione;
	private Integer idFascicolo;
	private BigDecimal numSottofasc;
	private String rifProv;
	private Date dtProv;
	private String protProv;
	private Integer uoProv;
	private Date dtScarto;
	private short nroAlleg;
	private String note;
	private Date dtTermPp;
	private String flgAcc;

	public PtvCopieConoscId() {
	}

	public PtvCopieConoscId(int idDoc, String codEnte, String txtOgg,
			String flgTpProt, int uoInCarico, int idUoAss, int idUoSped,
			Date dtSped, short nroAlleg) {
		this.idDoc = idDoc;
		this.codEnte = codEnte;
		this.txtOgg = txtOgg;
		this.flgTpProt = flgTpProt;
		this.uoInCarico = uoInCarico;
		this.idUoAss = idUoAss;
		this.idUoSped = idUoSped;
		this.dtSped = dtSped;
		this.nroAlleg = nroAlleg;
	}

	public PtvCopieConoscId(int idDoc, String codEnte, String tpPtg,
			Short annoPtg, Integer numPtg, String tpRpr, Short annoRpr,
			Integer numRpr, String txtOgg, Date dtProt, Date dtRpr,
			Date dtArrivo, String flgTpProt, String flgAnn, String flgEvd,
			String flgRsv, int uoInCarico, int idUoAss, int idUoSped,
			Date dtSped, Integer idIndice, BigDecimal idTitolazione,
			Integer idFascicolo, BigDecimal numSottofasc, String rifProv,
			Date dtProv, String protProv, Integer uoProv, Date dtScarto,
			short nroAlleg, String note, Date dtTermPp, String flgAcc) {
		this.idDoc = idDoc;
		this.codEnte = codEnte;
		this.tpPtg = tpPtg;
		this.annoPtg = annoPtg;
		this.numPtg = numPtg;
		this.tpRpr = tpRpr;
		this.annoRpr = annoRpr;
		this.numRpr = numRpr;
		this.txtOgg = txtOgg;
		this.dtProt = dtProt;
		this.dtRpr = dtRpr;
		this.dtArrivo = dtArrivo;
		this.flgTpProt = flgTpProt;
		this.flgAnn = flgAnn;
		this.flgEvd = flgEvd;
		this.flgRsv = flgRsv;
		this.uoInCarico = uoInCarico;
		this.idUoAss = idUoAss;
		this.idUoSped = idUoSped;
		this.dtSped = dtSped;
		this.idIndice = idIndice;
		this.idTitolazione = idTitolazione;
		this.idFascicolo = idFascicolo;
		this.numSottofasc = numSottofasc;
		this.rifProv = rifProv;
		this.dtProv = dtProv;
		this.protProv = protProv;
		this.uoProv = uoProv;
		this.dtScarto = dtScarto;
		this.nroAlleg = nroAlleg;
		this.note = note;
		this.dtTermPp = dtTermPp;
		this.flgAcc = flgAcc;
	}

	@Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)
	public int getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	@Column(name = "COD_ENTE", nullable = false, length = 3)
	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	@Column(name = "TP_PTG", length = 5)
	public String getTpPtg() {
		return this.tpPtg;
	}

	public void setTpPtg(String tpPtg) {
		this.tpPtg = tpPtg;
	}

	@Column(name = "ANNO_PTG", precision = 4, scale = 0)
	public Short getAnnoPtg() {
		return this.annoPtg;
	}

	public void setAnnoPtg(Short annoPtg) {
		this.annoPtg = annoPtg;
	}

	@Column(name = "NUM_PTG", precision = 7, scale = 0)
	public Integer getNumPtg() {
		return this.numPtg;
	}

	public void setNumPtg(Integer numPtg) {
		this.numPtg = numPtg;
	}

	@Column(name = "TP_RPR", length = 5)
	public String getTpRpr() {
		return this.tpRpr;
	}

	public void setTpRpr(String tpRpr) {
		this.tpRpr = tpRpr;
	}

	@Column(name = "ANNO_RPR", precision = 4, scale = 0)
	public Short getAnnoRpr() {
		return this.annoRpr;
	}

	public void setAnnoRpr(Short annoRpr) {
		this.annoRpr = annoRpr;
	}

	@Column(name = "NUM_RPR", precision = 7, scale = 0)
	public Integer getNumRpr() {
		return this.numRpr;
	}

	public void setNumRpr(Integer numRpr) {
		this.numRpr = numRpr;
	}

	@Column(name = "TXT_OGG", nullable = false, length = 500)
	public String getTxtOgg() {
		return this.txtOgg;
	}

	public void setTxtOgg(String txtOgg) {
		this.txtOgg = txtOgg;
	}

	@Column(name = "DT_PROT", length = 7)
	public Date getDtProt() {
		return this.dtProt;
	}

	public void setDtProt(Date dtProt) {
		this.dtProt = dtProt;
	}

	@Column(name = "DT_RPR", length = 7)
	public Date getDtRpr() {
		return this.dtRpr;
	}

	public void setDtRpr(Date dtRpr) {
		this.dtRpr = dtRpr;
	}

	@Column(name = "DT_ARRIVO", length = 7)
	public Date getDtArrivo() {
		return this.dtArrivo;
	}

	public void setDtArrivo(Date dtArrivo) {
		this.dtArrivo = dtArrivo;
	}

	@Column(name = "FLG_TP_PROT", nullable = false, length = 2)
	public String getFlgTpProt() {
		return this.flgTpProt;
	}

	public void setFlgTpProt(String flgTpProt) {
		this.flgTpProt = flgTpProt;
	}

	@Column(name = "FLG_ANN", length = 1)
	public String getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(String flgAnn) {
		this.flgAnn = flgAnn;
	}

	@Column(name = "FLG_EVD", length = 1)
	public String getFlgEvd() {
		return this.flgEvd;
	}

	public void setFlgEvd(String flgEvd) {
		this.flgEvd = flgEvd;
	}

	@Column(name = "FLG_RSV", length = 1)
	public String getFlgRsv() {
		return this.flgRsv;
	}

	public void setFlgRsv(String flgRsv) {
		this.flgRsv = flgRsv;
	}

	@Column(name = "UO_IN_CARICO", nullable = false, precision = 8, scale = 0)
	public int getUoInCarico() {
		return this.uoInCarico;
	}

	public void setUoInCarico(int uoInCarico) {
		this.uoInCarico = uoInCarico;
	}

	@Column(name = "ID_UO_ASS", nullable = false, precision = 8, scale = 0)
	public int getIdUoAss() {
		return this.idUoAss;
	}

	public void setIdUoAss(int idUoAss) {
		this.idUoAss = idUoAss;
	}

	@Column(name = "ID_UO_SPED", nullable = false, precision = 8, scale = 0)
	public int getIdUoSped() {
		return this.idUoSped;
	}

	public void setIdUoSped(int idUoSped) {
		this.idUoSped = idUoSped;
	}

	@Column(name = "DT_SPED", nullable = false, length = 7)
	public Date getDtSped() {
		return this.dtSped;
	}

	public void setDtSped(Date dtSped) {
		this.dtSped = dtSped;
	}

	@Column(name = "ID_INDICE", precision = 8, scale = 0)
	public Integer getIdIndice() {
		return this.idIndice;
	}

	public void setIdIndice(Integer idIndice) {
		this.idIndice = idIndice;
	}

	@Column(name = "ID_TITOLAZIONE", precision = 25, scale = 0)
	public BigDecimal getIdTitolazione() {
		return this.idTitolazione;
	}

	public void setIdTitolazione(BigDecimal idTitolazione) {
		this.idTitolazione = idTitolazione;
	}

	@Column(name = "ID_FASCICOLO", precision = 8, scale = 0)
	public Integer getIdFascicolo() {
		return this.idFascicolo;
	}

	public void setIdFascicolo(Integer idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	@Column(name = "NUM_SOTTOFASC", precision = 22, scale = 0)
	public BigDecimal getNumSottofasc() {
		return this.numSottofasc;
	}

	public void setNumSottofasc(BigDecimal numSottofasc) {
		this.numSottofasc = numSottofasc;
	}

	@Column(name = "RIF_PROV", length = 30)
	public String getRifProv() {
		return this.rifProv;
	}

	public void setRifProv(String rifProv) {
		this.rifProv = rifProv;
	}

	@Column(name = "DT_PROV", length = 7)
	public Date getDtProv() {
		return this.dtProv;
	}

	public void setDtProv(Date dtProv) {
		this.dtProv = dtProv;
	}

	@Column(name = "PROT_PROV", length = 20)
	public String getProtProv() {
		return this.protProv;
	}

	public void setProtProv(String protProv) {
		this.protProv = protProv;
	}

	@Column(name = "UO_PROV", precision = 8, scale = 0)
	public Integer getUoProv() {
		return this.uoProv;
	}

	public void setUoProv(Integer uoProv) {
		this.uoProv = uoProv;
	}

	@Column(name = "DT_SCARTO", length = 7)
	public Date getDtScarto() {
		return this.dtScarto;
	}

	public void setDtScarto(Date dtScarto) {
		this.dtScarto = dtScarto;
	}

	@Column(name = "NRO_ALLEG", nullable = false, precision = 3, scale = 0)
	public short getNroAlleg() {
		return this.nroAlleg;
	}

	public void setNroAlleg(short nroAlleg) {
		this.nroAlleg = nroAlleg;
	}

	@Column(name = "NOTE", length = 250)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "DT_TERM_PP", length = 7)
	public Date getDtTermPp() {
		return this.dtTermPp;
	}

	public void setDtTermPp(Date dtTermPp) {
		this.dtTermPp = dtTermPp;
	}

	@Column(name = "FLG_ACC", length = 1)
	public String getFlgAcc() {
		return this.flgAcc;
	}

	public void setFlgAcc(String flgAcc) {
		this.flgAcc = flgAcc;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PtvCopieConoscId))
			return false;
		PtvCopieConoscId castOther = (PtvCopieConoscId) other;

		return (this.getIdDoc() == castOther.getIdDoc())
				&& ((this.getCodEnte() == castOther.getCodEnte()) || (this
						.getCodEnte() != null && castOther.getCodEnte() != null && this
						.getCodEnte().equals(castOther.getCodEnte())))
				&& ((this.getTpPtg() == castOther.getTpPtg()) || (this
						.getTpPtg() != null && castOther.getTpPtg() != null && this
						.getTpPtg().equals(castOther.getTpPtg())))
				&& ((this.getAnnoPtg() == castOther.getAnnoPtg()) || (this
						.getAnnoPtg() != null && castOther.getAnnoPtg() != null && this
						.getAnnoPtg().equals(castOther.getAnnoPtg())))
				&& ((this.getNumPtg() == castOther.getNumPtg()) || (this
						.getNumPtg() != null && castOther.getNumPtg() != null && this
						.getNumPtg().equals(castOther.getNumPtg())))
				&& ((this.getTpRpr() == castOther.getTpRpr()) || (this
						.getTpRpr() != null && castOther.getTpRpr() != null && this
						.getTpRpr().equals(castOther.getTpRpr())))
				&& ((this.getAnnoRpr() == castOther.getAnnoRpr()) || (this
						.getAnnoRpr() != null && castOther.getAnnoRpr() != null && this
						.getAnnoRpr().equals(castOther.getAnnoRpr())))
				&& ((this.getNumRpr() == castOther.getNumRpr()) || (this
						.getNumRpr() != null && castOther.getNumRpr() != null && this
						.getNumRpr().equals(castOther.getNumRpr())))
				&& ((this.getTxtOgg() == castOther.getTxtOgg()) || (this
						.getTxtOgg() != null && castOther.getTxtOgg() != null && this
						.getTxtOgg().equals(castOther.getTxtOgg())))
				&& ((this.getDtProt() == castOther.getDtProt()) || (this
						.getDtProt() != null && castOther.getDtProt() != null && this
						.getDtProt().equals(castOther.getDtProt())))
				&& ((this.getDtRpr() == castOther.getDtRpr()) || (this
						.getDtRpr() != null && castOther.getDtRpr() != null && this
						.getDtRpr().equals(castOther.getDtRpr())))
				&& ((this.getDtArrivo() == castOther.getDtArrivo()) || (this
						.getDtArrivo() != null
						&& castOther.getDtArrivo() != null && this
						.getDtArrivo().equals(castOther.getDtArrivo())))
				&& ((this.getFlgTpProt() == castOther.getFlgTpProt()) || (this
						.getFlgTpProt() != null
						&& castOther.getFlgTpProt() != null && this
						.getFlgTpProt().equals(castOther.getFlgTpProt())))
				&& ((this.getFlgAnn() == castOther.getFlgAnn()) || (this
						.getFlgAnn() != null && castOther.getFlgAnn() != null && this
						.getFlgAnn().equals(castOther.getFlgAnn())))
				&& ((this.getFlgEvd() == castOther.getFlgEvd()) || (this
						.getFlgEvd() != null && castOther.getFlgEvd() != null && this
						.getFlgEvd().equals(castOther.getFlgEvd())))
				&& ((this.getFlgRsv() == castOther.getFlgRsv()) || (this
						.getFlgRsv() != null && castOther.getFlgRsv() != null && this
						.getFlgRsv().equals(castOther.getFlgRsv())))
				&& (this.getUoInCarico() == castOther.getUoInCarico())
				&& (this.getIdUoAss() == castOther.getIdUoAss())
				&& (this.getIdUoSped() == castOther.getIdUoSped())
				&& ((this.getDtSped() == castOther.getDtSped()) || (this
						.getDtSped() != null && castOther.getDtSped() != null && this
						.getDtSped().equals(castOther.getDtSped())))
				&& ((this.getIdIndice() == castOther.getIdIndice()) || (this
						.getIdIndice() != null
						&& castOther.getIdIndice() != null && this
						.getIdIndice().equals(castOther.getIdIndice())))
				&& ((this.getIdTitolazione() == castOther.getIdTitolazione()) || (this
						.getIdTitolazione() != null
						&& castOther.getIdTitolazione() != null && this
						.getIdTitolazione()
						.equals(castOther.getIdTitolazione())))
				&& ((this.getIdFascicolo() == castOther.getIdFascicolo()) || (this
						.getIdFascicolo() != null
						&& castOther.getIdFascicolo() != null && this
						.getIdFascicolo().equals(castOther.getIdFascicolo())))
				&& ((this.getNumSottofasc() == castOther.getNumSottofasc()) || (this
						.getNumSottofasc() != null
						&& castOther.getNumSottofasc() != null && this
						.getNumSottofasc().equals(castOther.getNumSottofasc())))
				&& ((this.getRifProv() == castOther.getRifProv()) || (this
						.getRifProv() != null && castOther.getRifProv() != null && this
						.getRifProv().equals(castOther.getRifProv())))
				&& ((this.getDtProv() == castOther.getDtProv()) || (this
						.getDtProv() != null && castOther.getDtProv() != null && this
						.getDtProv().equals(castOther.getDtProv())))
				&& ((this.getProtProv() == castOther.getProtProv()) || (this
						.getProtProv() != null
						&& castOther.getProtProv() != null && this
						.getProtProv().equals(castOther.getProtProv())))
				&& ((this.getUoProv() == castOther.getUoProv()) || (this
						.getUoProv() != null && castOther.getUoProv() != null && this
						.getUoProv().equals(castOther.getUoProv())))
				&& ((this.getDtScarto() == castOther.getDtScarto()) || (this
						.getDtScarto() != null
						&& castOther.getDtScarto() != null && this
						.getDtScarto().equals(castOther.getDtScarto())))
				&& (this.getNroAlleg() == castOther.getNroAlleg())
				&& ((this.getNote() == castOther.getNote()) || (this.getNote() != null
						&& castOther.getNote() != null && this.getNote()
						.equals(castOther.getNote())))
				&& ((this.getDtTermPp() == castOther.getDtTermPp()) || (this
						.getDtTermPp() != null
						&& castOther.getDtTermPp() != null && this
						.getDtTermPp().equals(castOther.getDtTermPp())))
				&& ((this.getFlgAcc() == castOther.getFlgAcc()) || (this
						.getFlgAcc() != null && castOther.getFlgAcc() != null && this
						.getFlgAcc().equals(castOther.getFlgAcc())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdDoc();
		result = 37 * result
				+ (getCodEnte() == null ? 0 : this.getCodEnte().hashCode());
		result = 37 * result
				+ (getTpPtg() == null ? 0 : this.getTpPtg().hashCode());
		result = 37 * result
				+ (getAnnoPtg() == null ? 0 : this.getAnnoPtg().hashCode());
		result = 37 * result
				+ (getNumPtg() == null ? 0 : this.getNumPtg().hashCode());
		result = 37 * result
				+ (getTpRpr() == null ? 0 : this.getTpRpr().hashCode());
		result = 37 * result
				+ (getAnnoRpr() == null ? 0 : this.getAnnoRpr().hashCode());
		result = 37 * result
				+ (getNumRpr() == null ? 0 : this.getNumRpr().hashCode());
		result = 37 * result
				+ (getTxtOgg() == null ? 0 : this.getTxtOgg().hashCode());
		result = 37 * result
				+ (getDtProt() == null ? 0 : this.getDtProt().hashCode());
		result = 37 * result
				+ (getDtRpr() == null ? 0 : this.getDtRpr().hashCode());
		result = 37 * result
				+ (getDtArrivo() == null ? 0 : this.getDtArrivo().hashCode());
		result = 37 * result
				+ (getFlgTpProt() == null ? 0 : this.getFlgTpProt().hashCode());
		result = 37 * result
				+ (getFlgAnn() == null ? 0 : this.getFlgAnn().hashCode());
		result = 37 * result
				+ (getFlgEvd() == null ? 0 : this.getFlgEvd().hashCode());
		result = 37 * result
				+ (getFlgRsv() == null ? 0 : this.getFlgRsv().hashCode());
		result = 37 * result + this.getUoInCarico();
		result = 37 * result + this.getIdUoAss();
		result = 37 * result + this.getIdUoSped();
		result = 37 * result
				+ (getDtSped() == null ? 0 : this.getDtSped().hashCode());
		result = 37 * result
				+ (getIdIndice() == null ? 0 : this.getIdIndice().hashCode());
		result = 37
				* result
				+ (getIdTitolazione() == null ? 0 : this.getIdTitolazione()
						.hashCode());
		result = 37
				* result
				+ (getIdFascicolo() == null ? 0 : this.getIdFascicolo()
						.hashCode());
		result = 37
				* result
				+ (getNumSottofasc() == null ? 0 : this.getNumSottofasc()
						.hashCode());
		result = 37 * result
				+ (getRifProv() == null ? 0 : this.getRifProv().hashCode());
		result = 37 * result
				+ (getDtProv() == null ? 0 : this.getDtProv().hashCode());
		result = 37 * result
				+ (getProtProv() == null ? 0 : this.getProtProv().hashCode());
		result = 37 * result
				+ (getUoProv() == null ? 0 : this.getUoProv().hashCode());
		result = 37 * result
				+ (getDtScarto() == null ? 0 : this.getDtScarto().hashCode());
		result = 37 * result + this.getNroAlleg();
		result = 37 * result
				+ (getNote() == null ? 0 : this.getNote().hashCode());
		result = 37 * result
				+ (getDtTermPp() == null ? 0 : this.getDtTermPp().hashCode());
		result = 37 * result
				+ (getFlgAcc() == null ? 0 : this.getFlgAcc().hashCode());
		return result;
	}

}
