package it.eng.auriga.ui.module.layout.server.gestioneAtti;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesSetsogginterniBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.SmistamentoAttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.SoggettoSmistamentoAttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.UfficioLiquidatoreBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;
import it.eng.client.DmpkProcessesSetsogginterni;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "SmistamentoAttiDataSource")
public class SmistamentoAttiDataSource extends AbstractDataSource<SmistamentoAttiBean, SmistamentoAttiBean>{	

	@Override
	public SmistamentoAttiBean add(SmistamentoAttiBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(AttiBean atto : bean.getListaRecord()) {		
			
			String errorMessage = "";
			
			if(isAttivoClienteCOTO()) {
				DmpkProcessesSetsogginterniBean input = new DmpkProcessesSetsogginterniBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdprocessin(atto.getIdProcedimento() != null ? new BigDecimal(atto.getIdProcedimento()) : null);
				input.setRuolosoggettiin("LIQUIDATORE");
				input.setListaxmlin(getXmlListaUfficioLiquidatore(bean));
					
				DmpkProcessesSetsogginterni dmpkProcessesSetsogginterni = new DmpkProcessesSetsogginterni();
				StoreResultBean<DmpkProcessesSetsogginterniBean> output = dmpkProcessesSetsogginterni.execute(getLocale(),loginBean, input);
				
				if(output.isInError()) {
					errorMessage += "Smistamento atto con ruolo LIQUIDATORE fallito";
					if(StringUtils.isNotBlank(output.getDefaultMessage())) {
						errorMessage += ": " + output.getDefaultMessage();
					} 
					errorMessage += ". ";
				}			
			}
						
			if(StringUtils.isNotBlank(atto.getRuoloSmistamento())) {
				
				DmpkProcessesSetsogginterniBean input = new DmpkProcessesSetsogginterniBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdprocessin(atto.getIdProcedimento() != null ? new BigDecimal(atto.getIdProcedimento()) : null);
				input.setRuolosoggettiin(atto.getRuoloSmistamento());
				input.setListaxmlin(getXmlListaSmistamento(bean));
					
				DmpkProcessesSetsogginterni dmpkProcessesSetsogginterni = new DmpkProcessesSetsogginterni();
				StoreResultBean<DmpkProcessesSetsogginterniBean> output = dmpkProcessesSetsogginterni.execute(getLocale(),loginBean, input);
					
				if(output.isInError()) {
					errorMessage += "Smistamento atto con ruolo " + atto.getRuoloSmistamento() + " fallito";
					if(StringUtils.isNotBlank(output.getDefaultMessage())) {
						errorMessage += ": " + output.getDefaultMessage();
					} 
					errorMessage += ".";
				}	
					
			} else {
				errorMessage = "Smistamento atto fallito: lo stato attuale e/o le tue abilitazioni non ti consentono di smistarlo.";
			}
			
			if(StringUtils.isNotBlank(errorMessage)) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();
				errorMessages.put(atto.getIdProcedimento(), errorMessage);
			}
						
		}
		
		if(errorMessages != null) {
			bean.setErrorMessages(errorMessages);			
		}
		
		return bean;
	}
	
	public boolean isAttivoClienteCOTO() {
		String cliente = ParametriDBUtil.getParametroDB(getSession(), "CLIENTE");
		return cliente != null && !"".equals(cliente) && "COTO".equals(cliente);
	}
	
	public String getXmlListaUfficioLiquidatore(SmistamentoAttiBean bean) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getListaUfficioLiquidatore(bean));
	}
	
	private List<SoggettoSmistamentoAttiBean> getListaUfficioLiquidatore(SmistamentoAttiBean bean) throws Exception {
		List<SoggettoSmistamentoAttiBean> listaUfficioLiquidatore = new ArrayList<SoggettoSmistamentoAttiBean>();
		if(bean.getListaUfficioLiquidatore() != null){
			for(UfficioLiquidatoreBean ufficioLiquidatoreBean : bean.getListaUfficioLiquidatore()) {
				if(ufficioLiquidatoreBean != null && StringUtils.isNotBlank(ufficioLiquidatoreBean.getUfficioLiquidatore())) {
					SoggettoSmistamentoAttiBean lSoggettoSmistamentoAttiBean = new SoggettoSmistamentoAttiBean();
					lSoggettoSmistamentoAttiBean.setFlgUoSv("G");			
					lSoggettoSmistamentoAttiBean.setIdUoSv(ufficioLiquidatoreBean.getUfficioLiquidatore());
					listaUfficioLiquidatore.add(lSoggettoSmistamentoAttiBean);
				}				
			}
		}
		return listaUfficioLiquidatore;
	}
	
	public String getXmlListaSmistamento(SmistamentoAttiBean bean) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getListaSmistamento(bean));
	}
	
	private List<SoggettoSmistamentoAttiBean> getListaSmistamento(SmistamentoAttiBean bean) throws Exception {
		List<SoggettoSmistamentoAttiBean> listaSmistamento = new ArrayList<SoggettoSmistamentoAttiBean>();
		if(bean.getListaSmistamento() != null){
			for(AssegnazioneBean assegnazioneBean : bean.getListaSmistamento()) {
				if(assegnazioneBean != null && StringUtils.isNotBlank(assegnazioneBean.getIdUo())) {
					SoggettoSmistamentoAttiBean lSoggettoSmistamentoAttiBean = new SoggettoSmistamentoAttiBean();
					lSoggettoSmistamentoAttiBean.setFlgUoSv(assegnazioneBean.getTypeNodo());			
					lSoggettoSmistamentoAttiBean.setIdUoSv(assegnazioneBean.getIdUo());
					listaSmistamento.add(lSoggettoSmistamentoAttiBean);
				}
			}
		}
		return listaSmistamento;
	}

	@Override
	public SmistamentoAttiBean get(SmistamentoAttiBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public SmistamentoAttiBean remove(SmistamentoAttiBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public SmistamentoAttiBean update(SmistamentoAttiBean bean,
			SmistamentoAttiBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<SmistamentoAttiBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(SmistamentoAttiBean bean)
	throws Exception {
		
		return null;
	}

}
