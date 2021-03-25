package it.eng.utility.ui.module.layout.server.common;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.ApplicationConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

@Datasource(id="ApplicationConfiguratorService")
public class ServiceRestApplicationConfig extends AbstractServiceDataSource<LoginBean, ApplicationConfigBean>{
	
	@Override
	public ApplicationConfigBean call(LoginBean bean) throws Exception {
		
		ApplicationConfigBean lApplicationConfigurator = (ApplicationConfigBean)SpringAppContext.getContext().getBean("ApplicationConfigurator");
		return lApplicationConfigurator;
	}

}
