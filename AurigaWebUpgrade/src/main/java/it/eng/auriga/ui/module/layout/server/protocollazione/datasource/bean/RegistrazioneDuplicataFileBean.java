package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;

public class RegistrazioneDuplicataFileBean {

	@NumeroColonna(numero = "1")
	private Integer nroAllegato;
	@NumeroColonna(numero = "2")
	private String impronta;
	@NumeroColonna(numero = "3")
	private String algoritmo;
	@NumeroColonna(numero = "4")
	private String encoding;
	public Integer getNroAllegato() {
		return nroAllegato;
	}
	public void setNroAllegato(Integer nroAllegato) {
		this.nroAllegato = nroAllegato;
	}
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	public String getAlgoritmo() {
		return algoritmo;
	}
	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
