package it.eng.auriga.ui.module.layout.client.organigramma;

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;


public class ContattiOrganigrammaCanvas extends ReplicableCanvas{

	private ReplicableCanvasForm mDynamicForm;
	private HiddenItem rowIdItem;	
	private SelectItem tipoItem;
	private TextItem emailItem;
	private TextItem telFaxItem;
	private SelectItem tipoTelItem;
	private CheckboxItem flgPecItem;
	private CheckboxItem flgDichIpaItem;
	private CheckboxItem flgCasellaIstituzItem;

	public ContattiOrganigrammaCanvas(ContattiOrganigrammaItem item) {
		super(item);			
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm() {

			@Override
			public boolean hasValue(Record defaultRecord) {

				if (getValues() != null && getValues().size() > 0) {
					Map<String, Object> lMap = getValues();
					if (lMap.get("tipo").equals("T")|| lMap.get("tipo").equals("F")) {
						if (lMap.get("telFax")!=null && !"".equals(lMap.get("telFax"))) {
							return true;
						}
					}else if (lMap.get("tipo").equals("E")) {
						if (lMap.get("email")!=null && !"".equals(lMap.get("email"))) {
							return true;
						}
					}
				}
				return false;
			}
		};
		mDynamicForm.setWrapItemTitles(false);		
		mDynamicForm.setValidateOnChange(false);

		rowIdItem = new HiddenItem("rowId");

		tipoItem = new SelectItem("tipo"); 
		tipoItem.setShowTitle(false); 
		tipoItem.setAllowEmptyValue(false);
		tipoItem.setWidth(80);
		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		tipoValueMap.put("E", I18NUtil.getMessages().soggetti_contatti_tipo_E_value()); 
		tipoValueMap.put("T", I18NUtil.getMessages().soggetti_contatti_tipo_T_value());
		tipoValueMap.put("F", I18NUtil.getMessages().soggetti_contatti_tipo_F_value()); 
		tipoItem.setValueMap(tipoValueMap);  
		tipoItem.setDefaultValue("E");				
		tipoItem.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.redraw();
			}
		});		

		emailItem = new TextItem("email");
		emailItem.setShowTitle(false); 
		emailItem.setWidth(200);
		emailItem.setAttribute("obbligatorio", true);
		emailItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipo = mDynamicForm.getValue("tipo") != null ? (String) mDynamicForm.getValueAsString("tipo") : "";
				return "E".equals(tipo);
			}
		});			
		emailItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {

				String tipo = mDynamicForm.getValue("tipo") != null ? (String) mDynamicForm.getValueAsString("tipo") : "";
				return "E".equals(tipo);
			}
		}), new RegExpValidator("^([_.\\]*[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z-_])*@(([0-9a-zA-Z])+([-\\w]*[0-9a-zA-Z])*\\.)+[a-zA-Z]{2,9})$"));

		telFaxItem = new TextItem("telFax");
		telFaxItem.setShowTitle(false); 
		telFaxItem.setWidth(200);
		telFaxItem.setAttribute("obbligatorio", true);
		telFaxItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				String tipo = mDynamicForm.getValue("tipo") != null ? (String) mDynamicForm.getValueAsString("tipo") : "";
				return !"E".equals(tipo);
			}
		});			
		telFaxItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				String tipo = mDynamicForm.getValue("tipo") != null ? (String) mDynamicForm.getValueAsString("tipo") : "";
				return !"E".equals(tipo);
			}
		})/**, new RegExpValidator("^[0-9]{5,12}")*/);
		// TODEL Tolta espressione regolare per validare il numero di telefono, ne serve una migliore

		final GWTRestDataSource tipoContattoTelDS = new GWTRestDataSource("TipoContattoTelOrganigrammaDataSource", "key", FieldType.TEXT);

		tipoTelItem = new SelectItem("tipoTel", I18NUtil.getMessages().soggetti_detail_contatti_tipoTelItem_title());
		tipoTelItem.setValueField("key");  
		tipoTelItem.setDisplayField("value");
		tipoTelItem.setOptionDataSource(tipoContattoTelDS); 		
		tipoTelItem.setWidth(200);					
		tipoTelItem.setCachePickListResults(false);		
		tipoTelItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {	
			
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				if(rowIdItem.getValue() != null && !"".equals((String) rowIdItem.getValue())) {
					Criterion[] criterias = new Criterion[1];	
					criterias[0] = new Criterion("rowId", OperatorId.EQUALS, (String) rowIdItem.getValue());	
					return new AdvancedCriteria(OperatorId.AND, criterias);
				} 
				return null;
			}
		});	
		tipoTelItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				String tipo = mDynamicForm.getValue("tipo") != null ? (String) mDynamicForm.getValueAsString("tipo") : "";
				return "T".equals(tipo);
			}
		});		

		flgPecItem = new CheckboxItem("flgPec", I18NUtil.getMessages().soggetti_detail_contatti_flgPecItem_title());
		flgPecItem.setWidth("*");
		flgPecItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm soggettiForm) {

				String tipo = mDynamicForm.getValue("tipo") != null ? (String) mDynamicForm.getValueAsString("tipo") : "";
				return "E".equals(tipo);
			}
		});

		flgDichIpaItem = new CheckboxItem("flgDichIpa", I18NUtil.getMessages().soggetti_detail_contatti_flgDichIpaItem_title());
		flgDichIpaItem.setWidth("*");
		flgDichIpaItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm soggettiForm) {

				String tipo = mDynamicForm.getValue("tipo") != null ? (String) mDynamicForm.getValueAsString("tipo") : "";
				return "E".equals(tipo) && !((ContattiOrganigrammaItem)getItem()).isNewMode();
			}
		});

		flgCasellaIstituzItem = new CheckboxItem("flgCasellaIstituz", I18NUtil.getMessages().soggetti_detail_contatti_flgCasellaIstituzItem_title());
		flgCasellaIstituzItem.setWidth("*");
		flgCasellaIstituzItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm soggettiForm) {

				String tipo = mDynamicForm.getValue("tipo") != null ? (String) mDynamicForm.getValueAsString("tipo") : "";
				return "E".equals(tipo) && !((ContattiOrganigrammaItem)getItem()).isNewMode();
			}
		});

		mDynamicForm.setFields(rowIdItem, tipoItem, emailItem, telFaxItem, tipoTelItem, flgPecItem, flgDichIpaItem, flgCasellaIstituzItem);	

		mDynamicForm.setNumCols(10);

		addChild(mDynamicForm);

	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		flgDichIpaItem.setCanEdit(false);
		flgCasellaIstituzItem.setCanEdit(false);
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}

	@Override
	public void editRecord(Record record) {
		
		GWTRestDataSource tipoContattoTelDS = (GWTRestDataSource) tipoTelItem.getOptionDataSource();
		if(record.getAttribute("rowId") != null && !"".equals(record.getAttributeAsString("rowId"))) {
			tipoContattoTelDS.addParam("rowId", record.getAttributeAsString("rowId"));										
		} else {
			tipoContattoTelDS.addParam("rowId", null);										
		}
		tipoTelItem.setOptionDataSource(tipoContattoTelDS);	

		super.editRecord(record);
	}
}