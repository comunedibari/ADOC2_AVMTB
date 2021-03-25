package it.eng.utility.email.operation.impl.contentoperation;

/**
 * Definisce il mime dell'attachments
 * @author michele
 *
 */
public class MimeAttachmentBean {

	private String mimetype;
	private String messageid;
	
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getMessageid() {
		return messageid;
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}	
	
}
