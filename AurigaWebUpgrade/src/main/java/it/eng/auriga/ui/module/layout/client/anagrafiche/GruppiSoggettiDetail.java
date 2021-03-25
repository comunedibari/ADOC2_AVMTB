package it.eng.auriga.ui.module.layout.client.anagrafiche;
import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class GruppiSoggettiDetail extends CustomDetail { 
		
	GruppiSoggettiDetail _instance;
	
	DynamicForm soggettiForm;
	DynamicForm datiPrincipaliForm;
	DynamicForm validitaForm;
	
	protected SoggettiGruppoItem soggettiItem;
	
	// HiddenItem 
	private HiddenItem 		idGruppoItem;				// ID gruppo
	
	// TextItem 
	private TextItem 		codiceRapidoItem;			// Codice rapido
	private TextItem 		nomeItem;					// denominazione
	
	// DateItem
	private DateItem 		dtValiditaDaItem;     		// Valido dal 
	private DateItem 		dtValiditaAItem;      		// Valido al		
	
	// CheckboxItem
	private CheckboxItem 	flgInibitaAssItem; // non utilizzabile in assegnazione/invio per conoscenza
	
	// DetailSection
	protected DetailSection soggettiSection;	
	protected DetailSection datiPrincipaliSection;
	protected DetailSection validitaSection;
	
	protected boolean editing;
	
	public GruppiSoggettiDetail(String nomeEntita) {		
		
		super(nomeEntita);
		
		_instance = this;		
				
        //******************************************************************************************
    	// Sezione DATI PRINCIPALI
        //******************************************************************************************
		
		// Creo il form
        datiPrincipaliForm = new DynamicForm();
        datiPrincipaliForm.setValuesManager(vm);
        datiPrincipaliForm.setWidth("100%"); 
        datiPrincipaliForm.setHeight(10);  
        datiPrincipaliForm.setPadding(5);
        datiPrincipaliForm.setNumCols(6);
        datiPrincipaliForm.setColWidths(10 , 100,  10 , 100, 10 , "*");
        
        datiPrincipaliForm.setWrapItemTitles(false);

        // Creo gli item
        idGruppoItem     = new HiddenItem("idGruppo");
                
		codiceRapidoItem = new TextItem("codiceRapido", I18NUtil.getMessages().gruppisoggetti_detail_codiceRapidoItem_title());
		codiceRapidoItem.setWidth(100);	
		codiceRapidoItem.setStartRow(true);
		codiceRapidoItem.setEndRow(false);
	    
		nomeItem = new TextItem("nome", I18NUtil.getMessages().gruppisoggetti_detail_nomeItem_title());
        nomeItem.setRequired(true);
        nomeItem.setWidth(380);
        nomeItem.setStartRow(false);
        nomeItem.setEndRow(false);
       
		
        // Aggiungo gli item al form
        datiPrincipaliForm.setItems(idGruppoItem, codiceRapidoItem, nomeItem);
        
        // Aggiungo il form alla section
        datiPrincipaliSection    = new DetailSection(I18NUtil.getMessages().gruppisoggetti_detail_datiPrincipaliSection_title(), false, true, false, datiPrincipaliForm);
        
        //******************************************************************************************
    	// Sezione VALIDITA'
        //******************************************************************************************
		// Creo il form
        validitaForm = new DynamicForm();
        validitaForm.setValuesManager(vm);
        validitaForm.setWidth("100%");  
        validitaForm.setHeight(10); 
        validitaForm.setPadding(5);
        validitaForm.setNumCols(6);
        validitaForm.setColWidths(10 , 100,  10 , 100, 10 , "*");
        
        validitaForm.setWrapItemTitles(false);
        
        dtValiditaDaItem = new DateItem("dtValiditaDa", I18NUtil.getMessages().gruppisoggetti_detail_dtValiditaDaItem_title());
        dtValiditaDaItem.setStartRow(true);
        dtValiditaDaItem.setEndRow(false);		
        
        dtValiditaAItem = new DateItem("dtValiditaA", I18NUtil.getMessages().gruppisoggetti_detail_dtValiditaAItem_title());
        dtValiditaAItem.setStartRow(false);
        dtValiditaAItem.setEndRow(false);		
                
        flgInibitaAssItem = new CheckboxItem("flgInibitaAss", I18NUtil.getMessages().gruppisoggetti_detail_flgInibitaAssItem_title());
        flgInibitaAssItem.setValue(false);
        flgInibitaAssItem.setColSpan(5);
        flgInibitaAssItem.setWidth(10);
        flgInibitaAssItem.setStartRow(true);
        flgInibitaAssItem.setTitleOrientation(TitleOrientation.RIGHT);
        flgInibitaAssItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return AurigaLayout.getParametroDB("RESTRIZIONI_ASS_NOT_DOC").equalsIgnoreCase("CONF_1");
			}
		});

        // Aggiungo gli item al form
        validitaForm.setItems(dtValiditaDaItem, dtValiditaAItem, flgInibitaAssItem);
        
        // Aggiungo il form alla section
        validitaSection    = new DetailSection(I18NUtil.getMessages().gruppisoggetti_detail_validitaSection_title(), false, true, false, validitaForm);
        
        //******************************************************************************************
    	// Sezione SOGGETTI
    	//******************************************************************************************
        
        // Creo il form
        soggettiForm = new DynamicForm();
        soggettiForm.setValuesManager(vm);
        soggettiForm.setWidth("100%");
        soggettiForm.setHeight(10); 
        soggettiForm.setPadding(5);
        soggettiForm.setNumCols(8);
        soggettiForm.setWrapItemTitles(false);        
        
        // Creo gli item
        soggettiItem = new SoggettiGruppoItem();
        soggettiItem.setName("listaSoggettiGruppo");
        soggettiItem.setShowTitle(false);
        soggettiItem.setShowNewButton(true);
        
        
        
        // Aggiungo gli item al form
        soggettiForm.setFields(soggettiItem);
                        
        // Aggiungo il form alla section
		soggettiSection  = new DetailSection(I18NUtil.getMessages().gruppisoggetti_detail_soggettiSection_title(), true, true, false, soggettiForm);	
		
		// Creo il VLAYOUT MAIN
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		lVLayout.setHeight(50);					
		VLayout lVLayoutSpacer = new VLayout();  
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);			
		lVLayout.addMember(datiPrincipaliSection);	
		lVLayout.addMember(validitaSection);	
		lVLayout.addMember(soggettiSection);	
		addMember(lVLayout);
		//addMember(lVLayoutSpacer);			
		
		setCanEdit(true);
	}
}
