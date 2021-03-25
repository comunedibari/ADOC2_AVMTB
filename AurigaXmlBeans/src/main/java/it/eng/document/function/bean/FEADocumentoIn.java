package it.eng.document.function.bean;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FEADocumentoIn extends CreaModDocumentoInBean {
	
	private static final long serialVersionUID = -4011655723485405230L;
	
	@XmlVariabile(nome="#CodMotivoAssegn", tipo = TipoVariabile.SEMPLICE)
	private String codMotivoAssegn;
	
	
	public String getCodMotivoAssegn() {
		return codMotivoAssegn;
	}

	public void setCodMotivoAssegn(String codMotivoAssegn) {
		this.codMotivoAssegn = codMotivoAssegn;
	}

	
	
}

