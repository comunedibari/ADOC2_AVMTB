package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;

public class DatiSoggXMLIOBean {

	@NumeroColonna(numero = "1")
	private String denominazioneSoggetto;

	@NumeroColonna(numero = "1")
	private String cognomeSoggetto;

	@NumeroColonna(numero = "2")
	private String nomeSoggetto;

	@NumeroColonna(numero = "3")
	private String flgPersFisica;

	@NumeroColonna(numero = "4")
	private String codfiscaleSoggetto;

	@NumeroColonna(numero = "15")
	private String codToponomastica;// Codice via (se indirizzo censito nella toponomastica)

	@NumeroColonna(numero = "16")
	private String indirizzo;// Nome via/toponimo

	@NumeroColonna(numero = "17")
	private String civico;// Civico (solo il N. la prima parte)

	@NumeroColonna(numero = "18")
	private String localitaFrazione;// Località/frazione

	@NumeroColonna(numero = "19")
	private String cap;// Codice ISTAT del comune

	@NumeroColonna(numero = "20")
	private String comune;// Nome del comune italiano o della città estera

	@NumeroColonna(numero = "21")
	private String codStato;// Codice ISTAT dello stato

	@NumeroColonna(numero = "22")
	private String nomeStato;// Nome dello stato

	@NumeroColonna(numero = "23")
	private String zona;// Zona della residenza/sede legale

	@NumeroColonna(numero = "24")
	private String complementoIndirizzo;// Info aggiuntive indirizzo

	@NumeroColonna(numero = "25")
	private String tipoToponimo;// Tipo di toponimo (i.e. via vicolo ecc)

	@NumeroColonna(numero = "26")
	private String appendici;// 2a parte del civico

	@NumeroColonna(numero = "27")
	private String codRapidoSoggetto;

	@NumeroColonna(numero = "28")
	private String codTipoSoggetto;

	@NumeroColonna(numero = "31")
	private String idUoSoggetto;

	@NumeroColonna(numero = "32")
	private String idUserSoggetto;

	@NumeroColonna(numero = "33")
	private String idScrivaniaSoggetto;

	@NumeroColonna(numero = "34")
	private String tipologiaSoggetto;

	public String getCodRapidoSoggetto() {
		return codRapidoSoggetto;
	}

	public void setCodRapidoSoggetto(String codRapidoSoggetto) {
		this.codRapidoSoggetto = codRapidoSoggetto;
	}

	public String getFlgPersFisica() {
		return flgPersFisica;
	}

	public void setFlgPersFisica(String flgPersFisica) {
		this.flgPersFisica = flgPersFisica;
	}

	public String getNomeSoggetto() {
		return nomeSoggetto;
	}

	public void setNomeSoggetto(String nomeSoggetto) {
		this.nomeSoggetto = nomeSoggetto;
	}

	public String getCodfiscaleSoggetto() {
		return codfiscaleSoggetto;
	}

	public void setCodfiscaleSoggetto(String codfiscaleSoggetto) {
		this.codfiscaleSoggetto = codfiscaleSoggetto;
	}

	public String getCodTipoSoggetto() {
		return codTipoSoggetto;
	}

	public void setCodTipoSoggetto(String codTipoSoggetto) {
		this.codTipoSoggetto = codTipoSoggetto;
	}

	public String getIdUoSoggetto() {
		return idUoSoggetto;
	}

	public void setIdUoSoggetto(String idUoSoggetto) {
		this.idUoSoggetto = idUoSoggetto;
	}

	public String getIdUserSoggetto() {
		return idUserSoggetto;
	}

	public void setIdUserSoggetto(String idUserSoggetto) {
		this.idUserSoggetto = idUserSoggetto;
	}

	public String getIdScrivaniaSoggetto() {
		return idScrivaniaSoggetto;
	}

	public void setIdScrivaniaSoggetto(String idScrivaniaSoggetto) {
		this.idScrivaniaSoggetto = idScrivaniaSoggetto;
	}

	public String getTipologiaSoggetto() {
		return tipologiaSoggetto;
	}

	public void setTipologiaSoggetto(String tipologiaSoggetto) {
		this.tipologiaSoggetto = tipologiaSoggetto;
	}

	/**
	 * @return the codToponomastica
	 */
	public String getCodToponomastica() {
		return codToponomastica;
	}

	/**
	 * @param codToponomastica
	 *            the codToponomastica to set
	 */
	public void setCodToponomastica(String codToponomastica) {
		this.codToponomastica = codToponomastica;
	}

	/**
	 * @return the indirizzo
	 */
	public String getIndirizzo() {
		return indirizzo;
	}

	/**
	 * @param indirizzo
	 *            the indirizzo to set
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	/**
	 * @return the civico
	 */
	public String getCivico() {
		return civico;
	}

	/**
	 * @param civico
	 *            the civico to set
	 */
	public void setCivico(String civico) {
		this.civico = civico;
	}

	/**
	 * @return the localitaFrazione
	 */
	public String getLocalitaFrazione() {
		return localitaFrazione;
	}

	/**
	 * @param localitaFrazione
	 *            the localitaFrazione to set
	 */
	public void setLocalitaFrazione(String localitaFrazione) {
		this.localitaFrazione = localitaFrazione;
	}

	/**
	 * @return the cap
	 */
	public String getCap() {
		return cap;
	}

	/**
	 * @param cap
	 *            the cap to set
	 */
	public void setCap(String cap) {
		this.cap = cap;
	}

	/**
	 * @return the comune
	 */
	public String getComune() {
		return comune;
	}

	/**
	 * @param comune
	 *            the comune to set
	 */
	public void setComune(String comune) {
		this.comune = comune;
	}

	/**
	 * @return the codStato
	 */
	public String getCodStato() {
		return codStato;
	}

	/**
	 * @param codStato
	 *            the codStato to set
	 */
	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}

	/**
	 * @return the nomeStato
	 */
	public String getNomeStato() {
		return nomeStato;
	}

	/**
	 * @param nomeStato
	 *            the nomeStato to set
	 */
	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}

	/**
	 * @return the zona
	 */
	public String getZona() {
		return zona;
	}

	/**
	 * @param zona
	 *            the zona to set
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}

	/**
	 * @return the complementoIndirizzo
	 */
	public String getComplementoIndirizzo() {
		return complementoIndirizzo;
	}

	/**
	 * @param complementoIndirizzo
	 *            the complementoIndirizzo to set
	 */
	public void setComplementoIndirizzo(String complementoIndirizzo) {
		this.complementoIndirizzo = complementoIndirizzo;
	}

	/**
	 * @return the tipoToponimo
	 */
	public String getTipoToponimo() {
		return tipoToponimo;
	}

	/**
	 * @param tipoToponimo
	 *            the tipoToponimo to set
	 */
	public void setTipoToponimo(String tipoToponimo) {
		this.tipoToponimo = tipoToponimo;
	}

	/**
	 * @return the appendice
	 */
	public String getAppendici() {
		return appendici;
	}

	/**
	 * @param appendice
	 *            the appendici to set
	 */
	public void setAppendici(String appendici) {
		this.appendici = appendici;
	}

	/**
	 * @return the denominazioneSoggetto
	 */
	public String getDenominazioneSoggetto() {
		return denominazioneSoggetto;
	}

	/**
	 * @param denominazioneSoggetto
	 *            the denominazioneSoggetto to set
	 */
	public void setDenominazioneSoggetto(String denominazioneSoggetto) {
		this.denominazioneSoggetto = denominazioneSoggetto;
	}

	/**
	 * @return the cognomeSoggetto
	 */
	public String getCognomeSoggetto() {
		return cognomeSoggetto;
	}

	/**
	 * @param cognomeSoggetto
	 *            the cognomeSoggetto to set
	 */
	public void setCognomeSoggetto(String cognomeSoggetto) {
		this.cognomeSoggetto = cognomeSoggetto;
	}

}
