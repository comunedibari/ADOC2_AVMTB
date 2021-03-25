package it.eng.utility.email.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.sun.jersey.multipart.FormDataParam;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
import it.eng.aurigamailbusiness.database.mail.MailboxAccountConfig;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.aurigamailbusiness.utility.MailUtil;
import it.eng.aurigamailbusiness.utility.cryptography.CryptographyUtil;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.process.exception.MailServerException;
import it.eng.utility.email.reader.ExtendImapMailReceiver;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.email.scheduler.MailScheduler;
import it.eng.utility.email.service.delete.DeleteResultBean;
import it.eng.utility.email.service.force.ForceResultBean;
import it.eng.utility.email.service.sender.EmailAddressBean;
import it.eng.utility.email.service.sender.EmailMessageResultBean;
import it.eng.utility.email.service.sender.EmailSenderAttachmentsBean;
import it.eng.utility.email.service.sender.EmailSenderBean;
import it.eng.utility.email.service.sender.EmailSenderResultBean;

/**
 * Classe che gestisce l'invio della mail
 * 
 * @author michele
 *
 */
@Path(value = "/RestMailService")
public class MailBoxService {

	@POST
	@Path("/send")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_XML)
	public EmailSenderResultBean sendMail(@FormDataParam("emailsender") EmailSenderBean bean, @FormDataParam("account") String account) throws Exception {

		EmailSenderResultBean result = new EmailSenderResultBean();
		result.setIsSave(false);
		result.setIsSent(false);
		result.setMessages(new ArrayList<EmailMessageResultBean>());

		try {
			// Recupero l'input stream e lo converto nel mimemessage
			MailboxAccount mailboxaccount = FactoryMailBusiness.getInstance().getMailboxAccount(account);

			// Setto le properties
			Properties propertiesAccount = new Properties();
			for (MailboxAccountConfig configaccount : mailboxaccount.getMailboxAccountConfigs()) {
				if (StringUtils.isEmpty(configaccount.getConfigValue())) {
					throw new MailServerException("Valore non impostato per il parametro " + configaccount.getId().getConfigKey());
				}
				propertiesAccount.put(configaccount.getId().getConfigKey(), configaccount.getConfigValue());
			}

			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_IS_PEC.keyname(), "false").equalsIgnoreCase("true")) {
				result.setIsPecAccount(true);
			} else {
				result.setIsPecAccount(false);
			}

			// Istanzio il mail sender
			JavaMailSenderImpl mailsender = new JavaMailSenderImpl();
			mailsender.setJavaMailProperties(propertiesAccount);
			mailsender.setHost(propertiesAccount.getProperty(AccountConfigKey.SMTP_HOST.keyname()));
			mailsender.setPort(Integer.valueOf(propertiesAccount.getProperty(AccountConfigKey.SMTP_PORT.keyname())));
			mailsender.setUsername(propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()));

			// 27.05.2014 gestione crittografia password
			mailsender.setPassword(CryptographyUtil.decryptionAccountPasswordWithAES(propertiesAccount));

			// Effettuo un controllo degli indirizzi
			EmailAddressBean emailaddress = controllList(bean);

			// Numero massimo di indirizzi
			Integer maxAddress = new Integer(propertiesAccount.getProperty(AccountConfigKey.SMTP_MAX_ADDRESS.keyname(), "50"));

			// Numero di gruppi
			Integer group = emailaddress.getgroup(maxAddress);

			if (group == 1) {

				MimeMessage message = createMimeMessage(mailsender, bean);
				message.setHeader("X-TipoRicevuta", "sintetica");

				EmailMessageResultBean messageresult = new EmailMessageResultBean();
				messageresult.setAddresslistbcc(new ArrayList<String>());
				messageresult.setAddresslistcc(new ArrayList<String>());
				messageresult.setAddresslistto(new ArrayList<String>());

				// To
				for (String address : bean.getAddressTo()) {
					// Destinatari
					message.addRecipient(RecipientType.TO, new InternetAddress(address));
					messageresult.getAddresslistto().add(address);
				}

				// Cc
				if (bean.getAddressCc() != null) {
					for (String address : bean.getAddressCc()) {
						// Destinatari CC
						message.addRecipient(RecipientType.CC, new InternetAddress(address));
						messageresult.getAddresslistcc().add(address);
					}
				}

				// Bcc
				if (bean.getAddressBcc() != null) {
					for (String address : bean.getAddressBcc()) {
						// Destinatari BCC
						message.addRecipient(RecipientType.BCC, new InternetAddress(address));
						messageresult.getAddresslistbcc().add(address);
					}
				}

				// Invio la mail
				mailsender.send(message);

				messageresult.setMessageid(MailUtil.getInstance(message).getMessageID());

				ByteArrayOutputStream outputdata = new ByteArrayOutputStream();
				message.writeTo(outputdata);
				outputdata.close();
				messageresult.setMimemessage(outputdata.toByteArray());

				result.getMessages().add(messageresult);

				// Controllo se salvare la mail nella cartella delle inviate
				if (bean.getSaveToSent() != null && bean.getSaveToSent()) {
					// Salvo nella cartella delle mail inviate
					// Effettuo un lock
					Object lock = MailScheduler.lock();

					// Prendo la prima mailbox possibile
					ExtendImapMailReceiver mail = MailScheduler.getImapMailReceivers().get(mailboxaccount.getMailboxes().iterator().next().getIdMailbox());
					mail.appendMessageToFolder(message, mail.getConfigbean().getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_SENT_FOLDER_NAME.keyname()));

					result.setIsSave(true);
					MailScheduler.unlock(lock);
				}

			} else {

				for (int i = 0; i < group; i++) {

					MimeMessage message = createMimeMessage(mailsender, bean);

					// Da Diego
					message.setHeader("X-TipoRicevuta", "sintetica");

					// Ciclo i gruppi
					List<String> addresses = emailaddress.getAddressForGroup(i, maxAddress);

					EmailMessageResultBean messageresult = new EmailMessageResultBean();
					messageresult.setAddresslistbcc(new ArrayList<String>());
					messageresult.setAddresslistcc(new ArrayList<String>());
					messageresult.setAddresslistto(new ArrayList<String>());

					List<InternetAddress> internetaddress = new ArrayList<InternetAddress>();

					for (String addressto : addresses) {
						internetaddress.add(new InternetAddress(addressto));
						messageresult.getAddresslistto().add(addressto);
					}

					message.setRecipients(RecipientType.TO, internetaddress.toArray(new InternetAddress[0]));

					// Invio la mail
					mailsender.send(message);

					messageresult.setMessageid(MailUtil.getInstance(message).getMessageID());

					ByteArrayOutputStream outputdata = new ByteArrayOutputStream();
					message.writeTo(outputdata);
					outputdata.close();
					messageresult.setMimemessage(outputdata.toByteArray());

					result.getMessages().add(messageresult);

					// Controllo se salvare la mail nella cartella delle inviate
					if (bean.getSaveToSent() != null && bean.getSaveToSent()) {
						// Salvo nella cartella delle mail inviate
						// Effettuo un lock
						Object lock = MailScheduler.lock();

						// Prendo la prima mailbox possibile
						ExtendImapMailReceiver mail = MailScheduler.getImapMailReceivers().get(mailboxaccount.getMailboxes().iterator().next().getIdMailbox());
						mail.appendMessageToFolder(message,
								mail.getConfigbean().getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_SENT_FOLDER_NAME.keyname()));

						result.setIsSave(true);
						MailScheduler.unlock(lock);
					}
				}
			}
			result.setIsSent(true);
		} catch (Throwable e) {
			// Setto il solo messaggio di errore
			result.setErrormessage(e.getMessage());
			result.setError(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	private MimeMessage createMimeMessage(JavaMailSenderImpl mailsender, EmailSenderBean bean) throws Exception {

		// Creo il messaggio
		MimeMessage message = mailsender.createMimeMessage();

		// Message Multipart
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		// Setto l'indirizzo from
		helper.setFrom(bean.getAddressFrom());

		// Setto il subject
		helper.setSubject(bean.getSubject());

		// Data di invio
		helper.setSentDate(new Date());

		// Setto il corpo della mail
		boolean ishtml = false;
		if (bean.getIsHtml() != null) {
			ishtml = bean.getIsHtml();
		}
		helper.setText(bean.getBody(), ishtml);

		// Setto gli attachments
		if (bean.getAttachments() != null) {
			for (EmailSenderAttachmentsBean attachment : bean.getAttachments()) {
				try {
					helper.addAttachment(attachment.getFilename(), new ByteArrayResource(attachment.getContent()));
				} catch (Exception e) {
					throw new Exception("Impossibile allegare il file " + attachment.getFilename(), e);
				}
			}
		}
		return message;

	}

	/**
	 * Metodo che controlla gli indirizzi mail
	 * 
	 * @param bean
	 * @return
	 */
	private EmailAddressBean controllList(EmailSenderBean bean) throws Exception {
		EmailAddressBean addressbean = new EmailAddressBean();
		if (bean.getAddressTo() != null && !bean.getAddressTo().isEmpty()) {
			for (String address : bean.getAddressTo()) {
				new InternetAddress(address);
				addressbean.getAddressto().add(address);
			}
		} else {
			throw new Exception("Nessun indirizzi destinatario settato!!");
		}
		// Cc
		if (bean.getAddressCc() != null) {
			for (String address : bean.getAddressCc()) {
				new InternetAddress(address);
				addressbean.getAddresscc().add(address);
			}
		}
		// Bcc
		if (bean.getAddressBcc() != null) {
			for (String address : bean.getAddressBcc()) {
				new InternetAddress(address);
				addressbean.getAddressbcc().add(address);
			}
		}
		return addressbean;
	}

	@POST
	@Path("/deleteelaborate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_XML)
	public DeleteResultBean deleteMessage(@FormDataParam("messageid") String messageid, @FormDataParam("account") String account,
			@FormDataParam("folder") String folder) throws Exception {
		DeleteResultBean result = new DeleteResultBean();
		try {
			MailboxAccount mailboxaccount = FactoryMailBusiness.getInstance().getMailboxAccount(account);

			String idmailbox = null;
			// Recupero il folder
			for (Mailbox mailboxtmp : mailboxaccount.getMailboxes()) {
				if (mailboxtmp.getFolder().equalsIgnoreCase(folder)) {
					idmailbox = mailboxtmp.getIdMailbox();
					break;
				}
			}
			if (idmailbox == null) {
				throw new Exception("Nessuna mailbox configurata con account '" + account + "' e folder '" + folder + "'");
			}

			// Effettuo un lock
			Object lock = MailScheduler.lock();
			// Prendo la prima mailbox possibile
			ExtendImapMailReceiver mail = MailScheduler.getImapMailReceivers().get(mailboxaccount.getMailboxes().iterator().next().getIdMailbox());
			// Provo a cancellare il messaggio dalla inbox
			mail.deleteMsg(messageid, null, null, folder);
			// Effettun unlock della risorsa
			MailScheduler.unlock(lock);
			// rimozione del messaggio dalla mailbox
			FactoryMailBusiness.getInstance().deleteMessage(idmailbox, messageid);
			result.setResult(true);
		} catch (Throwable e) {
			result.setError(ExceptionUtils.getStackTrace(e));
			result.setResult(false);
		}
		return result;
	}

	@POST
	@Path("/forceelaborate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_XML)
	public ForceResultBean forcetoelaborate(@FormDataParam("messageid") String messageid, @FormDataParam("account") String account,
			@FormDataParam("folder") String folder) throws Exception {
		ForceResultBean result = new ForceResultBean();
		try {
			MailboxAccount mailboxaccount = FactoryMailBusiness.getInstance().getMailboxAccount(account);

			String idmailbox = null;
			// Recupero il folder
			for (Mailbox mailboxtmp : mailboxaccount.getMailboxes()) {
				if (mailboxtmp.getFolder().equalsIgnoreCase(folder)) {
					idmailbox = mailboxtmp.getIdMailbox();
					break;
				}
			}
			if (idmailbox == null) {
				throw new Exception("Nessuna mailbox configurata con account '" + account + "' e folder '" + folder + "'");
			}

			FactoryMailBusiness.getInstance().forceToMessageElaborate(idmailbox, messageid);
			result.setResult(true);
		} catch (Throwable e) {
			result.setError(ExceptionUtils.getStackTrace(e));
			result.setResult(false);
		}
		return result;
	}

}