package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;


public class ContribuenteBean extends SoggEsternoProtBean {
	
	private String emailContribuente;	
	private String telContribuente;	
	private String codiceACS;
	
	public String getEmailContribuente() {
		return emailContribuente;
	}
	public void setEmailContribuente(String emailContribuente) {
		this.emailContribuente = emailContribuente;
	}
	public String getTelContribuente() {
		return telContribuente;
	}
	public void setTelContribuente(String telContribuente) {
		this.telContribuente = telContribuente;
	}
	public String getCodiceACS() {
		return codiceACS;
	}
	public void setCodiceACS(String codiceACS) {
		this.codiceACS = codiceACS;
	}
	
}
