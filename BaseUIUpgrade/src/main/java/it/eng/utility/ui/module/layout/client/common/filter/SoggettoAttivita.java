package it.eng.utility.ui.module.layout.client.common.filter;

import com.smartgwt.client.types.FieldType;


public class SoggettoAttivita extends CustomDataSourceField {
	
	public SoggettoAttivita(){
		super(FieldType.CUSTOM);		
	}
	
	public SoggettoAttivita(String name) {
		super(name, FieldType.CUSTOM);		
	}

	public SoggettoAttivita(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public SoggettoAttivita(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public SoggettoAttivita(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {

		setAttribute("customType", "soggetto_attivita");		

		SoggettoAttivitaItem editorType = new SoggettoAttivitaItem(); 
		setEditorType(editorType);

//		setValidOperators(OperatorId.EQUALS);

	}

}
