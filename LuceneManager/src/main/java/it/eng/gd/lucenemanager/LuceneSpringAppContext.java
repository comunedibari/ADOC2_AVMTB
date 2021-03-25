package it.eng.gd.lucenemanager;

import javax.persistence.Entity;
import org.springframework.context.ApplicationContext;

@Entity
public class LuceneSpringAppContext {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		LuceneSpringAppContext.context = context;
	}
	
}
