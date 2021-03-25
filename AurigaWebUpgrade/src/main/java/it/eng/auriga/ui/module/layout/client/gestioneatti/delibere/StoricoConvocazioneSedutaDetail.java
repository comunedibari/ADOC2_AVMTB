package it.eng.auriga.ui.module.layout.client.gestioneatti.delibere;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.archivio.ContenutiFascicoloPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.OperazioniEffettuateWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class StoricoConvocazioneSedutaDetail extends CustomDetail {
		
	private TabSet tabSet;
	
	private HiddenItem odgInfoItem;
	private HiddenItem idSedutaItem;
	private HiddenItem convocazioneInfoItem;
	
	private Tab tabDatiSeduta;
	private VLayout layoutTabDatiSeduta;
	private DetailSection sectionDatiSeduta;
	private DynamicForm formDatiSeduta;
	private DynamicForm formListaDatiSeduta;
	private TextItem numeroItem;
	private ImgButtonItem operazioniEffettuateButton;
	private ImgButtonItem visualizzaContenutiFascicoloButton;
	private DateTimeItem dtPrimaConvocazioneItem;
	private TextItem luogoPrimaConvocazioneItem;
	private DateTimeItem dtSecondaConvocazioneItem;
	private TextItem luogoSecondaConvocazioneItem;
	private TextItem desStatoSedutaItem;
	private SelectItem tipoSessioneItem;
	//private ButtonItem viewOdgInviatoItem; TODO
	
	private DetailSection sectionListaDatiSeduta;
	private HiddenItem listaArgomentiOdgEliminatiItem;
	private ListaDatiConvocazioneSedutaItem listaDatiConvocazioneSedutaItem;
	
	private Tab tabDatiPartecipanti;
	private VLayout layoutTabDatiPartecipanti;
	private DynamicForm formDatiPartecipanti;
	private ListaDatiPartecipantiSedutaItem listaDatiPartecipantiSedutaItem;

	private Record recordConvocazione;
	private String statoSeduta;
	private String organoCollegiale;
	private String codCircoscrizione;
	
	public StoricoConvocazioneSedutaDetail(String nomeEntita, Record recordConvocazione, String organoCollegiale) {
		
		super(nomeEntita);
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		this.recordConvocazione = recordConvocazione;
		
		this.organoCollegiale = organoCollegiale;
		
		this.codCircoscrizione = recordConvocazione != null && recordConvocazione.getAttributeAsRecord("odgInfo") != null &&
				recordConvocazione.getAttributeAsRecord("odgInfo").getAttributeAsString("codCircoscrizione") != null ?
				recordConvocazione.getAttributeAsRecord("odgInfo").getAttributeAsString("codCircoscrizione") : null;
				
		this.statoSeduta = recordConvocazione != null && recordConvocazione.getAttributeAsString("stato") != null ?
				recordConvocazione.getAttributeAsString("stato") : null;
			
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
		
		/**
		 *  Tab Seduta
		 */

		tabDatiSeduta = new Tab("<b>" + getTitleTabDatiSeduta() + "</b>");
		tabDatiSeduta.setAttribute("tabID", "HEADER");
		tabDatiSeduta.setPrompt(getTitleTabDatiSeduta());

		VLayout lVLayoutSpacerSeduta = new VLayout();
		lVLayoutSpacerSeduta.setWidth100();
		lVLayoutSpacerSeduta.setHeight(10);

		layoutTabDatiSeduta = createLayoutTab(getLayoutTabDatiSeduta(), lVLayoutSpacerSeduta);

		// Aggiungo i layout ai tab
		tabDatiSeduta.setPane(layoutTabDatiSeduta);

		tabSet.addTab(tabDatiSeduta);
		
		/**
		 *  Tab Partecipanti
		 */
		
		tabDatiPartecipanti = new Tab("<b>" + getTitleTabDatiPartecipanti() + "</b>");
		tabDatiPartecipanti.setAttribute("tabID", "PARTECIPANTI");
		tabDatiPartecipanti.setPrompt(getTitleTabDatiPartecipanti());

		layoutTabDatiPartecipanti = createLayoutTab(getLayoutTabDatiPartecipanti(), null);

		// Aggiungo i layout ai tab
		tabDatiPartecipanti.setPane(layoutTabDatiPartecipanti);
		
		tabSet.addTab(tabDatiPartecipanti);
	}
	
	public void caricaDettaglio(Record record) {
		recordConvocazione = record;
		editRecord(record);
		
		if(numeroItem != null) {
			numeroItem.setCanEdit(false);
		}
		
		if(listaDatiConvocazioneSedutaItem != null) {
			listaDatiConvocazioneSedutaItem.setCanEdit(false);
		}
		
		markForRedraw();
	}
	
	protected VLayout createLayoutTab(VLayout layout, VLayout spacerLayout) {
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		if(spacerLayout != null) {
			layoutTab.addMember(spacerLayout);
		}
		layoutTab.setRedrawOnResize(true);
		
		return layoutTab;
	}
	
	private String getTitleTabDatiSeduta() {
		return "Seduta";
	}
	
	private String getTitleTabDatiPartecipanti() {
		return "Partecipanti";
	}
	
	public VLayout getLayoutTabDatiSeduta() {
		
		formDatiSeduta = new DynamicForm();
		formDatiSeduta.setTabID("HEADER");		
		formDatiSeduta.setValuesManager(vm);
		formDatiSeduta.setWidth("100%");
		formDatiSeduta.setHeight("5");
		formDatiSeduta.setPadding(5);
		formDatiSeduta.setWrapItemTitles(false);
		formDatiSeduta.setNumCols(10);
		formDatiSeduta.setColWidths(10, 10, 10, 10, 10, 10, 10, 10, 10, "*");
		formDatiSeduta.setValuesManager(vm);
		
		odgInfoItem = new HiddenItem("odgInfo");
		idSedutaItem = new HiddenItem("idSeduta");
		convocazioneInfoItem = new HiddenItem("convocazioneInfo");
		
		numeroItem = new TextItem("numero", setTitleAlign("N°", 100, false));
		numeroItem.setStartRow(true);
		numeroItem.setColSpan(1);
		numeroItem.setCanEdit(false);
		//numeroItem.setDisabled(true);
		numeroItem.setWidth(117);
		
		operazioniEffettuateButton = new ImgButtonItem("operazioniEffettuate", "protocollazione/operazioniEffettuate.png",
				I18NUtil.getMessages().protocollazione_detail_operazioniEffettuateButton_prompt());
		operazioniEffettuateButton.setAlwaysEnabled(true);
		operazioniEffettuateButton.setColSpan(1);
		operazioniEffettuateButton.setEndRow(false);
		operazioniEffettuateButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idSeduta = idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
				String data = DateUtil.format(dtPrimaConvocazioneItem.getValueAsDate()).substring(0, 10);
				String title = "Operazioni sulla seduta di "+ organoCollegiale + " N° " + numeroItem.getValueAsString() + " del " + data;
			
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
		dtPrimaConvocazioneItem.setStartRow(true);
		dtPrimaConvocazioneItem.setColSpan(2);
		dtPrimaConvocazioneItem.setRequired(true);
		
		luogoPrimaConvocazioneItem = new TextItem("luogoPrimaConvocazione", "Luogo");
		luogoPrimaConvocazioneItem.setStartRow(false);
		luogoPrimaConvocazioneItem.setEndRow(true);
		luogoPrimaConvocazioneItem.setColSpan(1);
		
		dtSecondaConvocazioneItem = new DateTimeItem("dtSecondaConvocazione", "2a convocazione il");
		dtSecondaConvocazioneItem.setStartRow(true);
		dtSecondaConvocazioneItem.setColSpan(2);
		
		luogoSecondaConvocazioneItem = new TextItem("luogoSecondaConvocazione", "Luogo");
		luogoSecondaConvocazioneItem.setStartRow(false);
		luogoSecondaConvocazioneItem.setColSpan(1);
		
		desStatoSedutaItem = new TextItem("desStato", setTitleAlign("Stato", 100, false));
		desStatoSedutaItem.setStartRow(true);
		desStatoSedutaItem.setColSpan(7);
		desStatoSedutaItem.setCanEdit(false);
		desStatoSedutaItem.setWidth(117);
		
		GWTRestDataSource tipoSessioneDS = new GWTRestDataSource("LoadComboTipoSessioneOdgDataSource");
		tipoSessioneDS.addParam("tipo_sessione", organoCollegiale);
		
		tipoSessioneItem = new SelectItem("tipoSessione", setTitleAlign("Tipo sessione", 100, false));
		tipoSessioneItem.setValueField("key");
		tipoSessioneItem.setDisplayField("value");
		tipoSessioneItem.setOptionDataSource(tipoSessioneDS);
		tipoSessioneItem.setAutoFetchData(false);
		tipoSessioneItem.setAlwaysFetchMissingValues(true);
		tipoSessioneItem.setFetchMissingValues(true);
		tipoSessioneItem.setStartRow(true);
		tipoSessioneItem.setColSpan(7);
		tipoSessioneItem.setWidth(117);
		
		SpacerItem spacer1 = new SpacerItem();
		spacer1.setColSpan(1);
		spacer1.setWidth(10);
		
		formDatiSeduta.setItems(
				odgInfoItem,
				idSedutaItem,
				convocazioneInfoItem,
				numeroItem,
				operazioniEffettuateButton,
				visualizzaContenutiFascicoloButton,
				dtPrimaConvocazioneItem,spacer1,luogoPrimaConvocazioneItem,
				dtSecondaConvocazioneItem,spacer1,luogoSecondaConvocazioneItem,
				desStatoSedutaItem,
				tipoSessioneItem
		);
		
		sectionDatiSeduta = new DetailSection("Dati seduta", true, true, true, formDatiSeduta);
		sectionDatiSeduta.setHeight(5);
		
		formListaDatiSeduta = new DynamicForm();
		formListaDatiSeduta.setTabID("HEADER");		
		formListaDatiSeduta.setValuesManager(vm);
		formListaDatiSeduta.setWidth("100%");
		formListaDatiSeduta.setHeight("100%");
		formListaDatiSeduta.setPadding(5);
		formListaDatiSeduta.setWrapItemTitles(false);
		formListaDatiSeduta.setNumCols(10);
		formListaDatiSeduta.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		
		listaArgomentiOdgEliminatiItem = new HiddenItem("listaArgomentiOdgEliminati");
		
		listaDatiConvocazioneSedutaItem = new ListaDatiConvocazioneSedutaItem("listaArgomentiOdg", organoCollegiale, 
				codCircoscrizione, statoSeduta) {
			
			@Override
			public void onClickRemoveButton(ListGridRecord record) {
				
				RecordList recordList = formListaDatiSeduta.getValueAsRecordList("listaArgomentiOdgEliminati");
				if( recordList == null ) {
					recordList = new RecordList();
				}
				recordList.add(record);
				listaArgomentiOdgEliminatiItem.setValue(recordList);
				super.onClickRemoveButton(record);
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
			protected Boolean isOdGChiuso() {
				return true;
			}
		};
		listaDatiConvocazioneSedutaItem.setStartRow(true);
		listaDatiConvocazioneSedutaItem.setShowTitle(false);
		listaDatiConvocazioneSedutaItem.setHeight("95%");
		listaDatiConvocazioneSedutaItem.setCanEdit(false);
		
		formListaDatiSeduta.setItems(listaArgomentiOdgEliminatiItem, listaDatiConvocazioneSedutaItem);
		
		sectionListaDatiSeduta = new DetailSection("OdG - proposte di delibere e altri argomenti da discutere", false, true, false, formListaDatiSeduta);
				
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(sectionDatiSeduta);
		lVLayout.addMember(sectionListaDatiSeduta);
				
		return lVLayout;
	}
	
	public VLayout getLayoutTabDatiPartecipanti() {
		
		formDatiPartecipanti = new DynamicForm();
		formDatiPartecipanti.setTabID("PARTECIPANTI");		
		formDatiPartecipanti.setValuesManager(vm);
		formDatiPartecipanti.setWidth("100%");
		formDatiPartecipanti.setHeight("100%");
		formDatiPartecipanti.setPadding(5);
		formDatiPartecipanti.setWrapItemTitles(false);
		formDatiPartecipanti.setNumCols(12);
		formDatiPartecipanti.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		formDatiPartecipanti.setValuesManager(vm);

		listaDatiPartecipantiSedutaItem = new ListaDatiPartecipantiSedutaItem("listaPresenzeOdg",organoCollegiale, statoSeduta) {
			
			@Override
			public String getIdSeduta() {
				return idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
			}
		};
		listaDatiPartecipantiSedutaItem.setStartRow(true);
		listaDatiPartecipantiSedutaItem.setShowTitle(false);
		listaDatiPartecipantiSedutaItem.setHeight("95%");		
		listaDatiPartecipantiSedutaItem.setCanEdit(false);
		
		formDatiPartecipanti.setItems(listaDatiPartecipantiSedutaItem);
				
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(formDatiPartecipanti);
				
		return lVLayout;
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
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
		
		addFormValues(lRecordToSave, formDatiSeduta);
		
		addFormValues(lRecordToSave, formListaDatiSeduta);
		
		addFormValues(lRecordToSave, formDatiPartecipanti);
		
		return lRecordToSave;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		
		super.setCanEdit(false);
		
		setCanEdit(false, formDatiSeduta); 
		setCanEdit(false, formListaDatiSeduta); 
		setCanEdit(false, formDatiPartecipanti);
		
	}
}