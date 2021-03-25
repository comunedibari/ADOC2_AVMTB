package it.eng.utility.ui.module.layout.server.common;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.config.ListConfigurator;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

@Datasource(id="ListConfiguratorService")
public class ServiceRestListConfig extends AbstractServiceDataSource<LoginBean, ListConfigurator>{
	
	@Override
	public ListConfigurator call(LoginBean bean) throws Exception {
		
		return (ListConfigurator) SpringAppContext.getContext().getBean("ListConfigurator");
	}

}