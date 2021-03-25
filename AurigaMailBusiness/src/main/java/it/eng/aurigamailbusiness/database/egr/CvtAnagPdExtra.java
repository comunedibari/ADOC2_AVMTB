package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CvtAnagPdExtra generated by hbm2java
 */
@Entity
@Table(name = "CVT_ANAG_PD_EXTRA")
public class CvtAnagPdExtra implements java.io.Serializable {

	private int idAnag;
	private String matricola;

	public CvtAnagPdExtra() {
	}

	public CvtAnagPdExtra(int idAnag) {
		this.idAnag = idAnag;
	}

	public CvtAnagPdExtra(int idAnag, String matricola) {
		this.idAnag = idAnag;
		this.matricola = matricola;
	}

	@Id
	@Column(name = "ID_ANAG", unique = true, nullable = false, precision = 9, scale = 0)
	public int getIdAnag() {
		return this.idAnag;
	}

	public void setIdAnag(int idAnag) {
		this.idAnag = idAnag;
	}

	@Column(name = "MATRICOLA", length = 20)
	public String getMatricola() {
		return this.matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

}
