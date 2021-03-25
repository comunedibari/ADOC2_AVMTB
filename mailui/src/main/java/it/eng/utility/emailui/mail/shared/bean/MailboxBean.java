package it.eng.utility.emailui.mail.shared.bean;

import it.eng.utility.emailui.core.shared.annotation.JSONBean;

/**
 * Bean della mailbox
 * 
 * @author michele
 *
 */
@JSONBean
public class MailboxBean {

	private String accountname;
	private String idmailbox;
	private String description;
	private String name;
	private String processflow;
	private String status;

	// Operazioni e stato
	private String statusprocess;
	private String operationprocess;

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getStatusprocess() {
		return statusprocess;
	}

	public void setStatusprocess(String statusprocess) {
		this.statusprocess = statusprocess;
	}

	public String getOperationprocess() {
		return operationprocess;
	}

	public void setOperationprocess(String operationprocess) {
		this.operationprocess = operationprocess;
	}

	public String getIdmailbox() {
		return idmailbox;
	}

	public void setIdmailbox(String idmailbox) {
		this.idmailbox = idmailbox;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcessflow() {
		return processflow;
	}

	public void setProcessflow(String processflow) {
		this.processflow = processflow;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
