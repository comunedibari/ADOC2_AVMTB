package it.eng.utility.ui.module.layout.server.common;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterPrivilegiContainer;
import it.eng.utility.ui.module.layout.shared.bean.FilterPrivilegiUtil;

@Datasource(id="ServiceRestPrivilegiFilterUtil")
public class ServiceRestPrivilegiFilterUtil extends AbstractServiceDataSource<LoginBean, FilterPrivilegiContainer>{

	@Override
	public FilterPrivilegiContainer call(LoginBean bean) throws Exception {
		FilterPrivilegiUtil lFilterPrivilegiUtil = (FilterPrivilegiUtil)SpringAppContext.getContext().getBean("configurePrivilegi");
		
		return lFilterPrivilegiUtil.getContainer();
	}

}
