package it.eng.auriga.ui.module.layout.client.caselleEmail;

import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.LinkItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;

public class CaselleEmailDetail extends CustomDetail {
	
	protected CaselleEmailDetail instance;
	
	protected DynamicForm hiddenForm;
	protected HiddenItem idCasellaItem;	
	protected HiddenItem indirizzoEmailItem;	
	
	protected DynamicForm parametriForm;	

	public CaselleEmailDetail(String nomeEntita) {
		this(nomeEntita, null);
	}
		
	public CaselleEmailDetail(String nomeEntita, Record record) {
		super(nomeEntita);
		
		instance = this;		
		
		hiddenForm = new DynamicForm();
		hiddenForm.setValuesManager(vm);
		hiddenForm.setWidth("100%");  
		hiddenForm.setHeight("5");  		
		
		idCasellaItem = new HiddenItem("idCasella");		
		indirizzoEmailItem = new HiddenItem("indirizzoEmail");		
		
		hiddenForm.setItems(
			idCasellaItem,
			indirizzoEmailItem
		);
		
		parametriForm = new DynamicForm();
		parametriForm.setValuesManager(vm);
		parametriForm.setWidth("100%");  
		parametriForm.setHeight("5");  
		parametriForm.setPadding(5);
		parametriForm.setNumCols(6);
		parametriForm.setColWidths(500, 1, 1, 1, "*", "*");
		parametriForm.setWrapItemTitles(true);
		
		if(record != null) {
			List<FormItem> parametriItems = new ArrayList<FormItem>();
		
			RecordList parametriCasella = record.getAttributeAsRecordList("parametriCasella");
			
			for(int i = 0; i < parametriCasella.getLength(); i++) {
				Record param = parametriCasella.get(i);							
				parametriItems.add(buildParametroCasellaFormItem(param));
			}
			
			parametriForm.setFields(parametriItems.toArray(new FormItem[0]));						
		}
		
		setMembers(hiddenForm, parametriForm);	
	}
	
	public FormItem buildParametroCasellaFormItem(Record param) {
		FormItem item = null;
		
		if(param.getAttribute("tipo") != null) {
			if(param.getAttribute("tipo").equalsIgnoreCase("string")) {
				item = new TextItem(param.getAttribute("nome"), param.getAttribute("titolo"));
			} else if(param.getAttribute("tipo").equalsIgnoreCase("int")) {
				item = new NumericItem(param.getAttribute("nome"), param.getAttribute("titolo"), false);
			} else if(param.getAttribute("tipo").equalsIgnoreCase("enum")) {
				item = new SelectItem(param.getAttribute("nome"), param.getAttribute("titolo"));
				StringSplitterClient st = new StringSplitterClient(param.getAttribute("valueMap"), "|*|");
				item.setValueMap(st.getTokens());
			} else if(param.getAttribute("tipo").equalsIgnoreCase("password")) {
				item = new PasswordItem(param.getAttribute("nome"), param.getAttribute("titolo"));
				item.setWidth(250);							
			} else if(param.getAttribute("tipo").equalsIgnoreCase("path")) {
				item = new LinkItem(param.getAttribute("nome"));
				item.setTitle(param.getAttribute("titolo"));
				item.setWidth(250);							
			}
		}
		
		if(item == null) {
			item = new TextItem(param.getAttribute("nome"), param.getAttribute("titolo"));
		}
		
		item.setRequired(param.getAttribute("flgObblig") != null && param.getAttribute("flgObblig").equals("1"));
		if(item instanceof SelectItem) {
			((SelectItem)item).setAllowEmptyValue(!item.getRequired());				
		}
		
		item.setStartRow(true);
		return item;
	}
	
	@Override
	public void editRecord(Record record) {
		
		Record values = new Record();	
		values.setAttribute("idCasella", record.getAttribute("idCasella"));
		values.setAttribute("indirizzoEmail", record.getAttribute("indirizzoEmail"));
		
		RecordList parametriCasella = record.getAttributeAsRecordList("parametriCasella");
		
		if(parametriCasella != null) {
			for(int i = 0; i < parametriCasella.getLength(); i++) {
				Record param = parametriCasella.get(i);			
				values.setAttribute(param.getAttribute("nome"), param.getAttribute("valore"));
			}
		}
		// ATTENZIONE: alcuni dei nomi dei campi a maschera contengono il punto e l'editRecord non 
		// funziona correttamente, perciò devo settare manualmente i valori sugli item 
//		super.editRecord(values);
		for(DynamicForm form : vm.getMembers()) {
			for(FormItem item : form.getFields()) {
				item.setValue(values.getAttribute(item.getName()));
			}
		}		
	}
	
	public Record getRecordToSave() {		
		Record record = new Record();	
		record.setAttribute("idCasella", hiddenForm.getValue("idCasella"));
		record.setAttribute("indirizzoEmail", hiddenForm.getValue("indirizzoEmail"));
		RecordList parametriCasella = new RecordList();		
		for(FormItem item : parametriForm.getFields()) {
			Record param = new Record();
			param.setAttribute("nome", item.getName());
			param.setAttribute("valore", parametriForm.getValue(item.getName()));
			parametriCasella.add(param);
		}
		record.setAttribute("parametriCasella", parametriCasella);
		return record;
	}
	
}