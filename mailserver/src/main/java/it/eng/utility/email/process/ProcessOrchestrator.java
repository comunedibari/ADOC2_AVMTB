package it.eng.utility.email.process;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.internet.MimeMessage;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.utility.MailUtil;
import it.eng.aurigamailbusiness.utility.mail.MailVerifierException;
import it.eng.utility.email.config.MailServerSpringAppContext;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.impl.deletemessageoperation.DeleteMessageBean;
import it.eng.utility.email.process.exception.MailServerException;
import it.eng.utility.email.process.exception.PlannedOperation;
import it.eng.utility.email.process.exception.TryNumException;
import it.eng.utility.email.reader.ExtendImapMailReceiver;
import it.eng.utility.email.reader.ExtendPOP3MailReceiver;
import it.eng.utility.email.reader.MessageStatus;
import it.eng.utility.email.reader.OperationStatus;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.scheduler.MailScheduler;
import it.eng.utility.email.util.mail.MailVerifier;
import it.eng.utility.email.util.statusMessage.StatusMessage;
import it.eng.utility.email.util.xml.XmlUtil;
import it.eng.utility.storageutil.impl.filesystem.util.EnvironmentVariableConfigManager;

/**
 * Orchestratore del processo di elaborazione email
 * 
 * @author michele
 *
 */

public class ProcessOrchestrator {

	Logger log = LogManager.getLogger(ProcessOrchestrator.class);

	private ProcessOrchestrator() {
		// Costruttore privato non visibile esteramente
	}

	/**
	 * Operazioni configurate nell'applicativo
	 */
	private static Map<String, Class<?>> operations = new ConcurrentHashMap<String, Class<?>>();

	/**
	 * Variabile che contiene il processo di controllo
	 */
	private ProcessOperationFlow processflow = null;

	/**
	 * Id Mailbox
	 */
	private String idMailbox;

	/**
	 * Directory di appoggio delle mail
	 */
	private File mailDir;

	/**
	 * Directory di lavoro della mail
	 */
	private File workMailDir;

	/**
	 * Directory di lavoro della singola mail
	 */

	private File workMailDirMessage;

	/**
	 * Reader della mailbox
	 */
	private ExtendImapMailReceiver receiver;
	private ExtendPOP3MailReceiver receiverPop3;

	public String getIdMailbox() {
		return idMailbox;
	}

	/**
	 * Crea e carica una nuova istanza del processo di orchestrazione della mail
	 * 
	 * @param idmailbox
	 * @return
	 * @throws Exception
	 */
	public static ProcessOrchestrator newInstance(String idmailbox) throws Exception {
		// Recupero ed istanzio il processo di controllo
		ProcessOrchestrator orchestrator = new ProcessOrchestrator();
		FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();
		orchestrator.processflow = mailBusiness.getProcessFlow(idmailbox);
		orchestrator.idMailbox = idmailbox;
		if (orchestrator.receiver == null) {
			orchestrator.receiver = MailScheduler.getImapMailReceivers().get(idmailbox);			
		}
		if (orchestrator.receiverPop3 == null) {
			orchestrator.receiverPop3 = MailScheduler.getPOP3MailReceivers().get(idmailbox);
		}
		String directoryConfigValue = mailBusiness.getMailBoxConfigParameter(idmailbox,
				MailBoxConfigKey.MAILBOX_DIRECTORY);
		orchestrator.mailDir = new File(
				EnvironmentVariableConfigManager.replaceEnvironmentVariable(directoryConfigValue));
		if (!orchestrator.mailDir.exists()) {
			boolean iscreate = orchestrator.mailDir.mkdirs();
			if (!iscreate) {
				throw new Exception("Directory della mailbox " + idmailbox + " non creata!");
			}
		}

		// Creo la cartella temporanea di lavoro della singola mailbox
		orchestrator.workMailDir = new File(orchestrator.mailDir, "WORKMAIL");
		if (!orchestrator.workMailDir.exists()) {
			boolean iscreate = orchestrator.workMailDir.mkdirs();
			if (!iscreate) {
				throw new Exception("Directory temporanea della mailbox " + idmailbox + " non creata!");
			}
		}

		return orchestrator;
	}

	/**
	 * Metodo di elaborazione del messaggio in arrivo dalla mailbox
	 * 
	 * @param idMailbox
	 * @param message
	 * @throws Throwable
	 */
	public void processing(MimeMessage message) throws Throwable {

		String idMessaggio = MailUtil.getInstance(message).getMessageID();
		log.debug("Inizio processamento del messaggio: " + idMessaggio);
		FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();

		try {

			MailboxMessage mailboxMessage = null;

			try {
				mailboxMessage = mailBusiness.getMailBoxMessage(idMessaggio, this.idMailbox);
			} catch (Exception e) {
				log.error("Eccezione nel recupero del messaggio con id: " + idMessaggio, e);
				throw e;
			}

			if (mailboxMessage != null) {
				Boolean lockDiscarged = false;
				// messaggio in discharged error con URL MIME valorizzato da
				// riprocessare
				if (mailboxMessage.getStatus().equalsIgnoreCase(MessageStatus.MESSAGE_IN_ERROR_DISCHARGED.status())) {
					// verifico se posso ancora processare la mail o se si è
					// superato il numero di tentativi massimo
					Integer maxTryNum = null;
					if (receiver != null)
						maxTryNum = new Integer(receiver.getConfigbean().getMailconfig()
							.getProperty(MailBoxConfigKey.MAILBOX_MAX_TRY_NUM_OPERATION.keyname()));
					if (receiverPop3 != null)
						maxTryNum = new Integer(receiverPop3.getConfigbean().getMailconfig()
								.getProperty(MailBoxConfigKey.MAILBOX_MAX_TRY_NUM_OPERATION.keyname()));

					StatusMessage statusMessage = mailBusiness.getStatusMessage(mailboxMessage);
					Integer currentTryNum = 1;
					if (statusMessage != null && statusMessage.getTryNum() != null) {
						currentTryNum = statusMessage.getTryNum().getValue();
					}
					if (maxTryNum != -1 && currentTryNum > maxTryNum) {
						// superato il numero di tentativi per il
						// processamento iniziale del messaggio
						lockDiscarged = true;
					}
				}

				if (lockDiscarged) {
					// blocco il messaggio nel caso di discharged error già
					// processato un numero di volte superiore al massimo
					// impostato per la mailbox
					// lascio inalterato l'errore salvato
					mailBusiness.updateStatusMimeMessage(idMessaggio, idMailbox, MessageStatus.MESSAGE_OPERATION_LOCK,
							null);
				} else {

					// incremento il contatore del messaggio ma al momento
					// non resetto l'eventuale errore
					mailBusiness.incrementOrInitializeNumTryStatus(idMailbox, mailboxMessage);

					Boolean verifierOK = false;
					// Effettuo una verifica del messaggio
					MailVerifier verifier = (MailVerifier) MailServerSpringAppContext.getContext()
							.getBean("mailVerifier");
					MessageInfos messageInfos = null;

					try {

						messageInfos = verifier.verifyAnalize(message, this.workMailDirMessage);
						verifierOK = true; // verifica eseguita con successo
					} catch (MailVerifierException e) {
						log.error("Eccezione nella verifica del messaggio " + idMessaggio + " : " + e.getMessage(), e);
						// blocco l'operazione perchè il messaggio non è
						// verificabile da bouncy castle
						mailBusiness.updateStatusMimeMessage(idMessaggio, idMailbox,
								MessageStatus.MESSAGE_OPERATION_LOCK, e);
					} catch (Exception e) {
						log.error("Eccezione generica nella verifica del messaggio " + idMessaggio + " : "
								+ e.getMessage(), e);
						// eccezione generica che potrebbe essere
						// risolvibile, provo a riprocessare il file
						mailBusiness.updateStatusMimeMessage(idMessaggio, idMailbox,
								MessageStatus.MESSAGE_IN_ERROR_DISCHARGED, e);
					}

					if (verifierOK) {

						// Verifica eseguita con successo, procedo con
						// l'elaborazione del messaggio
						mailBusiness.updateStatusMimeMessage(idMessaggio, idMailbox,
								MessageStatus.MESSAGE_OPERATION_IN_PROGRESS, null);

						// Setto il riferimento al messaggio
						String url = mailBusiness.getMailBoxMessage(idMessaggio, idMailbox).getUrlMime();
						messageInfos.setUri(url);
						// uid e uidValidity
						messageInfos.setUid(mailboxMessage.getMessageUid());
						messageInfos.setUidValidity(mailboxMessage.getMailboxUidValidity());
						// Recupero l'operazione iniziale del processo e
						// incomincio il processamento
						ProcessOperationRecursiveResult lProcessOperationRecursiveResult = processOperation(
								messageInfos, processflow.getStart());
						boolean result = lProcessOperationRecursiveResult.getResult();

						// Controllo la fine dell'elaborazione del messaggio
						if (result) {

							mailBusiness.updateStatusFinishMimeMessageProcess(idMessaggio, idMailbox);

						} else {

							if (lProcessOperationRecursiveResult.isScheduled()) {
								// esiste un'operazione schedulata, imposto
								// lo stato di OPERATION_PLANNED in modo da
								// bloccare il flusso delle operazioni
								// e
								// riprenderlo quando l'operazione
								// pianificata è conclusa con successo
								mailBusiness.updateStatusMimeMessage(idMessaggio, idMailbox,
										MessageStatus.MESSAGE_OPERATION_PLANNED, null);
							} else if (!lProcessOperationRecursiveResult.isTrynum()) {
								// Sono sicuro che l'errore è stato
								// correttamente scritto nell'operation
								log.error("Messaggio con id " + idMessaggio + " in errore");
								mailBusiness.updateStatusMimeMessage(idMessaggio, idMailbox,
										MessageStatus.MESSAGE_IN_ERROR_OPERATION,
										new Exception(lProcessOperationRecursiveResult.getException()));
							}
						}
					}
				}
			} else {
				throw new Exception("Messagio null");
			}

		} catch (Exception e) {
			log.error("Eccezione nel processamento del messaggio con id: " + idMessaggio, e);
			throw e;
		}

	}

	/**
	 * Metodo che crea la cartella di lavoro per l'elaborazione del messaggio
	 */

	void clearWorkingDirectoryMessage(String idMessaggio) {

		// pulizia della cartella temporanea della mailbox, in tutti i casi, sia
		// che il messaggio sia stato elaborato, sia che si sia verificato un
		// errore

		try {

			FileUtils.deleteDirectory(workMailDirMessage);
			log.info("Pulizia cartella di lavoro del messaggio avente id " + idMessaggio + " eseguita con successo");

		} catch (Exception exc) {

			// Errore nella cancellazione dei file di lavoro
			// Provo a cancellare con java.nio
			// In tutti i casi procedo con la successiva elaborazione del
			// messaggio
			log.warn("Errore pulizia cartella di lavoro del messaggio avente id " + idMessaggio, exc);

			Path directoryPath = workMailDirMessage.toPath();
			try {
				Files.delete(directoryPath);
			} catch (NoSuchFileException x) {
				log.warn(String.format("%s: la cartella di lavoro del messaggio avente id %s2 non è stata trovata%n",
						directoryPath, idMessaggio));
			} catch (DirectoryNotEmptyException x) {

				log.warn(String.format(
						"%s: la cartella di lavoro del messaggio avente id %s2 non è vuota, procedo con l'eliminazione dei file contenuti%n",
						directoryPath, idMessaggio));
				// provo a eliminare i file all'interno della directory
				String[] fileDirectory = workMailDirMessage.list();
				for (String filename : fileDirectory) {
					File currentFile = new File(workMailDirMessage.getPath(), filename);
					Path pathFile = currentFile.toPath();
					try {
						Files.delete(pathFile);
					} catch (Exception e) {
						log.warn(String.format(
								"%s: Errore nella cancellazione del file %s2 dalla cartella di lavoro del messaggio avente id %s3",
								directoryPath, pathFile, idMessaggio));
					}
				}

			} catch (IOException x) {
				// File permission problems are caught here.
				log.warn(String.format("%s:IOException: problemi dei permessi con i file?", directoryPath));
			}

		}
	}

	/**
	 * Metodo che crea la cartella di lavoro per l'elaborazione del messaggio
	 */

	void createWorkingDirectoryMessage(String idMailbox, String idMessaggio) throws Exception {
		// inizializzazione delle directory temporanee specifiche per il
		// messaggio
		this.workMailDir = new File(this.mailDir, "WORKMAIL");
		if (!this.workMailDir.exists()) {
			boolean iscreate = this.workMailDir.mkdirs();
			if (!iscreate) {
				throw new Exception("Directory temporanea della mailbox " + idMailbox + " non creata!");
			}
		}
		// creo una singola directory temporanea per un messaggio, in modo da
		// pulire la cartella in maniera efficiente e non l'intera workmail
		// della mailbox
		// this.workMailDirMessage = new File(this.workMailDir,
		// java.util.UUID.randomUUID().toString());
		this.workMailDirMessage = new File(this.workMailDir, idMailbox + "-" + idMessaggio);
		if (!this.workMailDirMessage.exists()) {
			boolean iscreate = this.workMailDirMessage.mkdirs();
			if (!iscreate) {
				throw new Exception("Directory temporanea della mail avente id " + idMessaggio + " non creata!");
			}
		} else { // Diego: in fase di pulizia sui multithread può capitare che
					// ci sia cancelli una dir mentre è in esecuzione un altro
					// thread
					// creiamo una nuova directory con un numero progressivo in
					// modo da non cancellare una dir che è
			int i = 1;
			while (this.workMailDirMessage.exists()) {
				this.workMailDirMessage = new File(this.workMailDir, idMailbox + "-" + idMessaggio + "-" + i);
				if (this.workMailDirMessage.mkdirs())
					break;
				i++;
			}
			log.debug(
					"Directory temporanea della mail avente id " + idMessaggio + " creata: " + this.workMailDirMessage);
		}
	}

	/**
	 * @return Process dell'operazione @param infos @throws
	 *         IllegalAccessException @throws
	 * @throws InterruptedException
	 */
	private ProcessOperationRecursiveResult processOperation(MessageInfos infos, ProcessOperation operation)
			throws MailServerException, InterruptedException {

		// Elaboro il messaggio se non è stata interrotta l'esecuzione del
		// thread corrente

		FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();

		try {

			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}

			// Recupero il nome dell'operazione
			String name = mailBusiness.getOperationName(operation.getOperationid());
			operation.setOperationame(name);

			// Controllo lo stato di avanzamento dell'operazione
			MailboxMessageOperation mailboxMessageOperation = mailBusiness
					.getMessageOperation(operation.getOperationid(), infos.getHeaderinfo().getMessageid(), idMailbox);

			boolean execute = true;
			boolean executeControl = true;

			if (mailboxMessageOperation != null) {
				OperationStatus status = OperationStatus.getForValue(mailboxMessageOperation.getOperationStatus());
				switch (status) {

				case OPERATION_PLANNED:
					execute = false;
					break;

				case OPERATION_ERROR:
					execute = true;
					break;

				case OPERATION_IN_PROGRESS:
					execute = true;
					break;

				case OPERATION_FORCED:
					execute = false;
					break;

				case OPERATION_FINISH:
					execute = false;
					break;

				}
			}

			Object operationValueReturn = null;

			if (execute) {

				Short maxTry = null;
				if (receiver != null)
					maxTry = new Short(receiver.getConfigbean().getMailconfig()
						.getProperty(MailBoxConfigKey.MAILBOX_MAX_TRY_NUM_OPERATION.keyname()));
				if (receiverPop3 != null)
					maxTry = new Short(receiverPop3.getConfigbean().getMailconfig()
						.getProperty(MailBoxConfigKey.MAILBOX_MAX_TRY_NUM_OPERATION.keyname()));

				// Aggiorno il trynum e lo stato dell'operazione ed
				// eventualmente la blocco
				executeControl = mailBusiness.updateTryNumStateOperationAndExecuteControl(operation.getOperationid(),
						infos.getHeaderinfo().getMessageid(), idMailbox, maxTry);

				if (executeControl) {

					// Recupero l'operazione
					Class<?> abstractclass = operations.get(mailBusiness.getOperationName(operation.getOperationid()));

					// Istanzio la classe
					AbstractMessageOperation<?> messageOperation = (AbstractMessageOperation<?>) abstractclass
							.newInstance();

					// Setto il reader sulla classe
					messageOperation.setMailreceiver(this.receiver);					
					messageOperation.setMailreceiverPop3(this.receiverPop3);
					
					// setto id mailbox
					messageOperation.setIdMailbox(this.idMailbox);

					// setto id operazione
					messageOperation.setIdOperation(operation.getOperationid());

					// setto il tentativo corrente dell'operazione e il numero
					// massimo di tentativi

					Short currentNumTryOperation = mailBusiness.getCurrentTryNumOperation(operation.getOperationid(),
							infos.getHeaderinfo().getMessageid(), idMailbox);
					messageOperation.setCurrentNumTryOperation(currentNumTryOperation);
					messageOperation.setMaxTryOperation(maxTry);

					// Recupero le configurazioni
					operation.setConfigurations(mailBusiness.getMessageConfiguration(operation.getOperationid()));

					// Setto le configurazioni della specifica operazione per la
					// specifica mailbox
					Field[] fields = messageOperation.getClass().getDeclaredFields();
					for (Field field : fields) {
						if (field.isAnnotationPresent(ConfigOperation.class)) {
							String key = field.getAnnotation(ConfigOperation.class).name();
							String value = operation.getConfigurations().getProperty(key);
							Object object = null;
							if (field.getType().isAssignableFrom(Boolean.class)) {
								object = new Boolean(value);
							} else if (field.getType().isAssignableFrom(BigDecimal.class)) {
								object = new BigDecimal(value);
							} else if (field.getType().isAssignableFrom(Integer.class)) {
								object = new Integer(value);
							} else if (field.getType().isAssignableFrom(String.class)) {
								object = value;
							}
							if (object != null) {
								BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(messageOperation,
										field.getName(), object);
							}
						}
					}

					log.info("Elaboro operazione " + operation.getOperationame() + " per il messaggio con id: "
							+ infos.getHeaderinfo().getMessageid());

					// Elaboro l'operazione
					operationValueReturn = messageOperation.elaborate(infos);

					if (Thread.currentThread().isInterrupted()) {
						throw new InterruptedException();
					}

					// Setto il valore del risultato dell'operazione sul
					// messaggio
					infos.getOpresult().put(operation.getOperationame() + "_" + operation.getOperationid(),
							operationValueReturn);

					// Converto l'oggetto con jaxb
					String xmlResultOperation = XmlUtil.objectToXml(operationValueReturn);

					log.info("Risultato dell'operazione " + operation.getOperationame() + " per il messaggio avente id "
							+ infos.getHeaderinfo().getMessageid() + ":" + xmlResultOperation);

					if (operationValueReturn instanceof DeleteMessageBean
							&& ((DeleteMessageBean) operationValueReturn) != null
							&& !((DeleteMessageBean) operationValueReturn).getDeleteok()) {
						// La cancellazione non è andata a buon fine
						// se non ho trovato il messaggio forzo comunque la fine
						// dell'operazione
						OperationStatus operationStatus = ((DeleteMessageBean) operationValueReturn).getNotFound()
								? OperationStatus.OPERATION_FORCED : OperationStatus.OPERATION_ERROR;
						mailBusiness.saveOrUpdateOperation(operation.getOperationid(),
								infos.getHeaderinfo().getMessageid(), operationStatus, xmlResultOperation, idMailbox);
					} else {

						if (Thread.currentThread().isInterrupted()) {
							throw new InterruptedException();
						}

						// Salvo l'oggetto nel database
						mailBusiness.saveOrUpdateOperation(operation.getOperationid(),
								infos.getHeaderinfo().getMessageid(), OperationStatus.OPERATION_FINISH,
								xmlResultOperation, idMailbox);
					}

				} else {

					// Rilancio una eccezione di messaggio bloccato
					throw new TryNumException();

				}

			} else {

				if (Thread.currentThread().isInterrupted()) {
					throw new InterruptedException();
				}

				// Recupero l'oggetto con jaxb
				if (mailboxMessageOperation.getOperationValue() != null) {
					operationValueReturn = XmlUtil.xmlToObject(mailboxMessageOperation.getOperationValue());

					// Setto il valore del risultato dell'operazione sul
					// messaggio
					infos.getOpresult().put(operation.getOperationame() + "_" + operation.getOperationid(),
							operationValueReturn);
				}
			}

			if (executeControl) {

				// Controllo le variabili
				if (operation.getNode() != null && operation.getNode().getNodeElaborate() != null) {

					JexlEngine jexl = new JexlEngine();

					for (ProcessNodeExpression nodeExpression : operation.getNode().getNodeElaborate()) {
						// Creo l'espressione di controllo
						String jexlExp = nodeExpression.getExpression();
						Expression expression = jexl.createExpression(jexlExp);

						// controllo l'espressione
						JexlContext jc = new MapContext();
						jc.set("messageinfo", infos);
						if (!infos.getOpresult().isEmpty()) {
							Map<String, Object> mappa = new ConcurrentHashMap<String, Object>();
							// Recupero tutte le operazioni fatte e salvo le
							// ultime per tipo
							// Vengono gestiti anche i casi in cui il nome
							// dell'operazione contiene '_'
							Set<String> keys = infos.getOpresult().keySet();
							for (String key : keys) {
								String subkey = "";
								String[] subkeys = StringUtils.split(key, "_");
								if (subkeys.length > 2) {
									for (String tt : subkeys) {
										subkey += tt + "_";
									}
									if (subkey.length() > 1) {
										subkey = subkey.substring(0, subkey.length() - 1);
									}
								} else if (subkeys.length == 2) {
									subkey = subkeys[0];
								} else {
									subkey = subkeys[0];
								}
								mappa.put(subkey, infos.getOpresult().get(key));
							}
							jc.set("operation", mappa);
						}

						// Valuta l'espressione
						Boolean result = (Boolean) expression.evaluate(jc);
						if (result) {
							// Chiamata ricorsiva all'operazione successiva
							ProcessOperationRecursiveResult lProcessOperationRecursiveResult = processOperation(infos,
									nodeExpression.getOperation());
							if (!lProcessOperationRecursiveResult.getResult()) {
								return lProcessOperationRecursiveResult;
							}
						} else {
							log.error("Espressione non soddisfatta: " + expression.getExpression()
									+ " - salto operazione");
						}
					}
				}
			}
			ProcessOperationRecursiveResult lProcessOperationRecursiveResult = new ProcessOperationRecursiveResult();
			lProcessOperationRecursiveResult.setResult(true);
			return lProcessOperationRecursiveResult;
		}

		catch (InterruptedException e) {
			// thread interrotto, rilancio l'eccezione in modo che sia gestita
			// da MessageElaborate in modo adeguato
			Thread.currentThread().interrupt();
			throw e;
		}

		catch (PlannedOperation e) {
			// blocco il flusso fino a quando non viene completata l'operazione
			// schedulata
			try {
				// Aggiorno l'operazione allo stato PLANNED
				mailBusiness.saveOrUpdateOperation(operation.getOperationid(), infos.getHeaderinfo().getMessageid(),
						OperationStatus.OPERATION_PLANNED, null, idMailbox);
				ProcessOperationRecursiveResult lProcessOperationRecursiveResult = new ProcessOperationRecursiveResult();
				lProcessOperationRecursiveResult.setResult(false);
				lProcessOperationRecursiveResult.setException(null);
				lProcessOperationRecursiveResult.setScheduled(true);
				return lProcessOperationRecursiveResult;
			} catch (Throwable er) {
				// Nel caso di non riuscita del salvataggio dell'operazione
				// rilancio l'eccezione
				log.error("Errore salvataggio in database dell'operazione in errore!", er);
				ProcessOperationRecursiveResult lProcessOperationRecursiveResult = new ProcessOperationRecursiveResult();
				lProcessOperationRecursiveResult.setResult(false);
				lProcessOperationRecursiveResult.setException(er.getMessage());
				return lProcessOperationRecursiveResult;
			}
		} catch (TryNumException e) {
			// superato il numero di tentativi di elaborazione dell'operazione
			ProcessOperationRecursiveResult lProcessOperationRecursiveResult = new ProcessOperationRecursiveResult();
			lProcessOperationRecursiveResult.setResult(false);
			lProcessOperationRecursiveResult.setException(null);
			lProcessOperationRecursiveResult.setTrynum(true);
			return lProcessOperationRecursiveResult;
		} catch (Throwable e) {
			// eccezione generica, salvo l'errore e termino l'elaborazione del
			// messaggio
			try {
				String error = null;
				if (!(e instanceof TryNumException)) {
					// Converto lo stacktrace in stringa
					error = ExceptionUtils.getStackTrace(e);
				}
				// Aggiorno l'operazione con lo stato di errore
				mailBusiness.saveOrUpdateOperation(operation.getOperationid(), infos.getHeaderinfo().getMessageid(),
						OperationStatus.OPERATION_ERROR, error, idMailbox);
				ProcessOperationRecursiveResult lProcessOperationRecursiveResult = new ProcessOperationRecursiveResult();
				lProcessOperationRecursiveResult.setResult(false);
				lProcessOperationRecursiveResult.setException(error);
				return lProcessOperationRecursiveResult;
			} catch (Throwable er) {
				// Nel caso di non riuscita del salvataggio dell'operazione
				// rilancio l'eccezione
				log.error("Errore salvataggio in database dell'operazione in errore!", er);
				ProcessOperationRecursiveResult lProcessOperationRecursiveResult = new ProcessOperationRecursiveResult();
				lProcessOperationRecursiveResult.setResult(false);
				lProcessOperationRecursiveResult.setException(er.getMessage());
				return lProcessOperationRecursiveResult;
			}
		}

	}

	/**
	 * Setting delle operazioni
	 * 
	 * @param operations
	 */
	public static void setOperations(Map<String, Class<?>> operations) {
		ProcessOrchestrator.operations = operations;
	}

	public static Map<String, Class<?>> getOperations() {
		return ProcessOrchestrator.operations;
	}

	public File getWorkMailDirMessage() {
		return workMailDirMessage;
	}

	public void setWorkMailDirMessage(File workMailDirMessage) {
		this.workMailDirMessage = workMailDirMessage;
	}

	class ProcessOperationRecursiveResult {

		private boolean result;
		private String exception;
		private boolean trynum;
		private boolean scheduled;

		public boolean getResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

		public String getException() {
			return exception;
		}

		public void setException(String exception) {
			this.exception = exception;
		}

		public boolean isScheduled() {
			return scheduled;
		}

		public void setScheduled(boolean scheduled) {
			this.scheduled = scheduled;
		}

		public boolean isTrynum() {
			return trynum;
		}

		public void setTrynum(boolean trynum) {
			this.trynum = trynum;
		}

	}
}