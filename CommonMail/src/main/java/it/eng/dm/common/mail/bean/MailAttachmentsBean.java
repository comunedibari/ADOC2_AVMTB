package it.eng.dm.common.mail.bean;

import it.eng.core.annotation.Attachment;
import it.eng.core.business.beans.AbstractBean;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Bean che indica l'attachments sulla mail
 * 
 * 
 */
@XmlRootElement
public class MailAttachmentsBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -1206659064167411605L;
	
	private String messageid;
	private String filename;
	private String mimetype;
	private String contentID;
	private String disposition;
	
	private Long size;

	@XmlTransient
	@Attachment
	private File file;

	@XmlTransient
	private byte[] data;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getContentID() {
		return contentID;
	}

	public void setContentID(String contentID) {
		this.contentID = contentID;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	

	
}
