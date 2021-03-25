package it.eng.auriga.ui.module.layout.client.protocollazione;

import java.util.HashSet;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

/**
 * Maschera di dettaglio di una UD contenente le logiche per la modalità WIZARD (PGWeb)
 * ATTENZIONE: la modalità WIZARD viene attivata solo se il metodo isModalitaWizard() restituisce TRUE
 * 
 * @author Mattia Zanin
 *
 */

public abstract class ProtocollazioneDetailWizard extends ProtocollazioneDetail {

	protected ProtocollazioneDetailWizard instance;

	protected Tab tabDatiPrincipali;
	protected VLayout layoutDatiPrincipali;

	protected Canvas markDetailSectionAssegnazione;
	protected Canvas markDetailSectionCondivisione;
	protected Canvas markDetailSectionClassificazioneFascicolazione;
	protected Canvas markDetailSectionFolderCustom;

	protected DynamicForm canaleDataRicezioneForm;
	protected SelectItem mezzoTrasmissioneItem;
	protected ExtendedTextItem nroRaccomandataItem;
	protected ExtendedDateItem dataRaccomandataItem;
	protected TextItem nroListaRaccomandataItem;
	protected DateTimeItem dataEOraArrivoItem;
	protected SelectItem attoAutProtDifferitaItem;
	protected TextItem motivazioneProtDifferitaItem;
	protected ProtocollazioneDetailSection detailSectionCanaleDataRicezione;

	protected DynamicForm supportoOriginaleForm;
	protected RadioGroupItem supportoOriginaleItem;
	protected ProtocollazioneDetailSection detailSectionSupportoOriginale;
	
	protected DynamicForm documentiCollegatiForm;
	protected DocumentiCollegatiItem documentiCollegatiItem;
	protected ProtocollazioneDetailSection detailSectionDocumentiCollegati;
	
	protected DynamicForm altriRiferimentiForm;
	protected AltriRiferimentiItem altriRiferimentiItem;
	protected ProtocollazioneDetailSection detailSectionAltriRiferimenti;

	protected Timer timerChangedAllegato = null;
	protected boolean flgToRedraw = false;
	
	public ProtocollazioneDetailWizard(final String nomeEntita) {

		super(nomeEntita);

		instance = this;
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
	}
	
	@Override
	public boolean showModelliSelectItem() {
		
		if(isModalitaWizard()) {
			return false;
		}
		return super.showModelliSelectItem();
	}

	@Override
	protected void createTabSet() throws IllegalStateException {

		if(isModalitaWizard()) {
			
			createTabDatiPrincipali();
	
			tabSet = new TabSet();
			tabSet.setTabBarPosition(Side.TOP);
			tabSet.setTabBarAlign(Side.LEFT);
			tabSet.setWidth100();
			tabSet.setBorder("0px");
			tabSet.setCanFocus(false);
			tabSet.setTabIndex(-1);
			tabSet.setPaneMargin(0);
	
			tabSet.addTab(tabDatiPrincipali);
			
		} else {
			super.createTabSet();
		}
	}

	protected void createTabDatiPrincipali() {

		layoutDatiPrincipali = getLayoutDatiPrincipali();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			tabDatiPrincipali = new Tab("<h5><b>Dati principali</b></h5>");
			tabDatiPrincipali.setAttribute("tabID", "HEADER");
			tabDatiPrincipali.setPrompt("Dati principali");
			layoutDatiPrincipali.setTabIndex(-1);
			layoutDatiPrincipali.setCanFocus(false);
			VLayout tabPaneCreated = createTabPane(layoutDatiPrincipali);
			tabPaneCreated.setTabIndex(-1);
			tabPaneCreated.setCanFocus(false);
			tabDatiPrincipali.setPane(tabPaneCreated);		
		} else {
		tabDatiPrincipali = new Tab("<b>Dati principali</b>");
		tabDatiPrincipali.setAttribute("tabID", "HEADER");
		tabDatiPrincipali.setPrompt("Dati principali");
		tabDatiPrincipali.setPane(createTabPane(layoutDatiPrincipali));
		}

	}

	public VLayout getLayoutDatiPrincipali() {

		VLayout layoutDatiPrincipali = new VLayout(5);

		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiPrincipali.addMember(detailSectionRegistrazione);
		
		createDetailSectionNuovaRegistrazione();
		detailSectionNuovaRegistrazione.setVisible(false);
		layoutDatiPrincipali.addMember(detailSectionNuovaRegistrazione);

		if(showDetailSectionNuovaRegistrazioneProtGenerale()) {
			createDetailSectionNuovaRegistrazioneProtGenerale();			
			layoutDatiPrincipali.addMember(detailSectionNuovaRegistrazioneProtGenerale);
		}

		if(showDetailSectionTipoDocumento()) {
			createDetailSectionTipoDocumento();
			detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));
			layoutDatiPrincipali.addMember(detailSectionTipoDocumento);
		}
		
		if(showDetailSectionAltreVie() && showDetailSectionAltreVieAfterHeader()) {
			createDetailSectionAltreVie();
			layoutDatiPrincipali.addMember(detailSectionAltreVie);
		}
		
		// solo in ENTRATA
		if (showDetailSectionCanaleDataRicezione()) {
			createDetailSectionCanaleDataRicezione();
			layoutDatiPrincipali.addMember(detailSectionCanaleDataRicezione);
		}
		
		createDetailSectionSupportoOriginale();
		layoutDatiPrincipali.addMember(detailSectionSupportoOriginale);
		
		// solo in ENTRATA
		if(showDetailSectionEsibenti()) {
			createDetailSectionEsibenti();
			layoutDatiPrincipali.addMember(detailSectionEsibenti);
		}
		
		createDetailSectionMittenti();
		layoutDatiPrincipali.addMember(detailSectionMittenti);

		createDetailSectionInteressati();
		layoutDatiPrincipali.addMember(detailSectionInteressati);

		// solo in ENTRATA
		if (showDetailSectionDatiRicezione()) {
			createDetailSectionDatiRicezione();
			layoutDatiPrincipali.addMember(detailSectionDatiRicezione);
		}
		
		if(showDetailSectionAssegnazioneBeforeDestinatari()) {
			createDetailSectionAssegnazione();
			markDetailSectionAssegnazione = new Canvas("markDetailSectionAssegnazione");
			markDetailSectionAssegnazione.setVisibility(Visibility.HIDDEN);
			layoutDatiPrincipali.addMember(markDetailSectionAssegnazione);
			layoutDatiPrincipali.addMember(detailSectionAssegnazione);
		}
		
		if(showDetailSectionDestinatari() && !showDetailSectionDestinatariAfterAssegnazione()) {
			createDetailSectionDestinatari();
			layoutDatiPrincipali.addMember(detailSectionDestinatari);
		}

		createDetailSectionContenuti();
		layoutDatiPrincipali.addMember(detailSectionContenuti);

		createDetailSectionAllegati();
		layoutDatiPrincipali.addMember(detailSectionAllegati);

		if(!showDetailSectionAssegnazioneBeforeDestinatari()) {
			createDetailSectionAssegnazione();
			markDetailSectionAssegnazione = new Canvas("markDetailSectionAssegnazione");
			markDetailSectionAssegnazione.setVisibility(Visibility.HIDDEN);
			layoutDatiPrincipali.addMember(markDetailSectionAssegnazione);
			layoutDatiPrincipali.addMember(detailSectionAssegnazione);
		}
		
		if(showDetailSectionDestinatari() && showDetailSectionDestinatariAfterAssegnazione()) {
			createDetailSectionDestinatari();
			layoutDatiPrincipali.addMember(detailSectionDestinatari);
		}

		createDetailSectionCondivisione();
		markDetailSectionCondivisione = new Canvas("markDetailSectionCondivisione");
		markDetailSectionCondivisione.setVisibility(Visibility.HIDDEN);
		layoutDatiPrincipali.addMember(markDetailSectionCondivisione);
		layoutDatiPrincipali.addMember(detailSectionCondivisione);

		createDetailSectionClassificazioneFascicolazione();
		markDetailSectionClassificazioneFascicolazione = new Canvas("markDetailSectionClassificazioneFascicolazione");
		markDetailSectionClassificazioneFascicolazione.setVisibility(Visibility.HIDDEN);
		layoutDatiPrincipali.addMember(markDetailSectionClassificazioneFascicolazione);
		layoutDatiPrincipali.addMember(detailSectionClassificazioneFascicolazione);

		if(showDetailSectionFolderCustom()) {
			createDetailSectionFolderCustom();
			markDetailSectionFolderCustom = new Canvas("markDetailSectionFolderCustom");
			markDetailSectionFolderCustom.setVisibility(Visibility.HIDDEN);
			layoutDatiPrincipali.addMember(markDetailSectionFolderCustom);
			layoutDatiPrincipali.addMember(detailSectionFolderCustom);
		}

		if(showDetailSectionAltreVie() && !showDetailSectionAltreVieAfterHeader()) {
			createDetailSectionAltreVie();
			layoutDatiPrincipali.addMember(detailSectionAltreVie);
		}

		// solo in ENTRATA e USCITA		
		if (showDetailSectionRegEmergenza()) {
			createDetailSectionRegEmergenza();
			layoutDatiPrincipali.addMember(detailSectionRegEmergenza);
		}

		createDetailSectionCollocazioneFisica();
		layoutDatiPrincipali.addMember(detailSectionCollocazioneFisica);

		if(showDetailSectionDocCollegato()) {
			createDetailSectionDocCollegato();
			layoutDatiPrincipali.addMember(detailSectionDocCollegato);
		}
		
		createDetailSectionDocumentiCollegati();
		layoutDatiPrincipali.addMember(detailSectionDocumentiCollegati);
		
		createDetailSectionAltriRiferimenti();
		layoutDatiPrincipali.addMember(detailSectionAltriRiferimenti);

		createDetailSectionAltriDati();
		layoutDatiPrincipali.addMember(detailSectionAltriDati);

		if(showDetailSectionPubblicazione()) {
			createDetailSectionPubblicazione();
			layoutDatiPrincipali.addMember(detailSectionPubblicazione);		
		}
		
		if(showDetailSectionRipubblicazione()) {
			createDetailSectionRipubblicazione();
			layoutDatiPrincipali.addMember(detailSectionRipubblicazione);		
		}
		
		return layoutDatiPrincipali;
	}

	// Visualizza la sezione degli assegnatari prima della sezione dei destinatari sopra i contenuti
	public boolean showDetailSectionAssegnazioneBeforeDestinatari() {
		return false;
	}
	
	// Visualizza la sezione dei destinatari dopo la sezione degli assegnatari sotto i contenuti
	public boolean showDetailSectionDestinatariAfterAssegnazione() {
		return false;
	}
	
	@Override
	public boolean showDocumentiCollegatiButton() {
		return !isModalitaWizard();
	}
	
	@Override
	public boolean showAltriRiferimentiButton() {
		return !isModalitaWizard();
	}
	
	@Override
	protected void afterCaricaAttributiDinamiciDoc() {

		super.afterCaricaAttributiDinamiciDoc();
		redrawSections(null);
	}
	
	@Override
	public void manageOnChangedRequiredAttrDinamicoItemInHeaderSection(FormItem item) {
		DetailSection detailSection = item != null && item.getForm() != null ? item.getForm().getDetailSection() : null;
		redrawSections(detailSection);
	}
	
	/**
	 * CANALE E DATA RICEZIONE
	 * 
	 */
	
	public boolean showDetailSectionCanaleDataRicezione() {
		return getFlgTipoProv() != null && getFlgTipoProv().equalsIgnoreCase("E");
	}

	protected void createDetailSectionCanaleDataRicezione() {

		canaleDataRicezioneForm = new DynamicForm();
		canaleDataRicezioneForm.setValuesManager(vm);
		canaleDataRicezioneForm.setWidth("100%");
		canaleDataRicezioneForm.setHeight("5");
		canaleDataRicezioneForm.setPadding(5);
		canaleDataRicezioneForm.setNumCols(14);
		canaleDataRicezioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		canaleDataRicezioneForm.setWrapItemTitles(false);
		canaleDataRicezioneForm.setTabSet(tabSet);
		canaleDataRicezioneForm.setTabID("HEADER");
		
		mezzoTrasmissioneItem = new SelectItem("mezzoTrasmissione", "Canale");
		GWTRestDataSource mezziTrasmissioneDS = new GWTRestDataSource("LoadComboCanaleRicezioneDataSource", "key", FieldType.TEXT);
		mezzoTrasmissioneItem.setOptionDataSource(mezziTrasmissioneDS);  
		mezzoTrasmissioneItem.setDisplayField("value");
		mezzoTrasmissioneItem.setValueField("key");		
//		LinkedHashMap<String, String> mezzoTrasmissioneValueMap = new LinkedHashMap<String, String>();
//		mezzoTrasmissioneValueMap.put("CM", "sportello (consegna a mano)");
//		mezzoTrasmissioneValueMap.put("R", "raccomandata");
//		mezzoTrasmissioneValueMap.put("L", "lettera");
//		mezzoTrasmissioneValueMap.put("PEC", "PEC (Posta Elettronica Certificata)");
//		mezzoTrasmissioneValueMap.put("PEO", "e-mail ordinaria");
//		mezzoTrasmissioneValueMap.put("A", "altro");
//		mezzoTrasmissioneItem.setValueMap(mezzoTrasmissioneValueMap);
		mezzoTrasmissioneItem.setRequired(true);
		mezzoTrasmissioneItem.setWidth(200);
		mezzoTrasmissioneItem.setAutoFetchData(true);
		mezzoTrasmissioneItem.setAlwaysFetchMissingValues(true);
		mezzoTrasmissioneItem.setFetchMissingValues(true);
		mezzoTrasmissioneItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", false);	
		mezzoTrasmissioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return true;
			}
		});	
		mezzoTrasmissioneItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				mezzoTrasmissioneItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", event.getOldValue() == null || "".equals(event.getOldValue()));
			}
		});
		mezzoTrasmissioneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				boolean skipResetDefaultValueFlgAssegnaAlMittDest = mezzoTrasmissioneItem.getAttributeAsBoolean("skipResetDefaultValueFlgAssegnaAlMittDest") != null && mezzoTrasmissioneItem.getAttributeAsBoolean("skipResetDefaultValueFlgAssegnaAlMittDest");
				manageChangedMezzoTrasmissione(skipResetDefaultValueFlgAssegnaAlMittDest);
				mezzoTrasmissioneItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", false);	
			}
		});

		SpacerItem spacerRaccomandataItem = new SpacerItem();
		spacerRaccomandataItem.setWidth(100);
		spacerRaccomandataItem.setColSpan(1);
		spacerRaccomandataItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return mezzoTrasmissioneItem.getValue() != null && ("R".equals(mezzoTrasmissioneItem.getValueAsString()) || "PREGR".equals(mezzoTrasmissioneItem.getValueAsString()));
			}
		});

		nroRaccomandataItem = new ExtendedTextItem("nroRaccomandata", I18NUtil.getMessages().protocollazione_detail_nroRaccomandataCanaleItem_title());
		nroRaccomandataItem.setWidth(100);
		nroRaccomandataItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				if(mezzoTrasmissioneItem.getValue() != null) {
					if("R".equals(mezzoTrasmissioneItem.getValueAsString())) {
						nroRaccomandataItem.setAttribute("obbligatorio", true);
						nroRaccomandataItem.setTitle(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().protocollazione_detail_nroRaccomandataCanaleItem_title()));
						nroRaccomandataItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
						return true;
					} else if("PREGR".equals(mezzoTrasmissioneItem.getValueAsString())) {
						nroRaccomandataItem.setAttribute("obbligatorio", false);
						nroRaccomandataItem.setTitle(I18NUtil.getMessages().protocollazione_detail_nroRaccomandataCanaleItem_title());
						nroRaccomandataItem.setTitleStyle(it.eng.utility.Styles.formTitle);
						return true;
					}					
				}
				return false;
			}
		});
		nroRaccomandataItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {

				return mezzoTrasmissioneItem.getValue() != null && "R".equals(mezzoTrasmissioneItem.getValueAsString());
			}
		}));
		nroRaccomandataItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				redrawSections(detailSectionCanaleDataRicezione);
			}
		});

		dataRaccomandataItem = new ExtendedDateItem("dataRaccomandata", I18NUtil.getMessages().protocollazione_detail_dataRaccomandataCanaleItem_title());
		dataRaccomandataItem.setWrapTitle(false);
		dataRaccomandataItem.setColSpan(1);
		dataRaccomandataItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				if(mezzoTrasmissioneItem.getValue() != null) {
					if("R".equals(mezzoTrasmissioneItem.getValueAsString())) {
						dataRaccomandataItem.setAttribute("obbligatorio", true);
						dataRaccomandataItem.setTitle(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().protocollazione_detail_dataRaccomandataCanaleItem_title()));
						dataRaccomandataItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
						return true;
					} else if("PREGR".equals(mezzoTrasmissioneItem.getValueAsString())) {
						dataRaccomandataItem.setAttribute("obbligatorio", false);
						dataRaccomandataItem.setTitle(I18NUtil.getMessages().protocollazione_detail_dataRaccomandataCanaleItem_title());
						dataRaccomandataItem.setTitleStyle(it.eng.utility.Styles.formTitle);
						return true;
					}					
				}
				return false;
			}
		});
		dataRaccomandataItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {

				return mezzoTrasmissioneItem.getValue() != null && "R".equals(mezzoTrasmissioneItem.getValueAsString());
			}
		}));
		dataRaccomandataItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				redrawSections(detailSectionCanaleDataRicezione);
			}
		});

		nroListaRaccomandataItem = new TextItem("nroListaRaccomandata", "Lista N°");
		nroListaRaccomandataItem.setWidth(100);
		nroListaRaccomandataItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return mezzoTrasmissioneItem.getValue() != null && ("R".equals(mezzoTrasmissioneItem.getValueAsString()) || "PREGR".equals(mezzoTrasmissioneItem.getValueAsString()));
			}
		});

		dataEOraArrivoItem = new DateTimeItem("dataEOraArrivo", "Data e ora di ricezione");
		dataEOraArrivoItem.setWrapTitle(false);
		dataEOraArrivoItem.setColSpan(1);
		dataEOraArrivoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				
				canaleDataRicezioneForm.markForRedraw();
			}
		});
		dataEOraArrivoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return mezzoTrasmissioneItem.getValue() == null || (!"R".equals(mezzoTrasmissioneItem.getValueAsString()) && !"PREGR".equals(mezzoTrasmissioneItem.getValueAsString()));
			}
		});
		
		attoAutProtDifferitaItem = new SelectItem("attoAutProtDifferita", "Atto autorizzazione prot. differita") {
			
			@Override
			public void onOptionClick(Record record) {
				
				motivazioneProtDifferitaItem.setValue(record.getAttribute("motivazione"));
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					motivazioneProtDifferitaItem.setValue("");
				}
				markForRedraw();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				motivazioneProtDifferitaItem.setValue("");
				markForRedraw();
			};			
		};
		attoAutProtDifferitaItem.setWidth(150);
//		attoAutProtDifferitaItem.setValueField("idUd");
//		attoAutProtDifferitaItem.setDisplayField("estremi");
//		attoAutProtDifferitaItem.setOptionDataSource(attoAutProtDifferitaDS);
		attoAutProtDifferitaItem.setAllowEmptyValue(true);
		attoAutProtDifferitaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

//				if(mezzoTrasmissioneItem.getValue() == null || !"R".equals(mezzoTrasmissioneItem.getValueAsString())) {
//					return dataEOraArrivoItem.getValue() != null;
//				}
				return false;
			}
		});
		
		motivazioneProtDifferitaItem = new TextItem("motivazioneProtDifferita", "Motivazione prot. differita");
		motivazioneProtDifferitaItem.setWidth(300);
		motivazioneProtDifferitaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

//				if(mezzoTrasmissioneItem.getValue() == null || !"R".equals(mezzoTrasmissioneItem.getValueAsString())) {
//					return dataEOraArrivoItem.getValue() != null;
//				}
				return false;
			}
		});
		
		canaleDataRicezioneForm.setFields(mezzoTrasmissioneItem, nroRaccomandataItem, dataRaccomandataItem, nroListaRaccomandataItem, dataEOraArrivoItem, attoAutProtDifferitaItem, motivazioneProtDifferitaItem);

		detailSectionCanaleDataRicezione = new ProtocollazioneDetailSection("Canale e data ricezione", true, detailSectionCanaleDataRicezioneToShowOpen(), true, canaleDataRicezioneForm); /*{
			
			@Override
			public boolean validate() {
				return canaleDataRicezioneForm.validate();
			}
			
			@Override
			public boolean isOpenable() {
				return true;
			}
			
			@Override
			public String getOpenErrorMessage() {
				return null;
			}
		};*/
	}	
	
	public boolean detailSectionCanaleDataRicezioneToShowOpen() {
		return true;
	}
	
	public void manageChangedMezzoTrasmissione(boolean skipResetDefaultValueFlgAssegnaAlMittDest) {
		
		canaleDataRicezioneForm.clearErrors(true);
		canaleDataRicezioneForm.redraw();

		supportoOriginaleForm.clearErrors(true);
		supportoOriginaleForm.redraw();
	
		if (mezzoTrasmissioneItem.getValue() == null || "CM".equals(mezzoTrasmissioneItem.getValueAsString())
				|| "A".equals(mezzoTrasmissioneItem.getValueAsString()) || "PREGR".equals(mezzoTrasmissioneItem.getValueAsString())) {
			detailSectionSupportoOriginale.show();
			if (mezzoTrasmissioneItem.getValue() != null && "CM".equals(mezzoTrasmissioneItem.getValueAsString())) {
				supportoOriginaleForm.setValue("supportoOriginale", "cartaceo");
			} else {
				supportoOriginaleForm.setValue("supportoOriginale", (String) null);
			}
			supportoOriginaleItem.setCanEdit(editing);			
		} else if ("R".equals(mezzoTrasmissioneItem.getValueAsString()) || "L".equals(mezzoTrasmissioneItem.getValueAsString())) {
			detailSectionSupportoOriginale.hide();
			supportoOriginaleForm.setValue("supportoOriginale", "cartaceo");
			supportoOriginaleItem.setCanEdit(false);			
		} else if ("PEC".equals(mezzoTrasmissioneItem.getValueAsString()) || "PEO".equals(mezzoTrasmissioneItem.getValueAsString())) {
			detailSectionSupportoOriginale.hide();
			supportoOriginaleForm.setValue("supportoOriginale", "digitale");
			supportoOriginaleItem.setCanEdit(false);			
		}
		
		// Resetta il valore di default nel check di "effettua assegnazione" dei mittenti e destinatari
		if(!skipResetDefaultValueFlgAssegnaAlMittDest) {
			if(mittentiItem != null) {
				mittentiItem.resetDefaultValueFlgAssegnaAlMittente();
			}
			if(destinatariItem != null) {
				destinatariItem.resetDefaultValueFlgAssegnaAlDestinatario();
			}
		}
				
		manageChangedPrimario();
		
		redrawSections(detailSectionCanaleDataRicezione);
	}
	
	/**
	 * SUPPORTO ORIGINALE
	 * 
	 */

	protected void createDetailSectionSupportoOriginale() {

		supportoOriginaleForm = new DynamicForm();
		supportoOriginaleForm.setValuesManager(vm);
		supportoOriginaleForm.setWidth("100%");
		supportoOriginaleForm.setHeight("5");
		supportoOriginaleForm.setPadding(5);
		supportoOriginaleForm.setTabSet(tabSet);
		supportoOriginaleForm.setTabID("HEADER");

		supportoOriginaleItem = new RadioGroupItem("supportoOriginale");
		supportoOriginaleItem.setShowTitle(false);
		supportoOriginaleItem.setVertical(false);
		supportoOriginaleItem.setValueMap("cartaceo", "digitale", "misto");
		supportoOriginaleItem.setRequired(true);
		supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", false);	
		supportoOriginaleItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", event.getOldValue() == null || "".equals(event.getOldValue()));
			}
		});
		supportoOriginaleItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				boolean skipResetDefaultValueFlgAssegnaAlMittDest = supportoOriginaleItem.getAttributeAsBoolean("skipResetDefaultValueFlgAssegnaAlMittDest") != null && supportoOriginaleItem.getAttributeAsBoolean("skipResetDefaultValueFlgAssegnaAlMittDest");				
				manageChangedSupportoOriginale(skipResetDefaultValueFlgAssegnaAlMittDest);
				supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", false);				
			}
		});

		supportoOriginaleForm.setFields(supportoOriginaleItem);

		detailSectionSupportoOriginale = new ProtocollazioneDetailSection("Supporto originale", true, detailSectionSupportoOriginaleToShowOpen(), true, supportoOriginaleForm); /*{
			
			@Override
			public boolean validate() {
				return supportoOriginaleForm.validate();
			}
			
			@Override
			public boolean isOpenable() {
				return !showDetailSectionCanaleDataRicezione() || super.isOpenable();
			}
			
			@Override
			public String getOpenErrorMessage() {
				if(!showDetailSectionCanaleDataRicezione()) {
					return null;
				} 
				return super.getOpenErrorMessage();
			}
		};*/
	}
	
	public boolean detailSectionSupportoOriginaleToShowOpen() {
		return true;
	}
	
	public void manageChangedSupportoOriginale(boolean skipResetDefaultValueFlgAssegnaAlMittDest) {
		
		// Resetta il valore di default nel check di "effettua assegnazione" dei mittenti e destinatari
		if(!skipResetDefaultValueFlgAssegnaAlMittDest) {
			if(mittentiItem != null) {
				mittentiItem.resetDefaultValueFlgAssegnaAlMittente();
			}
			if(destinatariItem != null) {
				destinatariItem.resetDefaultValueFlgAssegnaAlDestinatario();			
			}
		}

		manageChangedPrimario();		
		
		redrawSections(detailSectionSupportoOriginale);
	}
	
	/**
	 * MITTENTE
	 * 
	 */
	
	@Override
	public String getTitleDetailSectionMittenti() { 
		
		if(isModalitaWizard()) {
			return "Mittente" ;
		}
		return super.getTitleDetailSectionMittenti();
	}
	
	/**
	 * ESIBENTE E INTERESSATI
	 * 
	 */
	
	@Override
	public boolean showTabEsibentiEInteressati() {
		if(isModalitaWizard()) {
			return false;
		}
		return super.showTabEsibentiEInteressati();
	}
	
	@Override
	public boolean showDetailSectionEsibenti() {
		return isProtocollazioneDetailEntrata() && (isModalitaWizard() || ProtocollazioneUtil.isAttivoEsibenteSenzaWizard());
	}
	
	@Override
	public boolean showDetailSectionInteressati() {
		return isModalitaWizard() || ProtocollazioneUtil.isAttivoInteressatiSenzaWizard();
	}
	
	@Override
	protected void createInteressatiForm() {

		super.createInteressatiForm();
		
		if(isModalitaWizard()) {			
			interessatiForm.setTabID("HEADER");
		}
	}
	
	/**
	 * DATI RICEZIONE
	 * 
	 */
	
	@Override
	public String getTitleDetailSectionDatiRicezione() {
		
		if(isModalitaWizard()) {
			return "Protocollo ricevuto";
		}
		return super.getTitleDetailSectionDatiRicezione();
	}
	
	@Override
	public boolean showOpenDetailSectionDatiRicezione() {
		
		if(isModalitaWizard()) {
			return true;
		}
		return super.showOpenDetailSectionDatiRicezione();
	}
	
	@Override
	protected void createDatiRicezioneForm() {

		super.createDatiRicezioneForm();
		
		if(isModalitaWizard()) {		
			datiRicezioneForm.setFields(rifOrigineProtRicevutoItem, nroProtRicevutoItem, annoProtRicevutoItem, dataProtRicevutoItem);
		}
	}
	
	/**
	 * CONTENUTI
	 * 
	 */
	
	@Override
	public boolean isRequiredDetailSectionContenuti() {
		
		if(isModalitaWizard()) {		
			return true;
		}
		return super.isRequiredDetailSectionContenuti();
	}

	@Override
	public boolean isFormatoAmmessoFilePrimario(InfoFileRecord info) {		
		
		if(isModalitaWizard()) {		
			return !isCartaceo() || (info != null && info.getMimetype() != null && (info.getMimetype().equals("application/pdf") || info.getMimetype().startsWith("image")));
		}
		return super.isFormatoAmmessoFilePrimario(info);
	}
	
	@Override
	public String getFormatoNonAmmessoFilePrimarioWarning() {
		
		if(isModalitaWizard()) {		
			return "Il file non è un'immagine come atteso: poiché il canale/supporto originale specificato indica che il documento è cartaceo puoi allegare solo la/le immagini - scansioni o foto - che ne rappresentano la copia digitale";
		}
		return super.getFormatoNonAmmessoFilePrimarioWarning();
	}
	
	@Override
	public boolean showFlgOriginaleCartaceoECopiaSostitutivaPrimario() {
		return isModalitaWizard();
	}
	
	@Override
	protected void createFilePrimarioForm() {

		super.createFilePrimarioForm();
	
		if(isModalitaWizard()) {		
				
			flgOriginaleCartaceoItem.setShowIfCondition(new FormItemIfFunction() {
	
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
	
					flgOriginaleCartaceoItem.setAttribute("defaultValue", false);
					if (isDigitale()) {
						filePrimarioForm.setValue("flgOriginaleCartaceo", false);					
					}
					if (isCartaceo()) {
						filePrimarioForm.setValue("flgOriginaleCartaceo", true);
						flgOriginaleCartaceoItem.setAttribute("defaultValue", true);
					}				
					return !isDigitale() && !isCartaceo();
				}
			});
			
			flgCopiaSostitutivaItem.setShowIfCondition(new FormItemIfFunction() {
	
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
	
					return !isDigitale()
							&& (filePrimarioForm.getValue("flgOriginaleCartaceo") != null && (Boolean) filePrimarioForm.getValue("flgOriginaleCartaceo"));
				}
			});
	
			nomeFilePrimarioItem.setShowIfCondition(new FormItemIfFunction() {
	
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
	
					if (isCartaceo()) {
						nomeFilePrimarioItem.setTitle("Immagine doc. principale");
					} else {
						nomeFilePrimarioItem.setTitle("File doc. principale");
					}			
					return true;
				}
			});
					
			CustomValidator lFilePrimarioCartaceoValidator = new CustomValidator() {
	
				@Override
				protected boolean condition(Object value) {
	
					if (isCartaceo()) {
						if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
							InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
							if (lInfoFileRecord == null || lInfoFileRecord.getMimetype() == null 
									|| (!lInfoFileRecord.getMimetype().equals("application/pdf") && !lInfoFileRecord.getMimetype().startsWith("image"))) {
								if (isOpenableDetailSection(detailSectionContenuti)) {
									detailSectionContenuti.open();
								}
								return false;
							}
						}
					}
					return true;
				}
			};
			lFilePrimarioCartaceoValidator.setErrorMessage("Il file non è un'immagine come atteso: poiché il canale/supporto originale specificato indica che il documento è cartaceo puoi allegare solo la/le immagini - scansioni o foto - che ne rappresentano la copia digitale");
			
			nomeFilePrimarioItem.setValidators(lFilePrimarioCartaceoValidator/*, lFilePrimarioDigitaleValidator*/);
			
		}
	}

	/**
	 * ALLEGATI
	 * 
	 */
	
	@Override
	protected void createAllegatiItem() {

		if(isModalitaWizard()) {
			
			if(isModalitaAllegatiGrid()) {
				/* NUOVA GESTIONE ALLEGATI CON GRIDITEM */
				fileAllegatiItem = new AllegatiGridItem("listaAllegati", "listaAllegatiProt") {
					
					@Override
					public String getFlgTipoProvProtocollo() {
						return getFlgTipoProv();
					}
					
					@Override
					public Record getDetailRecord() {
						return getRecordToSave(null);		
					}
		
					@Override
					public Boolean validate() {
		
						boolean valid = super.validate();
						if (!isCartaceo()) {
							boolean hasFile = hasFile();
							if (!detailSectionContenuti.getDisabled() && !hasFile) {
								if (isOpenableDetailSection(detailSectionContenuti)) {
									detailSectionContenuti.open();
								}
								filePrimarioForm.setFieldErrors("nomeFilePrimario", "Obbligatorio associare almeno un file (primario o allegato)");
								valid = false;
							}
							if (!detailSectionAllegati.getDisabled() && !hasFile) {
								if (isOpenableDetailSection(detailSectionAllegati)) {
									detailSectionAllegati.open();
								}
								fileAllegatiForm.setFieldErrors("listaAllegati", "Obbligatorio associare almeno un file (primario o allegato)");
								valid = false;
							}
						}
						return valid;
					}
					
					@Override
					public Boolean valuesAreValid() {
						
						boolean valid = super.valuesAreValid();
						if (!isCartaceo()) {
							boolean hasFile = hasFile();
							if (!detailSectionContenuti.getDisabled() && !hasFile) {
								valid = false;
							}
							if (!detailSectionAllegati.getDisabled() && !hasFile) {
								valid = false;
							}
						}
						return valid;
					}
		
					@Override
					public boolean validateFormatoFileAllegato(InfoFileRecord lInfoFileRecord) {
											
						if (isCartaceo()) {
							if (lInfoFileRecord == null || lInfoFileRecord.getMimetype() == null 
									|| (!lInfoFileRecord.getMimetype().equals("application/pdf") && !lInfoFileRecord.getMimetype().startsWith("image"))) {
								return false;
							}
						}
						return true;	
					}
		
					@Override
					public String getFormatoFileNonValidoErrorMessage() {
						return "Il file non è un'immagine come atteso: poiché il canale/supporto originale specificato indica che il documento è cartaceo puoi allegare solo la/le immagini - scansioni o foto - che ne rappresentano la copia digitale";
					}
					
					@Override
					public String getFormatoFileOmissisNonValidoErrorMessage() {
						return "Il file omissis non è un'immagine come atteso: poiché il canale/supporto originale specificato indica che il documento è cartaceo puoi allegare solo la/le immagini - scansioni o foto - che ne rappresentano la copia digitale";			
					}
		
					@Override
					public String getTitleNomeFileAllegato() {
						if (isCartaceo()) {
							return "Immagine";
						} else {
							return "File";
						}
					}
					
					@Override
					public boolean isProtInModalitaWizard() {
						return true;
					}
		
					@Override
					public boolean isCanaleSupportoDigitale() {
						return isDigitale();
					}
		
					@Override
					public boolean isCanaleSupportoCartaceo() {
						return isCartaceo();
					}
		
//					@Override
//					public boolean showUpload() {
//						// return !isFromEmail();
//						return true;
//					};
		
					@Override
					public boolean isHideAcquisisciDaScannerInAltreOperazioniButton() {
						return isFromEmail() || isDigitale();
					};
		
					@Override
					public boolean sonoInMail() {
						return isFromEmail();
					};
		
					@Override
					public void clickTrasformaInPrimario(int index) {
						clickTrasfInPrimario(index);
					};
		
					@Override
					public boolean sonoModificaVisualizza() {
						return mode != null && !mode.equals("new");
					}
		
					@Override
					public boolean canBeEditedByApplet() {
						return canEditByApplet();
					}
					
					@Override
					public boolean isAttivaTimbraturaFilePostRegAllegato() {
						return isAttivaTimbraturaFilePostReg();
					}
		
					@Override
					public void manageOnChanged() {
						if(timerChangedAllegato == null) {
							timerChangedAllegato = new Timer() {
								
								public void run() {
									manageChangedPrimario();
								}
							};	
						}
						if(timerChangedAllegato.isRunning()) {
							timerChangedAllegato.cancel();							
						} 
						String delay = AurigaLayout.getParametroDB("DELAY_VALIDATE_AFTER_CHANGED_ALLEGATI_IN_PROT_WIZARD");
						timerChangedAllegato.schedule(delay != null && !"".equals(delay) ? Integer.parseInt(delay) : 1000);						
					}
					
					@Override
					public boolean isAttivaTimbroTipologia() {
						return isAttivaTimbroTipologiaProtocollazione();
					}
					
					@Override
					public boolean isAttivaVociBarcode() {		
						String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
						return idUd != null && !"".equals(idUd);
					}
					
					@Override
					public String getIdDocFilePrimario() {	
						String idDocPrimarioHidden = (idDocPrimarioHiddenItem.getValue() != null) ? String.valueOf(idDocPrimarioHiddenItem.getValue()) : null;
						return idDocPrimarioHidden;
					}	
					
					@Override
					public String getFinalitaImportaAllegatiMultiLookupArchivio() {
						return  "IMPORTA_UD";
					}
					
					@Override
					public String getImportaFileDocumentiButtonTitle() {
						return  I18NUtil.getMessages().protocollazione_detail_importaDocumentiDaAltriDocumenti_title();
					}
					
					@Override
					public boolean getShowTimbraBarcodeMenuOmissis() {
						return true;
					}
					
					@Override
					public String getIdUd() {
						return idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
					}
					
					@Override
					public boolean getShowFlgParere() {
						return showFlgParereInAllegatiItem();
					}
					
					@Override
					public boolean getShowFlgParteDispositivo() {
						return showFlgParteDispositivoInAllegatiItem();
					}
					
					@Override
					public boolean getShowFlgNoPubblAllegato() {
						return showFlgNoPubblInAllegatiItem();
					}
					
					@Override
					public boolean getShowFlgSostituisciVerPrec() {
						return showFlgSostituisciVerPrecItem();
					}
					
					@Override
					public boolean getShowImportaFileDaDocumenti() {
						return showImportaFileDaDocumentiInAllegatiItem();
					}
					
					@Override
					public boolean isGrigliaEditabile() {
						return true;
					}			
				};
				fileAllegatiItem.setShowTitle(false);
				fileAllegatiItem.setHeight(200);
			} else {
		
				fileAllegatiItem = new AllegatiItem() {
					
					@Override
					public String getFlgTipoProvProtocollo() {
						return getFlgTipoProv();
					}
		
					@Override
					public Boolean validate() {
		
						boolean valid = super.validate();
						if (!isCartaceo()) {
							boolean hasFile = hasFile();
							if (!detailSectionContenuti.getDisabled() && !hasFile) {
								if (isOpenableDetailSection(detailSectionContenuti)) {
									detailSectionContenuti.open();
								}
								filePrimarioForm.setFieldErrors("nomeFilePrimario", "Obbligatorio associare almeno un file (primario o allegato)");
								valid = false;
							}
							if (!detailSectionAllegati.getDisabled() && !hasFile) {
								if (isOpenableDetailSection(detailSectionAllegati)) {
									detailSectionAllegati.open();
								}
								fileAllegatiForm.setFieldErrors("listaAllegati", "Obbligatorio associare almeno un file (primario o allegato)");
								valid = false;
							}
						}
						return valid;
					}
					
					@Override
					public Boolean valuesAreValid() {
						
						boolean valid = super.valuesAreValid();
						if (!isCartaceo()) {
							boolean hasFile = hasFile();
							if (!detailSectionContenuti.getDisabled() && !hasFile) {
								valid = false;
							}
							if (!detailSectionAllegati.getDisabled() && !hasFile) {
								valid = false;
							}
						}
						return valid;
					}
		
					@Override
					public boolean validateFormatoFileAllegato(InfoFileRecord lInfoFileRecord) {
											
						if (isCartaceo()) {
							if (lInfoFileRecord == null || lInfoFileRecord.getMimetype() == null 
									|| (!lInfoFileRecord.getMimetype().equals("application/pdf") && !lInfoFileRecord.getMimetype().startsWith("image"))) {
								return false;
							}
						}
						return true;	
					}
		
					@Override
					public String getFormatoFileNonValidoErrorMessage() {
						return "Il file non è un'immagine come atteso: poiché il canale/supporto originale specificato indica che il documento è cartaceo puoi allegare solo la/le immagini - scansioni o foto - che ne rappresentano la copia digitale";
					}
		
					@Override
					public String getTitleNomeFileAllegato() {
						if (isCartaceo()) {
							return "Immagine";
						} else {
							return "File";
						}
					}
					
					@Override
					public boolean isProtInModalitaWizard() {
						return true;
					}
		
					@Override
					public boolean isCanaleSupportoDigitale() {
						return isDigitale();
					}
		
					@Override
					public boolean isCanaleSupportoCartaceo() {
						return isCartaceo();
					}
		
					@Override
					public boolean showUpload() {
						// return !isFromEmail();
						return true;
					};
		
					@Override
					public boolean showAcquisisciDaScanner() {
						return !isFromEmail() && !isDigitale();
					};
		
					@Override
					public boolean sonoInMail() {
						return isFromEmail();
					};
		
					@Override
					public void clickTrasformaInPrimario(int index) {
						clickTrasfInPrimario(index);
					};
		
					@Override
					public boolean sonoModificaVisualizza() {
						return mode != null && !mode.equals("new");
					}
		
					@Override
					public boolean canBeEditedByApplet() {
						return canEditByApplet();
					}
					
					@Override
					public boolean isAttivaTimbraturaFilePostRegAllegato() {
						return isAttivaTimbraturaFilePostReg();
					}
		
					@Override
					public void manageOnChanged() {
						if(timerChangedAllegato == null) {
							timerChangedAllegato = new Timer() {
								
								public void run() {
									manageChangedPrimario();
								}
							};	
						}
						if(timerChangedAllegato.isRunning()) {
							timerChangedAllegato.cancel();							
						} 
						String delay = AurigaLayout.getParametroDB("DELAY_VALIDATE_AFTER_CHANGED_ALLEGATI_IN_PROT_WIZARD");
						timerChangedAllegato.schedule(delay != null && !"".equals(delay) ? Integer.parseInt(delay) : 1000);											
					}
					
					@Override
					public Record getCanvasDefaultRecord() {		
						return getCanvasDefaultRecordAllegati();
					}
					
					@Override
					public boolean isAttivaTimbroTipologia() {
						return isAttivaTimbroTipologiaProtocollazione();
					}
					
					@Override
					public boolean isAttivaVociBarcode() {		
						String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
						return idUd != null && !"".equals(idUd);
					}
					
					@Override
					public String getIdDocFilePrimario() {	
						String idDocPrimarioHidden = (idDocPrimarioHiddenItem.getValue() != null) ? String.valueOf(idDocPrimarioHiddenItem.getValue()) : null;
						return idDocPrimarioHidden;
					}
					
					@Override
					public String getFinalitaImportaAllegatiMultiLookupArchivio() {
						return  "IMPORTA_UD";
					}
					
					@Override
					public String getImportaFileDocumentiBtnTitle() {
						return  I18NUtil.getMessages().protocollazione_detail_importaDocumentiDaAltriDocumenti_title();
					}
					
					@Override
					public boolean showTimbraBarcodeMenuOmissis() {
						return true;
					}
					
					@Override
					public String getIdUd() {
						return idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
					}
				};
				// Aggiungo solo gli attributi che devo visulizzare nella protocollazione, ma non metto le cose che sono solamente per gli atti 
				// poichè la maschera degli atti non estende il wizard
				fileAllegatiItem.setName("listaAllegati");
				fileAllegatiItem.setShowTitle(false);
				((AllegatiItem)fileAllegatiItem).setShowFlgParere(showFlgParereInAllegatiItem());
				((AllegatiItem)fileAllegatiItem).setShowFlgParteDispositivo(showFlgParteDispositivoInAllegatiItem());
				((AllegatiItem)fileAllegatiItem).setShowFlgNoPubblAllegato(showFlgNoPubblInAllegatiItem());
				((AllegatiItem)fileAllegatiItem).setShowFlgSostituisciVerPrec(showFlgSostituisciVerPrecItem());
				((AllegatiItem)fileAllegatiItem).setShowImportaFileDaDocumenti(showImportaFileDaDocumentiInAllegatiItem());
			}
		} else {
			super.createAllegatiItem();
		}
	}
	
	public Record getCanvasDefaultRecordAllegati() {		
		return null;
	}
	
	/**
	 * ASSEGNAZIONE
	 * 
	 */
	
	@Override
	protected void createAssegnazioneForm() {

		super.createAssegnazioneForm();
		
		if(isModalitaWizard()) {
			assegnazioneForm.setTabID("HEADER");
		}
	}

	@Override
	protected void createAssegnazioneItem() {
		
		if(isModalitaWizard()) {
			
			assegnazioneSalvataItem = new AssegnazioneItem() {
			
				@Override
				public boolean isProtInModalitaWizard() {
					return true;
				}
				
				@Override
				public String getSupportoOriginaleProt() {
					return isModalitaWizard() ? getSupportoOriginale() : null;
				}
				
				@Override
				public boolean isAttivoAssegnatarioUnicoCartaceoProt() {
					return isModalitaWizard() && isAttivoAssegnatarioUnicoCartaceo();
				}	
				
				@Override
				public boolean isAttivaRestrAssCartaceoProt() {
					return isModalitaWizard() && isAttivaRestrizioneAssegnazioneCartaceo();
				}
	
				@Override
				public Boolean getShowRemoveButton() {			
					return true;
				}
				
				@Override
				public void setCanEdit(Boolean canEdit) {
					super.setCanEdit(false);
					if (getCanvas() != null) {
						final VLayout lVLayout = (VLayout) getCanvas();
						for (int i = 0; i < lVLayout.getMembers().length; i++) {
							Canvas lVLayoutMember = lVLayout.getMember(i);
							if (lVLayoutMember instanceof HLayout) {
								for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
									if (lHLayoutMember instanceof ReplicableCanvas) {
										ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
										lReplicableCanvas.setCanEdit(false);
									} else if (lHLayoutMember instanceof RemoveButton) {
										// se è un bottone di remove lo disabilito
										((RemoveButton) lHLayoutMember).setAlwaysDisabled(true);
										((RemoveButton) lHLayoutMember).show();
									}									
								}
							}
						}
					}	
				}
			};
			assegnazioneSalvataItem.setName("listaAssegnazioniSalvate");
			assegnazioneSalvataItem.setFlgUdFolder("U");
			assegnazioneSalvataItem.setShowTitle(false);
			assegnazioneSalvataItem.setStartRow(true);
			assegnazioneSalvataItem.setNotReplicable(true);		
			if (isRequiredDetailSectionAssegnazione()) {
				assegnazioneSalvataItem.setAttribute("obbligatorio", true);			
			}			
			
			assegnazioneItem = new AssegnazioneItem() {
	
				@Override
				public boolean isProtInModalitaWizard() {
					return true;
				}
				
				@Override
				public String getSupportoOriginaleProt() {
					return getSupportoOriginale();
				}
	
				@Override
				public boolean isAttivoAssegnatarioUnicoCartaceoProt() {
					return isAttivoAssegnatarioUnicoCartaceo();
				}	
				
				@Override
				public boolean isAttivaRestrAssCartaceoProt() {
					return isAttivaRestrizioneAssegnazioneCartaceo();
				}
				
				@Override
				public void manageOnChanged() {
					if(isRequiredDetailSectionAssegnazione()) {
						redrawSections(detailSectionAssegnazione);
					}
				}
				
				@Override
				public Boolean validate() {	
					boolean valid = super.validate();
					valid = validateAssegnazioni() && valid;
					return valid;
				}
				
				@Override
				public Boolean valuesAreValid() {
					boolean valid = super.valuesAreValid();
					valid = valuesAreValidAssegnazioni() && valid;
					return valid;
				}
				
				@Override
				public boolean isObbligatorio() {
					if (assegnazioneSalvataItem != null && assegnazioneSalvataItem.getValueAsRecordList() != null && assegnazioneSalvataItem.getValueAsRecordList().getLength() > 0 && assegnazioneSalvataItem.hasValue()) {
						if(assegnazioneItem.getAssegnatarioUnico()) {
							return false;
						}
					}
					if(getNroAssegnazioni() > 0) {
						return false;
					}
					return super.isObbligatorio();
				}
				
				@Override
				public Boolean getNotReplicable() {
					if (assegnazioneSalvataItem != null && assegnazioneSalvataItem.getValueAsRecordList() != null && assegnazioneSalvataItem.getValueAsRecordList().getLength() > 0 && assegnazioneSalvataItem.hasValue()) {
						if(assegnazioneItem.getAssegnatarioUnico()) {
							return false;
						}
					}
					if(getNroAssegnazioni() > 0) {
						return false;
					}
					return super.getNotReplicable();
				}
				
			};
			assegnazioneItem.setName("listaAssegnazioni");
			assegnazioneItem.setFlgUdFolder("U");
			assegnazioneItem.setShowTitle(false);
			assegnazioneItem.setStartRow(true);		
			assegnazioneItem.setRedrawOnChange(true);
			if (isRequiredDetailSectionAssegnazione()) {
				assegnazioneItem.setAttribute("obbligatorio", true);
			}
			
		} else {
			super.createAssegnazioneItem();
		}
	}
	
	@Override
	protected void createConfermaAssegnazioneForm() {

		super.createConfermaAssegnazioneForm();		
		
		if(isModalitaWizard()) {
			confermaAssegnazioneForm.setTabID("HEADER");
		}
	}

	/**
	 * CONDIVISIONE
	 * 
	 */
	
	@Override
	protected void createCondivisioneForm() {
		
		super.createCondivisioneForm();
		
		if(isModalitaWizard()) {
			condivisioneForm.setTabID("HEADER");
		}
	}
	
	/**
	 * CLASSIFICAZIONE / FASCICOLAZIONE
	 * 
	 */	
	
	@Override
	protected void createClassificazioneFascicolazioneForm() {
		
		super.createClassificazioneFascicolazioneForm();
		
		if(isModalitaWizard()) {
			classificazioneFascicolazioneForm.setTabID("HEADER");
		}
	}

	/**
	 * FOLDER CUSTOM
	 * 
	 */
	
	@Override
	public String getTitleDetailSectionFolderCustom() {
		
		if(isModalitaWizard()) {
			return "Organizza";
		}
		return super.getTitleDetailSectionFolderCustom();
	}
		
	@Override
	protected void createFolderCustomForm() {
		
		super.createFolderCustomForm();
		
		if(isModalitaWizard()) {
			folderCustomForm.setTabID("HEADER");
		}
	}

	/**
	 * ALTRE VIE
	 * 
	 */
	
	@Override
	public boolean showTabAltreVie() {
		if(isModalitaWizard()) {
			return false;
		}
		return super.showTabAltreVie();
	}
	
	@Override
	public boolean showDetailSectionAltreVie() {
		return isModalitaWizard() || ProtocollazioneUtil.isAttivoAltreVieSenzaWizard();
	}
	
	@Override
	public String getTitleDetailSectionAltreVie() {
		
		if(isModalitaWizard()) {
			return "Indirizzi collegati";
		} 
		return super.getTitleDetailSectionAltreVie();
	}

	@Override
	public boolean showOpenDetailSectionAltreVie() {
		
		if(isModalitaWizard()) {
			return false;
		} 
		return super.showOpenDetailSectionAltreVie();
	}
		
	@Override
	protected void createAltreVieForm() {
		
		super.createAltreVieForm();
		
		if(isModalitaWizard()) {			
			altreVieForm.setTabID("HEADER");
		}
	}
	
	@Override
	public void manageOnChangedAltreVieItem() {
		if(isRequiredDetailSectionAltreVie()) {
			redrawSections(detailSectionAltreVie);
		}
	}
	
	/**
	 * DOCUMENTI COLLEGATI
	 * 
	 */
	
	protected void createDetailSectionDocumentiCollegati() {
		
		createDocumentiCollegatiForm();

		detailSectionDocumentiCollegati = new ProtocollazioneDetailSection("Documenti collegati", true, false, false, documentiCollegatiForm);
		detailSectionDocumentiCollegati.setViewReplicableItemHeight(450);
	}
	
	protected void createDocumentiCollegatiForm() {
		
		documentiCollegatiForm = new DynamicForm();
		documentiCollegatiForm.setValuesManager(vm);
		documentiCollegatiForm.setWidth("100%");
		documentiCollegatiForm.setHeight("5");
		documentiCollegatiForm.setPadding(5);
		documentiCollegatiForm.setTabSet(tabSet);
		documentiCollegatiForm.setTabID("HEADER");

		documentiCollegatiItem = new DocumentiCollegatiItem() {
			
			@Override
			public boolean isProtInModalitaWizard() {
				return isModalitaWizard();
			}
		};		
		documentiCollegatiItem.setName("listaDocumentiCollegati");
		documentiCollegatiItem.setShowTitle(false);
		
		documentiCollegatiForm.setFields(documentiCollegatiItem);
	}
	
	/**
	 * ALTRI RIFERIMENTI
	 * 
	 */
	
	protected void createDetailSectionAltriRiferimenti() {
		
		createAltriRiferimentiForm();

		detailSectionAltriRiferimenti = new ProtocollazioneDetailSection("Altri riferimenti", true, false, false, altriRiferimentiForm);
		detailSectionAltriRiferimenti.setViewReplicableItemHeight(450);
	}
	
	protected void createAltriRiferimentiForm() {
		
		altriRiferimentiForm = new DynamicForm();
		altriRiferimentiForm.setValuesManager(vm);
		altriRiferimentiForm.setWidth("100%");
		altriRiferimentiForm.setHeight("5");
		altriRiferimentiForm.setPadding(5);
		altriRiferimentiForm.setTabSet(tabSet);
		altriRiferimentiForm.setTabID("HEADER");

		altriRiferimentiItem = new AltriRiferimentiItem();
		altriRiferimentiItem.setName("listaAltriRiferimenti");
		altriRiferimentiItem.setShowTitle(false);
		
		altriRiferimentiForm.setFields(altriRiferimentiItem);
	}
	
	/**
	 * ALTRI DATI
	 * 
	 */
	
	@Override
	public String getTitleDetailSectionAltriDati() {
		
		if(isModalitaWizard()) {
			return "Note";
		}
		return super.getTitleDetailSectionAltriDati();
	}
	
	@Override
	protected void createAltriDatiForm() { // DIVERSO IN ATTI e ATTI2

		super.createAltriDatiForm();
		
		if(isModalitaWizard()) {			
			altriDatiForm.setFields(noteItem);
		}		
	}
	
	/****************
	 * ALTRI METODI *
	 ****************/

	@Override
	public void setInitialValues() {
		
		if(isModalitaWizard()) {
			if (detailSectionRegistrazione != null) {
				detailSectionRegistrazione.setVisible(false);
			}
			if (detailSectionTipoDocumento != null) {
				detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));				
			}
			for (Canvas member : layoutDatiPrincipali.getMembers()) {
				if (member instanceof DetailSection) {
					if (member instanceof HeaderDetailSection) {						
						if (((HeaderDetailSection) member).canCollapse() && !((HeaderDetailSection) member).showOpen()) {
							((HeaderDetailSection) member).openIfhasValue();
						} else {
							((HeaderDetailSection) member).open();
						}
					} else {						
						((DetailSection) member).openIfhasValue();
					}
				}
			}
			if (mode == null || mode.equals("new")) {
				redrawSections(null);
			}
		} else {
			super.setInitialValues();			
		}
	}
	
	@Override
	public boolean isOpenableDetailSection(DetailSection sectionToOpen) {
		
		if(isModalitaWizard()) {									
			for (final Canvas member : layoutDatiPrincipali.getMembers()) {
				if (member instanceof DetailSection) {
					DetailSection section = (DetailSection) member;
					if(section.getTitle() != null && !section.getTitle().equals(sectionToOpen.getTitle())) {
						if(section.isRequired() && !section.valuesAreValid()) {
							return false;													
						}
					} else break;					
				}
			}
		}
		return true;
	}
	
	@Override
	public String getOpenErrorMessageDetailSection(DetailSection sectionToOpen) {
		
		if(isModalitaWizard()) {									
			for (final Canvas member : layoutDatiPrincipali.getMembers()) {
				if (member instanceof DetailSection) {
					DetailSection section = (DetailSection) member;
					if(section.getTitle() != null && !section.getTitle().equals(sectionToOpen.getTitle())) {
						if(section.isRequired() && !section.validate()) {
							return "Obbligatorio prima compilare la sezione \"" + section.getTitle() + "\"";
						}
					} else break;
				}
			}
		}
		return null;
	}
	
	public FormItem getItemByNameInSection(DetailSection section, String fieldName) {
		if(section != null) {
			for(DynamicForm form : section.getForms()) {
				FormItem item = form.getItem(fieldName);
				if(item != null) {
					return item;
				} else {
					for(FormItem formItem : form.getFields()) {
						if(formItem instanceof ReplicableItem) {
							item = ((ReplicableItem)formItem).getItemByName(fieldName);
							if(item != null) {
								return item;
							}
						}
					}
				}				
			}
		}
		return null;
	}
	
	protected static native String getActiveElementId() /*-{
		try {
			return $doc.activeElement.id;
		} catch (err){
			return null;
		}
	}-*/;
	
	public void redrawSections(final DetailSection sectionFrom) {
		if(isModalitaWizard()) {			
			showHideSections();
			flgToRedraw = true;
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {
					
					if(flgToRedraw) {
						flgToRedraw = false;
						// se sono in modalità di visualizzazione non devo far scattare tutte le validazioni ma   
						// apro solo le sezioni che sono valorizzate
						if (mode != null && mode.equals("view")) {
							for (final Canvas member : layoutDatiPrincipali.getMembers()) {
								if (member instanceof DetailSection) {
									final DetailSection section = ((DetailSection) member);	
									section.openIfhasValue();
									section.redrawDetailSectionHeaderTitle();
								}
							}
							return;
						}
//						boolean inizia = (sectionFrom == null);
//						final String activeElementId = getActiveElementId();
						for (final Canvas member : layoutDatiPrincipali.getMembers()) {
							if (member instanceof DetailSection) {
								final DetailSection section = ((DetailSection) member);	
//									if(!inizia) {
//										inizia = sectionFrom != null && section.getTitle().equals(sectionFrom.getTitle());
//									} else {						
									if (section.isRequired()) {
										section.setDisabled(false);
										if(isOpenableDetailSection(section)) {
											section.open(new ServiceCallback<String>() {
						
												@Override
												public void execute(String openErrorMessage) {
													section.setDisabled(true);
													section.close();
												}
											});
										} else {
											section.setDisabled(true);
											section.close();
										}
									} else {
										if(isOpenableDetailSection(section)) {
											section.setDisabled(false);
											if (!section.containsFocus()) {
												// Devo evitare chi chiudere sezioni vuote in cui però ho il focus 
												// (altrimenti mi si potrebbero chiudere mentre inserisco i dati)
												if(section.isOpen()) {
													// Se la sezione era già aperta la lascio aperta 
													section.open();
												} else {
													section.openIfhasValue();												
												}
											}
											section.redrawDetailSectionHeaderTitle();
										} else {
											section.setDisabled(true);
											section.close();
										}
									}
//									}
							}
						}				
//						if(activeElementId != null && !"".equals(activeElementId)) {
//							Scheduler.get().scheduleDeferred(new ScheduledCommand() {
//
//								@Override
//								public void execute() {
//									Element element = DOM.getElementById(activeElementId);
//									if(element != null) {
//										element.focus();
//									}
//								}
//							});					
//						} else if(sectionFrom != null){
//							Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			//
//								@Override
//								public void execute() {
//									boolean next = false;
//									for (final Canvas member : layoutDatiPrincipali.getMembers()) {
//										if (member instanceof DetailSection) {
//											DetailSection section = (DetailSection) member;
//											if(next && section.isOpen()) {	
//												for(final FormItem item : section.getForms()[0].getFields()) {
//													if(item.isVisible() && (item.getCanFocus() != null && item.getCanFocus())) {
//														item.focusInItem();
//														break;
//													}
//												}
//												break;
//											}
//											if(section.getTitle() != null && section.getTitle().equals(sectionFrom.getTitle())) {
//												next = true;
//											}				
//										}
//									}
//								}
//							});						
//						}
					}						
				}
			});
		}
	}
	
	public void showHideSections() {
		// Se supporto digitale nascondo la collocazione fisica
		if (detailSectionCollocazioneFisica != null) {
			if (supportoOriginaleItem != null && supportoOriginaleItem.getValueAsString() != null && "digitale".equalsIgnoreCase(supportoOriginaleItem.getValueAsString())) {
				detailSectionCollocazioneFisica.hide();
			} else {
				detailSectionCollocazioneFisica.show();
			}
		}
	}
	
	@Override
	public boolean getShowFirstCanvasWhenEmptyAfterOpen() {
		
		if(isModalitaWizard()) {						
			return true;
		} else {
			return super.getShowFirstCanvasWhenEmptyAfterOpen();
		}
	}	

	@Override
	public boolean enableAcquisisciDaScannerMenuItem() {
		
		if(isModalitaWizard()) {						
			return !isFromEmail() && showUploadFilePrimario() && !isDigitale();
		} else {
			return super.enableAcquisisciDaScannerMenuItem();
		}
	}
	
	@Override
	public void manageChangedContenuti() {
		
		super.manageChangedContenuti();
		
		if(isModalitaWizard()) {			
			redrawSections(detailSectionContenuti);		
		}
	}

	@Override
	public void manageChangedPrimario() {
		
		super.manageChangedPrimario();
		
		if(isModalitaWizard()) {		
			
			Record detailRecord = new Record(vm.getValues());
			
			// Ricarico i mittenti per vedere se settare il valore di default nel check "effettua assegnazione"
			mittentiForm.clearErrors(true);
			if(mittentiItem != null) {
				mittentiItem.reloadTipoValueMap();
			}
			mittentiForm.redraw();
			
			destinatariForm.clearErrors(true);
			if(destinatariItem != null) {
				if (destinatariItem instanceof DestinatarioProtUscitaItem) {
					((DestinatarioProtUscitaItem) destinatariItem).redrawAddButtons();					
				}
				destinatariItem.reloadTipoValueMap();
			}
			destinatariForm.redraw();
			
			assegnazioneForm.clearErrors(true);
			if(assegnazioneItem != null) {
				if (mode == null || mode.equals("new")) {
					assegnazioneItem.show();
				} else if (detailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento")) {				
					assegnazioneItem.show();
				} else {
					assegnazioneItem.hide();
				}
				if (isAttivoAssegnatarioUnicoCartaceo()) {
					assegnazioneItem.setAssegnatarioUnico(true);
					assegnazioneItem.setFlgSenzaLD(true);
				} else {
					assegnazioneItem.setAssegnatarioUnico(false);
					assegnazioneItem.setFlgSenzaLD(false);
				} 	
				assegnazioneItem.reloadTipoValueMap();
				// se ho gia un assegnatario salvato ed è attivo l'assegnatario unico nascondo la lista delle assegnazioni
				if (assegnazioneItem.getAssegnatarioUnico() && assegnazioneSalvataItem != null && assegnazioneSalvataItem.getValueAsRecordList() != null && assegnazioneSalvataItem.getValueAsRecordList().getLength() > 0 && assegnazioneSalvataItem.hasValue()) {
					assegnazioneItem.drawAndSetValue(new RecordList());
					assegnazioneItem.hide();				
				}
			}			
			assegnazioneForm.redraw();
			
			showHideDetailSectionAssegnazione();
				
			filePrimarioForm.clearErrors(true);
			filePrimarioForm.markForRedraw();
			filePrimarioForm.validate();							
					
			fileAllegatiForm.clearErrors(true);
			fileAllegatiForm.redraw();
			
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
				@Override
				public void execute() {	
					mittentiItem.redraw();				
					mittentiItem.validate();
					destinatariItem.redraw();				
					destinatariItem.validate();		
					assegnazioneItem.redraw();
					assegnazioneItem.validate();	
					fileAllegatiItem.redraw();
					fileAllegatiItem.validate();
					// dopo i validate verifico se ci sono ancora errori da evidenziare nel tab
//					clearTabErrors();
//					showTabErrors();
				}
			});			
			
			redrawSections(detailSectionContenuti);					
		}
	}
	
	@Override
	public void manageChangedFilePrimario() {
		super.manageChangedFilePrimario();
		if(isModalitaWizard()) {			
			redrawSections(detailSectionContenuti);		
		}
	}		
	
	@Override
	public void manageChangedFilePrimarioOmissis() {
		super.manageChangedFilePrimarioOmissis();
		if(isModalitaWizard()) {			
			redrawSections(detailSectionContenuti);		
		}
	}	

	public Record buildRecordAssegnatarioFromDest(Record recordDest) {
		
		String typeNodo = null;
		String idUoSv = null;
		if (recordDest.getAttribute("tipoDestinatario") != null && "UOI".equals(recordDest.getAttribute("tipoDestinatario"))) {
			typeNodo = "UO";
			idUoSv = recordDest.getAttribute("idUoSoggetto");
		} else if (recordDest.getAttribute("tipoDestinatario") != null && "UP".equals(recordDest.getAttribute("tipoDestinatario"))) {
			typeNodo = "SV";
			idUoSv = recordDest.getAttribute("idScrivaniaSoggetto");
		}
		String organigramma = typeNodo + idUoSv;
		if (organigramma != null && !"".equals(organigramma)) {
			final Record recordAssegnatario = new Record();
			recordAssegnatario.setAttribute("idUo", idUoSv);
			recordAssegnatario.setAttribute("typeNodo", typeNodo);
			recordAssegnatario.setAttribute("tipo", "SV;UO");
			recordAssegnatario.setAttribute("codRapido", recordDest.getAttribute("codRapidoDestinatario"));
			recordAssegnatario.setAttribute("descrizione", recordDest.getAttribute("denominazioneDestinatario"));
			recordAssegnatario.setAttribute("organigramma", organigramma);
			return recordAssegnatario;
		}
		return null;
	}
	
	public void manageChangeFlgAssegnaAlMittDest(ChangeEvent event) {
		if(event.getValue() != null && (Boolean)event.getValue()) {	
			if (isModalitaWizard() && isAttivoAssegnatarioUnicoCartaceo()) {
				int nroAssegnazioni = getNroAssegnazioni();
				if(nroAssegnazioni > 0) {
					event.cancel();		
					AurigaLayout.addMessage(new MessageBean("Si può mettere un solo assegnatario", "", MessageType.ERROR));
				}
			}			
		}
	}
	
	public void manageChangedFlgAssegnaAlMittDest() {
		
		if (isModalitaWizard()) {
			
			if (assegnazioneForm != null) {
				assegnazioneForm.clearErrors(true);
				assegnazioneForm.redraw();
			}
		
			showHideDetailSectionAssegnazione();
			
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {	
					if (assegnazioneItem != null) {
						assegnazioneItem.redraw();
						assegnazioneItem.validate();	
					}
				}
			});		
		}
	}
	
	public void showHideDetailSectionAssegnazione() {
		
		if (isModalitaWizard()) {	
			
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {	
					if (detailSectionAssegnazione != null) {
						detailSectionAssegnazione.show();
					}
					if (mode == null || mode.equals("new")) {		
						int nroMittentiAssegnatari = getNroMittentiAssegnatari();
						int nroDestinatariAssegnatari = getNroDestinatariAssegnatari();
						if(nroMittentiAssegnatari > 0 || nroDestinatariAssegnatari > 0) {	
							 if(isAttivoAssegnatarioUnicoCartaceo()) {					
								int nroAssegnatari = getNroAssegnatari();
								if(nroAssegnatari == 0) {
									if (detailSectionAssegnazione != null) {
										detailSectionAssegnazione.hide();
									}
								}
							}
						}
					}
					redrawSections(detailSectionAssegnazione);
				}
			});
		}
	}
	
	public String getIdAssegnatarioFromRecordAssegnazione(Record record) {
		if("LD".equalsIgnoreCase(record.getAttribute("tipo"))) {
			return "G" + record.getAttribute("gruppo");				
		} else if("PREF".equalsIgnoreCase(record.getAttribute("tipo"))) {
			return record.getAttribute("typeNodo") + record.getAttribute("idUo");
		} else {
			return record.getAttribute("organigramma");
		}			
	}
	
	public int getNroAssegnatari() {
		RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
		int nroAssegnatari = 0;
		if(assegnazioneItem != null && assegnazioneItem.getVisible()) {
			if(listaAssegnazioni != null) {
				for(int n = 0; n < listaAssegnazioni.getLength(); n++) {
					String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioni.get(n));
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {
						nroAssegnatari++;
					}
				}
			}			
		}
		return nroAssegnatari;
	}
			
	public int getNroMittentiAssegnatari() {
		int nroMittentiAssegnatari = 0;
		RecordList listaMittenti = new Record(vm.getValues()).getAttributeAsRecordList("listaMittenti");
		if (listaMittenti != null) {
			for(int i = 0; i < listaMittenti.getLength(); i++) {
				if(listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente") != null && listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente")) {
					String idAssegnatario = null;
					if(listaMittenti.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUoSoggetto"))) {
						idAssegnatario = "UO" + listaMittenti.get(i).getAttribute("idUoSoggetto");
					} else if(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto"))) {
						idAssegnatario = "SV" + listaMittenti.get(i).getAttribute("idScrivaniaSoggetto");
					} else if(listaMittenti.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUserSoggetto"))) {
						idAssegnatario = "UT" + listaMittenti.get(i).getAttribute("idUserSoggetto");
					} 
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
						nroMittentiAssegnatari++;
					}
				}
			}
		}
		return nroMittentiAssegnatari;
	}
			
	public int getNroDestinatariAssegnatari() {
		int nroDestinatariAssegnatari = 0;
		RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
		if (listaDestinatari != null) {
			for(int i = 0; i < listaDestinatari.getLength(); i++) {
				if(listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario") != null && listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario")) {
					String idAssegnatario = null;
					if(listaDestinatari.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUoSoggetto"))) {
						idAssegnatario = "UO" + listaDestinatari.get(i).getAttribute("idUoSoggetto");
					} else if(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto"))) {
						idAssegnatario = "SV" + listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto");
					} else if(listaDestinatari.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUserSoggetto"))) {
						idAssegnatario = "UT" + listaDestinatari.get(i).getAttribute("idUserSoggetto");
					} 
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
						nroDestinatariAssegnatari++;
					}						
				}
			}
		}
		return nroDestinatariAssegnatari;
	}
	
	public String getIdAssegnatarioUnicoDaSalvare() {
		RecordList listaAssegnazioniSalvate = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioniSalvate");
		if(assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible()) {
			if(listaAssegnazioniSalvate != null && listaAssegnazioniSalvate.getLength() > 0) {
				return null;
			}		
		} else if(getNroAssegnazioni() == 1) {
			RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
			if(assegnazioneItem != null && assegnazioneItem.getVisible()) {
				if(listaAssegnazioni != null) {
					for(int n = 0; n < listaAssegnazioni.getLength(); n++) {
						String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioni.get(n));
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {
							return idAssegnatario;
						}
					}
				}			
			}
			RecordList listaMittenti = new Record(vm.getValues()).getAttributeAsRecordList("listaMittenti");
			if (listaMittenti != null) {
				for(int i = 0; i < listaMittenti.getLength(); i++) {
					if(listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente") != null && listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente")) {
						String idAssegnatario = null;
						if(listaMittenti.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaMittenti.get(i).getAttribute("idUoSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaMittenti.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaMittenti.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							return idAssegnatario;
						}
					}
				}
			}
			RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
			if (listaDestinatari != null) {
				for(int i = 0; i < listaDestinatari.getLength(); i++) {
					if(listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario") != null && listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario")) {
						String idAssegnatario = null;
						if(listaDestinatari.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaDestinatari.get(i).getAttribute("idUoSoggetto");
						} else if(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaDestinatari.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaDestinatari.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							return idAssegnatario;
						}						
					}
				}
			}
		}
		return null;
	}
			
	public int getNroAssegnazioni() {
		HashSet<String> assegnazioni = new HashSet<String>();
		RecordList listaAssegnazioniSalvate = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioniSalvate");
		int nroAssegnatariSalvati = 0;
		if(assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible()) {
			if(listaAssegnazioniSalvate != null) {
				for(int n = 0; n < listaAssegnazioniSalvate.getLength(); n++) {
					String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioniSalvate.get(n));
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {
						if(!assegnazioni.contains(idAssegnatario)) {
							assegnazioni.add(idAssegnatario);
							nroAssegnatariSalvati++;
						}
					}
				}
			}		
		}
		RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
		int nroAssegnatari = 0;
		if(assegnazioneItem != null && assegnazioneItem.getVisible()) {
			if(listaAssegnazioni != null) {
				for(int n = 0; n < listaAssegnazioni.getLength(); n++) {
					String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioni.get(n));
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {
						if(!assegnazioni.contains(idAssegnatario)) {
							assegnazioni.add(idAssegnatario);
							nroAssegnatari++;
						}
					}
				}
			}			
		}
		int nroMittentiAssegnatari = 0;
		RecordList listaMittenti = new Record(vm.getValues()).getAttributeAsRecordList("listaMittenti");
		if (listaMittenti != null) {
			for(int i = 0; i < listaMittenti.getLength(); i++) {
				if(listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente") != null && listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente")) {
					String idAssegnatario = null;
					if(listaMittenti.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUoSoggetto"))) {
						idAssegnatario = "UO" + listaMittenti.get(i).getAttribute("idUoSoggetto");
					} else if(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto"))) {
						idAssegnatario = "SV" + listaMittenti.get(i).getAttribute("idScrivaniaSoggetto");
					} else if(listaMittenti.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUserSoggetto"))) {
						idAssegnatario = "UT" + listaMittenti.get(i).getAttribute("idUserSoggetto");
					} 
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
						if(!assegnazioni.contains(idAssegnatario)) {
							assegnazioni.add(idAssegnatario);
							nroMittentiAssegnatari++;
						}
					}
				}
			}
		}
		int nroDestinatariAssegnatari = 0;
		RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
		if (listaDestinatari != null) {
			for(int i = 0; i < listaDestinatari.getLength(); i++) {
				if(listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario") != null && listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario")) {
					String idAssegnatario = null;
					if(listaDestinatari.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUoSoggetto"))) {
						idAssegnatario = "UO" + listaDestinatari.get(i).getAttribute("idUoSoggetto");
					} else if(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto"))) {
						idAssegnatario = "SV" + listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto");
					} else if(listaDestinatari.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUserSoggetto"))) {
						idAssegnatario = "UT" + listaDestinatari.get(i).getAttribute("idUserSoggetto");
					} 
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
						if(!assegnazioni.contains(idAssegnatario)) {
							assegnazioni.add(idAssegnatario);
							nroDestinatariAssegnatari++;
						}
					}						
				}
			}
		}
		int nroAssegnazioni = nroAssegnatariSalvati + nroAssegnatari + nroMittentiAssegnatari + nroDestinatariAssegnatari;
		return nroAssegnazioni;
	}
	
	public boolean validateAssegnazioni() {
		boolean valid = true;
		if (mode == null || mode.equals("new") || mode.equals("edit")) {						
			HashSet<String> assegnazioni = new HashSet<String>();
			RecordList listaAssegnazioniSalvate = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioniSalvate");
			int nroAssegnatariSalvati = 0;
			if(assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible()) {
				if(listaAssegnazioniSalvate != null) {
					for(int n = 0; n < listaAssegnazioniSalvate.getLength(); n++) {
						String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioniSalvate.get(n));						
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroAssegnatariSalvati++;
							}
						}
					}
				}													
			}
			RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
			int nroAssegnatari = 0;
			if(assegnazioneItem != null && assegnazioneItem.getVisible()) {
				if(listaAssegnazioni != null) {
					for(int n = 0; n < listaAssegnazioni.getLength(); n++) {
						String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioni.get(n));						
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroAssegnatari++;
							}
						}
					}
				}							
			}
			int nroMittentiAssegnatari = 0;
			RecordList listaMittenti = new Record(vm.getValues()).getAttributeAsRecordList("listaMittenti");
			if (listaMittenti != null) {
				for(int i = 0; i < listaMittenti.getLength(); i++) {
					if(listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente") != null && listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente")) {
						String idAssegnatario = null;
						if(listaMittenti.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaMittenti.get(i).getAttribute("idUoSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaMittenti.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaMittenti.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroMittentiAssegnatari++;
							}
						}
					}
				}
			}			
			int nroDestinatariAssegnatari = 0;
			RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
			if (listaDestinatari != null) {
				for(int i = 0; i < listaDestinatari.getLength(); i++) {
					if(listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario") != null && listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario")) {
						String idAssegnatario = null;
						if(listaDestinatari.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaDestinatari.get(i).getAttribute("idUoSoggetto");
						} else if(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaDestinatari.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaDestinatari.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroDestinatariAssegnatari++;
							}
						}						
					}
				}
			}				
			int nroAssegnazioni = nroAssegnatariSalvati + nroAssegnatari + nroMittentiAssegnatari + nroDestinatariAssegnatari;
			if(isRequiredDetailSectionAssegnazione()) {
				if (nroAssegnazioni == 0) {
					if (isOpenableDetailSection(detailSectionAssegnazione)) {
						detailSectionAssegnazione.open();
						assegnazioneForm.setFieldErrors("listaAssegnazioni", "Obbligatorio inserire almeno un assegnatario");
					}
					valid = false;					
				}
			}
			if (isAttivoAssegnatarioUnicoCartaceo()) {				
				if (nroAssegnazioni > 0) {
					if (nroAssegnazioni == 1 && nroAssegnatari == 1 && !validateAssegnatario(listaAssegnazioni.get(0))) {
						if (isOpenableDetailSection(detailSectionAssegnazione)) {
							detailSectionAssegnazione.open();
							assegnazioneForm.setFieldErrors("listaAssegnazioni", "Si può mettere un assegnatario che sia in organigramma e che sia\na) un punto di protocollo\nb) una UO/postazione che abbia come punto di protocollo collegato la UO che sta protocollando");
						}
						valid = false;
					} else if (nroAssegnazioni > 1 && nroAssegnazioni > nroAssegnatariSalvati) {
//						if (nroAssegnatariSalvati > 0) {
//							if (isOpenableDetailSection(detailSectionAssegnazione)) {
//								detailSectionAssegnazione.open();
//								assegnazioneForm.setFieldErrors("listaAssegnazioniSalvate", "Si può mettere un solo assegnatario");
//							}
//						}
						if (nroAssegnatari > 0) {
							if (isOpenableDetailSection(detailSectionAssegnazione)) {
								detailSectionAssegnazione.open();
								assegnazioneForm.setFieldErrors("listaAssegnazioni", "Si può mettere un solo assegnatario");
							}
						}
						if (nroMittentiAssegnatari > 0) {
							if (isOpenableDetailSection(detailSectionMittenti)) {
								detailSectionMittenti.open();
								mittentiForm.setFieldErrors("listaMittenti", "Si può mettere un solo assegnatario");
							}
						}
						if (nroDestinatariAssegnatari > 0) {
							if (isOpenableDetailSection(detailSectionDestinatari)) {
								detailSectionDestinatari.open();
								destinatariForm.setFieldErrors("listaDestinatari", "Si può mettere un solo assegnatario");
							}
						}
						valid = false;
					}
				}
			}
		}
		return valid;
	}
	
	public boolean valuesAreValidAssegnazioni() {
		boolean valid = true;
		if (mode == null || mode.equals("new") || mode.equals("edit")) {						
			HashSet<String> assegnazioni = new HashSet<String>();
			RecordList listaAssegnazioniSalvate = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioniSalvate");
			int nroAssegnatariSalvati = 0;
			if(assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible()) {
				if(listaAssegnazioniSalvate != null) {
					for(int n = 0; n < listaAssegnazioniSalvate.getLength(); n++) {
						String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioniSalvate.get(n));						
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroAssegnatariSalvati++;
							}
						}
					}
				}													
			}
			RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
			int nroAssegnatari = 0;
			if(assegnazioneItem != null && assegnazioneItem.getVisible()) {
				if(listaAssegnazioni != null) {
					for(int n = 0; n < listaAssegnazioni.getLength(); n++) {
						String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioni.get(n));						
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroAssegnatari++;
							}
						}
					}
				}							
			}
			int nroMittentiAssegnatari = 0;
			RecordList listaMittenti = new Record(vm.getValues()).getAttributeAsRecordList("listaMittenti");
			if (listaMittenti != null) {
				for(int i = 0; i < listaMittenti.getLength(); i++) {
					if(listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente") != null && listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente")) {
						String idAssegnatario = null;
						if(listaMittenti.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaMittenti.get(i).getAttribute("idUoSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaMittenti.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaMittenti.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroMittentiAssegnatari++;
							}
						}
					}
				}
			}			
			int nroDestinatariAssegnatari = 0;
			RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
			if (listaDestinatari != null) {
				for(int i = 0; i < listaDestinatari.getLength(); i++) {
					if(listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario") != null && listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario")) {
						String idAssegnatario = null;
						if(listaDestinatari.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaDestinatari.get(i).getAttribute("idUoSoggetto");
						} else if(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaDestinatari.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaDestinatari.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroDestinatariAssegnatari++;
							}
						}						
					}
				}
			}				
			int nroAssegnazioni = nroAssegnatariSalvati + nroAssegnatari + nroMittentiAssegnatari + nroDestinatariAssegnatari;
			if(isRequiredDetailSectionAssegnazione()) {
				if (nroAssegnazioni == 0) {
					valid = false;
				}
			}
			if (isAttivoAssegnatarioUnicoCartaceo()) {	
				if (nroAssegnazioni > 0) {
					if (nroAssegnazioni == 1 && nroAssegnatari == 1 && !validateAssegnatario(listaAssegnazioni.get(0))) {						
						valid = false;
					} else if (nroAssegnazioni > 1 && nroAssegnazioni > nroAssegnatariSalvati) {
						valid = false;
					}
				}
			}
		}
		return valid;
	}

	public boolean validateAssegnatario(Record recordAssegnatario) {
		
		if (recordAssegnatario == null)
			return false;
		return true;
	}

	@Override
	public boolean isFromCanaleSportello() {
		
		if(isModalitaWizard()) {
			if(showDetailSectionCanaleDataRicezione()) {
				return mezzoTrasmissioneItem != null  && mezzoTrasmissioneItem.getValue() != null 
						&& "CM".equals(mezzoTrasmissioneItem.getValueAsString());
			}
		}
		return false;
	}
	
	@Override
	public boolean isFromCanalePregresso() {
		
		if(isModalitaWizard()) {
			if(showDetailSectionCanaleDataRicezione()) {
				return mezzoTrasmissioneItem != null  && mezzoTrasmissioneItem.getValue() != null 
						&& "PREGR".equals(mezzoTrasmissioneItem.getValueAsString());
			}
		}
		return false;
	}

	public boolean isCanaleRicezioneDigitale() {
		
		if(showDetailSectionCanaleDataRicezione()) {
			return mezzoTrasmissioneItem != null && mezzoTrasmissioneItem.getValue() != null 
					&& ("PEC".equals(mezzoTrasmissioneItem.getValueAsString()) || "PEO".equals(mezzoTrasmissioneItem.getValueAsString()));
		}
		return false;
	}

	public boolean isCanaleRicezioneCartaceo() {
		
		if(showDetailSectionCanaleDataRicezione()) {
			return mezzoTrasmissioneItem != null && mezzoTrasmissioneItem.getValue() != null 
					&& ("R".equals(mezzoTrasmissioneItem.getValueAsString()) || "L".equals(mezzoTrasmissioneItem.getValueAsString()));
		}
		return false;
	}
	
	public String getSupportoOriginale() {
		
		if(supportoOriginaleItem != null) {
			return supportoOriginaleItem.getValueAsString();
		}
		return null;
	}

	public boolean isSupportoOriginaleDigitale() {
		return supportoOriginaleItem != null && supportoOriginaleItem.getValue() != null && "digitale".equals(supportoOriginaleItem.getValueAsString());
	}

	public boolean isSupportoOriginaleCartaceo() {
		return supportoOriginaleItem != null && supportoOriginaleItem.getValue() != null && "cartaceo".equals(supportoOriginaleItem.getValueAsString());
	}

	public boolean isDigitale() {
		return isCanaleRicezioneDigitale() || isSupportoOriginaleDigitale();
	}

	public boolean isCartaceo() {
		return isCanaleRicezioneCartaceo() || isSupportoOriginaleCartaceo();
	}
	
	// Indica se il documento è digitale oppure se è cartaceo/misto ma per primario e tutti gli allegati c'è il file ed è indicato che il file è copia sostitutiva (su tutti nel caso di supporto cartaceo e nel caso misto solo qu quelli per cui è indicato che l'originale dello specifico file è cartaceo)
	public boolean isDigitaleOrDigitalizzatoSostituivamente() {
		
		if(!isDigitale()) {
			if(uriFilePrimarioItem != null) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					boolean flgOriginaleCartaceo = filePrimarioForm.getValue("flgOriginaleCartaceo") != null && (Boolean) filePrimarioForm.getValue("flgOriginaleCartaceo");
					if(isCartaceo() || flgOriginaleCartaceo) {
						boolean flgCopiaSostitutiva = filePrimarioForm.getValue("flgCopiaSostitutiva") != null && (Boolean) filePrimarioForm.getValue("flgCopiaSostitutiva");
						if(!flgCopiaSostitutiva) {
							return false;
						}
					}			
				} else return false;	
			} else return false;
			if(fileAllegatiItem != null) {
				if(fileAllegatiItem instanceof AllegatiGridItem) {
					RecordList listaAllegati = ((AllegatiGridItem)fileAllegatiItem).getData();
					if(listaAllegati != null) {
						for(int i = 0; i < listaAllegati.getLength(); i++) {
							Record recordAllegato = listaAllegati.get(i);
							if (recordAllegato.getAttribute("uriFileAllegato") != null && !recordAllegato.getAttribute("uriFileAllegato").equals("")) {
								boolean flgOriginaleCartaceo = recordAllegato.getAttributeAsBoolean("flgOriginaleCartaceo") != null && recordAllegato.getAttributeAsBoolean("flgOriginaleCartaceo");
								if(isCartaceo() || flgOriginaleCartaceo) {
									boolean flgCopiaSostitutiva = recordAllegato.getAttributeAsBoolean("flgCopiaSostitutiva") != null && recordAllegato.getAttributeAsBoolean("flgCopiaSostitutiva");
									if(!flgCopiaSostitutiva) {
										return false;
									}
								}
							} else return false;
						}
					}
				} else if(fileAllegatiItem instanceof AllegatiItem) {
					for (ReplicableCanvas canvas : ((AllegatiItem)fileAllegatiItem).getAllCanvas()) {
						if(canvas.hasValue(((AllegatiItem)fileAllegatiItem).getCanvasDefaultRecord())) {
							Record recordAllegato = canvas.getFormValuesAsRecord();
							if (recordAllegato.getAttribute("uriFileAllegato") != null && !recordAllegato.getAttribute("uriFileAllegato").equals("")) {
								boolean flgOriginaleCartaceo = recordAllegato.getAttributeAsBoolean("flgOriginaleCartaceo") != null && recordAllegato.getAttributeAsBoolean("flgOriginaleCartaceo");
								if(isCartaceo() || flgOriginaleCartaceo) {
									boolean flgCopiaSostitutiva = recordAllegato.getAttributeAsBoolean("flgCopiaSostitutiva") != null && recordAllegato.getAttributeAsBoolean("flgCopiaSostitutiva");
									if(!flgCopiaSostitutiva) {
										return false;
									}
								}
							} else return false;
						}
					}
				}
			} else return false;
			return true;
		}
		return true;
	}
	
	public boolean isSupportoOriginaleValorizzato() {
		if(supportoOriginaleItem != null) {
			return supportoOriginaleItem.getValue() != null && !"".equals(supportoOriginaleItem.getValueAsString());
		}
		return false;
	}
	
	public boolean isAttivoAssegnatarioUnicoCartaceo() {
		return isModalitaWizard() && AurigaLayout.getParametroDBAsBoolean("ASSEGNATARIO_UNICO_CARTACEO") && 
				isSupportoOriginaleValorizzato() && !isDigitaleOrDigitalizzatoSostituivamente();
	}
	
	public boolean isAttivaRestrizioneAssegnazioneCartaceo() {
		return isModalitaWizard() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_RESTR_ASS_CARTACEO") && 
				isSupportoOriginaleValorizzato() && !isDigitaleOrDigitalizzatoSostituivamente();
	}

	@Override
	public Record getRecordToSave(String motivoVarDatiReg) {
		
		Record lRecordToSave = super.getRecordToSave(motivoVarDatiReg);
		
		if(isModalitaWizard()) {
			lRecordToSave.setAttribute("flgSkipControlliCartaceo", isDigitaleOrDigitalizzatoSostituivamente());
			if(showDetailSectionCanaleDataRicezione()) {
				addFormValues(lRecordToSave, canaleDataRicezioneForm);
			}
			addFormValues(lRecordToSave, supportoOriginaleForm);
			addFormValues(lRecordToSave, interessatiForm);
			addFormValues(lRecordToSave, altreVieForm);
			addFormValues(lRecordToSave, documentiCollegatiForm);
			addFormValues(lRecordToSave, altriRiferimentiForm);
		}
		
		return lRecordToSave;
	}
	
	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
		
		if(isModalitaWizard()) {
			if(showDetailSectionCanaleDataRicezione()) {
				if (mezzoTrasmissioneItem != null) {
					GWTRestDataSource mezzoTrasmissioneDS = (GWTRestDataSource) mezzoTrasmissioneItem.getOptionDataSource();
					if (record.getAttribute("mezzoTrasmissione") != null
							&& !"".equals(record.getAttributeAsString("mezzoTrasmissione"))) {
						mezzoTrasmissioneDS.addParam("codMezzoTrasm", record.getAttributeAsString("mezzoTrasmissione"));
					} else {
						mezzoTrasmissioneDS.addParam("codMezzoTrasm", null);
					}
					mezzoTrasmissioneItem.setOptionDataSource(mezzoTrasmissioneDS);
				}			
			}
		}
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		
		super.setCanEdit(canEdit);
		
		if(isModalitaWizard()) {
			redrawTabsAfterChangeMode();
		}
	}
	
	public VLayout getLayoutAssegnazioneEClassificazione() {

		if(isModalitaWizard()) {
			
			VLayout layoutAssegnazioneEClassificazione = new VLayout(5);

			layoutAssegnazioneEClassificazione.addMember(detailSectionAssegnazione);

			layoutAssegnazioneEClassificazione.addMember(detailSectionCondivisione);

			layoutAssegnazioneEClassificazione.addMember(detailSectionClassificazioneFascicolazione);

			if (showDetailSectionFolderCustom()) {
				layoutAssegnazioneEClassificazione.addMember(detailSectionFolderCustom);
			}

			return layoutAssegnazioneEClassificazione;
		
		} else {
			return super.getLayoutAssegnazioneEClassificazione();
		}
	}
	
	public void redrawTabsAfterChangeMode() {
		
		if(isModalitaWizard()) {
			
			if(mode != null && "view".equals(mode)) {
			
				if(tabSet.getTabWithID("ASSEGN_CLASSIF") == null) {
				
					createTabAssegnazioneEClassificazione();
				
					tabSet.addTab(tabAssegnazioneEClassificazione, 1);				
				}
			
			} else if(tabSet.getTabWithID("ASSEGN_CLASSIF") != null) {	
				
				int posDetailSectionAssegnazione = getMemberIndex(layoutDatiPrincipali.getMembers(), markDetailSectionAssegnazione) + 1;
				if(posDetailSectionAssegnazione != -1) {
					layoutDatiPrincipali.addMember(detailSectionAssegnazione, posDetailSectionAssegnazione);
				}
			
				int posDetailSectionCondivisione = getMemberIndex(layoutDatiPrincipali.getMembers(), markDetailSectionCondivisione) + 1;
				if(posDetailSectionCondivisione != -1) {
					layoutDatiPrincipali.addMember(detailSectionCondivisione, posDetailSectionCondivisione);
				}
			
				int posDetailSectionClassificazioneFascicolazione = getMemberIndex(layoutDatiPrincipali.getMembers(), markDetailSectionClassificazioneFascicolazione) + 1;
				if(posDetailSectionClassificazioneFascicolazione != -1) {
					layoutDatiPrincipali.addMember(detailSectionClassificazioneFascicolazione, posDetailSectionClassificazioneFascicolazione);
				}
			
				if (showDetailSectionFolderCustom()) {
					int posDetailSectionFolderCustom = getMemberIndex(layoutDatiPrincipali.getMembers(), markDetailSectionFolderCustom) + 1;
					if(posDetailSectionFolderCustom != -1) {
						layoutDatiPrincipali.addMember(detailSectionFolderCustom, posDetailSectionFolderCustom);
					}
				}
			
				tabSet.removeTab(tabAssegnazioneEClassificazione);
			}
		}
	}
	
	public int getMemberIndex(Canvas[] members, Canvas member) {
		
		if(members != null) {
			for(int i = 0; i < members.length; i++) {
				if(member != null && members[i].equals(member)) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public void modificaDatiMode(Boolean abilAggiuntaFile) {
		
		super.modificaDatiMode(abilAggiuntaFile);
		
		if(isModalitaWizard()) {
			if(showDetailSectionCanaleDataRicezione()) {
				if (mezzoTrasmissioneItem != null) {
					mezzoTrasmissioneItem.setCanEdit(false);
				}
				
				if (dataEOraArrivoItem != null) {
					dataEOraArrivoItem.setCanEdit(false);
				}
			}
			if (supportoOriginaleItem != null) {
				supportoOriginaleItem.setCanEdit(false);
			}
			if (esibentiItem != null) {
				esibentiItem.setCanEdit(false);
			}
			if (esibentiForm != null) {
				setCanEdit(false, esibentiForm);
			}
//			if (interessatiForm != null) {
//				setCanEdit(false, interessatiForm);
//			}
//			if (altreVieForm != null) {
//				setCanEdit(false, altreVieForm);
//			}			
		}
	}
	
	@Override
	public void editMode() {
		
		super.editMode();
		
		// Nella maschera di dettaglio UD in modalità wizard dobbiamo consentire la modifica di supporto e canale, nel caso di entrata. Devono essere possibili tutte le variazioni.
		/*
		if(isModalitaWizard()) {
			if(showDetailSectionCanaleDataRicezione()) {
				if (mezzoTrasmissioneItem != null) {
					mezzoTrasmissioneItem.setCanEdit(false);
				}
				
				if (dataEOraArrivoItem != null) {
					dataEOraArrivoItem.setCanEdit(false);
				}
			}
			if (supportoOriginaleItem != null) {
				supportoOriginaleItem.setCanEdit(false);
			}
		}
		*/
	}
	
	@Override
	public void clickRegistra(final String motivoVarDatiReg) {
		
		manageGestionePuntiProtocollo(new ServiceCallback<String>() {

			@Override
			public void execute(String puntoProtocollo) {
				Record lRecordToSave = getRecordToSave(motivoVarDatiReg);
				if (puntoProtocollo != null && !"".equals(puntoProtocollo)) {
					lRecordToSave.setAttribute("puntoProtAssegnatario", puntoProtocollo);
				}				
				manageClickRegistra(lRecordToSave);		
			}			
		});
		
	}
	
	public void manageGestionePuntiProtocollo(final ServiceCallback<String> callback) {

		if(callback != null) {
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT") && isModalitaWizard() && isAttivoAssegnatarioUnicoCartaceo()) {				
				GWTRestDataSource lPuntiProtocolloUoSvDatasource = new GWTRestDataSource("PuntiProtocolloUoSvDatasource", "key", FieldType.TEXT);
				lPuntiProtocolloUoSvDatasource.addParam("idAssegnatario", getIdAssegnatarioUnicoDaSalvare());
				lPuntiProtocolloUoSvDatasource.fetchData(null, new DSCallback() {
	
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {							
							Record[] data = response.getData();
							if (data.length > 1) {
								SelezionaPuntoProtocolloWindow selezionaPuntoProtocolloWindow = new SelezionaPuntoProtocolloWindow(data, callback);
								selezionaPuntoProtocolloWindow.show();
							} else if (data.length == 1) {
								callback.execute(data[0].getAttribute("key"));							
							} else {
								callback.execute(null);							
							}
						} else {
							AurigaLayout.addMessage(new MessageBean("Si è verificato un errore durante la selezione del punto di protocollo associato all'assegnatario", "", MessageType.ERROR));
						}
					}
				});
			} else {
				callback.execute(null);				
			}
		}
	}
	
	@Override
	public void nuovoDettaglio(CustomLayout layout) {
		
		super.nuovoDettaglio(layout);
		
		if(isModalitaWizard()) {			
			if(showDetailSectionCanaleDataRicezione()) {
				if (mezzoTrasmissioneItem != null) {
					String mezzoTrasmissioneDefault = AurigaLayout.getImpostazioniDocumento("mezzoTrasmissione");
					if(mezzoTrasmissioneDefault != null && !"".equals(mezzoTrasmissioneDefault)) {
						canaleDataRicezioneForm.setValue("mezzoTrasmissione", mezzoTrasmissioneDefault);					
						mezzoTrasmissioneItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
						mezzoTrasmissioneItem.fireEvent(new ChangedEvent(mezzoTrasmissioneItem.getJsObj()));						
					}						
				}		
			}
			if (supportoOriginaleItem != null) {	
				if(isCanaleRicezioneDigitale()) {
					supportoOriginaleForm.setValue("supportoOriginale", "digitale");
					supportoOriginaleItem.setCanEdit(false);			
					supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
					supportoOriginaleItem.fireEvent(new ChangedEvent(supportoOriginaleItem.getJsObj()));			
				} else if(isCanaleRicezioneCartaceo()) {
					supportoOriginaleForm.setValue("supportoOriginale", "cartaceo");
					supportoOriginaleItem.setCanEdit(false);			
					supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
					supportoOriginaleItem.fireEvent(new ChangedEvent(supportoOriginaleItem.getJsObj()));			
				} else {
					String supportoOriginaleDefault = null;
					if(isRepertorioDetailEntrata()) {
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioEntrata");
					} else if( isRepertorioDetailInterno()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioInterno");
					} else if(isRepertorioDetailUscita()) {
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioUscita");
					} else if(isProtocollazioneDetailEntrata()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoEntrata");
					} else if(isProtocollazioneDetailUscita()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoUscita");
					} else if(isProtocollazioneDetailInterna()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoInterna");
					} else if(isProtocollazioneDetailBozze()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoBozze");
					} 
					if(supportoOriginaleDefault != null && !"".equals(supportoOriginaleDefault)) {
						supportoOriginaleForm.setValue("supportoOriginale", supportoOriginaleDefault);
						supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
						supportoOriginaleItem.fireEvent(new ChangedEvent(supportoOriginaleItem.getJsObj()));				
					}				
				}
			}
		}
	}
	
	@Override
	public void nuovoDettaglio(CustomLayout layout, Map initialValues) {
		
		super.nuovoDettaglio(layout, initialValues);
		
		if(isModalitaWizard()) {	
			if(showDetailSectionCanaleDataRicezione()) {
				if (mezzoTrasmissioneItem != null) {
					if (isFromEmail()) {
						if(isFromCasellaPEC()) {
							canaleDataRicezioneForm.setValue("mezzoTrasmissione", "PEC");
						} else {
							canaleDataRicezioneForm.setValue("mezzoTrasmissione", "PEO");
						}
						mezzoTrasmissioneItem.setCanEdit(false);
						mezzoTrasmissioneItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
						mezzoTrasmissioneItem.fireEvent(new ChangedEvent(mezzoTrasmissioneItem.getJsObj()));						
					} else if(mezzoTrasmissioneItem.getValueAsString() == null || "".equals(mezzoTrasmissioneItem.getValueAsString())) {
						String mezzoTrasmissioneDefault = AurigaLayout.getImpostazioniDocumento("mezzoTrasmissione");
						if(mezzoTrasmissioneDefault != null && !"".equals(mezzoTrasmissioneDefault)) {
							canaleDataRicezioneForm.setValue("mezzoTrasmissione", mezzoTrasmissioneDefault);
							mezzoTrasmissioneItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
							mezzoTrasmissioneItem.fireEvent(new ChangedEvent(mezzoTrasmissioneItem.getJsObj()));						
						}	
					}
				}		
			}
			if (supportoOriginaleItem != null) {
				if(isFromEmail() || isCanaleRicezioneDigitale()) {
					supportoOriginaleForm.setValue("supportoOriginale", "digitale");
					supportoOriginaleItem.setCanEdit(false);			
					supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
					supportoOriginaleItem.fireEvent(new ChangedEvent(supportoOriginaleItem.getJsObj()));			
				} else if(isCanaleRicezioneCartaceo()) {
					supportoOriginaleForm.setValue("supportoOriginale", "cartaceo");
					supportoOriginaleItem.setCanEdit(false);			
					supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
					supportoOriginaleItem.fireEvent(new ChangedEvent(supportoOriginaleItem.getJsObj()));			
				} else {
					String supportoOriginaleDefault = null;
					if(isRepertorioDetailEntrata()) {
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioEntrata");
					} else if(isRepertorioDetailInterno()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioInterno");
					} else if(isRepertorioDetailUscita()) {
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioUscita");
					} else if(isProtocollazioneDetailEntrata()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoEntrata");
					} else if(isProtocollazioneDetailUscita()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoUscita");
					} else if(isProtocollazioneDetailInterna()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoInterna");
					} else if(isProtocollazioneDetailBozze()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoBozze");
					} 
					if(supportoOriginaleDefault != null && !"".equals(supportoOriginaleDefault)) {
						supportoOriginaleForm.setValue("supportoOriginale", supportoOriginaleDefault);
						supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
						supportoOriginaleItem.fireEvent(new ChangedEvent(supportoOriginaleItem.getJsObj()));
					}		
				}
			}
		}
	}
	
	@Override
	public void nuovoDettaglioMail(Record lRecord) {	
		
		super.nuovoDettaglioMail(lRecord);
		
		if(isModalitaWizard()) {	
			if(showDetailSectionCanaleDataRicezione()) {
				if (mezzoTrasmissioneItem != null) {
					if (isFromEmail()) {
						if(isFromCasellaPEC()) {
							canaleDataRicezioneForm.setValue("mezzoTrasmissione", "PEC");
						} else {
							canaleDataRicezioneForm.setValue("mezzoTrasmissione", "PEO");
						}
						mezzoTrasmissioneItem.setCanEdit(false);
						mezzoTrasmissioneItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
						mezzoTrasmissioneItem.fireEvent(new ChangedEvent(mezzoTrasmissioneItem.getJsObj()));						
					} else if(mezzoTrasmissioneItem.getValueAsString() == null || "".equals(mezzoTrasmissioneItem.getValueAsString())) {
						String mezzoTrasmissioneDefault = AurigaLayout.getImpostazioniDocumento("mezzoTrasmissione");
						if(mezzoTrasmissioneDefault != null && !"".equals(mezzoTrasmissioneDefault)) {
							canaleDataRicezioneForm.setValue("mezzoTrasmissione", mezzoTrasmissioneDefault);
							mezzoTrasmissioneItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
							mezzoTrasmissioneItem.fireEvent(new ChangedEvent(mezzoTrasmissioneItem.getJsObj()));						
						}	
					}
				}		
			}
			if (supportoOriginaleItem != null) {
				if(isFromEmail() || isCanaleRicezioneDigitale()) {
					supportoOriginaleForm.setValue("supportoOriginale", "digitale");
					supportoOriginaleItem.setCanEdit(false);			
					supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
					supportoOriginaleItem.fireEvent(new ChangedEvent(supportoOriginaleItem.getJsObj()));			
				} else if(isCanaleRicezioneCartaceo()) {
					supportoOriginaleForm.setValue("supportoOriginale", "cartaceo");
					supportoOriginaleItem.setCanEdit(false);			
					supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
					supportoOriginaleItem.fireEvent(new ChangedEvent(supportoOriginaleItem.getJsObj()));			
				} else {
					String supportoOriginaleDefault = null;
					if(isRepertorioDetailEntrata()) {
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioEntrata");
					} else if(isRepertorioDetailInterno()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioInterno");
					} else if(isRepertorioDetailUscita()) {
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioUscita");
					} else if(isProtocollazioneDetailEntrata()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoEntrata");
					} else if(isProtocollazioneDetailUscita()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoUscita");
					} else if(isProtocollazioneDetailInterna()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoInterna");
					} else if(isProtocollazioneDetailBozze()){
						supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoBozze");
					} 
					if(supportoOriginaleDefault != null && !"".equals(supportoOriginaleDefault)) {
						supportoOriginaleForm.setValue("supportoOriginale", supportoOriginaleDefault);
						supportoOriginaleItem.setAttribute("skipResetDefaultValueFlgAssegnaAlMittDest", true);						
						supportoOriginaleItem.fireEvent(new ChangedEvent(supportoOriginaleItem.getJsObj()));			
					}						
				}
			}
		}
	}
	
	@Override
	public boolean isAttivaStampaEtichettaAutoReg(Record record) {
		
		if(isModalitaWizard()) {
			/**
			 * La stampa automatica in modalita wizard deve partire solo in caso di supporto cartaceo o misto
			 */
			return AurigaLayout.getImpostazioneStampaAsBoolean("stampaEtichettaAutoReg") && !isDigitale();					
		} else {
			return super.isAttivaStampaEtichettaAutoReg(record);
		}
	}
	
	/**
	 * ottavio: 
	 * Ho commentato l'@Override perche' il tasto stampa etichetta viene gestito dalla store, 
	 * tramite l'abilitazione #Abilitazioni.StampaEtichetta nella super.showStampaEtichettaButton(record)
	 */
	/*
	@Override
	public boolean showStampaEtichettaButton(Record record) {
		
		**
		 * Il tasto stampa etichetta dove ho il wizard e dove VIETATA_STAMPA_ETICHETTA_DOC_DIG = true deve apparire solo in caso di protocollo cartaceo o misto, invece nel caso senza wizard possiamo lasciarla sempre
		 *
		if(isModalitaWizard()) {
			if (AurigaLayout.getParametroDBAsBoolean("VIETATA_STAMPA_ETICHETTA_DOC_DIG") && isDigitale()) {
				return false;
			}
			return true;	
		} else {
			return super.showStampaEtichettaButton(record);
		}			
	}
	*/

	@Override
	protected boolean isAttivaStampaEtichettaPostAss(Record record) {
		
		if( isSupportoOriginaleCartaceo() && 
				isConProtocolloGenerale() &&
				AurigaLayout.getParametroDBAsBoolean("ATTIVA_STAMPA_AUTO_ETICH_POST_ASS") &&
				AurigaLayout.getImpostazioneStampaAsBoolean("stampaEtichettaAutoReg") ){
			return true;
		}
		return false;
	}

}