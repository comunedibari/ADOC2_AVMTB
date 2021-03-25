package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioCCBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CondivisioneBean extends OperazioneMassivaArchivioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String flgUdFolder;
	private String idNotifica;
	private List<DestInvioCCBean> listaDestInvioCC;
	private String motivoInvio;
	private String messaggioInvio;
	private BigDecimal livelloPriorita;
	private Boolean flgInviaFascicolo;
	private Boolean flgInviaDocCollegati;
	private Boolean flgMandaNotificaMail;
	
	public String getFlgUdFolder() {
		return flgUdFolder;
	}
	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}
	public String getIdNotifica() {
		return idNotifica;
	}
	public void setIdNotifica(String idNotifica) {
		this.idNotifica = idNotifica;
	}
	public List<DestInvioCCBean> getListaDestInvioCC() {
		return listaDestInvioCC;
	}
	public void setListaDestInvioCC(List<DestInvioCCBean> listaDestInvioCC) {
		this.listaDestInvioCC = listaDestInvioCC;
	}
	public String getMotivoInvio() {
		return motivoInvio;
	}
	public void setMotivoInvio(String motivoInvio) {
		this.motivoInvio = motivoInvio;
	}
	public String getMessaggioInvio() {
		return messaggioInvio;
	}
	public void setMessaggioInvio(String messaggioInvio) {
		this.messaggioInvio = messaggioInvio;
	}
	public BigDecimal getLivelloPriorita() {
		return livelloPriorita;
	}
	public void setLivelloPriorita(BigDecimal livelloPriorita) {
		this.livelloPriorita = livelloPriorita;
	}
	public Boolean getFlgInviaFascicolo() {
		return flgInviaFascicolo;
	}
	public void setFlgInviaFascicolo(Boolean flgInviaFascicolo) {
		this.flgInviaFascicolo = flgInviaFascicolo;
	}
	public Boolean getFlgInviaDocCollegati() {
		return flgInviaDocCollegati;
	}
	public void setFlgInviaDocCollegati(Boolean flgInviaDocCollegati) {
		this.flgInviaDocCollegati = flgInviaDocCollegati;
	}
	public Boolean getFlgMandaNotificaMail() {
		return flgMandaNotificaMail;
	}
	public void setFlgMandaNotificaMail(Boolean flgMandaNotificaMail) {
		this.flgMandaNotificaMail = flgMandaNotificaMail;
	}
}