package it.eng.utility.ui.module.layout.server.common;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.config.FilterTypeConfigurator;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

@Datasource(id="FilterTypeConfiguratorService")
public class ServiceRestFilterTypeConfig extends AbstractServiceDataSource<LoginBean, FilterTypeConfigurator>{

	@Override
	public FilterTypeConfigurator call(LoginBean bean) throws Exception {
		
		FilterTypeConfigurator lFilterTypeConfigurator = (FilterTypeConfigurator) SpringAppContext.getContext().getBean("FilterTypeConfigurator"); 
		return lFilterTypeConfigurator;
	}

}
