package it.eng.auriga.ui.module.layout.server.archivio.datasource;

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
@Datasource(id = "FlgRestringiRicercaASelectDataSource")
public class FlgRestringiRicercaASelectDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String tipoNodo = StringUtils.isNotBlank(getExtraparams().get("tipoNodo")) ? getExtraparams().get("tipoNodo") : "";		
		
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		SimpleKeyValueBean lSimpleKeyValueBeanFS = new SimpleKeyValueBean();
		lSimpleKeyValueBeanFS.setKey("FS");
		lSimpleKeyValueBeanFS.setValue("fascicoli e sotto-fascicoli");
		
		SimpleKeyValueBean lSimpleKeyValueBeanF = new SimpleKeyValueBean();
		lSimpleKeyValueBeanF.setKey("F");
		lSimpleKeyValueBeanF.setValue("fascicoli");
		
		SimpleKeyValueBean lSimpleKeyValueBeanS = new SimpleKeyValueBean();
		lSimpleKeyValueBeanS.setKey("S");
		lSimpleKeyValueBeanS.setValue("sotto-fascicoli");
		
		SimpleKeyValueBean lSimpleKeyValueBeanU = new SimpleKeyValueBean();
		lSimpleKeyValueBeanU.setKey("U");
		lSimpleKeyValueBeanU.setValue("documenti");
		
		if(tipoNodo == null || !"F".equals(tipoNodo)) {
			lListResult.add(lSimpleKeyValueBeanFS);
		}
		lListResult.add(lSimpleKeyValueBeanF);
		lListResult.add(lSimpleKeyValueBeanS);
		if(tipoNodo == null || !"F".equals(tipoNodo)) {
			lListResult.add(lSimpleKeyValueBeanU);
		}		
				
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}

}
