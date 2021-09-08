package it.eng.utility.emailui.mail.server.datasource;

import it.eng.utility.database.DatabaseInitUtil;
import it.eng.utility.email.scheduler.MailScheduler;
import it.eng.utility.emailui.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.emailui.core.server.datasource.annotation.Datasource;
import it.eng.utility.emailui.mail.shared.bean.OperationDatabaseBean;

@Datasource(id="OperationDatabase")
public class OperationDatabase extends AbstractServiceDataSource<OperationDatabaseBean,OperationDatabaseBean>{

		
	@Override
	public OperationDatabaseBean call(OperationDatabaseBean bean) throws Exception {
		
		if(bean.getOperation().equals("STATUS")) {
			bean.setIscreate(DatabaseInitUtil.isDatabaseInitialize());
			if(!bean.getIscreate()){
				bean.setMessage("Le tabelle sul database "+DatabaseInitUtil.getConfigurationProperty("hibernate.connection.url")+"non esistono! Eseguire la creazione?");
			}
		}else if(bean.getOperation().equals("CREATE")) {
			DatabaseInitUtil.dropCreateTabels();
			bean.setIscreate(true);
			
			//Inizializzo lo scheduler
			MailScheduler.initialize();
			
		}		
		
		return bean;
	}
		
}
