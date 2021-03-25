package it.eng.auriga.ui.module.layout.client.protocollazione;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DocumentoCollegatoCanvas extends ReplicableCanvas{

	private HiddenItem idUdCollegataHiddenItem;
	private SelectItem tipoItem;
	private FormItem siglaRegistroItem;
	private AnnoItem annoItem;
	private ExtendedNumericItem numeroItem;
	private ExtendedNumericItem subItem;
	private TextAreaItem oggettoItem;
	private TextAreaItem motiviItem;
	private HiddenItem estremiRegHiddenItem;
	private HiddenItem datiCollegamentoHiddenItem;
	private HiddenItem flgLockedHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;
	
	public DocumentoCollegatoCanvas(DocumentiCollegatiItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		idUdCollegataHiddenItem = new HiddenItem("idUdCollegata");
		
		GWTRestDataSource tipoDS = new GWTRestDataSource("LoadComboTipoDocCollegatoDataSource", "key", FieldType.TEXT);

		tipoItem = new SelectItem("tipo", "Tipo");
		tipoItem.setRequired(true);		
//		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
//		tipoValueMap.put("PG", "Prot. Generale");
//		tipoValueMap.put("PI", "Prot. Interno");
//		tipoValueMap.put("NI", "Bozza");
//		tipoValueMap.put("PP", "Protocollo Particolare");
//		tipoValueMap.put("R", "Repertorio");
//		tipoItem.setValueMap(tipoValueMap);		
		tipoItem.setOptionDataSource(tipoDS);
		tipoItem.setAutoFetchData(true);
		tipoItem.setDisplayField("value");
		tipoItem.setValueField("key");
		tipoItem.setDefaultValue("PG");
		tipoItem.setWidth(120);
		tipoItem.setWrapTitle(false);
		tipoItem.setColSpan(1);
		tipoItem.setStartRow(true);
		tipoItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idUdCollegata", (String) null);
				mDynamicForm.setValue("oggetto", (String) null);					
				mDynamicForm.markForRedraw();
			}
		});
		tipoItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				String datiCollegamento = form.getValueAsString("datiCollegamento") != null ? form.getValueAsString("datiCollegamento") : "";				
				if(!"".equals(estremiReg) || !"".equals(datiCollegamento)) {
					return estremiReg  + " " + datiCollegamento;
				}
				return null;
			}
		});
		
		if(((DocumentiCollegatiItem)getItem()).isProtInModalitaWizard()) {
			
			siglaRegistroItem = new TextItem("siglaRegistro", "Registro (sigla)");
			siglaRegistroItem.setWidth(100);
			siglaRegistroItem.setColSpan(1);
			siglaRegistroItem.setAttribute("obbligatorio", true);
			siglaRegistroItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return tipoItem.getValueAsString() != null && ("R".equalsIgnoreCase(tipoItem.getValueAsString()) || "PP".equalsIgnoreCase(tipoItem.getValueAsString()));
				}
			}));	
			siglaRegistroItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return tipoItem.getValueAsString() != null && ("R".equalsIgnoreCase(tipoItem.getValueAsString()) || "PP".equalsIgnoreCase(tipoItem.getValueAsString()));
				}
			});
		} else {
			
			GWTRestDataSource repertorioDS = new GWTRestDataSource("LoadComboRepertorioDataSource", "key", FieldType.TEXT);
			repertorioDS.addParam("flgAncheNonAssegnabili", "1");

			siglaRegistroItem = new SelectItem("siglaRegistro", "Registro");
			siglaRegistroItem.setValueField("key");
			siglaRegistroItem.setDisplayField("value");
			siglaRegistroItem.setOptionDataSource(repertorioDS);
			siglaRegistroItem.setWidth(200);
			((SelectItem)siglaRegistroItem).setClearable(true);
			((SelectItem)siglaRegistroItem).setCachePickListResults(false);
			siglaRegistroItem.setColSpan(1);
			siglaRegistroItem.setAttribute("obbligatorio", true);
			siglaRegistroItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return tipoItem.getValueAsString() != null && "R".equalsIgnoreCase(tipoItem.getValueAsString());
				}
			}));	
			siglaRegistroItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return tipoItem.getValueAsString() != null && "R".equalsIgnoreCase(tipoItem.getValueAsString());
				}
			});
		}
		
		annoItem = new AnnoItem("anno", "Anno");
		annoItem.setRequired(true);		
		annoItem.setColSpan(1);
		annoItem.addChangedBlurHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idUdCollegata", (String) null);
				mDynamicForm.setValue("oggetto", (String) null);					
				mDynamicForm.markForRedraw();
			}
		});
		annoItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				String datiCollegamento = form.getValueAsString("datiCollegamento") != null ? form.getValueAsString("datiCollegamento") : "";				
				if(!"".equals(estremiReg) || !"".equals(datiCollegamento)) {
					return estremiReg  + " " + datiCollegamento;
				}
				return null;
			}
		});
		
		numeroItem = new ExtendedNumericItem("numero", "N.ro", false);		
		numeroItem.setRequired(true);
		numeroItem.setColSpan(1);
		numeroItem.setLength(7);
		numeroItem.addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {

				mDynamicForm.setValue("idUdCollegata", (String) null);
				mDynamicForm.setValue("oggetto", (String) null);					
				mDynamicForm.markForRedraw();
			}
		});
		numeroItem.addChangedBlurHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idUdCollegata", (String) null);
				mDynamicForm.setValue("oggetto", (String) null);					
				mDynamicForm.markForRedraw();
			}
		});
		numeroItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				String datiCollegamento = form.getValueAsString("datiCollegamento") != null ? form.getValueAsString("datiCollegamento") : "";				
				if(!"".equals(estremiReg) || !"".equals(datiCollegamento)) {
					return estremiReg  + " " + datiCollegamento;
				}
				return null;
			}
		});
		
		subItem = new ExtendedNumericItem("sub", "Sub", false);
		subItem.setWidth(80);
		subItem.setColSpan(1);
		subItem.setLength(3);
		subItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DocumentiCollegatiItem)getItem()).isProtInModalitaWizard()) {
					return tipoItem.getValueAsString() != null && "PP".equalsIgnoreCase(tipoItem.getValueAsString());
				}
				return false;
			}
		});	
		subItem.addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {

				mDynamicForm.setValue("idUdCollegata", (String) null);
				mDynamicForm.setValue("oggetto", (String) null);					
				mDynamicForm.markForRedraw();
			}
		});
		subItem.addChangedBlurHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idUdCollegata", (String) null);
				mDynamicForm.setValue("oggetto", (String) null);					
				mDynamicForm.markForRedraw();
			}
		});
		subItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				String datiCollegamento = form.getValueAsString("datiCollegamento") != null ? form.getValueAsString("datiCollegamento") : "";				
				if(!"".equals(estremiReg) || !"".equals(datiCollegamento)) {
					return estremiReg  + " " + datiCollegamento;
				}
				return null;
			}
		});

		// BOTTONI : seleziona dall'archivio, nuovo
		ImgButtonItem lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Seleziona dall'archivio");	
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);		
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {			
			@Override
			public void onIconClick(IconClickEvent event) {
									
				DocumentoCollegatoLookupArchivio lookupArchivioPopup = new DocumentoCollegatoLookupArchivio(mDynamicForm.getValuesAsRecord());
				lookupArchivioPopup.show();				
			}
		});	  
		
		ImgButtonItem visualizzaDettagliButton = new ImgButtonItem("visualizzaDettagliButton", "buttons/detail.png", "Visualizza dettagli");
		visualizzaDettagliButton.setAlwaysEnabled(true);
		visualizzaDettagliButton.setColSpan(1);		
		visualizzaDettagliButton.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return form.getValue("idUdCollegata") != null && !"".equals(form.getValue("idUdCollegata"));
			}
		});
		visualizzaDettagliButton.addIconClickHandler(new IconClickHandler() {			
			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record();
				record.setAttribute("idUd", idUdCollegataHiddenItem.getValue());					
				new DettaglioRegProtAssociatoWindow(record, "Dettaglio doc. collegato");
			}
		});			
		
		oggettoItem = new TextAreaItem("oggetto", "Oggetto");
		oggettoItem.setHeight(40);
		oggettoItem.setWidth(650);
		oggettoItem.setStartRow(true);
		oggettoItem.setColSpan(20);
		
		motiviItem = new TextAreaItem("motivi", "Motivi collegamento");
		motiviItem.setHeight(40);
		motiviItem.setWidth(650);
		motiviItem.setStartRow(true);
		motiviItem.setColSpan(20);
				
		estremiRegHiddenItem = new HiddenItem("estremiReg");
		datiCollegamentoHiddenItem = new HiddenItem("datiCollegamento");
		flgLockedHiddenItem = new HiddenItem("flgLocked");		
		
		mDynamicForm.setFields(
				idUdCollegataHiddenItem,
				tipoItem, 
				siglaRegistroItem,
				annoItem,
				numeroItem,
				subItem,
				lookupArchivioButton,
				visualizzaDettagliButton,
				oggettoItem,
				motiviItem,
				estremiRegHiddenItem,
				datiCollegamentoHiddenItem,
				flgLockedHiddenItem
		);
		

		mDynamicForm.setNumCols(30);
		mDynamicForm.setColWidths("50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

		addChild(mDynamicForm);

	}	

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}

	@Override
	public ReplicableCanvasForm[] getForm() {

		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	public void setFormValuesFromRecordArchivio(Record record) {
		mDynamicForm.clearErrors(true);						
		mDynamicForm.setValue("idUdCollegata", record.getAttribute("idUdFolder"));
		String segnaturaXOrd = record.getAttribute("segnaturaXOrd");		
		StringSplitterClient st = new StringSplitterClient(segnaturaXOrd, "-");		
		String tipo = st.getTokens()[0];
		if(tipo != null) {
			if("1".equals(tipo)) {
				mDynamicForm.setValue("tipo", "PG");				
			} else if("3".equals(tipo)) {
				mDynamicForm.setValue("tipo", "PI");				
			} else if("7".equals(tipo)) {
				mDynamicForm.setValue("tipo", "NI");				
			} else if("2".equals(tipo)) {
				mDynamicForm.setValue("tipo", "PP");				
			} else if("4".equals(tipo)) {
				mDynamicForm.setValue("tipo", "R");				
			}
		}
		mDynamicForm.setValue("siglaRegistro", st.getTokens()[1].trim());
		mDynamicForm.setValue("anno", st.getTokens()[2]);
		mDynamicForm.setValue("numero", st.getTokens()[3]);
		mDynamicForm.setValue("sub", st.getTokens()[4]); 
		mDynamicForm.setValue("oggetto", record.getAttribute("oggetto"));		
		mDynamicForm.markForRedraw();		
	}	
	
	public class DocumentoCollegatoLookupArchivio extends LookupArchivioPopup {

		public DocumentoCollegatoLookupArchivio(Record record) {
			super(record, true);			
		}

		public DocumentoCollegatoLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);			
		}

		@Override
		public String getWindowTitle() {
			return "Seleziona documento da collegare";
		}
		
		@Override
		public String getFinalita() {			
			return "COLLEGA_UD";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record);						
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}	

		@Override
		public void manageMultiLookupUndo(Record record) {	

		}			
	}
	
	public boolean isLocked() {

		return mDynamicForm.getValueAsString("flgLocked") != null && mDynamicForm.getValueAsString("flgLocked").equals("true");
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {

		super.setCanEdit(canEdit);
		oggettoItem.setCanEdit(false);
	}
	
}