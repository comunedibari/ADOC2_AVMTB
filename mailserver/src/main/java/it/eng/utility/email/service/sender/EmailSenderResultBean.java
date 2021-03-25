package it.eng.utility.email.service.sender;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * Bean di ritorno dell'invio mail contenente l'esito e l'eventuale
 * risultato 
 * @author michele
 *
 */
@XmlRootElement
public class EmailSenderResultBean {

	private String error;
	private String errormessage;
	private Boolean isSent;
	private Boolean isSave;
	private List<EmailMessageResultBean> messages;
	private Boolean isPecAccount;
		
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	public Boolean getIsPecAccount() {
		return isPecAccount;
	}
	public void setIsPecAccount(Boolean isPecAccount) {
		this.isPecAccount = isPecAccount;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Boolean getIsSent() {
		return isSent;
	}
	public void setIsSent(Boolean isSent) {
		this.isSent = isSent;
	}
	public Boolean getIsSave() {
		return isSave;
	}
	public void setIsSave(Boolean isSave) {
		this.isSave = isSave;
	}
	public List<EmailMessageResultBean> getMessages() {
		return messages;
	}
	public void setMessages(List<EmailMessageResultBean> messages) {
		this.messages = messages;
	}
}