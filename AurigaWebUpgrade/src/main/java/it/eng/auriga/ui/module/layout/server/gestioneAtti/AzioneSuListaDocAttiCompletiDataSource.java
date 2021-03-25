package it.eng.auriga.ui.module.layout.server.gestioneAtti;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreSetvisionatoBean;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfAzionesulistadocattiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.EsistiVisionatoXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AttiCompletiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AzioneSuListaAttiCompletiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.DocInfoLibroFirma;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.ItemTagAttiCompletiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.TagAttiCompletiSezioneCacheBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.TagAttoCompletoXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.TagAttoXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.XmlDettAzioneSuListaAttiBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.ContabiliaDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.NuovaPropostaAtto2CompletaDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.SIBDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.SICRADataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.client.DmpkCoreSetvisionato;
import it.eng.client.DmpkWfAzionesulistadocatti;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AzioneSuListaDocAttiCompletiDataSource")
public class AzioneSuListaDocAttiCompletiDataSource extends AbstractDataSource<AzioneSuListaAttiCompletiBean, AzioneSuListaAttiCompletiBean> {	
	
	private static Logger mLogger = Logger.getLogger(AzioneSuListaDocAttiCompletiDataSource.class);
	
	public static final String _SOTTOSCRIZIONE_CONSIGLIERE = "SOTTOSCRIZIONE_CONSIGLIERE";
	public static final String _ELIMINAZIONE_SOTTOSCRIZIONE_CONSIGLIERE = "ELIMINAZIONE_SOTTOSCRIZIONE_CONSIGLIERE";
	public static final String _PRESENTAZIONE_FIRMATARIO = "PRESENTAZIONE_FIRMATARIO";
	public static final String _RITIRO_FIRMATARIO = "RITIRO_FIRMATARIO";
	public static final String _ANNULLAMENTO = "ANNULLAMENTO";
	public static final String _RILASCIO_VISTO = "RILASCIO_VISTO";
	public static final String _RIFIUTO_VISTO = "RIFIUTO_VISTO";
	public static final String _EVENTO_AMC = "EVENTO_AMC";

	public NuovaPropostaAtto2CompletaDataSource getNuovaPropostaAtto2CompletaDataSource() {			
		NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = new NuovaPropostaAtto2CompletaDataSource() {
			
			@Override
			public boolean isAttivaRequestMovimentiDaAMC(NuovaPropostaAtto2CompletaBean bean) {
				return false;
			}
		};
		lNuovaPropostaAtto2CompletaDataSource.setSession(getSession());
		lNuovaPropostaAtto2CompletaDataSource.setExtraparams(getExtraparams());	
		// devo settare in ProtocolloDataSource i messages di NuovaPropostaAtto2CompletaDataSource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lNuovaPropostaAtto2CompletaDataSource.setMessages(getMessages()); 		
		return lNuovaPropostaAtto2CompletaDataSource;
	}
	
	public SIBDataSource getSIBDataSource() {	
		SIBDataSource lSIBDataSource = new SIBDataSource();
		lSIBDataSource.setSession(getSession());
		lSIBDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lSIBDataSource.setMessages(getMessages()); 		
		return lSIBDataSource;
	}	
	
	public ContabiliaDataSource getContabiliaDataSource() {
		ContabiliaDataSource lContabiliaDataSource = new ContabiliaDataSource();
		lContabiliaDataSource.setSession(getSession());
		lContabiliaDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lContabiliaDataSource.setMessages(getMessages()); 		
		return lContabiliaDataSource;
	}	
	
	public SICRADataSource getSICRADataSource() {
		SICRADataSource lSICRADataSource = new SICRADataSource();
		lSICRADataSource.setSession(getSession());
		lSICRADataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lSICRADataSource.setMessages(getMessages()); 		
		return lSICRADataSource;
	}	
	
	public boolean isAttivoSIB() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SIB".equalsIgnoreCase(lSistAMC);
	}
	
	public boolean isAttivoContabilia() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && ("CONTABILIA".equalsIgnoreCase(lSistAMC) || "CONTABILIA2".equalsIgnoreCase(lSistAMC));
	}
	
	public boolean isAttivoSICRA() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SICRA".equalsIgnoreCase(lSistAMC);
	}

	@Override
	public AzioneSuListaAttiCompletiBean add(AzioneSuListaAttiCompletiBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = new HashMap<String, String>();;		
		
		boolean isAttivaNuovaPropostaAtto2Completa = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_NUOVA_PROPOSTA_ATTO_2") && ParametriDBUtil.getParametroDBAsBoolean(getSession(), "GESTIONE_ATTI_COMPLETA");
		
		if(isAttivaNuovaPropostaAtto2Completa && bean.getAzione() != null && _EVENTO_AMC.equalsIgnoreCase(bean.getAzione()) && StringUtils.isNotBlank(bean.getEventoAMC())) {
				
			for(AttiCompletiBean attoBean : bean.getListaRecord()) {
				// se non è andato in errore
				if(!errorMessages.containsKey(attoBean.getUnitaDocumentariaId())) {
					// se è un atto con rilevanza contabile ed è attiva l'integrazione con AMC
					if(attoBean.getFlgRilevanzaContabile() != null && "1".equals(attoBean.getFlgRilevanzaContabile())) {
						if(isAttivoSIB()) {	
							NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean(); 
							lNuovaPropostaAtto2CompletaBean.setIdUd(attoBean.getUnitaDocumentariaId());		
							lNuovaPropostaAtto2CompletaBean = getNuovaPropostaAtto2CompletaDataSource().get(lNuovaPropostaAtto2CompletaBean);	
							lNuovaPropostaAtto2CompletaBean.setEventoSIB(bean.getEventoAMC());
							getSIBDataSource().aggiornaAtto(lNuovaPropostaAtto2CompletaBean);										
							if(lNuovaPropostaAtto2CompletaBean.getEsitoEventoSIB() != null && lNuovaPropostaAtto2CompletaBean.getEsitoEventoSIB().equals("KO")) {
								errorMessages.put(attoBean.getUnitaDocumentariaId(), "Si è verificato un errore durante la chiamata al servizio " + bean.getEventoAMC() + " di SIB: " + lNuovaPropostaAtto2CompletaBean.getErrMsgEventoSIB());																	
							} 																		
						}
						if(isAttivoContabilia()) {
							NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean(); 
							lNuovaPropostaAtto2CompletaBean.setIdUd(attoBean.getUnitaDocumentariaId());		
							lNuovaPropostaAtto2CompletaBean = getNuovaPropostaAtto2CompletaDataSource().get(lNuovaPropostaAtto2CompletaBean);						
							if(getNuovaPropostaAtto2CompletaDataSource().isAttivoContabilia(lNuovaPropostaAtto2CompletaBean)) {			
								if(lNuovaPropostaAtto2CompletaBean.getFlgSpesa() != null && lNuovaPropostaAtto2CompletaBean.getFlgSpesa().toUpperCase().startsWith(NuovaPropostaAtto2CompletaDataSource._FLG_SI)) {																		
									if("aggiornaProposta".equalsIgnoreCase(bean.getEventoAMC())) {
										getContabiliaDataSource().aggiornaProposta(lNuovaPropostaAtto2CompletaBean);
									} else if("bloccoDatiProposta".equalsIgnoreCase(bean.getEventoAMC())) {
										getContabiliaDataSource().bloccoDatiProposta(lNuovaPropostaAtto2CompletaBean);				
									} else if("invioAttoDef".equalsIgnoreCase(bean.getEventoAMC())) {
										getContabiliaDataSource().invioAttoDef(lNuovaPropostaAtto2CompletaBean);				
									} else if("invioAttoDefEsec".equalsIgnoreCase(bean.getEventoAMC())) {
										getContabiliaDataSource().invioAttoDefEsec(lNuovaPropostaAtto2CompletaBean);				
									} else if("invioAttoEsec".equalsIgnoreCase(bean.getEventoAMC())) {
										getContabiliaDataSource().invioAttoEsec(lNuovaPropostaAtto2CompletaBean);				
									} else if("sbloccoDatiProposta".equalsIgnoreCase(bean.getEventoAMC())) {
										getContabiliaDataSource().sbloccoDatiProposta(lNuovaPropostaAtto2CompletaBean);				
									} else if("annullamentoProposta".equalsIgnoreCase(bean.getEventoAMC())) {
										getContabiliaDataSource().annullamentoProposta(lNuovaPropostaAtto2CompletaBean);				
									}
									if(lNuovaPropostaAtto2CompletaBean.getEsitoEventoContabilia() != null && lNuovaPropostaAtto2CompletaBean.getEsitoEventoContabilia().equals("KO")) {
										errorMessages.put(attoBean.getUnitaDocumentariaId(), "Si è verificato un errore durante la chiamata al servizio " + bean.getEventoAMC() + " di Contabilia: " + lNuovaPropostaAtto2CompletaBean.getErrMsgEventoContabilia());																	
									} 																		
								} else {
									errorMessages.put(attoBean.getUnitaDocumentariaId(), "Atto senza rilevanza contabile");												
								}
							} else {
								errorMessages.put(attoBean.getUnitaDocumentariaId(), "Nessuna integrazione prevista per questa tipologia di atto (" + lNuovaPropostaAtto2CompletaBean.getSiglaRegProvvisoria() + ")");												
							}
						}
						if(isAttivoSICRA()) {
							NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean(); 
							lNuovaPropostaAtto2CompletaBean.setIdUd(attoBean.getUnitaDocumentariaId());		
							lNuovaPropostaAtto2CompletaBean = getNuovaPropostaAtto2CompletaDataSource().get(lNuovaPropostaAtto2CompletaBean);	
							//TODO se setto direttamente i campi qui sotto da lista senza fare la load devo togliere il controllo sotto (flgSpesa che inizia con SI)
//							lNuovaPropostaAtto2CompletaBean.setSiglaRegProvvisoria();
//							lNuovaPropostaAtto2CompletaBean.setNumeroRegProvvisoria();
//							lNuovaPropostaAtto2CompletaBean.setAnnooRegProvvisoria();
//							lNuovaPropostaAtto2CompletaBean.setDataRegProvvisoria();
							if(getNuovaPropostaAtto2CompletaDataSource().isAttivoSICRA(lNuovaPropostaAtto2CompletaBean)) {
								if(lNuovaPropostaAtto2CompletaBean.getFlgSpesa() != null && lNuovaPropostaAtto2CompletaBean.getFlgSpesa().toUpperCase().startsWith(NuovaPropostaAtto2CompletaDataSource._FLG_SI)) {																			
									if("archiviaAtto".equalsIgnoreCase(bean.getEventoAMC())) {
										getSICRADataSource().archiviaAtto(lNuovaPropostaAtto2CompletaBean);
									} else if("setEsecutivitaAtto".equalsIgnoreCase(bean.getEventoAMC())) {
										getSICRADataSource().setEsecutivitaAtto(lNuovaPropostaAtto2CompletaBean);				
									}
									if(lNuovaPropostaAtto2CompletaBean.getEsitoEventoSICRA() != null && lNuovaPropostaAtto2CompletaBean.getEsitoEventoSICRA().equals("KO")) {
										errorMessages.put(attoBean.getUnitaDocumentariaId(), "Si è verificato un'errore durante la chiamata al servizio " + bean.getEventoAMC() + " di SICRA: " + lNuovaPropostaAtto2CompletaBean.getErrMsgEventoSICRA());								
									}									
								} else {
									errorMessages.put(attoBean.getUnitaDocumentariaId(), "Atto senza rilevanza contabile");		
								}
							} else {
								errorMessages.put(attoBean.getUnitaDocumentariaId(), "Nessuna integrazione prevista per questa tipologia di atto (" + lNuovaPropostaAtto2CompletaBean.getSiglaRegProvvisoria() + ")");
							}
						}
					} else {
						errorMessages.put(attoBean.getUnitaDocumentariaId(), "Atto senza rilevanza contabile");		
					}
				}					
			}				
			
		} else {
		
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			
			DmpkWfAzionesulistadocattiBean input = new DmpkWfAzionesulistadocattiBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
			input.setAzionein(bean.getAzione());
			input.setListapropattiin(lXmlUtilitySerializer.bindXmlList(getListaPropAtti(bean)));		
			input.setXmldettazionein(lXmlUtilitySerializer.bindXml(getXmlDettAzione(bean)));
					
			DmpkWfAzionesulistadocatti store = new DmpkWfAzionesulistadocatti();
			StoreResultBean<DmpkWfAzionesulistadocattiBean> output = store.execute(getLocale(),loginBean, input);
				
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					throw new StoreException(output);
				} else {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
	
			if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {				
				StringReader sr = new StringReader(output.getResultBean().getEsitiout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					if (v.get(3).equalsIgnoreCase("ko")) {
						errorMessages.put(v.get(0), v.get(4));
					}
				}
			}
			
			if(isAttivaNuovaPropostaAtto2Completa && bean.getAzione() != null && _ANNULLAMENTO.equalsIgnoreCase(bean.getAzione())) {
				for(AttiCompletiBean attoBean : bean.getListaRecord()) {
					// se non è andato in errore
					if(!errorMessages.containsKey(attoBean.getUnitaDocumentariaId())) {
						// se è un atto con rilevanza contabile ed è attiva l'integrazione con Contabilia o con SICRA
						if(attoBean.getFlgRilevanzaContabile() != null && "1".equals(attoBean.getFlgRilevanzaContabile())) {
							if(isAttivoContabilia()) {
								NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean(); 
								lNuovaPropostaAtto2CompletaBean.setIdUd(attoBean.getUnitaDocumentariaId());		
								lNuovaPropostaAtto2CompletaBean = getNuovaPropostaAtto2CompletaDataSource().get(lNuovaPropostaAtto2CompletaBean);						
								if(getNuovaPropostaAtto2CompletaDataSource().isAttivoContabilia(lNuovaPropostaAtto2CompletaBean)) {			
									if(lNuovaPropostaAtto2CompletaBean.getFlgSpesa() != null && lNuovaPropostaAtto2CompletaBean.getFlgSpesa().toUpperCase().startsWith(NuovaPropostaAtto2CompletaDataSource._FLG_SI)) {		
										getContabiliaDataSource().annullamentoProposta(lNuovaPropostaAtto2CompletaBean);				
										if(lNuovaPropostaAtto2CompletaBean.getEsitoEventoContabilia() != null && lNuovaPropostaAtto2CompletaBean.getEsitoEventoContabilia().equals("KO")) {
											errorMessages.put(attoBean.getUnitaDocumentariaId(), "Si è verificato un errore durante la chiamata al servizio annullamentoProposta di Contabilia: " + lNuovaPropostaAtto2CompletaBean.getErrMsgEventoSICRA());																	
										} 										
									}
								}	
							}
							if(isAttivoSICRA()) {
								NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean(); 
								lNuovaPropostaAtto2CompletaBean.setIdUd(attoBean.getUnitaDocumentariaId());		
								lNuovaPropostaAtto2CompletaBean = getNuovaPropostaAtto2CompletaDataSource().get(lNuovaPropostaAtto2CompletaBean);	
								//TODO se setto direttamente i campi qui sotto da lista senza fare la load devo togliere il controllo sotto (flgSpesa che inizia con SI)
	//							lNuovaPropostaAtto2CompletaBean.setSiglaRegProvvisoria();
	//							lNuovaPropostaAtto2CompletaBean.setNumeroRegProvvisoria();
	//							lNuovaPropostaAtto2CompletaBean.setAnnooRegProvvisoria();
	//							lNuovaPropostaAtto2CompletaBean.setDataRegProvvisoria();
								if(getNuovaPropostaAtto2CompletaDataSource().isAttivoSICRA(lNuovaPropostaAtto2CompletaBean)) {
									if(lNuovaPropostaAtto2CompletaBean.getFlgSpesa() != null && lNuovaPropostaAtto2CompletaBean.getFlgSpesa().toUpperCase().startsWith(NuovaPropostaAtto2CompletaDataSource._FLG_SI)) {	
										getSICRADataSource().archiviaAtto(lNuovaPropostaAtto2CompletaBean);							
										if(lNuovaPropostaAtto2CompletaBean.getEsitoEventoSICRA() != null && lNuovaPropostaAtto2CompletaBean.getEsitoEventoSICRA().equals("KO")) {
											errorMessages.put(attoBean.getUnitaDocumentariaId(), "Si è verificato un'errore durante la chiamata al servizio archiviaAtto di SICRA: " + lNuovaPropostaAtto2CompletaBean.getErrMsgEventoSICRA());								
										}
									}
								}
							}
						}
					}					
				}
			}
		
		}
		
		bean.setErrorMessages(errorMessages);		
		
		return bean;
	}	
	
	public AzioneSuListaAttiCompletiBean segnaComeVisionato(AzioneSuListaAttiCompletiBean bean) throws Exception {
		
	    if (bean.getListaRecord() !=null && !bean.getListaRecord().isEmpty()){
	    	
	    	HashMap<String, String> errorMessages = new HashMap<String, String>();
	    	
	    	AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();
			
			DmpkCoreSetvisionatoBean input = new DmpkCoreSetvisionatoBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
			
			// Prendo le NOTE dal primo record
			String note = "";
			if (bean.getListaRecord().get(0)!=null){
				note = bean.getListaRecord().get(0).getNote();	
			}
			
			// Estrae la lista degli id delle UNITA' DOCUMENTARIE (U) e la lista degli id dei FOLDER (F)
			List<SimpleValueBean> listaIdUd = new ArrayList<SimpleValueBean>();	
			estraiIdUd(bean, listaIdUd);
			
			// Se ci sono unita' documentarie chiamo la store e li setto come visionati
			if (listaIdUd !=null && !listaIdUd.isEmpty()){
				// -- (ogglig) Tipo di oggetti da segnare come visionati: U = Unità documentarie
				input.setFlgtpobjin("U");  
				
				// -- Lista XML Standard con una sola colonna con gli ID delle UD o folder da marcare come visionati	
				input.setIdobjlistin(new XmlUtilitySerializer().bindXmlList(listaIdUd));
							  
				// -- Annotazioni alla presa visione
				input.setNotein(note);
								
				DmpkCoreSetvisionato servizio = new DmpkCoreSetvisionato();
				StoreResultBean<DmpkCoreSetvisionatoBean> output = servizio.execute(this.getLocale(), loginBean, input);
				if (output.getDefaultMessage() != null) {		
					// Leggo gli esiti
					String xmlEsitiOut = output.getResultBean().getEsitiout();
					if (xmlEsitiOut != null) {
						List<EsistiVisionatoXmlBean> esiti = XmlListaUtility.recuperaLista(xmlEsitiOut, EsistiVisionatoXmlBean.class);
						if (esiti!=null && esiti.size()>0){
							for (EsistiVisionatoXmlBean esitoBean : esiti) {
								// Se e' KO
								if (StringUtils.isNotBlank(esitoBean.getEsito()) && esitoBean.getEsito().equalsIgnoreCase("KO")){
									errorMessages.put(esitoBean.getIdUdFolder(), esitoBean.getMessaggioErrore());
								}								   
							}
						}
					}
				}
			}
			
			if(errorMessages != null && !errorMessages.isEmpty()) {
				bean.setErrorMessages(errorMessages);
			}
	    }	    
	    
		return bean;
	}
	
	private void estraiIdUd(AzioneSuListaAttiCompletiBean bean, List<SimpleValueBean> listaIdUdOut) throws Exception {	
		
		for (int i = 0; i < bean.getListaRecord().size(); ++i) {
			AttiCompletiBean item = (AttiCompletiBean) bean.getListaRecord().get(i);
			SimpleValueBean lSimpleValueBean = new SimpleValueBean();
			lSimpleValueBean.setValue(item.getUnitaDocumentariaId());
			listaIdUdOut.add(lSimpleValueBean);					
		}
	}
	
	private List<DocInfoLibroFirma> getListaPropAtti(AzioneSuListaAttiCompletiBean bean) throws Exception {
		List<DocInfoLibroFirma> listaPropAtti = new ArrayList<DocInfoLibroFirma>();
		if(bean.getListaRecord() != null){
			for(AttiCompletiBean attoBean : bean.getListaRecord()) {
				DocInfoLibroFirma lDocInfoLibroFirma = new DocInfoLibroFirma();
				lDocInfoLibroFirma.setIdUd(attoBean.getUnitaDocumentariaId());
				lDocInfoLibroFirma.setIdProcess(attoBean.getIdProcedimento());
				listaPropAtti.add(lDocInfoLibroFirma);
			}
		}
		return listaPropAtti;
	}
	
	private XmlDettAzioneSuListaAttiBean getXmlDettAzione(AzioneSuListaAttiCompletiBean bean) throws Exception {
		XmlDettAzioneSuListaAttiBean scXmlDettAzione = new XmlDettAzioneSuListaAttiBean();
		if(StringUtils.isNotBlank(bean.getAzione())) {
			if(_SOTTOSCRIZIONE_CONSIGLIERE.equalsIgnoreCase(bean.getAzione())) {
				
			} else if(_ELIMINAZIONE_SOTTOSCRIZIONE_CONSIGLIERE.equalsIgnoreCase(bean.getAzione())) {
				
			} else if(_PRESENTAZIONE_FIRMATARIO.equalsIgnoreCase(bean.getAzione())) {
				
			} else if(_RITIRO_FIRMATARIO.equalsIgnoreCase(bean.getAzione())) {
				scXmlDettAzione.setMotivo(bean.getMotivoOsservazioni());
			} else if(_ANNULLAMENTO.equalsIgnoreCase(bean.getAzione())) {
				scXmlDettAzione.setMotivo(bean.getMotivoOsservazioni());
			} else if(_RILASCIO_VISTO.equalsIgnoreCase(bean.getAzione())) {
				scXmlDettAzione.setOsservazioni(bean.getMotivoOsservazioni());
			} else if(_RIFIUTO_VISTO.equalsIgnoreCase(bean.getAzione())) {
				scXmlDettAzione.setOsservazioni(bean.getMotivoOsservazioni());
			}
		}		
		return scXmlDettAzione;
	}
	
	public AzioneSuListaAttiCompletiBean saveTagCommenti(AzioneSuListaAttiCompletiBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		DmpkWfAzionesulistadocattiBean input = new DmpkWfAzionesulistadocattiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setAzionein(pInBean.getAzione());
		input.setListapropattiin(getListaTagAtti(pInBean));
		
		TagAttiCompletiSezioneCacheBean tagAttiCompletiSezioneCacheBean = new TagAttiCompletiSezioneCacheBean();
		tagAttiCompletiSezioneCacheBean.setListaTag(getTag(pInBean.getItemTagAttiCompleti()));
		input.setXmldettazionein(new XmlUtilitySerializer().bindXml(tagAttiCompletiSezioneCacheBean));
		
		DmpkWfAzionesulistadocatti dmpkWfAzionesulistadocatti = new DmpkWfAzionesulistadocatti();
		StoreResultBean<DmpkWfAzionesulistadocattiBean> result = dmpkWfAzionesulistadocatti.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + result.getDefaultMessage());
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		if(result.getResultBean().getEsitiout() != null && !"".equals(result.getResultBean().getEsitiout())){
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(result.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);

			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));

				if (v.get(3).equalsIgnoreCase("ko") || v.get(3).equalsIgnoreCase("KO")) {
					errorMessages.put(v.get(2), v.get(4));
				}
			}
			pInBean.setErrorMessages(errorMessages);
		}
		
		return pInBean;
	}
	
	public AzioneSuListaAttiCompletiBean eliminaTagCommenti(AzioneSuListaAttiCompletiBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		DmpkWfAzionesulistadocattiBean input = new DmpkWfAzionesulistadocattiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setAzionein(pInBean.getAzione());
		input.setListapropattiin(getListaTagAtti(pInBean));
		TagAttiCompletiSezioneCacheBean tagAttiCompletiSezioneCacheBean = new TagAttiCompletiSezioneCacheBean();
		tagAttiCompletiSezioneCacheBean.setListaTag(getTag(pInBean.getItemTagAttiCompleti()));
		input.setXmldettazionein(new XmlUtilitySerializer().bindXml(tagAttiCompletiSezioneCacheBean));
		
		DmpkWfAzionesulistadocatti dmpkWfAzionesulistadocattiv = new DmpkWfAzionesulistadocatti();
		StoreResultBean<DmpkWfAzionesulistadocattiBean> result = dmpkWfAzionesulistadocattiv.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + result.getDefaultMessage());
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		if(result.getResultBean().getEsitiout() != null && !"".equals(result.getResultBean().getEsitiout())){
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(result.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);

			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));

				if (v.get(3).equalsIgnoreCase("ko") || v.get(3).equalsIgnoreCase("KO")) {
					errorMessages.put(v.get(2), v.get(4));
				}
			}
			pInBean.setErrorMessages(errorMessages);
		}
		
		return pInBean;
	}
	
//	public AzioneSuListaAttiCompletiBean invioTardivoRagioneria(AzioneSuListaAttiCompletiBean pInBean) throws Exception {
//		
//		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
//
//		String token = loginBean.getToken();
//		String idUserLavoro = loginBean.getIdUserLavoro();
//		
//		HashMap<String, String> errorMessages = null;
//		
//		DmpkWfAzionesulistadocattiBean input = new DmpkWfAzionesulistadocattiBean();
//		input.setCodidconnectiontokenin(token);
//		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
//		input.setAzionein(pInBean.getAzione());
//		input.setListapropattiin(getListaTagAtti(pInBean));
//		
//		DmpkWfAzionesulistadocatti dmpkWfAzionesulistadocattiv = new DmpkWfAzionesulistadocatti();
//		StoreResultBean<DmpkWfAzionesulistadocattiBean> result = dmpkWfAzionesulistadocattiv.execute(getLocale(), loginBean, input);
//		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
//			if (result.isInError()) {
//				mLogger.error("Errore nel recupero dell'output: " + result.getDefaultMessage());
//				throw new StoreException(result.getDefaultMessage());
//			} else {
//				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
//			}
//		}
//
//		if(result.getResultBean().getEsitiout() != null && !"".equals(result.getResultBean().getEsitiout())){
//			errorMessages = new HashMap<String, String>();
//			StringReader sr = new StringReader(result.getResultBean().getEsitiout());
//			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
//
//			for (int i = 0; i < lista.getRiga().size(); i++) {
//				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
//
//				if (v.get(3).equalsIgnoreCase("ko") || v.get(3).equalsIgnoreCase("KO")) {
//					errorMessages.put(v.get(2), v.get(4));
//				}
//			}
//			pInBean.setErrorMessages(errorMessages);
//		}
//		
//		return pInBean;
//	}
	
	private String getListaTagAtti(AzioneSuListaAttiCompletiBean pInBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, JAXBException {
		
		String attiCompleti = null;
		List<TagAttoCompletoXmlBean> listaAtti = new ArrayList<TagAttoCompletoXmlBean>();
		for(AttiCompletiBean item : pInBean.getListaRecord()){
			TagAttoCompletoXmlBean tagAttoCompletoXmlBean = new TagAttoCompletoXmlBean();
			tagAttoCompletoXmlBean.setIdUd(item.getUnitaDocumentariaId());
			tagAttoCompletoXmlBean.setIdProcess(item.getIdProcedimento());
			listaAtti.add(tagAttoCompletoXmlBean);
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		attiCompleti = lXmlUtilitySerializer.bindXmlList(listaAtti);
		
		return attiCompleti;
	}
	
	private List<TagAttoXmlBean> getTag(List<ItemTagAttiCompletiBean> pInBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, JAXBException{
		
		List<TagAttoXmlBean> listaTag = new ArrayList<TagAttoXmlBean>();
		for(ItemTagAttiCompletiBean item : pInBean) {
			StringSplitterServer st = new StringSplitterServer(item.getItemLavTag(), ",");
			if(st.countTokens() > 1) {
				while(st.hasMoreTokens()) {
					TagAttoXmlBean lTagAttoXmlBean = new TagAttoXmlBean();
					lTagAttoXmlBean.setCodiceTag(st.nextToken());
					listaTag.add(lTagAttoXmlBean);
				}
			} else {
				TagAttoXmlBean lTagAttoXmlBean = new TagAttoXmlBean();
				lTagAttoXmlBean.setCodiceTag(item.getItemLavTag());
				listaTag.add(lTagAttoXmlBean);
			}
				
		}
		
		return listaTag;
	}
	
	@Override
	public AzioneSuListaAttiCompletiBean get(AzioneSuListaAttiCompletiBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public AzioneSuListaAttiCompletiBean remove(AzioneSuListaAttiCompletiBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public AzioneSuListaAttiCompletiBean update(AzioneSuListaAttiCompletiBean bean,
			AzioneSuListaAttiCompletiBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<AzioneSuListaAttiCompletiBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AzioneSuListaAttiCompletiBean bean)
	throws Exception {
		
		return null;
	}

}
