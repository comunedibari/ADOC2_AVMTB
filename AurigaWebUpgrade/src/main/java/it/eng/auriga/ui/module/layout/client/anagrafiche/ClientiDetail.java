package it.eng.auriga.ui.module.layout.client.anagrafiche;

import java.util.LinkedHashMap;

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;

public class ClientiDetail extends CustomDetail {
	
	protected ClientiDetail _instance;
	
	// DynamicForm
	protected DynamicForm soggettiForm;
	protected DynamicForm nascitaIstituzioneForm;
	protected DynamicForm indirizziForm;
	protected DynamicForm contattiForm;
	protected DynamicForm cessazioneForm;
	protected DynamicForm altreDenominazioniForm;
	protected DynamicForm datiPagamentoForm;
	
	// DetailSection
	protected DetailSection soggettiSection;
	protected DetailSection nascitaIstituzioneSection;
	protected DetailSection indirizziSection;
	protected DetailSection contattiSection;
	protected DetailSection altreDenominazioniSection;
	protected DetailSection cessazioneSection;
	protected DetailSection datiPagamentoSection;
	
	// SelectItem
	protected SelectItem tipoItem;
	protected SelectItem condizioneGiuridicaItem;
	protected SelectItem causaleCessazioneItem;
	protected SelectItem sessoItem;
	protected SelectItem esigibilitaIvaItem;
	
	// FilteredSelectItem
	protected FilteredSelectItem cittadinanzaItem;
	protected FilteredSelectItem statoNascitaIstituzioneItem;
	protected FilteredSelectItem comuneNascitaIstituzioneItem;

	// TextItem
	protected TextItem codiceRapidoItem;
	protected TextItem cognomeItem;
	protected TextItem nomeItem;
	protected TextItem titoloItem;
	protected TextItem denominazioneItem;
	protected TextItem codiceFiscaleItem;
	protected TextItem partitaIvaItem;
	protected TextItem codiceIpaItem;
	protected TextItem provNascitaIstituzioneItem;
	protected TextItem cittaNascitaIstituzioneItem;	
	protected TextItem beneficiarioItem;
	protected TextItem istitutoFinanziarioItem;
	protected TextItem ibanItem;
	protected TextItem abiItem;
	protected TextItem cabItem;
	protected TextItem odaCigItem;
	protected TextItem odaCupItem;
	protected TextItem emailPecItem;
	
	// HiddenItem
	protected HiddenItem idSoggettoItem;
	protected HiddenItem idClienteItem;	
	protected HiddenItem flgDiSistemaItem;
	protected HiddenItem flgValidoItem;
	protected HiddenItem flgIgnoreWarningItem;	
	protected HiddenItem flgInOrganigrammaItem;		
	protected HiddenItem nomeComuneNascitaIstituzioneItem;	
	
	// DateItem 
	protected DateItem dataNascitaIstituzioneItem;
	protected DateItem dataCessazioneItem;
	
	// ReplicableItem
	protected IndirizziSoggettoItem indirizziItem;
	protected ContattiSoggettoItem contattiItem;
	protected AltreDenominazioniSoggettoItem altreDenominazioniItem;	
	
	protected boolean editing;
	
	protected String nomeEntita;
	
	public ClientiDetail(String nomeEntita) {
		super(nomeEntita);
		_instance = this;
		
		setNomeEntita(nomeEntita);
		
		buildSoggettiSection();
		buildDatiPagamentoSection();
		buildNascitaIstituzioneSection();
		buildCessazioneSection();
		buildIndirizziSection();
		buildContattiSection();
		buildAltreDenominazioniSection();
	}
	
	// Sezione SOGGETTI
	private void  buildSoggettiSection(){
		
		soggettiForm = new DynamicForm();			
		soggettiForm.setValuesManager(vm);
		soggettiForm.setWidth("100%");  
		soggettiForm.setHeight("5");  
		soggettiForm.setPadding(5);
		soggettiForm.setNumCols(12);
		soggettiForm.setWrapItemTitles(false);
		
		idSoggettoItem        = new HiddenItem("idSoggetto");		
		idClienteItem         = new HiddenItem("idCliente");
		flgDiSistemaItem      = new HiddenItem("flgDiSistema");			
		flgValidoItem         = new HiddenItem("flgValido");			
		flgIgnoreWarningItem  = new HiddenItem("flgIgnoreWarning"); flgIgnoreWarningItem.setDefaultValue(0);
		flgInOrganigrammaItem = new HiddenItem("flgInOrganigramma");			
		
		tipoItem = new SelectItem("tipo", I18NUtil.getMessages().clienti_detail_tipoItem_title()) {
			@Override
			public void setValue(String value) {
				
				super.setValue(value);
				if(value.equals("UP") || value.equals("#AF")) {
					nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().clienti_detail_nascitaSection_title());
					indirizziItem.validateTipo("1");
				} else {
					nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().clienti_detail_istituzioneSection_title());
					indirizziItem.validateTipo(null);
				}				
				_instance.redraw();
			}
		}; 		
		
		String idDominio = AurigaLayout.getIdDominio();
		boolean gestPA = AurigaLayout.getParametroDBAsBoolean("GEST_PA_IN_RUBRICA_DA_SP_AOO");
		LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
		if(idDominio == null || "".equals(idDominio) || gestPA) 
		{
			styleMap.put("PA+", I18NUtil.getMessages().clienti_tipo_APA_value());
		}
		styleMap.put("#AF", I18NUtil.getMessages().clienti_tipo_AF_value());
		styleMap.put("#AG", I18NUtil.getMessages().clienti_tipo_AG_value());		
		tipoItem.setValueMap(styleMap); 
		tipoItem.setWidth(200);						
		tipoItem.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				
				String tipo = soggettiForm.getValue("tipo") != null ? soggettiForm.getValueAsString("tipo") : "";
				if(tipo.equals("UP") || tipo.equals("#AF")) {
					nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().clienti_detail_nascitaSection_title());
					indirizziItem.validateTipo("1");					
				} else {
					nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().clienti_detail_istituzioneSection_title());
					indirizziItem.validateTipo(null);
				}
				_instance.redraw();
			}
		});
		tipoItem.setDefaultValue("PA+");
		tipoItem.setVisible(false);
		
		cognomeItem = new TextItem("cognome",I18NUtil.getMessages().clienti_detail_cognomeItem_title());		
		cognomeItem.setWidth(200);
		cognomeItem.setStartRow(true);
		cognomeItem.setAttribute("obbligatorio", true);		
		cognomeItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});			
		cognomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return isPersonaFisica();
			}
		}));
		
		nomeItem = new TextItem("nome", I18NUtil.getMessages().clienti_detail_nomeItem_title());
		nomeItem.setWidth(200);
		nomeItem.setAttribute("obbligatorio", true);		
		nomeItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});
		nomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return isPersonaFisica();
			}
		}));
		
		titoloItem = new TextItem("titolo", I18NUtil.getMessages().clienti_detail_titoloItem_title());
		titoloItem.setWidth(200);
		titoloItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});
		titoloItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});
		
		codiceIpaItem = new TextItem("codiceIpa",I18NUtil.getMessages().clienti_detail_codiceIpaItem_title());		
		codiceIpaItem.setWidth(100);		
		//codiceIpaItem.setAttribute("obbligatorio", true);
		codiceIpaItem.setRequired(true);

		codiceRapidoItem = new TextItem("codiceRapido",I18NUtil.getMessages().clienti_detail_codiceRapidoItem_title());
		codiceRapidoItem.setStartRow(true);
		codiceRapidoItem.setRequired(ClientiLayout.isNespresso());
		codiceRapidoItem.setWidth(100);
		codiceRapidoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return ClientiLayout.isNespresso();
			}
		}));


		denominazioneItem = new TextItem("denominazione", I18NUtil.getMessages().clienti_detail_denominazioneItem_title());
		denominazioneItem.setWidth(200);		
		denominazioneItem.setAttribute("obbligatorio", true);		
		denominazioneItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return !isPersonaFisica();
			}
		});	
		denominazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return !isPersonaFisica();
			}
		}));
		
		codiceFiscaleItem = new TextItem("codiceFiscale", I18NUtil.getMessages().clienti_detail_codiceFiscaleItem_title());
		codiceFiscaleItem.setWidth(200);
		codiceFiscaleItem.setStartRow(true);
		codiceFiscaleItem.setLength(16);
		codiceFiscaleItem.setWrapTitle(false);	
		codiceFiscaleItem.setCharacterCasing(CharacterCasing.UPPER);
		codiceFiscaleItem.setValidators(new CustomValidator() {			
			@Override
			protected boolean condition(Object value) {
				
				if(value == null || "".equals((String) value)) return true;
				if(isPersonaFisica()) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					return regExp.test((String) value);
				} else {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
					return regExp.test((String) value);
				}				
			}
		});
		
		partitaIvaItem = new TextItem("partitaIva", I18NUtil.getMessages().clienti_detail_partitaIvaItem_title());
		partitaIvaItem.setWidth(200);		
		partitaIvaItem.setLength(11);
		partitaIvaItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return !isPersonaFisica();
			}
		});		
		partitaIvaItem.setValidators(new CustomValidator() {			
			@Override
			protected boolean condition(Object value) {
				
				if(value == null || "".equals((String) value)) return true;
				if(isPersonaFisica()) {
					return true;
				} else {
					RegExp regExp = RegExp.compile(RegExpUtility.partitaIvaRegExp());
					return regExp.test((String) value);
				} 			
			}
		});
			
		sessoItem = new SelectItem("sesso", I18NUtil.getMessages().clienti_detail_sessoItem_title());
		sessoItem.setWidth(100);
		sessoItem.setAllowEmptyValue(true);
		LinkedHashMap<String, String> sessoValueMap = new LinkedHashMap<String, String>();		
		sessoValueMap.put("M", I18NUtil.getMessages().clienti_sesso_M_value()); 
		sessoValueMap.put("F", I18NUtil.getMessages().clienti_sesso_F_value());
		sessoItem.setValueMap(sessoValueMap);  
		sessoItem.setDefaultValue((String) null);				
		sessoItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});	
		
		cittadinanzaItem = new FilteredSelectItem("cittadinanza", I18NUtil.getMessages().clienti_detail_cittadinanzaItem_title()); 
		ListGridField codIstatStatoCittadinanzaField = new ListGridField("codIstatStato", "Cod.");	
		codIstatStatoCittadinanzaField.setHidden(true);
		ListGridField nomeStatoCittadinanzaField = new ListGridField("nomeStato", "Stato");		
		cittadinanzaItem.setPickListFields(codIstatStatoCittadinanzaField, nomeStatoCittadinanzaField);
		cittadinanzaItem.setValueField("codIstatStato");  
		cittadinanzaItem.setDisplayField("nomeStato");
		final GWTRestDataSource cittadinanzaSoggettoDS = new GWTRestDataSource("StatoDataSource", "codIstatStato", FieldType.TEXT, true);
		cittadinanzaSoggettoDS.addParam("flgSoloVld", "1");	
		cittadinanzaItem.setOptionDataSource(cittadinanzaSoggettoDS); 
		cittadinanzaItem.setWidth(230);
		cittadinanzaItem.setClearable(true);	
		cittadinanzaItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
												
				return isPersonaFisica();
			}
		});	
				
		condizioneGiuridicaItem = new SelectItem("condizioneGiuridica", I18NUtil.getMessages().clienti_detail_condizioneGiuridicaItem_title()); 	
		condizioneGiuridicaItem.setValueField("key");  
		condizioneGiuridicaItem.setDisplayField("value");
		final GWTRestDataSource condizioneGiuridicaSoggettoDS = new GWTRestDataSource("CondGiuridicaSoggettoDataSource", "key", FieldType.TEXT);
		
		condizioneGiuridicaItem.setOptionDataSource(condizioneGiuridicaSoggettoDS); 		
		condizioneGiuridicaItem.setWidth(200);	
		condizioneGiuridicaItem.setClearable(true);
		
		condizioneGiuridicaItem.setCachePickListResults(false);		
		condizioneGiuridicaItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {				
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				if(idSoggettoItem.getValue() != null && !"".equals((String) idSoggettoItem.getValue())) {
					Criterion[] criterias = new Criterion[1];	
					criterias[0] = new Criterion("idSoggetto", OperatorId.EQUALS, (String) idSoggettoItem.getValue());	
					return new AdvancedCriteria(OperatorId.AND, criterias);
				} 
				return null;
			}
		});
		condizioneGiuridicaItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
												
				return !isPersonaFisica() && !ClientiLayout.isNespresso();
			}
		});	
		
		esigibilitaIvaItem = new SelectItem("esigibilitaIva", I18NUtil.getMessages().clienti_detail_esigibilitaIvaItem_title());
		esigibilitaIvaItem.setWidth(200);
		esigibilitaIvaItem.setAllowEmptyValue(false);
		
		LinkedHashMap<String, String> esigibilitaIvaMap = new LinkedHashMap<String, String>();
		esigibilitaIvaMap.put("S", I18NUtil.getMessages().clienti_descEsigibilitaIva_S_value());
		esigibilitaIvaMap.put("D", I18NUtil.getMessages().clienti_descEsigibilitaIva_D_value());
		esigibilitaIvaMap.put("I", I18NUtil.getMessages().clienti_descEsigibilitaIva_I_value());
		esigibilitaIvaItem.setValueMap(esigibilitaIvaMap);
		esigibilitaIvaItem.setDefaultValue((String) "S");
		esigibilitaIvaItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
												
				return ClientiLayout.isNespresso();
			}
		});	
		

		odaCigItem = new TextItem("odaCig", I18NUtil.getMessages().clienti_detail_odaCigItem_title());
		odaCigItem.setWidth(200);
		odaCigItem.setStartRow(true);
		
	
		odaCupItem = new TextItem("odaCup", I18NUtil.getMessages().clienti_detail_odaCupItem_title());
		odaCupItem.setWidth(200);
		
		
		emailPecItem = new TextItem("emailPec", I18NUtil.getMessages().clienti_detail_emailPecItem_title());
		emailPecItem.setWidth(200);
		emailPecItem.setStartRow(true);
		
		soggettiForm.setItems(
				idSoggettoItem, 
				idClienteItem,
				flgDiSistemaItem,			
				flgValidoItem,
				flgIgnoreWarningItem,
				flgInOrganigrammaItem,
				tipoItem,
				cognomeItem, 
				nomeItem, 
				titoloItem,
				codiceRapidoItem,
				denominazioneItem,
				codiceIpaItem,
				codiceFiscaleItem, 
				partitaIvaItem,
				sessoItem,
				cittadinanzaItem,
				condizioneGiuridicaItem,
				esigibilitaIvaItem,
				odaCigItem,
				odaCupItem,
				emailPecItem
				
		);
		soggettiSection  = new DetailSection(I18NUtil.getMessages().clienti_detail_soggettiSection_title(), true, true, false, soggettiForm);
		addMember(soggettiSection);
	}

	// Sezione NASCITA ISTITUZIONE
	private void  buildNascitaIstituzioneSection(){
		
		nascitaIstituzioneForm = new DynamicForm();
		nascitaIstituzioneForm.setValuesManager(vm);
		nascitaIstituzioneForm.setWidth("100%");  
		nascitaIstituzioneForm.setHeight("5");  
		nascitaIstituzioneForm.setPadding(5);	
		nascitaIstituzioneForm.setNumCols(12);		
		nascitaIstituzioneForm.setWrapItemTitles(false);
		dataNascitaIstituzioneItem = new DateItem("dataNascitaIstituzione",   I18NUtil.getMessages().clienti_detail_dataNascitaIstituzioneItem_title());
		statoNascitaIstituzioneItem = new FilteredSelectItem("statoNascitaIstituzione", I18NUtil.getMessages().clienti_detail_statoNascitaIstituzioneItem_title()) {
			@Override
			protected void clearSelect() {
				super.clearSelect();
				comuneNascitaIstituzioneItem.setValue("");
				nomeComuneNascitaIstituzioneItem.setValue("");
				provNascitaIstituzioneItem.setValue("");
				cittaNascitaIstituzioneItem.setValue("");	
				nascitaIstituzioneForm.redraw();
			};		
		};
		ListGridField codIstatStatoNascitaIstituzioneField = new ListGridField("codIstatStato", "Cod. Istat");	
		codIstatStatoNascitaIstituzioneField.setHidden(true);
		ListGridField nomeStatoNascitaIstituzioneField = new ListGridField("nomeStato", "Stato");		
		statoNascitaIstituzioneItem.setPickListFields(codIstatStatoNascitaIstituzioneField, nomeStatoNascitaIstituzioneField);
		statoNascitaIstituzioneItem.setValueField("codIstatStato");  
		statoNascitaIstituzioneItem.setDisplayField("nomeStato");
		final GWTRestDataSource statoNascitaIstituzioneDS = new GWTRestDataSource("StatoDataSource", "codIstatStato", FieldType.TEXT, true);
		statoNascitaIstituzioneItem.setOptionDataSource(statoNascitaIstituzioneDS); 
		statoNascitaIstituzioneItem.setWidth(220);
		statoNascitaIstituzioneItem.setClearable(true);		
		statoNascitaIstituzioneItem.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
										
				nascitaIstituzioneForm.redraw();
			}
		});	
		nomeComuneNascitaIstituzioneItem = new HiddenItem("nomeComuneNascitaIstituzione");
		comuneNascitaIstituzioneItem = new FilteredSelectItem("comuneNascitaIstituzione", I18NUtil.getMessages().clienti_detail_comuneNascitaIstituzioneItem_title()) {
			@Override
			public void onOptionClick(Record record) {
								
				nomeComuneNascitaIstituzioneItem.setValue(record.getAttribute("nomeComune"));
				GWTRestDataSource comuneNascitaIstituzioneDS = (GWTRestDataSource) comuneNascitaIstituzioneItem.getOptionDataSource();
				comuneNascitaIstituzioneDS.addParam("nomeComune", record.getAttribute("nomeComune"));						
				comuneNascitaIstituzioneItem.setOptionDataSource(comuneNascitaIstituzioneDS);
				provNascitaIstituzioneItem.setValue(record.getAttribute("targaProvincia"));					
			}
			@Override
			public void setValue(String value) {
				
				super.setValue(value);				
				if(value == null || "".equals(value)) {
					nomeComuneNascitaIstituzioneItem.setValue("");
					GWTRestDataSource comuneNascitaIstituzioneDS = (GWTRestDataSource) comuneNascitaIstituzioneItem.getOptionDataSource();
					comuneNascitaIstituzioneDS.addParam("nomeComune", null);						
					comuneNascitaIstituzioneItem.setOptionDataSource(comuneNascitaIstituzioneDS);
					provNascitaIstituzioneItem.setValue("");					
				}
			}
		};
		ListGridField codIstatComuneNascitaIstituzioneField = new ListGridField("codIstatComune", "Cod. Istat");	
		codIstatComuneNascitaIstituzioneField.setHidden(true);
		ListGridField nomeComuneNascitaIstituzioneField = new ListGridField("nomeComune", "Comune");
		ListGridField targaProvinciaNascitaIstituzioneField = new ListGridField("targaProvincia", "Prov.");	
		targaProvinciaNascitaIstituzioneField.setWidth(50);
		comuneNascitaIstituzioneItem.setPickListFields(codIstatComuneNascitaIstituzioneField, nomeComuneNascitaIstituzioneField, targaProvinciaNascitaIstituzioneField);
		comuneNascitaIstituzioneItem.setEmptyPickListMessage(I18NUtil.getMessages().clienti_detail_indirizzi_comuneItem_noSearchOrEmptyMessage());
		comuneNascitaIstituzioneItem.setValueField("codIstatComune");  
		comuneNascitaIstituzioneItem.setDisplayField("nomeComune");
		final GWTRestDataSource comuneNascitaIstituzioneDS = new GWTRestDataSource("ComuneDataSource", "codIstatComune", FieldType.TEXT, true);
		comuneNascitaIstituzioneItem.setOptionDataSource(comuneNascitaIstituzioneDS); 
		comuneNascitaIstituzioneItem.setWidth(220);
		comuneNascitaIstituzioneItem.setClearable(true);
		comuneNascitaIstituzioneItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String stato = ((statoNascitaIstituzioneItem.getValue() != null) ? (String) statoNascitaIstituzioneItem.getValue() : "");
				return !"".equals(stato) && "200".equals(stato);				
			}
		});		
		provNascitaIstituzioneItem = new TextItem("provNascitaIstituzione",I18NUtil.getMessages().clienti_detail_provNascitaIstituzioneItem_title());
		provNascitaIstituzioneItem.setWidth(50);	
		provNascitaIstituzioneItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String stato = ((statoNascitaIstituzioneItem.getValue() != null) ? (String) statoNascitaIstituzioneItem.getValue() : "");
				return !"".equals(stato) && "200".equals(stato);				
			}
		});
		cittaNascitaIstituzioneItem = new TextItem("cittaNascitaIstituzione", I18NUtil.getMessages().clienti_detail_cittaNascitaIstituzioneItem_title());		
		cittaNascitaIstituzioneItem.setWidth(250);
		cittaNascitaIstituzioneItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String stato = ((statoNascitaIstituzioneItem.getValue() != null) ? (String) statoNascitaIstituzioneItem.getValue() : "");
				return !"".equals(stato) && !"200".equals(stato);
			}
		});			
		nascitaIstituzioneForm.setItems(
				dataNascitaIstituzioneItem,				
				statoNascitaIstituzioneItem,
				nomeComuneNascitaIstituzioneItem,
				comuneNascitaIstituzioneItem,
				provNascitaIstituzioneItem,
				cittaNascitaIstituzioneItem				
		);
		nascitaIstituzioneSection = new DetailSection(I18NUtil.getMessages().clienti_detail_istituzioneSection_title(), true, true, false, nascitaIstituzioneForm);
		addMember(nascitaIstituzioneSection);
	}
	
	// Sezione CESSAZIONE
	private void  buildCessazioneSection(){
		
		cessazioneForm = new DynamicForm();
		cessazioneForm.setValuesManager(vm);
		cessazioneForm.setWidth("100%");  
		cessazioneForm.setHeight("5");  
		cessazioneForm.setPadding(5);		
		cessazioneForm.setNumCols(12);		
		cessazioneForm.setWrapItemTitles(false);
		dataCessazioneItem    = new DateItem("dataCessazione", I18NUtil.getMessages().clienti_detail_dataCessazioneItem_title());
		causaleCessazioneItem = new SelectItem("causaleCessazione", I18NUtil.getMessages().clienti_detail_causaleCessazioneItem_title());
		causaleCessazioneItem.setValueField("key");  
		causaleCessazioneItem.setDisplayField("value");
		final GWTRestDataSource causaleCessazioneDS = new GWTRestDataSource("CausaleCessazioneSoggettoDataSource", "key", FieldType.TEXT);		
		causaleCessazioneItem.setOptionDataSource(causaleCessazioneDS); 		
		causaleCessazioneItem.setWidth(200);	
		causaleCessazioneItem.setClearable(true);
		causaleCessazioneItem.setCachePickListResults(false);
		causaleCessazioneItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {				
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				if(idSoggettoItem.getValue() != null && !"".equals((String) idSoggettoItem.getValue())) {
					Criterion[] criterias = new Criterion[1];	
					criterias[0] = new Criterion("idSoggetto", OperatorId.EQUALS, (String) idSoggettoItem.getValue());	
					return new AdvancedCriteria(OperatorId.AND, criterias);
				} 
				return null;
			}
		});
		cessazioneForm.setItems(
				dataCessazioneItem,
				causaleCessazioneItem
		);
		cessazioneSection = new DetailSection(I18NUtil.getMessages().clienti_detail_cessazioneSection_title(), true, false, false, cessazioneForm);
		addMember(cessazioneSection);
	}
	
	// Sezione INDIRIZZI
	private void  buildIndirizziSection(){
		
		indirizziForm = new DynamicForm();
		indirizziForm.setValuesManager(vm);
		indirizziForm.setWidth("100%");  
		indirizziForm.setHeight("5");  
		indirizziForm.setPadding(5);
		indirizziForm.setWrapItemTitles(false);
		indirizziItem = new IndirizziSoggettoItem() {	
			@Override
			public String getFlgPersonaFisica() {
				
				return _instance.isPersonaFisica() ? "1" : "";
			}
		};
		indirizziItem.setName("listaIndirizzi");
		indirizziItem.setShowTitle(false);
		indirizziForm.setFields(indirizziItem);
		indirizziSection          = new DetailSection(I18NUtil.getMessages().clienti_detail_indirizziSection_title(), true, true, false, indirizziForm);
		addMember(indirizziSection);
	}
	
	// Sezione CONTATTI
	private void  buildContattiSection(){
		
		contattiForm = new DynamicForm();
		contattiForm.setValuesManager(vm);
		contattiForm.setWidth("100%");  
		contattiForm.setHeight("5");  
		contattiForm.setPadding(5);
		contattiForm.setWrapItemTitles(false);
		contattiItem = new ContattiSoggettoItem() {
			@Override
			public boolean isNewMode() {
				
				return (getLayout() == null || (getLayout().getMode() != null && "new".equals(getLayout().getMode())));
			}
		};
		contattiItem.setName("listaContatti");
		contattiItem.setShowTitle(false);
		contattiForm.setFields(contattiItem);
		contattiSection           = new DetailSection(I18NUtil.getMessages().clienti_detail_contattiSection_title(), true, true, false, contattiForm);
		addMember(contattiSection);
	}
	
	// Sezione ALTRE DENOMINAZIONI
	private void  buildAltreDenominazioniSection(){
		
		altreDenominazioniForm = new DynamicForm();
		altreDenominazioniForm.setValuesManager(vm);
		altreDenominazioniForm.setWidth("100%");  
		altreDenominazioniForm.setHeight("5");  
		altreDenominazioniForm.setPadding(5);
		altreDenominazioniForm.setWrapItemTitles(false);
		altreDenominazioniItem = new AltreDenominazioniSoggettoItem();
		altreDenominazioniItem.setName("listaAltreDenominazioni");
		altreDenominazioniItem.setShowTitle(false);
		altreDenominazioniForm.setFields(altreDenominazioniItem);
		altreDenominazioniSection = new DetailSection(I18NUtil.getMessages().clienti_detail_altreDenominazioniSection_title(), true, false, false, altreDenominazioniForm);
		addMember(altreDenominazioniSection);
	}
	
	// Sezione DATI PAGAMENTO
	private void  buildDatiPagamentoSection(){
		
		datiPagamentoForm = new DynamicForm();
		datiPagamentoForm.setValuesManager(vm);
		datiPagamentoForm.setWidth("100%");  
		datiPagamentoForm.setHeight("5");  
		datiPagamentoForm.setPadding(5);
		datiPagamentoForm.setWrapItemTitles(false);
		datiPagamentoForm.setNumCols(6);		
		beneficiarioItem = new TextItem("beneficiario", I18NUtil.getMessages().clienti_detail_beneficiarioItem_title());
		beneficiarioItem.setWidth(200);
		beneficiarioItem.setStartRow(true);
		istitutoFinanziarioItem = new TextItem("istitutoFinanziario", I18NUtil.getMessages().clienti_detail_istitutoFinanziarioItem_title());
		istitutoFinanziarioItem.setWidth(455);
		istitutoFinanziarioItem.setColSpan(3);
		ibanItem = new TextItem("iban", I18NUtil.getMessages().clienti_detail_ibanItem_title());
		ibanItem.setWidth(200);
		ibanItem.setStartRow(true);
		abiItem = new TextItem("abi", I18NUtil.getMessages().clienti_detail_abiItem_title());
		abiItem.setWidth(200);
		cabItem = new TextItem("cab", I18NUtil.getMessages().clienti_detail_cabItem_title());
		cabItem.setWidth(200);
	    datiPagamentoForm.setItems(
	    		                   beneficiarioItem,
	    		                   istitutoFinanziarioItem,
	    		                   ibanItem,
	    		                   abiItem,
	    		                   cabItem
		);
		datiPagamentoSection = new DetailSection(I18NUtil.getMessages().clienti_detail_datiPagamentoSection_title(), true, true, false, datiPagamentoForm);
		
		
		addMember(datiPagamentoSection);
	}
	
	public boolean isPersonaFisica() {
		String tipo = tipoItem.getValueAsString() != null ? tipoItem.getValueAsString() : "";
		return tipo.equals("UP") || tipo.equals("#AF");
	}
	
	public boolean isPersonaFisica(Record record) {
		String tipo = (record != null && record.getAttribute("tipo") != null) ? record.getAttributeAsString("tipo") : "";		
		return tipo.equals("UP") || tipo.equals("#AF");
	}
	
	@Override
	public void editNewRecord() {	
		
		GWTRestDataSource cittadinanzaDS = (GWTRestDataSource) cittadinanzaItem.getOptionDataSource();
		cittadinanzaDS.addParam("codIstatStato", null);										
		cittadinanzaItem.setOptionDataSource(cittadinanzaDS);
		
		GWTRestDataSource condizioneGiuridicaSoggettoDS = (GWTRestDataSource) condizioneGiuridicaItem.getOptionDataSource();
		condizioneGiuridicaSoggettoDS.addParam("idSoggetto", null);										
		condizioneGiuridicaItem.setOptionDataSource(condizioneGiuridicaSoggettoDS);
		
		GWTRestDataSource comuneNascitaIstituzioneDS = (GWTRestDataSource) comuneNascitaIstituzioneItem.getOptionDataSource();
		comuneNascitaIstituzioneDS.addParam("codIstatComune", null);										
		comuneNascitaIstituzioneDS.addParam("nomeComune", null);										
		comuneNascitaIstituzioneItem.setOptionDataSource(comuneNascitaIstituzioneDS);
		
		GWTRestDataSource statoNascitaIstituzioneDS = (GWTRestDataSource) statoNascitaIstituzioneItem.getOptionDataSource();
		statoNascitaIstituzioneDS.addParam("codIstatStato", null);										
		statoNascitaIstituzioneItem.setOptionDataSource(statoNascitaIstituzioneDS);
		
		GWTRestDataSource causaleCessazioneDS = (GWTRestDataSource) causaleCessazioneItem.getOptionDataSource();
		causaleCessazioneDS.addParam("idSoggetto", null);										
		causaleCessazioneItem.setOptionDataSource(causaleCessazioneDS);
		
		super.editNewRecord();
	}
	
	@Override
	public void editRecord(Record record) {
				
		String idDominio = AurigaLayout.getIdDominio();
		boolean gestPA = AurigaLayout.getParametroDBAsBoolean("GEST_PA_IN_RUBRICA_DA_SP_AOO");
		LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
		if(idDominio == null || "".equals(idDominio) || gestPA) 
		{
			styleMap.put("PA+", I18NUtil.getMessages().clienti_tipo_APA_value());
		}
		styleMap.put("#AF", I18NUtil.getMessages().clienti_tipo_AF_value());
		styleMap.put("#AG", I18NUtil.getMessages().clienti_tipo_AG_value());		
		tipoItem.setValueMap(styleMap);
		tipoItem.setValue("PA+");

		GWTRestDataSource cittadinanzaDS = (GWTRestDataSource) cittadinanzaItem.getOptionDataSource();
		if(record.getAttribute("cittadinanza") != null && !"".equals(record.getAttributeAsString("cittadinanza"))) {
			cittadinanzaDS.addParam("codIstatStato", record.getAttributeAsString("cittadinanza"));										
		} else {
			cittadinanzaDS.addParam("codIstatStato", null);										
		}
		cittadinanzaItem.setOptionDataSource(cittadinanzaDS);
		GWTRestDataSource condizioneGiuridicaSoggettoDS = (GWTRestDataSource) condizioneGiuridicaItem.getOptionDataSource();
		if(record.getAttribute("idSoggetto") != null && !"".equals(record.getAttributeAsString("idSoggetto"))) {
			condizioneGiuridicaSoggettoDS.addParam("idSoggetto", record.getAttributeAsString("idSoggetto"));										
		} else {
			condizioneGiuridicaSoggettoDS.addParam("idSoggetto", null);										
		}
		condizioneGiuridicaItem.setOptionDataSource(condizioneGiuridicaSoggettoDS);
		GWTRestDataSource comuneNascitaIstituzioneDS = (GWTRestDataSource) comuneNascitaIstituzioneItem.getOptionDataSource();
		if(record.getAttribute("comuneNascitaIstituzione") != null && !"".equals(record.getAttributeAsString("comuneNascitaIstituzione"))) {
			comuneNascitaIstituzioneDS.addParam("codIstatComune", record.getAttributeAsString("comuneNascitaIstituzione"));		
			comuneNascitaIstituzioneDS.addParam("nomeComune", record.getAttributeAsString("nomeComuneNascitaIstituzione"));		
		} else {
			comuneNascitaIstituzioneDS.addParam("codIstatComune", null);		
			comuneNascitaIstituzioneDS.addParam("nomeComune", null);
		}
		comuneNascitaIstituzioneItem.setOptionDataSource(comuneNascitaIstituzioneDS);
		GWTRestDataSource statoNascitaIstituzioneDS = (GWTRestDataSource) statoNascitaIstituzioneItem.getOptionDataSource();
		if(record.getAttribute("statoNascitaIstituzione") != null && !"".equals(record.getAttributeAsString("statoNascitaIstituzione"))) {
			statoNascitaIstituzioneDS.addParam("codIstatStato", record.getAttributeAsString("statoNascitaIstituzione"));										
		} else {
			statoNascitaIstituzioneDS.addParam("codIstatStato", null);										
		}
		statoNascitaIstituzioneItem.setOptionDataSource(statoNascitaIstituzioneDS);
		GWTRestDataSource causaleCessazioneDS = (GWTRestDataSource) causaleCessazioneItem.getOptionDataSource();
		if(record.getAttribute("idSoggetto") != null && !"".equals(record.getAttributeAsString("idSoggetto"))) {
			causaleCessazioneDS.addParam("idSoggetto", record.getAttributeAsString("idSoggetto"));										
		} else {
			causaleCessazioneDS.addParam("idSoggetto", null);										
		}
		causaleCessazioneItem.setOptionDataSource(causaleCessazioneDS);
		
		if(isPersonaFisica(record)) {
			nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().clienti_detail_nascitaSection_title());
		} else {
			nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().clienti_detail_istituzioneSection_title());
		}	
		
		super.editRecord(record);	
		
		markForRedraw();
	}
	
	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		for(DynamicForm form : vm.getMembers()) {
			for(FormItem item : form.getFields()) {	 		
				if(!(item instanceof HeaderItem) && 
						!(item instanceof ImgButtonItem) && 
						!(item instanceof TitleItem)) {					
					if(item instanceof DateItem || item instanceof DateTimeItem) {
						TextItem textItem = new TextItem();
						textItem.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
						if(item instanceof DateItem) {
							((DateItem)item).setTextFieldProperties(textItem);
						} else if(item instanceof DateTimeItem) {
							((DateTimeItem)item).setTextFieldProperties(textItem);
						}							
						item.redraw();
					} else {
						item.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
					}						
					item.setCanEdit(canEdit);					
				}		
				if(item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
				}
			}
		}
		provNascitaIstituzioneItem.setCanEdit(false);	
	}
	
	public void salvaDati(final ServiceCallback<Record> callback) {
		final Record detailRecord = new Record(vm.getValues());					
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");	
		GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("AnagraficaClientiDataSource");
		lGWTRestService.call(detailRecord, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				if(callback != null) {
					callback.execute(object);
				} else {
					Layout.hideWaitPopup();							
				}
			}
		});
	}
	
	public String getNomeEntita() {
		return nomeEntita;
	}

	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}
}