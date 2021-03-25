package it.eng.utility.email.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.eng.aurigamailbusiness.database.listeners.AurigaMailPreInsertEventListener;
import it.eng.aurigamailbusiness.database.listeners.AurigaMailPreUpdateEventListener;
import it.eng.core.business.ReflectionUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.email.bean.MailUiConfigurator;
import it.eng.utility.session.SessionFile;
import it.eng.utility.session.SessionFileConfigurator;

public class SynchroConfig {

	private static Logger logger = LogManager.getLogger(SynchroConfig.class);

	public static void init() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("mailui.xml");
		// configuro i contesti di mailui, mailserver e aurigamailbusiness
		SpringAppContext.setContext(context);
		MailServerSpringAppContext.setContext(context);
		// Inizializzo i datasource
		try {
			// inizializzo hibernate per l'utilizzo di aurigamailbusiness
			initMailBusiness();
		} catch (Exception e) {
			logger.error("Errore inizializzazione SynchroConfig: " + e.getMessage(), e);
			throw e;
		}
	}

	private static void initMailBusiness() throws Exception {
		SessionFileConfigurator lSessionFileConfigurator = (SessionFileConfigurator) SpringAppContext.getContext().getBean("SessionFileConfigurator");
		String consoleType = MailUiConfigurator.getConsoleType();
		if (lSessionFileConfigurator != null && lSessionFileConfigurator.getSessions().size() > 0) {
			for (SessionFile lSessionFile : lSessionFileConfigurator.getSessions()) {
				if (logger.isDebugEnabled()) {
					logger.debug("Aggiungo la session per " + lSessionFile.getSessionName() + " mediante il file " + lSessionFile.getFileName());
				}
				Configuration configuration = new Configuration();
				configuration.configure(lSessionFile.getFileName());
				List<String> listaPackage = new ArrayList<String>();
				listaPackage.add("it.eng.aurigamailbusiness.database.mail");
				if (consoleType != null && consoleType.equalsIgnoreCase("egr")) {
					// inserisco anche le entity specifiche per egr
					listaPackage.add("it.eng.aurigamailbusiness.database.egr");
				}
				it.eng.core.business.HibernateUtil.setListEntityPackage(listaPackage);
				ReflectionUtil.setListEntityPackage(listaPackage);
				configuration.setListener("save", new AurigaMailPreInsertEventListener());
				configuration.setListener("save-update", new AurigaMailPreUpdateEventListener());
				configuration.setListener("update", new AurigaMailPreUpdateEventListener());
				it.eng.core.business.HibernateUtil.addSessionFactory(lSessionFile.getSessionName(), configuration);
				it.eng.core.business.HibernateUtil.addEnte(lSessionFile.getSessionName(), lSessionFile.getSessionName());
			}
		} else {
			// GESTISCO IL DEFAULT
			if (logger.isDebugEnabled()) {
				logger.debug("Aggiungo la session di default");
			}
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			List<String> listaPackage = new ArrayList<String>();
			listaPackage.add("it.eng.aurigamailbusiness.database.mail");
			if (consoleType != null && consoleType.equalsIgnoreCase("egr")) {
				// inserisco anche le entity specifiche per egr
				listaPackage.add("it.eng.aurigamailbusiness.database.egr");
			}
			it.eng.core.business.HibernateUtil.setListEntityPackage(listaPackage);
			ReflectionUtil.setListEntityPackage(listaPackage);
			configuration.setListener("save", new AurigaMailPreInsertEventListener());
			configuration.setListener("save-update", new AurigaMailPreUpdateEventListener());
			configuration.setListener("update", new AurigaMailPreUpdateEventListener());
			it.eng.core.business.HibernateUtil.addSessionFactory("DEFAULT", configuration);
			it.eng.core.business.HibernateUtil.addEnte("DEFAULT", "DEFAULT");
		}
	}
}
