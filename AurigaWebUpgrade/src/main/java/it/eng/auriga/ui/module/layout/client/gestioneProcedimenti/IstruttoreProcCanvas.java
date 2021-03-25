package it.eng.auriga.ui.module.layout.client.gestioneProcedimenti;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class IstruttoreProcCanvas extends ReplicableCanvas {

	private SelectItem tipoIstruttoreProcItem;
	private HiddenItem nomeUtenteIstruttoreProcItem; 
	private FilteredSelectItemWithDisplay utenteIstruttoreProcItem;
	private ExtendedTextItem codRapidoGruppoIstruttoreProcItem;
	private HiddenItem nomeGruppoIstruttoreProcItem;
	private FilteredSelectItemWithDisplay gruppoIstruttoreProcItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public IstruttoreProcCanvas(IstruttoreProcItem item) {
		super(item);
	}

	public IstruttoreProcCanvas(IstruttoreProcItem item, HashMap<String, String> parameters) {
		// TODO Auto-generated constructor stub
		super(item, parameters);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);	

		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");
	
		tipoIstruttoreProcItem = new SelectItem("tipoIstruttoreProc");
		tipoIstruttoreProcItem.setShowTitle(false);
		LinkedHashMap<String, String> tipoIstruttoreProcValueMap = new LinkedHashMap<String, String>();
		tipoIstruttoreProcValueMap.put("UT", "Persona");
		tipoIstruttoreProcValueMap.put("LD", "Team");
		tipoIstruttoreProcItem.setDefaultValue("UT");
		tipoIstruttoreProcItem.setValueMap(tipoIstruttoreProcValueMap);
		tipoIstruttoreProcItem.setWidth(150);
		tipoIstruttoreProcItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				mDynamicForm.markForRedraw();
			}
		});

		SelectGWTRestDataSource utenteIstruttoreProcDS = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT, new String[]{"cognomeNome"/*, "username"*/}, true);
		utenteIstruttoreProcDS.addParam("flgSoloTributi", "1");
		
		nomeUtenteIstruttoreProcItem = new HiddenItem("nomeUtenteIstruttoreProc");
		
		utenteIstruttoreProcItem = new FilteredSelectItemWithDisplay("idUtenteIstruttoreProc", utenteIstruttoreProcDS){
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);	
//				mDynamicForm.setValue("idUtenteIstruttoreProc", record.getAttributeAsString("idUtente"));
				mDynamicForm.setValue("nomeUtenteIstruttoreProc", record.getAttributeAsString("cognomeNome"));				
			}			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("idUtenteIstruttoreProc", "");		
				mDynamicForm.setValue("nomeUtenteIstruttoreProc", "");		
			};	
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("idUtenteIstruttoreProc", "");		
					mDynamicForm.setValue("nomeUtenteIstruttoreProc", "");	
				}
			}		
		};		
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");//		
		ListGridField utentiUsernameField = new ListGridField("username", "Username");				
		utentiUsernameField.setWidth(80);		
		utenteIstruttoreProcItem.setPickListFields(utentiCognomeNomeField, utentiUsernameField);		
		utenteIstruttoreProcItem.setFilterLocally(true);
		utenteIstruttoreProcItem.setValueField("idUtente");  		
		utenteIstruttoreProcItem.setShowTitle(false);
		utenteIstruttoreProcItem.setOptionDataSource(utenteIstruttoreProcDS); 
		utenteIstruttoreProcItem.setWidth(300);
		utenteIstruttoreProcItem.setClearable(true);
		utenteIstruttoreProcItem.setShowIcons(true);
		utenteIstruttoreProcItem.setAutoFetchData(false);
		utenteIstruttoreProcItem.setAlwaysFetchMissingValues(true);
		utenteIstruttoreProcItem.setFetchMissingValues(true);
		if(AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utenteIstruttoreProcItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utenteIstruttoreProcItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});		
		utenteIstruttoreProcItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return tipoIstruttoreProcItem.getValueAsString() != null && "UT".equalsIgnoreCase(tipoIstruttoreProcItem.getValueAsString());
			}
		});
		
				
		SelectGWTRestDataSource gruppoIstruttoreProcDS = new SelectGWTRestDataSource("LoadComboGruppoTeamDataSource", "idGruppo", FieldType.TEXT, new String[] { "nomeGruppo" }, true);
		
		codRapidoGruppoIstruttoreProcItem = new ExtendedTextItem("codRapidoGruppoIstruttoreProc", "Cod. rapido");
		codRapidoGruppoIstruttoreProcItem.setWidth(80);
		codRapidoGruppoIstruttoreProcItem.setColSpan(1);
		codRapidoGruppoIstruttoreProcItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				cercaSoggettoLD();
			}
		});
		codRapidoGruppoIstruttoreProcItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return tipoIstruttoreProcItem.getValueAsString() != null && "LD".equalsIgnoreCase(tipoIstruttoreProcItem.getValueAsString());
			}
		});
		codRapidoGruppoIstruttoreProcItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				if (codRapidoGruppoIstruttoreProcItem.getValue() != null && !"".equals(codRapidoGruppoIstruttoreProcItem.getValueAsString().trim()) && gruppoIstruttoreProcItem.getValue() == null) {
					return false;
				}
				return true;
			}
		});
		
		nomeGruppoIstruttoreProcItem = new HiddenItem("nomeGruppoIstruttoreProc");
		
		gruppoIstruttoreProcItem = new FilteredSelectItemWithDisplay("idGruppoIstruttoreProc", gruppoIstruttoreProcDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
//				mDynamicForm.setValue("idGruppoIstruttoreProc", record.getAttributeAsString("idGruppo"));
				mDynamicForm.setValue("codRapidoGruppoIstruttoreProc", record.getAttributeAsString("codiceRapidoGruppo"));
				mDynamicForm.setValue("nomeGruppoIstruttoreProc", record.getAttributeAsString("nomeGruppo"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("idGruppoIstruttoreProc", "");
				mDynamicForm.setValue("codRapidoGruppoIstruttoreProc", "");
				mDynamicForm.setValue("nomeGruppoIstruttoreProc", "");
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("idGruppoIstruttoreProc", "");
					mDynamicForm.setValue("codRapidoGruppoIstruttoreProc", "");
					mDynamicForm.setValue("nomeGruppoIstruttoreProc", "");
				}
			};
		};
//		gruppoIstruttoreProcItem.setAutoFetchData(false);
		gruppoIstruttoreProcItem.setFetchMissingValues(true);
		ListGridField codiceRapidoGruppoField = new ListGridField("codiceRapidoGruppo", "Cod. rapido");
		codiceRapidoGruppoField.setWidth(70);
		ListGridField nomeGruppoField = new ListGridField("nomeGruppo", "Nome");
		nomeGruppoField.setWidth("*");
		gruppoIstruttoreProcItem.setPickListFields(codiceRapidoGruppoField, nomeGruppoField);
		gruppoIstruttoreProcItem.setFilterLocally(true);
		gruppoIstruttoreProcItem.setValueField("idGruppo");
		gruppoIstruttoreProcItem.setOptionDataSource(gruppoIstruttoreProcDS);
		gruppoIstruttoreProcItem.setShowTitle(false);
		gruppoIstruttoreProcItem.setWidth(400);
		gruppoIstruttoreProcItem.setClearable(true);
		gruppoIstruttoreProcItem.setShowIcons(true);
		gruppoIstruttoreProcItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return tipoIstruttoreProcItem.getValueAsString() != null && "LD".equalsIgnoreCase(tipoIstruttoreProcItem.getValueAsString());
			}
		});
		
		mDynamicForm.setFields(tipoIstruttoreProcItem, utenteIstruttoreProcItem, nomeUtenteIstruttoreProcItem, codRapidoGruppoIstruttoreProcItem, gruppoIstruttoreProcItem, nomeGruppoIstruttoreProcItem);
		

		addChild(mDynamicForm);

	}

	@Override
	public void editRecord(Record record) {
		
		mDynamicForm.clearErrors(true);
		if (record.getAttribute("tipoIstruttoreProc") != null) {
			if ("UT".equalsIgnoreCase(record.getAttribute("tipoIstruttoreProc"))) {
				if (record.getAttribute("idUtenteIstruttoreProc") != null && !"".equals(record.getAttributeAsString("idUtenteIstruttoreProc"))) {
					LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
					valueMap.put(record.getAttribute("idUtenteIstruttoreProc"), record.getAttribute("nomeUtenteIstruttoreProc"));
					utenteIstruttoreProcItem.setValueMap(valueMap);
				}
				
				GWTRestDataSource utentiDS = (GWTRestDataSource) utenteIstruttoreProcItem.getOptionDataSource();
				if (record.getAttribute("idUtenteIstruttoreProc") != null && !"".equals(record.getAttributeAsString("idUtenteIstruttoreProc"))) {
					utentiDS.addParam("idUtente", record.getAttributeAsString("idUtenteIstruttoreProc"));
				} else {
					utentiDS.addParam("idUtente", null);
				}
				utenteIstruttoreProcItem.setOptionDataSource(utentiDS);
			} else if ("LD".equalsIgnoreCase(record.getAttribute("tipoIstruttoreProc"))) {
				if (record.getAttribute("idGruppoIstruttoreProc") != null && !"".equals(record.getAttributeAsString("idGruppoIstruttoreProc"))) {
					LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
					valueMap.put(record.getAttribute("idGruppoIstruttoreProc"), record.getAttribute("nomeGruppoIstruttoreProc"));
					gruppoIstruttoreProcItem.setValueMap(valueMap);
				}

				GWTRestDataSource gruppoIstruttoreProcDS = (GWTRestDataSource) gruppoIstruttoreProcItem.getOptionDataSource();
				if (record.getAttribute("idGruppoIstruttoreProc") != null && !"".equals(record.getAttributeAsString("idGruppoIstruttoreProc"))) {
					gruppoIstruttoreProcDS.addParam("idGruppo", record.getAttributeAsString("idGruppoIstruttoreProc"));
				} else {
					gruppoIstruttoreProcDS.addParam("idGruppo", null);
				}
				gruppoIstruttoreProcItem.setOptionDataSource(gruppoIstruttoreProcDS);
			}
		}
		super.editRecord(record);
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}
	
	protected void cercaSoggettoLD() {
		mDynamicForm.clearErrors(true);
		mDynamicForm.setValue("idGruppoIstruttoreProc", (String) null);
		final String codRapidoGruppo = codRapidoGruppoIstruttoreProcItem.getValueAsString().toUpperCase();
		if (codRapidoGruppo != null && !"".equals(codRapidoGruppo)) {			
			SelectGWTRestDataSource gruppoIstruttoreProcDS = new SelectGWTRestDataSource("LoadComboGruppoTeamDataSource", "idGruppo", FieldType.TEXT, new String[] { "nomeGruppo" }, true);		
			gruppoIstruttoreProcDS.extraparam.put("codiceRapidoGruppo", codRapidoGruppo);
			gruppoIstruttoreProcDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String codiceRapido = data.get(i).getAttribute("codiceRapidoGruppo");
							if (codRapidoGruppo.equalsIgnoreCase(codiceRapido)) {
								mDynamicForm.setValue("codRapidoGruppoIstruttoreProc", data.get(i).getAttribute("codiceRapidoGruppo"));
								mDynamicForm.setValue("idGruppoIstruttoreProc", data.get(i).getAttribute("idGruppo"));
								mDynamicForm.setValue("nomeGruppoIstruttoreProc", data.get(i).getAttribute("nomeGruppo"));
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setFieldErrors("codRapidoGruppoIstruttoreProc", "Gruppo inesistente");
					}
				}
			});
		}
	}

}
