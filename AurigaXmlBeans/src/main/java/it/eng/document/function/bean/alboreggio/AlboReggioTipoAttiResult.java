package it.eng.document.function.bean.alboreggio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboReggioTipoAttiResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean ok;
	private String message;
	private boolean timeout;
	private boolean rispostaNonRicevuta;
	private AlboReggioTipoAtto[] payload;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AlboReggioTipoAtto[] getPayload() {
		return payload;
	}

	public void setPayload(AlboReggioTipoAtto[] payload) {
		this.payload = payload;
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
