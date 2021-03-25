package it.eng.auriga.ui.module.layout.client.scrivania;

import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.menu.ShowInMenuFunction;

import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * Classe costruita a partire dalla {@link ShowInMenuFunction}
 * I controlli vengono fatti direttamente sull'attributo del nodo della 
 * scrivania selezionato
 * @author Rametta
 *
 */
public class ShowInMenuFunctionFromScrivania extends ShowInMenuFunction{

	protected ScrivaniaLayout layout;
	protected String name; 
	protected String operator; 	
	protected String[] values; 
	
	public ShowInMenuFunctionFromScrivania(ScrivaniaLayout pLayout, ListGridField[] pListGridField,
			CustomList pList, String pName, String pOperator, String[] valori) {
		super(pListGridField, pList);	
		layout = pLayout;
		name = pName;
		operator = pOperator != null ? pOperator : "equals";
		values = valori;
	}

	@Override
	public boolean mustBeShown() {
		String value = null;
		if("idNode".equals(name)) {
			value = layout != null ? layout.getIdNode() : null;
		} else if("idFolder".equals(name)) {
			value = layout != null ? layout.getIdFolder() : null;
		} else if("tipoNodo".equals(name)) {
			value = layout != null ? layout.getTipoNodo() : null;
		}
		if(value != null) {
			for (String val : values){		
				if(operator != null) {
					if(operator.equals("equals") && value.equals(val)){
						return true;
					}
					if(operator.equals("notEquals") && !value.equals(val)){
						return true;
					}
					if(operator.equals("startsWith") && value.startsWith(val)){
						return true;
					}
					if(operator.equals("notStartsWith") && !value.startsWith(val)){
						return true;
					}
					if(operator.equals("contains") && value.contains(val)){
						return true;
					}
					if(operator.equals("notContains") && !value.contains(val)){
						return true;
					}
				}
			}			
		}
		return false;
	}

}
