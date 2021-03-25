package it.eng.auriga.ui.module.layout.server.organigramma.datasource;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGettiponuovareluouserBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityIureluouserBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettreluouserBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityMovepostazioneutenteBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecuritySubstuserinreluoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;

import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.PostazioneBean;
import it.eng.client.DmpkDefSecurityGettiponuovareluouser;
import it.eng.client.DmpkDefSecurityIureluouser;
import it.eng.client.DmpkDefSecurityLoaddettreluouser;
import it.eng.client.DmpkDefSecurityMovepostazioneutente;
import it.eng.client.DmpkDefSecuritySubstuserinreluo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "PostazioneDatasource")
public class PostazioneDatasource extends AbstractDataSource<PostazioneBean, PostazioneBean> {

	private static Logger mLogger = Logger.getLogger(PostazioneDatasource.class);

	@Override
	public PostazioneBean get(PostazioneBean bean) throws Exception {
		mLogger.debug("Start method get");
		DmpkDefSecurityLoaddettreluouserBean lDmpkDefSecurityLoaddettreluouserBean = new DmpkDefSecurityLoaddettreluouserBean();

		/**
		 * Controllo di recupero dell' idUO, viene verificato il corretto recupero del dato da lista oppure da menù ad albero, poichè la medesima property viene
		 * settata in maniera differente a seconda dalla posizione in cui viene richiesta.
		 */
		if (StringUtils.isNotBlank(bean.getIdUo())) {
			lDmpkDefSecurityLoaddettreluouserBean.setIduoio(bean.getIdUo() != null ? new BigDecimal(bean.getIdUo()) : null);
			bean.setIdUoSvUt(bean.getIdUo());
		} else {
			lDmpkDefSecurityLoaddettreluouserBean.setIduoio(bean.getIdUoSvUt() != null ? new BigDecimal(bean.getIdUoSvUt()) : null);
			bean.setIdUo(bean.getIdUoSvUt());
		}

		lDmpkDefSecurityLoaddettreluouserBean.setIduserio(bean.getIdUser() != null ? new BigDecimal(bean.getIdUser()) : null);
		lDmpkDefSecurityLoaddettreluouserBean.setFlgtiporelio(bean.getTipoRelUtenteUo());
		DmpkDefSecurityLoaddettreluouser store = new DmpkDefSecurityLoaddettreluouser();
		StoreResultBean<DmpkDefSecurityLoaddettreluouserBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkDefSecurityLoaddettreluouserBean);
		if (result.isInError()) {
			throw new StoreException(result);
		}
		bean.setFlgInclScrivVirt(result.getResultBean().getFlginclscrivvirtout() != null && result.getResultBean().getFlginclscrivvirtout() == 1);
		bean.setFlgInclSottoUo(result.getResultBean().getFlginclsottouoout() != null && result.getResultBean().getFlginclsottouoout() == 1);
		if (StringUtils.isNotBlank(result.getResultBean().getDtiniziovldio()))
			bean.setDataDal(new SimpleDateFormat(FMT_STD_DATA).parse(result.getResultBean().getDtiniziovldio()));
		if (StringUtils.isNotBlank(result.getResultBean().getDtfinevldout()))
			bean.setDataAl(new SimpleDateFormat(FMT_STD_DATA).parse(result.getResultBean().getDtfinevldout()));
		bean.setCiRelUserUo(result.getResultBean().getIduoio() + "." + result.getResultBean().getIduserio() + "." + result.getResultBean().getFlgtiporelio() + "." + result.getResultBean().getDtiniziovldio() + "." + (result.getResultBean().getIdruoloammio() != null ? String.valueOf(result.getResultBean().getIdruoloammio()) : null));
		bean.setIdScrivania(result.getResultBean().getIdscrivaniaout() != null ? String.valueOf(result.getResultBean().getIdscrivaniaout()) : null);
		// TODO: Sarebbe meglio usare un campo per la descrizione e verificare a cosa serve il campo intestazione
		if (StringUtils.isNotBlank(result.getResultBean().getIntestazionescrivaniaout())){
			bean.setIntestazione(result.getResultBean().getIntestazionescrivaniaout());
		}else{
			bean.setIntestazione(result.getResultBean().getDesuserio());
		}
		bean.setRuolo(result.getResultBean().getDesruoloammout());
		bean.setNomePostazione(result.getResultBean().getIntestazionescrivaniaout());
		bean.setUsername(result.getResultBean().getUsernameio());
		bean.setDescrizioneUser(result.getResultBean().getDesuserio());
		
		if(!bean.isSkipFlgUoPuntoProtocollo()){
			bean.setFlgUoPuntoProtocollo(result.getResultBean().getFlguoppout() != null && result.getResultBean().getFlguoppout() == 1);	
		}
		
		//bean.setListaUOPuntoProtocolloEscluse(result.getResultBean().getXmluoppout());
		
		bean.setNriLivelliUo(result.getResultBean().getNrilivelliuoio());
		bean.setDenominazioneUo(result.getResultBean().getDenominazioneuoio());
		
		// Setto il flag che indica se la UO ha in carico documenti/fascicoli 
		bean.setFlgPresentiDocFasc(result.getResultBean().getFlgpresentidocfascout());
						
		// Setto la UO che ha in carico i documenti/fascicoli
		bean.setFlgTipoDestDoc(result.getResultBean().getFlgtipodestdocout());
		bean.setIdUODestDocfasc(result.getResultBean().getIduosvdestdocfascout());
		bean.setLivelliUODestDocFasc(result.getResultBean().getLivelliuodestdocfascout());
		bean.setDesUODestDocFasc(result.getResultBean().getDesuodestdocfascout());
						
		//bean.setIdUOSVDestDocfasc(new BigDecimal(100020));
		//bean.setLivelliUODestDocFasc("A10");
		//bean.setDesUODestDocFasc("GABINETTO DEL SINDACO");
		
		//String sDate1="31/12/2000";
		//Date dataSoppressa=new SimpleDateFormat(FMT_STD_DATA).parse(sDate1);
		//bean.setDataAl(dataSoppressa);
		
		// Resoconto sulla situazione documentazione competenza alla UO
	    // Situazione dei documenti/fascicoli assegnati
		bean.setNrDocAssegnati(result.getResultBean().getNrodocassout());
		bean.setDataConteggioDocAssegnati(StringUtils.isNotBlank(result.getResultBean().getTsrilevnrodocassout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(result.getResultBean().getTsrilevnrodocassout()) : null);
		bean.setNrFascAssegnati(result.getResultBean().getNrofascassout());
		bean.setDataConteggioFascAssegnati(StringUtils.isNotBlank(result.getResultBean().getTsrilevnrofascassout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(result.getResultBean().getTsrilevnrofascassout()) : null);
		bean.setDescUoSpostamentoDocFasc((StringUtils.isNotBlank(result.getResultBean().getLivelliuodestdocfascout()) ? result.getResultBean().getLivelliuodestdocfascout() :  "") + " " + (StringUtils.isNotBlank(result.getResultBean().getDesuodestdocfascout()) ? result.getResultBean().getDesuodestdocfascout() :  ""));
		bean.setStatoSpostamentoDocFasc(result.getResultBean().getStatospostdocfascout());
		bean.setDataInizioSpostamentoDocFasc(StringUtils.isNotBlank(result.getResultBean().getTsiniziospostdocfascout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(result.getResultBean().getTsiniziospostdocfascout()) : null);
		bean.setDataFineSpostamentoDocFasc(StringUtils.isNotBlank(result.getResultBean().getTsfinespostdocfascout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(result.getResultBean().getTsfinespostdocfascout()) : null);
		
		mLogger.debug("End method get");
		return bean;
	}

	@Override
	public PostazioneBean add(PostazioneBean bean) throws Exception {
			
		mLogger.debug("Start method add");
		DmpkDefSecurityIureluouserBean lDmpkDefSecurityIureluouserBean = new DmpkDefSecurityIureluouserBean();
		lDmpkDefSecurityIureluouserBean.setIduserin(bean.getIdUser() != null ? new BigDecimal(bean.getIdUser()) : null);
		if (bean.getDataDal() != null)
			lDmpkDefSecurityIureluouserBean.setDtiniziovldin(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataDal()));
		if (bean.getDataAl() != null)
			lDmpkDefSecurityIureluouserBean.setDtfinevldin(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataAl()));
		lDmpkDefSecurityIureluouserBean.setFlgtiporelin(bean.getTipoRelUtenteUo());
		lDmpkDefSecurityIureluouserBean.setFlginclsottouoin(bean.isFlgInclSottoUo() ? 1 : 0);
		lDmpkDefSecurityIureluouserBean.setFlginclscrivvirtin(bean.isFlgInclScrivVirt() ? 1 : 0);
		lDmpkDefSecurityIureluouserBean.setDesruoloammin(bean.getRuolo());
	
		/**
		 * Controllo di recupero dell' idUO, viene verificato il corretto recupero del dato da lista oppure da menù ad albero, poichè la medesima property viene
		 * settata in maniera differente a seconda dalla posizione in cui viene richiesta.
		 */
		if (StringUtils.isNotBlank(bean.getIdUo())) {
			lDmpkDefSecurityIureluouserBean.setIduoin(bean.getIdUo() != null ? new BigDecimal(bean.getIdUo()) : null);
			bean.setIdUoSvUt(bean.getIdUo());
		} else {
			lDmpkDefSecurityIureluouserBean.setIduoin(bean.getIdUoSvUt() != null ? new BigDecimal(bean.getIdUoSvUt()) : null);
			bean.setIdUo(bean.getIdUoSvUt());
		}
	
		if (StringUtils.isNotBlank(bean.getCiRelUserUo())) {
			lDmpkDefSecurityIureluouserBean.setFlgxscrivaniain("V");
			lDmpkDefSecurityIureluouserBean.setCireluseruoio(bean.getCiRelUserUo());
			lDmpkDefSecurityIureluouserBean.setIdscrivaniaio(StringUtils.isNotBlank(bean.getIdScrivania()) ? new BigDecimal(bean.getIdScrivania()) : null);
		} else {
			lDmpkDefSecurityIureluouserBean.setFlgxscrivaniain("N");
		}
	
		lDmpkDefSecurityIureluouserBean.setNuovaintestazionescrivaniaio(bean.getNomePostazione());

		
		// Id. della UO a cui spostare documenti e fascicoli assegnati alla UO (in genere impostata in caso di cessazione della UO)
		lDmpkDefSecurityIureluouserBean.setIduosvdestdocin(bean.getIdUoSpostaDocFasc());
		
		// Tipologia destinatario ( UO/SV) 
		lDmpkDefSecurityIureluouserBean.setFlgtipodestdocin(bean.getTypeNodoSpostaDocFasc());
					
				
		DmpkDefSecurityIureluouser store = new DmpkDefSecurityIureluouser();
		StoreResultBean<DmpkDefSecurityIureluouserBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),lDmpkDefSecurityIureluouserBean);
		if (result.isInError()) {
			throw new StoreException(result);
		}
		
		/**
		 * Dopo aver creato la Relazione valorizzo la chiave primaria della stessa: ciRelUserUo
		 */
		bean.setCiRelUserUo(StringUtils.isNotBlank(result.getResultBean().getCireluseruoio()) ? result.getResultBean().getCireluseruoio() : null);
		
		mLogger.debug("End method add");
		return bean;

	}

	@Override
	public PostazioneBean update(PostazioneBean bean, PostazioneBean oldvalue) throws Exception {
		
		if(bean.getFlgSpostamento() != null && bean.getFlgSpostamento()){
			mLogger.debug("Start method spostamento utente");
			bean = spostamentoSV(bean);
			mLogger.debug("End method spostamento utente");
		} else if (bean.getFlgDuplicazione() != null && bean.getFlgDuplicazione()) {
			mLogger.debug("Start method sostituzione utente");
			bean = duplicazioneSV(bean);
			mLogger.debug("End method sostituzione utente");
		} else if (bean.getSostituzioneSV() != null && bean.getSostituzioneSV().equals("1")) {
			mLogger.debug("Start method sostituzione utente");
			bean = sostituisciUtente(bean);
			mLogger.debug("End method sostituzione utente");
		} else {
			mLogger.debug("Start method update");
			DmpkDefSecurityIureluouserBean lDmpkDefSecurityIureluouserBean = new DmpkDefSecurityIureluouserBean();
			lDmpkDefSecurityIureluouserBean.setCireluseruoio(bean.getCiRelUserUo());
			lDmpkDefSecurityIureluouserBean.setIduserin(bean.getIdUser() != null ? new BigDecimal(bean.getIdUser()) : null);
			if (bean.getDataDal() != null)
				lDmpkDefSecurityIureluouserBean.setDtiniziovldin(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataDal()));
			if (bean.getDataAl() != null)
				lDmpkDefSecurityIureluouserBean.setDtfinevldin(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataAl()));
			lDmpkDefSecurityIureluouserBean.setFlgtiporelin(bean.getTipoRelUtenteUo());
			lDmpkDefSecurityIureluouserBean.setFlginclsottouoin(bean.isFlgInclSottoUo() ? 1 :  0);
			lDmpkDefSecurityIureluouserBean.setFlginclscrivvirtin(bean.isFlgInclScrivVirt() ? 1 : 0);
			lDmpkDefSecurityIureluouserBean.setDesruoloammin(bean.getRuolo());
			
			/**
			 * Controllo di recupero dell' idUO, viene verificato il corretto recupero del dato da lista oppure da menù ad albero, poichè la medesima property
			 * viene settata in maniera differente a seconda dalla posizione in cui viene richiesta.
			 */
			if (StringUtils.isNotBlank(bean.getIdUo())) {
				lDmpkDefSecurityIureluouserBean.setIduoin(bean.getIdUo() != null ? new BigDecimal(bean.getIdUo()) : null);
				bean.setIdUoSvUt(bean.getIdUo());
			} else {
				lDmpkDefSecurityIureluouserBean.setIduoin(bean.getIdUoSvUt() != null ? new BigDecimal(bean.getIdUoSvUt()) : null);
				bean.setIdUo(bean.getIdUoSvUt());
			}

			if (StringUtils.isNotBlank(bean.getCiRelUserUo())) {
				lDmpkDefSecurityIureluouserBean.setFlgxscrivaniain("V");
				lDmpkDefSecurityIureluouserBean.setCireluseruoio(bean.getCiRelUserUo());
				lDmpkDefSecurityIureluouserBean.setIdscrivaniaio(!"null".equals(bean.getIdScrivania()) && StringUtils.isNotBlank(bean.getIdScrivania()) ? new BigDecimal(bean.getIdScrivania()) : null);
			} else {
				lDmpkDefSecurityIureluouserBean.setFlgxscrivaniain("N");
			}

			lDmpkDefSecurityIureluouserBean.setNuovaintestazionescrivaniaio(bean.getNomePostazione());
			lDmpkDefSecurityIureluouserBean.setListaesclusioniuoppin(bean.getListaUOPuntoProtocolloEscluse());
			// FIXME mettere in lDmpkDefSecurityIureluouserBean le uo con ereditarieta abilitata
			
			// Id. della UO a cui spostare documenti e fascicoli assegnati alla UO (in genere impostata in caso di cessazione della UO)
			lDmpkDefSecurityIureluouserBean.setIduosvdestdocin(bean.getIdUoSpostaDocFasc());
			
			// Tipologia destinatario ( UO/SV) 
			lDmpkDefSecurityIureluouserBean.setFlgtipodestdocin(bean.getTypeNodoSpostaDocFasc());
			
			
			DmpkDefSecurityIureluouser store = new DmpkDefSecurityIureluouser();
			StoreResultBean<DmpkDefSecurityIureluouserBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
					lDmpkDefSecurityIureluouserBean);
			if (result.isInError()) {
				throw new StoreException(result);
			}
			mLogger.debug("End method update");
		}
		return bean;
	}

	public PostazioneBean sostituisciUtente(PostazioneBean bean) throws Exception {
		
		DmpkDefSecuritySubstuserinreluoBean lDmpkDefSecuritySubstuserinreluoBean = new DmpkDefSecuritySubstuserinreluoBean();
		// CIRelUserUOIO - Id della relazione utente-uo di partenza
		lDmpkDefSecuritySubstuserinreluoBean.setCireluseruoio(bean.getCiRelUserUo());
		// IdUserNewIn - Id. Del nuovo utente
		lDmpkDefSecuritySubstuserinreluoBean.setIdusernewin(bean.getIdUserNew() != null ? new BigDecimal(bean.getIdUserNew()) : null);
		// DtInizioVldIn - Data inizio della sezione "Da sostituire con"
		if (bean.getDataDalNew() != null)
			lDmpkDefSecuritySubstuserinreluoBean.setDtiniziovldin(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataDalNew()));
		// Data fine della sezione "Da sostituire con"
		if (bean.getDataAlNew() != null)
			lDmpkDefSecuritySubstuserinreluoBean.setDtfinevldin(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataAlNew()));
		// FlgTipoRelIn (valori A/D) select del tipo di relazione UO-utente della sezione "Da sostituire con"
		lDmpkDefSecuritySubstuserinreluoBean.setFlgtiporelin(bean.getTipoRelUtenteUoNew());
		// FlgInclSottoUOIn (valori 1/0) check "Incluse sotto-UO" della sezione "Da sostituire con"
		lDmpkDefSecuritySubstuserinreluoBean.setFlginclsottouoin(bean.isFlgInclSottoUoNew() ? 1 : 0);
		// FlgInclScrivVirtIn (valori 1/0) check "Incluse postazioni" della sezione "Da sostituire con"
		lDmpkDefSecuritySubstuserinreluoBean.setFlginclscrivvirtin(bean.isFlgInclScrivVirtNew() ? 1 : 0);
		// IdRuoloAmmIn Id. del ruolo della select (Dirigente ecc) della sezione "Da sostituire con"
		lDmpkDefSecuritySubstuserinreluoBean.setDesruoloammin(bean.getRuoloNew());

		// Lista delle UO escluse
		lDmpkDefSecuritySubstuserinreluoBean.setListaesclusioniuoppin(bean.getListaUOPuntoProtocolloEscluse());
		// FIXME mettere in lDmpkDefSecuritySubstuserinreluoBean le uo con ereditarieta abilitata
		
		DmpkDefSecuritySubstuserinreluo store = new DmpkDefSecuritySubstuserinreluo();
		StoreResultBean<DmpkDefSecuritySubstuserinreluoBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
				lDmpkDefSecuritySubstuserinreluoBean);
		if (output.isInError()) {
			throw new StoreException(output);
		}
		
		PostazioneBean result = new PostazioneBean();
		result.setIdUser(bean.getIdUserNew());
		result.setIdUo(bean.getIdUo());
		result.setTipoRelUtenteUo(bean.getTipoRelUtenteUo());
		
		return result;
	}

	public PostazioneBean recuperaTipoAssegnazioneXUtente(PostazioneBean bean) throws Exception {
		
		DmpkDefSecurityGettiponuovareluouserBean lDmpkDefSecurityGettiponuovareluouserBean = new DmpkDefSecurityGettiponuovareluouserBean();		
		
		/**
		 * Controllo di recupero dell' idUO, viene verificato il corretto recupero del dato da lista oppure da menù ad albero, poichè la medesima property viene
		 * settata in maniera differente a seconda dalla posizione in cui viene richiesta.
		 */
		if (StringUtils.isNotBlank(bean.getIdUo())) {
			lDmpkDefSecurityGettiponuovareluouserBean.setIduoin(bean.getIdUo() != null ? new BigDecimal(bean.getIdUo()) : null);
			bean.setIdUoSvUt(bean.getIdUo());
		} else {
			lDmpkDefSecurityGettiponuovareluouserBean.setIduoin(bean.getIdUoSvUt() != null ? new BigDecimal(bean.getIdUoSvUt()) : null);
			bean.setIdUo(bean.getIdUoSvUt());
		}
		lDmpkDefSecurityGettiponuovareluouserBean.setCireluseruotosubstin(bean.getCiRelUserUo());
		lDmpkDefSecurityGettiponuovareluouserBean.setIduserin(bean.getIdUser() != null ? new BigDecimal(bean.getIdUser()) : null);
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(loginBean.getSchema());
		
		DmpkDefSecurityGettiponuovareluouser store = new DmpkDefSecurityGettiponuovareluouser();
		StoreResultBean<DmpkDefSecurityGettiponuovareluouserBean> result = store.execute(getLocale(), lSchemaBean, lDmpkDefSecurityGettiponuovareluouserBean);
		if (result.isInError()) {
			throw new StoreException(result);
		}
		
		bean.setTipoRelUtenteUo(result.getResultBean().getFlgtiporelout());
		
		return bean;
	}

	private PostazioneBean spostamentoSV(PostazioneBean bean) throws Exception{
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkDefSecurityMovepostazioneutenteBean lDmpkDefSecurityMovepostazioneutenteBean = new DmpkDefSecurityMovepostazioneutenteBean();
		lDmpkDefSecurityMovepostazioneutenteBean.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkDefSecurityMovepostazioneutenteBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkDefSecurityMovepostazioneutenteBean.setCireluseruoio(bean.getCiRelUserUo());
		
		/**
		 * Controllo di recupero dell' idUO, viene verificato il corretto recupero del dato da lista oppure da menù ad albero, poichè la medesima property viene
		 * settata in maniera differente a seconda dalla posizione in cui viene richiesta.
		 */
		if (StringUtils.isNotBlank(bean.getIdUo())) {
			lDmpkDefSecurityMovepostazioneutenteBean.setIduoin(bean.getIdUo() != null ? new BigDecimal(bean.getIdUo()) : null);
			bean.setIdUoSvUt(bean.getIdUo());
		} else {
			lDmpkDefSecurityMovepostazioneutenteBean.setIduoin(bean.getIdUoSvUt() != null ? new BigDecimal(bean.getIdUoSvUt()) : null);
			bean.setIdUo(bean.getIdUoSvUt());
		}
		
		if (bean.getDataDal() != null)
			lDmpkDefSecurityMovepostazioneutenteBean.setDtiniziovldin(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataDal()));
		if (bean.getDataAl() != null)
			lDmpkDefSecurityMovepostazioneutenteBean.setDtfinevldin(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataAl()));
		lDmpkDefSecurityMovepostazioneutenteBean.setFlgtiporelin(bean.getTipoRelUtenteUo());
		lDmpkDefSecurityMovepostazioneutenteBean.setFlginclsottouoin(bean.isFlgInclSottoUo() ? 1 : 0);
		lDmpkDefSecurityMovepostazioneutenteBean.setFlginclscrivvirtin(bean.isFlgInclScrivVirt() ? 1 : 0);
		lDmpkDefSecurityMovepostazioneutenteBean.setNuovaintestazionescrivaniaio(bean.getNomePostazione());
		lDmpkDefSecurityMovepostazioneutenteBean.setDesruoloammin(bean.getRuolo());
		
		DmpkDefSecurityMovepostazioneutente lDmpkDefSecurityMovepostazioneutente = new DmpkDefSecurityMovepostazioneutente();
		StoreResultBean<DmpkDefSecurityMovepostazioneutenteBean> result = lDmpkDefSecurityMovepostazioneutente.execute(getLocale(), loginBean, lDmpkDefSecurityMovepostazioneutenteBean);
		
		if (result.isInError()) {
			throw new StoreException(result);
		}
		
		PostazioneBean loadDett = new PostazioneBean();
		loadDett.setIdUo(bean.getIdUo());
		loadDett.setIdUser(bean.getIdUser());
		loadDett.setTipoRelUtenteUo(bean.getTipoRelUtenteUo());
		
		return get(loadDett);
	}
	
	private PostazioneBean duplicazioneSV(PostazioneBean bean) throws Exception{
		
		DmpkDefSecurityIureluouserBean lDmpkDefSecurityIureluouserBean = new DmpkDefSecurityIureluouserBean();
		lDmpkDefSecurityIureluouserBean.setIduserin(bean.getIdUser() != null ? new BigDecimal(bean.getIdUser()) : null);
		if (bean.getDataDal() != null)
			lDmpkDefSecurityIureluouserBean.setDtiniziovldin(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataDal()));
		if (bean.getDataAl() != null)
			lDmpkDefSecurityIureluouserBean.setDtfinevldin(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataAl()));

		lDmpkDefSecurityIureluouserBean.setFlgtiporelin(bean.getTipoRelUtenteUo());
		lDmpkDefSecurityIureluouserBean.setFlginclsottouoin(bean.isFlgInclSottoUo() ? 1 :  0);
		lDmpkDefSecurityIureluouserBean.setFlginclscrivvirtin(bean.isFlgInclScrivVirt() ? 1 : 0);
		lDmpkDefSecurityIureluouserBean.setDesruoloammin(bean.getRuolo());
	
		/**
		 * Controllo di recupero dell' idUO, viene verificato il corretto recupero del dato da lista oppure da menù ad albero, poichè la medesima property viene
		 * settata in maniera differente a seconda dalla posizione in cui viene richiesta.
		 */
		if (StringUtils.isNotBlank(bean.getIdUo())) {
			lDmpkDefSecurityIureluouserBean.setIduoin(bean.getIdUo() != null ? new BigDecimal(bean.getIdUo()) : null);
		} else {
			lDmpkDefSecurityIureluouserBean.setIduoin(bean.getIdUoSvUt() != null ? new BigDecimal(bean.getIdUoSvUt()) : null);
		}

		lDmpkDefSecurityIureluouserBean.setFlgxscrivaniain("N");
		lDmpkDefSecurityIureluouserBean.setNuovaintestazionescrivaniaio(bean.getNomePostazione());
	
		DmpkDefSecurityIureluouser store = new DmpkDefSecurityIureluouser();
		StoreResultBean<DmpkDefSecurityIureluouserBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
				lDmpkDefSecurityIureluouserBean);
		if (result.isInError()) {
			throw new StoreException(result);
		}
		
		PostazioneBean loadDett = new PostazioneBean();
		loadDett.setIdUo(bean.getIdUo());
		loadDett.setIdUser(bean.getIdUser());
		loadDett.setTipoRelUtenteUo(bean.getTipoRelUtenteUo());
		
		return get(loadDett);
	}

	@Override
	public PostazioneBean remove(PostazioneBean bean) throws Exception {
		mLogger.debug("Start method remove");
		mLogger.debug("End method remove");
		return bean;
	}

	@Override
	public PaginatorBean<PostazioneBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		mLogger.debug("Start method fetch");
		mLogger.debug("End method fetch");
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(PostazioneBean bean) throws Exception {
		mLogger.debug("Start method validate");
		mLogger.debug("End method validate");
		return null;
	}

}
