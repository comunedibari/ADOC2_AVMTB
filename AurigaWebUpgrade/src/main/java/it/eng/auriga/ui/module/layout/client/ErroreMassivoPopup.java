package it.eng.auriga.ui.module.layout.client;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ErroreMassivoPopup extends ModalWindow {

	public ErroreMassivoPopup(String nomeEntita, String intestazionePrimaColonna, RecordList listaErrori, int numeroRecordSelezionati, int larghezzaPopup, int altezzaPopup) {
		this(nomeEntita, intestazionePrimaColonna, listaErrori, numeroRecordSelezionati, larghezzaPopup, altezzaPopup, null);
	}
	
	public ErroreMassivoPopup(String nomeEntita, String intestazionePrimaColonna, RecordList listaErrori, int numeroRecordSelezionati, int larghezzaPopup, int altezzaPopup, final ServiceCallback<Record> callback) {
		
		super(nomeEntita, true, false);
	
		setModalMaskOpacity(50);
		setWidth(larghezzaPopup);		
		setHeight(altezzaPopup);
		setKeepInParentRect(true);
		setTitle("Dettaglio errori operazione");
		setShowModalMask(true);
		setShowCloseButton(true);
		
		setShowMaximizeButton(true);
		setShowMinimizeButton(false);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		StaticTextItem messageItem = new StaticTextItem();
			
		messageItem.setTextAlign(Alignment.LEFT);
		
		messageItem.setValue("<span style=\"font-size:13;\">Sono andati in errore " + listaErrori.getLength() + " record su " + numeroRecordSelezionati +" . Di seguito il dettaglio: </span>");
		messageItem.setWidth(450);
		messageItem.setWrap(false);
		messageItem.setTop(15);
		messageItem.setShowTitle(false);
		
		DynamicForm form = new DynamicForm();
		form.setItemLayout(FormLayoutType.ABSOLUTE);
		form.setHeight("5%");
		form.setWidth("80%");
		form.setLayoutAlign(Alignment.CENTER);
		form.setItems(messageItem);
		form.setOverflow(Overflow.VISIBLE);
		
		ListGrid errorsGrid = new ListGrid(){
			
			@Override
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				return "font-size:12;padding-right:20px;padding-left:5px";
			}
		};

		errorsGrid.setLayoutAlign(Alignment.CENTER);
		errorsGrid.setHeight("30%");
		errorsGrid.setWidth("80%");
		errorsGrid.setShowAllRecords(true); 
		errorsGrid.setWrapCells(true);
		errorsGrid.setFixedRecordHeights(false); 
		
		ListGridField idError = new ListGridField("idError", intestazionePrimaColonna);
		idError.setAutoFitWidth(false);
		idError.setWidth("35%");
		idError.setAlign(Alignment.LEFT);
		idError.setWrap(true);

		ListGridField descrizione = new ListGridField("descrizione", "Descrizione errore");
		descrizione.setWidth("*");
		descrizione.setAlign(Alignment.LEFT);
		descrizione.setWrap(true);
		
		errorsGrid.setFields(idError, descrizione);
		errorsGrid.setData(listaErrori);
		errorsGrid.setWrapCells(true);
		
		IButton closeFinestraButton = new IButton();
		closeFinestraButton.setLayoutAlign(Alignment.CENTER);
		closeFinestraButton.setTitle("Chiudi");
		closeFinestraButton.setIcon("ok.png");
		closeFinestraButton.setIconHeight(16);
		closeFinestraButton.setIconWidth(16);
		closeFinestraButton.setWidth(100);
		closeFinestraButton.setBottom(20);
		
		closeFinestraButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				if(callback != null) {
					callback.execute(new Record());
				}
				markForDestroy();
			}
		});
		
		VLayout layout = new VLayout();  
		layout.setMembersMargin(10);
		layout.setPadding(10);
		layout.setHeight("90%");
		layout.setWidth100();
		layout.addMember(form);
		layout.addMember(errorsGrid);
		layout.addMember(closeFinestraButton);
		
		setBody(layout);
	}

}
