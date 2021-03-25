package it.eng.auriga.ui.module.layout.client.tipoFascicoliAggr;

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.defattivitaprocedimenti.AttributiAddXEventiDelTipoItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.titolario.LookupTitolarioPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author cristiano
 *
 */

public class TipoFascicoliAggregatiDetail extends CustomDetail {

	protected DynamicForm formTipologiaFascicoliAggregati;
	private HiddenItem idFolderTypeItem;
	private TextItem nomeItem;
	private TextAreaItem annotazioniItem;
	private FilteredSelectItem idFolderTypeGenItem;
	private HiddenItem nomeFolderTypeGenItem;
	private FilteredSelectItem idProcessTypeItem;
	private HiddenItem nomeProcessTypeItem;
	
	protected DetailSection detailSectionTemplateTimbro;
	protected DynamicForm formTemplate;
	private TextItem templateNomeItem;
	private TextItem templateTimbroItem;
	private TextItem templateCodiceItem;

	private CheckboxItem flgConservPermItem;
	private NumericItem periodoConservInAnniItem;

	protected DynamicForm formAbilitazioni;
	private CheckboxItem flgRichAbilXVisualizzItem;
	private CheckboxItem flgRichAbilXGestItem;
	private CheckboxItem flgRichAbilXAssegnItem;

	protected DetailSection detailSectionClassificazione;
	protected DynamicForm formClassificazione;
	private TextItem livelliClassificazioneItem;
	private HiddenItem desClassificazioneItem;
	private FilteredSelectItemWithDisplay idClassificazioneItem;
	private ImgButtonItem lookupTitolarioButton;

	protected DetailSection detailSectionAttributiCustom;
	protected DynamicForm formMetadatiSpecifici;
	protected AttributiAddXEventiDelTipoItem defAttivitaProcedimentiItem;

	public TipoFascicoliAggregatiDetail(String nomeEntita) {
		super(nomeEntita);

		setInitValues();

		setAbilValues();

		setClassificazione();

		setTemplate();

		setAttrCustValues();

		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);

		detailSectionClassificazione = new DetailSection("Classificazione", true, true, false, formClassificazione);

		detailSectionTemplateTimbro = new DetailSection("Template", true, true, false, formTemplate);

		detailSectionAttributiCustom = new DetailSection(I18NUtil.getMessages().tipofascicoliaggr_detail_section_attr_custom(), true, true, false,
				formMetadatiSpecifici);

		lVLayout.addMember(formTipologiaFascicoliAggregati);
		lVLayout.addMember(formAbilitazioni);
		lVLayout.addMember(detailSectionClassificazione);
		lVLayout.addMember(detailSectionTemplateTimbro);
		lVLayout.addMember(detailSectionAttributiCustom);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}

	private void setInitValues() {

		formTipologiaFascicoliAggregati = new DynamicForm();
		formTipologiaFascicoliAggregati.setValuesManager(vm);
		formTipologiaFascicoliAggregati.setHeight("5");
		formTipologiaFascicoliAggregati.setPadding(5);
		formTipologiaFascicoliAggregati.setWrapItemTitles(false);
		formTipologiaFascicoliAggregati.setNumCols(15);
		formTipologiaFascicoliAggregati.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		idFolderTypeItem = new HiddenItem("idFolderType");

		nomeItem = new TextItem("nome", I18NUtil.getMessages().tipofascicoliaggr_detail_nome());
		nomeItem.setWidth(350);
		nomeItem.setRequired(true);
		nomeItem.setStartRow(true);
		nomeItem.setColSpan(14);

		annotazioniItem = new TextAreaItem("annotazioni", I18NUtil.getMessages().tipofascicoliaggr_detail_annotazioni());
		annotazioniItem.setStartRow(true);
		annotazioniItem.setLength(4000);
		annotazioniItem.setHeight(60);
		annotazioniItem.setWidth(650);
		annotazioniItem.setColSpan(14);

		nomeFolderTypeGenItem = new HiddenItem("nomeFolderTypeGen");

		GWTRestDataSource folderTypesGenDS = new GWTRestDataSource("LoadComboTipiFascAggrDataSource", "idFascAggrTypePadre", FieldType.TEXT, true);
		ListGridField idFascAggrTypePadreField = new ListGridField("idFascAggrTypePadre", "Id.");
		idFascAggrTypePadreField.setHidden(true);
		ListGridField nomeFascAggrTypePadreField = new ListGridField("nomeFascAggrTypePadre", "Nome");
		idFolderTypeGenItem = new FilteredSelectItem("idFolderTypeGen", I18NUtil.getMessages().tipofascicoliaggr_detail_idFolderTypeGen()) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				formTipologiaFascicoliAggregati.setValue("nomeFolderTypeGen", record.getAttribute("nomeFascAggrTypePadre"));
				markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				formTipologiaFascicoliAggregati.setValue("nomeFolderTypeGen", "");
				markForRedraw();
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					formTipologiaFascicoliAggregati.setValue("nomeFolderTypeGen", "");
				}
				markForRedraw();
            }

		};
		idFolderTypeGenItem.setPickListFields(idFascAggrTypePadreField, nomeFascAggrTypePadreField);
		idFolderTypeGenItem.setWidth(450);
		idFolderTypeGenItem.setStartRow(true);
		idFolderTypeGenItem.setClearable(true);
		idFolderTypeGenItem.setValueField("idFascAggrTypePadre");
		idFolderTypeGenItem.setDisplayField("nomeFascAggrTypePadre");
		idFolderTypeGenItem.setColSpan(14);
		idFolderTypeGenItem.setAllowEmptyValue(true);
		idFolderTypeGenItem.setOptionDataSource(folderTypesGenDS);
		idFolderTypeGenItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		nomeProcessTypeItem = new HiddenItem("nomeProcessType");
		
		GWTRestDataSource processTypesDS = new GWTRestDataSource("LoadComboTipiProcDataSource", "idProcessType", FieldType.TEXT, true);
		ListGridField keyField = new ListGridField("key", "Id.");
		keyField.setHidden(true);
		ListGridField valueField = new ListGridField("value", "Nome");
		idProcessTypeItem = new FilteredSelectItem("idProcessType", I18NUtil.getMessages().tipofascicoliaggr_detail_idProcessType()) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				formTipologiaFascicoliAggregati.setValue("nomeProcessType", record.getAttribute("value"));
				markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				formTipologiaFascicoliAggregati.setValue("nomeProcessType", "");
				markForRedraw();
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					formTipologiaFascicoliAggregati.setValue("nomeProcessType", "");
				}
				markForRedraw();
            }

		};
		idProcessTypeItem.setPickListFields(keyField, valueField);
		idProcessTypeItem.setWidth(450);
		idProcessTypeItem.setStartRow(true);
		idProcessTypeItem.setClearable(true);
		idProcessTypeItem.setValueField("key");
		idProcessTypeItem.setDisplayField("value");
		idProcessTypeItem.setColSpan(14);
		idProcessTypeItem.setAllowEmptyValue(true);
		idProcessTypeItem.setOptionDataSource(processTypesDS);
		idProcessTypeItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});		

		formTipologiaFascicoliAggregati.setItems(idFolderTypeItem, nomeItem, annotazioniItem, idFolderTypeGenItem, nomeFolderTypeGenItem, idProcessTypeItem, nomeProcessTypeItem);
	}

	private void setAbilValues() {

		formAbilitazioni = new DynamicForm();
		formAbilitazioni.setValuesManager(vm);
		formAbilitazioni.setHeight("5");
		formAbilitazioni.setPadding(5);
		formAbilitazioni.setWrapItemTitles(false);
		formAbilitazioni.setNumCols(15);
		formAbilitazioni.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		SpacerItem spacer = new SpacerItem();
		spacer.setColSpan(2);

		flgRichAbilXVisualizzItem = new CheckboxItem("flgRichAbilXVisualizz", I18NUtil.getMessages().tipofascicoliaggr_detail_flgRichAbilXVisualizz());
		flgRichAbilXVisualizzItem.setColSpan(1);
		flgRichAbilXVisualizzItem.setWidth("*");

		flgRichAbilXGestItem = new CheckboxItem("flgRichAbilXGest", I18NUtil.getMessages().tipofascicoliaggr_detail_flgRichAbilXGest());
		flgRichAbilXGestItem.setWidth("*");
		flgRichAbilXGestItem.setColSpan(1);

		flgRichAbilXAssegnItem = new CheckboxItem("flgRichAbilXAssegn", I18NUtil.getMessages().tipofascicoliaggr_detail_flgRichAbilXAssegn());
		flgRichAbilXAssegnItem.setWidth("*");
		flgRichAbilXAssegnItem.setColSpan(1);

		LinkedHashMap<String, Boolean> flgValueMap = new LinkedHashMap<String, Boolean>();
		flgValueMap.put("1", true);
		flgValueMap.put("0", false);
		flgValueMap.put("", false);
		flgValueMap.put(null, false);

		flgConservPermItem = new CheckboxItem("flgConservPerm", I18NUtil.getMessages().tipofascicoliaggr_detail_flgConservPerm());
		flgConservPermItem.setValueMap(flgValueMap);
		flgConservPermItem.setColSpan(1);
		flgConservPermItem.setWidth("*");
		flgConservPermItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				formAbilitazioni.redraw();
			}
		});

		periodoConservInAnniItem = new NumericItem("periodoConservInAnni", I18NUtil.getMessages().tipofascicoliaggr_detail_periodoConservInAnni());
		periodoConservInAnniItem.setColSpan(1);
		periodoConservInAnniItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				Boolean flgConservIllimitata = formAbilitazioni.getValue("flgConservPerm") != null && (Boolean) formAbilitazioni.getValue("flgConservPerm");
				return flgConservIllimitata == null || !flgConservIllimitata;
			}
		});

		formAbilitazioni
				.setItems(spacer, flgRichAbilXVisualizzItem, flgRichAbilXGestItem, flgRichAbilXAssegnItem, flgConservPermItem, periodoConservInAnniItem);
	}

	private void setClassificazione() {

		formClassificazione = new DynamicForm();
		formClassificazione.setValuesManager(vm);
		formClassificazione.setHeight("5");
		formClassificazione.setPadding(5);
		formClassificazione.setWrapItemTitles(false);
		formClassificazione.setNumCols(15);
		formClassificazione.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		livelliClassificazioneItem = new TextItem("livelliClassificazione", I18NUtil.getMessages().tipofascicoliaggr_detail_livelliClassificazione());
		livelliClassificazioneItem.setWidth(100);
		livelliClassificazioneItem.setColSpan(1);
		// livelliClassificazioneItem.setRequired(true);
		livelliClassificazioneItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				if (livelliClassificazioneItem.getValue() != null && !"".equals(livelliClassificazioneItem.getValueAsString().trim())
						&& desClassificazioneItem.getValue() == null) {
					return false;
				}
				return true;
			}
		});
		livelliClassificazioneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				manageIndiceChange();
			}
		});

		// BOTTONI : seleziona dal titolario
		lookupTitolarioButton = new ImgButtonItem("lookupTitolarioButton", "lookup/titolario.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupTitolarioButton_prompt());
		lookupTitolarioButton.setWidth(16);
		lookupTitolarioButton.setColSpan(1);
		lookupTitolarioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				final ClassificaFascicoloLookupTitolario lookupTitolarioPopup = new ClassificaFascicoloLookupTitolario(formClassificazione.getValuesAsRecord());
				lookupTitolarioPopup.show();
			}
		});

		desClassificazioneItem = new HiddenItem("desClassificazione");

		// lista classifiche assegnabili
		SelectGWTRestDataSource classificheDS = new SelectGWTRestDataSource("LoadComboClassificaDataSource", "idClassifica", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		idClassificazioneItem = new FilteredSelectItemWithDisplay("idClassificazione", classificheDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) idClassificazioneItem.getOptionDataSource();
				classificheDS.addParam("descrizione", record.getAttributeAsString("descrizione"));
				desClassificazioneItem.setOptionDataSource(classificheDS);
				formClassificazione.setValue("idClassificazione", record.getAttributeAsString("idClassifica"));
				formClassificazione.setValue("desClassificazione", record.getAttributeAsString("descrizione"));
				formClassificazione.setValue("livelliClassificazione", record.getAttributeAsString("indice"));
				formClassificazione.clearFieldErrors("indice", true);
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) idClassificazioneItem.getOptionDataSource();
				classificheDS.addParam("descrizione", null);
				desClassificazioneItem.setOptionDataSource(classificheDS);
				formClassificazione.setValue("idClassificazione", "");
				formClassificazione.setValue("desClassificazione", "");
				formClassificazione.setValue("livelliClassificazione", "");
				formClassificazione.clearFieldErrors("indice", true);
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) idClassificazioneItem.getOptionDataSource();
					classificheDS.addParam("descrizione", null);
					desClassificazioneItem.setOptionDataSource(classificheDS);
					formClassificazione.setValue("idClassificazione", "");
					formClassificazione.setValue("desClassificazione", "");
					formClassificazione.setValue("livelliClassificazione", "");
					formClassificazione.clearFieldErrors("indice", true);
				}
			}
		};
		idClassificazioneItem.setAutoFetchData(false);
		idClassificazioneItem.setFetchMissingValues(true);
		ListGridField indiceField = new ListGridField("indice", "Indice");
		indiceField.setWidth(100);
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		ListGridField descrizioneEstesaField = new ListGridField("descrizioneEstesa", "Descr. estesa");
		descrizioneEstesaField.setHidden(true);
		idClassificazioneItem.setPickListFields(indiceField, descrizioneField, descrizioneEstesaField);
		idClassificazioneItem.setEmptyPickListMessage(I18NUtil.getMessages().protocollazione_detail_classificazioneItem_noSearchOrEmptyMessage());
		idClassificazioneItem.setValueField("idClassifica");
		idClassificazioneItem.setOptionDataSource(classificheDS);
		idClassificazioneItem.setShowTitle(false);
		idClassificazioneItem.setClearable(true);
		idClassificazioneItem.setShowIcons(true);
		idClassificazioneItem.setWidth(500);
		// idClassificazioneItem.setRequired(true);
		idClassificazioneItem.setColSpan(11);

		formClassificazione.setItems(livelliClassificazioneItem, lookupTitolarioButton, idClassificazioneItem, desClassificazioneItem);

	}

	private void setTemplate() {

		formTemplate = new DynamicForm();
		formTemplate.setValuesManager(vm);
		formTemplate.setWidth("100%");
		formTemplate.setHeight("5");
		formTemplate.setPadding(5);
		formTemplate.setWrapItemTitles(false);
		formTemplate.setNumCols(12);
		formTemplate.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		templateNomeItem = new TextItem("templateNome", I18NUtil.getMessages().tipofascicoliaggr_detail_template_nome());
		templateNomeItem.setStartRow(true);
		templateNomeItem.setWidth(450);
		templateNomeItem.setColSpan(1);

		templateTimbroItem = new TextItem("templateTimbro", I18NUtil.getMessages().tipofascicoliaggr_detail_template_timbro());
		templateTimbroItem.setWidth(450);
		templateTimbroItem.setColSpan(1);
		templateTimbroItem.setStartRow(true);

		templateCodiceItem = new TextItem("templateCode", I18NUtil.getMessages().tipofascicoliaggr_detail_template_codid());
		templateCodiceItem.setWidth(450);
		templateCodiceItem.setStartRow(true);
		templateCodiceItem.setColSpan(1);

		formTemplate.setItems(templateNomeItem, templateTimbroItem, templateCodiceItem);
	}

	private void setAttrCustValues() {

		formMetadatiSpecifici = new DynamicForm();
		formMetadatiSpecifici.setValuesManager(vm);
		formMetadatiSpecifici.setWidth("100%");
		formMetadatiSpecifici.setHeight("5");
		formMetadatiSpecifici.setPadding(5);
		formMetadatiSpecifici.setWrapItemTitles(false);
		formMetadatiSpecifici.setNumCols(12);
		formMetadatiSpecifici.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		defAttivitaProcedimentiItem = new AttributiAddXEventiDelTipoItem();
		defAttivitaProcedimentiItem.setName("listaAttributiAddizionali");
		defAttivitaProcedimentiItem.setShowTitle(false);
		defAttivitaProcedimentiItem.setOrdinabile(true);

		formMetadatiSpecifici.setFields(defAttivitaProcedimentiItem);
	}

	@Override
	public void editNewRecord(Map initialValues) {
		
		super.editNewRecord(initialValues);
		reloadComboFromRecord(new Record(initialValues));
	}

	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
		reloadComboFromRecord(record);
		
		if (record.getAttribute("idClassificazione") != null && !"".equals(record.getAttributeAsString("idClassificazione"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idClassificazione"), record.getAttribute("desClassificazione"));
			idClassificazioneItem.setValueMap(valueMap);
		}
		if (record.getAttribute("idFolderTypeGen") != null && !"".equals(record.getAttributeAsString("idFolderTypeGen"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idFolderTypeGen"), record.getAttribute("nomeFolderTypeGen"));
			idFolderTypeGenItem.setValueMap(valueMap);
		}
		if (record.getAttribute("idProcessType") != null && !"".equals(record.getAttributeAsString("idProcessType"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idProcessType"), record.getAttribute("nomeProcessType"));
			idProcessTypeItem.setValueMap(valueMap);
		}
	}

	private void reloadComboFromRecord(Record record) {

		GWTRestDataSource classificheDS = (GWTRestDataSource) idClassificazioneItem.getOptionDataSource();		
		if (record.getAttribute("idClassificazione") != null && !"".equals(record.getAttribute("idClassificazione"))) {
			classificheDS.addParam("idClassifica", (String) record.getAttribute("idClassificazione"));
		} else {
			classificheDS.addParam("idClassifica", null);
		}
		idClassificazioneItem.setOptionDataSource(classificheDS);
		idClassificazioneItem.fetchData();
		
		GWTRestDataSource folderTypesGenDS = (GWTRestDataSource) idFolderTypeGenItem.getOptionDataSource();		
		if (record.getAttribute("idFolderType") != null && !"".equals(record.getAttribute("idFolderType"))) {
			folderTypesGenDS.addParam("idFolderType", (String) record.getAttribute("idFolderType"));
		} else {
			folderTypesGenDS.addParam("idFolderType", null);
		}
		if (record.getAttribute("idFolderTypeGen") != null && !"".equals(record.getAttribute("idFolderTypeGen"))) {
			folderTypesGenDS.addParam("idFolderTypeGen", (String) record.getAttribute("idFolderTypeGen"));
		} else {
			folderTypesGenDS.addParam("idFolderTypeGen", null);
		}
		idFolderTypeGenItem.setOptionDataSource(folderTypesGenDS);
		idFolderTypeGenItem.fetchData();
		
		GWTRestDataSource processTypesDS = (GWTRestDataSource) idProcessTypeItem.getOptionDataSource();		
		if (record.getAttribute("idProcessType") != null && !"".equals(record.getAttribute("idProcessType"))) {
			processTypesDS.addParam("idProcessType", (String) record.getAttribute("idProcessType"));
		} else {
			processTypesDS.addParam("idProcessType", null);
		}
		idProcessTypeItem.setOptionDataSource(processTypesDS);
		idProcessTypeItem.fetchData();
	}

	public class ClassificaFascicoloLookupTitolario extends LookupTitolarioPopup {

		public ClassificaFascicoloLookupTitolario(Record record) {
			super(record, true);
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordTitolario(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}

	public void setFormValuesFromRecordTitolario(Record record) {
		
		formClassificazione.clearErrors(true);
		String idClassifica = record.getAttribute("idClassificazione");
		if (idClassifica == null || "".equals(idClassifica)) {
			idClassifica = record.getAttribute("idFolder");
		}
		formClassificazione.setValue("desClassificazione", record.getAttribute("descrizione"));
		formClassificazione.setValue("livelliClassificazione", record.getAttribute("indice"));
		formClassificazione.setValue("idClassificazione", idClassifica);

		manageIndiceChange();

		formClassificazione.markForRedraw();
	}

	protected void manageIndiceChange() {
		
		formClassificazione.setValue("idClassificazione", (String) null);
		formClassificazione.clearErrors(true);
		final String value = livelliClassificazioneItem.getValueAsString();
		SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) idClassificazioneItem.getOptionDataSource();
		classificheDS.addParam("indice", value);
		classificheDS.addParam("descrizione", null);
		idClassificazioneItem.setOptionDataSource(classificheDS);
		if (value != null && !"".equals(value)) {
			classificheDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String indice = data.get(i).getAttribute("indice");
							if (value.equals(indice)) {
								SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) idClassificazioneItem.getOptionDataSource();
								classificheDS.addParam("descrizione", data.get(i).getAttributeAsString("descrizione"));
								idClassificazioneItem.setOptionDataSource(classificheDS);

								formClassificazione.setValue("idClassificazione", data.get(i).getAttribute("idClassifica"));
								formClassificazione.setValue("desClassificazione", data.get(i).getAttributeAsString("descrizione"));
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						livelliClassificazioneItem.validate();
						livelliClassificazioneItem.blurItem();
					}
				}
			});
		}
	}

}
