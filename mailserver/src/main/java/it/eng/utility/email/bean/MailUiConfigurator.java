package it.eng.utility.email.bean;


public class MailUiConfigurator {

	private static String mailConnectId;
	private static String consoleType;
	

	public static String getMailConnectId() {
		return mailConnectId;
	}

	public static void setMailConnectId(String mailConnectId) {
		MailUiConfigurator.mailConnectId = mailConnectId;
	}

	public static String getConsoleType() {
		return consoleType;
	}

	public static void setConsoleType(String consoleType) {
		MailUiConfigurator.consoleType = consoleType;
	}

	
}
