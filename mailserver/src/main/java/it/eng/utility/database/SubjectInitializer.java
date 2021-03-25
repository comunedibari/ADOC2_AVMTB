package it.eng.utility.database;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.email.bean.MailUiConfigurator;
import it.eng.utility.session.SessionFile;
import it.eng.utility.session.SessionFileConfigurator;

public class SubjectInitializer {

	private static final String SESSION_CONFIG_BEAN = "SessionFileConfigurator";

	private static final String PREFIX = "MAILBOX_";

	private static final String DEFAULT = "DEFAULT";

	public static Session getSession(String idMailbox) throws Exception {
		init(idMailbox);
		return HibernateUtil.begin();
	}

	/**
	 * Restituisce la connessione per quell'ente
	 * 
	 * @param idMailbox
	 * @param ente
	 * @return
	 * @throws Exception
	 */
	public static Session getSession(String url, String user, String password) throws Exception {
		// Create the SessionFactory from hibernate.cfg.xml
		Configuration configuration = new Configuration();
		configuration.configure();
		configuration.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
		configuration.setProperty("hibernate.connection.url", url);
		configuration.setProperty("hibernate.connection.username", user);
		configuration.setProperty("hibernate.connection.password", password);
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		configuration.setProperty("hibernate.show_sql", "false");
		Reflections reflections = new Reflections("it.eng.aurigamailbusiness.database.mail");
		Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
		Iterator<Class<?>> iteratore = entities.iterator();
		while (iteratore.hasNext()) {
			configuration.addAnnotatedClass(iteratore.next());
		}
		String consoleType = MailUiConfigurator.getConsoleType();
		if (consoleType != null && consoleType.equalsIgnoreCase("egr")) {
			reflections = new Reflections("it.eng.aurigamailbusiness.database.mail");
			entities = reflections.getTypesAnnotatedWith(Entity.class);
			iteratore = entities.iterator();
			while (iteratore.hasNext()) {
				configuration.addAnnotatedClass(iteratore.next());
			}
		}
		return configuration.buildSessionFactory().openSession();
	}

	public static void init(String idMailbox) {
		SubjectBean sb = new SubjectBean();
		sb.setIdDominio(getDominioToUse(idMailbox));
		SubjectUtil.subject.set(sb);
	}

	public static String getDominioToUse(String idMailbox) {
		if (StringUtils.isEmpty(idMailbox))
			return DEFAULT;
		String name = PREFIX + idMailbox;
		SessionFileConfigurator lSessionFileConfigurator = (SessionFileConfigurator) SpringAppContext.getContext().getBean(SESSION_CONFIG_BEAN);
		List<SessionFile> listaSessioni = lSessionFileConfigurator.getSessions();
		for (SessionFile session : listaSessioni) {
			if (session.getSessionName().equals(name)) {
				return name;
			}
		}
		return DEFAULT;
	}
}
