package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaElaboraAttiAmministrativiResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean responseElaborazione;
	private String esitoElaborazione;
	private String codiceElaborazione;
	private String messaggioElaborazione;
	
	public boolean isResponseElaborazione() {
		return responseElaborazione;
	}
	
	public void setResponseElaborazione(boolean responseElaborazione) {
		this.responseElaborazione = responseElaborazione;
	}
	
	public String getEsitoElaborazione() {
		return esitoElaborazione;
	}
	
	public void setEsitoElaborazione(String esitoElaborazione) {
		this.esitoElaborazione = esitoElaborazione;
	}
	
	public String getCodiceElaborazione() {
		return codiceElaborazione;
	}
	
	public void setCodiceElaborazione(String codiceElaborazione) {
		this.codiceElaborazione = codiceElaborazione;
	}
	
	public String getMessaggioElaborazione() {
		return messaggioElaborazione;
	}
	
	public void setMessaggioElaborazione(String messaggioElaborazione) {
		this.messaggioElaborazione = messaggioElaborazione;
	}

	@Override
	public String toString() {
		return "ContabiliaElaboraAttiAmministrativiResponse [responseElaborazione=" + responseElaborazione
				+ ", esitoElaborazione=" + esitoElaborazione + ", codiceElaborazione=" + codiceElaborazione
				+ ", messaggioElaborazione=" + messaggioElaborazione + "]";
	}
	
}
