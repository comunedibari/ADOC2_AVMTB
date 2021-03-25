package it.eng.utility.ui.module.layout.client.common.filter;

import com.smartgwt.client.types.FieldType;

public class AggregazioniPratica extends CustomDataSourceField {
	
	public AggregazioniPratica(){
		super(FieldType.CUSTOM);		
	}
	
	public AggregazioniPratica(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public AggregazioniPratica(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public AggregazioniPratica(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {

		setAttribute("customType", "aggregazioni_pratica");		

		AggregazioniPraticaItem editorType = new AggregazioniPraticaItem(); 
		setEditorType(editorType);
		
	}
}
