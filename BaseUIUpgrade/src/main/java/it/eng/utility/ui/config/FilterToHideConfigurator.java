package it.eng.utility.ui.config;

import java.util.List;
import java.util.Map;

public class FilterToHideConfigurator {

	private Map<String, List<String>> fieldsToHide;

	public Map<String, List<String>> getFieldsToHide() {
		return fieldsToHide;
	}

	public void setFieldsToHide(Map<String, List<String>> fieldsToHide) {
		this.fieldsToHide = fieldsToHide;
	}

}
