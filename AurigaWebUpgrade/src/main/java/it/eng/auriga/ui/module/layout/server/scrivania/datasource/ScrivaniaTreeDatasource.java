package it.eng.auriga.ui.module.layout.server.scrivania.datasource;

import it.eng.auriga.database.store.dmpk_scrivania_virtuale.bean.DmpkScrivaniaVirtualeGetcontenutinodofuncBean;
import it.eng.auriga.database.store.dmpk_scrivania_virtuale.bean.DmpkScrivaniaVirtualeGetnavigatorescrivaniaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.scrivania.datasource.bean.ScrivaniaTreeNodeBean;
import it.eng.client.DmpkScrivaniaVirtualeGetcontenutinodofunc;
import it.eng.client.DmpkScrivaniaVirtualeGetnavigatorescrivania;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractTreeDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



import org.apache.commons.lang3.StringUtils;

@Datasource(id="ScrivaniaTreeDatasource")
public class ScrivaniaTreeDatasource extends AbstractTreeDataSource<ScrivaniaTreeNodeBean>{

	@Override
	public PaginatorBean<ScrivaniaTreeNodeBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		List<ScrivaniaTreeNodeBean> data = new ArrayList<ScrivaniaTreeNodeBean>();
		
		
		DmpkScrivaniaVirtualeGetnavigatorescrivaniaBean input = new DmpkScrivaniaVirtualeGetnavigatorescrivaniaBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);		
							
		DmpkScrivaniaVirtualeGetnavigatorescrivania getnavigatorescrivania = new DmpkScrivaniaVirtualeGetnavigatorescrivania();
		StoreResultBean<DmpkScrivaniaVirtualeGetnavigatorescrivaniaBean> output = getnavigatorescrivania.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);	
		
		String idUtenteModPec = output.getResultBean().getIdutentemgoout();
		if (output.getResultBean().getTreexmlout() != null){
				
			StringReader sr = new StringReader(output.getResultBean().getTreexmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																
		       		ScrivaniaTreeNodeBean node = new ScrivaniaTreeNodeBean();	    		        		
		       		node.setNroLivello(v.get(0) != null ? new BigDecimal(v.get(0)) : null); //colonna 1 dell'xml		        		
		       		node.setIdNode(v.get(1)); //colonna 2 dell'xml
		       		node.setNome(v.get(2)); //colonna 3 dell'xml   
		       		node.setTipo(v.get(3)); //colonna 4 dell'xml   
		       		node.setDettagli(v.get(4)); //colonna 5 dell'xml   
		       		node.setFlgEsplodiNodo(v.get(5)); //colonna 6 dell'xml
		       		node.setAzione(v.get(6)); //colonna 7 dell'xml		        		
		       		node.setParametri(v.get(7)); //colonna 8 dell'xml
		       		node.setParentId(v.get(8) != null ? v.get(8).toString() : null); //colonna 9 dell'xml	
		       		node.setCriteriAvanzati(v.get(9) != null ? v.get(9).toString() : null); //colonna 10 dell'xml
		       		node.setFlgMultiselezione(v.get(10) != null && "1".equals(v.get(10))); //colonna 11 dell'xml
		       		node.setCodSezione(v.get(11)); //colonna 12 dell'xml
		       		node.setFlgContenuti(v.get(12) != null && "1".equals(v.get(12))); //colonna 13 dell'xml
		       		node.setNroContenuti(v.get(13)); //colonna 14 dell'xml		       				       		
		       		node.setIdUtenteModPec(idUtenteModPec);		       		
		       		data.add(node);		        		
		   		}			
			}					
		}
		PaginatorBean<ScrivaniaTreeNodeBean> lPaginatorBean = new PaginatorBean<ScrivaniaTreeNodeBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}
	
	public ScrivaniaTreeNodeBean getNroContenutiNodo(ScrivaniaTreeNodeBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkScrivaniaVirtualeGetcontenutinodofuncBean input = new DmpkScrivaniaVirtualeGetcontenutinodofuncBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : loginBean.getIdUser());
		input.setIddominioin(loginBean.getSpecializzazioneBean() != null ? loginBean.getSpecializzazioneBean().getIdDominio() : null);
		input.setCinodoin(bean.getIdNode());
		input.setIdutentemgoin(bean.getIdUtenteModPec());
		
		DmpkScrivaniaVirtualeGetcontenutinodofunc getcontenutinodofunc = new DmpkScrivaniaVirtualeGetcontenutinodofunc();
		
		StoreResultBean<DmpkScrivaniaVirtualeGetcontenutinodofuncBean> output = getcontenutinodofunc.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);					
		
		String nroContenuti = output.getResultBean().getNrocontenutiout();
		
		bean.setNroContenuti(nroContenuti);
		
		return bean;		
	}
	
}
