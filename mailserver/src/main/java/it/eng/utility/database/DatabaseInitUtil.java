package it.eng.utility.database;

import java.io.File;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
import it.eng.aurigamailbusiness.database.mail.MailboxAccountConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxAccountConfigId;
import it.eng.aurigamailbusiness.database.mail.MailboxConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxConfigId;
import it.eng.aurigamailbusiness.database.mail.MailboxOperation;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.utility.email.bean.MailUiConfigurator;
import it.eng.utility.email.database.script.ExecuteScriptWork;
import it.eng.utility.email.reader.MailBoxStatus;
import it.eng.utility.email.reader.config.MailBoxConfigKey;

public class DatabaseInitUtil {

	private DatabaseInitUtil() {
		throw new UnsupportedOperationException("Classe di utilità");
	}

	private static Configuration configuration;

	/**
	 * Ritorna una session hibernate
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Session getSession() throws Exception {
		return SubjectInitializer.getSession(null);
	}

	/**
	 * Recupero una configurazione
	 * 
	 * @return
	 */
	public static String getConfigurationProperty(String property) {
		if (configuration == null) {
			return null;
		} else {
			return configuration.getProperty(property);
		}
	}

	/**
	 * Recupera le configurazioni
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Configuration getHibernateConfiguration() throws Exception {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		return configuration;
	}

	public static Boolean isHibernateInitialize() {
		Session lSession = null;
		try {
			lSession = SubjectInitializer.getSession(null);
		} catch (Exception e) {
			return false;
		} finally {
			if (lSession != null) {
				lSession.close();
			}
		}
		return true;
	}

	/**
	 * Inizializzo la hibernate util
	 */
	public static void initialize() throws Exception {
		configuration = getHibernateConfiguration();
	}

	public static Boolean isDatabaseInitialize() throws Exception {
		// configuration = getHibernateConfiguration();
		// //Setto la validazione
		// configuration.getProperties().put("hibernate.hbm2ddl.auto",
		// "validate");
		// try{
		// sessionfactory = configuration.buildSessionFactory();
		// return true;
		// }catch(HibernateException e){
		// log.warn("Errore validazione", e);
		// return false;
		// }
		return true;
	}

	/**
	 * Method that actually creates the file.
	 * 
	 * @param dbDialect
	 *            to use
	 */

	public static void generate(String dialect, File output) {

		configuration.setProperty("hibernate.dialect", dialect);
		SchemaExport export = new SchemaExport(configuration);
		export.setDelimiter(";");
		export.setOutputFile(output.getAbsolutePath());
		export.execute(true, false, false, false);

	}

	/**
	 * Metodo che crea droppa e ricrea le tabelle
	 * 
	 * @throws Exception
	 */
	public static void dropCreateTabels() throws Exception {
		if (configuration == null) {
			return;
		}
		configuration.getProperties().remove("hibernate.hbm2ddl.auto");

		// Lancio gli script di creazione delle tabelle
		Session session = null;
		try {
			session = SubjectInitializer.getSession(null);
			session.doWork(new ExecuteScriptWork());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Metodo che crea una mailbox di template
	 * 
	 * @param mailboxname
	 * @throws Exception
	 */
	public static void createMailboxTemplate(String mailboxname, String accountmail) throws Exception {
		Session session = null;
		Transaction trans = null;
		try {
			session = SubjectInitializer.getSession(null);
			trans = session.beginTransaction();

			String idmailbox = UUID.randomUUID().toString();

			// Creo un template di account
			AccountConfigKey[] accountconfigs = AccountConfigKey.values();

			MailboxAccount account = new MailboxAccount(UUID.randomUUID().toString(), accountmail);
			session.save(account);

			// Inserisco le configurazioni
			for (AccountConfigKey accountconfig : accountconfigs) {

				MailboxAccountConfigId id = new MailboxAccountConfigId(account.getIdAccount(), accountconfig.keyname());
				String valore = "<inserire il valore>";
				switch (accountconfig) {

				case IMAP_AUTH:
					valore = "true";
					break;
				case IMAP_AUTH_LOGIN_DISABLE:
					valore = "true";
					break;
				case IMAP_SOCKETFACTORY_CLASS:
					valore = "java.net.ssl.SSLSocketFactory";
					break;
				case IMAP_SOCKETFACTORY_FALLBACK:
					valore = "false";
					break;
				case IMAP_SSL:
					valore = "true";
					break;
				case IMAP_STORE_PROTOCOL:
					valore = "imaps";
					break;
				case SMTP_AUTH:
					valore = "true";
					break;
				case SMTP_AUTH_LOGIN_DISABLE:
					valore = "true";
					break;
				case SMTP_SOCKETFACTORY_CLASS:
					valore = "java.net.ssl.SSLSocketFactory";
					break;
				case SMTP_SOCKETFACTORY_FALLBACK:
					valore = "false";
					break;
				case SMTP_SSL:
					valore = "true";
					break;

				// 27.05.2014 gestione crittografia password
				case ACCOUNT_PASSWORD_ENCRYPTION:
					valore = "false";
					break;
				}
				MailboxAccountConfig config = new MailboxAccountConfig(id, account, valore);
				session.save(config);
			}

			// Creo un template per la mailbox
			Mailbox mailbox = new Mailbox();
			mailbox.setDescription("<inserire la descrizione>");
			mailbox.setFolder("INBOX");
			mailbox.setIdMailbox(idmailbox);
			mailbox.setName(mailboxname);
			mailbox.setMailboxAccount(account);
			mailbox.setStatus(MailBoxStatus.MAILBOX_ACTIVE.status());

			String uuidegrammata = UUID.randomUUID().toString();
			String uuidsigner = UUID.randomUUID().toString();
			String uuidinter = UUID.randomUUID().toString();

			// Recupero ed inserisco un flusso standard con le operazioni
			String flow = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><processOperationFlow><start><node><nodeElaborate><expression>1==1</expression><operation><node><nodeElaborate><expression>1==1</expression><operation><node><nodeElaborate><expression>1==1</expression><operation><operationid>"
					+ uuidegrammata + "</operationid></operation></nodeElaborate></node><operationid>" + uuidinter
					+ "</operationid></operation></nodeElaborate></node><operationid>" + uuidsigner
					+ "</operationid></operation></nodeElaborate></node><operationid>1</operationid></start></processOperationFlow>";
			mailbox.setProcessFlow(flow);
			session.save(mailbox);

			// Setto le operazioni
			MailboxOperation mailboxOp1 = new MailboxOperation("1", mailbox, "StartOperation");
			MailboxOperation mailboxOp2 = new MailboxOperation(uuidegrammata, mailbox, "EGrammataMessageOperation");
			MailboxOperation mailboxOp3 = new MailboxOperation(uuidsigner, mailbox, "SignerOperation");
			MailboxOperation mailboxOp4 = new MailboxOperation(uuidinter, mailbox, "InterOperation");

			if (session.get(MailboxOperation.class, "1") == null) {
				session.save(mailboxOp1);
			}

			session.save(mailboxOp2);
			session.save(mailboxOp3);
			session.save(mailboxOp4);

			// Configurazioni standard della mailbox
			MailBoxConfigKey[] mailboxkeys = MailBoxConfigKey.values();
			for (MailBoxConfigKey mailboxkey : mailboxkeys) {

				MailboxConfigId id = new MailboxConfigId(mailboxkey.keyname(), mailbox.getIdMailbox());

				String value = "<inserire valore>";

				switch (mailboxkey) {

				case MAILBOX_AUTOSTART:
					value = "false";
					break;
				case MAILBOX_DELAY:
					value = MailboxConfigDefaultValue.DEFAULT_DELAY.toString();
					break;
				case MAILBOX_DELETE_TO_FILE_SYSTEM:
					value = "false";
					break;
				case MAILBOX_DIRECTORY:
					value = "<valore della directory su cui salvare le mail>";
					break;
				case MAILBOX_FETCH:
					value = MailboxConfigDefaultValue.DEFAULT_FETCH.toString();
					break;
				case MAILBOX_SENT_FOLDER_NAME:
					value = "Sent mail";
					break;
				case MAILBOX_SEARCH_BY_UID:
					value = "false";
					break;
				case MAILBOX_CLEAR_DISCHARGED:
					value = "false";
					break;
				case MAILBOX_DELETE_DELAY:
					value = "0";
					break;
				case MAILBOX_MAX_TRY_NUM_OPERATION:
					value = MailboxConfigDefaultValue.DEFAULT_MAX_TRY_NUM_OPERATION.toString();
					break;
				case MAILBOX_MAX_MESSAGE_ERROR_TO_PROCESS:
					value = MailboxConfigDefaultValue.DEFAULT_MAX_NUM_MESSAGE_ERROR_TO_PROCESS.toString();
					break;
				case MAILBOX_DELETE_SENT_PEC:
					value = MailboxConfigDefaultValue.DEFAULT_DELETE_SENT_PEC.toString();
					break;
				case MAILBOX_MAILCONNECT_ID:
					value = MailUiConfigurator.getMailConnectId(); // successivamente
																	// sono
																	// fatti i
																	// controlli
																	// per
																	// verificare
																	// che il
																	// mailconnectid
																	// sia
																	// impostato
																	// correttamente
					break;
				}
				MailboxConfig config = new MailboxConfig(id, mailbox, value);
				session.save(config);
			}

			// Inserimento di due configurazioni globali
			MailboxConfigId id1 = new MailboxConfigId("mail.egrammata.ente", mailbox.getIdMailbox());
			MailboxConfig config1 = new MailboxConfig(id1, mailbox, "1");
			session.save(config1);

			MailboxConfigId id2 = new MailboxConfigId("mail.egrammata.utente", mailbox.getIdMailbox());
			MailboxConfig config2 = new MailboxConfig(id2, mailbox, "1");
			session.save(config2);

			trans.commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
