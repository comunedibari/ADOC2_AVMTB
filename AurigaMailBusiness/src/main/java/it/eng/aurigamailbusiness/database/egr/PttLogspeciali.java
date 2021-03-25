package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

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
 * PttLogspeciali generated by hbm2java
 */
@Entity
@Table(name = "PTT_LOGSPECIALI")
public class PttLogspeciali implements java.io.Serializable {

	private PttLogspecialiId id;
	private CvtUtenti cvtUtenti;
	private String desUte;
	private Integer idDoc;
	private Short numCopia;
	private Integer idFascicolo;
	private Integer numSottofasc;
	private String note;

	public PttLogspeciali() {
	}

	public PttLogspeciali(PttLogspecialiId id, CvtUtenti cvtUtenti) {
		this.id = id;
		this.cvtUtenti = cvtUtenti;
	}

	public PttLogspeciali(PttLogspecialiId id, CvtUtenti cvtUtenti,
			String desUte, Integer idDoc, Short numCopia, Integer idFascicolo,
			Integer numSottofasc, String note) {
		this.id = id;
		this.cvtUtenti = cvtUtenti;
		this.desUte = desUte;
		this.idDoc = idDoc;
		this.numCopia = numCopia;
		this.idFascicolo = idFascicolo;
		this.numSottofasc = numSottofasc;
		this.note = note;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idUte", column = @Column(name = "ID_UTE", nullable = false, precision = 6, scale = 0)),
			@AttributeOverride(name = "data", column = @Column(name = "DATA", nullable = false, length = 7)),
			@AttributeOverride(name = "desFs", column = @Column(name = "DES_FS", nullable = false, length = 10)) })
	public PttLogspecialiId getId() {
		return this.id;
	}

	public void setId(PttLogspecialiId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UTE", nullable = false, insertable = false, updatable = false)
	public CvtUtenti getCvtUtenti() {
		return this.cvtUtenti;
	}

	public void setCvtUtenti(CvtUtenti cvtUtenti) {
		this.cvtUtenti = cvtUtenti;
	}

	@Column(name = "DES_UTE", length = 50)
	public String getDesUte() {
		return this.desUte;
	}

	public void setDesUte(String desUte) {
		this.desUte = desUte;
	}

	@Column(name = "ID_DOC", precision = 8, scale = 0)
	public Integer getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(Integer idDoc) {
		this.idDoc = idDoc;
	}

	@Column(name = "NUM_COPIA", precision = 4, scale = 0)
	public Short getNumCopia() {
		return this.numCopia;
	}

	public void setNumCopia(Short numCopia) {
		this.numCopia = numCopia;
	}

	@Column(name = "ID_FASCICOLO", precision = 8, scale = 0)
	public Integer getIdFascicolo() {
		return this.idFascicolo;
	}

	public void setIdFascicolo(Integer idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	@Column(name = "NUM_SOTTOFASC", precision = 5, scale = 0)
	public Integer getNumSottofasc() {
		return this.numSottofasc;
	}

	public void setNumSottofasc(Integer numSottofasc) {
		this.numSottofasc = numSottofasc;
	}

	@Column(name = "NOTE", length = 250)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
