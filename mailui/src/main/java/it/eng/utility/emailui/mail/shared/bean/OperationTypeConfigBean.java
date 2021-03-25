package it.eng.utility.emailui.mail.shared.bean;

import it.eng.utility.emailui.core.shared.annotation.JSONBean;

@JSONBean
public class OperationTypeConfigBean {

	private String key;
	private String title;
	private String description;
	private String type;
	private String value;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	

}

