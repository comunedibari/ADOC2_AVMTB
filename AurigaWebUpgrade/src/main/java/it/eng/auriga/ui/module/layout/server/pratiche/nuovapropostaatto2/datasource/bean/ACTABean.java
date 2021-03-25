package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

public class ACTABean {
	
	//INPUT
	private String indiceClassificazioneEstesa;
	private String descrizioneVoceTitolario;
	private String codiceFascicoloDossier;
	private String suffissoCodiceFascicoloDossier;
	
	//OUTPUT
	private Boolean presenzaIndiceClassificazione;
	private String idFascicoloDossier;
	
	
	public String getIndiceClassificazioneEstesa() {
		return indiceClassificazioneEstesa;
	}
	public void setIndiceClassificazioneEstesa(String indiceClassificazioneEstesa) {
		this.indiceClassificazioneEstesa = indiceClassificazioneEstesa;
	}
	public String getDescrizioneVoceTitolario() {
		return descrizioneVoceTitolario;
	}
	public void setDescrizioneVoceTitolario(String descrizioneVoceTitolario) {
		this.descrizioneVoceTitolario = descrizioneVoceTitolario;
	}
	public String getCodiceFascicoloDossier() {
		return codiceFascicoloDossier;
	}
	public void setCodiceFascicoloDossier(String codiceFascicoloDossier) {
		this.codiceFascicoloDossier = codiceFascicoloDossier;
	}
	public String getSuffissoCodiceFascicoloDossier() {
		return suffissoCodiceFascicoloDossier;
	}
	public void setSuffissoCodiceFascicoloDossier(String suffissoCodiceFascicoloDossier) {
		this.suffissoCodiceFascicoloDossier = suffissoCodiceFascicoloDossier;
	}	
	public Boolean getPresenzaIndiceClassificazione() {
		return presenzaIndiceClassificazione;
	}
	public void setPresenzaIndiceClassificazione(Boolean presenzaIndiceClassificazione) {
		this.presenzaIndiceClassificazione = presenzaIndiceClassificazione;
	}
	public String getIdFascicoloDossier() {
		return idFascicoloDossier;
	}
	public void setIdFascicoloDossier(String idFascicoloDossier) {
		this.idFascicoloDossier = idFascicoloDossier;
	}
	
}