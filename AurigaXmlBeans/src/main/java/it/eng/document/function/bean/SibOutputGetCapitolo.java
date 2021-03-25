package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibOutputGetCapitolo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SibEntry> entries;
	private boolean ok;
	private String messaggio;
	private boolean timeout;
	private boolean rispostaNonRicevuta;

	public List<SibEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<SibEntry> entries) {
		this.entries = entries;
	}

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

}
