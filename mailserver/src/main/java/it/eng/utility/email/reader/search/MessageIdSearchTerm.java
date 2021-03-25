package it.eng.utility.email.reader.search;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

import it.eng.aurigamailbusiness.utility.MailUtil;

/**
 * Classe di ricerca che identifica se messaggio dall'id
 * 
 * @author michele
 *
 */
public class MessageIdSearchTerm extends SearchTerm {

	private static final long serialVersionUID = 1L;

	private String msgid;

	public MessageIdSearchTerm(String msgid) {
		this.msgid = msgid;
	}

	@Override
	public boolean match(Message msg) {
		try {
			if (MailUtil.getMessageID(msg).equals(msgid)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}
}