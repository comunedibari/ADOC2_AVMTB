package it.eng.auriga.ui.module.layout.server.common;

import it.eng.auriga.ui.module.layout.shared.bean.ParametriDBBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id="ParametriDBDataSource")
public class ParametriDBDataSource extends AbstractServiceDataSource<LoginBean, ParametriDBBean>{
	
	@Override
	public ParametriDBBean call(LoginBean bean) throws Exception {
		
		return ParametriDBUtil.getParametriDB(getSession());
	}

}