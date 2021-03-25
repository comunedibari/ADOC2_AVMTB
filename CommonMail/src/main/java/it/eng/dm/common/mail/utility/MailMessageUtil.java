package it.eng.dm.common.mail.utility;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;

public class MailMessageUtil {

	public static String getMessageId(String messageId){
		return (StringUtils.remove(StringUtils.remove(messageId, "<"),">"));
	}

	public static String getMessageId(MimeMessage lMimeMessage) throws MessagingException {
		String[] lStringIdMessages = lMimeMessage.getHeader("Message-ID");
		if (lStringIdMessages.length == 0) return null;
		else {
			String lStrIdMessage = lStringIdMessages[0];
			return getMessageId(lStrIdMessage);
		}
	}
	
	public static String getMessageId(Message lMimeMessage) throws MessagingException {
		String[] lStringIdMessages = lMimeMessage.getHeader("Message-ID");
		if (lStringIdMessages == null) return null;
		if (lStringIdMessages.length == 0) return null;
		else {
			String lStrIdMessage = lStringIdMessages[0];
			return getMessageId(lStrIdMessage);
		}
	}
}
