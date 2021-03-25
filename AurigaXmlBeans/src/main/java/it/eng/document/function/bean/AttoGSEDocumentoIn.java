package it.eng.document.function.bean;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class AttoGSEDocumentoIn extends CreaModDocumentoInBean {
	
	private static final long serialVersionUID = -4011655723485405230L;
	
	@XmlVariabile(nome="FORMA_GIURIDICA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String formaGiuridica;

	@XmlVariabile(nome="ATTO_CONCESSIONE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String attoConcessione;


	public final String getFormaGiuridica() {
		return formaGiuridica;
	}

	public final void setFormaGiuridica(String formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}

	public final String getAttoConcessione() {
		return attoConcessione;
	}

	public final void setAttoConcessione(String attoConcessione) {
		this.attoConcessione = attoConcessione;
	}
	

}

