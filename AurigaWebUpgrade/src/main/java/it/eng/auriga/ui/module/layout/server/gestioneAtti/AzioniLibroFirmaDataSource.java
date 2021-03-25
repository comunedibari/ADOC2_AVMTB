package it.eng.auriga.ui.module.layout.server.gestioneAtti;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core_2.bean.DmpkCore2InviarimuovidoclibrofirmaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.DocInfoLibroFirma;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.OperazioneMassivaAttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.OperazioneMassivaProcedimentiBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.ProcedimentiInIterBean;
import it.eng.client.DmpkCore2Inviarimuovidoclibrofirma;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AzioniLibroFirmaDataSource")
public class AzioniLibroFirmaDataSource extends AbstractDataSource<AttiBean,AttiBean>  {

	private static final Logger log = Logger.getLogger(AzioniLibroFirmaDataSource.class);

	public OperazioneMassivaAttiBean mandaALibroFirma (OperazioneMassivaAttiBean bean) throws Exception {
		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(AttiBean atto : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(atto.getUnitaDocumentariaId());
			docInfo.setIdProcess(atto.getIdProcedimento());
			inputList.add(docInfo);
		}
		
		HashMap<String, String> errorMessages = mandaALibroFirmaCommon(inputList);
		
		bean.setErrorMessages(errorMessages);
		return bean;
	}
	
	public OperazioneMassivaProcedimentiBean mandaALibroFirmaDaProcedimentiInIter (OperazioneMassivaProcedimentiBean bean) throws Exception {
		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(ProcedimentiInIterBean procedimentiInIterBean : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(procedimentiInIterBean.getIdUdRispostaCedAutotutele());
			docInfo.setIdProcess(procedimentiInIterBean.getIdProcedimento());
			docInfo.setIdDocType(procedimentiInIterBean.getIdDocTypeRispostaCedAutotutele());
			inputList.add(docInfo);
		}
		
		HashMap<String, String> errorMessages = mandaALibroFirmaCommon(inputList);
		
		bean.setErrorMessages(errorMessages);
		return bean;
	}
	
	public OperazioneMassivaProcedimentiBean mandaALibroFirmaDaProcedimentiPersonali (OperazioneMassivaProcedimentiBean bean) throws Exception {
		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(ProcedimentiInIterBean procedimentiInIterBean : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(procedimentiInIterBean.getIdUdRispostaCedAutotutele());
			docInfo.setIdProcess(procedimentiInIterBean.getIdProcedimento());
			docInfo.setIdDocType(procedimentiInIterBean.getIdDocTypeRispostaCedAutotutele());
			inputList.add(docInfo);
		}
		
		HashMap<String, String> errorMessages = mandaALibroFirmaCommon(inputList);
		
		bean.setErrorMessages(errorMessages);
		return bean;
	}


	private HashMap<String, String> mandaALibroFirmaCommon(List<DocInfoLibroFirma> inputList) throws Exception{
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String inputListStr = new XmlUtilitySerializer().bindXmlList(inputList);
		
		DmpkCore2InviarimuovidoclibrofirmaBean input = new DmpkCore2InviarimuovidoclibrofirmaBean();
		input.setAzionein("invio");
		input.setDocinfoin(inputListStr);
		
		DmpkCore2Inviarimuovidoclibrofirma store = new DmpkCore2Inviarimuovidoclibrofirma();
		StoreResultBean<DmpkCore2InviarimuovidoclibrofirmaBean> output = store.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			throw new StoreException(output);
		}

		HashMap<String, String> errorMessages = null;		

		if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(output.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
				if (v.get(3).equalsIgnoreCase("ko")) {
					errorMessages.put(v.get(2), v.get(4));
				}
			}
		}
		
		return errorMessages;
	}

	public OperazioneMassivaAttiBean togliDaLibroFirma (OperazioneMassivaAttiBean bean) throws Exception {

		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(AttiBean atto : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(atto.getUnitaDocumentariaId());
			docInfo.setIdProcess(atto.getIdProcedimento());
			inputList.add(docInfo);
		}
		HashMap<String, String> errorMessages = togliDaLibroFirmaCommon(inputList);
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}
	
	public OperazioneMassivaProcedimentiBean togliDaLibroFirmaDaProcedimentiInIter (OperazioneMassivaProcedimentiBean bean) throws Exception {

		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(ProcedimentiInIterBean procedimentiInIterBean : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(procedimentiInIterBean.getIdUdRispostaCedAutotutele());
			docInfo.setIdProcess(procedimentiInIterBean.getIdProcedimento());
			docInfo.setIdDocType(procedimentiInIterBean.getIdDocTypeRispostaCedAutotutele());
			inputList.add(docInfo);
		}
		HashMap<String, String> errorMessages = togliDaLibroFirmaCommon(inputList);
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}
	
	public OperazioneMassivaProcedimentiBean togliDaLibroFirmaDaProcedimentiPersonali (OperazioneMassivaProcedimentiBean bean) throws Exception {

		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(ProcedimentiInIterBean procedimentiInIterBean : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(procedimentiInIterBean.getIdUdRispostaCedAutotutele());
			docInfo.setIdProcess(procedimentiInIterBean.getIdProcedimento());
			docInfo.setIdDocType(procedimentiInIterBean.getIdDocTypeRispostaCedAutotutele());
			inputList.add(docInfo);
		}
		HashMap<String, String> errorMessages = togliDaLibroFirmaCommon(inputList);
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}

	private HashMap<String, String> togliDaLibroFirmaCommon(List<DocInfoLibroFirma> inputList) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String inputListStr = new XmlUtilitySerializer().bindXmlList(inputList);
		
		DmpkCore2InviarimuovidoclibrofirmaBean input = new DmpkCore2InviarimuovidoclibrofirmaBean();
		input.setAzionein("rimuovi");
		input.setDocinfoin(inputListStr);
		
		DmpkCore2Inviarimuovidoclibrofirma store = new DmpkCore2Inviarimuovidoclibrofirma();
		StoreResultBean<DmpkCore2InviarimuovidoclibrofirmaBean> output = store.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			throw new StoreException(output);
		}

		HashMap<String, String> errorMessages = null;		

		if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(output.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
				if (v.get(3).equalsIgnoreCase("ko")) {
					errorMessages.put(v.get(2), v.get(4));
				}
			}
		}
		return errorMessages;
	}

	public OperazioneMassivaAttiBean segnaDaRicontrollare (OperazioneMassivaAttiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		for (int i = 0; i < bean.getListaRecord().size(); ++i) {
			AttiBean atto = bean.getListaRecord().get(i);
			
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("U");
				input.setIduddocin(new BigDecimal(atto.getUnitaDocumentariaId()));
				
				CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
				creaModDocumentoInBean.setCodStatoDett("DACONTR");				
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
				
				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
				if (output.getDefaultMessage() != null) {	
					String dettagliAtto = "";
					if(StringUtils.isNotBlank(atto.getNumeroAtto())) {
						dettagliAtto = atto.getNumeroAtto() + " - ";
					}
					errorMessages.put(dettagliAtto + "Proposta " + atto.getNumeroProposta(), output.getDefaultMessage());
				}
		}
		
		if(errorMessages != null && !errorMessages.isEmpty()) {
			bean.setErrorMessages(errorMessages);
		}
		
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}

	@Override
	public AttiBean get(AttiBean bean) throws Exception {
		return null;
	}

	@Override
	public AttiBean add(AttiBean bean) throws Exception {
		return null;
	}

	@Override
	public AttiBean remove(AttiBean bean) throws Exception {
		return null;
	}

	@Override
	public AttiBean update(AttiBean bean, AttiBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<AttiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AttiBean bean) throws Exception {
		return null;
	}





}
