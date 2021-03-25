package it.eng.cryptoutil;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.core.config.ConfigUtil;
import it.eng.module.foutility.fo.FormatRecognitionCtrl;
import it.eng.suiteutility.module.mimedb.entity.TAnagFormatiDig;
// import it.eng.utility.email.scheduler.MailScheduler;

/**
 * Inizializzaione del contesto
 * 
 * @author michele
 *
 */
public class ContextListener implements ServletContextListener {

	private static Logger mLogger = LogManager.getLogger(ContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {

		// MailScheduler.destroy();

		// pulizia del classe loader referenziato dal LogManager, altrimenti potrebbe generare un leak
		// ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		// LogFactory.release(contextClassLoader);

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

		try {
			mLogger.info("Inizio caricamento contesto mailUI");
			ConfigUtil.initialize(event.getServletContext());
			Map<String, TAnagFormatiDig> lMap = new FormatRecognitionCtrl().listAllMimetypes();
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("La mappa contiene " + lMap.size());
			}
			for (String lString : lMap.keySet()) {
				if (mLogger.isDebugEnabled()) {
					mLogger.debug("Caricato " + lMap.get(lString) + " per chiave " + lString);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Fatal initizalizion Business Runtime", e);
		}
	}

}
