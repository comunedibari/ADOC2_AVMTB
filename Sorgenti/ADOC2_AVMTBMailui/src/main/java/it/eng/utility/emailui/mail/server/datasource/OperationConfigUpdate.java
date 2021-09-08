package it.eng.utility.emailui.mail.server.datasource;

import java.util.HashMap;
import java.util.List;

import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.emailui.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.emailui.core.server.datasource.annotation.Datasource;
import it.eng.utility.emailui.mail.shared.bean.OperationBean;
import it.eng.utility.emailui.mail.shared.bean.OperationTypeConfigBean;

@Datasource(id="OperationConfigUpdate")
public class OperationConfigUpdate extends AbstractServiceDataSource<OperationBean,OperationBean>{

		
	@Override
	public OperationBean call(OperationBean bean) throws Exception {
				
		List<OperationTypeConfigBean> configbeans =  bean.getConfiguration();
		HashMap<String,String> mappa = new HashMap<>();
		if(configbeans!=null){
			for(OperationTypeConfigBean config:configbeans) { 
			
				mappa.put(config.getKey(), config.getValue());	
				
			}	
		}
		FactoryMailBusiness.getInstance().updateMailBoxOperation(bean.getOperationnum(), bean.getIdmailbox() ,mappa);
		return bean;
	}
		
}
