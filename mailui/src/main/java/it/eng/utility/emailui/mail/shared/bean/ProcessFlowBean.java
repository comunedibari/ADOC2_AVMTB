package it.eng.utility.emailui.mail.shared.bean;

import it.eng.utility.emailui.core.shared.annotation.JSONBean;

import java.util.List;

/**
 * Bean che contiene la lista delle operazioni
 * @author michele
 *
 */
@JSONBean
public class ProcessFlowBean {

	private String idmailbox;
	private List<OperationBean> operation;
	private List<OperationTypeBean> operationtype;
		
	public String getIdmailbox() {
		return idmailbox;
	}

	public void setIdmailbox(String idmailbox) {
		this.idmailbox = idmailbox;
	}

	public List<OperationTypeBean> getOperationtype() {
		return operationtype;
	}

	public void setOperationtype(List<OperationTypeBean> operationtype) {
		this.operationtype = operationtype;
	}

	public List<OperationBean> getOperation() {
		return operation;
	}

	public void setOperation(List<OperationBean> operation) {
		this.operation = operation;
	}
}