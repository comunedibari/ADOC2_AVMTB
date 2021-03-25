package it.eng.auriga.ui.module.layout.client.gestioneUtenti;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGridField;

public class SocietaUtentiCanvas extends ReplicableCanvas{
	protected SelectItemWithDisplay societaItem;	
	private ReplicableCanvasForm mDynamicForm;
	

	private HiddenItem denominazioneSocietaItem;
	
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		

		denominazioneSocietaItem       = new HiddenItem("denominazioneSocieta");
		
		

		societaItem = createSelectItem("idSocieta",
				                              I18NUtil.getMessages().gestioneutenti_combo_societa_title(), 
				                              "LoadComboSocietaUtenteDataSource", 
				                              new String[] { "denominazioneSocieta"}, 
				                              new String[]{"idSocieta"}, 
				                              new String[]{I18NUtil.getMessages().gestioneutenti_combo_societa_denominazioneItem_title()
			                                              }, 
				                              new Object[]{300}, 
				                              410, 
				                              "idSocieta", 
				                              "denominazioneSocieta",
				                              true);
		societaItem.setAllowEmptyValue(false);
		
		societaItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
									
				String idSocieta = "";
				if (((SocietaUtentiItem)getItem()).getIdSocieta() !=null){
					idSocieta = ((SocietaUtentiItem)getItem()).getIdSocieta();				
				}				
				SelectGWTRestDataSource societaDS = (SelectGWTRestDataSource) societaItem.getOptionDataSource();
				societaDS.addParam("idSocieta", idSocieta);
				societaItem.setOptionDataSource(societaDS);	
				return new Criteria("idSocieta", idSocieta);															
			}
		});	
		
		
		mDynamicForm.setFields(societaItem,
				               denominazioneSocietaItem);	
							   
		mDynamicForm.setNumCols(2);		
		addChild(mDynamicForm);
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
				
		super.editRecord(record);
		
		//Inizializzo le combo con la descrizione restituita dal servizio		
		if(record.getAttribute("idSocieta") != null && !"".equals(record.getAttributeAsString("idSocieta"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idSocieta"), record.getAttribute("denominazioneSocieta"));
			societaItem.setValueMap(valueMap);										
		} 
		
	}
	
	
	
		
	
	private void manageOnOptionClick(String name, Record record){	
		
		String idSocieta             = record.getAttributeAsString("idSocieta");
		String denominazioneSocieta  = record.getAttributeAsString("denominazioneSocieta");

		mDynamicForm.setValue("idSocieta",             idSocieta);	
        mDynamicForm.setValue("denominazioneSocieta",  denominazioneSocieta);	
		
		
	}	
	private void manageClearSelect(String name){
		
		if (name.equals("idSocieta")){			
			societaItem.clearValue();
			mDynamicForm.setValue("idSocieta", (String)null);			
			mDynamicForm.setValue("denominazioneSocieta", (String)null);
		}
		
	}		
	
	private SelectItemWithDisplay createSelectItem(String name, 
                                                   String title,
                                                   String datasourceName, 
                                                   String[] campiVisibili, 
                                                   String[] campiHidden, 
                                                   String[] descrizioni, 
                                                   Object[] width,
                                                   int widthTotale, 
                                                   String idField, 
                                                   String displayField,
                                                   boolean isClearable){

        SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(datasourceName, idField, FieldType.TEXT, campiVisibili, true);
        SelectItemWithDisplay lSelectItemWithDisplay = new SelectItemWithDisplay(name, title, lGwtRestDataSource){
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

        int i=0;

        List<ListGridField> lList = new ArrayList<ListGridField>();
        for (String lString : campiVisibili){
        	ListGridField field = new ListGridField(lString, descrizioni[i]);
        	if (width[i] instanceof String){
        		field.setWidth((String)width[i]);
        	} else field.setWidth((Integer)width[i]);
        	
        	i++;
        	lList.add(field);
        }
        for (String lString : campiHidden){
        	ListGridField field = new ListGridField(lString, lString);
        	field.setHidden(true);
        	lList.add(field);
        }
        lSelectItemWithDisplay.setPickListFields(lList.toArray(new ListGridField[]{}));		
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
}
