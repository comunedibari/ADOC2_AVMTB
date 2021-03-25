package it.eng.document.function.bean;

import javax.xml.bind.annotation.XmlRootElement;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;


@XmlRootElement
public class CreaModFPBiAutoBean extends CreaModFatturaInBean {

	private static final long serialVersionUID = -6609881844566442763L;
	
	@XmlVariabile(nome = "NRO_PAGINE_Doc", tipo = TipoVariabile.SEMPLICE)
	private Integer nroPagine;

	public Integer getNroPagine() {
		return nroPagine;
	}

	public void setNroPagine(Integer nroPagine) {
		this.nroPagine = nroPagine;
	}
	
}
