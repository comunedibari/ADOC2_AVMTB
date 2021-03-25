package it.eng.auriga.ui.module.layout.client.gestioneatti.delibere;

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.archivio.ContenutiFascicoloPopup;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.OperazioniEffettuateWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class StoricoDiscussioneSedutaDetail extends CustomDetail {
	
	private TabSet tabSet;
	
	// 1 TAB
	private Tab tabDatiSeduta;
	private VLayout layoutTabDatiSeduta;
	private DetailSection sectionDatiDiscussioneSeduta;
	private DynamicForm formDatiDiscussione;	
	private HiddenItem idSedutaItem;
	private TextItem numeroItem;
	private ImgButtonItem operazioniEffettuateButton;
	private ImgButtonItem visualizzaContenutiFascicoloButton;
	private DateTimeItem dtPrimaConvocazioneItem;
	private TextItem luogoPrimaConvocazioneItem;
	private DateTimeItem dtSecondaConvocazioneItem;
	private TextItem luogoSecondaConvocazioneItem;
	private TextItem desStatoSedutaItem;
	private TextItem tipoSessioneItem;
	//private ButtonItem viewOdgConvocazioneItem;
	private DynamicForm formListaDatiDiscussione;	
	private HiddenItem esitiXtipoArgomentoItem;
	private DetailSection sectionListaDatiDiscussioneSeduta;
	private ListaDatiDiscussioneSedutaItem listaDatiDiscussioneSedutaItem;
	
	// 2 TAB
	private Tab tabDatiPrimaConv;
	private VLayout layoutTabDatiPrimaConv;
	private DetailSection sectionDatiPrimaConv;
	private DynamicForm formDatiPrimaConv;
	private DateTimeItem dtInizioLavoriPrimaConvItem;
	private DateTimeItem dtFineLavoriPrimaConvItem;
	private HiddenItem fileVerbalePrimaConvItem;
	private DetailSection sectionListaDatiPrimaConv;
	private DynamicForm formListaDatiPrimaConv;
	private ListaDatiDiscussionePrimaConvocazioneItem listaDatiDiscussionePrimaConvItem;
	private DetailSection verbalePrimaConvSection;
	private DynamicForm formVerbalePrimaConv;
	private CKEditorItem editorVerbalePrimaConvItem;
	private ButtonItem visualizzaVerbalePrimaConvItem;
	
	// 3 TAB
	private Tab tabDatiSecondaConv;
	private VLayout layoutTabDatiSecondaConv;
	private DetailSection sectionDatiSecondaConv;
	private DynamicForm formDatiSecondaConv;
	private DateTimeItem dtInizioLavoriSecondaConvItem;
	private DateTimeItem dtFineLavoriSecondaConvItem;
	private HiddenItem fileVerbaleSecondaConvItem;
	private DetailSection sectionListaDatiSecondaConv;
	private DynamicForm formListaDatiSecondaConv;
	private ListaDatiDiscussioneSecondaConvocazioneItem listaDatiDiscussioneSecondaConvItem;
	private DetailSection verbaleSecondaConvSection;
	private DynamicForm formVerbaleSecondaConv;
	private CKEditorItem editorVerbaleSecondaConvItem;
	private ButtonItem visualizzaVerbaleSecondaConvItem;
	
	private Record recordCommissione;
	
	private String statoSeduta;
	private String organoCollegiale;
	private String codCircoscrizione;
	
	private static String prima_convocazione = "1a_convocazione";
	private static String prima_convocazione_deserta = "1a_convocazione_deserta";
	private static String seconda_convocazione = "2a_convocazione";

	public StoricoDiscussioneSedutaDetail(String nomeEntita, Record recordCommissione, String organoColleggiale) {
		super(nomeEntita);
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		this.recordCommissione = recordCommissione;
		
		this.organoCollegiale = organoColleggiale;
		
		this.codCircoscrizione = recordCommissione != null && recordCommissione.getAttributeAsRecord("odgInfo") != null &&
						recordCommissione.getAttributeAsRecord("odgInfo").getAttributeAsString("codCircoscrizione") != null ?
						recordCommissione.getAttributeAsRecord("odgInfo").getAttributeAsString("codCircoscrizione") : null;
						
		this.statoSeduta = recordCommissione != null && recordCommissione.getAttributeAsString("stato") != null ?
				recordCommissione.getAttributeAsString("stato") : null;
				
		createTabSet();

		VLayout mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(tabSet);
		
		setMembers(mainLayout);	
		
		setCanEdit(false);
	}
	
	protected void createTabSet() {
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);
		
		/**
		 *  Tab Seduta
		 */

		tabDatiSeduta = new Tab("<b>" + getTitleTabDatiSeduta() + "</b>");
		tabDatiSeduta.setAttribute("tabID", "HEADER");
		tabDatiSeduta.setPrompt(getTitleTabDatiSeduta());


		layoutTabDatiSeduta = createLayoutTab(getLayoutTabDatiSeduta(), lVLayoutSpacer);

		// Aggiungo i layout ai tab
		tabDatiSeduta.setPane(layoutTabDatiSeduta);

		tabSet.addTab(tabDatiSeduta);
		
		/**
		 *  Tab Prima Convocazione
		 */
		
		tabDatiPrimaConv = new Tab("<b>" + getTitleTabDatiPrimaConvocazione() + "</b>");
		tabDatiPrimaConv.setAttribute("tabID", "PRIMA_CONV");
		tabDatiPrimaConv.setPrompt(getTitleTabDatiPrimaConvocazione());


		layoutTabDatiPrimaConv = createLayoutTab(getLayoutTabDatiPrimaConvocazione(), lVLayoutSpacer);

		// Aggiungo i layout ai tab
		tabDatiPrimaConv.setPane(layoutTabDatiPrimaConv);

		tabSet.addTab(tabDatiPrimaConv);
		
		/**
		 * Tab Seconda Convocazione
		 */
		
		tabDatiSecondaConv = new Tab("<b>" + getTitleTabDatiSecondaConvocazione() + "</b>");
		tabDatiSecondaConv.setAttribute("tabID", "SECONDA_CONV");
		tabDatiSecondaConv.setPrompt(getTitleTabDatiSecondaConvocazione());

		layoutTabDatiSecondaConv = createLayoutTab(getLayoutTabDatiSecondaConvocazione(), lVLayoutSpacer);

		// Aggiungo i layout ai tab
		tabDatiSecondaConv.setPane(layoutTabDatiSecondaConv);

		tabSet.addTab(tabDatiSecondaConv);
	}
	
	public void caricaDettaglio(Record record) {
		recordCommissione = record;
		editRecord(record);
		
		if(listaDatiDiscussioneSedutaItem != null) {
			listaDatiDiscussioneSedutaItem.setCanEdit(false);
		}
	
		markForRedraw();
	}

	protected VLayout createLayoutTab(VLayout layout, VLayout spacerLayout) {
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		return layoutTab;
	}
	
	private String getTitleTabDatiSeduta() {
		return "Seduta";
	}
	
	private String getTitleTabDatiPrimaConvocazione() {
		return "1° Convocazione";
	}
	
	private String getTitleTabDatiSecondaConvocazione() {
		return "2° Convocazione";
	}
	
	public VLayout getLayoutTabDatiSeduta() {
		
		formDatiDiscussione = new DynamicForm();
		formDatiDiscussione.setTabID("HEADER");		
		formDatiDiscussione.setValuesManager(vm);
		formDatiDiscussione.setWidth("100%");
		formDatiDiscussione.setHeight("5");
		formDatiDiscussione.setPadding(5);
		formDatiDiscussione.setWrapItemTitles(false);
		formDatiDiscussione.setNumCols(10);
		formDatiDiscussione.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		formDatiDiscussione.setValuesManager(vm);
		
		idSedutaItem = new HiddenItem("idSeduta");
		
		numeroItem = new TextItem("numero", setTitleAlign("N°", 100, false));
		numeroItem.setStartRow(true);
		numeroItem.setColSpan(1);
		numeroItem.setCanEdit(false);
		numeroItem.setWidth(140);
		
		operazioniEffettuateButton = new ImgButtonItem("operazioniEffettuate", "protocollazione/operazioniEffettuate.png",
				I18NUtil.getMessages().protocollazione_detail_operazioniEffettuateButton_prompt());
		operazioniEffettuateButton.setAlwaysEnabled(true);
		operazioniEffettuateButton.setColSpan(1);
		operazioniEffettuateButton.setEndRow(false);
		operazioniEffettuateButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idSeduta = idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
				String title = "Operazioni sulla seduta di "+ organoCollegiale + " N° " + numeroItem.getValueAsString();
				if(dtPrimaConvocazioneItem.getValueAsDate() != null) {
					title += " del " + DateUtil.format(dtPrimaConvocazioneItem.getValueAsDate()).substring(0, 10);
				}
				new OperazioniEffettuateWindow(idSeduta, title);
			}
		});
		operazioniEffettuateButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String idSeduta = idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
				if(idSeduta!=null && !"".equals(idSeduta)) {
					return true;
				}
				return false;
			}
		});
		
		visualizzaContenutiFascicoloButton = new ImgButtonItem("visualizzaContenutiFascicoloButton", "menu/archivio.png"
				, "Mostra contenuti fascicolo");
		visualizzaContenutiFascicoloButton.setEndRow(false);
		visualizzaContenutiFascicoloButton.setColSpan(1);
		visualizzaContenutiFascicoloButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {						
				// Setto la radice come nodo di partenza predefinito
				Record lRecord = new Record();
				lRecord.setAttribute("idUdFolder", getIdFascicolo());
				lRecord.setAttribute("idNode", "/");
				lRecord.setAttribute("provenienza", "ORGANI_COLLEGIALI");
				ContenutiFascicoloPopup contenutiFascicoliPopup = new ContenutiFascicoloPopup(lRecord);
				contenutiFascicoliPopup.show();
			}
		});	
		visualizzaContenutiFascicoloButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
		
				if(getIdFascicolo() != null)
					return true;
				return false;
			}
		});

		dtPrimaConvocazioneItem = new DateTimeItem("dtPrimaConvocazione", "1a convocazione il");
		dtPrimaConvocazioneItem.setCanEdit(false);
		dtPrimaConvocazioneItem.setStartRow(true);
		dtPrimaConvocazioneItem.setColSpan(2);
		
		luogoPrimaConvocazioneItem = new TextItem("luogoPrimaConvocazione", "Luogo");
		luogoPrimaConvocazioneItem.setWidth(250);
		luogoPrimaConvocazioneItem.setCanEdit(false);
		luogoPrimaConvocazioneItem.setStartRow(false);
		luogoPrimaConvocazioneItem.setEndRow(true);
		luogoPrimaConvocazioneItem.setColSpan(1);
		
		dtSecondaConvocazioneItem = new DateTimeItem("dtSecondaConvocazione", "2a convocazione il");
		dtSecondaConvocazioneItem.setStartRow(true);
		dtSecondaConvocazioneItem.setColSpan(2);
		dtSecondaConvocazioneItem.setCanEdit(false);
		dtSecondaConvocazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilSecondaConvocazione();
			}
		}); 
		
		luogoSecondaConvocazioneItem = new TextItem("luogoSecondaConvocazione", "Luogo");
		luogoSecondaConvocazioneItem.setWidth(250);
		luogoSecondaConvocazioneItem.setStartRow(false);
		luogoSecondaConvocazioneItem.setColSpan(1);
		luogoSecondaConvocazioneItem.setCanEdit(false);
		luogoSecondaConvocazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilSecondaConvocazione();
			}
		}); 
		
		desStatoSedutaItem = new TextItem("desStato", "Stato");
		desStatoSedutaItem.setColSpan(7);
		desStatoSedutaItem.setWidth(140);
		desStatoSedutaItem.setCanEdit(false);
		desStatoSedutaItem.setStartRow(false);
		
		tipoSessioneItem = new TextItem("tipoSessione", setTitleAlign("Tipo sessione", 100, false));
		tipoSessioneItem.setColSpan(7);
		tipoSessioneItem.setWidth(140);
		tipoSessioneItem.setCanEdit(false);
		tipoSessioneItem.setStartRow(false);

//		viewOdgConvocazioneItem = new ButtonItem("viewOdgConvocazione","Visualizza OdG convocazione");		
//		viewOdgConvocazioneItem.setColSpan(7);
//		viewOdgConvocazioneItem.setShowTitle(false);
//		viewOdgConvocazioneItem.setStartRow(true);
//		viewOdgConvocazioneItem.setTop(20);
//		viewOdgConvocazioneItem.setIcon("file/preview.png");
//		viewOdgConvocazioneItem.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				
//				clickPreviewFile();
//			}
//		});
//		viewOdgConvocazioneItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//
//				boolean isVisible = false;
//				Record detailRecord = getRecordToSave();
//				Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
//				if(odgInfo != null && odgInfo.getAttributeAsString("uri") != null && !"".equals(odgInfo.getAttributeAsString("uri"))) {
//					isVisible = true;
//				}
//				return isVisible;
//			}
//		});

		formDatiDiscussione.setItems(
				idSedutaItem,
				numeroItem,
				operazioniEffettuateButton,
				visualizzaContenutiFascicoloButton,
				dtPrimaConvocazioneItem,luogoPrimaConvocazioneItem,
				dtSecondaConvocazioneItem,luogoSecondaConvocazioneItem,
				desStatoSedutaItem,
				tipoSessioneItem
				//viewOdgConvocazioneItem
		);
		
		sectionDatiDiscussioneSeduta = new DetailSection("Dati seduta", true, true, false, formDatiDiscussione);
		sectionDatiDiscussioneSeduta.setHeight(5);
		
		formListaDatiDiscussione = new DynamicForm();
		formListaDatiDiscussione.setTabID("HEADER");		
		formListaDatiDiscussione.setValuesManager(vm);
		formListaDatiDiscussione.setWidth("100%");
		formListaDatiDiscussione.setHeight("100%");
		formListaDatiDiscussione.setPadding(5);
		formListaDatiDiscussione.setWrapItemTitles(false);
		formListaDatiDiscussione.setNumCols(10);
		formListaDatiDiscussione.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		formListaDatiDiscussione.setValuesManager(vm);
		
		esitiXtipoArgomentoItem = new HiddenItem("esitiXtipoArgomento"); 
		
		listaDatiDiscussioneSedutaItem = new ListaDatiDiscussioneSedutaItem("listaArgomentiOdg", organoCollegiale, 
				codCircoscrizione, statoSeduta) {
			
			public RecordList getEsitiXtipoArgomento() {
				return esitiXtipoArgomentoItem.getValueAsRecordList();
			}
			
			@Override
			public String getIdSeduta() {
				return idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
			}
			
			@Override
			public boolean fromStoricoSeduta() {
				return true;
			}
			
			@Override
			protected boolean isButtonEnabled() {
				return false;
			}
			
		};
		listaDatiDiscussioneSedutaItem.setStartRow(true);
		listaDatiDiscussioneSedutaItem.setShowTitle(false);
		listaDatiDiscussioneSedutaItem.setHeight("95%");
		listaDatiDiscussioneSedutaItem.setCanEdit(false);
		
		formListaDatiDiscussione.setItems(esitiXtipoArgomentoItem, listaDatiDiscussioneSedutaItem);
		
		sectionListaDatiDiscussioneSeduta = new DetailSection("Proposte di delibere e altri argomenti in discussione", false, true, false, formListaDatiDiscussione);
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(sectionDatiDiscussioneSeduta);
		lVLayout.addMember(sectionListaDatiDiscussioneSeduta);
				
		return lVLayout;
	}
	
	public void massiveOperationCallback(DSResponse response, RecordList lista, String pkField, String successMessage,
			String completeErrorMessage, String partialErrorMessage, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			RecordList listaErrori = new RecordList();
			if (errorMessages != null && errorMessages.size() > 0) {
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						Record recordErrore = new Record();
						recordErrore.setAttribute("idError", getEstremiAttoFromRecord(record));						
						errorMsg += "<br/>" + getEstremiAttoFromRecord(record) + ": " + errorMessages.get(record.getAttribute(pkField));		
						recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
						listaErrori.add(recordErrore);
					}
				}
			}						
			Layout.hideWaitPopup();
			if(listaErrori != null && listaErrori.getLength() > 0) {
				ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Estremi", listaErrori, lista.getLength(), 600, 300);
				errorePopup.show();
			} else if (errorMsg != null && !"".equals(errorMsg)) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else {
				if (successMessage != null && !"".equalsIgnoreCase(successMessage)) {
					Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				}
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
			Record recordToGet = getRecordToSave();
			recordToGet.setAttribute("organoCollegiale", organoCollegiale);
			GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
			lGWTRestDataSource.call(recordToGet, new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record object) {
					caricaDettaglio(object);							
				}
			});
		} else {
			Layout.hideWaitPopup();
		}
	}
	
	private String getEstremiAttoFromRecord(Record record){
		return record.getAttribute("tipo") + " " + record.getAttribute("codTipo") + " " + record.getAttribute("estremiPropostaUD");
	}
	
	public VLayout getLayoutTabDatiPrimaConvocazione() {
		
		formDatiPrimaConv = new DynamicForm();
		formDatiPrimaConv.setTabID("PRIMA_CONV");		
		formDatiPrimaConv.setValuesManager(vm);
		formDatiPrimaConv.setWidth("100%");
		formDatiPrimaConv.setHeight("5");
		formDatiPrimaConv.setPadding(5);
		formDatiPrimaConv.setWrapItemTitles(false);
		formDatiPrimaConv.setNumCols(12);
		formDatiPrimaConv.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		dtInizioLavoriPrimaConvItem = new DateTimeItem("datiDiscPrimaConvDtInizioLavori", "Inizio lavori alle");
		dtInizioLavoriPrimaConvItem.setStartRow(false);
		dtInizioLavoriPrimaConvItem.setColSpan(1);
		dtInizioLavoriPrimaConvItem.setWidth(150);
		dtInizioLavoriPrimaConvItem.setCanEdit(isAbilPrimaConvocazione());
		
		dtFineLavoriPrimaConvItem = new DateTimeItem("datiDiscPrimaConvDtFineLavori", "Fine lavori alle");
		dtFineLavoriPrimaConvItem.setStartRow(false);
		dtFineLavoriPrimaConvItem.setColSpan(1);
		dtFineLavoriPrimaConvItem.setWidth(150);
		dtFineLavoriPrimaConvItem.setCanEdit(isAbilPrimaConvocazione());
		
		fileVerbalePrimaConvItem = new HiddenItem("fileVerbalePrimaConvocazione");
		
		formDatiPrimaConv.setItems(dtInizioLavoriPrimaConvItem, dtFineLavoriPrimaConvItem, fileVerbalePrimaConvItem);
		
		sectionDatiPrimaConv = new DetailSection("Dati convocazione", true, true, false, formDatiPrimaConv);
		
		formListaDatiPrimaConv = new DynamicForm();
		formListaDatiPrimaConv.setTabID("PRIMA_CONV");		
		formListaDatiPrimaConv.setValuesManager(vm);
		formListaDatiPrimaConv.setWidth("100%");
		formListaDatiPrimaConv.setHeight("5");
		formListaDatiPrimaConv.setPadding(5);
		formListaDatiPrimaConv.setWrapItemTitles(false);
		formListaDatiPrimaConv.setNumCols(12);
		formListaDatiPrimaConv.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		sectionListaDatiPrimaConv = new DetailSection("Presenze all'appello iniziale", false, true, false, formListaDatiPrimaConv);
		
		listaDatiDiscussionePrimaConvItem = new ListaDatiDiscussionePrimaConvocazioneItem("listaDatiDiscPrimaConvPresenzeAppelloIniziale", organoCollegiale, isAbilPrimaConvocazione());
		listaDatiDiscussionePrimaConvItem.setStartRow(true);
		listaDatiDiscussionePrimaConvItem.setShowTitle(false);
		listaDatiDiscussionePrimaConvItem.setHeight(245);
		listaDatiDiscussionePrimaConvItem.setCanEdit(false);
		
		formListaDatiPrimaConv.setItems(listaDatiDiscussionePrimaConvItem);

		buildVerbalePrimaConvSection();
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		lVLayout.addMember(sectionDatiPrimaConv);
		lVLayout.addMember(sectionListaDatiPrimaConv);
		lVLayout.addMember(verbalePrimaConvSection);
				
		return lVLayout;
	}
	
	public VLayout getLayoutTabDatiSecondaConvocazione() {
		
		formDatiSecondaConv = new DynamicForm();
		formDatiSecondaConv.setTabID("SECONDA_CONV");		
		formDatiSecondaConv.setValuesManager(vm);
		formDatiSecondaConv.setWidth("100%");
		formDatiSecondaConv.setHeight("5");
		formDatiSecondaConv.setPadding(5);
		formDatiSecondaConv.setWrapItemTitles(false);
		formDatiSecondaConv.setNumCols(12);
		formDatiSecondaConv.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		dtInizioLavoriSecondaConvItem = new DateTimeItem("datiDiscSecondaConvDtInizioLavori", "Inizio lavori alle");
		dtInizioLavoriSecondaConvItem.setStartRow(false);
		dtInizioLavoriSecondaConvItem.setColSpan(1);
		dtInizioLavoriSecondaConvItem.setWidth(150);
		dtInizioLavoriSecondaConvItem.setCanEdit(isAbilSecondaConvocazione());
		
		dtFineLavoriSecondaConvItem = new DateTimeItem("datiDiscSecondaConvDtFineLavori", "Fine lavori alle");
		dtFineLavoriSecondaConvItem.setStartRow(false);
		dtFineLavoriSecondaConvItem.setColSpan(1);
		dtFineLavoriSecondaConvItem.setWidth(150);
		dtFineLavoriSecondaConvItem.setCanEdit(isAbilSecondaConvocazione());
		
		fileVerbaleSecondaConvItem = new HiddenItem("fileVerbaleSecondaConvocazione");
		
		formDatiSecondaConv.setItems(dtInizioLavoriSecondaConvItem, dtFineLavoriSecondaConvItem, fileVerbaleSecondaConvItem);
		
		sectionDatiSecondaConv = new DetailSection("Dati convocazione", true, true, false, formDatiSecondaConv);
		
		formListaDatiSecondaConv = new DynamicForm();
		formListaDatiSecondaConv.setTabID("SECONDA_CONV");		
		formListaDatiSecondaConv.setValuesManager(vm);
		formListaDatiSecondaConv.setWidth("100%");
		formListaDatiSecondaConv.setHeight("5");
		formListaDatiSecondaConv.setPadding(5);
		formListaDatiSecondaConv.setWrapItemTitles(false);
		formListaDatiSecondaConv.setNumCols(12);
		formListaDatiSecondaConv.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		listaDatiDiscussioneSecondaConvItem = new ListaDatiDiscussioneSecondaConvocazioneItem("listaDatiDiscSecondaConvPresenzeAppelloIniziale", organoCollegiale, isAbilSecondaConvocazione());
		listaDatiDiscussioneSecondaConvItem.setStartRow(true);
		listaDatiDiscussioneSecondaConvItem.setShowTitle(false);
		listaDatiDiscussioneSecondaConvItem.setHeight(245);
		listaDatiDiscussioneSecondaConvItem.setCanEdit(false);
		
		formListaDatiSecondaConv.setItems(listaDatiDiscussioneSecondaConvItem);
		
		sectionListaDatiSecondaConv = new DetailSection("Presenze all'appello iniziale", false, true, false, formListaDatiSecondaConv);

		buildVerbaleSecondaConvSection();
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		lVLayout.addMember(sectionDatiSecondaConv);
		lVLayout.addMember(sectionListaDatiSecondaConv);
		lVLayout.addMember(verbaleSecondaConvSection);
				
		return lVLayout;
	}
	
	private void buildVerbalePrimaConvSection() {
		
		formVerbalePrimaConv = new DynamicForm();
		formVerbalePrimaConv.setTabID("PRIMA_CONV");		
		formVerbalePrimaConv.setValuesManager(vm);
		formVerbalePrimaConv.setWidth("100%");
		formVerbalePrimaConv.setHeight("5");
		formVerbalePrimaConv.setPadding(5);
		formVerbalePrimaConv.setNumCols(1);
		formVerbalePrimaConv.setWrapItemTitles(false);
		
		SpacerItem spacer1 = new SpacerItem();
		spacer1.setColSpan(1);
		spacer1.setWidth(50);
		spacer1.setStartRow(true);
		
		editorVerbalePrimaConvItem = new CKEditorItem("datiDiscPrimaConvEditorVerbale", 4000, "restricted");
		editorVerbalePrimaConvItem.setShowTitle(false);
		editorVerbalePrimaConvItem.setColSpan(20);
		editorVerbalePrimaConvItem.setWidth("100%");
		editorVerbalePrimaConvItem.setCanEdit(isAbilPrimaConvocazione());
		
		visualizzaVerbalePrimaConvItem = new ButtonItem("visualizzaVerbalePrimaConv", "Visualizza verbale");		
		visualizzaVerbalePrimaConvItem.setColSpan(7);
		visualizzaVerbalePrimaConvItem.setStartRow(true);
		visualizzaVerbalePrimaConvItem.setTop(20);
		visualizzaVerbalePrimaConvItem.setCanEdit(isAbilPrimaConvocazione());
		visualizzaVerbalePrimaConvItem.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// Genero il verbale o mostro quello presente
				Record detailRecord = getRecordToSave();
				if (detailRecord.getAttribute("fileVerbalePrimaConvocazione") != null) {
					Record verbalePrimaConvRecord = detailRecord.getAttributeAsRecord("fileVerbalePrimaConvocazione");
					if (verbalePrimaConvRecord != null && verbalePrimaConvRecord.getAttribute("uri") != null && !"".equalsIgnoreCase(verbalePrimaConvRecord.getAttribute("uri"))) {
						String display = verbalePrimaConvRecord.getAttributeAsString("displayFilename");
						String uri = verbalePrimaConvRecord.getAttribute("uri");
						boolean remoteUri = false;
						preview(display, uri, remoteUri, new InfoFileRecord(verbalePrimaConvRecord.getAttributeAsRecord("infoFileVerbale")));
					} else {
						generaVisualizzaAnteprimaOdg(detailRecord);
					}
				} else {
					generaVisualizzaAnteprimaOdg(detailRecord);
				}
			}
		});
		
		formVerbalePrimaConv.setItems(spacer1,editorVerbalePrimaConvItem,spacer1,visualizzaVerbalePrimaConvItem);
		verbalePrimaConvSection = new DetailSection("Verbale", true, true, false, formVerbalePrimaConv);
	}
	
	private void buildVerbaleSecondaConvSection() {
		
		formVerbaleSecondaConv = new DynamicForm();
		formVerbaleSecondaConv.setTabID("SECONDA_CONV");		
		formVerbaleSecondaConv.setValuesManager(vm);
		formVerbaleSecondaConv.setWidth("100%");
		formVerbaleSecondaConv.setHeight("5");
		formVerbaleSecondaConv.setPadding(5);
		formVerbaleSecondaConv.setNumCols(1);
		formVerbaleSecondaConv.setWrapItemTitles(false);
		
		SpacerItem spacer1 = new SpacerItem();
		spacer1.setColSpan(1);
		spacer1.setWidth(50);
		spacer1.setStartRow(true);
		
		editorVerbaleSecondaConvItem = new CKEditorItem("datiDiscSecondaConvEditorVerbale", 4000, "restricted");
		editorVerbaleSecondaConvItem.setShowTitle(false);
		editorVerbaleSecondaConvItem.setColSpan(20);
		editorVerbaleSecondaConvItem.setWidth("100%");
		editorVerbaleSecondaConvItem.setCanEdit(isAbilSecondaConvocazione());
		
		visualizzaVerbaleSecondaConvItem = new ButtonItem("visualizzaVerbaleSecondaConv", "Visualizza verbale");		
		visualizzaVerbaleSecondaConvItem.setColSpan(7);
		visualizzaVerbaleSecondaConvItem.setStartRow(true);
		visualizzaVerbaleSecondaConvItem.setTop(20);
		visualizzaVerbaleSecondaConvItem.setCanEdit(isAbilSecondaConvocazione());
		visualizzaVerbaleSecondaConvItem.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				
			}
		});
		
		formVerbaleSecondaConv.setItems(spacer1,editorVerbaleSecondaConvItem,spacer1,visualizzaVerbaleSecondaConvItem);
		verbaleSecondaConvSection = new DetailSection("Verbale", true, true, false, formVerbaleSecondaConv);
	}
	
	private Boolean isAbilPrimaConvocazione() {
		Boolean isCanEdit = false;
		if(recordCommissione != null && recordCommissione.getAttribute("stato") != null
				&& prima_convocazione.equalsIgnoreCase(recordCommissione.getAttribute("stato"))) {
			isCanEdit = true;
		}
		return isCanEdit;
	}
	
	private boolean isAbilSecondaConvocazione() {
		Boolean isCanEdit = false;
		if(recordCommissione != null && recordCommissione.getAttribute("stato") != null) {
			if(prima_convocazione_deserta.equalsIgnoreCase(recordCommissione.getAttribute("stato")) ||
			   seconda_convocazione.equalsIgnoreCase(recordCommissione.getAttribute("stato")) ) {
					isCanEdit = true;	
			}
		}
		return isCanEdit;
	}
	
	public void preview(final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, null, false, false);
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	private void generaVisualizzaAnteprimaOdg(Record detailRecord){
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		lGWTRestDataSource.executecustom("generaAnteprimaOdg", detailRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordFileGenerato = dsResponse.getData()[0];
					String uri = recordFileGenerato.getAttributeAsString("uri");
					InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(recordFileGenerato.getAttributeAsRecord("infoFile").getJsObj()));
					String nomeFile = recordFileGenerato.getAttributeAsString("nomeFile");
					preview(nomeFile, uri, false, infoFile);
				}
			}
		});
	}
	
	private String getIdFascicolo() {
		final Record detailRecord = getRecordToSave();
		final Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
		if(odgInfo != null && odgInfo.getAttributeAsString("idFascicolo") != null &&
				!"".equalsIgnoreCase(odgInfo.getAttributeAsString("idFascicolo"))) {
			return odgInfo.getAttributeAsString("idFascicolo");
		}
		return null;
	}
	
	public Record getRecordToSave() {
		
		Record lRecordToSave = new Record(getValuesManager().getValues());
		
		lRecordToSave.setAttribute("organoCollegiale", organoCollegiale);
		
		// 1 TAB
		addFormValues(lRecordToSave, formDatiDiscussione);	
		addFormValues(lRecordToSave, formListaDatiDiscussione);	
		
		// 2 TAB
		addFormValues(lRecordToSave, formDatiPrimaConv); 
		addFormValues(lRecordToSave, formListaDatiPrimaConv); 
		addFormValues(lRecordToSave, formVerbalePrimaConv);
		
		// 3 TAB
		addFormValues(lRecordToSave, formDatiSecondaConv);
		addFormValues(lRecordToSave, formListaDatiSecondaConv);
		addFormValues(lRecordToSave, formVerbaleSecondaConv);
		
		return lRecordToSave;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		
		super.setCanEdit(canEdit);
		
		setCanEdit(false, formDatiDiscussione);
		setCanEdit(false, formListaDatiDiscussione);
		setCanEdit(false, formDatiPrimaConv);
		setCanEdit(false, formListaDatiPrimaConv); 
		setCanEdit(false, formVerbalePrimaConv);
		setCanEdit(false, formDatiSecondaConv);
		setCanEdit(false, formListaDatiSecondaConv);
		setCanEdit(false, formVerbaleSecondaConv);
	}
		
}