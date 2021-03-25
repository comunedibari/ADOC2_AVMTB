package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;


public class DestinatarioProtBean extends IndirizzoSoggettoProtBean {
	
	private Boolean fromLoadDett;
    private String idSoggetto;
	private String idUoSoggetto;
    private String idUserSoggetto;
    private String idScrivaniaSoggetto;
    private String tipoDestinatario;
	private String codRapidoDestinatario;
	private String denominazioneDestinatario;
	private String cognomeDestinatario;
	private String nomeDestinatario;
	private String codfiscaleDestinatario;
	private String indirizzoMailDestinatario;
	private String gruppiDestinatario;
	private String idGruppoInterno;
	private String idGruppoEsterno;
	private String aoomdgDestinatario;
    private String descrAoomdgDestinatario;
    private String idDestInvioCC;
    private Boolean flgPC;
    private OpzioniInvioBean opzioniInvioCondivisione;		
	private String idAssegnatario;
    private Boolean flgAssegnaAlDestinatario;
    private OpzioniInvioBean opzioniInvioAssegnazione;	
//	private Boolean flgPresaInCarico;
//  private Boolean flgMancataPresaInCarico;
//  private Integer giorniTrascorsi;
    private MezzoTrasmissioneDestinatarioBean mezzoTrasmissioneDestinatario;
    private String gruppoSalvato;
	
    public Boolean getFromLoadDett() {
		return fromLoadDett;
	}
	public void setFromLoadDett(Boolean fromLoadDett) {
		this.fromLoadDett = fromLoadDett;
	}
	public String getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
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
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}
	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public String getCodRapidoDestinatario() {
		return codRapidoDestinatario;
	}
	public void setCodRapidoDestinatario(String codRapidoDestinatario) {
		this.codRapidoDestinatario = codRapidoDestinatario;
	}
	public String getDenominazioneDestinatario() {
		return denominazioneDestinatario;
	}
	public void setDenominazioneDestinatario(String denominazioneDestinatario) {
		this.denominazioneDestinatario = denominazioneDestinatario;
	}
	public String getCognomeDestinatario() {
		return cognomeDestinatario;
	}
	public void setCognomeDestinatario(String cognomeDestinatario) {
		this.cognomeDestinatario = cognomeDestinatario;
	}
	public String getNomeDestinatario() {
		return nomeDestinatario;
	}
	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}
	public String getCodfiscaleDestinatario() {
		return codfiscaleDestinatario;
	}
	public void setCodfiscaleDestinatario(String codfiscaleDestinatario) {
		this.codfiscaleDestinatario = codfiscaleDestinatario;
	}
	public String getIndirizzoMailDestinatario() {
		return indirizzoMailDestinatario;
	}
	public void setIndirizzoMailDestinatario(String indirizzoMailDestinatario) {
		this.indirizzoMailDestinatario = indirizzoMailDestinatario;
	}
	public String getGruppiDestinatario() {
		return gruppiDestinatario;
	}
	public void setGruppiDestinatario(String gruppiDestinatario) {
		this.gruppiDestinatario = gruppiDestinatario;
	}
	public String getIdGruppoInterno() {
		return idGruppoInterno;
	}
	public void setIdGruppoInterno(String idGruppoInterno) {
		this.idGruppoInterno = idGruppoInterno;
	}
	public String getIdGruppoEsterno() {
		return idGruppoEsterno;
	}
	public void setIdGruppoEsterno(String idGruppoEsterno) {
		this.idGruppoEsterno = idGruppoEsterno;
	}
	public String getAoomdgDestinatario() {
		return aoomdgDestinatario;
	}
	public void setAoomdgDestinatario(String aoomdgDestinatario) {
		this.aoomdgDestinatario = aoomdgDestinatario;
	}
	public String getDescrAoomdgDestinatario() {
		return descrAoomdgDestinatario;
	}
	public void setDescrAoomdgDestinatario(String descrAoomdgDestinatario) {
		this.descrAoomdgDestinatario = descrAoomdgDestinatario;
	}
	public String getIdDestInvioCC() {
		return idDestInvioCC;
	}
	public void setIdDestInvioCC(String idDestInvioCC) {
		this.idDestInvioCC = idDestInvioCC;
	}
	public Boolean getFlgPC() {
		return flgPC;
	}
	public void setFlgPC(Boolean flgPC) {
		this.flgPC = flgPC;
	}
	public OpzioniInvioBean getOpzioniInvioCondivisione() {
		return opzioniInvioCondivisione;
	}
	public void setOpzioniInvioCondivisione(OpzioniInvioBean opzioniInvioCondivisione) {
		this.opzioniInvioCondivisione = opzioniInvioCondivisione;
	}
	public String getIdAssegnatario() {
		return idAssegnatario;
	}
	public void setIdAssegnatario(String idAssegnatario) {
		this.idAssegnatario = idAssegnatario;
	}
	public Boolean getFlgAssegnaAlDestinatario() {
		return flgAssegnaAlDestinatario;
	}
	public void setFlgAssegnaAlDestinatario(Boolean flgAssegnaAlDestinatario) {
		this.flgAssegnaAlDestinatario = flgAssegnaAlDestinatario;
	}
	public OpzioniInvioBean getOpzioniInvioAssegnazione() {
		return opzioniInvioAssegnazione;
	}
	public void setOpzioniInvioAssegnazione(OpzioniInvioBean opzioniInvioAssegnazione) {
		this.opzioniInvioAssegnazione = opzioniInvioAssegnazione;
	}
//	public Boolean getFlgPresaInCarico() {
//		return flgPresaInCarico;
//	}
//	public void setFlgPresaInCarico(Boolean flgPresaInCarico) {
//		this.flgPresaInCarico = flgPresaInCarico;
//	}
//	public Boolean getFlgMancataPresaInCarico() {
//		return flgMancataPresaInCarico;
//	}
//	public void setFlgMancataPresaInCarico(Boolean flgMancataPresaInCarico) {
//		this.flgMancataPresaInCarico = flgMancataPresaInCarico;
//	}
//	public Integer getGiorniTrascorsi() {
//		return giorniTrascorsi;
//	}
//	public void setGiorniTrascorsi(Integer giorniTrascorsi) {
//		this.giorniTrascorsi = giorniTrascorsi;
//	}
	public MezzoTrasmissioneDestinatarioBean getMezzoTrasmissioneDestinatario() {
		return mezzoTrasmissioneDestinatario;
	}
	public void setMezzoTrasmissioneDestinatario(MezzoTrasmissioneDestinatarioBean mezzoTrasmissioneDestinatario) {
		this.mezzoTrasmissioneDestinatario = mezzoTrasmissioneDestinatario;
	}
	public String getGruppoSalvato() {
		return gruppoSalvato;
	}
	public void setGruppoSalvato(String gruppoSalvato) {
		this.gruppoSalvato = gruppoSalvato;
	}
}