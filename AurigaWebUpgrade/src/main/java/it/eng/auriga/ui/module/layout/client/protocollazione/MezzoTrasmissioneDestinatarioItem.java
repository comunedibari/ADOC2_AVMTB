package it.eng.auriga.ui.module.layout.client.protocollazione;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public abstract class MezzoTrasmissioneDestinatarioItem extends CanvasItem {

	private DynamicForm lDynamicForm;

	private HiddenItem civicoItem;
	private HiddenItem internoItem;
	private HiddenItem scalaItem;
	private HiddenItem pianoItem;
	private HiddenItem capItem;
	private HiddenItem frazioneItem;
	private HiddenItem codIstatComuneItem;
	private HiddenItem comuneItem;
	private HiddenItem codIstatStatoItem;
	private HiddenItem statoItem;
	private HiddenItem provinciaItem;
	private HiddenItem zonaItem;
	private HiddenItem complementoIndirizzoItem;
	private HiddenItem tipoToponimoItem;
	private HiddenItem ciToponimoItem;
	private HiddenItem appendiciItem;
	private HiddenItem idIndirizzoItem;
	private HiddenItem indirizzoItem;

	private HiddenItem descrizioneMezzoTrasmissioneDestinatarioItem;
	private HiddenItem descrizioneIndirizzoItem;

	private SelectItem mezzoTrasmissioneDestinatarioItem;
	private SelectItemWithDisplay indirizzoDestinatarioItem;

	// DateItem
	private DateItem dataRaccomandataDestinatarioItem;
	private DateItem dataNotificaDestinatarioItem;

	// TextItem
	private TextItem nroRaccomandataDestinatarioItem;
	private TextItem nroNotificaDestinatarioItem;

	/**
	 * In questo metodo devono essere inserite le condizioni tali per cui il componente MezzoTrasmissioneDestinatarioItem deve essere visualizzato nel form in
	 * cui è inserito. Tale metodo è necessario perchè la condizione di visibilità deve essere propagata a tutti gli item annidati nel
	 * MezzoTrasmissioneDestinatarioItem, altrimenti quando questo componente viene nascosto tramite lo la showIfCondition si verificano dei problemi di
	 * allineamento (alcuni item al suo interno occupano comunque lo spazione nella GUI, anche se non visualizzati).
	 * 
	 * @return true se il MezzoTrasmissioneDestinatarioItem deve essere visulizzato nel form in cui è inserito
	 */
	public abstract boolean showMezzoTrasmissioneItem();

	/**
	 * Crea un AttachmentItem. In fase di init, lo disegna e ne setta lo showHandler per gestire il setValue
	 * 
	 */
	public MezzoTrasmissioneDestinatarioItem() {
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {

			public void onInit(FormItem item) {
				// Inizializza il componente
				// buildObject(item.getJsObj()).disegna((Record) item.getValue());
				// Per qualche motivo l'istruzione precedente genera un record errato, facendo così invece funziona
				Record initRecord = item.getValue() != null ? new Record(((Record) item.getValue()).toMap()) : new Record();
				MezzoTrasmissioneDestinatarioItem jsObj = buildObject(item.getJsObj());
				jsObj.disegna(initRecord);
				
				// Setto lo showValue (Gestiste il setValue
				addShowValueHandler(new ShowValueHandler() {

					@Override
					public void onShowValue(ShowValueEvent event) {
						setValue(event.getDataValueAsRecord());
					}
				});
			}
		});
		setShouldSaveValue(true);

		// Setto la showIfCondition con l'implementazione del metodo astratto showMezzoTrasmissioneItem
		setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return showMezzoTrasmissioneItem();
			}
		});
	}

	protected void disegna(Record lRecord) {

		lDynamicForm = new DynamicForm();
		lDynamicForm.setWidth("100%");
		lDynamicForm.setNumCols(6);
		lDynamicForm.setColWidths("100", "*", "*", "*", "*", "*");

		lDynamicForm.setOverflow(Overflow.VISIBLE);
		// lDynamicForm.setCellBorder(1);

		idIndirizzoItem = new HiddenItem("idIndirizzo");
		indirizzoItem = new HiddenItem("indirizzo");
		civicoItem = new HiddenItem("civico");
		internoItem = new HiddenItem("interno");
		scalaItem = new HiddenItem("scala");
		pianoItem = new HiddenItem("piano");
		capItem = new HiddenItem("cap");
		frazioneItem = new HiddenItem("frazione");
		codIstatComuneItem = new HiddenItem("codIstatComune");
		comuneItem = new HiddenItem("comune");
		codIstatStatoItem = new HiddenItem("codIstatStato");
		statoItem = new HiddenItem("stato");
		provinciaItem = new HiddenItem("provincia");
		zonaItem = new HiddenItem("zona");
		complementoIndirizzoItem = new HiddenItem("complementoIndirizzo");
		tipoToponimoItem = new HiddenItem("tipoToponimo");
		ciToponimoItem = new HiddenItem("ciToponimo");
		appendiciItem = new HiddenItem("appendici");

		descrizioneMezzoTrasmissioneDestinatarioItem = new HiddenItem("descrizioneMezzoTrasmissioneDestinatario");
		descrizioneIndirizzoItem = new HiddenItem("descrizioneIndirizzo");

		// Mezzi di trasmissione
		GWTRestDataSource mezziTrasmissioneDS = new GWTRestDataSource("LoadComboMezziTrasmissioneDataSource", "key", FieldType.TEXT);
		mezziTrasmissioneDS.extraparam.put("idRegistrazione", null);
		mezzoTrasmissioneDestinatarioItem = new SelectItem("mezzoTrasmissioneDestinatario",
				I18NUtil.getMessages().protocollazione_detail_mezzoTrasmissioneItem_title());
		mezzoTrasmissioneDestinatarioItem.setOptionDataSource(mezziTrasmissioneDS);
		mezzoTrasmissioneDestinatarioItem.setAutoFetchData(false);
		mezzoTrasmissioneDestinatarioItem.setFetchMissingValues(false);
		mezzoTrasmissioneDestinatarioItem.setDisplayField("value");
		mezzoTrasmissioneDestinatarioItem.setValueField("key");
		mezzoTrasmissioneDestinatarioItem.setWrapTitle(false);
		mezzoTrasmissioneDestinatarioItem.setStartRow(true);
		mezzoTrasmissioneDestinatarioItem.setAllowEmptyValue(true);
		mezzoTrasmissioneDestinatarioItem.setClearable(false);
		mezzoTrasmissioneDestinatarioItem.setRedrawOnChange(true);
		mezzoTrasmissioneDestinatarioItem.setWidth(180);
		mezzoTrasmissioneDestinatarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				dataRaccomandataDestinatarioItem.setValue("");
				nroRaccomandataDestinatarioItem.setValue("");
				dataNotificaDestinatarioItem.setValue("");
				nroNotificaDestinatarioItem.setValue("");
			}
		});
		mezzoTrasmissioneDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showMezzoTrasmissioneItem();
			}
		});

		// Data raccomandata
		dataRaccomandataDestinatarioItem = new DateItem("dataRaccomandataDestinatario",
				I18NUtil.getMessages().protocollazione_detail_dataRaccomandataDestinatarioItem_title());
		dataRaccomandataDestinatarioItem.setWrapTitle(false);
		dataRaccomandataDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isRaccomandata(mezzoTrasmissioneDestinatarioItem.getValueAsString()) && showMezzoTrasmissioneItem();
			}
		});

		// Numero raccomandata
		nroRaccomandataDestinatarioItem = new TextItem("nroRaccomandataDestinatario",
				I18NUtil.getMessages().protocollazione_detail_nroRaccomandataDestinatarioItem_title());
		nroRaccomandataDestinatarioItem.setWrapTitle(false);
		nroRaccomandataDestinatarioItem.setWidth(80);
		nroRaccomandataDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isRaccomandata(mezzoTrasmissioneDestinatarioItem.getValueAsString()) && showMezzoTrasmissioneItem();
			}
		});

		// Data notifica al destinatario
		dataNotificaDestinatarioItem = new DateItem("dataNotificaDestinatario",
				I18NUtil.getMessages().protocollazione_detail_dataNotificaDestinatarioItem_title());
		dataNotificaDestinatarioItem.setWrapTitle(false);
		dataNotificaDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isNotifica(mezzoTrasmissioneDestinatarioItem.getValueAsString()) && showMezzoTrasmissioneItem();
			}
		});

		// Numero notifica
		nroNotificaDestinatarioItem = new TextItem("nroNotificaDestinatario",
				I18NUtil.getMessages().protocollazione_detail_nroNotificaDestinatarioItem_title());
		nroNotificaDestinatarioItem.setWrapTitle(false);
		nroNotificaDestinatarioItem.setWidth(80);
		nroNotificaDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isNotifica(mezzoTrasmissioneDestinatarioItem.getValueAsString()) & showMezzoTrasmissioneItem();
			}
		});

		// Indirizzo
		indirizzoDestinatarioItem = createSelectIndirizzoDestinatario();
		indirizzoDestinatarioItem.setAllowEmptyValue(false);
		indirizzoDestinatarioItem.setWrapTitle(false);
		indirizzoDestinatarioItem.setFetchMissingValues(true);
		indirizzoDestinatarioItem.setShowTitle(true);
		indirizzoDestinatarioItem.setStartRow(false);
		indirizzoDestinatarioItem.setColSpan(3);
		indirizzoDestinatarioItem.setName("indirizzoDestinatario");
		indirizzoDestinatarioItem.setAutoFetchData(false);
		indirizzoDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showMezzoTrasmissioneItem();
			}
		});
		
		indirizzoDestinatarioItem.addDataArrivedHandler(new DataArrivedHandler() {			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				RecordList data = event.getData();
				if(data != null && data.getLength() == 1){
					Record record = data.get(0);
					lDynamicForm.setValue("indirizzoDestinatario", record.getAttribute("idIndirizzo"));
					manageOnOptionClick("indirizzoDestinatario", record);
				}				
			};
		});

		setFormItems(mezzoTrasmissioneDestinatarioItem, dataRaccomandataDestinatarioItem, nroRaccomandataDestinatarioItem, dataNotificaDestinatarioItem,
				nroNotificaDestinatarioItem, indirizzoDestinatarioItem,

				// Hidden
				idIndirizzoItem, civicoItem, internoItem, scalaItem, pianoItem, capItem, frazioneItem, codIstatComuneItem, comuneItem, codIstatStatoItem,
				statoItem, provinciaItem, zonaItem, complementoIndirizzoItem, tipoToponimoItem, ciToponimoItem, appendiciItem, indirizzoItem,
				descrizioneMezzoTrasmissioneDestinatarioItem, descrizioneIndirizzoItem);

		setCanvas(lDynamicForm);
		setValue(lRecord);
	}

	private SelectItemWithDisplay createSelectIndirizzoDestinatario() {
		SelectItemWithDisplay lItem = createSelectItem("indirizzoDestinatario", I18NUtil.getMessages().protocollazione_detail_indirizzoDestinatarioItem_title(),
				"LoadComboIndirizziDataSource", new String[] { "indirizzoDisplay" }, new String[] { "idIndirizzo" }, new String[] { "Indirizzo" },
				new Object[] { "*" }, 550, "idIndirizzo", "indirizzoDisplay", true);
		return lItem;
	}

	private SelectItemWithDisplay createSelectItem(String name, String title, String datasourceName, String[] campiVisibili, String[] campiHidden,
			String[] descrizioni, Object[] width, int widthTotale, String idField, String displayField, boolean isClearable) {

		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(datasourceName, idField, FieldType.TEXT, campiVisibili, true);
		SelectItemWithDisplay lSelectItemWithDisplay = new SelectItemWithDisplay(name, title, lGwtRestDataSource) {

			@Override
			public void onOptionClick(Record record) {
				manageOnOptionClick(getName(), record);
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				manageClearSelect(getName());
			}
		};

		int i = 0;
		List<ListGridField> lList = new ArrayList<ListGridField>();
		for (String lString : campiVisibili) {
			ListGridField field = new ListGridField(lString, descrizioni[i]);
			if (width[i] instanceof String) {
				field.setWidth((String) width[i]);
			} else
				field.setWidth((Integer) width[i]);

			i++;
			lList.add(field);
		}

		for (String lString : campiHidden) {
			ListGridField field = new ListGridField(lString, lString);
			field.setHidden(true);
			lList.add(field);
		}

		lSelectItemWithDisplay.setPickListFields(lList.toArray(new ListGridField[] {}));
		lSelectItemWithDisplay.setFilterLocally(true);
		lSelectItemWithDisplay.setValueField(idField);
		lSelectItemWithDisplay.setOptionDataSource(lGwtRestDataSource);
		lSelectItemWithDisplay.setWidth(widthTotale);
		lSelectItemWithDisplay.setRequired(false);
		lSelectItemWithDisplay.setClearable(isClearable);
		lSelectItemWithDisplay.setShowIcons(isClearable);
		lSelectItemWithDisplay.setCachePickListResults(false);
		lSelectItemWithDisplay.setDisplayField(displayField);
		return lSelectItemWithDisplay;
	}

	private void manageOnOptionClick(String name, Record record) {

		if (name.equals("indirizzoDestinatario")) {
			String idIndirizzo = record.getAttributeAsString("idIndirizzo");
			String indirizzo = record.getAttributeAsString("indirizzo");
			String civico = record.getAttributeAsString("civico");
			String interno = record.getAttributeAsString("interno");
			String scala = record.getAttributeAsString("scala");
			String piano = record.getAttributeAsString("piano");
			String cap = record.getAttributeAsString("cap");
			String frazione = record.getAttributeAsString("frazione");
			String codIstatComune = record.getAttributeAsString("codIstatComune");
			String comune = record.getAttributeAsString("comune");
			String codIstatStato = record.getAttributeAsString("codIstatStato");
			String stato = record.getAttributeAsString("stato");
			String provincia = record.getAttributeAsString("provincia");
			String zona = record.getAttributeAsString("zona");
			String complementoIndirizzo = record.getAttributeAsString("complementoIndirizzo");
			String tipoToponimo = record.getAttributeAsString("tipoToponimo");
			String ciToponimo = null;// record.getAttributeAsString("ciToponimo");
			String appendici = record.getAttributeAsString("appendici");

			lDynamicForm.setValue("idIndirizzo", idIndirizzo);
			lDynamicForm.setValue("indirizzo", indirizzo);
			lDynamicForm.setValue("civico", civico);
			lDynamicForm.setValue("interno", interno);
			lDynamicForm.setValue("scala", scala);
			lDynamicForm.setValue("piano", piano);
			lDynamicForm.setValue("cap", cap);
			lDynamicForm.setValue("frazione", frazione);
			lDynamicForm.setValue("codIstatComune", codIstatComune);
			lDynamicForm.setValue("comune", comune);
			lDynamicForm.setValue("codIstatStato", codIstatStato);
			lDynamicForm.setValue("stato", stato);
			lDynamicForm.setValue("provincia", provincia);
			lDynamicForm.setValue("zona", zona);
			lDynamicForm.setValue("complementoIndirizzo", complementoIndirizzo);
			lDynamicForm.setValue("tipoToponimo", tipoToponimo);
			lDynamicForm.setValue("ciToponimo", ciToponimo);
			lDynamicForm.setValue("appendici", appendici);
		}
	}

	private void manageClearSelect(String name) {
		if (name.equals("indirizzoDestinatario")) {
			String idIndirizzo = null;
			String indirizzo = null;
			String civico = null;
			String interno = null;
			String scala = null;
			String piano = null;
			String cap = null;
			String frazione = null;
			String codIstatComune = null;
			String comune = null;
			String codIstatStato = null;
			String stato = null;
			String provincia = null;
			String zona = null;
			String complementoIndirizzo = null;
			String tipoToponimo = null;
			String ciToponimo = null;
			String appendici = null;

			lDynamicForm.setValue("idIndirizzo", idIndirizzo);
			lDynamicForm.setValue("indirizzo", indirizzo);
			lDynamicForm.setValue("civico", civico);
			lDynamicForm.setValue("interno", interno);
			lDynamicForm.setValue("scala", scala);
			lDynamicForm.setValue("piano", piano);
			lDynamicForm.setValue("cap", cap);
			lDynamicForm.setValue("frazione", frazione);
			lDynamicForm.setValue("codIstatComune", codIstatComune);
			lDynamicForm.setValue("comune", comune);
			lDynamicForm.setValue("codIstatStato", codIstatStato);
			lDynamicForm.setValue("stato", stato);
			lDynamicForm.setValue("provincia", provincia);
			lDynamicForm.setValue("zona", zona);
			lDynamicForm.setValue("complementoIndirizzo", complementoIndirizzo);
			lDynamicForm.setValue("tipoToponimo", tipoToponimo);
			lDynamicForm.setValue("ciToponimo", ciToponimo);
			lDynamicForm.setValue("appendici", appendici);

			idIndirizzoItem.setValue(idIndirizzo);
			indirizzoItem.setValue(indirizzo);
			civicoItem.setValue(civico);
			internoItem.setValue(interno);
			scalaItem.setValue(scala);
			pianoItem.setValue(piano);
			capItem.setValue(cap);
			frazioneItem.setValue(frazione);
			codIstatComuneItem.setValue(codIstatComune);
			comuneItem.setValue(comune);
			codIstatStatoItem.setValue(codIstatStato);
			statoItem.setValue(stato);
			provinciaItem.setValue(provincia);
			zonaItem.setValue(zona);
			complementoIndirizzoItem.setValue(complementoIndirizzo);
			tipoToponimoItem.setValue(tipoToponimo);
			ciToponimoItem.setValue(ciToponimo);
			appendiciItem.setValue(appendici);

			indirizzoDestinatarioItem.clearValue();
		}
	}

	public void refreshFilteredSelectIndirizzoDestinatario(String idSoggetto) {

		if (indirizzoDestinatarioItem != null) {
			indirizzoDestinatarioItem.clearValue();
			((SelectGWTRestDataSource) indirizzoDestinatarioItem.getOptionDataSource()).addParam("idSoggetto", (String) idSoggetto);
			indirizzoDestinatarioItem.fetchData();
		}

	}
	
	public void setIdSoggetto(String idSoggetto) {

		if (indirizzoDestinatarioItem != null) {
			((SelectGWTRestDataSource) indirizzoDestinatarioItem.getOptionDataSource()).addParam("idSoggetto", (String) idSoggetto);
		}

	}

	/**
	 * Serve per istanziare la classe tramite GWT
	 * 
	 * @param jsObj
	 */
	public MezzoTrasmissioneDestinatarioItem(JavaScriptObject jsObj) {
		super(jsObj);
	}

	/**
	 * Esegue il build quando l'oggetto non esiste. return AttachmentItem;
	 * 
	 * @param jsObj
	 * @return
	 */
	public MezzoTrasmissioneDestinatarioItem buildObject(JavaScriptObject jsObj) {
		
		MezzoTrasmissioneDestinatarioItem lItem = getOrCreateRef(jsObj);
		return lItem;
	}

	/**
	 * Crea l'istanza o ne restituisce una a partire dall'oggetto Javascript
	 * 
	 * @param jsObj
	 * @return
	 */
	public static MezzoTrasmissioneDestinatarioItem getOrCreateRef(JavaScriptObject jsObj) {
		if (jsObj == null)
			return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if (obj != null) {
			obj.setJsObj(jsObj);
			return (MezzoTrasmissioneDestinatarioItem) obj;
		} else {
			return null;
		}
	}

	@Override
	public void setValue(Object value) {
		
		Record lRecord = (Record) value;
	
		// Inizializzo le combo
		if (mezzoTrasmissioneDestinatarioItem != null) {
			if (lRecord!=null && lRecord.getAttribute("mezzoTrasmissioneDestinatario") != null  && !"".equals(lRecord.getAttributeAsString("mezzoTrasmissioneDestinatario"))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(lRecord.getAttribute("mezzoTrasmissioneDestinatario"), lRecord.getAttribute("descrizioneMezzoTrasmissioneDestinatario"));
				mezzoTrasmissioneDestinatarioItem.setValueMap(valueMap);
			}	
		}
		
		if (indirizzoDestinatarioItem != null) {
			if (lRecord != null && lRecord.getAttribute("indirizzoDestinatario") != null && !"".equals(lRecord.getAttributeAsString("indirizzoDestinatario")) && lRecord.getAttribute("descrizioneIndirizzo") != null && !"".equals(lRecord.getAttributeAsString("descrizioneIndirizzo"))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(lRecord.getAttribute("indirizzoDestinatario"), lRecord.getAttribute("descrizioneIndirizzo"));
				indirizzoDestinatarioItem.setValueMap(valueMap);
				manageOnOptionClick("indirizzoDestinatario", lRecord);
			}	
		}
		lDynamicForm.editRecord(lRecord != null ? lRecord : new Record());
		// Memorizzo il record
		storeValue(lRecord);
	
	}

	@Override
	public Object getValue() {
		
		return lDynamicForm != null ? lDynamicForm.getValuesAsRecord() : new Record();
	}

	@Override
	public void setWidth(int width) {
		
		super.setWidth(width);
	}

	@Override
	public void setWidth(String width) {
		
		super.setWidth(width);
	}

	public void setFormItems(FormItem... items) {
		for (FormItem item : items) {
			item.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					
					Record lRecord = new Record(lDynamicForm.getValues());
					lRecord.setAttribute(event.getItem().getName(), event.getValue());
					storeValue(lRecord);
				}
			});
		}
		lDynamicForm.setItems(items);
	}

	public DynamicForm getForm() {
		return lDynamicForm;
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		
		for (FormItem item : lDynamicForm.getFields()) {
			item.setCanEdit(canEdit);
		}
		// fileButtons.setCanEdit(canEdit);
	}

	private boolean isRaccomandata(String mezzoTrasmissione) {
		if (mezzoTrasmissione != null) {
			if ("R".equals(mezzoTrasmissione))
				return true;
		}
		return false;
	}

	private boolean isNotifica(String mezzoTrasmissione) {
		if (mezzoTrasmissione != null) {
			if ("NM".equals(mezzoTrasmissione))
				return true;
		}
		return false;
	}

	public void setCanEditMezzoTrasmissioneMode() {
		if (this.isCreated()) {
			this.setCanEdit(true);
		}

	}

}