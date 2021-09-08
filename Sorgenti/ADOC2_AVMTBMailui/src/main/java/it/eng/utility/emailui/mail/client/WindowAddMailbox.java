package it.eng.utility.emailui.mail.client;

import it.eng.utility.emailui.core.client.callback.ServiceCallback;
import it.eng.utility.emailui.core.client.datasource.GWTRestService;
import it.eng.utility.emailui.core.client.util.JSONUtil;
import it.eng.utility.emailui.mail.shared.bean.MailboxBean;



import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class WindowAddMailbox extends Window {

	final GWTRestService<MailboxBean,MailboxBean> addmailboxdatasource= new GWTRestService<MailboxBean,MailboxBean>("MailBoxAdd");
	
	public WindowAddMailbox() {
	
		setTitle("Nuova Mailbox");
		setSize("400", "400");
		setShowModalMask(true);
		setIsModal(true);
		setAutoCenter(true);
			
		Button button = new Button("Salva");

    	final DynamicForm form = new DynamicForm();
    	
    	TextItem mailboxname = new TextItem("name","Name");
    	TextItem account = new TextItem("account","Account");
    	form.setFields(mailboxname,account);
    	   	
		//Gestione del bottone di salvataggio
		button.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				MailboxBean bean = new MailboxBean();
				bean.setName(form.getValueAsString("name"));
				bean.setAccountname(form.getValueAsString("account"));
								
				addmailboxdatasource.call(bean, JSONUtil.MailboxBeanJsonWriter, JSONUtil.MailboxBeanJsonReader, new ServiceCallback<MailboxBean>() {
					
					@Override
					public void execute(MailboxBean bean) {
						SC.warn("Mailbox creata con successo! Configurare i parametri mancanti a database!");
						WindowAddMailbox.this.markForDestroy();
					}
				});
				

			}
		});		
		addItem(form);
		addItem(button);
	}	
}