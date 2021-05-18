package it.eng.utility.emailui.mail.server.datasource;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.aurigamailbusiness.bean.MailboxBean;
import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.emailui.core.server.bean.AdvancedCriteria;
import it.eng.utility.emailui.core.server.bean.OrderByBean;
import it.eng.utility.emailui.core.server.bean.PaginatorBean;
import it.eng.utility.emailui.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.emailui.core.server.datasource.annotation.Datasource;

@Datasource(id = "MailboxDataSource")
public class MailboxDataSource extends AbstractFetchDataSource<MailboxBean> {

	@Override
	public PaginatorBean<MailboxBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		PaginatorBean<MailboxBean> paginator = new PaginatorBean<>();
		// Recupero tutte le mailbox configurate con stato attivo o disattivo
		List<Mailbox> lista = FactoryMailBusiness.getInstance().getAllMailBoxForConnectId();
		paginator.setEndRow(0);
		paginator.setTotalRows(0);
		if (lista != null) {
			for (Mailbox mailbox : lista) {
				if (StringUtils.isBlank(mailbox.getName())) {
					mailbox.setName(mailbox.getIdMailbox());
				}
				MailboxBean bean = new MailboxBean();
				bean.setIdMailbox(mailbox.getIdMailbox());
				bean.setDescription(mailbox.getDescription());
				// Visualizzo anche lo stato nel nome
				bean.setName(mailbox.getName() + " - " + mailbox.getStatus());
				bean.setStatus(mailbox.getStatus());
				//bean.setProcessFlow(mailbox.getProcessFlow());
				paginator.addRecord(bean);
			}
			paginator.setEndRow(paginator.getData().size());
			paginator.setTotalRows(paginator.getData().size());
		}
		paginator.setStartRow(0);
		return paginator;
	}
}
