package it.eng.utility.ui.module.layout.server.common;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.AppletParameterConfigurator;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

@Datasource(id = "AppletConfiguratorService")
public class AppletConfiguratorService extends AbstractServiceDataSource<LoginBean, AppletParameterConfigurator>{
	
	@Override
	public AppletParameterConfigurator call(LoginBean bean) throws Exception {
		
		AppletParameterConfigurator lAppletParameterConfigurator = (AppletParameterConfigurator)SpringAppContext.getContext().getBean("AppletParameterConfigurator");
		return lAppletParameterConfigurator;
	}

}

