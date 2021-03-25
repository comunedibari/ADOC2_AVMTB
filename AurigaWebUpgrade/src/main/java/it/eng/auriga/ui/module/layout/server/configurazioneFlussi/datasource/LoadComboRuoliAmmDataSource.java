package it.eng.auriga.ui.module.layout.server.configurazioneFlussi.datasource;

import java.util.ArrayList;
import java.util.List;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboRuoliAmmDataSource")
public class LoadComboRuoliAmmDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		// Input
		DmpkLoadComboDmfn_load_comboBean inputParamComboBean = new DmpkLoadComboDmfn_load_comboBean();
		inputParamComboBean.setTipocomboin("RUOLO_AMM");

		inputParamComboBean.setAltriparametriin("INTERNAL_VALUE|*|CID");
		
		// Output
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), inputParamComboBean);

		String xmlLista = storeResult.getResultBean().getListaxmlout();

		List<SimpleKeyValueBean> resultList = new ArrayList<SimpleKeyValueBean>();

		List<XmlListaSimpleBean> listaXML = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);
		resultList = new ArrayList<SimpleKeyValueBean>();

		for (XmlListaSimpleBean lXmlListaSimpleBean : listaXML) {
			SimpleKeyValueBean lBean = new SimpleKeyValueBean();
			lBean.setKey(lXmlListaSimpleBean.getKey());
			lBean.setValue(lXmlListaSimpleBean.getValue());
			resultList.add(lBean);
		}

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(resultList);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(resultList.size());
		lPaginatorBean.setTotalRows(resultList.size());

		return lPaginatorBean;
	}

}
