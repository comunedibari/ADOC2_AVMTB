package it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean;

import it.eng.document.NumeroColonna;

public class ModelloAttivitaBean {

	@NumeroColonna(numero = "1")
	private String activityName;
	@NumeroColonna(numero = "2")
	private String esitiXGenModello;
	@NumeroColonna(numero = "3")
	private String idTipoDoc;
	@NumeroColonna(numero = "4")
	private String nomeTipoDoc;
	@NumeroColonna(numero = "5")
	private String descrizione;
	@NumeroColonna(numero = "6")
	private String nomeFile;
	@NumeroColonna(numero = "7")
	private String formato;
	@NumeroColonna(numero = "8")
	private Boolean flgDaFirmare;
	@NumeroColonna(numero = "9")
	private Boolean flgLocked;
	@NumeroColonna(numero = "10")
	private String uri;
	@NumeroColonna(numero = "11")
	private String tipoModello;		
	@NumeroColonna(numero = "12")
	private Boolean flgParteDispositivo;	
	@NumeroColonna(numero = "13")
	private String idModello;
	@NumeroColonna(numero = "14")
	private String nomeModello;
	@NumeroColonna(numero = "15")
	private Boolean flgSkipAnteprima;		
	@NumeroColonna(numero = "16")
	private Boolean flgParere;	
	@NumeroColonna(numero = "17")
	private Boolean flgNoPubbl;	
	@NumeroColonna(numero = "18")
	private Boolean flgPubblicaSeparato;	
	
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getEsitiXGenModello() {
		return esitiXGenModello;
	}
	public void setEsitiXGenModello(String esitiXGenModello) {
		this.esitiXGenModello = esitiXGenModello;
	}
	public String getIdTipoDoc() {
		return idTipoDoc;
	}
	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
	public String getNomeTipoDoc() {
		return nomeTipoDoc;
	}
	public void setNomeTipoDoc(String nomeTipoDoc) {
		this.nomeTipoDoc = nomeTipoDoc;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public Boolean getFlgDaFirmare() {
		return flgDaFirmare;
	}
	public void setFlgDaFirmare(Boolean flgDaFirmare) {
		this.flgDaFirmare = flgDaFirmare;
	}
	public Boolean getFlgLocked() {
		return flgLocked;
	}
	public void setFlgLocked(Boolean flgLocked) {
		this.flgLocked = flgLocked;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTipoModello() {
		return tipoModello;
	}
	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}	
	public Boolean getFlgParteDispositivo() {
		return flgParteDispositivo;
	}	
	public void setFlgParteDispositivo(Boolean flgParteDispositivo) {
		this.flgParteDispositivo = flgParteDispositivo;
	}
	public String getIdModello() {
		return idModello;
	}
	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}
	public String getNomeModello() {
		return nomeModello;
	}
	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}
	public Boolean getFlgSkipAnteprima() {
		return flgSkipAnteprima;
	}
	public void setFlgSkipAnteprima(Boolean flgSkipAnteprima) {
		this.flgSkipAnteprima = flgSkipAnteprima;
	}
	public Boolean getFlgParere() {
		return flgParere;
	}
	public void setFlgParere(Boolean flgParere) {
		this.flgParere = flgParere;
	}
	public Boolean getFlgNoPubbl() {
		return flgNoPubbl;
	}
	public void setFlgNoPubbl(Boolean flgNoPubbl) {
		this.flgNoPubbl = flgNoPubbl;
	}
	public Boolean getFlgPubblicaSeparato() {
		return flgPubblicaSeparato;
	}
	public void setFlgPubblicaSeparato(Boolean flgPubblicaSeparato) {
		this.flgPubblicaSeparato = flgPubblicaSeparato;
	}

}
