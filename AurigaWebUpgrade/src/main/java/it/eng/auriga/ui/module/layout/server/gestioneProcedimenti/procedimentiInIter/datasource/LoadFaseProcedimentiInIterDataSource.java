package it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id="LoadFaseProcedimentiInIterDataSource")
public class LoadFaseProcedimentiInIterDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		String idProcessType = "";
		getExtraparams();
		if (criteria.getCriterionByFieldName("criteria")!=null){
			List<Map<String,String>> lList = (ArrayList<Map<String,String>>)criteria.getCriterionByFieldName("criteria").getValue();
			if (lList!=null){
				Map<String,String> lMap = lList.get(0);
				if (lMap!=null && lMap.get("fieldName").equals("idProcessType")){
					idProcessType = lMap.get("value");
				}
			}
		}

		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("FASI_WF");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("INTERNAL_VALUE|*|CID|*|ID_PROCESS_TYPE|*|" + idProcessType);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		if (lStoreResultBean.isInError() || xmlLista == null){
			return new PaginatorBean<SimpleKeyValueBean>(new ArrayList<SimpleKeyValueBean>());
		}
		List<SimpleKeyValueBean> lListResult = XmlUtility.recuperaListaSemplice(xmlLista);
		return new PaginatorBean<SimpleKeyValueBean>(lListResult);
	}

}
