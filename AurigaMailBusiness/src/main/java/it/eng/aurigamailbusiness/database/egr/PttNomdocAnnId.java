package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PttNomdocAnnId generated by hbm2java
 */
@Embeddable
public class PttNomdocAnnId implements java.io.Serializable {

	private int idDoc;
	private Integer idAnag;
	private Date dtAnn;
	private int uoAnn;
	private int uteAnn;
	private Integer idAttoAnn;
	private String flgEsibDestContr;
	private String flgDestCopia;
	private Integer idTopon;
	private Integer numCiv;
	private String espCiv;
	private String desInd;
	private Integer idCom;
	private String desCom;
	private Integer cap;
	private String desAnag;

	public PttNomdocAnnId() {
	}

	public PttNomdocAnnId(int idDoc, Date dtAnn, int uoAnn, int uteAnn) {
		this.idDoc = idDoc;
		this.dtAnn = dtAnn;
		this.uoAnn = uoAnn;
		this.uteAnn = uteAnn;
	}

	public PttNomdocAnnId(int idDoc, Integer idAnag, Date dtAnn, int uoAnn,
			int uteAnn, Integer idAttoAnn, String flgEsibDestContr,
			String flgDestCopia, Integer idTopon, Integer numCiv,
			String espCiv, String desInd, Integer idCom, String desCom,
			Integer cap, String desAnag) {
		this.idDoc = idDoc;
		this.idAnag = idAnag;
		this.dtAnn = dtAnn;
		this.uoAnn = uoAnn;
		this.uteAnn = uteAnn;
		this.idAttoAnn = idAttoAnn;
		this.flgEsibDestContr = flgEsibDestContr;
		this.flgDestCopia = flgDestCopia;
		this.idTopon = idTopon;
		this.numCiv = numCiv;
		this.espCiv = espCiv;
		this.desInd = desInd;
		this.idCom = idCom;
		this.desCom = desCom;
		this.cap = cap;
		this.desAnag = desAnag;
	}

	@Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)
	public int getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	@Column(name = "ID_ANAG", precision = 9, scale = 0)
	public Integer getIdAnag() {
		return this.idAnag;
	}

	public void setIdAnag(Integer idAnag) {
		this.idAnag = idAnag;
	}

	@Column(name = "DT_ANN", nullable = false, length = 7)
	public Date getDtAnn() {
		return this.dtAnn;
	}

	public void setDtAnn(Date dtAnn) {
		this.dtAnn = dtAnn;
	}

	@Column(name = "UO_ANN", nullable = false, precision = 8, scale = 0)
	public int getUoAnn() {
		return this.uoAnn;
	}

	public void setUoAnn(int uoAnn) {
		this.uoAnn = uoAnn;
	}

	@Column(name = "UTE_ANN", nullable = false, precision = 6, scale = 0)
	public int getUteAnn() {
		return this.uteAnn;
	}

	public void setUteAnn(int uteAnn) {
		this.uteAnn = uteAnn;
	}

	@Column(name = "ID_ATTO_ANN", precision = 8, scale = 0)
	public Integer getIdAttoAnn() {
		return this.idAttoAnn;
	}

	public void setIdAttoAnn(Integer idAttoAnn) {
		this.idAttoAnn = idAttoAnn;
	}

	@Column(name = "FLG_ESIB_DEST_CONTR", length = 1)
	public String getFlgEsibDestContr() {
		return this.flgEsibDestContr;
	}

	public void setFlgEsibDestContr(String flgEsibDestContr) {
		this.flgEsibDestContr = flgEsibDestContr;
	}

	@Column(name = "FLG_DEST_COPIA", length = 1)
	public String getFlgDestCopia() {
		return this.flgDestCopia;
	}

	public void setFlgDestCopia(String flgDestCopia) {
		this.flgDestCopia = flgDestCopia;
	}

	@Column(name = "ID_TOPON", precision = 9, scale = 0)
	public Integer getIdTopon() {
		return this.idTopon;
	}

	public void setIdTopon(Integer idTopon) {
		this.idTopon = idTopon;
	}

	@Column(name = "NUM_CIV", precision = 5, scale = 0)
	public Integer getNumCiv() {
		return this.numCiv;
	}

	public void setNumCiv(Integer numCiv) {
		this.numCiv = numCiv;
	}

	@Column(name = "ESP_CIV", length = 12)
	public String getEspCiv() {
		return this.espCiv;
	}

	public void setEspCiv(String espCiv) {
		this.espCiv = espCiv;
	}

	@Column(name = "DES_IND", length = 60)
	public String getDesInd() {
		return this.desInd;
	}

	public void setDesInd(String desInd) {
		this.desInd = desInd;
	}

	@Column(name = "ID_COM", precision = 6, scale = 0)
	public Integer getIdCom() {
		return this.idCom;
	}

	public void setIdCom(Integer idCom) {
		this.idCom = idCom;
	}

	@Column(name = "DES_COM", length = 60)
	public String getDesCom() {
		return this.desCom;
	}

	public void setDesCom(String desCom) {
		this.desCom = desCom;
	}

	@Column(name = "CAP", precision = 5, scale = 0)
	public Integer getCap() {
		return this.cap;
	}

	public void setCap(Integer cap) {
		this.cap = cap;
	}

	@Column(name = "DES_ANAG", length = 150)
	public String getDesAnag() {
		return this.desAnag;
	}

	public void setDesAnag(String desAnag) {
		this.desAnag = desAnag;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PttNomdocAnnId))
			return false;
		PttNomdocAnnId castOther = (PttNomdocAnnId) other;

		return (this.getIdDoc() == castOther.getIdDoc())
				&& ((this.getIdAnag() == castOther.getIdAnag()) || (this
						.getIdAnag() != null && castOther.getIdAnag() != null && this
						.getIdAnag().equals(castOther.getIdAnag())))
				&& ((this.getDtAnn() == castOther.getDtAnn()) || (this
						.getDtAnn() != null && castOther.getDtAnn() != null && this
						.getDtAnn().equals(castOther.getDtAnn())))
				&& (this.getUoAnn() == castOther.getUoAnn())
				&& (this.getUteAnn() == castOther.getUteAnn())
				&& ((this.getIdAttoAnn() == castOther.getIdAttoAnn()) || (this
						.getIdAttoAnn() != null
						&& castOther.getIdAttoAnn() != null && this
						.getIdAttoAnn().equals(castOther.getIdAttoAnn())))
				&& ((this.getFlgEsibDestContr() == castOther
						.getFlgEsibDestContr()) || (this.getFlgEsibDestContr() != null
						&& castOther.getFlgEsibDestContr() != null && this
						.getFlgEsibDestContr().equals(
								castOther.getFlgEsibDestContr())))
				&& ((this.getFlgDestCopia() == castOther.getFlgDestCopia()) || (this
						.getFlgDestCopia() != null
						&& castOther.getFlgDestCopia() != null && this
						.getFlgDestCopia().equals(castOther.getFlgDestCopia())))
				&& ((this.getIdTopon() == castOther.getIdTopon()) || (this
						.getIdTopon() != null && castOther.getIdTopon() != null && this
						.getIdTopon().equals(castOther.getIdTopon())))
				&& ((this.getNumCiv() == castOther.getNumCiv()) || (this
						.getNumCiv() != null && castOther.getNumCiv() != null && this
						.getNumCiv().equals(castOther.getNumCiv())))
				&& ((this.getEspCiv() == castOther.getEspCiv()) || (this
						.getEspCiv() != null && castOther.getEspCiv() != null && this
						.getEspCiv().equals(castOther.getEspCiv())))
				&& ((this.getDesInd() == castOther.getDesInd()) || (this
						.getDesInd() != null && castOther.getDesInd() != null && this
						.getDesInd().equals(castOther.getDesInd())))
				&& ((this.getIdCom() == castOther.getIdCom()) || (this
						.getIdCom() != null && castOther.getIdCom() != null && this
						.getIdCom().equals(castOther.getIdCom())))
				&& ((this.getDesCom() == castOther.getDesCom()) || (this
						.getDesCom() != null && castOther.getDesCom() != null && this
						.getDesCom().equals(castOther.getDesCom())))
				&& ((this.getCap() == castOther.getCap()) || (this.getCap() != null
						&& castOther.getCap() != null && this.getCap().equals(
						castOther.getCap())))
				&& ((this.getDesAnag() == castOther.getDesAnag()) || (this
						.getDesAnag() != null && castOther.getDesAnag() != null && this
						.getDesAnag().equals(castOther.getDesAnag())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdDoc();
		result = 37 * result
				+ (getIdAnag() == null ? 0 : this.getIdAnag().hashCode());
		result = 37 * result
				+ (getDtAnn() == null ? 0 : this.getDtAnn().hashCode());
		result = 37 * result + this.getUoAnn();
		result = 37 * result + this.getUteAnn();
		result = 37 * result
				+ (getIdAttoAnn() == null ? 0 : this.getIdAttoAnn().hashCode());
		result = 37
				* result
				+ (getFlgEsibDestContr() == null ? 0 : this
						.getFlgEsibDestContr().hashCode());
		result = 37
				* result
				+ (getFlgDestCopia() == null ? 0 : this.getFlgDestCopia()
						.hashCode());
		result = 37 * result
				+ (getIdTopon() == null ? 0 : this.getIdTopon().hashCode());
		result = 37 * result
				+ (getNumCiv() == null ? 0 : this.getNumCiv().hashCode());
		result = 37 * result
				+ (getEspCiv() == null ? 0 : this.getEspCiv().hashCode());
		result = 37 * result
				+ (getDesInd() == null ? 0 : this.getDesInd().hashCode());
		result = 37 * result
				+ (getIdCom() == null ? 0 : this.getIdCom().hashCode());
		result = 37 * result
				+ (getDesCom() == null ? 0 : this.getDesCom().hashCode());
		result = 37 * result
				+ (getCap() == null ? 0 : this.getCap().hashCode());
		result = 37 * result
				+ (getDesAnag() == null ? 0 : this.getDesAnag().hashCode());
		return result;
	}

}
