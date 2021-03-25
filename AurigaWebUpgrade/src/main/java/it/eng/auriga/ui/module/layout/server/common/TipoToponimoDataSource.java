package it.eng.auriga.ui.module.layout.server.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "TipoToponimoDataSource")
public class TipoToponimoDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(TipoToponimoDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {		
		
		String rowId = StringUtils.isNotBlank(getExtraparams().get("rowId")) ? getExtraparams().get("rowId") : "";
		String toponimo = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("rowId")) {
					rowId = (String) criterion.getValue();
				}
				else if(criterion.getFieldName().equals("value")) {
					toponimo = (String) criterion.getValue();
				}
			}
		}
		
		if(toponimo.length()< 3)
			toponimo = "";
		
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("GENERICO");		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		lDmpkLoadComboDmfn_load_comboBean.setPkrecin(null);
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("NOME_TABELLA|*|DMT_RUBRICA_SOGGETTI|*|NOME_COL_TABELLA|*|INDIRIZZI_LUOGHI.TIPO_TOPONIMO|*|ROWID|*|" + rowId + "|*|STR_IN_DES|*|" + toponimo + "|*|ID_USER_LAVORO|*|"  + idUserLavoro);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			List<XmlListaSimpleBean> lista = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);
			boolean obbligatorio = StringUtils.isNotBlank(getExtraparams().get("obbligatorio")) && new Boolean(getExtraparams().get("obbligatorio"));
					
			for (XmlListaSimpleBean lRiga : lista){
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				String colonna1 = lRiga.getKey();
				lSimpleKeyValueBean.setKey(colonna1.substring(colonna1.indexOf(";") + 1));
				lSimpleKeyValueBean.setValue(lRiga.getValue());
				if(obbligatorio) {
					lSimpleKeyValueBean.setDisplayValue(FrontendUtil.getRequiredFormItemTitle(lRiga.getValue()));					
				} else {
					lSimpleKeyValueBean.setDisplayValue(lRiga.getValue());
				}
				lListResult.add(lSimpleKeyValueBean);
			}	
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}

}
