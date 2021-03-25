package it.eng.utility.ui.module.layout.shared.bean;

import java.util.Map;

public abstract class RequiredIfFunction {

	public String utente;
	public String[] privilegi;
	
	public abstract boolean isRequired(Map<String, Object> requiredIfMap);
}
