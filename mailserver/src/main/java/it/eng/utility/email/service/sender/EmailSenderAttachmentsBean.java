package it.eng.utility.email.service.sender;


/**
 * Bean indicante gli attachment da inviare
 * @author michele
 */
public class EmailSenderAttachmentsBean {

	private String filename;
	private byte[] content;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	
	
}