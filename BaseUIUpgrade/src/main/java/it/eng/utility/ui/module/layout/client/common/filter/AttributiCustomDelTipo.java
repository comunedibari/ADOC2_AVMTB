package it.eng.utility.ui.module.layout.client.common.filter;

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.AttributiCustomDelTipoItem;

public class AttributiCustomDelTipo extends CustomDataSourceField {
	
	public AttributiCustomDelTipo() {
		super(FieldType.CUSTOM);
	}

	public AttributiCustomDelTipo(String name) {
		super(name, FieldType.CUSTOM);
	}

	public AttributiCustomDelTipo(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public AttributiCustomDelTipo(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public AttributiCustomDelTipo(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public AttributiCustomDelTipo(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "attributi_custom_del_tipo");		
		
		AttributiCustomDelTipoItem editorType = new AttributiCustomDelTipoItem(property);
		setEditorType(editorType);
        
	}

}
