package it.eng.utility.email.operation.impl.sendmailoperation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean contenente l'informazione di riusciuta copia del messaggio
 * 
 * @author michele
 *
 */
@XmlRootElement
public class SendMailMessageBean {

	private Boolean sendok;
	private Boolean saveok;
	private Boolean alreadyreply;
	private String replyto;
	private String sendfrom;

	public String getReplyto() {
		return replyto;
	}

	public void setReplyto(String replyto) {
		this.replyto = replyto;
	}

	public Boolean getAlreadyreply() {
		return alreadyreply;
	}

	public void setAlreadyreply(Boolean alreadyreply) {
		this.alreadyreply = alreadyreply;
	}

	public Boolean getSendok() {
		return sendok;
	}

	public void setSendok(Boolean sendok) {
		this.sendok = sendok;
	}

	public Boolean getSaveok() {
		return saveok;
	}

	public void setSaveok(Boolean saveok) {
		this.saveok = saveok;
	}

	public String getSendfrom() {
		return sendfrom;
	}

	public void setSendfrom(String sendfrom) {
		this.sendfrom = sendfrom;
	}
}