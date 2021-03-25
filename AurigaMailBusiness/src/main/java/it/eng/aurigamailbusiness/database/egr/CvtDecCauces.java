package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CvtDecCauces generated by hbm2java
 */
@Entity
@Table(name = "CVT_DEC_CAUCES")
public class CvtDecCauces implements java.io.Serializable {

	private String idCausale;
	private String desCausale;

	public CvtDecCauces() {
	}

	public CvtDecCauces(String idCausale) {
		this.idCausale = idCausale;
	}

	public CvtDecCauces(String idCausale, String desCausale) {
		this.idCausale = idCausale;
		this.desCausale = desCausale;
	}

	@Id
	@Column(name = "ID_CAUSALE", unique = true, nullable = false, length = 2)
	public String getIdCausale() {
		return this.idCausale;
	}

	public void setIdCausale(String idCausale) {
		this.idCausale = idCausale;
	}

	@Column(name = "DES_CAUSALE", length = 30)
	public String getDesCausale() {
		return this.desCausale;
	}

	public void setDesCausale(String desCausale) {
		this.desCausale = desCausale;
	}

}
