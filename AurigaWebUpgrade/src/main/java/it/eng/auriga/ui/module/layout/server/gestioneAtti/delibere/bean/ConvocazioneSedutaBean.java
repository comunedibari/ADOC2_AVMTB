package it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean;

import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.shared.bean.FileDocumentoBean;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author DANCRIST
 *
 */

public class ConvocazioneSedutaBean {
	
	private String organoCollegiale; // Valori possibili: giunta, consiglio, commissione
	private String listaCommissioni;
	
	private String idSeduta;
	private String numero;
	private Date dtPrimaConvocazione;
	private String luogoPrimaConvocazione;
	private Date dtSecondaConvocazione;
	private String luogoSecondaConvocazione;
	/**
	 * Valori possibili: nuova, OdG_in_preparazione, OdG_chiuso, inviata_convocazione
	 */
	private String stato;
	private String desStato;
	/**
	 * Valori possibili: ordinaria, straordinaria, urgente
	 */
	@XmlVariabile(nome = "tipoSessione", tipo = TipoVariabile.SEMPLICE)
	private String tipoSessione;
	
	private OdgInfoXmlBean odgInfo; 
	
	private List<ArgomentiOdgXmlBean> listaArgomentiOdg;
	private List<ArgomentiOdgXmlBean> listaArgomentiOdgEliminati;
	
	private List<PresenzeOdgXmlBean> listaPresenzeOdg;
	
	private ConvocazioneInfoXmlBean convocazioneInfo;
	
	private FileDocumentoBean fileDocumento;
	
	private String destinatari;
	
	public String getOrganoCollegiale() {
		return organoCollegiale;
	}
	public void setOrganoCollegiale(String organoCollegiale) {
		this.organoCollegiale = organoCollegiale;
	}
	public String getListaCommissioni() {
		return listaCommissioni;
	}
	public void setListaCommissioni(String listaCommissioni) {
		this.listaCommissioni = listaCommissioni;
	} 
	public String getIdSeduta() {
		return idSeduta;
	}
	public void setIdSeduta(String idSeduta) {
		this.idSeduta = idSeduta;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Date getDtPrimaConvocazione() {
		return dtPrimaConvocazione;
	}
	public void setDtPrimaConvocazione(Date dtPrimaConvocazione) {
		this.dtPrimaConvocazione = dtPrimaConvocazione;
	}
	public String getLuogoPrimaConvocazione() {
		return luogoPrimaConvocazione;
	}
	public void setLuogoPrimaConvocazione(String luogoPrimaConvocazione) {
		this.luogoPrimaConvocazione = luogoPrimaConvocazione;
	}
	public Date getDtSecondaConvocazione() {
		return dtSecondaConvocazione;
	}
	public void setDtSecondaConvocazione(Date dtSecondaConvocazione) {
		this.dtSecondaConvocazione = dtSecondaConvocazione;
	}
	public String getLuogoSecondaConvocazione() {
		return luogoSecondaConvocazione;
	}
	public void setLuogoSecondaConvocazione(String luogoSecondaConvocazione) {
		this.luogoSecondaConvocazione = luogoSecondaConvocazione;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getDesStato() {
		return desStato;
	}
	public void setDesStato(String desStato) {
		this.desStato = desStato;
	}
	public String getTipoSessione() {
		return tipoSessione;
	}
	public void setTipoSessione(String tipoSessione) {
		this.tipoSessione = tipoSessione;
	}
	public OdgInfoXmlBean getOdgInfo() {
		return odgInfo;
	}
	public void setOdgInfo(OdgInfoXmlBean odgInfo) {
		this.odgInfo = odgInfo;
	}
	public List<ArgomentiOdgXmlBean> getListaArgomentiOdg() {
		return listaArgomentiOdg;
	}
	public void setListaArgomentiOdg(List<ArgomentiOdgXmlBean> listaArgomentiOdg) {
		this.listaArgomentiOdg = listaArgomentiOdg;
	}
	public List<PresenzeOdgXmlBean> getListaPresenzeOdg() {
		return listaPresenzeOdg;
	}
	public void setListaPresenzeOdg(List<PresenzeOdgXmlBean> listaPresenzeOdg) {
		this.listaPresenzeOdg = listaPresenzeOdg;
	}
	public FileDocumentoBean getFileDocumento() {
		return fileDocumento;
	}
	public void setFileDocumento(FileDocumentoBean fileDocumento) {
		this.fileDocumento = fileDocumento;
	}
	public String getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}
	public List<ArgomentiOdgXmlBean> getListaArgomentiOdgEliminati() {
		return listaArgomentiOdgEliminati;
	}
	public void setListaArgomentiOdgEliminati(List<ArgomentiOdgXmlBean> listaArgomentiOdgEliminati) {
		this.listaArgomentiOdgEliminati = listaArgomentiOdgEliminati;
	}
	public ConvocazioneInfoXmlBean getConvocazioneInfo() {
		return convocazioneInfo;
	}
	public void setConvocazioneInfo(ConvocazioneInfoXmlBean convocazioneInfo) {
		this.convocazioneInfo = convocazioneInfo;
	}
	
}