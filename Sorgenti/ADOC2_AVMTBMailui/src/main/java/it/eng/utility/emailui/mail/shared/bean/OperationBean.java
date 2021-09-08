package it.eng.utility.emailui.mail.shared.bean;

import it.eng.utility.emailui.core.shared.annotation.JSONBean;

import java.util.List;

@JSONBean
public class OperationBean {

	private String idmailbox;
	private String operationnumparent;
	private String operationnum;
	private String operationname;
	private String operationdescription;
	private String expression;	
	private List<OperationTypeConfigBean> configuration;	
	
	public String getIdmailbox() {
		return idmailbox;
	}

	public void setIdmailbox(String idmailbox) {
		this.idmailbox = idmailbox;
	}

	public List<OperationTypeConfigBean> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(List<OperationTypeConfigBean> configuration) {
		this.configuration = configuration;
	}

	public String getOperationnumparent() {
		return operationnumparent;
	}

	public void setOperationnumparent(String operationnumparent) {
		this.operationnumparent = operationnumparent;
	}

	public String getOperationnum() {
		return operationnum;
	}

	public void setOperationnum(String operationnum) {
		this.operationnum = operationnum;
	}

	public String getOperationname() {
		return operationname;
	}

	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}

	public String getOperationdescription() {
		return operationdescription;
	}

	public void setOperationdescription(String operationdescription) {
		this.operationdescription = operationdescription;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	
	
}
