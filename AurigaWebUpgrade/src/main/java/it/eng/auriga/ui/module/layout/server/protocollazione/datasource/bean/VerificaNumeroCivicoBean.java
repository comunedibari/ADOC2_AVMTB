package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

public class VerificaNumeroCivicoBean {

	private String codiceStrada;
	private String numeroCivico;
	private String letteraCivico;
	private String coloreCivico;
	private Boolean esitoVerifica;
	
	public String getCodiceStrada() {
		return codiceStrada;
	}
	
	public void setCodiceStrada(String codiceStrada) {
		this.codiceStrada = codiceStrada;
	}
	
	public String getNumeroCivico() {
		return numeroCivico;
	}
	
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
	
	public String getLetteraCivico() {
		return letteraCivico;
	}
	
	public void setLetteraCivico(String letteraCivico) {
		this.letteraCivico = letteraCivico;
	}
	
	public String getColoreCivico() {
		return coloreCivico;
	}
	
	public void setColoreCivico(String coloreCivico) {
		this.coloreCivico = coloreCivico;
	}
	
	public Boolean getEsitoVerifica() {
		return esitoVerifica;
	}
	
	public void setEsitoVerifica(Boolean esitoVerifica) {
		this.esitoVerifica = esitoVerifica;
	}
	
}
