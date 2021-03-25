package it.eng.auriga.ui.module.layout.server.common;

import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "StopSearchDatasource")
public class StopSearchDatasource extends AbstractServiceDataSource<StopSearchBean, StopSearchBean> {

	@Override
	public StopSearchBean call(StopSearchBean bean)	throws Exception {
		return StopSearchUtility.stopSearch(bean, getLocale(), AurigaUserUtil.getLoginInfo(getSession()));
	}

}
