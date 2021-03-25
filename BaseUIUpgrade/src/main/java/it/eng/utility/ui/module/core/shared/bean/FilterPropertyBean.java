package it.eng.utility.ui.module.core.shared.bean;

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;

public class FilterPropertyBean {

	private String name;
	private AdvancedCriteria criteria;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCriteria(AdvancedCriteria criteria) {
		this.criteria = criteria;
	}

	public AdvancedCriteria getCriteria() {
		return criteria;
	}
	
	
}
