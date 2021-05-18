package it.eng.utility.emailui.core.shared.message;

import it.eng.utility.emailui.core.shared.annotation.JSONBean;


/**
 * MessageBean contenente i messaggi del server
 * @author michele
 *
 */
@JSONBean
public class MessageBean {

	private String message;
	private String detailmessage;
	private MessageType type;
		
	public MessageBean(String message, String detailmessage, MessageType type) {
		super();
		this.message = message;
		this.detailmessage = detailmessage;
		this.type = type;
	}
	
	public MessageBean() {
		// TODO Auto-generated constructor stub
	}
	
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetailmessage() {
		return detailmessage;
	}
	public void setDetailmessage(String detailmessage) {
		this.detailmessage = detailmessage;
	}
}