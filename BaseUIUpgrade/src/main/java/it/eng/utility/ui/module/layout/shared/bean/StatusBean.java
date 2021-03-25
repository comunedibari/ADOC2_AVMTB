package it.eng.utility.ui.module.layout.shared.bean;

import java.io.Serializable;

public class StatusBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2058490531499093696L;

	private int caricato;
	private int numero;
	private boolean forceStopDownload;
	private boolean finito;
	private boolean finish;
	
	
	public void setCaricato(int caricato) {
		this.caricato = caricato;
	}
	public int getCaricato() {
		return caricato;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getNumero() {
		return numero;
	}
	public void setForceStopDownload(boolean forceStopDownload) {
		this.forceStopDownload = forceStopDownload;
	}
	public boolean isForceStopDownload() {
		return forceStopDownload;
	}
	public void setFinito(boolean finito) {
		this.finito = finito;
	}
	public boolean getFinito() {
		return finito;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	public boolean isFinish() {
		return finish;
	}
	
}
