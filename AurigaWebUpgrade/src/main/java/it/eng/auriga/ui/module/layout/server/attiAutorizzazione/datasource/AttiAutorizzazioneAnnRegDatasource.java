package it.eng.auriga.ui.module.layout.server.attiAutorizzazione.datasource;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocIuattoautannregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocLoaddettattoautannregBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.attiAutorizzazione.datasource.bean.AttiAutorizzazioneAnnRegBean;
import it.eng.auriga.ui.module.layout.server.attiAutorizzazione.datasource.bean.RegDaAnnullareBean;
import it.eng.auriga.ui.module.layout.server.attiAutorizzazione.datasource.bean.XmlDatiAttoOutBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkRegistrazionedocIuattoautannreg;
import it.eng.client.DmpkRegistrazionedocLoaddettattoautannreg;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

@Datasource(id="AttiAutorizzazioneAnnRegDatasource")
public class AttiAutorizzazioneAnnRegDatasource extends AbstractFetchDataSource<AttiAutorizzazioneAnnRegBean>{
	
	@Override
	public PaginatorBean<AttiAutorizzazioneAnnRegBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String restringiAttiAutAnnReg = getExtraparams().get("restringiAttiAutAnnReg") != null ? getExtraparams().get("restringiAttiAutAnnReg") : "tutti";
		
		String colsToReturn = "2,4,14,15,18,54,201";		
		
		List<AttiAutorizzazioneAnnRegBean> data = new ArrayList<AttiAutorizzazioneAnnRegBean>();   
        
		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();
//		scCriteriAvanzati.setIdDocType("20"); // è il tipo degli atti di annullamento
        scCriteriAvanzati.setRestringiAttiAutAnnReg(restringiAttiAutAnnReg);
        
        XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
        String advancedFilters = lXmlUtilitySerializer.bindXml(scCriteriAvanzati);         
        
		FindRepositoryObjectBean lFindRepositoryObjectBean = new FindRepositoryObjectBean();		
		lFindRepositoryObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());
		lFindRepositoryObjectBean.setFlgUdFolder("U");
		lFindRepositoryObjectBean.setAdvancedFilters(advancedFilters);
		lFindRepositoryObjectBean.setFlgSenzaPaginazione(new Integer(1));
		lFindRepositoryObjectBean.setColsToReturn(colsToReturn);
			
		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findrepositoryobject(
				getLocale(), 
				loginBean, 
				lFindRepositoryObjectBean
			).getList();
		} catch(Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		String xmlResultSetOut = (String) resFinder.get(0);
		String errorMessageOut = null;
		
		if (resFinder.size() > 5){ 
			errorMessageOut = (String) resFinder.get(5);
		}
			
		if(errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}
		
		// Conversione ListaRisultati ==> EngResultSet 
		if (xmlResultSetOut != null){
			StringReader sr = new StringReader(xmlResultSetOut);
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																	
		        	AttiAutorizzazioneAnnRegBean node = new AttiAutorizzazioneAnnRegBean();	        		
		        	node.setIdAtto(v.get(1)); //col.  2    
		        	node.setNroBozza(v.get(13)); //col.  14 
		        	node.setNroAtto(v.get(3)); //col.  4
		        	node.setTsRegBozza(v.get(14) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(14)) : null); //col. 15 		        		
		        	node.setOggetto(v.get(17)); //col.  18 		        	
		        	node.setTsRegAtto(v.get(200) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(200)) : null); //col.  201 	      		        	
		        	node.setFlgAttoChiuso(StringUtils.isNotBlank(v.get(53)) && "chiuso".equals(v.get(53)) ? new Integer(1) : null); //col.  54
		        	data.add(node);
	    		}
			}							
		}			
					
		PaginatorBean<AttiAutorizzazioneAnnRegBean> lPaginatorBean = new PaginatorBean<AttiAutorizzazioneAnnRegBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}	
	
	public AttiAutorizzazioneAnnRegBean inserisciRegInAttoAut(AttiAutorizzazioneAnnRegBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocIuattoautannregBean input = new DmpkRegistrazionedocIuattoautannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudattoio(bean.getIdAtto() != null ? new BigDecimal(bean.getIdAtto()) : null);
		input.setOggettoin(bean.getOggetto());
		input.setFlgregdaannullarein("I");
		input.setXmlregdaannullarein(creaXmlRegDaAnnullare(bean.getListaRegDaAnnullare()));
		
		DmpkRegistrazionedocIuattoautannreg dmpkRegistrazionedocIuattoautannreg = new DmpkRegistrazionedocIuattoautannreg();
		StoreResultBean<DmpkRegistrazionedocIuattoautannregBean> output = dmpkRegistrazionedocIuattoautannreg.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;		
	}
	
	public AttiAutorizzazioneAnnRegBean chiudiAtto(AttiAutorizzazioneAnnRegBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocIuattoautannregBean input = new DmpkRegistrazionedocIuattoautannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudattoio(bean.getIdAtto() != null ? new BigDecimal(bean.getIdAtto()) : null);
		input.setOggettoin(bean.getOggetto());
		input.setFlgattochiusoin(new Integer(1));
		input.setFlgregdaannullarein("I");
		input.setXmlregdaannullarein(null);
		
		DmpkRegistrazionedocIuattoautannreg dmpkRegistrazionedocIuattoautannreg = new DmpkRegistrazionedocIuattoautannreg();
		StoreResultBean<DmpkRegistrazionedocIuattoautannregBean> output = dmpkRegistrazionedocIuattoautannreg.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;		
	}
	
	private String creaXmlRegDaAnnullare(List<RegDaAnnullareBean> listaRegDaAnnullare) throws Exception {
		List<RegDaAnnullareBean> lista  = new ArrayList<RegDaAnnullareBean>();		
		if(listaRegDaAnnullare != null && listaRegDaAnnullare.size() > 0) {
			for(RegDaAnnullareBean regDaAnnullare : listaRegDaAnnullare) {
				if(StringUtils.isNotBlank(regDaAnnullare.getIdUd())) {
					lista.add(regDaAnnullare);
				}
			}								
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(lista);
	}
	
	@Override
	public AttiAutorizzazioneAnnRegBean get(AttiAutorizzazioneAnnRegBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		// Inizializzo l'INPUT		
		DmpkRegistrazionedocLoaddettattoautannregBean input = new DmpkRegistrazionedocLoaddettattoautannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setIdudattoio(new BigDecimal(bean.getIdAtto()));
				
		// Inizializzo l'OUTPUT
		DmpkRegistrazionedocLoaddettattoautannreg loaddettattoautannreg = new DmpkRegistrazionedocLoaddettattoautannreg();
		StoreResultBean<DmpkRegistrazionedocLoaddettattoautannregBean> output = loaddettattoautannreg.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}		
		
		AttiAutorizzazioneAnnRegBean result = new AttiAutorizzazioneAnnRegBean();		
		
		if(StringUtils.isNotBlank(output.getResultBean().getXmldatiattoout())) {
			XmlDatiAttoOutBean scXmlDatiAtto = new XmlDatiAttoOutBean();
	        XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
	        scXmlDatiAtto = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getXmldatiattoout(), XmlDatiAttoOutBean.class);      	       
	        if(scXmlDatiAtto != null) {
				result.setIdAtto(bean.getIdAtto());	
	        	result.setNroBozza(scXmlDatiAtto.getNroRegNumIniziale());	        		
	        	result.setTsRegBozza(scXmlDatiAtto.getTsRegistrazioneRegNumIniziale() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(scXmlDatiAtto.getTsRegistrazioneRegNumIniziale()) : null);
	        	result.setDesUteBozza(scXmlDatiAtto.getDesUserRegNumIniziale());
	        	result.setNroAtto(scXmlDatiAtto.getNroRegNumFinale());	        		
	        	result.setTsRegAtto(scXmlDatiAtto.getTsRegistrazioneRegNumFinale() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(scXmlDatiAtto.getTsRegistrazioneRegNumFinale()) : null);
	        	result.setDesUteAtto(scXmlDatiAtto.getDesUserRegNumFinale());
	        	result.setOggetto(scXmlDatiAtto.getDesOgg());
	        	result.setListaRegDaAnnullare(scXmlDatiAtto.getRegistrazioniAutorizzate());
	        	result.setFlgAttoChiuso(StringUtils.isNotBlank(scXmlDatiAtto.getFlgAttoChiuso()) ? new Integer(scXmlDatiAtto.getFlgAttoChiuso()) : null);
			}					
		}								
				
		return result;		
	}
	
	@Override
	public AttiAutorizzazioneAnnRegBean update(AttiAutorizzazioneAnnRegBean bean, AttiAutorizzazioneAnnRegBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocIuattoautannregBean input = new DmpkRegistrazionedocIuattoautannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudattoio(StringUtils.isNotBlank(bean.getIdAtto()) ? new BigDecimal(bean.getIdAtto()) : null);		
		input.setOggettoin(bean.getOggetto());		
		input.setFlgregdaannullarein("C");
		input.setXmlregdaannullarein(creaXmlRegDaAnnullare(bean.getListaRegDaAnnullare()));
		
		DmpkRegistrazionedocIuattoautannreg dmpkRegistrazionedocIuattoautannreg = new DmpkRegistrazionedocIuattoautannreg();		
		StoreResultBean<DmpkRegistrazionedocIuattoautannregBean> output = dmpkRegistrazionedocIuattoautannreg.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;		
	}

	@Override
	public AttiAutorizzazioneAnnRegBean add(AttiAutorizzazioneAnnRegBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocIuattoautannregBean input = new DmpkRegistrazionedocIuattoautannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setOggettoin(bean.getOggetto());		
		input.setFlgregdaannullarein("C");		
		input.setXmlregdaannullarein(null);
		
		DmpkRegistrazionedocIuattoautannreg dmpkRegistrazionedocIuattoautannreg = new DmpkRegistrazionedocIuattoautannreg();
		StoreResultBean<DmpkRegistrazionedocIuattoautannregBean> output = dmpkRegistrazionedocIuattoautannreg.execute(getLocale(), loginBean, input);
						
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.setIdAtto((String.valueOf(output.getResultBean().getIdudattoio())));
		
		return bean;
	}
		
	@Override
	public AttiAutorizzazioneAnnRegBean remove(AttiAutorizzazioneAnnRegBean bean) throws Exception {
		return null;
	}	
	
}
