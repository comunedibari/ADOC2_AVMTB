package it.eng.auriga.ui.module.layout.client.gestioneatti.delibere;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class ModelloConvocazioneWindow extends ModalWindow {
	
	protected ModelloConvocazioneWindow _instance = this;
	
	protected DynamicForm mDynamicForm;
	protected CKEditorItem testoHtmlItem;
	
	private String testoHtml;
	
	public ModelloConvocazioneWindow(String testoHtml) { 
		
		super("modello_convocazione_window", false, false);
		
		this.testoHtml = testoHtml;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		setWidth(1000);
		setHeight(600);	
		setTop(50);  
		//setBackgroundColor("#FFFFFF");  
		setVisibility(Visibility.HIDDEN);  
		setAlign(Alignment.CENTER);  
		setAnimateTime(1200); // milliseconds
		setCanDragReposition(true);
		setCanDragResize(true);
		setKeepInParentRect(true);
		setIsModal(false);
		setAutoCenter(false);
		setShowCloseButton(false);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		setTitle("Modello convocazione");

		createMainLayout();
		
		layout.addMember(mDynamicForm);
		
		DetailToolStripButton saveButton = new DetailToolStripButton("Genera", "ok.png");
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
		
				if(testoHtmlItem != null && testoHtmlItem.getValue() != null &&
						!"".equalsIgnoreCase(testoHtmlItem.getValue())) {
					
					focusAfterGroup();
					manageOnOkButtonClick(testoHtmlItem.getValue());
					markForDestroy();	
				} else {
					/*
					 * METTO IL CARATTERE VUOTO PER EVITARE ERRORE DI unmarshal DI UNA SEZIONE CACHE VUOTA
					 */
					testoHtmlItem.setValue("<p> </p>");
					focusAfterGroup();
					manageOnOkButtonClick(testoHtmlItem.getValue());
					markForDestroy();	
				}
							
			}
		});
		
		DetailToolStripButton annullaButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
		annullaButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {				
				markForDestroy();
			}
		});
		
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(saveButton);
		_buttons.addMember(annullaButton);
		
		portletLayout.addMember(_buttons);
		
		addItem(portletLayout);
		
		setIcon("protocollazione/generaDaModello.png");	
	}
	
	protected void createMainLayout() {
		
		mDynamicForm = new DynamicForm();													
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setNumCols(5);
		mDynamicForm.setColWidths("*","1","1","1","1");		
		mDynamicForm.setCellPadding(5);		
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setBorder("1px");
		
		testoHtmlItem = new CKEditorItem("testoModello", -1, "STANDARD", "96%", -1, "", false, true);
		testoHtmlItem.setTitle("");
		testoHtmlItem.setWidth("100%");
		testoHtmlItem.setHeight(600);
		testoHtmlItem.setShowTitle(false);
		testoHtmlItem.setStartRow(true);
		testoHtmlItem.setCanEdit(true);
		testoHtmlItem.setValue(testoHtml);
		
		mDynamicForm.setItems(testoHtmlItem);
	}
	
	public void manageOnOkButtonClick(String values) {

	}
	
}