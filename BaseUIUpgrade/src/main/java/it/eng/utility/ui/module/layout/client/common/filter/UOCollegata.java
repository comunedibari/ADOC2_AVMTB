package it.eng.utility.ui.module.layout.client.common.filter;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.UOCollegataItem;

public class UOCollegata extends CustomDataSourceField {
	
	public UOCollegata() {
		super(FieldType.CUSTOM);
	}
	
	public UOCollegata(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public UOCollegata(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public UOCollegata(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {

		setAttribute("customType", "uo_collegata");		

		UOCollegataItem editorType = new UOCollegataItem(); 
		setEditorType(editorType);
		
	}

}
