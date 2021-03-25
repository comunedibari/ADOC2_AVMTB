package it.eng.utility.ui.module.layout.client.common;

import java.util.Map;

import com.smartgwt.client.widgets.form.DynamicForm;

public interface IDocumentItem {

	public abstract DynamicForm getForm();
	
	public abstract Boolean validate();

	public abstract void clearErrors();
	
	public abstract Map getMapErrors();
	
	public abstract Boolean valuesAreValid();
}
