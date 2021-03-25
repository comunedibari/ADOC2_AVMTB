package it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean;

import it.eng.document.NumeroColonna;

public class ContattoSoggettoXmlBean {
	
	@NumeroColonna(numero = "1")
	private String rowId;
	@NumeroColonna(numero = "2")
	private String tipo;
	@NumeroColonna(numero = "3")
	private String emailTelFax;
	@NumeroColonna(numero = "4")
	private String tipoTel;
	@NumeroColonna(numero = "5")
	private String flgPec;
	@NumeroColonna(numero = "6")
	private String flgDichIpa;
	@NumeroColonna(numero = "7")
	private String flgCasellaIstituz;
	
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEmailTelFax() {
		return emailTelFax;
	}
	public void setEmailTelFax(String emailTelFax) {
		this.emailTelFax = emailTelFax;
	}
	public String getTipoTel() {
		return tipoTel;
	}
	public void setTipoTel(String tipoTel) {
		this.tipoTel = tipoTel;
	}
	public String getFlgPec() {
		return flgPec;
	}
	public void setFlgPec(String flgPec) {
		this.flgPec = flgPec;
	}
	public String getFlgDichIpa() {
		return flgDichIpa;
	}
	public void setFlgDichIpa(String flgDichIpa) {
		this.flgDichIpa = flgDichIpa;
	}
	public String getFlgCasellaIstituz() {
		return flgCasellaIstituz;
	}
	public void setFlgCasellaIstituz(String flgCasellaIstituz) {
		this.flgCasellaIstituz = flgCasellaIstituz;
	}
}