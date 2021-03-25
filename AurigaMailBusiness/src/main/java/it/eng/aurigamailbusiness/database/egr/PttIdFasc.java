package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * PttIdFasc generated by hbm2java
 */
@Entity
@Table(name = "PTT_ID_FASC")
public class PttIdFasc implements java.io.Serializable {

	private BigDecimal idTitolazione;
	private PttTitolazioni pttTitolazioni;
	private short anno;
	private int progr;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;

	public PttIdFasc() {
	}

	public PttIdFasc(PttTitolazioni pttTitolazioni, short anno, int progr) {
		this.pttTitolazioni = pttTitolazioni;
		this.anno = anno;
		this.progr = progr;
	}

	public PttIdFasc(PttTitolazioni pttTitolazioni, short anno, int progr,
			Date dtIns, Integer uteIns, Date dtUltMod, Integer uteUltMod) {
		this.pttTitolazioni = pttTitolazioni;
		this.anno = anno;
		this.progr = progr;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "pttTitolazioni"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID_TITOLAZIONE", unique = true, nullable = false, precision = 25, scale = 0)
	public BigDecimal getIdTitolazione() {
		return this.idTitolazione;
	}

	public void setIdTitolazione(BigDecimal idTitolazione) {
		this.idTitolazione = idTitolazione;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public PttTitolazioni getPttTitolazioni() {
		return this.pttTitolazioni;
	}

	public void setPttTitolazioni(PttTitolazioni pttTitolazioni) {
		this.pttTitolazioni = pttTitolazioni;
	}

	@Column(name = "ANNO", nullable = false, precision = 4, scale = 0)
	public short getAnno() {
		return this.anno;
	}

	public void setAnno(short anno) {
		this.anno = anno;
	}

	@Column(name = "PROGR", nullable = false, precision = 8, scale = 0)
	public int getProgr() {
		return this.progr;
	}

	public void setProgr(int progr) {
		this.progr = progr;
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

}
