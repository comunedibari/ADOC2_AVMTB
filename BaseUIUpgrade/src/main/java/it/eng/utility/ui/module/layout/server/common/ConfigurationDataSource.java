package it.eng.utility.ui.module.layout.server.common;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.ui.config.FilterTypeConfigurator;
import it.eng.utility.ui.config.ListConfigurator;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.AllFilterSelectBean;
import it.eng.utility.ui.module.layout.shared.bean.AppletParameterConfigurator;
import it.eng.utility.ui.module.layout.shared.bean.ApplicationConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.ConfigurationBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;


@Datasource(id="ConfigurationDataSource")
public class ConfigurationDataSource extends AbstractServiceDataSource<LoginBean, ConfigurationBean>{
	
	@Override
	public ConfigurationBean call(LoginBean bean) throws Exception {
		
		ConfigurationBean config = new ConfigurationBean();
		config.setListConfig((ListConfigurator) SpringAppContext.getContext().getBean("ListConfigurator")); 
		config.setApplicationConfig((ApplicationConfigBean)SpringAppContext.getContext().getBean("ApplicationConfigurator"));
		config.setGenericConfig((GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator"));		
		config.setFilterTypeConfig((FilterTypeConfigurator) SpringAppContext.getContext().getBean("FilterTypeConfigurator")); 
		config.setFilterConfig(getXmlFiltriTradotto());		
		config.setAppletConfig((AppletParameterConfigurator)SpringAppContext.getContext().getBean("AppletParameterConfigurator"));				
		AllFilterSelectBean allFilterSelectBean = new AllFilterSelectBean();
		allFilterSelectBean.setSelects(SpringAppContext.getContext().getBeansOfType(FilterSelectBean.class));
		config.setFilterSelectConfig(allFilterSelectBean);		
		DocumentConfiguration documentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
		config.setAlgoritmoImpronta(documentConfiguration.getAlgoritmo().value());
		config.setEncoding(documentConfiguration.getEncoding().value());
		return config;
	}

}
