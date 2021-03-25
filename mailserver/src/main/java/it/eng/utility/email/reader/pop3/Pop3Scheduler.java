package it.eng.utility.email.reader.pop3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.email.reader.ExtendPOP3MailReceiver;


public class Pop3Scheduler {

	private static final Logger log = LogManager.getLogger(Pop3Scheduler.class);
	
	private AtomicBoolean isActive = new AtomicBoolean(false);
	
	private ScheduledExecutorService scheduler = null;
	
	 public void schedula(ExtendPOP3MailReceiver mailreceiver, long delay) {
		 if (!getIsActive()) {
			 scheduler = Executors.newScheduledThreadPool(1);
	    	 Runnable task1 = new ConfigurationMonitor(mailreceiver);
	         log.info("Avvio schedulazione del processo ConfigurationMonitor.");
	         scheduler.scheduleAtFixedRate(task1, 1, delay, java.util.concurrent.TimeUnit.MILLISECONDS);
	         isActive.set(true);
		 } else {
			 log.debug("ExtendPOP3MailReceiver già schedulato.");
		 }
		 
    }
	 
	public void closeScheduler() {
		if (scheduler != null) 
			scheduler.shutdown();
		isActive.set(false);
	}

	public boolean getIsActive() {
		return isActive.get();
	}

	public void setIsActive(boolean isActive) {
		this.isActive.set(isActive);
	}
	
}
