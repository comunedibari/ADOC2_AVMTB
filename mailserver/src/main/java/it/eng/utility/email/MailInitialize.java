package it.eng.utility.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.email.scheduler.MailScheduler;

/**
 * Inizializzazione del servizo di mail
 * @author michele
 *
 */
public class MailInitialize {

	static Logger log = LogManager.getLogger(MailInitialize.class);
	
	public static void main(String[] args) throws Exception {
		log.debug("INIZIALIZZAZIONE!!!");
		MailScheduler.initialize();
	}
}