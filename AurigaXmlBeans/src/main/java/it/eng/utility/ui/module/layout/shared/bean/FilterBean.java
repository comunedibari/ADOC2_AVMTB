package it.eng.utility.ui.module.layout.shared.bean;

import java.util.List;

public class FilterBean {

	private List<FilterFieldBean> fields;
	private Boolean notAbleToManageAddFilter;

	public void setFields(List<FilterFieldBean> fields) {
		this.fields = fields;
	}

	public List<FilterFieldBean> getFields() {
		return fields;
	}

	public void setNotAbleToManageAddFilter(Boolean ableToManageAddFilter) {
		this.notAbleToManageAddFilter = ableToManageAddFilter;
	}

	public Boolean getNotAbleToManageAddFilter() {
		return notAbleToManageAddFilter;
	}
	
}
