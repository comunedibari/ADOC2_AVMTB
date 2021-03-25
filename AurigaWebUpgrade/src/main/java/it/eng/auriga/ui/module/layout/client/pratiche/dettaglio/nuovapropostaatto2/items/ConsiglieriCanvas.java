package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class ConsiglieriCanvas extends ReplicableCanvas {
	
	private SelectItem consigliereItem;
	private HiddenItem consigliereFromLoadDettHiddenItem;
	private HiddenItem desConsigliereHiddenItem;
	private CheckboxItem flgConsigliereFirmatarioItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public ConsiglieriCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		GWTRestDataSource consigliereDS = new GWTRestDataSource("LoadComboConsiglieriDataSource", "key", FieldType.TEXT);
		if(((ConsiglieriItem)getItem()).getAltriParamLoadCombo() != null) {
			consigliereDS.addParam("altriParamLoadCombo", ((ConsiglieriItem)getItem()).getAltriParamLoadCombo());
		}
		
		consigliereItem = new SelectItem("consigliere") {
			
//			@Override
//			protected ListGrid builPickListProperties() {
//				ListGrid consiglierePickListProperties = super.builPickListProperties();
//				if(consiglierePickListProperties == null) {
//					consiglierePickListProperties = new ListGrid();
//				}
//				consiglierePickListProperties.addFetchDataHandler(new FetchDataHandler() {
//
//					@Override
//					public void onFilterData(FetchDataEvent event) {
//						GWTRestDataSource consigliereDS = (GWTRestDataSource) consigliereItem.getOptionDataSource();
//						consigliereDS.addParam("key", (String) consigliereFromLoadDettHiddenItem.getValue());												
//						consigliereItem.setOptionDataSource(consigliereDS);
//						consigliereItem.invalidateDisplayValueCache();
//					}
//				});
//				return consiglierePickListProperties;
//			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desConsigliere", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("consigliere", "");
				mDynamicForm.setValue("desConsigliere", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("consigliere", "");
					mDynamicForm.setValue("desConsigliere", "");
				}
			}
		};
		consigliereItem.setOptionDataSource(consigliereDS);
		consigliereItem.setShowTitle(false);
		consigliereItem.setStartRow(true);
		consigliereItem.setWidth(650);
		consigliereItem.setValueField("key");
		consigliereItem.setDisplayField("value");
		consigliereItem.setAllowEmptyValue(false);
		consigliereItem.setClearable(true);
		consigliereItem.setAutoFetchData(false);
		consigliereItem.setAlwaysFetchMissingValues(true);
		consigliereItem.setFetchMissingValues(true);
		consigliereItem.setRequired(true);
		
		consigliereFromLoadDettHiddenItem = new HiddenItem("consigliereFromLoadDett");
		desConsigliereHiddenItem = new HiddenItem("desConsigliere");
		
		flgConsigliereFirmatarioItem = new CheckboxItem("flgConsigliereFirmatario", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgFirmatario_title());
		flgConsigliereFirmatarioItem.setDefaultValue(false);
		flgConsigliereFirmatarioItem.setColSpan(1);
		flgConsigliereFirmatarioItem.setWidth("*");
		flgConsigliereFirmatarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((ConsiglieriItem)getItem()).showFlgFirmatario();
			}
		});
					
		mDynamicForm.setFields(consigliereItem, consigliereFromLoadDettHiddenItem, desConsigliereHiddenItem, flgConsigliereFirmatarioItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, consigliereItem, "consigliere", new String[]{"desConsigliere"}, "key");
		super.editRecord(record);				
	}
	
}
