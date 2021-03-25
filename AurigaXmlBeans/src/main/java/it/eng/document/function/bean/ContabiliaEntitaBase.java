package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaEntitaBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codice;
	private String descrizione;
	private ContabiliaStato stato;
	
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
	
	public ContabiliaStato getStato() {
		return stato;
	}
	
	public void setStato(ContabiliaStato stato) {
		this.stato = stato;
	}
	
	@Override
	public String toString() {
		return "ContabiliaEntitaBase [codice=" + codice + ", descrizione=" + descrizione + ", stato=" + stato + "]";
	}
	
}
