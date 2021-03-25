package it.eng.document.function.bean;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AllegatoPraticaBean extends FilePrimarioBean {

	private static final long serialVersionUID = 4214358898578136185L;

	private BigDecimal idUd;
	
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
		
}
