package it.eng.utility.email.reader.config;

import java.util.Properties;

/**
 * Bean di configurazione dell'account
 * @author michele
 *
 */
public class AccountConfigBean {

	private String accountAddress;
	private Properties accountconfig;
	

	public String getAccountAddress() {
		return accountAddress;
	}
	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}
	public Properties getAccountconfig() {
		return accountconfig;
	}
	public void setAccountconfig(Properties accountconfig) {
		this.accountconfig = accountconfig;
	}	
}