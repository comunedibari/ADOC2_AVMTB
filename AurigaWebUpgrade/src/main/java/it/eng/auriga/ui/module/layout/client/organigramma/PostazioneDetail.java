package it.eng.auriga.ui.module.layout.client.organigramma;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.gestioneUtenti.UOCollegatePuntoProtocolloPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class PostazioneDetail extends CustomDetail {

	// Sostituzione utente
	protected DetailSection detailSectionUtenteDaSostituireCon;
	private DynamicForm formUtenteDaSostituireCon;
	private FilteredSelectItemWithDisplay utentiNewItem;
	private DateItem dataDalNewItem;
	private DateItem dataAlNewItem;
	private SelectItem tipoDiAssegnazioneNewSelectItem;
	private SelectItem selezionaRuoloNewItem;
	private CheckboxItem estendiDelegaSottoUoNewCheckboxItem;
	private CheckboxItem estendiDelegaAllePostazioniUtenteNewCheckboxItem;

	// Utente corrente
	protected DetailSection detailSectionUtenteCorrente;
	private DynamicForm formUtenteCorrente;
	private HiddenItem ciRelUserUoItem;
	private HiddenItem idScrivaniaItem;
	private HiddenItem idUoItem;
	private HiddenItem sostituzioneSVItem;
	private HiddenItem intestazioneItem;
	private HiddenItem flgSpostamentoItem;
	private HiddenItem flgDuplicazioneItem;
	private FilteredSelectItemWithDisplay utentiItem;
	private DateItem dataDalItem;
	
	private HiddenItem dataAlOrigItem;
	private DateItem dataAlItem;
	private SelectItem tipoDiAssegnazioneSelectItem;
	private SelectItem selezionaRuoloItem;
	private CheckboxItem estendiDelegaSottoUoCheckboxItem;
	private CheckboxItem estendiDelegaAllePostazioniUtenteCheckboxItem;
	private TextItem nomePostazioneItem;
	private ImgButtonItem uoCollegatePuntoProtocolloButtonFormUtenteCorrente;
	private ImgButtonItem uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon;
	
	private SpacerItem spacer2Item;
	private SpacerItem spacer3Item;
	

	private HiddenItem listaUOPuntoProtocolloEscluseItem;
	private HiddenItem listaUOPuntoProtocolloIncluseItem;
	private HiddenItem listaUOPuntoProtocolloEreditarietaAbilitataItem;
	private HiddenItem flgUoPuntoProtocolloItem;
	
	private HiddenItem nriLivelliUoItem;
	private HiddenItem denominazioneUoItem;
	
	private String tipoAssegnatari;
	
	
	/** UO per spostare doc/fasc **/
	private DetailSection uoSpostaDocFascSection;
	private DynamicForm uoSpostaDocFascForm;
	
	private ExtendedTextItem codRapidoSpostaDocFascItem;
	private FilteredSelectItemWithDisplay organigrammaSpostaDocFascItem;
	private ImgButtonItem lookupOrganigrammaSpostaDocFascButton;
	
	private HiddenItem flgTipoDestDocItem;
	private HiddenItem flgPresentiDocFascItem;
	private HiddenItem typeNodoSpostaDocFascItem;
	private HiddenItem idUoSpostaDocFascItem;
	private HiddenItem descrizioneSpostaDocFascItem;
	

	
	/** Situazione documentazione in competenza alla UO **/
	private DetailSection resocontoSpostamentoDocFascSection;
	private DynamicForm resocontoSpostamentoDocFascForm;	
	private NumericItem nrDocAssegnatiItem;
	private DateTimeItem dataConteggioDocAssegnatiItem;
	private NumericItem nrFascAssegnatiItem;
	private DateTimeItem dataConteggioFascAssegnatiItem;
	private TextItem descUoSpostamentoDocFascItem;
	private TextItem statoSpostamentoDocFascItem;
	private DateTimeItem dataInizioSpostamentoDocFascItem;
	private DateTimeItem dataFineSpostamentoDocFascItem;
	
	public PostazioneDetail(String nomeEntita, boolean isSostituzione) {

		super(nomeEntita);

		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		setTipoAssegnatari("SV");
		
		populateItemsUtenteCorrente(isSostituzione);
		
		// Sezione doc/fasc assegnati
		creaSelectOrganigrammaSpostaDocFasc();
					
		// Sezione Resoconto documentazione e mail in competenza alla UO
		creaResocontoSpostamentoSection();

		
		if (isSostituzione) {

			populateItemsUtenteNuovo();
			
			lVLayout.addMember(detailSectionUtenteCorrente);
			lVLayout.addMember(detailSectionUtenteDaSostituireCon);

		} else {

			lVLayout.addMember(formUtenteCorrente);

		}

		lVLayout.addMember(uoSpostaDocFascSection);			
		lVLayout.addMember(resocontoSpostamentoDocFascSection);
		
		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}

	private void populateItemsUtenteCorrente(boolean isSostituzione) {

		formUtenteCorrente = new DynamicForm();
		formUtenteCorrente.setValuesManager(vm);
		formUtenteCorrente.setWrapItemTitles(false);
		formUtenteCorrente.setNumCols(20);
		formUtenteCorrente.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		ciRelUserUoItem    = new HiddenItem("ciRelUserUo");
		idScrivaniaItem    = new HiddenItem("idScrivania");
		idUoItem           = new HiddenItem("idUo");
		sostituzioneSVItem = new HiddenItem("sostituzioneSV");
		if (isSostituzione) {
			sostituzioneSVItem.setDefaultValue("1");
		}
		
		intestazioneItem = new HiddenItem("intestazione");
		flgSpostamentoItem = new HiddenItem("flgSpostamento");
		flgDuplicazioneItem = new HiddenItem("flgDuplicazione");
		flgUoPuntoProtocolloItem = new HiddenItem("flgUoPuntoProtocollo");
		listaUOPuntoProtocolloIncluseItem = new HiddenItem("listaUOPuntoProtocolloIncluse");
		listaUOPuntoProtocolloEscluseItem = new HiddenItem("listaUOPuntoProtocolloEscluse");
		listaUOPuntoProtocolloEreditarietaAbilitataItem = new HiddenItem("listaUOPuntoProtocolloEreditarietaAbilitata");
		nriLivelliUoItem = new HiddenItem("nriLivelliUo");
		denominazioneUoItem = new HiddenItem("denominazioneUo");
		
		
		
		creaSelectUtenti();
		
		ImgButtonItem variazioniButton = new ImgButtonItem("variazioniButton", "protocollazione/operazioniEffettuate.png", "Visualizza variazioni");
		variazioniButton.setAlwaysEnabled(true);
		variazioniButton.setColSpan(1);
		variazioniButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(getValuesManager().getValues());
				String idScrivania = record.getAttributeAsString("idScrivania") != null ? record.getAttributeAsString("idScrivania") : null;
				return idScrivania != null && !"".equals(idScrivania);
			}
		});
		variazioniButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				final Record record = new Record(getValuesManager().getValues());
				final String idScrivania = record.getAttributeAsString("idScrivania") != null ? record.getAttributeAsString("idScrivania") : null;							
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("VariazioniDatiUoSvDataSource");
				lGwtRestDataSource.addParam("idUoSv", idScrivania);			
				lGwtRestDataSource.addParam("flgUoSv", "SV");				
				lGwtRestDataSource.fetchData(null, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							if(response.getData() != null && response.getData().length > 0) {
								Record recordVariazione = response.getData()[0];
								String codiceUo = "";
								if(recordVariazione != null){
									codiceUo = recordVariazione.getAttributeAsString("codiceUo");
								}
								String estremi = record.getAttributeAsString("intestazione");
								String title = "Variazioni della postazione occupata da "  + estremi + " nella UO "+ codiceUo;
								new VariazioniDatiUoSvWindow(idScrivania, "SV", title);
							} else {
								AurigaLayout.addMessage(new MessageBean("Non c'è nessuna variazione storicizzata da visualizzare", "", MessageType.ERROR));							
							}
						}
					}
				});												
			}
		});

		creaSelectTipoDiAssegnazione();

		creaSelectRuolo();

		dataDalItem = new DateItem("dataDal", I18NUtil.getMessages().organigramma_postazione_detail_data_dal());
		dataDalItem.setColSpan(1);
		dataDalItem.setStartRow(true);
		dataDalItem.setRequired(true);

		dataAlOrigItem = new HiddenItem("dataAlOrig");

		dataAlItem = new DateItem("dataAl", I18NUtil.getMessages().organigramma_postazione_detail_data_al());
		dataAlItem.setColSpan(1);
		
		dataAlItem.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				updateSpostaDocFascSection(event.getItem().getValue());
			}
		});	
		
		dataAlItem.addBlurHandler(new BlurHandler() {			
			@Override
			public void onBlur(BlurEvent event) {	
				updateSpostaDocFascSection(event.getItem().getValue());
			}
		});
		

		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setStartRow(true);
		spacerItem.setColSpan(1);

		estendiDelegaSottoUoCheckboxItem = new CheckboxItem("flgInclSottoUo");
		estendiDelegaSottoUoCheckboxItem.setValue(false);
		estendiDelegaSottoUoCheckboxItem.setColSpan(1);
		estendiDelegaSottoUoCheckboxItem.setWidth(10);
		estendiDelegaSottoUoCheckboxItem.setStartRow(true);
		estendiDelegaSottoUoCheckboxItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean visible = false;
				// delega/funzionale
				if (formUtenteCorrente.getValueAsString("tipoRelUtenteUo") != null && formUtenteCorrente.getValueAsString("tipoRelUtenteUo").equals("D")) {
					estendiDelegaSottoUoCheckboxItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_flg_estendi_delega_sotto_uo());
					visible = true;
				}
				// Appartenenza gerarchica
				else if (formUtenteCorrente.getValueAsString("tipoRelUtenteUo") != null && formUtenteCorrente.getValueAsString("tipoRelUtenteUo").equals("A")) {
					estendiDelegaSottoUoCheckboxItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_con_delega_alle_sotto_uo());
					visible = true;
				}
				return visible;
			}
		});

		estendiDelegaAllePostazioniUtenteCheckboxItem = new CheckboxItem("flgInclScrivVirt");
		estendiDelegaAllePostazioniUtenteCheckboxItem.setValue(false);
		estendiDelegaAllePostazioniUtenteCheckboxItem.setColSpan(1);
		estendiDelegaAllePostazioniUtenteCheckboxItem.setWidth(10);
		estendiDelegaAllePostazioniUtenteCheckboxItem.setStartRow(false);
		estendiDelegaAllePostazioniUtenteCheckboxItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean visible = false;
				// delega/funzionale
				if (formUtenteCorrente.getValueAsString("tipoRelUtenteUo") != null && formUtenteCorrente.getValueAsString("tipoRelUtenteUo").equals("D")) {
					estendiDelegaAllePostazioniUtenteCheckboxItem.setTitle(I18NUtil.getMessages()
							.organigramma_postazione_detail_flg_estendi_delega_postazioni_utente());
					visible = true;
				}
				// Appartenenza gerarchica
				else if (formUtenteCorrente.getValueAsString("tipoRelUtenteUo") != null && formUtenteCorrente.getValueAsString("tipoRelUtenteUo").equals("A")) {
					estendiDelegaAllePostazioniUtenteCheckboxItem.setTitle(I18NUtil.getMessages()
							.organigramma_postazione_detail_flg_con_delega_postazioni_utente());
					visible = true;
				}
				return visible;
			}
		});

		nomePostazioneItem = new TextItem("nomePostazione", "Nome postazione") {

			@Override
			public void setCanEdit(Boolean canEdit) {

				super.setCanEdit(canEdit);
				setHint(canEdit ? "Da valorizzare se diverso da cognome e nome dell'utente" : null);
				setShowHintInField(true);
			}
		};
		nomePostazioneItem.setStartRow(true);
		nomePostazioneItem.setColSpan(15);
		nomePostazioneItem.setWidth(550);
		nomePostazioneItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!isTipoDiAssegnazioneL());
			}
		});

		
		
		spacer2Item = new SpacerItem();
		spacer2Item.setWidth(20);
		spacer2Item.setColSpan(1);
		spacer2Item.setStartRow(true);

		//Bottone per vedere Abilitazioni vs UO collegate al punto di protocollo 
		uoCollegatePuntoProtocolloButtonFormUtenteCorrente = new ImgButtonItem("uoCollegatePuntoProtocolloFormUtenteCorrente", "buttons/uoCollegatePuntoProtocollo.png", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_title());
		uoCollegatePuntoProtocolloButtonFormUtenteCorrente.setAlwaysEnabled(true);
		uoCollegatePuntoProtocolloButtonFormUtenteCorrente.setColSpan(1);
		uoCollegatePuntoProtocolloButtonFormUtenteCorrente.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUOPP = formUtenteCorrente.getValueAsString("idUo");
				String idUser = formUtenteCorrente.getValueAsString("idUser");
				String listaUOPuntoProtocolloEscluse = formUtenteCorrente.getValueAsString("listaUOPuntoProtocolloEscluse");
				String listaUOPuntoProtocolloEreditarietaAbilitata = formUtenteCorrente.getValueAsString("listaUOPuntoProtocolloEreditarietaAbilitata");
				Record record = new Record();
				record.setAttribute("idUOPP", idUOPP);
				record.setAttribute("idUser", idUser);
				record.setAttribute("listaUOPuntoProtocolloEscluse", listaUOPuntoProtocolloEscluse);
				record.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", listaUOPuntoProtocolloEreditarietaAbilitata);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
				lGwtRestDataSource.executecustom("leggiUOCollegatePuntoProtocollo", record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS){
							if (response.getData() != null) {
								Record lRecordDb    = response.getData()[0];
								RecordList listaUOCollegatePuntoProtocollo  = lRecordDb.getAttributeAsRecordList("listaUOCollegatePuntoProtocollo");
								Record recordNew = new Record();
								recordNew.setAttribute("listaUOCollegatePuntoProtocollo", listaUOCollegatePuntoProtocollo);
								final String denominazioneUo = formUtenteCorrente.getValueAsString("denominazioneUo");
								final String codRapido = formUtenteCorrente.getValueAsString("nriLivelliUo");								
								String title = "Abilitazioni vs UO collegate al punto di protocollo " + codRapido + "-" + denominazioneUo;								
								String mode = getMode();								
								UOCollegatePuntoProtocolloPopup uoCollegatePuntoProtocolloPopup = new UOCollegatePuntoProtocolloPopup(recordNew, title, mode) {
									@Override
									public void onClickOkButton(Record formRecord, DSCallback callback) {
										Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
										Layout.hideWaitPopup();
										setFormValuesFromRecordAfterAbilUOPuntoProtocollo(formRecord);
										markForDestroy();
									}
								};
								uoCollegatePuntoProtocolloPopup.show();						
							}
						}
					}						
				});				
			}
		});
		
		uoCollegatePuntoProtocolloButtonFormUtenteCorrente.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilToModUoCollegatePuntoProtocollo() && !isInSostituzione();
			}
		});

		
		formUtenteCorrente.setFields( // visibili
				                      utentiItem, 
			                          variazioniButton, 
			                          tipoDiAssegnazioneSelectItem, 
			                          dataDalItem, 
			                          dataAlOrigItem, 
			                          dataAlItem, 
			                          selezionaRuoloItem, 
			                          estendiDelegaSottoUoCheckboxItem, 
			                          estendiDelegaAllePostazioniUtenteCheckboxItem, 
			                          nomePostazioneItem,
			                          spacer2Item,
			                          uoCollegatePuntoProtocolloButtonFormUtenteCorrente,
			
			                          // Hidden
			                          ciRelUserUoItem, 
			                          idScrivaniaItem, 
			                          idUoItem, 
			                          sostituzioneSVItem, 
			                          intestazioneItem, 
			                          flgSpostamentoItem,
			                          flgDuplicazioneItem,
			                          flgUoPuntoProtocolloItem,
			                          listaUOPuntoProtocolloIncluseItem,
			                          listaUOPuntoProtocolloEscluseItem,
			                          listaUOPuntoProtocolloEreditarietaAbilitataItem,
			                          nriLivelliUoItem,
			                  		  denominazioneUoItem
		);

	}

	private void populateItemsUtenteNuovo() {

		formUtenteDaSostituireCon = new DynamicForm();
		formUtenteDaSostituireCon.setValuesManager(vm);
		formUtenteDaSostituireCon.setWrapItemTitles(false);
		formUtenteDaSostituireCon.setNumCols(20);
		formUtenteDaSostituireCon.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		creaSelectUtentiNew();

		creaSelectTipoDiAssegnazioneNew();

		creaSelectRuoloNew();

		dataDalNewItem = new DateItem("dataDalNew", I18NUtil.getMessages().organigramma_postazione_detail_data_dal());
		dataDalNewItem.setColSpan(1);
		dataDalNewItem.setStartRow(true);
		dataDalNewItem.setRequired(true);
		dataDalNewItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO bisogna togliere un giorno dalla data vecchia
				if (dataAlOrigItem.getValue() == null) {
					Date dataAl = dataDalNewItem.getValueAsDate();
					CalendarUtil.addDaysToDate(dataAl, -1);
					dataAlItem.setDefaultValue(dataAl);
				}
			}
		});

		dataAlNewItem = new DateItem("dataAlNew", I18NUtil.getMessages().organigramma_postazione_detail_data_al());
		dataAlNewItem.setColSpan(1);

		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setStartRow(true);
		spacerItem.setColSpan(1);

		estendiDelegaSottoUoNewCheckboxItem = new CheckboxItem("flgInclSottoUoNew");
		estendiDelegaSottoUoNewCheckboxItem.setValue(false);
		estendiDelegaSottoUoNewCheckboxItem.setColSpan(1);
		estendiDelegaSottoUoNewCheckboxItem.setWidth(10);
		estendiDelegaSottoUoNewCheckboxItem.setStartRow(true);
		estendiDelegaSottoUoNewCheckboxItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				// delega/funzionale
				if (formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew") != null
						&& formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew").equals("D")) {
					estendiDelegaSottoUoNewCheckboxItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_flg_estendi_delega_sotto_uo());
					return true;
				}
				// Appartenenza gerarchica
				else if (formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew") != null
						&& formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew").equals("A")) {
					estendiDelegaSottoUoNewCheckboxItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_con_delega_alle_sotto_uo());
					return true;
				}
				return false;
			}
		});

		estendiDelegaAllePostazioniUtenteNewCheckboxItem = new CheckboxItem("flgInclScrivVirtNew");
		estendiDelegaAllePostazioniUtenteNewCheckboxItem.setValue(false);
		estendiDelegaAllePostazioniUtenteNewCheckboxItem.setColSpan(1);
		estendiDelegaAllePostazioniUtenteNewCheckboxItem.setWidth(10);
		estendiDelegaAllePostazioniUtenteNewCheckboxItem.setStartRow(false);
		estendiDelegaAllePostazioniUtenteNewCheckboxItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				// delega/funzionale
				if (formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew") != null
						&& formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew").equals("D")) {
					estendiDelegaAllePostazioniUtenteNewCheckboxItem.setTitle(I18NUtil.getMessages()
							.organigramma_postazione_detail_flg_estendi_delega_postazioni_utente());
					return true;
				}
				// Appartenenza gerarchica
				else if (formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew") != null
						&& formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew").equals("A")) {
					estendiDelegaAllePostazioniUtenteNewCheckboxItem.setTitle(I18NUtil.getMessages()
							.organigramma_postazione_detail_flg_con_delega_postazioni_utente());
					return true;
				}
				return false;
			}
		});

		
		spacer3Item = new SpacerItem();
		spacer3Item.setWidth(20);
		spacer3Item.setColSpan(1);
		spacer3Item.setStartRow(true);
		
		//Bottone per vedere Abilitazioni vs UO collegate al punto di protocollo 
		uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon = new ImgButtonItem("uoCollegatePuntoProtocolloFormUtenteDaSostituireCon", "buttons/uoCollegatePuntoProtocollo.png", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_title());
		uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon.setAlwaysEnabled(true);
		uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon.setColSpan(1);
		uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon.addIconClickHandler(new IconClickHandler() {

					@Override
					public void onIconClick(IconClickEvent event) {
						String idUOPP = formUtenteCorrente.getValueAsString("idUo");
						String idUser = formUtenteCorrente.getValueAsString("idUser");
						String listaUOPuntoProtocolloEscluse = formUtenteCorrente.getValueAsString("listaUOPuntoProtocolloEscluse");
						String listaUOPuntoProtocolloEreditarietaAbilitata = formUtenteCorrente.getValueAsString("listaUOPuntoProtocolloEreditarietaAbilitata");
						Record record = new Record();
						record.setAttribute("idUOPP", idUOPP);
						record.setAttribute("idUser", idUser);
						record.setAttribute("listaUOPuntoProtocolloEscluse", listaUOPuntoProtocolloEscluse);
						record.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", listaUOPuntoProtocolloEreditarietaAbilitata);
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
						lGwtRestDataSource.executecustom("leggiUOCollegatePuntoProtocollo", record, new DSCallback() {
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS){
									if (response.getData() != null) {
										Record lRecordDb    = response.getData()[0];
										RecordList listaUOCollegatePuntoProtocollo  = lRecordDb.getAttributeAsRecordList("listaUOCollegatePuntoProtocollo");
										Record recordNew = new Record();
										recordNew.setAttribute("listaUOCollegatePuntoProtocollo", listaUOCollegatePuntoProtocollo);
										final String denominazioneUo = formUtenteCorrente.getValueAsString("denominazioneUo");
										final String codRapido = formUtenteCorrente.getValueAsString("nriLivelliUo");								
										String title = "Abilitazioni vs UO collegate al punto di protocollo " + codRapido + "-" + denominazioneUo;								
										String mode = getMode();								
										UOCollegatePuntoProtocolloPopup uoCollegatePuntoProtocolloPopup = new UOCollegatePuntoProtocolloPopup(recordNew, title, mode) {
											@Override
											public void onClickOkButton(Record formRecord, DSCallback callback) {
												Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
												Layout.hideWaitPopup();
												setFormValuesFromRecordAfterAbilUOPuntoProtocollo(formRecord);
												markForDestroy();
											}
										};
										uoCollegatePuntoProtocolloPopup.show();						
									}
								}
							}						
						});				
					}
		});
				
		uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return isAbilToModUoCollegatePuntoProtocollo() && isInSostituzione();
					}
		});

				
		formUtenteDaSostituireCon.setFields(utentiNewItem, 
				                            tipoDiAssegnazioneNewSelectItem, 
				                            dataDalNewItem, 
				                            dataAlNewItem, 
				                            selezionaRuoloNewItem,
				                            estendiDelegaSottoUoNewCheckboxItem, 
				                            estendiDelegaAllePostazioniUtenteNewCheckboxItem,
				                            spacer3Item,
				                            uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon
				                            );
		
		detailSectionUtenteCorrente        = new DetailSection(I18NUtil.getMessages().organigramma_postazione_detail_section_utente_corrente(), false, false, false, formUtenteCorrente);
		detailSectionUtenteDaSostituireCon = new DetailSection(I18NUtil.getMessages().organigramma_postazione_detail_section_utente_nuovo(), false, false, false, formUtenteDaSostituireCon);


	}

	private void creaSelectTipoDiAssegnazione() {
		GWTRestDataSource tipoRelUOUtenteDS = new GWTRestDataSource("LoadComboTipoRelUtenteUODataSource");
		tipoDiAssegnazioneSelectItem = new SelectItem("tipoRelUtenteUo", I18NUtil.getMessages().organigramma_postazione_detail_tipo_assegnazione());
		tipoDiAssegnazioneSelectItem.setOptionDataSource(tipoRelUOUtenteDS);
		tipoDiAssegnazioneSelectItem.setValueField("key");
		tipoDiAssegnazioneSelectItem.setDisplayField("value");
		tipoDiAssegnazioneSelectItem.setAttribute("obbligatorio", true);
		tipoDiAssegnazioneSelectItem.setRedrawOnChange(true);
		tipoDiAssegnazioneSelectItem.setWidth(550);
		tipoDiAssegnazioneSelectItem.setColSpan(15);
		tipoDiAssegnazioneSelectItem.setStartRow(true);
		tipoDiAssegnazioneSelectItem.setDefaultToFirstOption(true);
		tipoDiAssegnazioneSelectItem.setRequired(true);
		tipoDiAssegnazioneSelectItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				formUtenteCorrente.setValue("nomePostazione", ""); nomePostazioneItem.setValue("");
				formUtenteCorrente.setValue("ruolo", "");selezionaRuoloItem.setValue("");
				formUtenteCorrente.markForRedraw();
			}
		});
	}

	private void creaSelectTipoDiAssegnazioneNew() {
		GWTRestDataSource tipoRelUtenteUoNewDS = new GWTRestDataSource("LoadComboTipoRelUtenteUODataSource");
		tipoDiAssegnazioneNewSelectItem = new SelectItem("tipoRelUtenteUoNew", I18NUtil.getMessages().organigramma_postazione_detail_tipo_assegnazione());
		tipoDiAssegnazioneNewSelectItem.setOptionDataSource(tipoRelUtenteUoNewDS);
		tipoDiAssegnazioneNewSelectItem.setValueField("key");
		tipoDiAssegnazioneNewSelectItem.setDisplayField("value");
		tipoDiAssegnazioneNewSelectItem.setAttribute("obbligatorio", true);
		tipoDiAssegnazioneNewSelectItem.setRedrawOnChange(true);
		tipoDiAssegnazioneNewSelectItem.setWidth(550);
		tipoDiAssegnazioneNewSelectItem.setColSpan(15);
		tipoDiAssegnazioneNewSelectItem.setStartRow(true);
		tipoDiAssegnazioneNewSelectItem.setDefaultToFirstOption(true);
		tipoDiAssegnazioneNewSelectItem.setRequired(true);
		tipoDiAssegnazioneNewSelectItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				formUtenteDaSostituireCon.setValue("ruoloNew", "");selezionaRuoloNewItem.setValue("");
				formUtenteDaSostituireCon.markForRedraw();
			}
		});
	}

	private void creaSelectRuolo() {
		GWTRestDataSource selezionaRuoloDataSource = new GWTRestDataSource("LoadComboRuoloDataSource");
		selezionaRuoloItem = new SelectItem("ruolo", I18NUtil.getMessages().organigramma_postazione_detail_ruolo());
		selezionaRuoloItem.setShowTitle(true);
		selezionaRuoloItem.setValueField("key");
		selezionaRuoloItem.setDisplayField("value");
		selezionaRuoloItem.setOptionDataSource(selezionaRuoloDataSource);
		selezionaRuoloItem.setWidth(550);
		selezionaRuoloItem.setColSpan(15);
		selezionaRuoloItem.setAllowEmptyValue(true);
		selezionaRuoloItem.setStartRow(true);
		selezionaRuoloItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!isTipoDiAssegnazioneL());
			}
		});
		
	}

	private void creaSelectRuoloNew() {
		GWTRestDataSource selezionaRuoloNewDataSource = new GWTRestDataSource("LoadComboRuoloDataSource");
		selezionaRuoloNewItem = new SelectItem("ruoloNew", I18NUtil.getMessages().organigramma_postazione_detail_ruolo());
		selezionaRuoloNewItem.setShowTitle(true);
		selezionaRuoloNewItem.setValueField("key");
		selezionaRuoloNewItem.setDisplayField("value");
		selezionaRuoloNewItem.setOptionDataSource(selezionaRuoloNewDataSource);
		selezionaRuoloNewItem.setWidth(550);
		selezionaRuoloNewItem.setColSpan(15);
		selezionaRuoloNewItem.setAllowEmptyValue(true);
		selezionaRuoloNewItem.setStartRow(true);
		selezionaRuoloNewItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!isTipoDiAssegnazioneNewL());
			}
		});
	}

	protected void creaSelectUtenti() {
		
		SelectGWTRestDataSource lGwtRestDataSourceUtenti = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT, new String[] {
				"cognomeNome", "username" }, true);
		lGwtRestDataSourceUtenti.addParam("idUtenteToExclude", String.valueOf(Layout.getUtenteLoggato().getIdUser()));
		utentiItem = new FilteredSelectItemWithDisplay("idUser", lGwtRestDataSourceUtenti) {

			@Override
			public void onOptionClick(Record record) {
				String idUser = record.getAttribute("idUtente");
				recuperaTipoAssegnazioneXUtente(idUser, formUtenteCorrente.getValueAsString("idUo"), formUtenteCorrente.getValueAsString("ciRelUserUo"),
						new ServiceCallback<String>() {

							@Override
							public void execute(String tipoDiAssegnazioneDefault) {

								formUtenteCorrente.setValue("tipoRelUtenteUo", tipoDiAssegnazioneDefault);
								formUtenteCorrente.markForRedraw();
							}
						});
			}
		};
		utentiItem.setColSpan(15);
		ListGridField utentiCodiceField = new ListGridField("codice", "Cod.");
		utentiCodiceField.setWidth(100);
		
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");
		
		ListGridField utentiUsernameField = new ListGridField("username", "Username");
		utentiUsernameField.setWidth(190);
		
		utentiItem.setPickListFields(utentiCodiceField, utentiCognomeNomeField, utentiUsernameField);
		utentiItem.setFilterLocally(true);
		utentiItem.setValueField("idUtente");
		utentiItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_utenti());
		utentiItem.setOptionDataSource(lGwtRestDataSourceUtenti);
		utentiItem.setWidth(550);
		utentiItem.setRequired(true);
		utentiItem.setAllowEmptyValue(false);
		utentiItem.setClearable(false);
		utentiItem.setShowIcons(true);		
		utentiItem.setAutoFetchData(false);
		utentiItem.setFetchMissingValues(false);
		
		if (AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utentiItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utentiItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});
	}

	protected void creaSelectUtentiNew() {
		SelectGWTRestDataSource lGwtRestDataSourceUtentiNew = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT,
				new String[] { "cognomeNome", "username" }, true);
		lGwtRestDataSourceUtentiNew.addParam("idUtenteToExclude", String.valueOf(Layout.getUtenteLoggato().getIdUser()));
		utentiNewItem = new FilteredSelectItemWithDisplay("idUserNew", lGwtRestDataSourceUtentiNew) {

			@Override
			public void onOptionClick(Record record) {
				String idUserNew = record.getAttribute("idUtente");
				recuperaTipoAssegnazioneXUtente(idUserNew, formUtenteCorrente.getValueAsString("idUo"), formUtenteCorrente.getValueAsString("ciRelUserUo"),
						new ServiceCallback<String>() {

							@Override
							public void execute(String tipoDiAssegnazioneDefault) {

								formUtenteDaSostituireCon.setValue("tipoRelUtenteUoNew", tipoDiAssegnazioneDefault);
								formUtenteDaSostituireCon.markForRedraw();
							}
						});
			}
		};
		utentiNewItem.setColSpan(15);
		ListGridField utentiNewCodiceField = new ListGridField("codice", "Cod.");
		utentiNewCodiceField.setWidth(100);
		
		ListGridField utentiNewCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");
		
		ListGridField utentiNewUsernameField = new ListGridField("username", "Username");
		utentiNewUsernameField.setWidth(190);
		
		utentiNewItem.setPickListFields(utentiNewCodiceField, utentiNewCognomeNomeField, utentiNewUsernameField);
		utentiNewItem.setFilterLocally(true);
		utentiNewItem.setValueField("idUtente");
		utentiNewItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_utenti());
		utentiNewItem.setOptionDataSource(lGwtRestDataSourceUtentiNew);
		utentiNewItem.setWidth(550);
		utentiNewItem.setRequired(true);
		utentiNewItem.setAllowEmptyValue(false);
		utentiNewItem.setClearable(false);
		utentiNewItem.setShowIcons(true);
		if (AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utentiNewItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utentiNewItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});
	}

	@Override
	public void editRecord(Record record) {
		record.setAttribute("dataAlOrig", record.getAttribute("dataAl"));
		super.editRecord(record);
		
		initCombo(record);
		
		initSpostaDocFascSection(record);
		
		markForRedraw();
	}

	
	
	private void initCombo(Record record) {
		 
		// Inizializzo la combo degli UTENTI con il nome della postazione
		GWTRestDataSource utentiDS = (GWTRestDataSource) utentiItem.getOptionDataSource();
		if (record.getAttribute("idUser") != null && !"".equals(record.getAttributeAsString("idUser"))) {
			utentiDS.addParam("idUtente", record.getAttributeAsString("idUser"));
					
			if (record.getAttribute("descrizioneUser") != null && !"".equals(record.getAttributeAsString("descrizioneUser"))) {
				String descrizione = record.getAttribute("descrizioneUser");
				if (record.getAttribute("username") != null && !"".equals(record.getAttributeAsString("username"))) {
							descrizione += " ** " + record.getAttribute("username");
				}
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("idUser"), descrizione);
				utentiItem.setValueMap(valueMap);
				utentiItem.setValue(record.getAttribute("idUser"));
			}
		} 
		else 
		{
			utentiDS.addParam("idUtente", null);
		}
		utentiItem.setOptionDataSource(utentiDS);

				
		// Inizializzo la combo del RUOLO
		GWTRestDataSource selezionaRuoloDS = (GWTRestDataSource) selezionaRuoloItem.getOptionDataSource();
		if (record.getAttribute("ruolo") != null && !"".equals(record.getAttributeAsString("ruolo"))) {
			selezionaRuoloDS.addParam("ruoloAssegnato", record.getAttributeAsString("ruolo"));
		} 
		else 
		{
			selezionaRuoloDS.addParam("ruoloAssegnato", null);
		}
		selezionaRuoloItem.setOptionDataSource(selezionaRuoloDS);
				
			
		 // Combo Organigramma Sposta Doc/Fasc
		 if (record.getAttributeAsString("idUODestDocfasc") != null && !"".equals(record.getAttributeAsString("idUODestDocfasc"))) {
			 LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			 valueMap.put(record.getAttributeAsString("idUODestDocfasc"), record.getAttributeAsString("desUODestDocFasc"));
			 organigrammaSpostaDocFascItem.setValueMap(valueMap);
			 organigrammaSpostaDocFascItem.setValue(record.getAttribute("idUODestDocfasc"));
			 uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", record.getAttribute("desUODestDocFasc"));	
			 uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", record.getAttribute("livelliUODestDocFasc"));
			 uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", record.getAttribute("flgTipoDestDoc"));
		 } 
	}
	
	
	private void initSpostaDocFascSection(Record record) {
		 Date dataCessazione = record.getAttribute("dataAl") != null ? DateUtil.parseInput(record.getAttribute("dataAl")) : null;
		 // Se la data non e' presente, nascondo le sezioni
		 if (dataCessazione!=null){
			 // Se la data >= data odierna, mostro le sezioni per lo spostamento e nascondo quella del resoconto
			 if (isDataCessazioneValida(dataCessazione)){
				 uoSpostaDocFascSection.setVisible(true);	
				 codRapidoSpostaDocFascItem.setRequired(true);
				 organigrammaSpostaDocFascItem.setRequired(true);
				 resocontoSpostamentoDocFascSection.setVisible(false);
			 }
			 // Se la data < data odierna, nascondo le sezioni per lo spostamento e mostro quella del resoconto
			 if (isDataCessazioneScaduta(dataCessazione)){
				 uoSpostaDocFascSection.setVisible(false);	
				 codRapidoSpostaDocFascItem.setRequired(false);
				 organigrammaSpostaDocFascItem.setRequired(false);
				 resocontoSpostamentoDocFascSection.setVisible(true);
			 }
		 }
        else
           {
		     uoSpostaDocFascSection.setVisible(false);	
			 codRapidoSpostaDocFascItem.setRequired(false);
			 organigrammaSpostaDocFascItem.setRequired(false);
			 resocontoSpostamentoDocFascSection.setVisible(false);
			}
	}
	
	
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		if (mode.equals("new")) {
			utentiItem.setCanEdit(true);
		} else {
			utentiItem.setCanEdit(false);
		}
		if (isInSostituzione()) {
			setCanEdit(false, formUtenteCorrente);
		}
		
		// i campi della sezione RESOCONTO devono essere sempre read-only
		setCanEdit(false, resocontoSpostamentoDocFascForm);						

	}

	private void recuperaTipoAssegnazioneXUtente(String idUser, String idUo, String ciRelUserUo, final ServiceCallback<String> callback) {
		Record record = new Record();
		record.setAttribute("idUo", idUo);
		record.setAttribute("ciRelUserUo", ciRelUserUo);
		record.setAttribute("idUser", idUser);
		new GWTRestDataSource("PostazioneDatasource").executecustom("recuperaTipoAssegnazioneXUtente", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				callback.execute(response.getData()[0].getAttribute("tipoRelUtenteUo"));
			}
		});
	}

	public boolean isInSostituzione() {
		Record record = formUtenteCorrente.getValuesAsRecord();
		return (record.getAttribute("sostituzioneSV") != null && record.getAttribute("sostituzioneSV").equals("1"));
	}
	
	private boolean isTipoDiAssegnazioneL(){
		return (tipoDiAssegnazioneSelectItem.getValue()!=null && tipoDiAssegnazioneSelectItem.getValueAsString().equals("L"));
	}
	
	private boolean isTipoDiAssegnazioneNewL(){
		return (tipoDiAssegnazioneNewSelectItem.getValue()!=null && tipoDiAssegnazioneNewSelectItem.getValueAsString().equals("L"));
	}

	public boolean isAbilToModUoCollegatePuntoProtocollo() {
		// TODO Auto-generated method stub
		return (flgUoPuntoProtocolloItem.getValue()!=null && flgUoPuntoProtocolloItem.getValue().toString().equals("true"));
	}

	public String getMode() {
		return getLayout().getMode();
	}

	public void setFormValuesFromRecordAfterAbilUOPuntoProtocollo(Record record) {
		formUtenteCorrente.setValue("listaUOPuntoProtocolloIncluse", record.getAttribute("listaUOPuntoProtocolloIncluse"));
		formUtenteCorrente.setValue("listaUOPuntoProtocolloEscluse", record.getAttribute("listaUOPuntoProtocolloEscluse"));
		formUtenteCorrente.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
	}
	
	
	private void creaSelectOrganigrammaSpostaDocFasc() {

		String tipoAssegnatari = "UO;SV";
		
		uoSpostaDocFascForm = new DynamicForm();
		uoSpostaDocFascForm.setValuesManager(vm);
		uoSpostaDocFascForm.setWidth("100%");
		uoSpostaDocFascForm.setHeight("5");
		uoSpostaDocFascForm.setPadding(5);
		uoSpostaDocFascForm.setWrapItemTitles(false);		
		uoSpostaDocFascForm.setNumCols(6);
		uoSpostaDocFascForm.setColWidths(1, 1, 1, 1, "*", "*");
		
		flgPresentiDocFascItem                = new HiddenItem("flgPresentiDocFasc");
		typeNodoSpostaDocFascItem             = new HiddenItem("typeNodoSpostaDocFasc");
		idUoSpostaDocFascItem                 = new HiddenItem("idUoSpostaDocFasc");
		descrizioneSpostaDocFascItem          = new HiddenItem("descrizioneSpostaDocFasc");
		flgTipoDestDocItem                    = new HiddenItem("flgTipoDestDoc");
		
		
		
		codRapidoSpostaDocFascItem = new ExtendedTextItem("codRapidoSpostaDocFasc", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoSpostaDocFascItem.setWidth(80);
		codRapidoSpostaDocFascItem.setColSpan(1);
		codRapidoSpostaDocFascItem.setStartRow(true);
		codRapidoSpostaDocFascItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", (String) null);
				uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", (String) null);
				uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", (String) null);
				uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", (String) null);
				uoSpostaDocFascForm.setValue("gruppoSpostaDocFasc", (String) null);
				uoSpostaDocFascForm.clearErrors(true);
				final String value = codRapidoSpostaDocFascItem.getValueAsString();
				{
					GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaSpostaDocFascItem.getOptionDataSource();
					organigrammaDS.addParam("flgSoloValide", "1");
					organigrammaSpostaDocFascItem.setOptionDataSource(organigrammaDS);
					if (value != null && !"".equals(value)) {
						organigrammaSpostaDocFascItem.fetchData(new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								RecordList data = response.getDataAsRecordList();
								boolean trovato = false;
								if (data.getLength() > 0) {
									for (int i = 0; i < data.getLength(); i++) {
										String codice = data.get(i).getAttribute("codice");
										String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
										if (value.equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
											uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", data.get(i).getAttribute("descrizioneOrig"));
											uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", data.get(i).getAttribute("id"));
											uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", data.get(i).getAttribute("idUo"));
											uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", data.get(i).getAttribute("typeNodo"));
											uoSpostaDocFascForm.clearErrors(true);
											trovato = true;
											break;
										}
									}
								}
								if (!trovato) {
									codRapidoSpostaDocFascItem.validate();
									codRapidoSpostaDocFascItem.blurItem();
								}
							}
						});
					} else {
						organigrammaSpostaDocFascItem.fetchData();
					}
				}
			}
		});
		codRapidoSpostaDocFascItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (codRapidoSpostaDocFascItem.getValue() != null && 
					!"".equals(codRapidoSpostaDocFascItem.getValueAsString().trim()) && 
					organigrammaSpostaDocFascItem.getValue() == null						
					) {
					return false;
				}
				return true;
			}
		});
		
		SelectGWTRestDataSource lGwtRestDataSourceOrganigramma = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT, new String[] { "descrizione" }, true);
		lGwtRestDataSourceOrganigramma.addParam("tipoAssegnatari", tipoAssegnatari);
		lGwtRestDataSourceOrganigramma.addParam("finalita", "ALTRO");

		organigrammaSpostaDocFascItem = new FilteredSelectItemWithDisplay("organigrammaSpostaDocFasc", lGwtRestDataSourceOrganigramma) {

			@Override
			public void manageOnCellClick(CellClickEvent event) {
				String flgSelXFinalita = event.getRecord().getAttributeAsString("flgSelXFinalita");
				if (flgSelXFinalita == null || "1".equals(flgSelXFinalita)) {
					onOptionClick(event.getRecord());
				} else {
					event.cancel();
					Layout.addMessage(new MessageBean("Valore non selezionabile", "", MessageType.ERROR));
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							clearSelect();
						}
					});
				}
			}

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc",            record.getAttributeAsString("codice"));
				uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc",             record.getAttributeAsString("typeNodo"));
				uoSpostaDocFascForm.setValue("idUoSpostaDocFasc",                 record.getAttributeAsString("idUo"));
				uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc",          record.getAttributeAsString("descrizioneOrig"));		
				uoSpostaDocFascForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						organigrammaSpostaDocFascItem.fetchData();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", "");
				uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", "");
				if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", "");
				}
				uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", "");
				uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", "");
				uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", "");
				uoSpostaDocFascForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						organigrammaSpostaDocFascItem.fetchData();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", "");
					uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", "");
					if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", "");
					}
					uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", "");
					uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", "");
					uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", "");					
					uoSpostaDocFascForm.clearErrors(true);
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
							organigrammaSpostaDocFascItem.fetchData();
						}
					});
				}
			}
		};
		List<ListGridField> organigrammaSpostaDocFascPickListFields = new ArrayList<ListGridField>();
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			ListGridField typeNodoField = new ListGridField("iconaTypeNodo", "Tipo");
			typeNodoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			typeNodoField.setAlign(Alignment.CENTER);
			typeNodoField.setWidth(30);
			typeNodoField.setShowHover(false);
			typeNodoField.setCanFilter(false);
			typeNodoField.setCellFormatter(new CellFormatter() {
				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("iconaTypeNodo") != null && !"".equals(record.getAttributeAsString("iconaTypeNodo"))) {
						return "<div align=\"center\"><img src=\"images/organigramma/tipo/" + record.getAttribute("iconaTypeNodo")
								+ ".png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			organigrammaSpostaDocFascPickListFields.add(typeNodoField);
		}
		ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().organigramma_list_codUoField_title());
		codiceField.setWidth(80);
		codiceField.setShowHover(true);
		codiceField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return (record != null ? record.getAttribute("descrizioneEstesa") : null);
			}
		});
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			codiceField.setCanFilter(false);
		}
		organigrammaSpostaDocFascPickListFields.add(codiceField);
		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		descrizioneField.setWidth("*");
		organigrammaSpostaDocFascPickListFields.add(descrizioneField);
		
		organigrammaSpostaDocFascItem.setPickListFields(organigrammaSpostaDocFascPickListFields.toArray(new ListGridField[organigrammaSpostaDocFascPickListFields.size()]));
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			organigrammaSpostaDocFascItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());			
		} else {
			organigrammaSpostaDocFascItem.setFilterLocally(true);
		}
		organigrammaSpostaDocFascItem.setAutoFetchData(false);
		organigrammaSpostaDocFascItem.setAlwaysFetchMissingValues(true);
		organigrammaSpostaDocFascItem.setFetchMissingValues(true);
		organigrammaSpostaDocFascItem.setValueField("id");
		organigrammaSpostaDocFascItem.setOptionDataSource(lGwtRestDataSourceOrganigramma);
		organigrammaSpostaDocFascItem.setShowTitle(false);
		organigrammaSpostaDocFascItem.setWidth(550);
		organigrammaSpostaDocFascItem.setClearable(true);
		organigrammaSpostaDocFascItem.setShowIcons(true);
		organigrammaSpostaDocFascItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		
		ListGrid pickListProperties = organigrammaSpostaDocFascItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codRapido = uoSpostaDocFascForm.getValueAsString("codRapidoSpostaDocFasc");
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaSpostaDocFascItem.getOptionDataSource();
				organigrammaDS.addParam("codice", codRapido);
				organigrammaDS.addParam("finalita", "ALTRO");
				organigrammaDS.addParam("flgSoloValide", "1");
				organigrammaSpostaDocFascItem.setOptionDataSource(organigrammaDS);
				organigrammaSpostaDocFascItem.invalidateDisplayValueCache();
			}
		});
		organigrammaSpostaDocFascItem.setPickListProperties(pickListProperties);

		lookupOrganigrammaSpostaDocFascButton = new ImgButtonItem("lookupOrganigrammaSpostaDocFascButton", "lookup/organigramma.png", I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaSpostaDocFascButton.setColSpan(1);
		lookupOrganigrammaSpostaDocFascButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				AssegnazioneLookupOrganigrammaSpostaDocFasc lookupOrganigrammaPopup = new AssegnazioneLookupOrganigrammaSpostaDocFasc(null);
				lookupOrganigrammaPopup.show();
			}
		});
		
		uoSpostaDocFascForm.setFields( // visibili
				                       codRapidoSpostaDocFascItem,
				                       lookupOrganigrammaSpostaDocFascButton,
				                       organigrammaSpostaDocFascItem,				                       
	                                   // Hidden
				                       flgTipoDestDocItem,
				                       flgPresentiDocFascItem,
				                       typeNodoSpostaDocFascItem             ,
				                       idUoSpostaDocFascItem                 ,
				                       descrizioneSpostaDocFascItem          
	                                 );	
		
		uoSpostaDocFascSection = new DetailSection(I18NUtil.getMessages().organigramma_postazione_detail_uoSpostaDocFascSection_title(), true, true, false, uoSpostaDocFascForm);
		uoSpostaDocFascSection.setVisible(isUOSpostaDocFascSectionVisible());		
	}

	
	private void creaResocontoSpostamentoSection() {
		resocontoSpostamentoDocFascForm = new DynamicForm();
		resocontoSpostamentoDocFascForm.setValuesManager(vm);
		resocontoSpostamentoDocFascForm.setWidth("100%");
		resocontoSpostamentoDocFascForm.setHeight("5");
		resocontoSpostamentoDocFascForm.setPadding(5);
		resocontoSpostamentoDocFascForm.setWrapItemTitles(false);		
		resocontoSpostamentoDocFascForm.setNumCols(16);
		resocontoSpostamentoDocFascForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		
		// Conteggio documenti
		nrDocAssegnatiItem = new NumericItem("nrDocAssegnati", I18NUtil.getMessages().organigramma_uo_detail_nrDocAssegnati_title());
		nrDocAssegnatiItem.setStartRow(true);
		nrDocAssegnatiItem.setWidth(100);

		dataConteggioDocAssegnatiItem = new DateTimeItem("dataConteggioDocAssegnati", I18NUtil.getMessages().organigramma_uo_detail_dataConteggioDocAssegnati_title());
		dataConteggioDocAssegnatiItem.setWidth(120);

		// Conteggio fascicoli
		nrFascAssegnatiItem = new NumericItem("nrFascAssegnati", I18NUtil.getMessages().organigramma_uo_detail_nrFascAssegnati_title());
		nrFascAssegnatiItem.setStartRow(true);
		nrFascAssegnatiItem.setWidth(100);

		dataConteggioFascAssegnatiItem = new DateTimeItem("dataConteggioFascAssegnati", I18NUtil.getMessages().organigramma_uo_detail_dataConteggioFascAssegnati_title());
		dataConteggioFascAssegnatiItem.setWidth(120);
		
		// Stato spostamento doc/fasc
		descUoSpostamentoDocFascItem = new TextItem("descUoSpostamentoDocFasc", I18NUtil.getMessages().organigramma_postazione_detail_descUoSpostamentoDocFasc_title());		
		descUoSpostamentoDocFascItem.setStartRow(true);
		descUoSpostamentoDocFascItem.setColSpan(3);
		descUoSpostamentoDocFascItem.setWidth(340);

		statoSpostamentoDocFascItem = new TextItem("statoSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_statoSpostamentoDocFasc_title());		
		statoSpostamentoDocFascItem.setWidth(120);
		
		dataInizioSpostamentoDocFascItem = new DateTimeItem("dataInizioSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_dataInizioSpostamentoDocFasc_title());
		dataInizioSpostamentoDocFascItem.setWidth(120);
		
		dataFineSpostamentoDocFascItem = new DateTimeItem("dataFineSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_dataFineSpostamentoDocFasc_title());
		dataFineSpostamentoDocFascItem.setWidth(120);

					
		resocontoSpostamentoDocFascForm.setFields( // visibili
				                                       nrDocAssegnatiItem,
				                                       dataConteggioDocAssegnatiItem,
				                                       nrFascAssegnatiItem,
				                                       dataConteggioFascAssegnatiItem,
				                                       descUoSpostamentoDocFascItem,
				                                       statoSpostamentoDocFascItem,
				                                       dataInizioSpostamentoDocFascItem,
				                                       dataFineSpostamentoDocFascItem
                                                      );	

		resocontoSpostamentoDocFascSection = new DetailSection(I18NUtil.getMessages().organigramma_postazione_detail_resocontoSpostamentoDocFascSection_title(), true, true, false, resocontoSpostamentoDocFascForm);
		resocontoSpostamentoDocFascSection.setVisible(false);		
	}
	
	private boolean isUOSpostaDocFascSectionVisible(){
		
		Date dataCessazione      = dataAlItem.getValueAsDate();
		
		// Se la data di cessazione < data odierna   
		if (isDataCessazioneScaduta(dataCessazione)){
		
			// Verifico se sono presenti doc/fasc
			String flgPresentiDocFasc = vm.getValueAsString("flgPresentiDocFasc");
			
			if (flgPresentiDocFasc != null){
				if (flgPresentiDocFasc.equalsIgnoreCase("1")){
					return true;
				}
				else{
					return false;
				}
			}else{
				return false;
			}	
		}
		
		// Se la data di cessazione >= data odierna
		if (isDataCessazioneValida(dataCessazione)){
			return true;
		}
		else{
			return false;
		}		
	}
		
	
	public boolean isDataCessazioneValida(Date dataCessazione){
		Date today = new Date();
		
		if ( dataCessazione == null)
			return false;
		
		if ( dataCessazione.after(today) || CalendarUtil.isSameDate(dataCessazione, today) )
			return true;
		else
			return false;
		
	}
	
	public boolean isDataCessazioneScaduta(Date dataCessazione){
		Date today = new Date();
		
		if ( dataCessazione == null)
			return false;
		
		if ( !CalendarUtil.isSameDate(dataCessazione, today) && dataCessazione.before(today) )
			return true;
		else
			return false;
	}
	
	public class AssegnazioneLookupOrganigrammaSpostaDocFasc extends LookupOrganigrammaPopup {

		public AssegnazioneLookupOrganigrammaSpostaDocFasc(Record record) {
			super(record, true, getFlgIncludiUtenti());
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordSpostaDocFasc(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {
		}

		@Override
		public String getFinalita() {
				return "ALTRO";
		}
		
		@Override
		public Boolean getFlgSoloAttive() {
			return true;
		}

	}
	
	public void setFormValuesFromRecordSpostaDocFasc(Record record) {
		String idOrganigramma = record.getAttribute("idUoSvUt");
		String tipo = record.getAttribute("tipo");
		int pos = tipo.indexOf("_");
		if (pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", tipo + idOrganigramma);
		uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", idOrganigramma);
		uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", tipo);
		uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", record.getAttribute("codRapidoUo"));
		uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", record.getAttribute("nome"));		
		uoSpostaDocFascForm.clearErrors(true);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				organigrammaSpostaDocFascItem.fetchData();
			}
		});
	}

	
	private void updateSpostaDocFascSection(Object dataCessazione){

		 organigrammaSpostaDocFascItem.setValue("");  uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", (String) null);
		 descrizioneSpostaDocFascItem.setValue("");   uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc",  (String) null);
		 codRapidoSpostaDocFascItem.setValue("");     uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc",    (String) null);
		 idUoSpostaDocFascItem.setValue("");          uoSpostaDocFascForm.setValue("idUoSpostaDocFasc",         (String) null);
		 typeNodoSpostaDocFascItem.setValue("");      uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc",     (String) null);


		if (dataCessazione != null){
			uoSpostaDocFascSection.setVisible(isUOSpostaDocFascSectionVisible());	
			codRapidoSpostaDocFascItem.setRequired(isUOSpostaDocFascSectionVisible());
			organigrammaSpostaDocFascItem.setRequired(isUOSpostaDocFascSectionVisible());
			resocontoSpostamentoDocFascSection.setVisible(false);
		}
		else{
			uoSpostaDocFascSection.setVisible(false);	
			codRapidoSpostaDocFascItem.setRequired(false);
			organigrammaSpostaDocFascItem.setRequired(false);
			resocontoSpostamentoDocFascSection.setVisible(false);
		}
		uoSpostaDocFascSection.markForRedraw();	
		resocontoSpostamentoDocFascSection.markForRedraw();
	}


	public Integer getFlgIncludiUtenti() {
		Integer flgIncludiUtenti = new Integer(1);
		String tipoAssegnatari = getTipoAssegnatari();
		if(tipoAssegnatari != null) {
			if("UO".equals(tipoAssegnatari)) {
				flgIncludiUtenti = new Integer(0);
			} else if("SV".equals(tipoAssegnatari)) {
				flgIncludiUtenti = new Integer(2);				 
			} 
		}
		return flgIncludiUtenti;
	}

	public String getTipoAssegnatari() {
		return tipoAssegnatari;
	}

	public void setTipoAssegnatari(String tipoAssegnatari) {
		this.tipoAssegnatari = tipoAssegnatari;
	}
}
