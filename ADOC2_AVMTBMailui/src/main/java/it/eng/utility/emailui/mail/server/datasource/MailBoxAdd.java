package it.eng.utility.emailui.mail.server.datasource;

import it.eng.utility.database.DatabaseInitUtil;
import it.eng.utility.emailui.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.emailui.core.server.datasource.annotation.Datasource;
import it.eng.utility.emailui.mail.shared.bean.MailboxBean;

@Datasource(id = "MailBoxAdd")
public class MailBoxAdd extends AbstractServiceDataSource<MailboxBean, MailboxBean> {

	@Override
	public MailboxBean call(MailboxBean bean) throws Exception {
		// Creo la nuova mailbox
		DatabaseInitUtil.createMailboxTemplate(bean.getName(), bean.getAccountname());
		return bean;
	}
}