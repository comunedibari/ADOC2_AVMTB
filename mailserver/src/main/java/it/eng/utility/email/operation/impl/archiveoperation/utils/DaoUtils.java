package it.eng.utility.email.operation.impl.archiveoperation.utils;

import java.util.List;

import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.session.SessionFile;
import it.eng.utility.session.SessionFileConfigurator;

public class DaoUtils {

	private DaoUtils() {
		throw new IllegalStateException("Classe di utilità");
	}

	private static final String SESSION_CONFIG_BEAN = "SessionFileConfigurator";

	private static final String PREFIX = "MAILBOX_";

	private static final String DEFAULT = "DEFAULT";

	/**
	 * se è censita la casella utilizzo la sua sessione, se no faccio funzionare
	 * il default
	 * 
	 * @param classeDao
	 * @param idMailbox
	 * @return
	 */
	public static DaoGenericOperations getDao(Class classeDao, String idMailbox) {
		SubjectBean sb = new SubjectBean();
		sb.setIdDominio(getDominioToUse(idMailbox));
		SubjectUtil.subject.set(sb);
		return DaoFactory.getDao(classeDao);
	}

	public static String getDominioToUse(String idMailbox) {
		String name = PREFIX + idMailbox;
		SessionFileConfigurator lSessionFileConfigurator = (SessionFileConfigurator) SpringAppContext.getContext()
				.getBean(SESSION_CONFIG_BEAN);
		List<SessionFile> listaSessioni = lSessionFileConfigurator.getSessions();
		for (SessionFile session : listaSessioni) {
			if (session.getSessionName().equals(name)) {
				return name;
			}
		}
		return DEFAULT;
	}

}
