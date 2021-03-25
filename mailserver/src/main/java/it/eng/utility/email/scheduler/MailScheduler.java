package it.eng.utility.email.scheduler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.event.IntegrationEvent;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ErrorHandler;

import com.ctc.wstx.util.StringUtil;
import com.google.common.base.Predicate;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxAccountConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxConfig;
import it.eng.aurigamailbusiness.database.utility.ParameterUtility;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.utility.email.bean.MailUiConfigurator;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.process.MessageElaborateChannel;
import it.eng.utility.email.process.ProcessOrchestrator;
import it.eng.utility.email.process.exception.MailServerException;
import it.eng.utility.email.reader.ExtendImapMailReceiver;
import it.eng.utility.email.reader.ExtendPOP3MailReceiver;
import it.eng.utility.email.reader.config.AccountConfigBean;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.reader.config.MailConfiguratorBean;
import it.eng.utility.email.reader.pop3.Pop3Scheduler;
import it.eng.utility.email.util.xml.XmlUtil;

/**
 * Schedulatore per le mail
 * 
 * @author michele
 *
 */
public class MailScheduler {

	static Logger log = LogManager.getLogger(MailScheduler.class);

	/**
	 * Mappa che contiene gli adattatori
	 */
	private static Map<String, ImapIdleChannelAdapter> imapIdleChannelAdapters = new ConcurrentHashMap<String, ImapIdleChannelAdapter>();
	private static Map<String, Pop3Scheduler> pop3SchedulerMap = new ConcurrentHashMap<String, Pop3Scheduler>();

	/**
	 * Mappa che contiene i reader della casella di posta configurata
	 */
	private static Map<String, ExtendImapMailReceiver> imapMailReceivers = new ConcurrentHashMap<String, ExtendImapMailReceiver>();
	private static Map<String, ExtendPOP3MailReceiver> pop3MailReceivers = new ConcurrentHashMap<String, ExtendPOP3MailReceiver>();

	/**
	 * Lock delle risorse
	 */
	public static Object lock = new Object();

	private static Boolean isserver = true;

	/**
	 * Inizializzaione server del contesto per scarico delle mailbox <br>
	 * Restituisce true se non sono riscontrati errori in inizializzazione delle
	 * mailbox In caso di errori bloccanti solleva un'eccezione
	 * 
	 * @throws Exception
	 */
	public static Boolean initialize() throws Exception {
		return initialize(true);
	}

	/**
	 * Inizializzazione dello scarico delle mailbox. Si istanzia il constesto
	 * jaxb
	 * 
	 * @throws Exception
	 */
	public static Boolean initialize(Boolean isserver) throws Exception {

		Boolean result = true;

		MailScheduler.isserver = isserver;

		log.error("MailScheduler initialize");

		if (FactoryMailBusiness.getInstance().checkAllMailBoxesAreIdConfigured()) {
			// Recupero tutte le operazioni configurate
			// Recupero le entity di hibernate
			Reflections reflection = new Reflections("it.eng.utility.email",
					new TypeAnnotationsScanner().filterResultsBy(new Predicate<String>() {

						public boolean apply(String input) {
							if (StringUtils.equalsIgnoreCase(input, XmlRootElement.class.getName())
									|| StringUtils.equalsIgnoreCase(input, MessageOperation.class.getName())) {
								return true;
							} else {
								return false;
							}
						}
					}));

			// Recupero le configurazioni delle mailbox
			List<MailConfiguratorBean> configurationmailbox = new ArrayList<MailConfiguratorBean>();
			// recupero solo le mailbox associate al mailconnectid
			List<Mailbox> mailboxs = FactoryMailBusiness.getInstance().getAllActiveMailBoxForConnectId();
			if (mailboxs != null) {

				// recupero la durata massima per l'elaborazione di un singolo
				// messaggio
				BigDecimal sendTimeout = ParameterUtility.getMaxMinutiPerProcessoMail();

				for (Mailbox mailbox : mailboxs) {

					try {

						log.info("Imposto configurazioni della mailbox " + mailbox.getIdMailbox());

						Set<MailboxConfig> configs = mailbox.getMailboxConfigs();
						Properties properties = new Properties();
						for (MailboxConfig config : configs) {
							if (config.getId().getConfigKey()
									.equals(MailBoxConfigKey.MAILBOX_MAX_TRY_NUM_OPERATION.keyname())) {
								// In un hashmap non posso avere valori nulli
								if (StringUtils.isBlank(config.getConfigValue())) {
									// Memorizzo -1
									properties.put(config.getId().getConfigKey(), new String("-1"));
								} else {
									properties.put(config.getId().getConfigKey(), config.getConfigValue());
								}
							} else {
								if (StringUtils.isEmpty(config.getConfigValue())) {
									throw new MailServerException(
											"Valore non impostato per il parametro " + config.getId().getConfigKey());
								}
								properties.put(config.getId().getConfigKey(), config.getConfigValue());
							}
						}
						MailConfiguratorBean config = new MailConfiguratorBean();
						config.setMailconfig(properties);
						config.setMailboxid(mailbox.getIdMailbox());
						config.setFolder(mailbox.getFolder());
						config.setSendTimeout(sendTimeout);

						// Recupero le configurazioni dell'account
						AccountConfigBean account = new AccountConfigBean();
						account.setAccountAddress(mailbox.getMailboxAccount().getAccount());
						Properties propertiesAccount = Util.getJavaMailDefaultProperties();

						for (MailboxAccountConfig configaccount : mailbox.getMailboxAccount()
								.getMailboxAccountConfigs()) {
							if (StringUtils.isEmpty(configaccount.getConfigValue())) {
								throw new MailServerException("Valore non impostato per il parametro "
										+ configaccount.getId().getConfigKey());
							}
							propertiesAccount.put(configaccount.getId().getConfigKey(), configaccount.getConfigValue());
						}
						account.setAccountconfig(propertiesAccount);
						config.setAccount(account);
						configurationmailbox.add(config);

					} catch (Exception exc) {
						result = false;
						log.error("Errore nell'inizializzazione della mailbox " + mailbox.getIdMailbox(), exc);
					}

				}
			}

			if (isserver) {
				// Istanzio il contesto jaxb
				Set<Class<?>> classesXML = reflection.getTypesAnnotatedWith(XmlRootElement.class);
				XmlUtil.setContext(JAXBContext.newInstance(classesXML.toArray(new Class[0])));

				// Recupero tutte le operazioni
				Set<Class<?>> classesOperation = reflection.getTypesAnnotatedWith(MessageOperation.class);
				Map<String, Class<?>> operations = new ConcurrentHashMap<String, Class<?>>();
				for (Class<?> op : classesOperation) {
					MessageOperation msgoperation = op.getAnnotation(MessageOperation.class);
					operations.put(msgoperation.name(), op);
				}
				ProcessOrchestrator.setOperations(operations);
			}
			for (MailConfiguratorBean config : configurationmailbox) {

				try {
					// avvio solo le caselle abilitate, il controllo è eseguito
					// precedentemente
					Long lLongDelay = Long
							.valueOf(config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DELAY.keyname()));
					Properties lProperties = config.getMailconfig();
					lProperties.setProperty(MailBoxConfigKey.MAILBOX_DELAY.keyname(), lLongDelay.toString());
					config.setMailconfig(lProperties);
					startInternal(config);
				} catch (Exception exc) {
					result = false;
					log.error("Errore nell'inizializzazione della mailbox " + config.getMailboxid(), exc);
				}

			}
		} else {
			log.error("Non tutte le mailbox attive hanno il mailconnectid configurato");
		}

		return result;

	}

	public static Map<String, ExtendImapMailReceiver> getImapMailReceivers() {
		return imapMailReceivers;
	}
	
	public static Map<String, ExtendPOP3MailReceiver> getPOP3MailReceivers() {
		return pop3MailReceivers;
	}

	/**
	 * Lock della risorsa
	 * 
	 * @return
	 */
	public static Object lock() {
		return lock;
	}

	/**
	 * unlock dell'oggetto
	 * 
	 * @param obj
	 */
	public static void unlock(Object obj) {

	}

	/**
	 * Ricarica
	 */
	public static void reload() {

	}

	/**
	 * Start della mailobox
	 * 
	 * @param idMailbox
	 * @throws Exception
	 */
	public static void start(String idMailbox) throws Exception {
		// Sincronizzo il blocco per ovviare a problemi
		synchronized (lock) {
			
			// Recupero le nuove configurazioni
			ImapIdleChannelAdapter channel = imapIdleChannelAdapters.remove(idMailbox);
			ExtendImapMailReceiver receiver = imapMailReceivers.remove(idMailbox);

			if (channel != null && channel.isRunning()) {
				channel.stop();
			}
			if (receiver != null) {
				receiver.destroy();
			}
			// se è pop3
			Pop3Scheduler pop3Scheduler = pop3SchedulerMap.remove(idMailbox);
			ExtendPOP3MailReceiver receiverPop3 = pop3MailReceivers.remove(idMailbox);
			
			if (pop3Scheduler != null) {
				pop3Scheduler.closeScheduler();
			}
			if (receiverPop3 != null) {
				receiverPop3.destroy();
			}
			
			
			Mailbox mailbox = FactoryMailBusiness.getInstance().getMailBoxIfActive(idMailbox);
			if (mailbox != null) {

				// recupero la durata massima per l'elaborazione di un singolo
				// messaggio
				BigDecimal sendTimeout = ParameterUtility.getMaxMinutiPerProcessoMail();

				Set<MailboxConfig> configs = mailbox.getMailboxConfigs();
				Properties properties = new Properties();
				for (MailboxConfig config : configs) {
					if (config.getId().getConfigKey()
							.equals(MailBoxConfigKey.MAILBOX_MAX_TRY_NUM_OPERATION.keyname())) {
						// In un hashmap non posso avere valori nulli
						if (StringUtils.isBlank(config.getConfigValue())) {
							// Memorizzo -1
							properties.put(config.getId().getConfigKey(), "-1");
						} else {
							properties.put(config.getId().getConfigKey(), config.getConfigValue());
						}
					} else {
						if (StringUtils.isEmpty(config.getConfigValue())) {
							throw new MailServerException(
									"Valore non impostato per il parametro " + config.getId().getConfigKey());
						}
						properties.put(config.getId().getConfigKey(), config.getConfigValue());
					}
				}
				MailConfiguratorBean config = new MailConfiguratorBean();
				config.setMailconfig(properties);
				config.setMailboxid(mailbox.getIdMailbox());
				config.setFolder(mailbox.getFolder());
				config.setSendTimeout(sendTimeout);

				// Recupero le configurazioni dell'account
				AccountConfigBean account = new AccountConfigBean();
				account.setAccountAddress(mailbox.getMailboxAccount().getAccount());
				// inserisco le configurazioni comuni
				Properties propertiesAccount = Util.getJavaMailDefaultProperties();

				for (MailboxAccountConfig configaccount : mailbox.getMailboxAccount().getMailboxAccountConfigs()) {
					if (StringUtils.isEmpty(configaccount.getConfigValue())) {
						throw new MailServerException(
								"Valore non impostato per il parametro " + configaccount.getId().getConfigKey());
					}
					propertiesAccount.put(configaccount.getId().getConfigKey(), configaccount.getConfigValue());
				}
				account.setAccountconfig(propertiesAccount);
				config.setAccount(account);
				startInternal(config);
				// se non è pop faccio partire il channel imap
				String hostPop = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.POP3_HOST.keyname());
				if (StringUtils.isNotBlank(hostPop)) {
					imapIdleChannelAdapters.get(idMailbox).start();
				} else {
					Long delay = Long.valueOf(config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DELAY.keyname()));
					pop3SchedulerMap.get(idMailbox).schedula(receiverPop3, delay);
				}
			} else {
				log.info("Mailbox non attiva o non configurata per il server corrente");
			}
		}
	}

	/**
	 * Stop della mailbox
	 * 
	 * @param idmailbox
	 * @throws Exception
	 */
	public static void stop(final String idmailbox) throws Exception {
		final ImapIdleChannelAdapter channel = imapIdleChannelAdapters.get(idmailbox);
		if (channel != null && channel.isRunning()) {
			channel.stop();
		}
	}

	/**
	 * Stato della mailbox
	 * 
	 * @param idmailbox
	 * @return
	 */
	public static Boolean isActive(String idmailbox) {
		ImapIdleChannelAdapter channel = imapIdleChannelAdapters.get(idmailbox);
		if (channel == null) {
			return false;
		} else {
			return channel.isRunning();
		}
	}

	/**
	 * Stato della mailbox disabilitata o meno: si verifica se il mailconnectid
	 * configurato coincide con quello impostato per il server, se non è
	 * impostato non avvio la mailbox altrimenti rischio di attivarla su più
	 * server se attivi contemporaneamente
	 * 
	 * @param idMailbox
	 * @return
	 */
	public static Boolean isEnabled(String idMailbox) {
		String mailConnectIdMailbox = FactoryMailBusiness.getInstance().getMailBoxConfigParameter(idMailbox,
				MailBoxConfigKey.MAILBOX_MAILCONNECT_ID);
		if (StringUtils.isBlank(mailConnectIdMailbox)) {
			log.error("Mailconnectid non configurato per la mailbox");
			return false;
		}
		return mailConnectIdMailbox.equalsIgnoreCase(MailUiConfigurator.getMailConnectId());

	}

	/**
	 * Effettua il destroy del mailscheduler
	 */
	public static void destroy() {
		Set<String> keys = imapIdleChannelAdapters.keySet();
		for (String key : keys) {
			ImapIdleChannelAdapter channel = imapIdleChannelAdapters.remove(key);
			ExtendImapMailReceiver receiver = imapMailReceivers.remove(key);

			if (channel != null && channel.isRunning()) {
				channel.stop();
			}
			if (receiver != null) {
				try {
					receiver.destroy();
				} catch (Exception e) {
					log.warn("Errore destroy mailbox receiver", e);
				}
			}
		}
		// distruggo anche le connessioni Pop3
		for (String key : pop3MailReceivers.keySet()) {
			Pop3Scheduler pop3Scheduler = pop3SchedulerMap.remove(key);
			if (pop3Scheduler != null) {
				try {
					pop3Scheduler.closeScheduler();
				} catch(Exception e) {
					log.warn("Errore destroy mailbox receiver", e);
				}
			}				
			ExtendPOP3MailReceiver receiver = pop3MailReceivers.remove(key);
			if (receiver != null) {
				try {
					receiver.destroy();
				} catch (Exception e) {
					log.warn("Errore destroy mailbox receiver", e);
				}
			}
		}
	}

	/**
	 * Start interno del canale di comunicazione con la mailbox
	 * 
	 * @param config
	 * @throws Exception
	 */
	private static void startInternal(final MailConfiguratorBean config) throws Exception {

		String idMailBox = config.getMailboxid();
		log.error("Inizio avvio mailbox con id: " + idMailBox);
		
		// Effettuo un delay di 5 secondi
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(GregorianCalendar.SECOND, 5);

		
		String hostPop = config.getAccount().getAccountconfig().getProperty(AccountConfigKey.POP3_HOST.keyname());
		if (isserver) {
			// imap
			if (!StringUtils.isNotBlank(hostPop)) {
				final ExtendImapMailReceiver mailreceiver = new ExtendImapMailReceiver(config);
				imapMailReceivers.put(idMailBox, mailreceiver);

				MessageElaborateChannel outputChannel = new MessageElaborateChannel(
						ProcessOrchestrator.newInstance(idMailBox), mailreceiver);

				final ImapIdleChannelAdapter channel = new ImapIdleChannelAdapter(mailreceiver);
				mailreceiver.setChannel(channel);
				channel.setSendTimeout(config.getSendTimeout().longValueExact());
				channel.setShouldReconnectAutomatically(true); // riconnessione in
																// automatico per
																// eventuali errori
																// di connessioni al
																// server imap
				channel.setOutputChannel(outputChannel);
				channel.setApplicationEventPublisher(new ApplicationEventPublisher() {

					@Override
					public void publishEvent(ApplicationEvent arg0) {
						if (arg0 instanceof IntegrationEvent) {
							// IntegrationEvent lImapIdleExceptionEvent =
							// (IntegrationEvent)arg0;
							// lImapIdleExceptionEvent.getCause().printStackTrace();
						}
					}

					@Override
					public void publishEvent(Object arg0) {
						// TODO Auto-generated method stub

					}
				});
				channel.setErrorChannel(new MessageChannel() {

					@Override
					public boolean send(Message<?> arg0, long arg1) {
						// ErrorMessage lErrorMessage = (ErrorMessage)arg0;
						// lErrorMessage.getPayload().printStackTrace();
						return false;
					}

					@Override
					public boolean send(Message<?> arg0) {
						// ErrorMessage lErrorMessage = (ErrorMessage)arg0;
						// lErrorMessage.getPayload().printStackTrace();
						return false;
					}
				});

				ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
				taskScheduler.setPoolSize(1);
				taskScheduler.initialize();

				Long lLongDelay = Long
						.valueOf(config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DELAY.keyname()));
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
				log.debug("Avvio alle " + calendarToShow.getTime() + " la mailbox con id: " + idMailBox);

				taskScheduler.schedule(new Runnable() {

					@Override
					public void run() {
						// inserisco una routing key in modo che ogni mailbox abbia
						// il proprio log
						ThreadContext.put("ROUTINGKEY", config.getMailboxid());
						// assegno nome al thread
						Thread.currentThread().setName("Thread-receive-" + config.getMailboxid());
						if (config.getMailconfig().containsKey(MailBoxConfigKey.MAILBOX_AUTOSTART.keyname())) {
							Boolean autostart = Boolean.parseBoolean(
									config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_AUTOSTART.keyname()));
							if (autostart) {
								channel.start();
								log.error("Mailbox con id: " + config.getMailboxid() + " avviata");
							}
						}
					}
				}, calendarToShow.getTime());
				taskScheduler.setErrorHandler(new ErrorHandler() {

					@Override
					public void handleError(Throwable arg0) {
						// Qualcosa va in errore
						log.error(
								"Errore nel task della mailbox con id " + config.getMailboxid() + " :" + arg0.getMessage(),
								arg0);
					}
				});
				channel.setTaskScheduler(taskScheduler);
				imapIdleChannelAdapters.put(config.getMailboxid(), channel);
				
			} else {
				final ExtendPOP3MailReceiver mailreceiver = new ExtendPOP3MailReceiver(config);
				pop3MailReceivers.put(idMailBox, mailreceiver);
				Long delay = Long.valueOf(config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DELAY.keyname()));
				Pop3Scheduler pop3Scheduler = new Pop3Scheduler();
				pop3Scheduler.schedula(mailreceiver, delay);
				pop3SchedulerMap.put(config.getMailboxid(), pop3Scheduler);
				
			}
			
			// Inizializzo i messaggi della mailbox, pulendo anche eventuali
			// messaggi in discharged se la relativa configurazione è impostata
			Boolean clearDischarged = false;
			if (config.getMailconfig().containsKey(MailBoxConfigKey.MAILBOX_CLEAR_DISCHARGED.keyname())) {
				clearDischarged = Boolean.parseBoolean(
						config.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_CLEAR_DISCHARGED.keyname()));
			}
			FactoryMailBusiness mailBusiness = FactoryMailBusiness.getInstance();
			// inizializzo messaggi bloccati
			mailBusiness.initializeMailboxMessage(idMailBox, clearDischarged);
			// inizializzo uid
			//mailBusiness.resetUIDMailbox(idMailBox);
			

		}
	}
}
