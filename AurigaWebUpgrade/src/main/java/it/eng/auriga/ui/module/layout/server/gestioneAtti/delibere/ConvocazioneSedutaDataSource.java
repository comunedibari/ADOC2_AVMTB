package it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollGetconvocazionesedutaBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollSaveconvocazionesedutaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.ArgomentiOdgXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.ConvocazioneInfoXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.ConvocazioneSedutaBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.OdgInfoXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.PresenzeOdgXmlBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.shared.bean.FileDocumentoBean;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkSeduteOrgCollGetconvocazioneseduta;
import it.eng.client.DmpkSeduteOrgCollSaveconvocazioneseduta;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.SalvataggioFile;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileSavedIn;
import it.eng.document.function.bean.FileSavedOut;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="ConvocazioneSedutaDataSource")
public class ConvocazioneSedutaDataSource extends AbstractServiceDataSource<ConvocazioneSedutaBean,ConvocazioneSedutaBean> {

	private static Logger mLogger = Logger.getLogger(ConvocazioneSedutaDataSource.class);

	@Override
	public ConvocazioneSedutaBean call(ConvocazioneSedutaBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkSeduteOrgCollGetconvocazionesedutaBean input = new DmpkSeduteOrgCollGetconvocazionesedutaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setOrganocollegialein(pInBean.getOrganoCollegiale());
		input.setIdsedutaio(pInBean.getIdSeduta() != null ? pInBean.getIdSeduta() : null);
		/**
		 * (obblig. OrganoCollegialeIn = commissione) Lista con gli ID delle commissioni per cui si convoca seduta
		 */
		input.setListacommissioniin(pInBean.getListaCommissioni());
		
		DmpkSeduteOrgCollGetconvocazioneseduta dmpkSeduteOrgCollGetconvocazioneseduta = new DmpkSeduteOrgCollGetconvocazioneseduta();
		StoreResultBean<DmpkSeduteOrgCollGetconvocazionesedutaBean> output = dmpkSeduteOrgCollGetconvocazioneseduta.execute(getLocale(),loginBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);						
		}
		
		ConvocazioneSedutaBean lConvocazioneSedutaBean = new ConvocazioneSedutaBean();
		/**
		 * (obblig. OrganoCollegialeIn = commessione) Lista con gli ID delle commissioni per cui si convoca seduta
		 */
		lConvocazioneSedutaBean.setIdSeduta(output.getResultBean().getIdsedutaio());
		/**
		 * N.ro di seduta progressivo per organo e per anno (è vuoto se StatoSedutaOut = nuova)
		 */
		lConvocazioneSedutaBean.setNumero(output.getResultBean().getNrosedutaout() != null ? String.valueOf(output.getResultBean().getNrosedutaout()) : null);
		lConvocazioneSedutaBean.setDtPrimaConvocazione(output.getResultBean().getDataora1aconvocazioneout() != null ? 
				new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getDataora1aconvocazioneout()) : null);
		lConvocazioneSedutaBean.setLuogoPrimaConvocazione(output.getResultBean().getLuogo1aconvocazioneout());	
		lConvocazioneSedutaBean.setDtSecondaConvocazione(output.getResultBean().getDataora2aconvocazioneout() != null ? 
				new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getDataora2aconvocazioneout()) : null);
		lConvocazioneSedutaBean.setLuogoSecondaConvocazione(output.getResultBean().getLuogo2aconvocazioneout());	
		
		lConvocazioneSedutaBean.setStato(output.getResultBean().getStatosedutaout());
		String statoSedutaOut = output.getResultBean().getStatosedutaout();
		if(StringUtils.isNotBlank(statoSedutaOut)) {
			statoSedutaOut = statoSedutaOut.replaceAll("_", " ");
			lConvocazioneSedutaBean.setDesStato(statoSedutaOut);
		}
		lConvocazioneSedutaBean.setTipoSessione(output.getResultBean().getTiposessioneout());
		
		if(StringUtils.isNotBlank(output.getResultBean().getOdginfoout())) {
			OdgInfoXmlBean lOdgInfoBean = new XmlUtilityDeserializer().unbindXml(output.getResultBean().getOdginfoout(), OdgInfoXmlBean.class);
			lConvocazioneSedutaBean.setOdgInfo(lOdgInfoBean);
		}
		
		if(StringUtils.isNotBlank(output.getResultBean().getConvocazioneinfoout())) {
			ConvocazioneInfoXmlBean conconvocazioneInfo = new XmlUtilityDeserializer().unbindXml(output.getResultBean().getConvocazioneinfoout(), ConvocazioneInfoXmlBean.class);
			lConvocazioneSedutaBean.setConvocazioneInfo(conconvocazioneInfo);
		}
		
		if(StringUtils.isNotBlank(output.getResultBean().getArgomentiodgout())) {
			List<ArgomentiOdgXmlBean> listaArgomentiOdg = XmlListaUtility.recuperaLista(output.getResultBean().getArgomentiodgout(), ArgomentiOdgXmlBean.class);
			lConvocazioneSedutaBean.setListaArgomentiOdg(listaArgomentiOdg);
		}
		
		if(StringUtils.isNotBlank(output.getResultBean().getPresenzeout())) {
			List<PresenzeOdgXmlBean> listaPresenzeOdg = XmlListaUtility.recuperaLista(output.getResultBean().getPresenzeout(), PresenzeOdgXmlBean.class);
			lConvocazioneSedutaBean.setListaPresenzeOdg(listaPresenzeOdg);
			
			if(lConvocazioneSedutaBean.getListaPresenzeOdg() != null && !lConvocazioneSedutaBean.getListaPresenzeOdg().isEmpty()) {
				String destinatari = "";
				for(PresenzeOdgXmlBean item : lConvocazioneSedutaBean.getListaPresenzeOdg()) {
					if(item != null && item.getDestinatari() != null && !"".equals(item.getDestinatari())) {
						destinatari += item.getDestinatari() + "; ";
					}
				}
				lConvocazioneSedutaBean.setDestinatari(destinatari);
			}
		}
		
		return lConvocazioneSedutaBean;
	}
	
	public ConvocazioneSedutaBean salvaConvocazioneSeduta(ConvocazioneSedutaBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		if(pInBean.getOdgInfo() != null && pInBean.getOdgInfo().getAzioneDaFare() != null) {
			mLogger.error("CONVOCAZIONE SEDUTA - Azione su OdG: " + pInBean.getOdgInfo().getAzioneDaFare());
		}
		
		if(pInBean.getConvocazioneInfo() != null && pInBean.getConvocazioneInfo().getAzioneDaFare() != null) {
			mLogger.error("CONVOCAZIONE SEDUTA - Azione su Convocazione: " + pInBean.getConvocazioneInfo().getAzioneDaFare());
		}
		
		// Devo persistere in archivio il documento generato da modello
		if (pInBean.getOdgInfo() != null && StringUtils.isNotBlank(pInBean.getOdgInfo().getUri())){
			OdgInfoXmlBean lOdgInfoXmlBean =  pInBean.getOdgInfo();
			// Recupero il file da persistere
			File lFile = StorageImplementation.getStorage().extractFile(lOdgInfoXmlBean.getUri());
			
			FileSavedIn lFileSavedIn = new FileSavedIn();
			lFileSavedIn.setSaved(lFile);
			Locale local = new Locale(loginBean.getLinguaApplicazione());
			SalvataggioFile lSalvataggioFile = new SalvataggioFile();
			FileSavedOut out = lSalvataggioFile.savefile(local, loginBean, lFileSavedIn);			
			
			if(out.getErrorInSaved() != null) {
				mLogger.error("CONVOCAZIONE SEDUTA - Errore durante il salvataggio del file OdG in repository: " + out.getErrorInSaved());
				throw new StoreException("Si è verificato un errore durante il salvataggio del file OdG in repository");
			}

			lOdgInfoXmlBean.setUri(out.getUri());
			pInBean.setOdgInfo(lOdgInfoXmlBean);
		}
		
		// Devo persistere in archivio il documento generato da modello
		if (pInBean.getConvocazioneInfo() != null && StringUtils.isNotBlank(pInBean.getConvocazioneInfo().getUri())){
			ConvocazioneInfoXmlBean lConvocazioneInfoXmlBean =  pInBean.getConvocazioneInfo();
			// Recupero il file da persistere
			File lFile = StorageImplementation.getStorage().extractFile(lConvocazioneInfoXmlBean.getUri());
			
			FileSavedIn lFileSavedIn = new FileSavedIn();
			lFileSavedIn.setSaved(lFile);
			Locale local = new Locale(loginBean.getLinguaApplicazione());
			SalvataggioFile lSalvataggioFile = new SalvataggioFile();
			FileSavedOut out = lSalvataggioFile.savefile(local, loginBean, lFileSavedIn);
			
			if(out.getErrorInSaved() != null) {
				mLogger.error("CONVOCAZIONE SEDUTA - Errore durante il salvataggio del file Convocazione in repository: " + out.getErrorInSaved());
				throw new StoreException("Si è verificato un errore durante il salvataggio del file Convocazione in repository");
			}
			
			lConvocazioneInfoXmlBean.setUri(out.getUri());
			pInBean.setConvocazioneInfo(lConvocazioneInfoXmlBean);
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		DmpkSeduteOrgCollSaveconvocazionesedutaBean input = new DmpkSeduteOrgCollSaveconvocazionesedutaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setOrganocollegialein(pInBean.getOrganoCollegiale());
		//TODO da splittare
		input.setListacommissioniin(pInBean.getListaCommissioni());
		/**
		 * In input può essere vuoto se la seduta è nuova, in output salvo in caso di errore è sempre valorizzato
		 */
		input.setIdsedutaio(pInBean.getIdSeduta());
		input.setDataora1aconvocazionein(pInBean.getDtPrimaConvocazione() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(pInBean.getDtPrimaConvocazione()) : null);
		input.setLuogo1aconvocazionein(pInBean.getLuogoPrimaConvocazione());
		input.setDataora2aconvocazionein(pInBean.getDtSecondaConvocazione() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(pInBean.getDtSecondaConvocazione()) : null);
		input.setLuogo2aconvocazionein(pInBean.getLuogoSecondaConvocazione());
		input.setTiposessionein(pInBean.getTipoSessione());
		input.setStatosedutain(pInBean.getStato());
		
		List<ArgomentiOdgXmlBean> listaArgomentiOdgCompleta = new ArrayList<ArgomentiOdgXmlBean>();
		if (pInBean.getListaArgomentiOdgEliminati() != null ) {
			for(ArgomentiOdgXmlBean itemEliminato : pInBean.getListaArgomentiOdgEliminati()) {
				itemEliminato.setFlgElimina("1");
				listaArgomentiOdgCompleta.add(itemEliminato);
			}
		}
		if (pInBean.getListaArgomentiOdg() != null ) {
			for(ArgomentiOdgXmlBean itemCorrente : pInBean.getListaArgomentiOdg()) {
				listaArgomentiOdgCompleta.add(itemCorrente);
			}
		}
		input.setArgomentiodgin(lXmlUtilitySerializer.bindXmlList(listaArgomentiOdgCompleta));
		
		if (pInBean.getListaPresenzeOdg() != null) {
			input.setPresenzein(lXmlUtilitySerializer.bindXmlList(pInBean.getListaPresenzeOdg()));
		}
		if (pInBean.getOdgInfo() != null) {
			input.setOdginfoio(lXmlUtilitySerializer.bindXml(pInBean.getOdgInfo()));
		}
		if(pInBean.getConvocazioneInfo() != null) {
			input.setConvocazioneinfoin(lXmlUtilitySerializer.bindXml(pInBean.getConvocazioneInfo()));
		}
		
		DmpkSeduteOrgCollSaveconvocazioneseduta dmpkSeduteOrgCollSaveconvocazioneseduta = new DmpkSeduteOrgCollSaveconvocazioneseduta();
		StoreResultBean<DmpkSeduteOrgCollSaveconvocazionesedutaBean> output = dmpkSeduteOrgCollSaveconvocazioneseduta.execute(getLocale(),loginBean, input);
		
		if(output.isInError()) {
			mLogger.error("CONVOCAZIONE SEDUTA - Errore store di salvataggio convocazione seduta: " + output.getDefaultMessage());
			throw new StoreException(output);						
		}
		
		pInBean.setIdSeduta(output.getResultBean().getIdsedutaio());

		/**
		 * N.ro di seduta progressivo per organo e per anno (salvo in caso di errore è sempre valorizzato)
		 */
		pInBean.setNumero(Integer.toString(output.getResultBean().getNrosedutaout()));
		if(StringUtils.isNotBlank(output.getResultBean().getOdginfoio())) {
			pInBean.setOdgInfo(new XmlUtilityDeserializer().unbindXml(output.getResultBean().getOdginfoio(), OdgInfoXmlBean.class));
		}

		return pInBean;		
	}
	
	public ConvocazioneSedutaBean getInfoFile(ConvocazioneSedutaBean bean) throws Exception {
		
		ConvocazioneSedutaBean output = new ConvocazioneSedutaBean();		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(StringUtils.isNotBlank(getExtraparams().get("idUd")) ? new BigDecimal(getExtraparams().get("idUd")) : null);
		lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(null);
		lRecuperaDocumentoInBean.setTsAnnDati(null);
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
		FileDocumentoBean lFileDocumentoBean = new FileDocumentoBean();
		lFileDocumentoBean.setIdDoc(String.valueOf(documento.getIdDocPrimario()));
		lFileDocumentoBean.setDisplayFilename(documento.getNomeFilePrimario());
		lFileDocumentoBean.setUri(documento.getUriFilePrimario());
		lFileDocumentoBean.setInfo(documento.getInfoFile());
		
		output.setFileDocumento(lFileDocumentoBean);
		
		return output;
	}
	
	public FileDaFirmareBean generaModelloConvocazione(ConvocazioneSedutaBean lSalvaSedutaConvocazioneBean) throws Exception {
		// Ricavo i dati da iniettare nel modello
		String nomeModello = lSalvaSedutaConvocazioneBean.getConvocazioneInfo() != null ? lSalvaSedutaConvocazioneBean.getConvocazioneInfo().getNomeModello() : ""; 
		String templateValues = getDatiXGenDaModelloConvocazione(lSalvaSedutaConvocazioneBean, nomeModello);
		// Genero da modello
		Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean templateWithValues = ModelliUtil.fillFreeMarkerTemplateWithModel(null, lSalvaSedutaConvocazioneBean.getConvocazioneInfo().getIdModello(), mappaValoriCopertinaFinale, true, true, getSession());

		return templateWithValues;
	}
	
	private String getDatiXGenDaModelloConvocazione(ConvocazioneSedutaBean bean, String nomeModello) throws Exception {
		
		// Creo la sezione cache
		SezioneCache variabiliSezioneCache = creaSezioneCachePerModelloConvocazione(bean);
		
		// chiamo la get dati per modello
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(bean.getIdSeduta());
		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("S");
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
		// Creo gli attributi addizionali
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(variabiliSezioneCache);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
				lDmpkModelliDocGetdatixgendamodelloInput);
		if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
		}
		
		return lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();				
	}
	
	private SezioneCache creaSezioneCachePerModelloConvocazione(ConvocazioneSedutaBean lConvocazioneSedutaBean) throws Exception {
		// Creo la sezione chache coi dati a maschera
		SezioneCache variabiliPerModello = new SezioneCache();
		// Metto tutte le variabile che mi servono
		// qua gestisco i ceck
		if (lConvocazioneSedutaBean.getConvocazioneInfo() != null && 
				StringUtils.isNotBlank(lConvocazioneSedutaBean.getConvocazioneInfo().getTestoHtml())) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "TestoHtml", lConvocazioneSedutaBean.getConvocazioneInfo().getTestoHtml());
		}

		return variabiliPerModello;	
		
	}
	
	public FileDaFirmareBean generaModelloOdg(ConvocazioneSedutaBean lSalvaSedutaConvocazioneBean) throws Exception {
		// Ricavo i dati da iniettare nel modello
		String nomeModello = lSalvaSedutaConvocazioneBean.getOdgInfo() != null ? lSalvaSedutaConvocazioneBean.getOdgInfo().getNomeModello() : ""; 
		String templateValues = getDatiXGenDaModelloOdg(lSalvaSedutaConvocazioneBean, nomeModello);
		// Genero da modello
		Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean templateWithValues = ModelliUtil.fillFreeMarkerTemplateWithModel(null, lSalvaSedutaConvocazioneBean.getOdgInfo().getIdModello(), mappaValoriCopertinaFinale, true, true, getSession());
		// FileDaFirmareBean templateWithValues = ModelliUtil.fillTemplate(null, lSalvaSedutaConvocazioneBean.getOdgInfo().getIdModello(), templateValues, true, getSession());
		return templateWithValues;
	}
	
	private String getDatiXGenDaModelloOdg(ConvocazioneSedutaBean bean, String nomeModello) throws Exception {
				
		String tipoOdGConsolidato = getExtraparams().get("tipoOdGConsolidato");
		
		// Creo la sezione cache
		SezioneCache variabiliSezioneCache = creaSezioneCachePerModelloOdg(bean, tipoOdGConsolidato);
		
		// chiamo la get dati per modello
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(bean.getIdSeduta());
		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("S");
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
		// Creo gli attributi addizionali
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(variabiliSezioneCache);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
				lDmpkModelliDocGetdatixgendamodelloInput);
		if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
		}
		
		return lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();				
	}
	
	private SezioneCache creaSezioneCachePerModelloOdg(ConvocazioneSedutaBean lConvocazioneSedutaBean, String tipoOdGConsolidato) throws Exception {
		// Creo la sezione chache coi dati a maschera
		SezioneCache variabiliPerModello = new SezioneCache();
		// Metto tutte le variabile che mi servono
		// qua gestisco i ceck
		SimpleDateFormat dateFormatter = new SimpleDateFormat(FMT_STD_DATA);
		
		if (StringUtils.isNotBlank(tipoOdGConsolidato)) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "tipoOdGConsolidato", tipoOdGConsolidato);
		}
		if (StringUtils.isNotBlank(lConvocazioneSedutaBean.getIdSeduta())) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "idSeduta", lConvocazioneSedutaBean.getIdSeduta());
		}
		if (StringUtils.isNotBlank(lConvocazioneSedutaBean.getOrganoCollegiale())) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "organoColleggiale", lConvocazioneSedutaBean.getOrganoCollegiale());
		}
		if (lConvocazioneSedutaBean.getDtPrimaConvocazione() != null) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "dtPrimaConvocazione", dateFormatter.format(lConvocazioneSedutaBean.getDtPrimaConvocazione()));
		}
		if (lConvocazioneSedutaBean.getDtSecondaConvocazione() != null) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "dtSecondaConvocazione", dateFormatter.format(lConvocazioneSedutaBean.getDtSecondaConvocazione()));
		}
		if (StringUtils.isNotBlank(lConvocazioneSedutaBean.getTipoSessione())) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "tipoSessione", lConvocazioneSedutaBean.getTipoSessione());
		}
		if (lConvocazioneSedutaBean.getListaArgomentiOdg() != null) {
			putVariabileListaSezioneCache(variabiliPerModello, "listaArgomentiOdg", new XmlUtilitySerializer().createVariabileLista(lConvocazioneSedutaBean.getListaArgomentiOdg()));
		}
		if (lConvocazioneSedutaBean.getListaPresenzeOdg() != null) {
			Lista listaPresenzeOdg = new XmlUtilitySerializer().createVariabileLista(lConvocazioneSedutaBean.getListaPresenzeOdg());
			putVariabileListaSezioneCache(variabiliPerModello, "listaPresenzeOdg", listaPresenzeOdg);			
		}
		
		return variabiliPerModello;	
		
	}
	
	private void putVariabileSempliceSezioneCache(SezioneCache sezioneCache, String nomeVariabile, String valoreSemplice) {
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setValoreSemplice(valoreSemplice);			
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice(nomeVariabile, valoreSemplice));
		}
	}
	
	private void putVariabileListaSezioneCache(SezioneCache sezioneCache, String nomeVariabile, Lista lista) {
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setLista(lista);	
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileLista(nomeVariabile, lista));
		}
	}
	
	private int getPosVariabileSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {	
		if(sezioneCache != null && sezioneCache.getVariabile() != null) {
			for(int i = 0; i < sezioneCache.getVariabile().size(); i++) {
				Variabile var = sezioneCache.getVariabile().get(i);
				if(var.getNome().equals(nomeVariabile)) {
					return i;
				}
			}
		}
		return -1;
	}
		
	public FileDocumentoBean getFileFromIdUd(ArgomentiOdgXmlBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(new BigDecimal(bean.getIdUd()));
		lRecuperaDocumentoInBean.setFlgSoloAbilAzioni("1");
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
		FileDocumentoBean lFileDocumentoBean = new FileDocumentoBean();
		lFileDocumentoBean.setIdDoc(documento.getIdDocPrimario() != null ? String.valueOf(documento.getIdDocPrimario()) : null);
		lFileDocumentoBean.setDisplayFilename(documento.getNomeFilePrimario());
		lFileDocumentoBean.setUri(documento.getUriFilePrimario());
		lFileDocumentoBean.setInfo(documento.getInfoFile());
		
		return lFileDocumentoBean;
	}
}