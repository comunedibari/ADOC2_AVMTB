package it.eng.utility.email.reader;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.reader.config.MailBoxConfigKey;

/**
 * Classe che mette in pausa la lettura della mailbox
 * 
 * @author michele
 *
 */
public class MailboxWait {

	private ExtendImapMailReceiver mailreader = null;
	private ExtendPOP3MailReceiver mailreaderPop;
	private Logger log = LogManager.getLogger(MailboxWait.class);

	/**
	 * Delay di defaut espresso in millisecondi
	 */
	private static final Integer DEFAULT_DELAY = 10000;

	public MailboxWait(ExtendImapMailReceiver mailreader) {
		this.mailreader = mailreader;
	}
	
	public MailboxWait(ExtendPOP3MailReceiver mailreader) {
		this.mailreaderPop = mailreader;
	}


	public void waitRead() {
		if (mailreader != null) {
			// Recupero il delay necessario da db e lo risetto nella configurazioni ad ogni giro in modo da poterlo variare a runtime
			String delays = FactoryMailBusiness.getInstance().getMailBoxConfigParameter(mailreader.getConfigbean().getMailboxid(), MailBoxConfigKey.MAILBOX_DELAY);
			mailreader.getConfigbean().getMailconfig().setProperty(MailBoxConfigKey.MAILBOX_DELAY.keyname(), delays);
			Long delay = new Long(DEFAULT_DELAY);
			Long lLongDelay = new Long(delays);
			GregorianCalendar lGregorianCalendar = new GregorianCalendar();
			int ore = lGregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY);
			int minuti = lGregorianCalendar.get(GregorianCalendar.MINUTE);
			int secondi = lGregorianCalendar.get(GregorianCalendar.SECOND);
			int millisecond = lGregorianCalendar.get(GregorianCalendar.MILLISECOND);
			long actualTime = millisecond + (secondi * 1000) + (minuti * 60 * 1000) + (ore * 60 * 60 * 1000);
			long midnight = lGregorianCalendar.getTimeInMillis() - actualTime;
			long divisor = actualTime / lLongDelay.longValue();
			long startTime = (divisor + 1) * lLongDelay;
			GregorianCalendar calendarToShow = new GregorianCalendar();
			calendarToShow.setTime(new Date(midnight + startTime));
			delay = startTime - actualTime;
			log.info("Delay: " + delay);
			log.error("Avvio la ricerca di nuovi messaggi alle " + calendarToShow.getTime());
			try {
				// Tempo di attesa per il riavvio
				Thread.sleep(delay);
			} catch (Exception e) {
				// In caso di errore rinotifico subito il riavvio
			}
		} else { // pop3
			// Recupero il delay necessario da db e lo risetto nella configurazioni ad ogni giro in modo da poterlo variare a runtime
			String delays = FactoryMailBusiness.getInstance().getMailBoxConfigParameter(mailreaderPop.getConfigbean().getMailboxid(), MailBoxConfigKey.MAILBOX_DELAY);
			mailreaderPop.getConfigbean().getMailconfig().setProperty(MailBoxConfigKey.MAILBOX_DELAY.keyname(), delays);
			Long delay = new Long(DEFAULT_DELAY);
			Long lLongDelay = new Long(delays);
			GregorianCalendar lGregorianCalendar = new GregorianCalendar();
			int ore = lGregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY);
			int minuti = lGregorianCalendar.get(GregorianCalendar.MINUTE);
			int secondi = lGregorianCalendar.get(GregorianCalendar.SECOND);
			int millisecond = lGregorianCalendar.get(GregorianCalendar.MILLISECOND);
			long actualTime = millisecond + (secondi * 1000) + (minuti * 60 * 1000) + (ore * 60 * 60 * 1000);
			long midnight = lGregorianCalendar.getTimeInMillis() - actualTime;
			long divisor = actualTime / lLongDelay.longValue();
			long startTime = (divisor + 1) * lLongDelay;
			GregorianCalendar calendarToShow = new GregorianCalendar();
			calendarToShow.setTime(new Date(midnight + startTime));
			delay = startTime - actualTime;
			log.info("Delay: " + delay);
			log.error("Avvio la ricerca di nuovi messaggi alle " + calendarToShow.getTime());
			try {
				// Tempo di attesa per il riavvio
				Thread.sleep(delay);
			} catch (Exception e) {
				// In caso di errore rinotifico subito il riavvio
			}
		}
		
	}
}