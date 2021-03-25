package it.eng.auriga.ui.module.layout.server.iterAtti.datasource;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.AttoConFlussoWFXmlBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.List;

@Datasource(id = "LoadComboAttoConFlussoWFDataSource")
public class LoadComboAttoConFlussoWFDataSource extends AbstractFetchDataSource<AttoConFlussoWFXmlBean> {

	@Override
	public PaginatorBean<AttoConFlussoWFXmlBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
				
		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("ATTO_CON_FLUSSO_WF");
		bean.setFlgsolovldin(BigDecimal.ONE);
		bean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_SOLO_ASSEGNABILI|*|1");
		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> result = store.execute(getLocale(), lAurigaLoginBean, bean);
		bean = result.getResultBean();
		List<AttoConFlussoWFXmlBean> resultList = XmlListaUtility.recuperaLista(bean.getListaxmlout(), AttoConFlussoWFXmlBean.class);
		PaginatorBean<AttoConFlussoWFXmlBean> toReturn = new PaginatorBean<AttoConFlussoWFXmlBean>();
		toReturn.setStartRow(0);
		toReturn.setEndRow(resultList.size());
		toReturn.setData(resultList);
		toReturn.setTotalRows(resultList.size());
		return toReturn;
		
	}

	
}
