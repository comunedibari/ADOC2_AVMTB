package it.eng.utility.emailui.mail.shared.bean;

import java.util.List;

import it.eng.utility.emailui.core.shared.annotation.JSONBean;

@JSONBean
public class OperationTypeBean {

	private String name;
	private String description;
	private List<OperationTypeConfigBean> configs;

	public List<OperationTypeConfigBean> getConfigs() {
		return configs;
	}

	public void setConfigs(List<OperationTypeConfigBean> configs) {
		this.configs = configs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}