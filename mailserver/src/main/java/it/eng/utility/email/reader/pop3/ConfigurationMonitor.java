package it.eng.utility.email.reader.pop3;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.utility.MailUtil;
import it.eng.utility.email.LogUtility;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.process.MessageElaborateChannel;
import it.eng.utility.email.process.ProcessOrchestrator;
import it.eng.utility.email.process.MessageElaborateChannel.MessageCallable;
import it.eng.utility.email.reader.ExtendPOP3MailReceiver;
import it.eng.utility.email.reader.MessageStatus;
import it.eng.utility.email.reader.OperationStatus;

public class ConfigurationMonitor implements Runnable {

    private static final Logger log = LogManager.getLogger(ConfigurationMonitor.class);
    // @Scheduled(fixedRate = 300000)
    private ExtendPOP3MailReceiver mailreceiver;
        
    public ConfigurationMonitor(ExtendPOP3MailReceiver mailreceiver) {
		super();
		this.mailreceiver = mailreceiver;
	}


	public void loadConfiguration() {
        if (log.isDebugEnabled()){
            log.debug("Verifico se i dati di configurazione sono stati aggiornati...");
           } 
            try {
            	// inserisco una routing key in modo che ogni mailbox abbia
				// il proprio log
				ThreadContext.put("ROUTINGKEY", mailreceiver.getConfigbean().getMailboxid());
				// assegno nome al thread
				Thread.currentThread().setName("Thread-receive-" + mailreceiver.getConfigbean().getMailboxid());
				String idMailbox = mailreceiver.getConfigbean().getMailboxid();
				MessageElaborateChannel outputChannel = new MessageElaborateChannel(
						ProcessOrchestrator.newInstance(idMailbox), mailreceiver);
				for (Message message : mailreceiver.receive() ) {
					String messageId = MailUtil.getInstance((MimeMessage) message).getMessageID();;
					
					// nuovo thread asincrono per gestire il timeout nel processamento del messaggio

					
					MessageCallable messageCallable = outputChannel.new MessageCallable();
					messageCallable.setMimeMessage((MimeMessage) message);
					messageCallable.setMessageId(messageId);
					messageCallable.setMailboxId(idMailbox);

					ExecutorService executorService = Executors.newSingleThreadExecutor();

					Future<Boolean> futureResult = executorService.submit(messageCallable);

					try {
						futureResult.get(10, TimeUnit.MINUTES);
					} catch (TimeoutException e) {
						// reimposto la routingkey visto che con il thread interno potrebbe essere stata rimossa
						ThreadContext.put("ROUTINGKEY", idMailbox);
						futureResult.cancel(true); // setto Interrupt nel thread, l'eccezione sarà gestita dai metodi del ProcessOrchestrator e rilanciata
						log.error("Timeout Exception durante il processamento del messaggio con id: " + messageId, e);
					} catch (ExecutionException e) {
						// reimposto la routingkey visto che con il thread interno potrebbe essere stata rimossa
						ThreadContext.put("ROUTINGKEY", idMailbox);
						futureResult.cancel(true);
						log.error("ExecutionException durante il processamento del messaggio con id: " + messageId, e);
					}

					finally {
						// chiudo il pool, attendendo eventualmente il messaggio di errore
						shutdownAndAwaitTermination(executorService);
						// gestisco eventuale eccezione sollevata nel processamento del messaggio
						if (messageCallable.getError() != null) {
							setErrorProcessingMessage(idMailbox, messageId, messageCallable.getError());
						}

					}

				}				
				mailreceiver.waitForNewMessages();
			} catch (Throwable e) {
				log.error(e);
				e.printStackTrace();
			} 
			
        
    }
	
	private void shutdownAndAwaitTermination(ExecutorService pool) throws Exception {
		pool.shutdown(); // Impedisco l'avvio di nuovi task
		try {
			// Attendo che il task sia interrotto. Nel process orchestrator e nei singoli messaggi sono presenti dei controlli sull'interruzione del thread
			if (!pool.awaitTermination(90, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Forzo nuovamente la cancellazione del thread
				// Ricontrollo lo stato del task
				if (!pool.awaitTermination(90, TimeUnit.SECONDS)) {
					log.error("ThreadPool non interrotto");
					throw new Exception("ThreadPool non interrotto");
				}
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Mantengo lo stato di interruzione
			Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * Metodo che imposta lo stato di errore in caso il processamento sia andato in errore
	 * 
	 * @param mailboxId
	 * @param messageId
	 * @param throwable
	 */

	private void setErrorProcessingMessage(String mailboxId, String messageId, Throwable throwable) {

		try {

			// loggo eventuali errori Hibernate
			LogUtility.logHibernateException(throwable);

			// Converto lo stacktrace in stringa
			String error = ExceptionUtils.getStackTrace(throwable);

			// Verifico le operazioni associate al messaggio

			MailboxMessage messaggio = FactoryMailBusiness.getInstance().getMailBoxMessageWithOperations(messageId, mailboxId);

			Set<MailboxMessageOperation> messageOperations = messaggio.getMailboxMessageOperations();

			if (messageOperations == null || messageOperations.isEmpty()) {
				// nessuna operazione associata al messaggio
				// significa che si e' verificato un errore nella verifica
				// del messaggio o prima di iniziare le operazioni del messaggio
				// imposto in discharged error ma non cancello il file
				// associato, in questo modo provo a riprocessare il
				// messaggio

				FactoryMailBusiness.getInstance().updateStatusMimeMessage(messageId, mailboxId, MessageStatus.MESSAGE_IN_ERROR_DISCHARGED, throwable);
				log.debug("Nessuna operazione eseguita - imposto lo status di " + MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.name());

			} else {
				FactoryMailBusiness.getInstance().updateStatusMimeMessage(messageId, mailboxId, MessageStatus.MESSAGE_IN_ERROR_OPERATION, throwable);
				for (MailboxMessageOperation operation : messageOperations) {
					String operationId = operation.getId().getIdMailboxoperation();
					if (operation.getOperationStatus().equals(OperationStatus.OPERATION_IN_PROGRESS.status())) {
						// setto l'operazione in errore
						FactoryMailBusiness.getInstance().saveOrUpdateOperation(operationId, messageId, OperationStatus.OPERATION_ERROR, error, mailboxId);
						log.error("Operazione in errore: " + operationId);
						break;
					}
				}

			}
		} catch (Throwable er) {
			log.error("Errore aggiornamento stato operazione in errore in database: " + throwable.getMessage(), throwable);
		}
	}



    public ExtendPOP3MailReceiver getMailreceiver() {
		return mailreceiver;
	}


	public void setMailreceiver(ExtendPOP3MailReceiver mailreceiver) {
		this.mailreceiver = mailreceiver;
	}


	@Override
    public void run() {
        try {
            loadConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
    }
	
	
}

