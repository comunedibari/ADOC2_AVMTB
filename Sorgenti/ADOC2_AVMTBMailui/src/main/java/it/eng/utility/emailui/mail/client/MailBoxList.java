package it.eng.utility.emailui.mail.client;

import it.eng.utility.emailui.core.client.datasource.GWTRestDataSource;

import com.smartgwt.client.widgets.grid.ListGrid;


/**
 * Lista della mailbox supportate
 * @author michele
 *
 */
public class MailBoxList extends ListGrid {

	final GWTRestDataSource mailboxdatasource= new GWTRestDataSource("MailboxDataSource");
	
	
	public MailBoxList() {
		setDataSource(mailboxdatasource);
			
	}
}