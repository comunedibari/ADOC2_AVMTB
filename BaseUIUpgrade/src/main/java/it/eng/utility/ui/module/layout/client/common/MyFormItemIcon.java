package it.eng.utility.ui.module.layout.client.common;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;

public class MyFormItemIcon extends FormItemIcon{
	
	private FormItem owner;

	public void setOwner(FormItem owner) {
		this.owner = owner;
	}

	public FormItem getOwner() {
		return owner;
	}
	
	
}
