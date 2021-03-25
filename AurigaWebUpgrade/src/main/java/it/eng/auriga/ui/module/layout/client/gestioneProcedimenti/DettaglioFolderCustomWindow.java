package it.eng.auriga.ui.module.layout.client.gestioneProcedimenti;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.archivio.FolderCustomDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class DettaglioFolderCustomWindow extends ModalWindow {

	private DettaglioFolderCustomWindow _window;
	
	private CustomDetail detail;
	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton editButton;
	protected DetailToolStripButton saveButton;
	protected DetailToolStripButton reloadDetailButton;
	protected DetailToolStripButton undoButton;
	
	protected String mode;	
	
	protected String estremiProcedimento;	
	
	public DettaglioFolderCustomWindow(final Record record) {
			
		super("dettagliofoldercustom", true);
		
		_window = this;
		
		estremiProcedimento = getTipoEstremiRecord(record);
		
		String title = "Dettaglio cartella "+ estremiProcedimento;
		
	    setTitle(title);
				
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);	
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT); 			  		
		detail = new FolderCustomDetail("dettagliofoldercustom") {
			
			@Override
			protected void manageReloadTask() {
				
				reload(new DSCallback() {		
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						taskItem.setCanEdit(false);
					}
				});		
			}
		};
		detail.viewMode();
		detail.editRecord(record);
		detail.setHeight100();
		detail.setDataSource(lGwtRestDataSource);
				
		editButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		editButton.addClickHandler(new ClickHandler() { 
			
			@Override
			public void onClick(ClickEvent event) {   
				editMode();        		
			}   
		}); 

		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() { 
			
			@Override
			public void onClick(ClickEvent event) { 
				onSaveButtonClick();				
			}   
		}); 		

		reloadDetailButton = new DetailToolStripButton(I18NUtil.getMessages().reloadDetailButton_prompt(), "buttons/reloadDetail.png");
		reloadDetailButton.addClickHandler(new ClickHandler() {  
			
			@Override
			public void onClick(ClickEvent event) {  
				
				reload(new DSCallback() {					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						editMode();
					}
				});				
			}   
		}); 

		undoButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
		undoButton.addClickHandler(new ClickHandler() {  
			
			@Override
			public void onClick(ClickEvent event) {   
				
				reload(new DSCallback() {					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						viewMode();				
					}
				});				
			}   
		}); 
		
		detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(editButton);
		detailToolStrip.addButton(saveButton);
		detailToolStrip.addButton(reloadDetailButton);
		detailToolStrip.addButton(undoButton);		
		
		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(detail , detailToolStrip);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);				
		
		viewMode();
		
        setIcon("tree/folder.png");              
	}
	
	public void onSaveButtonClick() {
		final Record record = new Record(detail.getValuesManager().getValues());
		if(detail.validate()) {
			DSOperationType saveOperationType = detail.getValuesManager().getSaveOperationType();		
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			DSCallback callback = new DSCallback() {	
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						try {
							reload(new DSCallback() {		
								
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									viewMode();		
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(estremiProcedimento), "", MessageType.INFO));								
								}
							});						
						} catch(Exception e) {
							Layout.hideWaitPopup();
						}	
					} else {
						Layout.hideWaitPopup();
					}
				}
			};
			try {
				if((saveOperationType!=null && saveOperationType.equals(DSOperationType.ADD)) || record.getAttribute("idUdFolder")==null || record.getAttribute("idUdFolder").equals("")) {			
					detail.getDataSource().addData(record, callback);
				} else {
					detail.getDataSource().updateData(record, callback);
				}
			} catch(Exception e) {
				Layout.hideWaitPopup();
			}
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}

	public void reload(final DSCallback callback) {
		if(this.mode.equals("new")) {
			detail.editNewRecord();
			detail.getValuesManager().clearErrors(true);
		} else {
			Record record = new Record(detail.getValuesManager().getValues());			
			((GWTRestDataSource)detail.getDataSource()).getData(record, new DSCallback() {	
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						detail.editRecord(record);	
						detail.getValuesManager().clearErrors(true);
						callback.execute(response, null, new DSRequest());
					} 				
				}
			});	
		}		
	}
	
	public void newMode() {
		this.mode = "new";
		detail.setCanEdit(true);		
		editButton.hide();
		saveButton.show();
		reloadDetailButton.hide();
		undoButton.hide();	

		((FolderCustomDetail) detail).newMode();			
	}

	public void viewMode() {
		this.mode = "view";
		detail.setCanEdit(false);			
		saveButton.hide();
		reloadDetailButton.hide();
		undoButton.hide();		
		editButton.show();
		Record record = new Record(detail.getValuesManager().getValues());
		
		((FolderCustomDetail) detail).viewMode();
		if(record.getAttributeAsBoolean("abilModificaDati")) {
			editButton.show();
		} else {
			editButton.hide();
		}			
	}

	public void editMode() {
		this.mode = "edit";
		detail.setCanEdit(true);		
		editButton.hide();
		saveButton.show();
		reloadDetailButton.show();
		undoButton.show();

		((FolderCustomDetail) detail).editMode();
	}
	
	public void popolaForm(Record record) {
		detail.editRecord(record);
		detail.refreshTabIndex();	
	}
	
	@Override
	public void manageOnCloseClick() {
		
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
	}
	
	public String getTipoEstremiRecord(Record record) {
		String estremi = "";
		if (record.getAttributeAsString("percorsoFolderApp") != null && !"".equals(record.getAttributeAsString("percorsoFolderApp"))) {
			estremi += record.getAttributeAsString("percorsoFolderApp") + "/";
		}
		if (record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
			estremi += record.getAttributeAsString("nome");
		}
		return estremi;
	}
}