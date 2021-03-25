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

@Datasource(id="LoadComboPrivilegiTipoRegistrazioneDataSource")
public class LoadComboPrivilegiTipoRegistrazioneDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboPrivilegiTipoRegistrazioneDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
				
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		
		// Inizializzo l'INPUT
		SimpleKeyValueBean listaPrivilegiValueMap1 = new SimpleKeyValueBean();
		listaPrivilegiValueMap1.setKey("A");
		listaPrivilegiValueMap1.setValue(MessageUtil.getValue(getLocale().getLanguage(), getSession(), "sub_profili_abilitazioni_TR_A"));
	
		lListResult.add(listaPrivilegiValueMap1);
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}
}