package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaStato implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codice;
	private String descrizione;
	
	public String getCodice() {
		return codice;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	@Override
	public String toString() {
		return "ContabiliaStato [codice=" + codice + ", descrizione=" + descrizione + "]";
	}
	
}
