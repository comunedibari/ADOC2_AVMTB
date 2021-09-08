package it.eng.utility.emailui.mail.server.datasource;


import it.eng.utility.email.bean.MailUiConfigurator;
import it.eng.utility.emailui.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.emailui.core.server.datasource.annotation.Datasource;
import it.eng.utility.emailui.mail.shared.bean.MailconnectConfigBean;


@Datasource(id="MailConnectConfigDatasource")
public class MailConnectConfigDataSource extends AbstractServiceDataSource<MailconnectConfigBean, MailconnectConfigBean> {

	@Override
	public MailconnectConfigBean call(MailconnectConfigBean bean) throws Exception {
		bean = new MailconnectConfigBean();
		bean.setIdMailbox(MailUiConfigurator.getMailConnectId());
		bean.setConsoleType(MailUiConfigurator.getConsoleType());
		return bean;
	}
}


