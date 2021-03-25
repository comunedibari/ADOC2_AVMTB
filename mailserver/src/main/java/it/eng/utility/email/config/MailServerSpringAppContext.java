package it.eng.utility.email.config;

import org.springframework.context.ApplicationContext;

public class MailServerSpringAppContext {
	
	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		MailServerSpringAppContext.context = context;
	}

}
