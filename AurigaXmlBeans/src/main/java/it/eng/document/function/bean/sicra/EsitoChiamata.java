package it.eng.document.function.bean.sicra;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EsitoChiamata implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean ok;
	private String messaggio;
	private boolean timeout;
	private boolean rispostaNonRicevuta;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public boolean isTimeout() {
		return timeout;
	}

	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

	public boolean isRispostaNonRicevuta() {
		return rispostaNonRicevuta;
	}

	public void setRispostaNonRicevuta(boolean rispostaNonRicevuta) {
		this.rispostaNonRicevuta = rispostaNonRicevuta;
	}

}// SicraOutputEsito