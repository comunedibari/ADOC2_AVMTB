package it.eng.utility.email.reader.config;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Classe di configurazione del bean
 * @author michele
 *
 */
public class MailConfiguratorBean {

	private String mailboxid;
	private Properties mailconfig;
	private AccountConfigBean account;
	private String folder;
	private BigDecimal sendTimeout; // durata massima in minuti per l'elaborazione di un singolo messaggio
	
	public AccountConfigBean getAccount() {
		return account;
	}

	public void setAccount(AccountConfigBean account) {
		this.account = account;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public Properties getMailconfig() {
		return mailconfig;
	}

	public void setMailconfig(Properties mailconfig) {
		this.mailconfig = mailconfig;
	}

	public String getMailboxid() {
		return mailboxid;
	}

	public void setMailboxid(String mailboxid) {
		this.mailboxid = mailboxid;
	}

	public BigDecimal getSendTimeout() {
		return sendTimeout;
	}

	public void setSendTimeout(BigDecimal sendTimeout) {
		this.sendTimeout = sendTimeout;
	}
}