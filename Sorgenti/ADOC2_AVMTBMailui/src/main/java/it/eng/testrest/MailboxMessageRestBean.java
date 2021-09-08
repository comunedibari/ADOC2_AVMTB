package it.eng.testrest;

import java.util.Date;

public class MailboxMessageRestBean {

	private String messageId;
	private String idMailbox;
	private String status;
	private String statusMessage;
	private String urlMime;
	private Date dateDischarged;
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getIdMailbox() {
		return idMailbox;
	}
	public void setIdMailbox(String idMailbox) {
		this.idMailbox = idMailbox;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getUrlMime() {
		return urlMime;
	}
	public void setUrlMime(String urlMime) {
		this.urlMime = urlMime;
	}
	public Date getDateDischarged() {
		return dateDischarged;
	}
	public void setDateDischarged(Date dateDischarged) {
		this.dateDischarged = dateDischarged;
	}
	
	
}
