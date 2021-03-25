package it.eng.auriga.ui.module.layout.client.pratiche;

import java.util.Date;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class IntervalloDatePopup extends ModalWindow {

	protected IntervalloDatePopup instance;
	protected DynamicForm form;	
	protected DateItem dataDaItem;
	protected DateItem dataAItem;

	public IntervalloDatePopup(String nomeEntita, String title){
		
		super(nomeEntita, true);
		
		instance = this;
		
		setTitle(title);
		
		setWidth(350);
		setHeight(120);
		
		setAutoCenter(true);

		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);    
				
		form = new DynamicForm() {
			@Override
			public boolean validate() {
				
				if(dataDaItem.getValue() == null && dataAItem.getValue() == null) {
					return false;
				}
				return true;
			}
		};
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(8);
		form.setColWidths(120,1,1,1,1,1,"*","*");		
		form.setCellPadding(5);
		form.setWrapItemTitles(false);					
		
		dataDaItem = new DateItem();
		dataDaItem.setTitle("Dal");
		dataDaItem.setStartRow(true);
		dataDaItem.setEndRow(false);
		
		dataAItem = new DateItem();
		dataAItem.setTitle("al");
		dataAItem.setStartRow(false);
		dataAItem.setEndRow(true);
		
		form.setFields(new FormItem[]{dataDaItem, dataAItem});
						
		Button button = new Button("Ok");
		button.setIcon("ok.png");
		button.setIconSize(16); 
		button.setAutoFit(false);
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(form.validate()) {
					manageOnClickOkButton(dataDaItem.getValueAsDate(), dataAItem.getValueAsDate());
					markForDestroy();
				} else {
					Layout.addMessage(new MessageBean("Selezionare almeno una data", "", MessageType.ERROR));
				}
			}
		});
				
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(button);
		 		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(form);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
				
		setIcon("blank.png");
	}	

	public abstract void manageOnClickOkButton(Date dataDa, Date dataA);
	
}
