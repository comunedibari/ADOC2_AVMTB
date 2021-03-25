package it.eng.auriga.ui.module.layout.client.postaElettronica;

import java.util.ArrayList;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.AttachmentReplicableItem;
import it.eng.auriga.ui.module.layout.client.invioMail.InvioMailLayoutNew;
import it.eng.auriga.ui.module.layout.client.invioMail.TipologiaMail;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloAction;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloWindow;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public class GestioneModelliWindow extends PostaElettronicaWindow {

	protected GestioneModelliWindow _window;

	private InvioMailLayoutNew _layout;

	protected GWTRestDataSource modelliDS;
	protected GWTRestDataSource firmeDS;

	protected ToolStrip mainToolStrip;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;
	protected SelectItem firmaInCalceSelectItem;
	protected ListGrid firmaInCalcePickListProperties;
	protected ToolStripButton firmaPredefinitaButton;

	//TODO cancellare
	protected SaveModelloWindow saveModelloWindow;
//	protected DetailToolStripButton salvaComeModelloButton;
		
	protected DetailToolStripButton salvaModelloButton;
	
	//Per la select per selezionare e aggiungere i modelli
//	protected ComboBoxItem modelloSelectItem;
//	protected ListGrid modelloPickListProperties;
	
	//Per il salvataggio del modello
//	private SaveModelloAction saveModelloAction;
	
	protected FileUploadItemWithFirmaAndMimeType uploadFileItem;

	protected DSCallback callbackGestioneModelliButton;	

	public GestioneModelliWindow(String nomeEntita, String tipoRel, TipologiaMail tipologiaMailGestioneModelli, DSCallback callback) {
		
		/*
		 * Il primo parametro consiste nel nome entita; deve essere impostato adeguatamente poichè in base a questo
		 * valore si leggerà e salverà la preferences con key diversa
		 */
		super(nomeEntita, true, tipoRel, null, null, false, null, null, null, callback, tipologiaMailGestioneModelli);
		
		_window = this;
		this.callbackGestioneModelliButton = callback;
	}

	//TODO RIMUOVERE I COSTRUTTORI INUTILI?
//	public GestioneModelliWindow(String tipoRel, CustomLayout customLayoutToDoSearch, DSCallback callback) {
//		super("nuovo_messaggio", true, tipoRel, null, null, false, null, null, null, customLayoutToDoSearch, callback);
//
//		_window = this;
//	}
//
//	public GestioneModelliWindow(String tipoRel, CustomDetail customDetailToReload, DSCallback callback) {
//		super("nuovo_messaggio", true, tipoRel, null, null, false, null, null, null, customDetailToReload, callback);
//
//		_window = this;
//	}

	@Override
	public String getTitleWindow() {
		return I18NUtil.getMessages().gestioneModelli_title();
	}

	@Override
	public String getIconWindow() {
		return "mail/PEO.png";
	}

	@Override
	protected VLayout createMainLayout() {

		VLayout mainLayout = super.createMainLayout();

		createMainToolstrip();

		createSalvaModelloButton();

		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right
//		detailToolStrip.addButton(confermaButton);
//		detailToolStrip.addButton(salvaBozzaButton);
//		detailToolStrip.addButton(salvaComeModelloButton);
		detailToolStrip.addButton(salvaModelloButton);

		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(mainToolStrip, 0);
		mainLayout.addMember(tabSetGenerale);
		mainLayout.addMember(detailToolStrip);

		return mainLayout;
	}

	private void createSalvaModelloButton() {
		// Creo e setto le impostazioni per il pulsante che dovrà salvare il modello
		salvaModelloButton = new DetailToolStripButton(I18NUtil.getMessages().gestioneModelli_salvaModelloButton_title(), 
				"buttons/template_save.png");
		salvaModelloButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickSalvaComeModello();
			}
		});
	}

	private void createMainToolstrip() {

		// Creo la select relativa ai modelli
		createModelliSelectItem();
//		createSelectModelli();

		if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			// Creo la select relativa alla firma in calce
			createSelectFirmaInCalce();
		}

		// Creo la mainToolstrip e aggiungo le select impostate
		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		mainToolStrip.setBorder("0px");
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(30);

		// Aggiungo le due select di interesse
		mainToolStrip.addFormItem(modelliSelectItem);
//		mainToolStrip.addFormItem(modelloSelectItem);
		
		if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			mainToolStrip.addFormItem(firmaInCalceSelectItem);
			mainToolStrip.addButton(firmaPredefinitaButton);
		} 
		
		TitleItem uploadFileTitleItem = new TitleItem("Inserisci un' immagine");
		uploadFileTitleItem.setColSpan(1);

		uploadFileItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				afterUploadImmagineFirmaEmailHtml(uri, displayFileName);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				uploadFileItem.getCanvas().redraw();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				if (info.isFirmato() || info.getMimetype() == null || !info.getMimetype().toLowerCase().startsWith("image/")) {
					GWTRestDataSource.printMessage(new MessageBean("Il file non è un' immagine", "", MessageType.ERROR));
				}
			}
		});
		uploadFileItem.setColSpan(1);
		uploadFileItem.setWidth(16);
		
		mainToolStrip.addSpacer(50);
		mainToolStrip.addFormItem(uploadFileTitleItem);
		mainToolStrip.addFormItem(uploadFileItem);
	}
	
	public void afterUploadImmagineFirmaEmailHtml(String uri, String display) {
		
		if(_layout != null && _layout.getForm() != null){
			if(_layout.getForm().getStyleText().equals("text")){
				//Si sta considerando la modalità text				
				Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));				
			}else{
				// Prelevo i valori del form
				Map formValues = getMapValues();
				GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
				// Pongo un extraparam per l'immagine da aggiungere al corpo
				corpoMailDataSource.extraparam.put("image", "<img src=\"" + display + ":" + uri + "\" />");
				corpoMailDataSource.performCustomOperation("aggiungiImmagine", new Record(formValues), new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							editNewRecord(record.toMap());
						}
					}
				}, new DSRequest());
			}
		}
	}
	
//	private void createSaveModelloAction(){
//		saveModelloAction = new SaveModelloAction(modelliDS, modelloSelectItem) {
//			
//				@Override
//				public Map getMapValuesForAdd() {
//					Map values = getMapValues();
//					values.remove("attach");
//					return values;
//				}
//
//				@Override
//				public Map getMapValuesForUpdate() {
//					Map values = getMapValues();
//					values.remove("attach");
//					return values;
//				}
//							}
//		}

	@Override
	public VLayout getLayoutDatiWindow() {
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		buildNuovoMessaggio();

		_layout.setHeight100();
		_layout.setWidth100();

		return _layout;
	}

	private void buildNuovoMessaggio() {

		_layout = new InvioMailLayoutNew(this, nomeEntita, vm, null, tipologiaMailGestioneModelli);

		final Record lRecordNuovoMessaggio = new Record();

		if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			// Se sono nella modalità per cui devo visualizzare le firme in calce

			GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
			// Pongo un extraparam per il valore della nuova firma
			corpoMailDataSource.extraparam.put("newSignature", AurigaLayout.getHtmlSignature("nuovo"));
			corpoMailDataSource.performCustomOperation("addSignature", lRecordNuovoMessaggio, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];

						lRecordNuovoMessaggio.setAttribute("bodyHtml", record.getAttribute("bodyHtml"));

						_layout.getForm().editNewRecord(lRecordNuovoMessaggio.toMap());
						_layout.getForm().clearErrors(true);
						_layout.getForm().filtroPresenteFromModello(lRecordNuovoMessaggio.toMap());
						
						//Imposto il valore della select con quella che è stata pre-impostata come automatica
						if(AurigaLayout.getFirmaEmailAutoNuova() != null && !AurigaLayout.getFirmaEmailAutoNuova().equals("")){
							firmaInCalceSelectItem.setValue(AurigaLayout.getFirmaEmailAutoNuova());
						}
						
						show();
					}
				}
			}, new DSRequest());
		} else {
			if (AurigaLayout.getFirmaEmailHtml() != null) {
				lRecordNuovoMessaggio.setAttribute("bodyHtml", "<br/><br/>" + AurigaLayout.getFirmaEmailHtml());
			} else {
				lRecordNuovoMessaggio.setAttribute("bodyHtml", "");
			}

			_layout.getForm().editNewRecord(lRecordNuovoMessaggio.toMap());
			_layout.getForm().clearErrors(true);
			_layout.getForm().filtroPresenteFromModello(lRecordNuovoMessaggio.toMap());
			show();
		}

	}
	
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
	}
	
	public void createModelliSelectItem() {

		createModelliDatasource(nomeEntita);

		modelliSelectItem = new SelectItem("modelli");
		modelliSelectItem.setValueField("key");
		modelliSelectItem.setDisplayField("displayValue");
		modelliSelectItem.setTitle("<b>" + I18NUtil.getMessages().protocollazione_detail_modelliSelectItem_title() + "</b>");
		modelliSelectItem.setCachePickListResults(false);
		modelliSelectItem.setRedrawOnChange(true);
		modelliSelectItem.setShowOptionsFromDataSource(true);

		modelliPickListProperties = new ListGrid();
		modelliPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		modelliPickListProperties.setShowHeader(false);
//		modelliPickListProperties.setCanRemoveRecords(true);
		// apply the selected preference from the SelectItem
		modelliPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				String userid = (String) event.getRecord().getAttribute("userid");
				String prefName = (String) event.getRecord().getAttribute("prefName");
				if (prefName != null && !"".equals(prefName)) {
					AdvancedCriteria criteria = new AdvancedCriteria();
					criteria.addCriteria("prefName", OperatorId.EQUALS, prefName);
					criteria.addCriteria("flgIncludiPubbl", userid.startsWith("PUBLIC.") ? "1" : "0");
					if (userid.startsWith("UO.")) {
						String idUo = (String) event.getRecord().getAttribute("idUo");
						criteria.addCriteria("idUo", idUo);
					}
					modelliDS.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length != 0) {
								Record record = data[0];
								Record values = new Record(JSON.decode(record.getAttribute("value")));
								Map oldValues = getMapValues();
								ArrayList<Map> lArrayList = (ArrayList<Map>) oldValues.get("attach");
								if (lArrayList != null) {
									RecordList lRecordList = new RecordList();
									for (Map lMap : lArrayList) {
										Record lRecord = new Record(lMap);
										lRecordList.add(lRecord);
									}
									values.setAttribute("attach", lRecordList);
								}
								editNewRecord(values.toMap());
							}
						}
					});
				}
			}
		});

		modelliSelectItem.setPickListProperties(modelliPickListProperties);
		modelliSelectItem.setOptionDataSource(modelliDS);
		modelliSelectItem.setAllowEmptyValue(true);

		ListGridField modelliRemoveField = new ListGridField("remove");
		modelliRemoveField.setType(ListGridFieldType.ICON);
		modelliRemoveField.setWidth(18);
		modelliRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isAbilToRemoveModello(record)) {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				}
				return null;
			}
		});
//		modelliRemoveField.setIsRemoveField(true);
		
		modelliRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				if (isAbilToRemoveModello(event.getRecord())) {
					final String key = event.getRecord().getAttribute("key");
					modelliDS.removeData(event.getRecord(), new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							String value = (String) modelliSelectItem.getValue();
							if (key != null && value != null && key.equals(value)) {
								modelliSelectItem.setValue((String) null);
							}
						} 
					});
				}				
			}   
		});

		ListGridField modelliKeyField = new ListGridField("key");
		modelliKeyField.setHidden(true);

		ListGridField modelliDisplayValueField = new ListGridField("displayValue");
		modelliDisplayValueField.setWidth("100%");

		modelliSelectItem.setPickListFields(modelliRemoveField, modelliKeyField, modelliDisplayValueField);

		createSaveModelloWindow(nomeEntita);
		
		
	}

	private void createSelectFirmaInCalce() {
		createFirmeDatasource();
		
		//Creo il pulsante per selezionare la firma predefinita
		firmaPredefinitaButton = new ToolStripButton("", "menu/firma_email.png");
		firmaPredefinitaButton.setPrompt(I18NUtil.getMessages().firme_in_calce_firmaPredefinita_title());
		/*
		 * L'immagine di background normalmente è già impostata.
		 * Ne setto una vuota così da non avere lo stile precedente ma per vedere in trasparenza 
		 * lo sfondo della finestra
		 */
		firmaPredefinitaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				replaceSignature();
			}
			
			private void replaceSignature() {

				if(_layout != null && _layout.getForm() != null){
					if(_layout.getForm().getStyleText().equals("text")){
						//Si sta considerando la modalità text
						
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));
						
					}else{
						// Ottengo la firma impostata come predefinita
						final String nomeFirmaPredefinita = AurigaLayout.getFirmaEmailPredefinita();
						String firmaPredefinita = nomeFirmaPredefinita != null && !nomeFirmaPredefinita.equals("") ? AurigaLayout.getFirmaEmailHtml(nomeFirmaPredefinita) : null;
		
						if (firmaPredefinita != null && !firmaPredefinita.equals("")) {
							// Prelevo i valori del form
							Map formValues = getMapValues();
		
							GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
							// Pongo un extraparam per il valore della nuova firma
							corpoMailDataSource.extraparam.put("newSignature", firmaPredefinita);
							corpoMailDataSource.extraparam.put("modalita", "nuovaMail");
							corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record record = response.getData()[0];
										editNewRecord(record.toMap());
										firmaInCalceSelectItem.setValue(nomeFirmaPredefinita);
									}
								}
							}, new DSRequest());
						}
						else
						{
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_firmaPredefinitaNonPresente_warningMessage(), "", MessageType.WARNING));
						}
					}
				}
			}
		});
		
		// Creo la select
		firmaInCalceSelectItem = new SelectItem("firmaInCalce");
		firmaInCalceSelectItem.setValueField("prefName");
		firmaInCalceSelectItem.setDisplayField("prefName");

		firmaInCalceSelectItem.setTitle("<b>" + I18NUtil.getMessages().firme_in_calce_modelliSelectItem_title() + "</b>");
		firmaInCalceSelectItem.setWrapTitle(false); // In questo modo il titolo non viene mandato a capo
		firmaInCalceSelectItem.setCachePickListResults(false);
		firmaInCalceSelectItem.setRedrawOnChange(true);
		firmaInCalceSelectItem.setShowOptionsFromDataSource(true);
		firmaInCalceSelectItem.setOptionDataSource(firmeDS);
		firmaInCalceSelectItem.setAllowEmptyValue(true);

		firmaInCalcePickListProperties = new ListGrid();
		firmaInCalcePickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		firmaInCalcePickListProperties.setShowHeader(false);
		// apply the selected preference from the SelectItem
		firmaInCalcePickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				if(_layout != null && _layout.getForm() != null){
					if(_layout.getForm().getStyleText().equals("text")){
						//Si sta considerando la modalità text
						
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));
						
					}else{
						final String preferenceName = event.getRecord().getAttribute("prefName");
						if (preferenceName != null && !"".equals(preferenceName)) {
							AdvancedCriteria criteria = new AdvancedCriteria();
							criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
							firmeDS.fetchData(criteria, new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									Record[] data = response.getData();
									if (data.length != 0) {
										Record record = data[0];
		
										// Prelevo i valori del form
										Map formValues = getMapValues();
		
										GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
										// Pongo un extraparam per il valore della nuova firma
										corpoMailDataSource.extraparam.put("newSignature", record.getAttributeAsString("value"));
										corpoMailDataSource.extraparam.put("modalita", "nuovaMail");
										corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													Record record = response.getData()[0];
		
													editNewRecord(record.toMap());
												}
											}
										}, new DSRequest());
		
									}
								}
							});
						}
					}
				}
			}
		});

		// Inserisco la picklist
		firmaInCalceSelectItem.setPickListProperties(firmaInCalcePickListProperties);
	}

	private void createFirmeDatasource() {
		firmeDS = UserInterfaceFactory.getPreferenceDataSource();
		firmeDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "signature.email");
	}

	protected void createModelliDatasource(String nomeEntita) {
		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		if(tipologiaMailGestioneModelli.equals(TipologiaMail.RISPOSTA)){
			modelliDS.addParam("prefKey", "risposta.modelli");
		}else if(tipologiaMailGestioneModelli.equals(TipologiaMail.INOLTRO)){
			modelliDS.addParam("prefKey", "inoltro.modelli");
		}else if(tipologiaMailGestioneModelli.equals(TipologiaMail.INVIO_UD)){
			modelliDS.addParam("prefKey", "invio_documento.modelli");
		}
		
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("EML/MPB") ? "1" : "0");
	}

	protected void createSaveModelloWindow(String nomeEntita) {
		saveModelloWindow = new SaveModelloWindow(I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(), nomeEntita,
				new SaveModelloAction(modelliDS, modelliSelectItem) {

					@Override
					public Map getMapValuesForAdd() {
						Map values = getMapValues();
						values.remove("attach");
						return values;
					}
 
					@Override
					public Map getMapValuesForUpdate() {
						Map values = getMapValues();
						values.remove("attach");
						return values;
					}
				}) {

			@Override
			public boolean isAbilToSavePublic() {
				return Layout.isPrivilegioAttivo("EML/MPB");
			}
		};
	}

	public void clickSalvaComeModello() {
		if ((!saveModelloWindow.isDrawn()) || (!saveModelloWindow.isVisible())) {
			saveModelloWindow.clearValues();
			saveModelloWindow.selezionaModello(modelliSelectItem.getSelectedRecord());
			saveModelloWindow.redrawForms();
			saveModelloWindow.redraw();
			saveModelloWindow.show();
			//TODO CANCELLARE?
//			if (callback != null) {
//				callback.execute(null, null, null);
//			}
		}
	}

	private void editNewRecord(Map map) {
		vm.editRecord(new Record(map));
		_layout.getForm().verifyChangeStyle();
		_layout.getForm().filtroPresenteFromModello(map);
	}

	private Map getMapValues() {
		return vm.getValues();
	}

	@Override
	public void setFileAsAllegato(Record record) {
		AttachmentReplicableItem item = (AttachmentReplicableItem) _layout.getForm().getAttachmentReplicableItem();
		item.setFileAsAllegatoFromWindow(record);
	}

	@Override
	public void inviaMail(DSCallback callback) {
		//In questo caso non dobbiamo inviare mail quindi il metodo non fa nulla
	}

	@Override
	public void salvaBozza(DSCallback callback) {
		//In questo caso non dobbiamo inviare mail quindi il metodo non fa nulla
	}
	
	
//	protected void createSelectModelli(){
//		
//		createModelliDatasource(nomeEntita);
//		
//		modelloSelectItem = new ComboBoxItem("modelloSelectItem");
//		modelloSelectItem.setValueField("prefName");
//		modelloSelectItem.setDisplayField("prefName");
//		modelloSelectItem.setTitle("<b>" + I18NUtil.getMessages().protocollazione_detail_modelliSelectItem_title() + "</b>");
//		modelloSelectItem.setCanEdit(true);
//		modelloSelectItem.setWidth(300);
//		modelloSelectItem.setItemHoverFormatter(new FormItemHoverFormatter() {
//
//			@Override
//			public String getHoverHTML(FormItem item, DynamicForm form) {
//				return (String) modelloSelectItem.getValue();
//			}
//		});
//
//		modelloPickListProperties = new ListGrid();
//		modelloPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
//		modelloPickListProperties.setShowHeader(false);
//		// firmePickListProperties.setCanRemoveRecords(true); //TODO TOGLIERE?
//		modelloPickListProperties.addCellClickHandler(new CellClickHandler() {
//
//			@Override
//			public void onCellClick(CellClickEvent event) {
//				final String preferenceName = event.getRecord().getAttribute("prefName");
//				
////				if (preferenceName != null && !"".equals(preferenceName)) {
////					AdvancedCriteria criteria = new AdvancedCriteria();
////					criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
////					firmeDS.fetchData(criteria, new DSCallback() {
////
////						@Override
////						public void execute(DSResponse response, Object rawData, DSRequest request) {
////							Record[] data = response.getData();
////							if (data.length != 0) {
////								Record record = data[0];
////							}
////						}
////					});
////				}
//			}
//		});
//		modelloSelectItem.setPickListProperties(modelloPickListProperties);
//		modelloSelectItem.setOptionDataSource(modelliDS);
//		
////		ListGridField modelloPrefNameField = new ListGridField("prefName");
////		ListGridField modelloRemoveField = new ListGridField("remove");
////		modelloRemoveField.setType(ListGridFieldType.ICON);
////		modelloRemoveField.setWidth(18);
////		modelloRemoveField.setCellFormatter(new CellFormatter() {
////
////			@Override
////			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
////				if (record.getAttribute("prefName") == null || "".equals(record.getAttributeAsString("prefName"))) {
////					return null;
////				} else {
////					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
////				}
////			}
////		});
////		// firmeRemoveField.setIsRemoveField(true);
////		modelloRemoveField.addRecordClickHandler(new RecordClickHandler() {
////
////			@Override
////			public void onRecordClick(RecordClickEvent event) {
////				final String prefName = event.getRecord().getAttribute("prefName");
////				firmeDS.removeData(event.getRecord(), new DSCallback() {
////
////					@Override
////					public void execute(DSResponse response, Object rawData, DSRequest request) {
////						String value = (String) modelloSelectItem.getValue();
////						if (prefName != null && value != null && prefName.equals(value)) {
////							modelloSelectItem.setValue((String) null);
////						}
//////						AurigaLayout.getFirmeEmailHtml().remove(prefName);
////					}
////				});
////			}
////		});
////		modelloSelectItem.setPickListFields(modelloRemoveField, modelloPrefNameField);
//		modelloSelectItem.setAutoFetchData(false);
//		modelloSelectItem.setFetchMissingValues(true);
//		
//		
//		createSaveModelloAction();
//	}
	
	@Override
	public void manageOnCloseClick() {
		super.manageOnCloseClick();
		
		//Ritorno al chiamante
		if(callbackGestioneModelliButton != null){
			DSResponse lDsResponse = new DSResponse();
			lDsResponse.setStatus(DSResponse.STATUS_SUCCESS);
			callbackGestioneModelliButton.execute(lDsResponse, null, new DSRequest());
		
		}
	}
	
	@Override
	protected void onDestroy() {
		if(saveModelloWindow != null) {
			saveModelloWindow.destroy();
		}
		if(modelliDS != null) {
			modelliDS.destroy();
		}
		super.onDestroy();
	}
	
}

