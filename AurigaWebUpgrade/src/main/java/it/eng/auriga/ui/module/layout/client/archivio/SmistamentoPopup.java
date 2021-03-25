package it.eng.auriga.ui.module.layout.client.archivio;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.protocollazione.AssegnazioneItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class SmistamentoPopup extends ModalWindow {
	
	protected SmistamentoPopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected String flgUdFolder;
	
	protected AssegnazioneItem assegnazioneItem;
	protected TextAreaItem messaggioInvioItem;
	protected SelectItem livelloPrioritaItem;
//	protected DateTimeItem tsDecorrenzaAssegnazItem;
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public SmistamentoPopup(String pFlgUdFolder, Record pListRecord){
		
		super("smistamento", true);
		
		_window = this;
		
		flgUdFolder = pFlgUdFolder;
		
		String segnatura = (pListRecord != null && pListRecord.getAttribute("segnatura") != null) ? pListRecord.getAttribute("segnatura") : "";
		String title = "Compila dati smistamento";
		if(!"".equals(segnatura)) {
			title += " " + segnatura;
		}
		setTitle(title);
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);				
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(4);
		_form.setColWidths(120,"*","*","*");		
		_form.setWrapItemTitles(false);	
//		_form.setCellBorder(1);
		
		assegnazioneItem = new AssegnazioneItem();
		assegnazioneItem.setName("listaAssegnazioni");
		assegnazioneItem.setTitle("Destinatario");	
		assegnazioneItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		assegnazioneItem.setCanEdit(true);
		assegnazioneItem.setColSpan(4);
		assegnazioneItem.setFlgUdFolder(flgUdFolder);
		assegnazioneItem.setNotReplicable(true);		
		assegnazioneItem.setFlgSenzaLD(true);
		assegnazioneItem.setAttribute("obbligatorio", true);
		
		messaggioInvioItem = new TextAreaItem("messaggioInvio", "Messaggio");
		messaggioInvioItem.setStartRow(true);
		messaggioInvioItem.setLength(4000);
		messaggioInvioItem.setHeight(40);
		messaggioInvioItem.setColSpan(4);
		messaggioInvioItem.setWidth(750);			
		
		GWTRestDataSource livelloPrioritaDS = new GWTRestDataSource("LoadComboPrioritaInvioDataSource", "key", FieldType.TEXT);
		
		livelloPrioritaItem = new SelectItem("livelloPriorita", "Livello priorità"); 
		livelloPrioritaItem.setStartRow(true);
		livelloPrioritaItem.setOptionDataSource(livelloPrioritaDS);  
		livelloPrioritaItem.setAutoFetchData(true);
		livelloPrioritaItem.setDisplayField("value");
		livelloPrioritaItem.setValueField("key");			
		livelloPrioritaItem.setWidth(120);
		livelloPrioritaItem.setWrapTitle(false);
		livelloPrioritaItem.setColSpan(1);
		livelloPrioritaItem.setStartRow(true);
		livelloPrioritaItem.setAllowEmptyValue(true);
		
//		tsDecorrenzaAssegnazItem = new DateTimeItem("tsDecorrenzaAssegnaz", "Smistamento posticipato al");
//		tsDecorrenzaAssegnazItem.setWrapTitle(false);
//		tsDecorrenzaAssegnazItem.setColSpan(1);
				
//		_form.setFields(new FormItem[]{assegnazioneItem, messaggioInvioItem, livelloPrioritaItem, tsDecorrenzaAssegnazItem});
		_form.setFields(new FormItem[]{assegnazioneItem, messaggioInvioItem, livelloPrioritaItem});
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(assegnazioneItem.validate()) {
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
				
		setIcon("archivio/smista.png");

	}

	public abstract void onClickOkButton(DSCallback callback);
	
}
