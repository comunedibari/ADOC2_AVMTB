package it.eng.utility.ui.module.layout.client.menu;

/**
 * Classe che si occupa di rappresentare la relazione
 * tra il nome di un campo dei filtri con una serie 
 * di valori ammessi
 * 
 * Utilizzata nella {@link ShowInMenuFunctionFilterClause}
 * 
 * @author Rametta
 *
 */
public class FilterValueRelation {

	private String name;
	private String[] values;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	public String[] getValues() {
		return values;
	}
	public FilterValueRelation(String name, String[] values) {
		this.name = name;
		this.values = values;
	}
	public FilterValueRelation(String name, String string) {
		this.name = name;
		this.values = new String[]{string};
	}
	
	
	
}
