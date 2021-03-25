package it.eng.utility.ui.module.core.client.history;

import it.eng.utility.ui.module.core.shared.annotation.JSONBean;

import java.util.ArrayList;
import java.util.List;

@JSONBean
public class PortletHistory {
	
	
	private List<HistoryBean> portlets = new ArrayList<HistoryBean>();

	public List<HistoryBean> getPortlets() {
		return portlets;
	}

	public void setPortlets(List<HistoryBean> portlets) {
		this.portlets = portlets;
	}
	
	
	
}
