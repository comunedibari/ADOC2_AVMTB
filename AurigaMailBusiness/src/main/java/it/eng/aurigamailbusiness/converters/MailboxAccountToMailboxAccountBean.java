package it.eng.aurigamailbusiness.converters;

import it.eng.aurigamailbusiness.bean.MailboxAccountBean;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
import it.eng.core.business.converter.IBeanPopulate;

public class MailboxAccountToMailboxAccountBean implements IBeanPopulate<MailboxAccount, MailboxAccountBean> {

	@Override
	public void populate(MailboxAccount src, MailboxAccountBean dest) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void populateForUpdate(MailboxAccount src, MailboxAccountBean dest) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
