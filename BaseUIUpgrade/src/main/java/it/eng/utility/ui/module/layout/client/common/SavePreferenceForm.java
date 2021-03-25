package it.eng.utility.ui.module.layout.client.common;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class SavePreferenceForm extends DynamicForm{
	
	private final GWTRestDataSource optionDS;
	
	private final SavePreferenceWindow savePreferenceWindow;			
	private final ComboBoxItem preferenceNameComboBoxItem;
	private final ButtonItem savePreferencButtonItem;
	
	private boolean defaultValue;
	
	public SavePreferenceForm(Window window, GWTRestDataSource datasource, boolean defaultValue){
		
		this.savePreferenceWindow = (SavePreferenceWindow) window;
		this.optionDS = datasource;
		
		this.defaultValue = defaultValue;
		
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(120, 120);
		setCellPadding(5);
		setCanSubmit(true);
		
		preferenceNameComboBoxItem = new ComboBoxItem();
		preferenceNameComboBoxItem.setValueField("prefName");  
		preferenceNameComboBoxItem.setDisplayField("prefName");  
		preferenceNameComboBoxItem.setShowTitle(false);
		preferenceNameComboBoxItem.setCanEdit(true);
		preferenceNameComboBoxItem.setColSpan(2);
		preferenceNameComboBoxItem.setAlign(Alignment.CENTER);			
		preferenceNameComboBoxItem.setWidth(300);
		preferenceNameComboBoxItem.setRequired(true);
		preferenceNameComboBoxItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return (String) preferenceNameComboBoxItem.getValue();
			}
		});
				
		preferenceNameComboBoxItem.setOptionDataSource(optionDS);   					
						
		savePreferencButtonItem = new ButtonItem();
		savePreferencButtonItem.setTitle(I18NUtil.getMessages().savePreferenceButton_title());
		savePreferencButtonItem.setIcon("ok.png");
		savePreferencButtonItem.setIconHeight(16);
		savePreferencButtonItem.setIconWidth(16);
		savePreferencButtonItem.setColSpan(2);
		savePreferencButtonItem.setWidth(100);
		savePreferencButtonItem.setTop(20);
		savePreferencButtonItem.setAlign(Alignment.CENTER);
			
		savePreferencButtonItem.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				if(validate()) {
					savePreferenceWindow.savePreferenceAction((String) preferenceNameComboBoxItem.getValue());
				}
			}

		});
		 
		setFields(new FormItem[]{preferenceNameComboBoxItem, savePreferencButtonItem});
		setAlign(Alignment.CENTER);
		setTop(50);
				
	}
	
	public void setPreferenceName(String prefName) {
		preferenceNameComboBoxItem.setValue(prefName);		
	}
	
	public void setValue(final String value) {
		preferenceNameComboBoxItem.setValue(value);
		optionDS.fetchData(new Criteria(), new DSCallback() {   
            @Override  
            public void execute(DSResponse response, Object rawData, DSRequest request) {   
                Record[] data = response.getData();
                if(value == null || "".equals(value)) {
	                if(defaultValue) {
	                	boolean showDefault = true;
	                    if (data != null && data.length > 0) { 
	                    	for(Record record : data) {
	                    		if(record.getAttribute("prefName").equalsIgnoreCase("DEFAULT")) {
	                    			showDefault = false;
	                    			break;
	                    		}
	                    	}
	                    }
	                    if(showDefault) {
	                    	preferenceNameComboBoxItem.setValue("DEFAULT");
	                    }
	            	}
                }
            }   
        });   				
	}
}
