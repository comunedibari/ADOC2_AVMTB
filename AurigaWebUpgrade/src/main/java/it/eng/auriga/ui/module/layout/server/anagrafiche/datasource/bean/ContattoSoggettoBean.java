package it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean;

public class ContattoSoggettoBean {
	
	private String rowId;
	private String tipo;
	private String email;
	private String telFax;
	private String tipoTel;
	private Boolean flgPec;
	private Boolean flgDichIpa;
	private Boolean flgCasellaIstituz;
	
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
	public String getTipoTel() {
		return tipoTel;
	}
	public void setTipoTel(String tipoTel) {
		this.tipoTel = tipoTel;
	}
	public Boolean getFlgPec() {
		return flgPec;
	}
	public void setFlgPec(Boolean flgPec) {
		this.flgPec = flgPec;
	}
	public Boolean getFlgDichIpa() {
		return flgDichIpa;
	}
	public void setFlgDichIpa(Boolean flgDichIpa) {
		this.flgDichIpa = flgDichIpa;
	}
	public Boolean getFlgCasellaIstituz() {
		return flgCasellaIstituz;
	}
	public void setFlgCasellaIstituz(Boolean flgCasellaIstituz) {
		this.flgCasellaIstituz = flgCasellaIstituz;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelFax() {
		return telFax;
	}
	public void setTelFax(String telFax) {
		this.telFax = telFax;
	}
	
}
