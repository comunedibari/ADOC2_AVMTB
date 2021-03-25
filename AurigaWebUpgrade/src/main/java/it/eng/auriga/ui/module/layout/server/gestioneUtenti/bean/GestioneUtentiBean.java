package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import java.util.List;
import java.util.Map;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.IndirizzoSoggettoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.SoggettoGruppoBean;

public class GestioneUtentiBean extends GestioneUtentiXmlBean {

	private Integer flgIgnoreWarning;
	private String cognome;
	private String nome;
	private String codFiscale;
	private String codRapido;
	private String password;
	private String confermaPassword;
	private String idProfilo;
	private List<String> idSubProfilo;
	private List<SocietaUtenteBean> listaSocietaUtenti;
	private List<SoggettoGruppoBean> listaClientiUtente;
	private List<IndirizzoSoggettoBean> listaIndirizzi;
	private List<PuntiVenditaBean> listaPuntiVendita;
	private List<UoAssociateUtenteBean> listaUoAssociateUtente;
	private List<DocAssAUOBean> listaVisualizzaDocumentiFascicoliStruttura;
	private List<DocAssAUOBean> listaModificaDocumentiFascicoliStruttura;
	private List<ApplEstAccredBean> listaApplEstAccreditate;
	private String idLogo;
	private String nomeLogo;
	private String idLingua;
	private String descrizioneLingua;
	private List<GruppoClientiBean> listaGruppoClientiUtenti;
	private String idMansione;
	private String nomeMansione;
	private List<AreeCommercialiBean> listaAreeCommerciali;
	private List<VisibEmailCaselleUoBean> listaVisibEmailTransCaselleAssociateUO;
	private String flgUtenteInternoEsterno;
	private String ordFirmatariFFDG;
	private String idUOPP;
	private String listaUOPuntoProtocolloEscluse;
	private String listaUOPuntoProtocolloIncluse;
	private String listaUOPuntoProtocolloEreditarietaAbilitata;
	private List<UOCollegatePuntoProtocolloBean> listaUOCollegatePuntoProtocollo;
	
	// Attributi dinamici
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;

	private String accountDefLocked;
	
	
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCodFiscale() {
		return codFiscale;
	}
	
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getCodRapido() {
	 return codRapido;
	}
	
	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}

	public Integer getFlgIgnoreWarning() {
		return flgIgnoreWarning;
	}

	public void setFlgIgnoreWarning(Integer flgIgnoreWarning) {
		this.flgIgnoreWarning = flgIgnoreWarning;
	}

	public String getIdProfilo() {
		return idProfilo;
	}

	public void setIdProfilo(String idProfilo) {
		this.idProfilo = idProfilo;
	}
	
	
	
	public List<SoggettoGruppoBean> getListaClientiUtente() {
		return listaClientiUtente;
	}

	public void setListaClientiUtente(List<SoggettoGruppoBean> listaClientiUtente) {
		this.listaClientiUtente = listaClientiUtente;
	}

	public List<SocietaUtenteBean> getListaSocietaUtenti() {
		return listaSocietaUtenti;
	}

	public void setListaSocietaUtenti(List<SocietaUtenteBean> listaSocietaUtenti) {
		this.listaSocietaUtenti = listaSocietaUtenti;
	}



	public List<IndirizzoSoggettoBean> getListaIndirizzi() {
		return listaIndirizzi;
	}

	public void setListaIndirizzi(List<IndirizzoSoggettoBean> listaIndirizzi) {
		this.listaIndirizzi = listaIndirizzi;
	}


	public String getIdLogo() {
		return idLogo;
	}

	public void setIdLogo(String idLogo) {
		this.idLogo = idLogo;
	}

	public String getNomeLogo() {
		return nomeLogo;
	}

	public void setNomeLogo(String nomeLogo) {
		this.nomeLogo = nomeLogo;
	}

	public String getIdLingua() {
		return idLingua;
	}

	public void setIdLingua(String idLingua) {
		this.idLingua = idLingua;
	}

	public String getDescrizioneLingua() {
		return descrizioneLingua;
	}

	public void setDescrizioneLingua(String descrizioneLingua) {
		this.descrizioneLingua = descrizioneLingua;
	}

	public List<GruppoClientiBean> getListaGruppoClientiUtenti() {
		return listaGruppoClientiUtenti;
	}

	public void setListaGruppoClientiUtenti(List<GruppoClientiBean> listaGruppoClientiUtenti) {
		this.listaGruppoClientiUtenti = listaGruppoClientiUtenti;
	}



	public String getIdMansione() {
		return idMansione;
	}

	public void setIdMansione(String idMansione) {
		this.idMansione = idMansione;
	}

	public String getNomeMansione() {
		return nomeMansione;
	}

	public void setNomeMansione(String nomeMansione) {
		this.nomeMansione = nomeMansione;
	}

	public List<AreeCommercialiBean> getListaAreeCommerciali() {
		return listaAreeCommerciali;
	}

	public void setListaAreeCommerciali(
			List<AreeCommercialiBean> listaAreeCommerciali) {
		this.listaAreeCommerciali = listaAreeCommerciali;
	}


	public List<PuntiVenditaBean> getListaPuntiVendita() {
		return listaPuntiVendita;
	}

	public void setListaPuntiVendita(List<PuntiVenditaBean> listaPuntiVendita) {
		this.listaPuntiVendita = listaPuntiVendita;
	}

	public List<UoAssociateUtenteBean> getListaUoAssociateUtente() {
		return listaUoAssociateUtente;
	}

	public void setListaUoAssociateUtente(
			List<UoAssociateUtenteBean> listaUoAssociateUtente) {
		this.listaUoAssociateUtente = listaUoAssociateUtente;
	}
    
	
	
	public List<DocAssAUOBean> getListaVisualizzaDocumentiFascicoliStruttura() {
		return listaVisualizzaDocumentiFascicoliStruttura;
	}

	public void setListaVisualizzaDocumentiFascicoliStruttura(
			List<DocAssAUOBean> listaVisualizzaDocumentiFascicoliStruttura) {
		this.listaVisualizzaDocumentiFascicoliStruttura = listaVisualizzaDocumentiFascicoliStruttura;
	}

	public List<DocAssAUOBean> getListaModificaDocumentiFascicoliStruttura() {
		return listaModificaDocumentiFascicoliStruttura;
	}

	public void setListaModificaDocumentiFascicoliStruttura(List<DocAssAUOBean> listaModificaDocumentiFascicoliStruttura) {
		this.listaModificaDocumentiFascicoliStruttura = listaModificaDocumentiFascicoliStruttura;
	}

	public List<ApplEstAccredBean> getListaApplEstAccreditate() {
		return listaApplEstAccreditate;
	}

	public void setListaApplEstAccreditate(
			List<ApplEstAccredBean> listaApplEstAccreditate) {
		this.listaApplEstAccreditate = listaApplEstAccreditate;
	}

	public List<VisibEmailCaselleUoBean> getListaVisibEmailTransCaselleAssociateUO() {
		return listaVisibEmailTransCaselleAssociateUO;
	}

	public void setListaVisibEmailTransCaselleAssociateUO(
			List<VisibEmailCaselleUoBean> listaVisibEmailTransCaselleAssociateUO) {
		this.listaVisibEmailTransCaselleAssociateUO = listaVisibEmailTransCaselleAssociateUO;
	}

	public String getFlgUtenteInternoEsterno() {
		return flgUtenteInternoEsterno;
	}

	public void setFlgUtenteInternoEsterno(String flgUtenteInternoEsterno) {
		this.flgUtenteInternoEsterno = flgUtenteInternoEsterno;
	}

	public Map<String, Object> getValori() {
		return valori;
	}
	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}
	public Map<String, String> getTipiValori() {
		return tipiValori;
	}
	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}

	public String getOrdFirmatariFFDG() {
		return ordFirmatariFFDG;
	}

	public void setOrdFirmatariFFDG(String ordFirmatariFFDG) {
		this.ordFirmatariFFDG = ordFirmatariFFDG;
	}

	
	public String getIdUOPP() {
		return idUOPP;
	}

	public void setIdUOPP(String idUOPP) {
		this.idUOPP = idUOPP;
	}



	public List<UOCollegatePuntoProtocolloBean> getListaUOCollegatePuntoProtocollo() {
		return listaUOCollegatePuntoProtocollo;
	}

	public void setListaUOCollegatePuntoProtocollo(List<UOCollegatePuntoProtocolloBean> listaUOCollegatePuntoProtocollo) {
		this.listaUOCollegatePuntoProtocollo = listaUOCollegatePuntoProtocollo;
	}

	public String getListaUOPuntoProtocolloEscluse() {
		return listaUOPuntoProtocolloEscluse;
	}

	public void setListaUOPuntoProtocolloEscluse(String listaUOPuntoProtocolloEscluse) {
		this.listaUOPuntoProtocolloEscluse = listaUOPuntoProtocolloEscluse;
	}

	public String getListaUOPuntoProtocolloIncluse() {
		return listaUOPuntoProtocolloIncluse;
	}

	public void setListaUOPuntoProtocolloIncluse(String listaUOPuntoProtocolloIncluse) {
		this.listaUOPuntoProtocolloIncluse = listaUOPuntoProtocolloIncluse;
	}

	
	public String getListaUOPuntoProtocolloEreditarietaAbilitata() {
		return listaUOPuntoProtocolloEreditarietaAbilitata;
	}

	public void setListaUOPuntoProtocolloEreditarietaAbilitata(String listaUOPuntoProtocolloEreditarietaAbilitata) {
		this.listaUOPuntoProtocolloEreditarietaAbilitata = listaUOPuntoProtocolloEreditarietaAbilitata;
	}

	public String getAccountDefLocked() {
		return accountDefLocked;
	}

	public void setAccountDefLocked(String accountDefLocked) {
		this.accountDefLocked = accountDefLocked;
	}

	public List<String> getIdSubProfilo() {
		return idSubProfilo;
	}

	public void setIdSubProfilo(List<String> idSubProfilo) {
		this.idSubProfilo = idSubProfilo;
	}

}