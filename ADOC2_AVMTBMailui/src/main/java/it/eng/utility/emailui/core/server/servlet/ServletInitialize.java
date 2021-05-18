package it.eng.utility.emailui.core.server.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.eng.aurigamailbusiness.database.listeners.AurigaMailPreInsertEventListener;
import it.eng.aurigamailbusiness.database.listeners.AurigaMailPreUpdateEventListener;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.core.business.ReflectionUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.util.ListUtil;
import it.eng.utility.MailUISpringAppContext;
import it.eng.utility.email.bean.MailUiConfigurator;
import it.eng.utility.email.config.MailServerSpringAppContext;
import it.eng.utility.email.scheduler.DeleteScheduler;
import it.eng.utility.email.scheduler.MailScheduler;
import it.eng.utility.emailui.core.server.datasource.SingletonDataSource;
import it.eng.utility.session.SessionFile;
import it.eng.utility.session.SessionFileConfigurator;

/**
 * Servlet di init per istanziare il server ADempiere
 * 
 * @author michele
 * 
 */
public class ServletInitialize extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(ServletInitialize.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// Inizializzo il contesto
		try {

			// imposto il classloader, in alcuni casi si hanno errori nel parsing dei Multipart e nel caricamento della classi BouncyCastle
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("mailui.xml");
			// configuro i contesti di mailui, mailserver e aurigamailbusiness
			MailUISpringAppContext.setContext(context);
			SpringAppContext.setContext(context);
			MailServerSpringAppContext.setContext(context);
			it.eng.spring.utility.SpringAppContext.setContext(context);
			// Inizializzo i datasource
			SingletonDataSource.getInstance().initialize();

			// configurazioni comuni JavaMail da applicare al sistema
			Util.setJavaMailDefaultProperties();

		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw new ServletException(e);
		}
		try {
			initMailBusiness();
			// inializzo lo scarico delle mailbox. Verifico se si sono riscontrati errori in fase di inizializzazione
			Boolean startWithoutError = MailScheduler.initialize();
			if (startWithoutError) {
				logger.info("Contesto mailUI avviato con successo");
			} else {
				logger.warn("Contesto mailUI avviato con alcuni errori - Verificare l'inizializzazione delle singole caselle");
			}
		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw new ServletException(e);
		}

		try {
			// se configurata avvio cancellazione in modalità asincrona
			DeleteScheduler deleteSchedulerBean = SpringAppContext.getContext().getBean(DeleteScheduler.class);
			deleteSchedulerBean.startDeleteScheduler();
		} catch (NoSuchBeanDefinitionException e) {
			logger.info("Cancellazione asincrona non è configurata");
		} catch (Exception e) {
			logger.error("Errore avvio cancellazione asincrona" + e.getMessage(), e);
			throw new ServletException(e);
		}

	}

	private void initMailBusiness() throws Exception {
		SessionFileConfigurator lSessionFileConfigurator = (SessionFileConfigurator) SpringAppContext.getContext().getBean("SessionFileConfigurator");
		String consoleType = MailUiConfigurator.getConsoleType();
		if (ListUtil.isNotEmpty(lSessionFileConfigurator.getSessions())) {
			for (SessionFile lSessionFile : lSessionFileConfigurator.getSessions()) {
				logger.debug("Aggiungo la session per " + lSessionFile.getSessionName() + " mediante il file " + lSessionFile.getFileName());
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
		} else

		{
			// GESTISCO IL DEFAULT
			logger.debug("Aggiungo la session di default");
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

	public void contextDestroyed() {
		this.destroy();
	}

}
