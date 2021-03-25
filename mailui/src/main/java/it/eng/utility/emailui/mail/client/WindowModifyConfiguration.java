package it.eng.utility.emailui.mail.client;

import java.util.List;

import it.eng.utility.emailui.core.client.callback.ServiceCallback;
import it.eng.utility.emailui.core.client.datasource.GWTRestService;
import it.eng.utility.emailui.core.client.util.JSONUtil;
import it.eng.utility.emailui.mail.shared.bean.OperationBean;
import it.eng.utility.emailui.mail.shared.bean.OperationTypeBean;
import it.eng.utility.emailui.mail.shared.bean.OperationTypeConfigBean;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class WindowModifyConfiguration extends Window {

	final GWTRestService<OperationBean,OperationBean> mailoperationconfigupdate = new GWTRestService<OperationBean,OperationBean>("OperationConfigUpdate");
	
	public WindowModifyConfiguration(OperationTypeBean operationtype,final OperationBean operationbean,final List<OperationTypeBean> operationtypelist,final String idmailbox) {
		
		setTitle("Modifica Configurazione");
		setAutoSize(true);
		setShowModalMask(true);
		setIsModal(true);
		setAutoCenter(true);
		
		VLayout layout = new VLayout();
		
		final DynamicForm form = new DynamicForm();
	
		form.setWrapItemTitles(false);
		
		FormItem[] items = new FormItem[operationtype.getConfigs().size()];
		int i=0;
		for(OperationTypeConfigBean conf:operationtype.getConfigs()){
			
			FormItem item = new FormItem(conf.getKey());
			item.setType(conf.getType());
			item.setPrompt(conf.getDescription());
			item.setTitle(conf.getTitle());
			item.setWidth(300);
			items[i++] = item;
		}
						
		form.setFields(items);
			
		layout.addMember(form);
				
		//Setto i valori dal bean
		for(OperationTypeConfigBean config:operationbean.getConfiguration()){
			form.setValue(config.getKey(), config.getValue());
		}
		
		
		Button button = new Button("Salva");
		//Gestione del bottone di salvataggio
		button.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				//Risetto i valori
				for(OperationTypeConfigBean config:operationbean.getConfiguration()){
					config.setValue(form.getValueAsString(config.getKey()));			
				}
				operationbean.setIdmailbox(idmailbox);
						
				mailoperationconfigupdate.call(operationbean, JSONUtil.OperationBeanJsonWriter, JSONUtil.OperationBeanJsonReader, new ServiceCallback<OperationBean>() {
					@Override
					public void execute(OperationBean bean) {
						WindowModifyConfiguration.this.destroy();									
					}
				});
			}
		});
		
		layout.addMember(button);
		
		layout.draw();
		
		addItem(layout);
		
	}
}