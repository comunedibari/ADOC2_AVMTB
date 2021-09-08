package it.eng.utility.emailui.mail.shared.bean;

import it.eng.utility.emailui.core.shared.annotation.JSONBean;

/**
 * Bean della MailconnectConfigBean
 * @author diego
 *
 */
@JSONBean
public class MailconnectConfigBean {

	private String idMailbox;
	private String consoleType;

	public String getIdMailbox() {
		return idMailbox;
	}

	public void setIdMailbox(String idMailbox) {
		this.idMailbox = idMailbox;
	}

	public String getConsoleType() {
		return consoleType;
	}

	public void setConsoleType(String consoleType) {
		this.consoleType = consoleType;
	}
	
	

}
