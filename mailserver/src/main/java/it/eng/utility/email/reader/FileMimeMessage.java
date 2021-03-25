package it.eng.utility.email.reader;

import java.io.InputStream;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class FileMimeMessage extends MimeMessage {

	private InputStream filemime; 	
	private byte[] data;
	
	public FileMimeMessage(Session session) {
		super(session);
	}
	
	public FileMimeMessage(Session session, InputStream pInputStream) throws MessagingException {
		super(session, pInputStream);
	}

	public InputStream getFilemime() {
		return filemime;
	}

	public void setFilemime(InputStream filemime) {
		this.filemime = filemime;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	

	
}