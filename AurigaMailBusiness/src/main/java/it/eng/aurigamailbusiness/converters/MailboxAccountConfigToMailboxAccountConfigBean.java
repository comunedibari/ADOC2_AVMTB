package it.eng.aurigamailbusiness.converters;

import it.eng.aurigamailbusiness.bean.MailboxAccountConfigBean;
import it.eng.aurigamailbusiness.database.mail.MailboxAccountConfig;
import it.eng.core.business.converter.IBeanPopulate;

public class MailboxAccountConfigToMailboxAccountConfigBean implements IBeanPopulate<MailboxAccountConfig, MailboxAccountConfigBean> {

	@Override
	public void populate(MailboxAccountConfig src, MailboxAccountConfigBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setIdAccount(src.getId().getIdAccount());
			dest.setConfigKey(src.getId().getConfigKey());
		}

	}

	@Override
	public void populateForUpdate(MailboxAccountConfig src, MailboxAccountConfigBean dest) throws Exception {

	}

}
