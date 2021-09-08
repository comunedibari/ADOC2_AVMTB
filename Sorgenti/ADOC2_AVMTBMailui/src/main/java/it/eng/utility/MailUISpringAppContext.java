package it.eng.utility;

import org.springframework.context.ApplicationContext;

/**
 * Classe contenente il contesto spring.
 * @author Rametta
 *
 */
public class MailUISpringAppContext {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		MailUISpringAppContext.context = context;
	}	
}
