package it.eng.utility.ui.module.layout.server.common;

import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

@Datasource(id="LogoutDataSource")
public class LogoutDataSource extends AbstractServiceDataSource<LoginBean, LoginBean>{

	@Override
	public LoginBean call(LoginBean bean) throws Exception {
		getSession().invalidate();		
		return new LoginBean();
	}

	
}
