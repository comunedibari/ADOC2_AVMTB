package it.eng.utility.emailui.mail.server.datasource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.emailui.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.emailui.core.server.datasource.annotation.Datasource;
import it.eng.utility.emailui.mail.shared.bean.OperationBean;

@Datasource(id = "OperationDelete")
public class OperationDelete extends AbstractServiceDataSource<OperationBean, OperationBean> {

	private static Logger logger = LogManager.getLogger(OperationDelete.class);

	@Override
	public OperationBean call(OperationBean bean) throws Exception {

		try {
			FactoryMailBusiness.getInstance().deleteMailBoxOperation(bean.getOperationnum(), bean.getIdmailbox());
		} catch (Exception e) {
			logger.error(e);
		}
		return bean;
	}

}
