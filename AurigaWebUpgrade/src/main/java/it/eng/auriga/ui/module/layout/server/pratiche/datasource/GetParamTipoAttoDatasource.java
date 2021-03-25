package it.eng.auriga.ui.module.layout.server.pratiche.datasource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_doc_types.bean.DmpkDocTypesGetparametritipoattoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.UfficioProponenteAttoDatasource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UoProtocollanteBean;
import it.eng.auriga.ui.module.layout.server.task.bean.AttributiCustomCablatiAttoXmlBean;
import it.eng.auriga.ui.module.layout.server.task.bean.ParametriTipoAttoBean;
import it.eng.auriga.ui.module.layout.server.task.bean.XmlDatiEventoOutBean;
import it.eng.client.DmpkDocTypesGetparametritipoatto;
import it.eng.document.function.bean.Flag;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id = "GetParamTipoAttoDatasource")
public class GetParamTipoAttoDatasource extends AbstractServiceDataSource<AttProcBean, AttProcBean> {

	private static final Logger log = Logger.getLogger(GetParamTipoAttoDatasource.class);

	@Override
	public AttProcBean call(AttProcBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkDocTypesGetparametritipoattoBean input = new DmpkDocTypesGetparametritipoattoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIddoctypein(new BigDecimal(bean.getIdTipoDoc()));
		DmpkDocTypesGetparametritipoatto dmpkDocTypesGetparametritipoatto = new DmpkDocTypesGetparametritipoatto();
		StoreResultBean<DmpkDocTypesGetparametritipoattoBean> output = dmpkDocTypesGetparametritipoatto.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		if (StringUtils.isNotBlank(output.getResultBean().getParametritipoattoout())) {
			XmlDatiEventoOutBean scXmlDatiEvento = new XmlDatiEventoOutBean();
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			log.debug("taskInfoXml: " + output.getResultBean().getParametritipoattoout());
			scXmlDatiEvento = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getParametritipoattoout(), XmlDatiEventoOutBean.class);
			if (scXmlDatiEvento != null) {
				bean.setAttributiAddDocTabs(scXmlDatiEvento.getAttributiAddDocTabs());
				bean.setFlgPubblicazioneAllegatiUguale(scXmlDatiEvento.getFlgPubblicazioneAllegatiUguale() != null && scXmlDatiEvento.getFlgPubblicazioneAllegatiUguale().equalsIgnoreCase("true"));
				bean.setFlgAvvioLiquidazioneContabilia(scXmlDatiEvento.getFlgAvvioLiquidazioneContabilia() == Flag.SETTED);
				bean.setFlgNumPropostaDiffXStruttura(scXmlDatiEvento.getFlgNumPropostaDiffXStruttura() != null && scXmlDatiEvento.getFlgNumPropostaDiffXStruttura().equalsIgnoreCase("true"));
				ParametriTipoAttoBean lParametriTipoAttoBean = new ParametriTipoAttoBean();
				lParametriTipoAttoBean.setAttributiCustomCablati(scXmlDatiEvento.getParametriTipoAttoAttributiCustomCablati()); 
//				lParametriTipoAttoBean.setAttributiCustomCablati(new java.util.ArrayList<it.eng.auriga.ui.module.layout.server.task.bean.AttributiCustomCablatiAttoXmlBean>());
				// va' fatto anche qui?
				String altriParamLoadComboUfficioProponente = null;
				if(lParametriTipoAttoBean.getAttributiCustomCablati() != null) {
					for(AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBean : lParametriTipoAttoBean.getAttributiCustomCablati()) {
						if(lAttributiCustomCablatiAttoXmlBean.getAttrName() != null && lAttributiCustomCablatiAttoXmlBean.getAttrName().equalsIgnoreCase("ID_UO_PROPONENTE")) {
							altriParamLoadComboUfficioProponente = lAttributiCustomCablatiAttoXmlBean.getAltriParametriLoadCombo();
							break;
						}
					}
				}					
				UfficioProponenteAttoDatasource lUfficioProponenteAttoDatasource = new UfficioProponenteAttoDatasource();		
				lUfficioProponenteAttoDatasource.setSession(getSession());
				Map<String, String> extraparams = new LinkedHashMap<String, String>();
				extraparams.put("altriParamLoadCombo", altriParamLoadComboUfficioProponente);			
				lUfficioProponenteAttoDatasource.setExtraparams(extraparams);
				PaginatorBean<UoProtocollanteBean> valoriUfficioProponente = lUfficioProponenteAttoDatasource.fetch(null, null, null, null);
				lParametriTipoAttoBean.setValoriUfficioProponente(valoriUfficioProponente != null ? valoriUfficioProponente.getData() : new ArrayList<UoProtocollanteBean>());
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegrante() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegrante() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultXTipoAtto(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegrante() == Flag.SETTED);
				} else {
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultXTipoAtto(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FLG_ALLEG_ATTO_PARTE_INT_DEFAULT"));
				}
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultOrdPermanente(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente() == Flag.SETTED);
				}
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultOrdTemporanea(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea() == Flag.SETTED);
				}				
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegPubblSeparata() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegPubblSeparata() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoPubblSepDefaultXTipoAtto(scXmlDatiEvento.getParametriTipoAttoDefaultAllegPubblSeparata() == Flag.SETTED);
				} else {
					lParametriTipoAttoBean.setFlgAllegAttoPubblSepDefaultXTipoAtto(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FLG_ALLEG_ATTO_PUBBL_SEPARATA_DEFAULT"));
				}
				bean.setParametriTipoAtto(lParametriTipoAttoBean);
			}
		}
//		setTestData(bean);		
		return bean;
	}

	public void setTestData(AttProcBean bean) {		
				
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA.setAttrName("DESCRIZIONE_ORD_MOBILITA");
//		lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA.setAttrLabel("Descrizione");
//		lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA.setAltezzaInRighe("10");
//		lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_ESECUTIVITA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_ESECUTIVITA.setAttrName("DATI_ESECUTIVITA");
//		lAttributiCustomCablatiAttoXmlBeanDATI_ESECUTIVITA.setAttrLabel("Esecutività");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_ESECUTIVITA);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE.setAttrName("FLG_IMMEDIATAMENTE_ESEGUIBILE");
//		lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE.setAttrLabel("immediatamente eseguibile");
//		lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE.setAttrName("MOTIVI_IE");
//		lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE.setAttrLabel("Motivo/i");
//		lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE.setAltezzaInRighe("10");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanLUOGO_ORD_MOBILITA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanLUOGO_ORD_MOBILITA.setAttrName("LUOGO_ORD_MOBILITA");
//		lAttributiCustomCablatiAttoXmlBeanLUOGO_ORD_MOBILITA.setAttrLabel("Ubicazione/i");
//		lAttributiCustomCablatiAttoXmlBeanLUOGO_ORD_MOBILITA.setAltezzaInRighe("10");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanLUOGO_ORD_MOBILITA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI.setAttrName("ATTIVA_SEZIONE_DESTINATARI");
//		lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI.setAttrLabel("attiva sezione destinatari");
//		lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO.setAttrName("DESTINATARI_ATTO");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO.setAttrLabel("Destinatari");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_PC_ATTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_PC_ATTO.setAttrName("DESTINATARI_PC_ATTO");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_PC_ATTO.setAttrLabel("Destinatari P.C.");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_PC_ATTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_PC_ATTO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO.setAttrName("DESTINATARI_ATTO_PREFISSO");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO.setAttrLabel("Prefisso");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME.setAttrName("DESTINATARI_ATTO_NOME");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME.setAttrLabel("Nome");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO.setAttrName("DESTINATARI_ATTO_INDIRIZZO");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO.setAttrLabel("Sede/indirizzo");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_CA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_CA.setAttrName("DESTINATARI_ATTO_CA");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_CA.setAttrLabel("C.A.");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_CA);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_TESTO_2 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_TESTO_2.setAttrName("DATI_TESTO_2");
//		lAttributiCustomCablatiAttoXmlBeanDATI_TESTO_2.setAttrLabel("Testo atto 2");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_TESTO_2);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2.setAttrName("PREMESSA_ATTO_2");
//		lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2.setAttrLabel("Premessa 2");
//		lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2.setAttrName("DISPOSITIVO_ATTO_2");
//		lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2.setAttrLabel("Dispositivo 2");
//		lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanVISTI_DIR_SUPERIORI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanVISTI_DIR_SUPERIORI.setAttrName("VISTI_DIR_SUPERIORI");
//		lAttributiCustomCablatiAttoXmlBeanVISTI_DIR_SUPERIORI.setAttrLabel("Visti Dir. Mattia Zanin");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanVISTI_DIR_SUPERIORI);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1.setAttrName("TASK_RESULT_2_VISTO_DIR_SUP_1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1.setAttrLabel("visto dirigente di area");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2.setAttrName("TASK_RESULT_2_VISTO_DIR_SUP_2");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2.setAttrLabel("visto direttore di divisione");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU.setAttrName("TASK_RESULT_2_FONDI_PRU");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU.setAttrLabel("fondi PRU");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013.setAttrName("TASK_RESULT_2_VISTO_PAR_117_2013");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013.setAttrLabel("visto parere favorevole ai sensi ai sensi della DD 117/2013 ssmm");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI.setAttrName("TASK_RESULT_2_NOTIFICA_DA_MESSI");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI.setAttrLabel("notifica tramite messi");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanCDC_ATTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanCDC_ATTO.setAttrName("CDC_ATTO");
//		lAttributiCustomCablatiAttoXmlBeanCDC_ATTO.setAttrLabel("Centro di Costo");
//		lAttributiCustomCablatiAttoXmlBeanCDC_ATTO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanCDC_ATTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanCDC_ATTO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO.setAttrName("TASK_RESULT_2_DECRETO_REGGIO");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO.setAttrLabel("decreto REGGIO");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA.setAttrName("TASK_RESULT_2_AVVOCATURA");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA.setAttrLabel("AVVOCATURA");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_NOTIZIARIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_NOTIZIARIO.setAttrName("DATI_PUBBL_NOTIZIARIO");
//		lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_NOTIZIARIO.setAttrLabel("Pubblicazione sul Notiziario");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_NOTIZIARIO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO.setAttrName("TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO.setAttrLabel("Da pubblicare");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO.setValoreFisso("SI");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA.setAttrName("ATTO_RIF_A_SISTEMA");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA.setAttrLabel("a sistema");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO.setAttrName("ATTO_RIFERIMENTO");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO.setAttrLabel("Atti di riferimento");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO.setMaxNumValori("");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setAttrName("IND_EMAIL_DEST_NOTIFICA_ATTO");
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setAttrLabel("Dest. notifiche");
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_BUR = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_BUR.setAttrName("DATI_PUBBL_BUR");
//		lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_BUR.setAttrLabel("Pubblicazione al B.U.");
//		lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_BUR.setMaxNumValori("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_BUR);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setAttrName("FLG_PUBBL_BUR");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setAttrLabel("Pubblicazione al B.U.");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setValoriPossibili("SI|*|NO");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setValoreFisso("NO");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setAttrName("ANNO_TERMINE_PUBBL_BUR");
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setAttrLabel("Anno di termine pubblicazione");
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setValoreFisso("2024");
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setAttrName("TIPO_DECORRENZA_PUBBL_BUR");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setAttrLabel("Decorrenza pubblicazione");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setValoreFisso("posticipata");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL.setAttrName("PUBBL_BUR_DAL");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL.setAttrLabel("a partire dal");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR.setAttrName("FLG_URGENTE_PUBBL_BUR");
//		lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR.setAttrLabel("pubblicazione urgente");
//		lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO.setAttrName("PUBBL_BUR_ENTRO");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO.setAttrLabel("entro il");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO);
	}

}
