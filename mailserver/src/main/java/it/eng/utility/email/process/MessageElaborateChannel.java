package it.eng.utility.email.process;

import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.utility.MailUtil;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.utility.email.LogUtility;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.reader.ExtendImapMailReceiver;
import it.eng.utility.email.reader.ExtendPOP3MailReceiver;
import it.eng.utility.email.reader.FileMimeMessage;
import it.eng.utility.email.reader.MessageStatus;
import it.eng.utility.email.reader.OperationStatus;

/**
 * Classe di elaborazione iniziale del messaggio mail
 * 
 * @author michele
 *
 */
public class MessageElaborateChannel implements MessageChannel {

	Logger log = LogManager.getLogger(MessageElaborateChannel.class);

	private ProcessOrchestrator orchestrator;
	private ExtendImapMailReceiver receiver;
	private ExtendPOP3MailReceiver receiverPop3;

	public MessageElaborateChannel(ProcessOrchestrator orchestrator, ExtendImapMailReceiver pExtendImapMailReceiver) {
		this.orchestrator = orchestrator;
		this.receiver = pExtendImapMailReceiver;
	}
	
	public MessageElaborateChannel(ProcessOrchestrator orchestrator, ExtendPOP3MailReceiver pExtendPOP3MailReceiver) {
		this.orchestrator = orchestrator;
		this.receiverPop3 = pExtendPOP3MailReceiver;
	}

	public final class MessageCallable implements Callable<Boolean> {

		private MimeMessage mimeMessage;
		private String messageId;
		private String mailboxId;
		private Throwable error;

		public void setMimeMessage(MimeMessage mimeMessage) {
			this.mimeMessage = mimeMessage;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}

		public void setMailboxId(String mailboxId) {
			this.mailboxId = mailboxId;
		}

		@Override
		public Boolean call() {

			try {
				return MessageElaborateChannel.this.process(mimeMessage, mailboxId, messageId);
			} catch (Throwable e) {
				// salvo l'eccezione per salvarla in database
				error = e;
				// ritorno comunque true in modo da procedere con il messaggio successivo
				return true;
			}

		}

		public Throwable getError() {
			return error;
		}

		public void setError(Throwable error) {
			this.error = error;
		}

	}

	/**
	 * Processamento del messaggio in input
	 * 
	 * @param mimeMessage
	 * @param messageId
	 * @return
	 * @throws Throwable
	 */

	private boolean process(MimeMessage mimeMessage, String mailboxId, String messageId) throws Throwable {

		// reimposto la routingkey visto che potrebbe essere stato creato un nuovo thread
		ThreadContext.put("ROUTINGKEY", mailboxId);

		// assegno un nome al thread
		Thread.currentThread().setName("Thread-process-" + mailboxId + "-" + messageId);

		log.info("Avvio processamento del messaggio con id " + messageId);
		// Passo il messaggio all'orchestratore e avvio il processo
		this.orchestrator.processing(mimeMessage);

		return true; // in tutti i casi restituisco true in modo da procedere con i successivi messaggi in coda

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

	@Override
	public boolean send(Message<?> message, long timeout) {
		return sendMessage(message, timeout);
	}

	@Override
	public boolean send(Message<?> message) {
		return sendMessage(message, MessageChannel.INDEFINITE_TIMEOUT);
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
	 * Metodo per il processamento del messaggio con gestione del timeout, se valorizzato
	 * 
	 * @param message
	 * @param timeout
	 * @return
	 */

	public boolean sendMessage(Message<?> message, long timeout) {

		String messageId = null;
		String idMailbox = this.orchestrator.getIdMailbox();

		try {

			MimeMessage mimeMessage = null;

			// istanzio una nuova sessione con le proprietà comuni
			Session mailSession = Session.getInstance(Util.getJavaMailDefaultProperties());
			if (message instanceof FileMimeMessage) {
				InputStream input = ((FileMimeMessage) message).getFilemime();
				mimeMessage = new MimeMessage(mailSession, input);
				messageId = MailUtil.getInstance(mimeMessage).getMessageID();
			} else if (message instanceof MimeMessage) {
				mimeMessage = (MimeMessage) message;
				messageId = MailUtil.getInstance(mimeMessage).getMessageID();
			} else if (message instanceof GenericMessage<?>) {
				mimeMessage = ((GenericMessage<MimeMessage>) message).getPayload();
				messageId = MailUtil.getInstance(mimeMessage).getMessageID();
			} else {
				throw new Exception("Messaggio di tipo " + message.getClass() + " non processabile!");
			}

			// creo la directory temporanea per il messaggio che voglio processare
			this.orchestrator.createWorkingDirectoryMessage(idMailbox, messageId);

			if (timeout == MessageChannel.INDEFINITE_TIMEOUT) {
				// nessun timeout impostato, non definisco un thread specifico
				try {
					return process(mimeMessage, idMailbox, messageId);
				} catch (Exception e) {
					setErrorProcessingMessage(idMailbox, messageId, e);
				}
			}

			// nuovo thread asincrono per gestire il timeout nel processamento del messaggio

			MessageCallable messageCallable = new MessageCallable();
			messageCallable.setMimeMessage(mimeMessage);
			messageCallable.setMessageId(messageId);
			messageCallable.setMailboxId(idMailbox);

			ExecutorService executorService = Executors.newSingleThreadExecutor();

			Future<Boolean> futureResult = executorService.submit(messageCallable);

			try {
				return futureResult.get(timeout, TimeUnit.MINUTES);
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

		} catch (Throwable e) {

			// reimposto la routingkey visto che con il thread interno potrebbe essere stata rimossa
			ThreadContext.put("ROUTINGKEY", idMailbox);

			log.error("MessageElaborateChannel - Errore sendMessage", e);

		} finally {

			// reimposto la routingkey visto che con il thread interno potrebbe essere stata rimossa
			ThreadContext.put("ROUTINGKEY", idMailbox);

			// pulisco la directory di lavoro del messaggio
			// non vengono sollevate eccezioni in caso di errore
			this.orchestrator.clearWorkingDirectoryMessage(messageId);

			log.debug("Messaggio con id " + messageId + " da rimuovere dalla lista dei messaggi da processare");
			if (StringUtils.isEmpty(messageId)) {
				// a questo punto il message id non può mai essere vuoto, se lo
				// è significa che si è verificato prcedentemente un errore
				// grave
				// in questo caso quindi conviene pulire la lista dei messaggi
				// da processare e riprendere lo scarico
				log.error("Errore nella rimozione del messaggio dalla lista di elaborazione: il messageId è null");
				if (receiver != null) {
					receiver.clearActualMessages();
				}
				if (receiverPop3 != null) {
					receiverPop3.clearActualMessages();
				}
			} else {
				if (receiver != null) {
					receiver.messageProcessed(messageId);
				}
				if (receiverPop3 != null) {
					receiverPop3.messageProcessed(messageId);
				}				
			}
		}
		return true; // in tutti i casi restituisco true in modo da procedere con i successivi messaggi in coda

	}

}