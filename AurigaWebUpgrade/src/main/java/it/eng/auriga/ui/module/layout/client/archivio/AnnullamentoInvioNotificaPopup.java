package it.eng.auriga.ui.module.layout.client.archivio;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class AnnullamentoInvioNotificaPopup extends ModalWindow {
	
	protected AnnullamentoInvioNotificaPopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected HiddenItem flgUdFolderItem;
	protected HiddenItem idUdFolderItem;
	protected HiddenItem flgInvioNotificaItem;
	protected HiddenItem idInvioNotificaItem;
	protected TextAreaItem motivoItem;	
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public AnnullamentoInvioNotificaPopup(Record pListRecord){
		this(pListRecord, null);
	}
	
	public AnnullamentoInvioNotificaPopup(Record pListRecord, String pTitle){
		
		super("annullamento_invio_notifica", true);
		
		_window = this;
		
		setAutoCenter(true);
		
		if(pTitle != null && !"".equals(pTitle)) {
			setTitle(pTitle);
		} else {			
			setTitle("Annullamento assegnazione");
		}

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);							
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths(120,1,1,1,1,1,"*","*");		
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);		
		
		flgUdFolderItem = new HiddenItem("flgUdFolder");
		idUdFolderItem = new HiddenItem("idUdFolder");
		flgInvioNotificaItem = new HiddenItem("flgInvioNotifica");
		idInvioNotificaItem = new HiddenItem("idInvioNotifica");		
		
		motivoItem = new TextAreaItem("motivo", "Motivo");
		motivoItem.setStartRow(true);
		motivoItem.setLength(4000);
		motivoItem.setHeight(40);
		motivoItem.setColSpan(8);
		motivoItem.setWidth(650);	
		
		_form.setFields(new FormItem[]{flgUdFolderItem, idUdFolderItem, flgInvioNotificaItem, idInvioNotificaItem, motivoItem});
		
		_form.editRecord(pListRecord);
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				onClickOkButton(new DSCallback() {			
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						_window.markForDestroy();
					}
				});						
			}
		});
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				_window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		 		
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
		
		layout.addMember(_form);
		
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
				
		setBody(portletLayout);
		
		setIcon("buttons/delete.png");
	}

	public abstract void onClickOkButton(DSCallback callback);
	
}
