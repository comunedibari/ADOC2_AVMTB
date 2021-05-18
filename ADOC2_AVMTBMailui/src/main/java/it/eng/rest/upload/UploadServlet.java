package it.eng.rest.upload;

import java.io.File;
import java.util.Date;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageId;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperationId;
import it.eng.aurigamailbusiness.database.mail.MailboxOperation;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.core.business.HibernateUtil;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.config.MailServerSpringAppContext;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.process.ProcessOperationFlow;
import it.eng.utility.email.reader.MessageStatus;
import it.eng.utility.email.reader.OperationStatus;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.storage.StorageCenter;
import it.eng.utility.email.util.mail.MailVerifier;
import it.eng.utility.email.util.xml.XmlUtil;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.filesystem.util.EnvironmentVariableConfigManager;

@RestController
@RequestMapping("/service/mailboxmessage")
public class UploadServlet {

	private static Logger mLogger = LogManager.getLogger(UploadServlet.class);

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("idMailbox") String idMailbox,
			@RequestParam("file") MultipartFile fileUploadAttr, final HttpSession session,
			HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		SubjectInitializer.init(null);
		Session hibernateSession = null;
		try {
			hibernateSession = HibernateUtil.begin();
			StorageCenter lStorageCenter = new StorageCenter();
			StorageService storage = lStorageCenter.getGlobalStorage(idMailbox);
			if (storage == null) {
				throw new Exception("Utilizzatore Storage 'MailConnect' non configurato per la mailbox");
			}
			String uri = storage.storeStream(fileUploadAttr.getInputStream());
			javax.mail.Session mailSession = javax.mail.Session.getInstance(Util.getJavaMailDefaultProperties());
			MimeMessage lMimeMessage = new MimeMessage(mailSession, storage.extract(uri));
			MailVerifier verifier = (MailVerifier) MailServerSpringAppContext.getContext().getBean("mailVerifier");
			// Aggiorno lo stato di processamento
			MessageInfos messageinfos = null;
			String directoryConfigValue = FactoryMailBusiness.getInstance().getMailBoxConfigParameter(idMailbox,
					MailBoxConfigKey.MAILBOX_DIRECTORY);
			File lFile = new File(EnvironmentVariableConfigManager.replaceEnvironmentVariable(directoryConfigValue));
			try {
				messageinfos = verifier.verifyAnalize(lMimeMessage, new File(lFile, "WORKMAIL"), false);
			} catch (Exception e) {
				mLogger.error("Mail caricata non valida", e);
				throw new Exception("Mail non valida: " + e.getMessage());
			}
			String idMessage = messageinfos.getHeaderinfo().getMessageid();
			if (org.apache.commons.lang3.StringUtils.isBlank(idMessage)) {
				mLogger.error("Message id della mail caricata non presente");
				throw new Exception("Message id della mail caricata non presente");
			}
			Mailbox lMailbox = (Mailbox) hibernateSession.get(Mailbox.class, idMailbox);
			ProcessOperationFlow flow = (ProcessOperationFlow) XmlUtil.xmlToObject(lMailbox.getProcessFlow());
			String lStrOperationId = flow.getStart().getOperationid();
			MailboxOperation lMailboxOperation = (MailboxOperation) hibernateSession
					.createCriteria(MailboxOperation.class).add(Restrictions.eq("operationName", "StartOperation"))
					.add(Restrictions.eq("idMailboxOperation", lStrOperationId)).list().get(0);

			Transaction lTransaction = hibernateSession.beginTransaction();
			MailboxMessage lMailboxMessage = new MailboxMessage();
			MailboxMessageId lMailboxMessageId = new MailboxMessageId();
			lMailboxMessageId.setIdMailbox(idMailbox);
			lMailboxMessageId.setMessageId(idMessage);
			lMailboxMessage.setId(lMailboxMessageId);
			if (hibernateSession.createCriteria(MailboxMessage.class).add(Restrictions.idEq(lMailboxMessageId))
					.uniqueResult() != null) {
				throw new Exception("Mail già nel sistema");
			}
			lMailboxMessage.setDateDischarged(new Date());
			lMailboxMessage.setStatus(MessageStatus.MESSAGE_FORCE_OPERATION.status());

			lMailboxMessage.setUrlMime(uri);

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
			StringBuilder lStringBuilder = new StringBuilder();
			return new ResponseEntity<String>(lStringBuilder.toString(), responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			mLogger.error("Eccezione in uploadServlet ", e);
			StringBuilder lStringBuilder = new StringBuilder();
			lStringBuilder.append(e.getMessage());
			return new ResponseEntity<String>(lStringBuilder.toString(), responseHeaders, HttpStatus.OK);
		} finally {
			HibernateUtil.release(hibernateSession);
		}
	}
}
