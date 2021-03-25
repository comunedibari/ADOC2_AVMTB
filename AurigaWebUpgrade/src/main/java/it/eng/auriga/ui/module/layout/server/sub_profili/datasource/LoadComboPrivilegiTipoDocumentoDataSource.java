package it.eng.auriga.ui.module.layout.server.sub_profili.datasource;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.utility.MessageUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

/**
 * 
 * @author OTTAVIO PASSALACQUA
 *
 */

@Datasource(id="LoadComboPrivilegiTipoDocumentoDataSource")
public class LoadComboPrivilegiTipoDocumentoDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboPrivilegiTipoDocumentoDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
				
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		
		// Inizializzo l'INPUT
		SimpleKeyValueBean listaPrivilegiValueMap1 = new SimpleKeyValueBean();
		listaPrivilegiValueMap1.setKey("A");
		listaPrivilegiValueMap1.setValue(MessageUtil.getValue(getLocale().getLanguage(), getSession(), "sub_profili_abilitazioni_TD_A_value"));
		
		
		SimpleKeyValueBean listaPrivilegiValueMap2 = new SimpleKeyValueBean();
		listaPrivilegiValueMap2.setKey("F");
		listaPrivilegiValueMap2.setValue(MessageUtil.getValue(getLocale().getLanguage(), getSession(), "sub_profili_abilitazioni_TD_F_value"));
		
		SimpleKeyValueBean listaPrivilegiValueMap3 = new SimpleKeyValueBean();
		listaPrivilegiValueMap3.setKey("P");
		listaPrivilegiValueMap3.setValue(MessageUtil.getValue(getLocale().getLanguage(), getSession(), "sub_profili_abilitazioni_TD_P_value"));
		
		
		lListResult.add(listaPrivilegiValueMap1);
		lListResult.add(listaPrivilegiValueMap2);
		lListResult.add(listaPrivilegiValueMap3);
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}
}