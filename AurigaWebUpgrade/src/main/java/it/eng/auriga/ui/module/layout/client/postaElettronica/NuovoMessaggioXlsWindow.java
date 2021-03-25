package it.eng.auriga.ui.module.layout.client.postaElettronica;


import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.AttachmentReplicableItem;
import it.eng.auriga.ui.module.layout.client.invioMail.InvioMailMultiXlsLayout;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public class NuovoMessaggioXlsWindow extends PostaElettronicaWindow {

	protected NuovoMessaggioXlsWindow _window;

	private InvioMailMultiXlsLayout _layout;

	protected GWTRestDataSource firmeDS;

	protected ToolStrip mainToolStrip;
	
	protected SelectItem firmaInCalceSelectItem;
	protected ListGrid firmaInCalcePickListProperties;
	protected ToolStripButton firmaPredefinitaButton;

	protected FileUploadItemWithFirmaAndMimeType uploadFileItem;

	public NuovoMessaggioXlsWindow(String tipoRel, DSCallback callback) {
		super("nuovo_messaggio_multi_destinatari_xls", true, tipoRel, null, null, false, null, null, null, callback);

		_window = this;
	}

	public NuovoMessaggioXlsWindow(String tipoRel, CustomLayout customLayoutToDoSearch, DSCallback callback) {
		super("nuovo_messaggio_multi_destinatari_xls", true, tipoRel, null, null, false, null, null, null, customLayoutToDoSearch, callback);

		_window = this;
	}

	public NuovoMessaggioXlsWindow(String tipoRel, CustomDetail customDetailToReload, DSCallback callback) {
		super("nuovo_messaggio_multi_destinatari_xls", true, tipoRel, null, null, false, null, null, null, customDetailToReload, callback);

		_window = this;
	}

	@Override
	public String getTitleWindow() {
		return I18NUtil.getMessages().posta_elettronica_nuovo_mess_window();
	}

	@Override
	public String getIconWindow() {
		return "menu/import_excel.png";
	}

	@Override
	protected VLayout createMainLayout() {

		VLayout mainLayout = super.createMainLayout();

		createMainToolstrip();

		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.addFill(); // push all buttons to the right
		detailToolStrip.addButton(confermaButton);

		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(mainToolStrip, 0);
		mainLayout.addMember(tabSetGenerale);
		mainLayout.addMember(detailToolStrip);

		return mainLayout;
	}

	private void createMainToolstrip() {

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
	
	public Record getInitialRecordNuovoMessaggio() {		
		return new Record();
	}

	private void buildNuovoMessaggio() {

		_layout = new InvioMailMultiXlsLayout(this, nomeEntita, vm, null) {
			@Override
			public boolean getFormFlgSalvaInviatiDefaultValue() {				
				return getDefaultSaveSentEmail();
			}
		};

		final Record lRecordNuovoMessaggio = getInitialRecordNuovoMessaggio();

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
			show();
		}

	}
	
	public boolean getDefaultSaveSentEmail() {
		return AurigaLayout.getParametroDBAsBoolean("DEFAULT_SAVE_SENT_EMAIL");
	}
	
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
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

	private void editNewRecord(Map map) {
		vm.editRecord(new Record(map));
		_layout.getForm().verifyChangeStyle();
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
		_layout.inviaMail(callback);
	}

	@Override
	public void salvaBozza(DSCallback callback) {
		
	}
}