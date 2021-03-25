package it.eng.utility.email.service;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MimeMessageMailbox extends MimeMessage {

	private static final String SUFFIX_MAIL = ".mailconnect2.0@eng.it";
	
	public MimeMessageMailbox(MimeMessage source) throws MessagingException {
		super(source);
	}
	
	@Override
	protected void updateMessageID() throws MessagingException {
		setHeader("Message-ID", UUID.randomUUID().toString()+SUFFIX_MAIL);
	}
	
}
