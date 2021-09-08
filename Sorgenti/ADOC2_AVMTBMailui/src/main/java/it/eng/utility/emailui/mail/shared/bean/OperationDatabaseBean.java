package it.eng.utility.emailui.mail.shared.bean;

import it.eng.utility.emailui.core.shared.annotation.JSONBean;

@JSONBean
public class OperationDatabaseBean {

	private Boolean iscreate;
	private String operation;
	private String message;
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getIscreate() {
		return iscreate;
	}
	public void setIscreate(Boolean iscreate) {
		this.iscreate = iscreate;
	}

	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
}