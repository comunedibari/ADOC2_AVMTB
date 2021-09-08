package it.eng.utility.emailui.mail.server.datasource;

import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.emailui.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.emailui.core.server.datasource.annotation.Datasource;
import it.eng.utility.emailui.mail.shared.bean.MailCanStartBean;

@Datasource(id = "MailCanStartDatasource")
public class MailCanStartDatasource extends AbstractServiceDataSource<MailCanStartBean, MailCanStartBean> {

	@Override
	public MailCanStartBean call(MailCanStartBean bean) throws Exception {
		bean = new MailCanStartBean();
		// Verifica tramite il metodo noOrAllMailBoxesAreIdConfigured che il mailconnect id sia configurato per tutte le caselle
		// Nn si possono avere situazioni ibride
		bean.setCanStart(FactoryMailBusiness.getInstance().checkAllMailBoxesAreIdConfigured());
		return bean;
	}

}
