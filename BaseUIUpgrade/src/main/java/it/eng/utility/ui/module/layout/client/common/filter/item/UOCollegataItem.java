package it.eng.utility.ui.module.layout.client.common.filter.item;

import com.google.gwt.core.client.JavaScriptObject;

import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class UOCollegataItem extends CustomItem{
	

	public UOCollegataItem(JavaScriptObject jsObj) {
		super(jsObj);
	}
	
	public UOCollegataItem() {
		super();
	}

	@Override
	public CustomItemFormField[] getFormFields() {
		return new CustomItemFormField[]{};
	}

	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		UOCollegataItem lFilterCanvasItem = new UOCollegataItem(jsObj);
		return lFilterCanvasItem;
	}
	
}
