package it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean;


public class UtenteCasellaBean {
	
	private String idUser;	
	private String username;	
	private String cognomeNome;	
	private Boolean flgSmistatore;	
	private Boolean flgMittente;	
	private Boolean flgAmministratore;
	
	
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCognomeNome() {
		return cognomeNome;
	}
	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}	
	public Boolean getFlgSmistatore() {
		return flgSmistatore;
	}
	public void setFlgSmistatore(Boolean flgSmistatore) {
		this.flgSmistatore = flgSmistatore;
	}
	public Boolean getFlgMittente() {
		return flgMittente;
	}
	public void setFlgMittente(Boolean flgMittente) {
		this.flgMittente = flgMittente;
	}
	public Boolean getFlgAmministratore() {
		return flgAmministratore;
	}
	public void setFlgAmministratore(Boolean flgAmministratore) {
		this.flgAmministratore = flgAmministratore;
	}	
	
}

