package it.eng.utility.email.util.caricamaildafs;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageId;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperationId;
import it.eng.aurigamailbusiness.database.mail.MailboxOperation;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.utility.MailUtil;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.core.business.HibernateUtil;
import it.eng.utility.database.MailboxConfigDefaultValue;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.config.MailServerSpringAppContext;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.process.ProcessOperationFlow;
import it.eng.utility.email.reader.MessageStatus;
import it.eng.utility.email.reader.OperationStatus;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.reader.config.MailConfiguratorBean;
import it.eng.utility.email.storage.StorageCenter;
import it.eng.utility.email.util.mail.MailVerifier;
import it.eng.utility.email.util.xml.XmlUtil;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.filesystem.util.EnvironmentVariableConfigManager;
import phelps.io.Files;

public class CaricaMailDaFs {

	private File uploadDirectory = null;

	private String idMailbox = "";

	private StorageService storage;

	private Logger logger = LogManager.getLogger(CaricaMailDaFs.class);

	private boolean deleteFileAfterSuccess = false;
	
	private MailConfiguratorBean configBean = null;

	public CaricaMailDaFs(String idMailbox, File uploadDirectory, StorageService storage, boolean deleteFileAfterSuccess, MailConfiguratorBean configBean) {
		this.idMailbox = idMailbox;
		this.uploadDirectory = uploadDirectory;
		this.storage = storage;
		this.deleteFileAfterSuccess = deleteFileAfterSuccess;
		this.configBean = configBean;
	}

	synchronized public void elaborate() throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		Session hibernateSession = null;
		String uri = null;
		boolean inError = false;
		try {
			// recupero i file presenti dentro la directory specificato
			if (!uploadDirectory.exists() && !uploadDirectory.isDirectory()) {
				logger.error("uploadDirectory non configurato correttamente");
				throw new Exception("uploadDirectory non configurato correttamente");
			}
			SubjectInitializer.init(null);
			hibernateSession = HibernateUtil.begin();
			Integer fetchSize = MailboxConfigDefaultValue.DEFAULT_FETCH;
			if (configBean.getMailconfig().containsKey(MailBoxConfigKey.MAILBOX_FETCH.keyname())) {
				fetchSize = Integer.parseInt(configBean.getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_FETCH.keyname()));
			}
			int i=0;
			for (String mailPath : recursiveFileList(uploadDirectory.getAbsolutePath(), fetchSize.intValue())) {
				if (i>=fetchSize.intValue()) 
					break;
				else
					i++;
				File mail = new File(mailPath);
				// TikaConfig tika = new TikaConfig();
				// Metadata metadata = new Metadata();
				// metadata.set(Metadata.RESOURCE_NAME_KEY, mail.getName());
				// FileInputStream fis = new FileInputStream(mail);
				// MediaType mediaType = tika.getDetector().detect(TikaInputStream.get(fis), metadata);
				// if (!mediaType.toString().equalsIgnoreCase("message/rfc822")) {
				// logger.info("file["+mail.getName()+"] non è del tipo corretto[message/rfc822].");
				// continue;
				// }
				if (!FilenameUtils.getExtension(mail.getAbsolutePath()).equalsIgnoreCase("eml")) {
					logger.info("file[" + mail.getName() + "] non è del tipo corretto[message/rfc822].");
					if (deleteFileAfterSuccess) {
						logger.info(String.format("Il file[%1$s] è stato cancellato? %2$s", mail.getName(), Files.delete(mail)));
					}
					continue;
				}
				uri = createSUUri(new FileInputStream(mail));

				javax.mail.Session mailSession = javax.mail.Session.getInstance(Util.getJavaMailDefaultProperties());
				MimeMessage lMimeMessage = null;
				try {
					lMimeMessage = new MimeMessage(mailSession, storage.extract(uri));
				} catch (Exception e) {
					String error = "Il file[" + uri + "] non è una mail valida.";
					logger.error(error, e);
					if (deleteFileAfterSuccess) {
						lMimeMessage = null;
						logger.info(String.format("Il file[%1$s] è stato cancellato? %2$s", mail.getName(), Files.delete(mail)));
					}
					continue;
				}
				MailVerifier verifier = (MailVerifier) MailServerSpringAppContext.getContext().getBean("mailVerifier");
				// Aggiorno lo stato di processamento
				MessageInfos messageinfos = null;
				String directoryConfigValue = FactoryMailBusiness.getInstance().getMailBoxConfigParameter(idMailbox, MailBoxConfigKey.MAILBOX_DIRECTORY);
				File lFile = new File(EnvironmentVariableConfigManager.replaceEnvironmentVariable(directoryConfigValue));
				try {
					messageinfos = verifier.verifyAnalize(lMimeMessage, new File(lFile, "WORKMAIL"), false);
				} catch (Exception e) {
					String error = "Mail caricata non valida";
					logger.error(error, e);
					if (deleteFileAfterSuccess) {
						lMimeMessage = null;
						messageinfos = null;
						logger.info(String.format("Il file[%1$s] è stato cancellato? %2$s", mail.getName(), Files.delete(mail)));
					}
					continue;
				}
				String idMessage = messageinfos.getHeaderinfo().getMessageid();
				if (org.apache.commons.lang3.StringUtils.isBlank(idMessage)) {
					String error = "Message id della mail caricata non presente";
					logger.error(error);
					if (deleteFileAfterSuccess) {
						lMimeMessage = null;
						messageinfos = null;
						logger.info(String.format("Il file[%1$s] è stato cancellato? %2$s", mail.getName(), Files.delete(mail)));
					}
					continue;
				}
				Mailbox lMailbox = (Mailbox) hibernateSession.get(Mailbox.class, idMailbox);
				ProcessOperationFlow flow = (ProcessOperationFlow) XmlUtil.xmlToObject(lMailbox.getProcessFlow());
				String lStrOperationId = flow.getStart().getOperationid();
				MailboxOperation lMailboxOperation = (MailboxOperation) hibernateSession.createCriteria(MailboxOperation.class)
						.add(Restrictions.eq("operationName", "StartOperation")).add(Restrictions.eq("idMailboxOperation", lStrOperationId)).list().get(0);

				if (!FactoryMailBusiness.getInstance().isMimeMessageElaborate(MailUtil.getInstance(lMimeMessage).getMessageID(), idMailbox)) {
					logger.info("ok");
				}
				Transaction lTransaction = hibernateSession.beginTransaction();
				MailboxMessage lMailboxMessage = new MailboxMessage();
				MailboxMessageId lMailboxMessageId = new MailboxMessageId();
				lMailboxMessageId.setIdMailbox(idMailbox);
				lMailboxMessageId.setMessageId(idMessage);
				lMailboxMessage.setId(lMailboxMessageId);
				if (hibernateSession.createCriteria(MailboxMessage.class).add(Restrictions.idEq(lMailboxMessageId)).uniqueResult() != null) {
					String info = "Mail già nel sistema";
					logger.info(info);
					if (deleteFileAfterSuccess) {
						lMimeMessage = null;
						messageinfos = null;
						logger.info(String.format("Il file[%1$s] è stato cancellato? %2$s", mail.getName(), Files.delete(mail)));
					}
					continue;
				}
				lMailboxMessage.setDateDischarged(new Date());
				lMailboxMessage.setStatus(MessageStatus.MESSAGE_IN_ERROR_OPERATION.status());

				lMailboxMessage.setUrlMime(uri);
				lMailboxMessage.setMessageUid(messageinfos.getUid());
				// Long.parseLong("1291735136")
				lMailboxMessage.setMailboxUidValidity(messageinfos.getUidValidity());
				MailboxMessageOperationId lMailboxMessageOperationId = new MailboxMessageOperationId();
				lMailboxMessageOperationId.setIdMailbox(idMailbox);
				lMailboxMessageOperationId.setIdMailboxoperation(lMailboxOperation.getIdMailboxOperation());
				lMailboxMessageOperationId.setMessageId(lMailboxMessageId.getMessageId());
				MailboxMessageOperation lMailboxMessageOperation = new MailboxMessageOperation();
				lMailboxMessageOperation.setMailboxMessage(lMailboxMessage);
				lMailboxMessageOperation.setOperationTryNum((short) 1);
				lMailboxMessageOperation.setOperationStatus(OperationStatus.OPERATION_ERROR.status());
				lMailboxMessageOperation.setOperationValue("operation_value");
				lMailboxMessageOperation.setDateOperation(new Date());
				lMailboxMessageOperation.setId(lMailboxMessageOperationId);
				lMailboxMessageOperation.setMailboxOperation(lMailboxOperation);
				hibernateSession.save(lMailboxMessage);
				hibernateSession.save(lMailboxMessageOperation);
				hibernateSession.flush();
				lTransaction.commit();
				logger.info(idMailbox + ": mail[" + mail.getName() + "] caricata correttamente.");
				if (deleteFileAfterSuccess) {
					lMimeMessage = null;
					messageinfos = null;
					logger.info(String.format("Il file[%1$s] è stato cancellato? %2$s", mail.getName(), Files.delete(mail)));
				}
			}

		} catch (Exception e) {
			logger.error("Eccezione in " + CaricaMailDaFs.class.toString(), e);
			inError = true;
			throw e;
		} finally {
			if (uri != null && inError)
				storage.delete(uri);
			if (hibernateSession != null)
				HibernateUtil.release(hibernateSession);
		}
	}

	private String createSUUri(FileInputStream mail) throws StorageException {
		StorageCenter lStorageCenter = new StorageCenter();
		StorageService storage = lStorageCenter.getGlobalStorage(idMailbox);
		return storage.storeStream(mail);
	}

	/**
	 * recupera in modo ricorsivo tutti i file presenti dentro una directory
	 * 
	 * @param directory
	 * @return
	 */
	private List<String> recursiveFileList(String directory, int fetch) {
		List<String> result = new ArrayList<String>();
		File directoryF = new File(directory);
		if (directory == null && !directoryF.exists())
			return null;
		for (String mail : directoryF.list()) {
			File mailF = new File(directory + File.separator + mail);
			if (mailF.isDirectory()) {
				result.addAll(recursiveFileList(directory + File.separator + mail, fetch));
			} else {
				result.add(mailF.getAbsolutePath());
				if (result.size() >= fetch)
					return result;
			}
			mailF = null;
		}
		directoryF = null;
		return result;
	}

}
