package it.eng.auriga.ui.module.layout.client.archivio;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public abstract class CambiaStatoPostDiscussionePopup extends Window {
	
	private CambiaStatoPostDiscussionePopup window;
	
	private DynamicForm form;
	protected SelectItem statoUdItem;
	protected HiddenItem flgGeneraFileUnioneItem;
	
	public CambiaStatoPostDiscussionePopup(){
			
		window = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(100);
		setWidth(450);	
		setKeepInParentRect(true);	
		
		setTitle("Cambia stato");
		setShowTitle(true);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(100, "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
				
		// Stato
		GWTRestDataSource statoUdDS = new GWTRestDataSource("LoadComboStatoPostDiscussioneDataSource", "key", FieldType.TEXT);
		
		statoUdItem = new SelectItem("statoUd", "Stato") {
			
			@Override
			public void onOptionClick(Record record) {
				flgGeneraFileUnioneItem.setValue(record.getAttribute("flgGeneraFileUnione"));						
			}
		};
		statoUdItem.setOptionDataSource(statoUdDS);  
		statoUdItem.setAutoFetchData(false);
		statoUdItem.setDisplayField("value");
		statoUdItem.setValueField("key");			
		statoUdItem.setWidth(300);
		statoUdItem.setWrapTitle(false);
		statoUdItem.setAllowEmptyValue(false);
		statoUdItem.setRedrawOnChange(true);
		statoUdItem.setClearable(false);
		statoUdItem.setStartRow(true);
		statoUdItem.setRequired(true);
		
		flgGeneraFileUnioneItem = new HiddenItem("flgGeneraFileUnione");		
		
		form.setFields(statoUdItem, flgGeneraFileUnioneItem);
		
		addItem(form);	
				
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {		
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {					
					final Record formRecord = new Record(form.getValues());
					onClickOkButton(formRecord, new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							window.markForDestroy();
						}
					});					
				}
			}
		});		
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		_buttons.setBackgroundColor(CustomDetail.backgroundColor);
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);
		
		setHeaderIcon("archivio/archiviaConcludi.png");
		
		draw();
	}
	
	public abstract void onClickOkButton(Record object, DSCallback callback);
	
}

