package it.eng.utility.ui.module.layout.shared.bean;

import java.util.Map;

public class AllFilterSelectBean {

	private Map<String, FilterSelectBean> selects;

	public void setSelects(Map<String, FilterSelectBean> selects) {
		this.selects = selects;
	}

	public Map<String, FilterSelectBean> getSelects() {
		return selects;
	}
}
