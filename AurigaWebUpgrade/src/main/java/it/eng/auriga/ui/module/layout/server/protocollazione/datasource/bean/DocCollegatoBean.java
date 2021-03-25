package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;


public class DocCollegatoBean {
	
	private String flgPresenteASistema;	
	private String idUdCollegata;
	private String tipo;
	private String siglaRegistro;
	private Integer anno;		
	private String numero;
	private String oggetto;
	private String motivi;
	private String estremiReg;
	private String datiCollegamento;
	private Boolean flgLocked;
	private String sub;
	
	public String getFlgPresenteASistema() {
		return flgPresenteASistema;
	}
	public void setFlgPresenteASistema(String flgPresenteASistema) {
		this.flgPresenteASistema = flgPresenteASistema;
	}
	public String getIdUdCollegata() {
		return idUdCollegata;
	}
	public void setIdUdCollegata(String idUdCollegata) {
		this.idUdCollegata = idUdCollegata;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSiglaRegistro() {
		return siglaRegistro;
	}
	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getMotivi() {
		return motivi;
	}
	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}
	public String getEstremiReg() {
		return estremiReg;
	}
	public void setEstremiReg(String estremiReg) {
		this.estremiReg = estremiReg;
	}
	public String getDatiCollegamento() {
		return datiCollegamento;
	}
	public void setDatiCollegamento(String datiCollegamento) {
		this.datiCollegamento = datiCollegamento;
	}
	public Boolean getFlgLocked() {
		return flgLocked;
	}
	public void setFlgLocked(Boolean flgLocked) {
		this.flgLocked = flgLocked;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	
}
