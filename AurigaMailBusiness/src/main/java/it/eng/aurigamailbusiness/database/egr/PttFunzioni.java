package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PttFunzioni generated by hbm2java
 */
@Entity
@Table(name = "PTT_FUNZIONI")
public class PttFunzioni implements java.io.Serializable {

	private String codFunz;
	private String desFunz;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private Set<PttFunzamm> pttFunzamms = new HashSet<PttFunzamm>(0);

	public PttFunzioni() {
	}

	public PttFunzioni(String codFunz, String desFunz) {
		this.codFunz = codFunz;
		this.desFunz = desFunz;
	}

	public PttFunzioni(String codFunz, String desFunz, Date dtIns,
			Integer uteIns, Date dtUltMod, Integer uteUltMod,
			Set<PttFunzamm> pttFunzamms) {
		this.codFunz = codFunz;
		this.desFunz = desFunz;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.pttFunzamms = pttFunzamms;
	}

	@Id
	@Column(name = "COD_FUNZ", unique = true, nullable = false, length = 5)
	public String getCodFunz() {
		return this.codFunz;
	}

	public void setCodFunz(String codFunz) {
		this.codFunz = codFunz;
	}

	@Column(name = "DES_FUNZ", nullable = false, length = 75)
	public String getDesFunz() {
		return this.desFunz;
	}

	public void setDesFunz(String desFunz) {
		this.desFunz = desFunz;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS", length = 7)
	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	@Column(name = "UTE_INS", precision = 6, scale = 0)
	public Integer getUteIns() {
		return this.uteIns;
	}

	public void setUteIns(Integer uteIns) {
		this.uteIns = uteIns;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ULT_MOD", length = 7)
	public Date getDtUltMod() {
		return this.dtUltMod;
	}

	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	@Column(name = "UTE_ULT_MOD", precision = 6, scale = 0)
	public Integer getUteUltMod() {
		return this.uteUltMod;
	}

	public void setUteUltMod(Integer uteUltMod) {
		this.uteUltMod = uteUltMod;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttFunzioni")
	public Set<PttFunzamm> getPttFunzamms() {
		return this.pttFunzamms;
	}

	public void setPttFunzamms(Set<PttFunzamm> pttFunzamms) {
		this.pttFunzamms = pttFunzamms;
	}

}