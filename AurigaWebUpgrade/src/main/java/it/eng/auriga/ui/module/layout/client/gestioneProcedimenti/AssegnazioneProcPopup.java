package it.eng.auriga.ui.module.layout.client.gestioneProcedimenti;

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
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class AssegnazioneProcPopup extends ModalWindow {
	
	protected AssegnazioneProcPopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected IstruttoreProcItem istruttoreProcItem;
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public AssegnazioneProcPopup(Record pListRecord){
		
		super("assegnazione_proc", true);
		
		_window = this;
		
		String estremiProcedimento = (pListRecord != null && pListRecord.getAttribute("estremiProcedimento") != null) ? pListRecord.getAttribute("estremiProcedimento") : "";
		String title = null;
		if(!"".equals(estremiProcedimento)) {
			title = "Compila dati assegnazione " + estremiProcedimento;
		} else {
			title = "Compila dati assegnazione";
		}
		
		setTitle(title);
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(5);
		_form.setColWidths(120,"*","*","*","*");		
		_form.setCellPadding(5);		
		_form.setWrapItemTitles(false);	
		
		String idProcedimento = pListRecord != null ? pListRecord.getAttribute("idProcedimento") : null;
		istruttoreProcItem = new IstruttoreProcItem(idProcedimento);
		istruttoreProcItem.setName("listaAssegnazione");
		istruttoreProcItem.setTitle("Assegnatario");	
		istruttoreProcItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		istruttoreProcItem.setCanEdit(true);
		istruttoreProcItem.setColSpan(5);
		istruttoreProcItem.setAttribute("obbligatorio", true);	
		istruttoreProcItem.setNotReplicable(true);
		
		_form.setFields(new FormItem[]{istruttoreProcItem});
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(istruttoreProcItem.validate()) {
					onClickOkButton(new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							_window.markForDestroy();
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
		
		setIcon("archivio/assegna.png");
	}

	public abstract void onClickOkButton(DSCallback callback);
	
	public void editRecordPerModificaInvio(Record record) {
		_form.editRecord(record);
		markForRedraw();
	}
	
}
