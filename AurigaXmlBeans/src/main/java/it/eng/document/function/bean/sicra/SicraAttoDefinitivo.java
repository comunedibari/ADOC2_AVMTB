package it.eng.document.function.bean.sicra;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraAttoDefinitivo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String siglaTipoAtto;
	private String codSettore;
	private BigInteger numAtto;
	private Integer annoAtto;
	private Calendar dataAtto;

	public String getSiglaTipoAtto() {
		return siglaTipoAtto;
	}

	public void setSiglaTipoAtto(String siglaTipoAtto) {
		this.siglaTipoAtto = siglaTipoAtto;
	}

	public String getCodSettore() {
		return codSettore;
	}

	public void setCodSettore(String codSettore) {
		this.codSettore = codSettore;
	}

	public BigInteger getNumAtto() {
		return numAtto;
	}

	public void setNumAtto(BigInteger numAtto) {
		this.numAtto = numAtto;
	}

	public Integer getAnnoAtto() {
		return annoAtto;
	}

	public void setAnnoAtto(Integer annoAtto) {
		this.annoAtto = annoAtto;
	}

	public Calendar getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Calendar dataAtto) {
		this.dataAtto = dataAtto;
	}

}
