package it.eng.auriga.ui.module.layout.client.archivio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.DateDisplayFormatter;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.AltreVieItem;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.SelezionaUfficioItems;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.SelezionaUtenteItems;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;


public class PraticaPregressaDetail extends FolderCustomDetail {
	
	private final int TITTLE_ALIGN_WIDTH = 150;
	
	protected AzioneSuPraticaPregressaPopup azioneSuPraticaPregressaPopup;

	protected PraticaPregressaDetail instance;

	protected DynamicForm protocolloForm;
	protected ExtendedTextItem numProtocolloGeneraleItem;
	protected AnnoItem annoProtocolloGeneraleItem;
	protected ExtendedTextItem siglaProtocolloSettoreItem;
	protected ExtendedTextItem numProtocolloSettoreItem;
	protected ExtendedNumericItem subProtocolloSettoreItem;
	protected AnnoItem annoProtocolloSettoreItem;
	protected HiddenItem attivaTimbroTipologiaItem;
	
	protected CheckboxItem flgDocumentazioneCompletaItem;
	
	protected HeaderDetailSection altrevieSection;
	protected DynamicForm altreVieForm;
	protected AltreVieItem altreVieItem;
	
	protected DetailSection documentiSection;
	protected DynamicForm documentiForm;
	protected AllegatiItem documentiItem;
	
	protected PraticaPregressaDetailSection datiArchivioPrelievoSection;
	
	protected DynamicForm datiArchivioPrelievoForm;
	protected DynamicForm datiArchivioPrelievoUfficioPrelievoForm;
	protected DynamicForm datiArchivioPrelievoResponsabilePrelievoForm;
	protected DynamicForm datiArchivioPrelievoNoteForm;
	protected DynamicForm datiCompetenzaUrbanisticaForm;
	
	private ExtendedTextItem classificaItem;
	private ExtendedTextItem udcItem;
	private ExtendedTextItem presenzaInArchivioItem;
	private SelezionaUfficioItems ufficioPrelievoItems;
	private DateItem dataPrelievoItem;
	private SelezionaUtenteItems responsabilePrelievoItems;
	private TextAreaItem noteUffRichiedenteItem;
	private TextAreaItem noteCittadellaItem;
	private SelectItem selectCompetenzaDiUrbanisticaItem;
	private SelectItem selectCartaceoReperibileItem;
	private SelectItem selectInArchivioItem;
	
	
	
	protected String nomeFolderType;
	protected Boolean flgTipoFolderConVie;
	protected String dictionaryEntrySezione;
	
	// Toolstrip contenente i bottoni di dettaglio
	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton editButton;
	public DetailToolStripButton saveButton;
	protected DetailToolStripButton reloadDetailButton;
	protected DetailToolStripButton undoButton;
	protected DetailToolStripButton registraPrelievoButton;
	protected DetailToolStripButton modificaPrelievoButton;
	protected DetailToolStripButton eliminaPrelievoButton;
	protected DetailToolStripButton registraRestituzionePrelievoButton;
	
	// Boolean che indica se sono state fatte azioni sul prelievo
	private Boolean eseguitaAzioneSuPrelievo = false;
	
	public Boolean getEseguitaAzioneSuPrelievo() {
		return eseguitaAzioneSuPrelievo;
	}
	
	public void setEseguitaAzioneSuPrelievo(Boolean eseguitaAzioneSuPrelievo) {
		this.eseguitaAzioneSuPrelievo = eseguitaAzioneSuPrelievo;
	}

	public PraticaPregressaDetail(String nomeEntita) {
		
		super(nomeEntita);

		instance = this;
		
	}
	
	@Override
	protected void init() {

		setPaddingAsLayoutMargin(false);

		createTabSet();		
		createDetailToolStrip();
		
		VLayout mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(tabSet);
		mainLayout.addMember(detailToolStrip);		
		
		setMembers(mainLayout);
		
	}
	
	public boolean showDetailToolStrip() {
		return getLayout() == null;
	}
	
	protected void createDetailToolStrip() {

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
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						onSaveButtonClick();
					}
				});
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
		
		if(registraPrelievoButton == null) {	
			registraPrelievoButton = new DetailToolStripButton("Registra prelievo", "richiesteAccessoAtti/azioni/registraPrelievo.png");
			registraPrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					onClickRegistraPrelievoButton();
				}
			});
		}
		
		if(modificaPrelievoButton == null) {	
			modificaPrelievoButton = new DetailToolStripButton("Modifica prelievo", "richiesteAccessoAtti/azioni/modificaPrelievo.png");
			modificaPrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					onClickModificaPrelievoButton();
				}
			});
		}
		
		if(eliminaPrelievoButton == null) {	
			eliminaPrelievoButton = new DetailToolStripButton("Elimina prelievo", "richiesteAccessoAtti/azioni/eliminaPrelievo.png");
			eliminaPrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					onClickEliminaPrelievoButton();
				}
			});
		}
		
		if(registraRestituzionePrelievoButton == null) {	
			registraRestituzionePrelievoButton = new DetailToolStripButton("Registra restituzione", "richiesteAccessoAtti/azioni/registraRestituzione.png");
			registraRestituzionePrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					onClickRegistraRestituzionePrelievoButton();
				}
			});
		}
		
		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right		
		detailToolStrip.addButton(editButton);
		detailToolStrip.addButton(saveButton);
		detailToolStrip.addButton(reloadDetailButton);
		detailToolStrip.addButton(undoButton);
		
		detailToolStrip.addButton(registraPrelievoButton);
		detailToolStrip.addButton(modificaPrelievoButton);
		detailToolStrip.addButton(eliminaPrelievoButton);
		detailToolStrip.addButton(registraRestituzionePrelievoButton);
	}
	
	public void reload(final DSCallback callback) {
		if (this.mode.equals("new")) {
			editNewRecord();
			getValuesManager().clearErrors(true);
		} else {
			Record lRecordToLoad = new Record(vm.getValues());
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
			lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						editRecord(record, recordNum);
						getValuesManager().clearErrors(true);
						callback.execute(response, null, new DSRequest());
					} else {
						Layout.addMessage(new MessageBean("Si è verificato un errore durante il caricamento del dettaglio", "", MessageType.ERROR));
					}
				}
			});			
		}
	}
	
	@Override
	public void clearTabErrors() {
		clearTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors() {
		showTabErrors(tabSet);
	}
	
	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate(); // perche estendo FolderCustomDetail
		if(!hasProtocolloGenerale() && !hasProtocolloSettore()) {
			protocolloForm.setFieldErrors("titleProtocolloGenerale", "Almeno uno tra Protocollo Generale e Protocollo Settore deve essere valorizzato. Inserire Numero e Anno.");
			protocolloForm.setFieldErrors("titleProtocolloSettore", "Almeno uno tra Protocollo Generale e Protocollo Settore deve essere valorizzato. Inserire Registro, Numero e Anno.");
			valid = false;					
		}	
		return valid;
	}

	public void onSaveButtonClick() {
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				
				final Record lRecordToSave = getRecordToSave();
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
				if (validate()) {			
					Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
					try {
						DSCallback callback = new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									final Record lRecordSaved = response.getData()[0];
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
									lGwtRestDataSource.getData(lRecordSaved, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												Record record = response.getData()[0];
												editRecord(record, recordNum);
												getValuesManager().clearErrors(true);
												viewMode();
												Layout.hideWaitPopup();
												Layout.addMessage(new MessageBean("Salvataggio effettuato con successo", "", MessageType.INFO));
											} else {
												Layout.hideWaitPopup();
											}
										}
									});
								} else {							
									Layout.hideWaitPopup();
								}
							}
						};
						if (lRecordToSave.getAttribute("idUdFolder") != null && !"".equals(lRecordToSave.getAttribute("idUdFolder"))) {
							lGwtRestDataSource.updateData(lRecordToSave, callback);
						} else {
							lGwtRestDataSource.addData(lRecordToSave, callback);
						}					
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
					
				} else {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
				}
			}
		});		
	}

	@Override
	public String getTitleTabDatiFascicolo() {
		return "Dati principali";
	}
	
	@Override
	public VLayout getLayoutTabDatiFolder() {
		
		VLayout layoutDatiPrincipali = new VLayout(5);
		
		estremiForm = new DynamicForm();
		estremiForm.setWidth100();
		estremiForm.setValuesManager(vm);
		estremiForm.setVisibility(Visibility.HIDDEN);
		
		idFolderAppItem = new HiddenItem("idFolderApp");
		idUdFolderItem = new HiddenItem("idUdFolder");
		nomeItem = new HiddenItem("nome");
		flgUdFolderItem = new HiddenItem("flgUdFolder");
		flgFascTitolarioItem = new HiddenItem("flgFascTitolario");
		flgFascTitolarioItem.setValue(false);

		estremiForm.setFields(idFolderAppItem, idUdFolderItem, nomeItem, flgUdFolderItem, flgFascTitolarioItem);

		protocolloForm = new DynamicForm();
		protocolloForm.setValuesManager(vm);
		protocolloForm.setWidth100();
		protocolloForm.setPadding(5);
		protocolloForm.setWrapItemTitles(false);
		protocolloForm.setNumCols(18);
		protocolloForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		protocolloForm.setTabSet(tabSet);
		protocolloForm.setTabID("HEADER");

		// Protocollo Generale
		
		TitleStaticTextItem titleProtocolloGenerale = new TitleStaticTextItem("<b>Protocollo Generale</b>", 125);
		titleProtocolloGenerale.setName("titleProtocolloGenerale");
		titleProtocolloGenerale.setStartRow(true);
		
		numProtocolloGeneraleItem = new ExtendedTextItem("numProtocolloGenerale", "N°") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);  
				setTabIndex(canEdit ? 0 : -1);				
			}
		};
		numProtocolloGeneraleItem.setWidth(100);
		numProtocolloGeneraleItem.setLength(50);
		numProtocolloGeneraleItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloGenerale();
			}
		});

		annoProtocolloGeneraleItem = new AnnoItem("annoProtocolloGenerale", "Anno") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);  
				setTabIndex(canEdit ? 0 : -1);				
			}
		};
		annoProtocolloGeneraleItem.setWidth(100);		
		annoProtocolloGeneraleItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloGenerale();
			}
		});

		// Protocollo Settore
		
		TitleStaticTextItem titleProtocolloSettore = new TitleStaticTextItem("<b>Protocollo Settore</b>", 125);
		titleProtocolloSettore.setName("titleProtocolloSettore");
		titleProtocolloSettore.setStartRow(true);
				
		numProtocolloSettoreItem = new ExtendedTextItem("numProtocolloSettore", "N°") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);  
				setTabIndex(canEdit ? 0 : -1);				
			}
		};
		numProtocolloSettoreItem.setWidth(100);
		numProtocolloSettoreItem.setLength(50);
		numProtocolloSettoreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloSettore();
			}
		});
		
		siglaProtocolloSettoreItem = new ExtendedTextItem("siglaProtocolloSettore", "Registro") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);  
				setTabIndex(canEdit ? 0 : -1);				
			}
		};
		siglaProtocolloSettoreItem.setColSpan(1);
		siglaProtocolloSettoreItem.setWidth(100);
		siglaProtocolloSettoreItem.setLength(3);
		siglaProtocolloSettoreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloSettore();
			}
		});
		
		annoProtocolloSettoreItem = new AnnoItem("annoProtocolloSettore", "Anno") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);  
				setTabIndex(canEdit ? 0 : -1);				
			}
		};
		annoProtocolloSettoreItem.setWidth(100);
		annoProtocolloSettoreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloSettore();
			}
		});
		
		subProtocolloSettoreItem = new ExtendedNumericItem("subProtocolloSettore", "Sub", false) {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);  
				setTabIndex(canEdit ? 0 : -1);				
			}
		};
		subProtocolloSettoreItem.setWidth(100);
		subProtocolloSettoreItem.setLength(50);
		subProtocolloSettoreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloSettore();
			}
		});
		
		attivaTimbroTipologiaItem = new HiddenItem("attivaTimbroTipologia");

		protocolloForm.setFields(
			titleProtocolloGenerale, numProtocolloGeneraleItem, annoProtocolloGeneraleItem,		
			titleProtocolloSettore, numProtocolloSettoreItem, siglaProtocolloSettoreItem, annoProtocolloSettoreItem, subProtocolloSettoreItem, attivaTimbroTipologiaItem
		);
		
		estremiSection = new PraticaPregressaHeaderDetailSection("Estremi pratica", true, true, true, estremiForm, protocolloForm);
		
		// Oggetto pratica
		
		datiPrincipaliForm = new DynamicForm();
		datiPrincipaliForm.setValuesManager(vm);
		datiPrincipaliForm.setWidth("*");
		datiPrincipaliForm.setHeight("5");
		datiPrincipaliForm.setPadding(5);
		datiPrincipaliForm.setWrapItemTitles(false);
		datiPrincipaliForm.setNumCols(8);
		datiPrincipaliForm.setTabSet(tabSet);
		datiPrincipaliForm.setTabID("HEADER");

		descContenutiFascicoloItem = new TextAreaItem("descContenutiFascicolo", I18NUtil.getMessages().archivio_detail_descContenutiFascicoloItem_title());
		descContenutiFascicoloItem.setStartRow(true);
		descContenutiFascicoloItem.setEndRow(false);
		descContenutiFascicoloItem.setLength(800);
		descContenutiFascicoloItem.setHeight(40);
		descContenutiFascicoloItem.setWidth(650);
		descContenutiFascicoloItem.setColSpan(8);
		descContenutiFascicoloItem.setShowTitle(false);
		
		flgDocumentazioneCompletaItem = new CheckboxItem("flgDocumentazioneCompleta", "Documentazione completa");
		flgDocumentazioneCompletaItem.setStartRow(true);
		flgDocumentazioneCompletaItem.setColSpan(1);
		flgDocumentazioneCompletaItem.setWidth(100);
		flgDocumentazioneCompletaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				datiCompetenzaUrbanisticaForm.markForRedraw();
			}
		});
		
		datiPrincipaliForm.setItems(descContenutiFascicoloItem, flgDocumentazioneCompletaItem);
		
		datiprincipaliSection = new PraticaPregressaHeaderDetailSection("Oggetto pratica", true, true, false, datiPrincipaliForm);
		
		// Tipo
		
		folderTypeForm = new DynamicForm();
		folderTypeForm.setValuesManager(vm);
		folderTypeForm.setWidth("*");
		folderTypeForm.setHeight("5");
		folderTypeForm.setPadding(5);
		folderTypeForm.setWrapItemTitles(false);
		folderTypeForm.setNumCols(8);
		folderTypeForm.setTabSet(tabSet);
		folderTypeForm.setTabID("HEADER");

		final GWTRestDataSource idFolderTypeDS = new GWTRestDataSource("LoadComboTipoFolderDataSource", "idFolderType", FieldType.TEXT);

		idFolderTypeItem = new SelectItem("idFolderType", I18NUtil.getMessages().archivio_list_tipoField_title());
		idFolderTypeItem.setShowTitle(false);
		idFolderTypeItem.setWidth(300);
		idFolderTypeItem.setColSpan(4);
		idFolderTypeItem.setAlign(Alignment.CENTER);
		idFolderTypeItem.setValueField("idFolderType");
		idFolderTypeItem.setDisplayField("descFolderType");
		idFolderTypeItem.setOptionDataSource(idFolderTypeDS);
		idFolderTypeItem.setAllowEmptyValue(true);
		idFolderTypeItem.setEndRow(false);

		folderTypeForm.setItems(idFolderTypeItem);
		
		foldertypeSection = new PraticaPregressaHeaderDetailSection("Tipo", true, true, false, folderTypeForm);		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {
			foldertypeSection.setVisibility(Visibility.HIDDEN);
		}

		// Indirizzo di riferimento
		
		altreVieForm = new DynamicForm();
		altreVieForm.setValuesManager(vm);
		altreVieForm.setWidth("100%");
		altreVieForm.setHeight("5");
		altreVieForm.setPadding(5);
		altreVieForm.setTabSet(tabSet);
		altreVieForm.setTabID("HEADER");

		altreVieItem = new AltreVieItem() {

			@Override
			public boolean showFlgFuoriComune() {
				return false;
			}
			
			@Override
			public boolean getFlgFuoriComune() {
				return false;
			}
			
			@Override
			public boolean isIndirizzoObbligatorioInCanvas() {
				return showAltreVieSection();
			}
			
			@Override
			public boolean isCivicoObbligatorioInCanvas() {
				return showAltreVieSection();
			}
			
			@Override
			public boolean isForceCapNonObbligatorioInCanvas() {
				return true;
			}
			
			@Override
			public boolean getShowStato() {
				return false;
			}
		};
		altreVieItem.setName("listaAltreVie");
		altreVieItem.setShowTitle(false);
		altreVieItem.setAttribute("obbligatorio", true);
		altreVieItem.setNotReplicable(true);

		altreVieForm.setFields(altreVieItem);
		
		altrevieSection = new PraticaPregressaHeaderDetailSection("Indirizzo di riferimento", true, true, true, altreVieForm);				
			
		// Documenti
				
		documentiForm = new DynamicForm();
		documentiForm.setValuesManager(vm);
		documentiForm.setWidth("100%");
		documentiForm.setPadding(5);
		documentiForm.setTabSet(tabSet);
		documentiForm.setTabID("HEADER");

		documentiItem = new AllegatiItem() {
			
			@Override
			public String getTitleTipiFileAllegato() {
				return "Sezione";
			}
			
			@Override
			public String getNroDocumentoBarcodeLabel() {
				return "N° protocollo pratica";
			}
			
			@Override
			public boolean isAttivaTimbroTipologia() {
				return vm.getValue("attivaTimbroTipologia") != null && ((Boolean) vm.getValue("attivaTimbroTipologia"));
			}
			
			@Override
			public boolean isAttivaVociBarcode() {
				return idUdFolderItem.getValue() != null && !"".equals((String) idUdFolderItem.getValue());
			}
			
			@Override
			public boolean isFromFolderPraticaPregressa() {
				return true;
			}

			@Override
			public GWTRestDataSource getTipiFileAllegatoDataSource() {
				GWTRestDataSource lLoadComboSezionePraticaPregressaDataSource = new GWTRestDataSource("LoadComboSezionePraticaPregressaDataSource", "idTipoFileAllegato", FieldType.TEXT, true);
				lLoadComboSezionePraticaPregressaDataSource.addParam("dictionaryEntrySezione", getDictionaryEntrySezione());
				return lLoadComboSezionePraticaPregressaDataSource;
			}
			
			@Override
			public String getDictionaryEntrySezione() {
				return getDictionaryEntrySezioneDocumenti();
			}

			@Override
			public boolean showFilterEditorInTipiFileAllegato() {
				return false;
			}

			@Override
			public String getTitleFlgParteDispositivo() {
				return "parte integrante";
			}

			@Override
			public boolean canBeEditedByApplet() {
				return true;
			}

			@Override
			public Record getCanvasDefaultRecord() {
				Record lRecord = new Record();	
				lRecord.setAttribute("flgOriginaleCartaceo", true);
				return lRecord;								
			}
			
			public String getIdFolder() {
				return idUdFolderItem != null ? (String) idUdFolderItem.getValue() : null;
			}
			
			@Override
			public boolean isHideTimbraInAltreOperazioniButton() {
				return false;
			}
		};
		documentiItem.setName("listaDocumentiIstruttoria");
		documentiItem.setShowTitle(false);

		documentiForm.setFields(documentiItem);
		
		documentiSection = new PraticaPregressaDetailSection("Documenti", true, false, false, documentiForm);
		
		// Note		
		
		altriDatiForm = new DynamicForm();
		altriDatiForm.setValuesManager(vm);
		altriDatiForm.setWidth("100%");
		altriDatiForm.setHeight("5");
		altriDatiForm.setPadding(5);
		altriDatiForm.setWrapItemTitles(false);
		altriDatiForm.setNumCols(8);
		altriDatiForm.setTabSet(tabSet);
		altriDatiForm.setTabID("HEADER");

		noteFascicoloItem = new TextAreaItem("noteFascicolo");
		noteFascicoloItem.setLength(800);
		noteFascicoloItem.setHeight(40);
		noteFascicoloItem.setColSpan(8);
		noteFascicoloItem.setWidth(650);
		noteFascicoloItem.setShowTitle(false);
		
		altriDatiForm.setItems(noteFascicoloItem);
		
		altridatiSection = new PraticaPregressaDetailSection("Note", true, true, false, altriDatiForm);
		
		// Dettaglio archivio e prelievo
		
		datiArchivioPrelievoForm = new DynamicForm();
		datiArchivioPrelievoForm.setValuesManager(vm);
		datiArchivioPrelievoForm.setWidth("100%");
		datiArchivioPrelievoForm.setHeight("5");
		datiArchivioPrelievoForm.setPadding(5);
		datiArchivioPrelievoForm.setWrapItemTitles(false);
		datiArchivioPrelievoForm.setNumCols(16);
		datiArchivioPrelievoForm.setHeight(1);
		datiArchivioPrelievoForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*" );
		datiArchivioPrelievoForm.setTabSet(tabSet);
		datiArchivioPrelievoForm.setTabID("HEADER");
		
//		classificaItem = new ExtendedTextItem("classifPregressa", setTitleAlign("Classifica", TITTLE_ALIGN_WIDTH, false));
		classificaItem = new ExtendedTextItem("classifPregressa", "Classifica");
		
		udcItem = new ExtendedTextItem("udc", "Unità di carico (UDC)");
		udcItem.setWidth(100);
		udcItem.setLength(50);
		
		presenzaInArchivioItem = new ExtendedTextItem("presenzaInArchivioDeposito", "Presenza in Archivio");
		presenzaInArchivioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				// Lo nascondo sempre, andrebbe eliminato
				return false;
			}
		});
		
		datiArchivioPrelievoForm.setItems(classificaItem, udcItem, presenzaInArchivioItem);
		
		datiArchivioPrelievoUfficioPrelievoForm = new DynamicForm();
		datiArchivioPrelievoUfficioPrelievoForm.setValuesManager(vm);
		datiArchivioPrelievoUfficioPrelievoForm.setWidth("100%");
		datiArchivioPrelievoUfficioPrelievoForm.setHeight("5");
		datiArchivioPrelievoUfficioPrelievoForm.setPadding(5);
		datiArchivioPrelievoUfficioPrelievoForm.setWrapItemTitles(false);
		datiArchivioPrelievoUfficioPrelievoForm.setNumCols(16);
		datiArchivioPrelievoUfficioPrelievoForm.setHeight(1);
		datiArchivioPrelievoUfficioPrelievoForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*" );
		datiArchivioPrelievoUfficioPrelievoForm.setTabSet(tabSet);
		datiArchivioPrelievoUfficioPrelievoForm.setTabID("HEADER");
		
		TitleItem titoloUfficioItem = new TitleItem(setTitleAlign("Prelievo effettuato dall'ufficio", TITTLE_ALIGN_WIDTH, false));
		titoloUfficioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isDatiPrelievoToShow();
			}
		});
				
		ufficioPrelievoItems = new SelezionaUfficioItems(datiArchivioPrelievoUfficioPrelievoForm, "datiPrelievoDaArchivioIdUO", "datiPrelievoDaArchivioDesUO", "datiPrelievoDaArchivioCodUO", "datiPrelievoDaArchivioOrganigrammaUO"){
			
			@Override
			protected boolean codRapidoItemShowIfCondition() {
				return isDatiPrelievoToShow();
			}
			
			@Override
			protected boolean codRapidoItemValidator() {
				return true;
			}
			
			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition() {
				return false;
			}
			
			@Override
			protected boolean organigrammaItemShowIfCondition() {
				return isDatiPrelievoToShow();
			}
				
		};
		
		dataPrelievoItem = new DateItem("datiPrelievoDaArchivioDataPrelievo", "in data");
		dataPrelievoItem.setEndRow(true);
		dataPrelievoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isDatiPrelievoToShow();
			}
		});
		
		List<FormItem> itemsUfficioPrelievo = new ArrayList<FormItem>();
		itemsUfficioPrelievo.add(titoloUfficioItem);
		itemsUfficioPrelievo.addAll(ufficioPrelievoItems);
		itemsUfficioPrelievo.add(dataPrelievoItem);
		
		datiArchivioPrelievoUfficioPrelievoForm.setFields(itemsUfficioPrelievo.toArray(new FormItem[itemsUfficioPrelievo.size()]));
		
		datiArchivioPrelievoResponsabilePrelievoForm = new DynamicForm();
		datiArchivioPrelievoResponsabilePrelievoForm.setValuesManager(vm);
		datiArchivioPrelievoResponsabilePrelievoForm.setWidth("100%");
		datiArchivioPrelievoResponsabilePrelievoForm.setHeight("5");
		datiArchivioPrelievoResponsabilePrelievoForm.setPadding(5);
		datiArchivioPrelievoResponsabilePrelievoForm.setWrapItemTitles(false);
		datiArchivioPrelievoResponsabilePrelievoForm.setNumCols(16);
		datiArchivioPrelievoResponsabilePrelievoForm.setHeight(1);
		datiArchivioPrelievoResponsabilePrelievoForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*" );
		datiArchivioPrelievoResponsabilePrelievoForm.setTabSet(tabSet);
		datiArchivioPrelievoResponsabilePrelievoForm.setTabID("HEADER");
		
		TitleItem titoloRespponsabileItem = new TitleItem(setTitleAlign("Responsabile del prelievo", TITTLE_ALIGN_WIDTH, false));
		titoloRespponsabileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isDatiPrelievoToShow();
			}
		});
		
		responsabilePrelievoItems = new SelezionaUtenteItems(datiArchivioPrelievoResponsabilePrelievoForm, "datiPrelievoDaArchivioIdUserResponsabile", "datiPrelievoDaArchivioUsernameResponsabile", 
				"datiPrelievoDaArchivioCodRapidoResponsabile", "datiPrelievoDaArchivioCognomeResponsabile", "datiPrelievoDaArchivioNomeResponsabile", "datiPrelievoDaArchivioCodiceFiscaleResponsabile", 
				"DatiPrelievoDaArchivioEmailResponsabile", "DatiPrelievoDaArchivioTelefonoResponsabile"){
			
			@Override
			protected boolean codRapidoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean lookupRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isDatiPrelievoToShow();
			}

			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isDatiPrelievoToShow();
			}

			@Override
			protected boolean nomeItemRequiredIfValidator(FormItem formItem, Object value) {
				return false;
			}

			@Override
			protected boolean nomeItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isDatiPrelievoToShow();
			}

			@Override
			protected boolean cognomeItemRequiredIfValidator(FormItem formItem, Object value) {
				return false;
			}

			@Override
			protected boolean cognomeItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isDatiPrelievoToShow();

			}

			@Override
			protected boolean codiceFiscaleItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean cercaInRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean cercaInOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean telefonoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}
			
			@Override
			protected boolean emailItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}
			
		};
		
		List<FormItem> itemsResponsabilePrelievo = new ArrayList<FormItem>();
		itemsResponsabilePrelievo.add(titoloRespponsabileItem);
		itemsResponsabilePrelievo.addAll(responsabilePrelievoItems);
		
		datiArchivioPrelievoResponsabilePrelievoForm.setFields(itemsResponsabilePrelievo.toArray(new FormItem[itemsResponsabilePrelievo.size()]));
		
		datiArchivioPrelievoNoteForm = new DynamicForm();
		datiArchivioPrelievoNoteForm.setValuesManager(vm);
		datiArchivioPrelievoNoteForm.setWidth("100%");
		datiArchivioPrelievoNoteForm.setHeight("5");
		datiArchivioPrelievoNoteForm.setPadding(5);
		datiArchivioPrelievoNoteForm.setWrapItemTitles(false);
		datiArchivioPrelievoNoteForm.setNumCols(16);
		datiArchivioPrelievoNoteForm.setHeight(1);
		datiArchivioPrelievoNoteForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*" );
		datiArchivioPrelievoNoteForm.setTabSet(tabSet);
		datiArchivioPrelievoNoteForm.setTabID("HEADER");
		
		noteUffRichiedenteItem = new TextAreaItem("datiPrelievoDaArchivioNoteRichiedente", setTitleAlign("Note uff. richiedente", TITTLE_ALIGN_WIDTH, false));
		noteUffRichiedenteItem.setWidth(300);
		noteUffRichiedenteItem.setHeight(50);
		noteUffRichiedenteItem.setStartRow(true);
		noteUffRichiedenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isDatiPrelievoToShow();
			}
		});
	
		noteCittadellaItem = new TextAreaItem("datiPrelievoDaArchivioNoteArchivio", setTitleAlign("Note cittadella", TITTLE_ALIGN_WIDTH, false));
		noteCittadellaItem.setWidth(300);
		noteCittadellaItem.setHeight(50);
		noteCittadellaItem.setStartRow(false);
		noteCittadellaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isDatiPrelievoToShow();
			}
		});
					
		datiArchivioPrelievoNoteForm.setItems(noteUffRichiedenteItem, noteCittadellaItem);
		
		datiCompetenzaUrbanisticaForm = new DynamicForm();
		datiCompetenzaUrbanisticaForm.setValuesManager(vm);
		datiCompetenzaUrbanisticaForm.setWidth("100%");
		datiCompetenzaUrbanisticaForm.setHeight("5");
		datiCompetenzaUrbanisticaForm.setPadding(5);
		datiCompetenzaUrbanisticaForm.setWrapItemTitles(false);
		datiCompetenzaUrbanisticaForm.setNumCols(16);
		datiCompetenzaUrbanisticaForm.setHeight(1);
		datiCompetenzaUrbanisticaForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*" );
		datiCompetenzaUrbanisticaForm.setTabSet(tabSet);
		datiCompetenzaUrbanisticaForm.setTabID("HEADER");
		
		final GWTRestDataSource selectCompetenzaDiUrbanisticaDS = new GWTRestDataSource("LoadComboCompetenzaDiUrbanisticaDataSource", "key", FieldType.TEXT);
//		selectCompetenzaDiUrbanisticaItem = new SelectItem("competenzaPratica", setTitleAlign("Competenza di Urbanistica", TITTLE_ALIGN_WIDTH, false));
		selectCompetenzaDiUrbanisticaItem = new SelectItem("competenzaPratica", "Competenza di Urbanistica");
		selectCompetenzaDiUrbanisticaItem.setValueField("key");
		selectCompetenzaDiUrbanisticaItem.setDisplayField("value");
		selectCompetenzaDiUrbanisticaItem.setOptionDataSource(selectCompetenzaDiUrbanisticaDS);
		selectCompetenzaDiUrbanisticaItem.setWidth(200);
		selectCompetenzaDiUrbanisticaItem.setStartRow(true);
		selectCompetenzaDiUrbanisticaItem.setRequired(true);
		
		selectCompetenzaDiUrbanisticaItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent arg0) {
				datiCompetenzaUrbanisticaForm.markForRedraw();
			}
		});
		
		final GWTRestDataSource selectCartaceoReperibileDS = new GWTRestDataSource("LoadComboCartaceoReperibileDataSource", "key", FieldType.TEXT);
		selectCartaceoReperibileItem = new SelectItem("reperibilitaCartaceoPratica", "Cartaceo reperibile");
		selectCartaceoReperibileItem.setValueField("key");
		selectCartaceoReperibileItem.setDisplayField("value");
		selectCartaceoReperibileItem.setOptionDataSource(selectCartaceoReperibileDS);
		selectCartaceoReperibileItem.setWidth(200);
		selectCartaceoReperibileItem.setStartRow(false);
		selectCartaceoReperibileItem.setAllowEmptyValue(true);

		selectCartaceoReperibileItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent arg0) {
				datiCompetenzaUrbanisticaForm.markForRedraw();
			}
		});
		selectCartaceoReperibileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				setRequiredInItem(selectCartaceoReperibileItem, "Cartaceo reperibile", isSelectCartaceoReperibileItemObbligatorio());
				return isSelectCartaceoReperibileItemToShow();
			}
		});
		
		selectCartaceoReperibileItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isSelectCartaceoReperibileItemObbligatorio();
			}
		}));
				
		final GWTRestDataSource selectInArchivioDS = new GWTRestDataSource("LoadComboInArchivioDataSource", "key", FieldType.TEXT);
		selectInArchivioItem = new SelectItem("archivioPresCartaceoPratica", "In archivio");
		selectInArchivioItem.setValueField("key");
		selectInArchivioItem.setDisplayField("value");
		selectInArchivioItem.setOptionDataSource(selectInArchivioDS);
		selectInArchivioItem.setWidth(200);
		selectInArchivioItem.setStartRow(false);
		selectInArchivioItem.setAllowEmptyValue(true);
		
		selectInArchivioItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent arg0) {
				datiCompetenzaUrbanisticaForm.markForRedraw();
			}
		});

		selectInArchivioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				setRequiredInItem(selectInArchivioItem, "In archivio", isSelectInArchivioItemObbligatorio());
				return isSelectInArchivioItemToShow();
			}
		});
		
		selectInArchivioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isSelectInArchivioItemObbligatorio();
			}
		}));
		
		datiCompetenzaUrbanisticaForm.setItems(selectCompetenzaDiUrbanisticaItem, selectCartaceoReperibileItem, selectInArchivioItem);
		
		datiArchivioPrelievoSection = new PraticaPregressaDetailSection("Dati archivio", true, true, false, datiArchivioPrelievoForm, datiCompetenzaUrbanisticaForm, datiArchivioPrelievoUfficioPrelievoForm, datiArchivioPrelievoResponsabilePrelievoForm, datiArchivioPrelievoNoteForm);
				
		List<Canvas> layoutDatiPrincipaliMembers = new ArrayList<Canvas>();
		layoutDatiPrincipaliMembers.add(estremiSection);		
		layoutDatiPrincipaliMembers.add(foldertypeSection);
		layoutDatiPrincipaliMembers.add(altrevieSection);
		layoutDatiPrincipaliMembers.add(datiprincipaliSection);
		layoutDatiPrincipaliMembers.add(documentiSection);		
		layoutDatiPrincipaliMembers.add(altridatiSection);
		layoutDatiPrincipaliMembers.add(datiArchivioPrelievoSection);
		
		layoutDatiPrincipali.setMembers(layoutDatiPrincipaliMembers.toArray(new Canvas[layoutDatiPrincipaliMembers.size()]));
		
		return layoutDatiPrincipali;
	}
	
	protected boolean hasProtocolloGenerale() {
		
		boolean hasNumProtocolloGenerale = numProtocolloGeneraleItem.getValue() != null && !"".equals(numProtocolloGeneraleItem.getValue());
		boolean hasAnnoProtocolloGenerale = annoProtocolloGeneraleItem.getValue() != null && !"".equals(annoProtocolloGeneraleItem.getValue());
		return hasNumProtocolloGenerale && hasAnnoProtocolloGenerale;		
	}
	
	protected boolean hasProtocolloSettore() {
		
		boolean hasSiglaProtocolloSettore = siglaProtocolloSettoreItem.getValue() != null && !"".equals(siglaProtocolloSettoreItem.getValue());
		boolean hasNumProtocolloSettore = numProtocolloSettoreItem.getValue() != null && !"".equals(numProtocolloSettoreItem.getValue());
		boolean hasAnnoProtocolloSettore = annoProtocolloSettoreItem.getValue() != null && !"".equals(annoProtocolloSettoreItem.getValue());
		return hasSiglaProtocolloSettore && hasNumProtocolloSettore && hasAnnoProtocolloSettore;		
	}
	
	protected void recuperaDatiProtocolloGenerale() {	
		
		if(hasProtocolloGenerale()) {		
			Record lRecord = new Record();		
			lRecord.setAttribute("nroProtocolloPregresso", numProtocolloGeneraleItem.getValueAsString());
			lRecord.setAttribute("siglaProtocolloPregresso", "PG");			
			lRecord.setAttribute("annoProtocolloPregresso", annoProtocolloGeneraleItem.getValueAsString());
			lRecord.setAttribute("oggetto", descContenutiFascicoloItem.getValueAsString());
			lRecord.setAttribute("listaAltreVie", new Record(vm.getValues()).getAttributeAsRecordList("listaAltreVie"));
			recuperaDatiProtocolloPGWeb(lRecord);
		}
	}
		
	protected void recuperaDatiProtocolloSettore() {
		
		if(hasProtocolloSettore()) {		
			Record lRecord = new Record();		
			lRecord.setAttribute("nroProtocolloPregresso", numProtocolloSettoreItem.getValueAsString());
			lRecord.setAttribute("siglaProtocolloPregresso", siglaProtocolloSettoreItem.getValueAsString());
			lRecord.setAttribute("annoProtocolloPregresso", annoProtocolloSettoreItem.getValueAsString());
			lRecord.setAttribute("subProtocolloPregresso", subProtocolloSettoreItem.getValueAsString());
			lRecord.setAttribute("oggetto", descContenutiFascicoloItem.getValueAsString());
			lRecord.setAttribute("listaAltreVie", new Record(vm.getValues()).getAttributeAsRecordList("listaAltreVie"));
			recuperaDatiProtocolloPGWeb(lRecord);
		}
	}
	
	protected void recuperaDatiProtocolloPGWeb(Record lRecord) {	
		
		if(lRecord != null) {
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lGwtRestDataSource.executecustom("recuperaDatiProtocolloPGWeb", lRecord, new DSCallback() {	
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {					
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
						Record record = response.getData()[0];						
						descContenutiFascicoloItem.setValue(record.getAttribute("oggetto"));		
						if(flgTipoFolderConVie != null && flgTipoFolderConVie) {
							Record lRecordAltreVie = new Record();
							lRecordAltreVie.setAttribute("listaAltreVie", record.getAttributeAsRecordList("listaAltreVie"));
							altreVieForm.setValues(lRecordAltreVie.toMap());
						}
					}
				}
			});		
		}
	}
	
	private boolean isDatiPrelievoToShow() {
		String stato = presenzaInArchivioItem.getValue() != null ? (String) presenzaInArchivioItem.getValue() : "";
		return stato.toLowerCase().startsWith("prelevato");
	}
	
	@Override
	public void editNewRecord() {

		super.editNewRecord();
		
		estremiSection.setTitle("Estremi pratica");
		this.nomeFolderType = null;
		this.flgTipoFolderConVie = null;
		this.dictionaryEntrySezione = null;
		
		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
		}
	}
	
	@Override
	public void editNewRecord(@SuppressWarnings("rawtypes") Map initialValues) {

		super.editNewRecord(initialValues);

		estremiSection.setTitle("Estremi pratica");
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {
			this.nomeFolderType = new Record(initialValues).getAttribute("nomeFolderType");
			if(nomeFolderType != null && !"".equals(nomeFolderType)) {
				estremiSection.setTitle("Estremi pratica - Tipo : " + nomeFolderType);				
			} 
			this.flgTipoFolderConVie = new Record(initialValues).getAttributeAsBoolean("flgTipoFolderConVie");
			this.dictionaryEntrySezione = new Record(initialValues).getAttribute("dictionaryEntrySezione");
		}
		
		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
		}
	}
	
	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
		
		estremiSection.setTitle("Estremi pratica");
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {
			this.nomeFolderType = record.getAttribute("nomeFolderType");
			if(nomeFolderType != null && !"".equals(nomeFolderType)) {
				estremiSection.setTitle("Estremi pratica - Tipo : " + nomeFolderType);
			}
			this.flgTipoFolderConVie = record.getAttributeAsBoolean("flgTipoFolderConVie");
			this.dictionaryEntrySezione = record.getAttribute("dictionaryEntrySezione");
		}
		
		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
		}
		ufficioPrelievoItems.afterEditRecord(record);
	}
	
	@Override
	public void newMode() {

		super.newMode();
		
		registraPrelievoButton.hide();
		modificaPrelievoButton.hide();
		eliminaPrelievoButton.hide();
		registraRestituzionePrelievoButton.hide();
		
		if (altrevieSection != null) {
			altrevieSection.setVisible(showAltreVieSection());
			altrevieSection.open();
		}
		
		if (documentiSection != null) {
			documentiSection.openIfhasValue();
		}
		
		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
			editButton.hide();
			saveButton.show();
			reloadDetailButton.hide();
			undoButton.hide();
		}
	}
	
	@Override
	public void editMode() {

		super.editMode();
		
		registraPrelievoButton.hide();
		modificaPrelievoButton.hide();
		eliminaPrelievoButton.hide();
		registraRestituzionePrelievoButton.hide();
		
		if (altrevieSection != null) {
			altrevieSection.setVisible(showAltreVieSection());
			altrevieSection.open();
		}
		
		if (documentiSection != null) {
			documentiSection.openIfhasValue();
		}
		
		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
			editButton.hide();
			saveButton.show();
			reloadDetailButton.show();		
			undoButton.show();
		}
	}
	
	@Override
	public void viewMode() {

		super.viewMode();
		
		registraPrelievoButton.hide();
		modificaPrelievoButton.hide();
		eliminaPrelievoButton.hide();
		registraRestituzionePrelievoButton.hide();
		
		if (altrevieSection != null) {
			altrevieSection.setVisible(showAltreVieSection());
			altrevieSection.openIfhasValue();
		}
		
		if (documentiSection != null) {
			documentiSection.openIfhasValue();
		}
		
		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
			editButton.show();
			saveButton.hide();
			reloadDetailButton.hide();		
			undoButton.hide();
		}
		
		if (showRegistraPrelievoButton()) {
			registraPrelievoButton.show();
		}
		if (showModificaPrelievoButton()) {
			modificaPrelievoButton.show();
		}
		if (showEliminaPrelievoButton()) {
			eliminaPrelievoButton.show();
		}
		if (showRegistraRestituzionePrelievoButton()) {
			registraRestituzionePrelievoButton.show();
		}
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		
		presenzaInArchivioItem.setCanEdit(false);
		ufficioPrelievoItems.setCanEdit(false);
		dataPrelievoItem.setCanEdit(false);
		responsabilePrelievoItems.setCanEdit(false);
		noteUffRichiedenteItem.setCanEdit(false);
		noteCittadellaItem.setCanEdit(false);
	}
	
	protected boolean showAltreVieSection() {
		return flgTipoFolderConVie != null && flgTipoFolderConVie;
	}
	
	protected String getDictionaryEntrySezioneDocumenti() {
		return dictionaryEntrySezione;
	}
	
	public boolean showRegistraPrelievoButton() {
		Record record = new Record(vm.getValues());	
		return record.getAttributeAsBoolean("abilRegistrazionePrelievo");
	}
	
	public boolean showModificaPrelievoButton() {
		Record record = new Record(vm.getValues());	
		return record.getAttributeAsBoolean("abilModificaPrelievo");
	}
	
	public boolean showEliminaPrelievoButton() {
		Record record = new Record(vm.getValues());	
		return record.getAttributeAsBoolean("abilEliminazionePrelievo");
	}
	
	public boolean showRegistraRestituzionePrelievoButton() {
		Record record = new Record(vm.getValues());	
		return record.getAttributeAsBoolean("abilRestituzionePrelievo");
	}
	
	public void onClickRegistraPrelievoButton() {
		manageAzioneSuPraticaPregressa("REGISTRA_PRELIEVO");
	}
	
	public void onClickModificaPrelievoButton() {
		manageAzioneSuPraticaPregressa("MODIFICA_PRELIEVO");
	}
	
	public void onClickEliminaPrelievoButton() {
		manageAzioneSuPraticaPregressa("ELIMINA_PRELIEVO");
	}
	
	public void onClickRegistraRestituzionePrelievoButton() {
		manageAzioneSuPraticaPregressa("REGISTRA_RESTITUZIONE");
	}
	
	private void manageAzioneSuPraticaPregressa(final String codOperazione) {

		setSaved(true);
		
		final boolean closeDetailAfterOperation = true;
		
		final Record record = buildRecordPerAzioneSuPratica();
		record.setAttribute("codOperPrelievoSuFascArchivio", codOperazione);
		
		azioneSuPraticaPregressaPopup  = new AzioneSuPraticaPregressaPopup(record) {
			
			@Override
			public void onClickOkButton(DSCallback service) {
				Record recordToPass = new Record();
				
				recordToPass.setAttribute("codOperPrelievoSuFascArchivio", codOperazione);
				
				recordToPass.setAttribute("idUdFolder", estremiForm != null ? estremiForm.getValue("idUdFolder"): null);

				recordToPass.setAttribute("classifPregressa", datiArchivioPrelievoForm != null ? datiArchivioPrelievoForm.getValue("classifPregressa"): null);
				recordToPass.setAttribute("udc", datiArchivioPrelievoForm != null ? datiArchivioPrelievoForm.getValue("udc"): null);
				recordToPass.setAttribute("presenzaInArchivioDeposito", datiArchivioPrelievoForm != null ? datiArchivioPrelievoForm.getValue("presenzaInArchivioDeposito"): null);
				
				recordToPass.setAttribute("datiPrelievoDaArchivioIdUO", datiArchivioPrelievoUfficioPrelievoForm != null ? datiArchivioPrelievoUfficioPrelievoForm.getValue("datiPrelievoDaArchivioIdUO"): null);
				recordToPass.setAttribute("datiPrelievoDaArchivioCodUO", datiArchivioPrelievoUfficioPrelievoForm != null ? datiArchivioPrelievoUfficioPrelievoForm.getValue("datiPrelievoDaArchivioCodUO"): null);
				recordToPass.setAttribute("datiPrelievoDaArchivioDesUO", datiArchivioPrelievoUfficioPrelievoForm != null ? datiArchivioPrelievoUfficioPrelievoForm.getValue("datiPrelievoDaArchivioDesUO"): null);
				recordToPass.setAttribute("datiPrelievoDaArchivioOrganigrammaUO", datiArchivioPrelievoUfficioPrelievoForm != null ? datiArchivioPrelievoUfficioPrelievoForm.getValue("datiPrelievoDaArchivioOrganigrammaUO"): null);
				recordToPass.setAttribute("datiPrelievoDaArchivioDataPrelievo",  datiArchivioPrelievoUfficioPrelievoForm != null ? datiArchivioPrelievoUfficioPrelievoForm.getValue("datiPrelievoDaArchivioDataPrelievo"): null);
				
				recordToPass.setAttribute("datiPrelievoDaArchivioIdUserResponsabile", datiArchivioPrelievoResponsabilePrelievoForm != null ? datiArchivioPrelievoResponsabilePrelievoForm.getValue("datiPrelievoDaArchivioIdUserResponsabile"): null);
				recordToPass.setAttribute("datiPrelievoDaArchivioUsernameResponsabile", datiArchivioPrelievoResponsabilePrelievoForm != null ? datiArchivioPrelievoResponsabilePrelievoForm.getValue("datiPrelievoDaArchivioUsernameResponsabile"): null);
				recordToPass.setAttribute("datiPrelievoDaArchivioCognomeResponsabile", datiArchivioPrelievoResponsabilePrelievoForm != null ? datiArchivioPrelievoResponsabilePrelievoForm.getValue("datiPrelievoDaArchivioCognomeResponsabile"): null);
				recordToPass.setAttribute("datiPrelievoDaArchivioNomeResponsabile", datiArchivioPrelievoResponsabilePrelievoForm != null ? datiArchivioPrelievoResponsabilePrelievoForm.getValue("datiPrelievoDaArchivioNomeResponsabile"): null);
				
				recordToPass.setAttribute("datiPrelievoDaArchivioNoteRichiedente", datiArchivioPrelievoNoteForm != null ? datiArchivioPrelievoNoteForm.getValue("datiPrelievoDaArchivioNoteRichiedente"): null);
				recordToPass.setAttribute("datiPrelievoDaArchivioNoteArchivio", datiArchivioPrelievoNoteForm != null ? datiArchivioPrelievoNoteForm.getValue("datiPrelievoDaArchivioNoteArchivio"): null);					
				
				recordToPass.setAttribute("motiviPrelievoSuFascArchivio", motiviPrelievoForm != null ? motiviPrelievoForm.getValue("motiviPrelievoSuFascArchivio") : null );
				
				recordToPass.setAttribute("datiPrelievoDaArchivioDataRestituzionePrelievo", dataRestituzionePrelievoForm != null ? dataRestituzionePrelievoForm.getValue("datiPrelievoDaArchivioDataRestituzionePrelievo") : null );
								
				Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
				try{
					GWTRestService<Record, Record> lAurigaOperPrelievoSuFascArchivioDataSource = new GWTRestService<Record, Record>("AurigaOperPrelievoSuFascArchivioDataSource");
					lAurigaOperPrelievoSuFascArchivioDataSource.addData(recordToPass, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Layout.hideWaitPopup();
							if (closeDetailAfterOperation) {
								azioneSuPraticaPregressaPopup.markForDestroy();	
							}
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record record = response.getData()[0];
								manageResultAzionePraticaPregressa(record, closeDetailAfterOperation);
							}
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}
			}
		};
		azioneSuPraticaPregressaPopup.show();
	}
	
	private void manageResultAzionePraticaPregressa(Record object, boolean closeDetailAfterOperation) {
		
		boolean success = object.getAttributeAsBoolean("success");
		String errorMsg = object.getAttribute("errorMessage");
		if (success) {
			eseguitaAzioneSuPrelievo = true;
			Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));
		}else {
			if (errorMsg != null && !"".equalsIgnoreCase(errorMsg)) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			}else {
				Layout.addMessage(new MessageBean("Operazione non riuscita", "", MessageType.ERROR));
			}
		}
		instance.reload(new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				viewMode();
			}
		});
	}
	
	private Record buildRecordPerAzioneSuPratica() {
		Record record = new Record(vm.getValues());
		// La data crea problemi quando viene salvata tramite vm.getValue(), la salvo a mano
		
		if (record.getAttribute("datiPrelievoDaArchivioDataPrelievo") != null && !"".equalsIgnoreCase(record.getAttribute("datiPrelievoDaArchivioDataPrelievo"))) {
			Date dataPrelievo = (Date) (vm.getValue("datiPrelievoDaArchivioDataPrelievo"));
//			DateUtil.setNormalDateDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
			DateUtil.setNormalDateDisplayFormatter(new DateDisplayFormatter() {
				
				@Override
				public String format(Date date) {
					if(date == null) return null;
	                //you'll probably want to create the DateTimeFormat outside this method.
	                //here for illustration purposes        
	                DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd/MM/yyyy");
	                String format = dateFormatter.format(date);
	                return format;
				}
			});
			record.setAttribute("datiPrelievoDaArchivioDataPrelievo", DateUtil.format(dataPrelievo));
		}
		return record;
	}
	
	private boolean isSelectCartaceoReperibileItemToShow() {
		String selectCompetenzaDiUrbanisticaItemValue = datiCompetenzaUrbanisticaForm.getValueAsString("competenzaPratica");
		return !(selectCompetenzaDiUrbanisticaItemValue != null && "no".equalsIgnoreCase(selectCompetenzaDiUrbanisticaItemValue));
	}
	
	private boolean isSelectInArchivioItemToShow(){
		String selectCartaceoReperibileItemValue = datiCompetenzaUrbanisticaForm.getValueAsString("reperibilitaCartaceoPratica");
		return isSelectCartaceoReperibileItemToShow() && !(selectCartaceoReperibileItemValue != null && "no".equalsIgnoreCase(selectCartaceoReperibileItemValue));
	}
	
	private boolean isSelectCartaceoReperibileItemObbligatorio() {
		if (isSelectCartaceoReperibileItemToShow()) {
			String statoScansione = datiPrincipaliForm.getValueAsString("flgDocumentazioneCompleta");
			return !(statoScansione != null && "true".equalsIgnoreCase(statoScansione));
		}
		return false;
	}
	
	private boolean isSelectInArchivioItemObbligatorio(){
		if (isSelectInArchivioItemToShow()) {
			String statoScansione = datiPrincipaliForm.getValueAsString("flgDocumentazioneCompleta");
			return !(statoScansione != null && "true".equalsIgnoreCase(statoScansione));
		}
		return false;
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	protected void setRequiredInItem(FormItem item, String title, boolean required) {
		item.setAttribute("obbligatorio", required);
		if (required) {
			item.setTitle(FrontendUtil.getRequiredFormItemTitle(title));
			item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		} else {
			item.setTitle(title);
			item.setTitleStyle(it.eng.utility.Styles.formTitle);
		}
	}
	
	public class TitleStaticTextItem extends StaticTextItem {

		public TitleStaticTextItem(String title, int width) {
			setTitle(title);
			setColSpan(1);
			setDefaultValue(title + AurigaLayout.getSuffixFormItemTitle());
			setWidth(width);
			setShowTitle(false);
			setAlign(Alignment.RIGHT);
			setTextBoxStyle(it.eng.utility.Styles.formTitle);
			setWrap(false);
		}

		@Override
		public void setCanEdit(Boolean canEdit) {
			setTextBoxStyle(it.eng.utility.Styles.formTitle);
		}
	}
	
	/**
	 * Classe che implementa una sezione di intestazione nella maschera di caricamento pratica pregressa
	 * 
	 */
	public class PraticaPregressaHeaderDetailSection extends HeaderDetailSection {

		public PraticaPregressaHeaderDetailSection(String pTitle, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
			this(pTitle,  null, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}

		public PraticaPregressaHeaderDetailSection(String pTitle, final Integer  pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
			this(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}
		
		public PraticaPregressaHeaderDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final String pBackgroundColor, final DynamicForm... forms) {
			super(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, pBackgroundColor, forms);
		}		
		
		@Override
		public boolean showFirstCanvasWhenEmptyAfterOpen() {
			return true;
		}
	}
	
	/**
	 * Classe che implementa una sezione nella maschera di caricamento pratica pregressa
	 * 
	 */
	public class PraticaPregressaDetailSection extends DetailSection {
		
		public PraticaPregressaDetailSection(String pTitle, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
			this(pTitle, null, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}

		public PraticaPregressaDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
				final DynamicForm... forms) {
			this(pTitle, pHeight, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}

		public PraticaPregressaDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
				final String pBackgroundColor, final DynamicForm... forms) {
			super(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, pBackgroundColor, forms);
		}
		
		@Override
		public boolean showFirstCanvasWhenEmptyAfterOpen() {
			return true;
		}
	}		

}