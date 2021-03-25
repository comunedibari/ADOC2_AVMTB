package it.eng.aurigamailbusiness.converters;

import it.eng.aurigamailbusiness.bean.MailboxInfoBean;
import it.eng.aurigamailbusiness.database.mail.MailboxInfo;
import it.eng.core.business.converter.IBeanPopulate;

public class MailboxInfoToMailboxInfoBean implements IBeanPopulate<MailboxInfo, MailboxInfoBean> {

	@Override
	public void populate(MailboxInfo src, MailboxInfoBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setIdMailbox(src.getId().getIdMailbox());
		}
	}

	@Override
	public void populateForUpdate(MailboxInfo src, MailboxInfoBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setIdMailbox(src.getId().getIdMailbox());
		}
	}

}
