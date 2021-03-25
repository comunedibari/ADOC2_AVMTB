package it.eng.utility.email.operation.impl.sendmailoperation;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.database.dao.MailSenderService;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.utility.cryptography.CryptographyUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.operation.impl.archiveoperation.utils.DaoUtils;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.storageutil.impl.filesystem.util.EnvironmentVariableConfigManager;

@MessageOperation(description = "Effettua l'invio di una mail automatica in base al template selezionato", name = "SendMailMessageOperation")
public class SendMailMessageOperation extends AbstractMessageOperation<SendMailMessageBean> {

	private Logger log = LogManager.getLogger(SendMailMessageOperation.class);

	@ConfigOperation(title = "template utilizzato per il corpo della mail", name = "sendmailmessageoperation.template", description = "Indica il template utilizzato per l'inserimento del testo nel corpo della mail, tale template dovrà essere messo nella cartella template della mailbox utilizzata")
	private String template = null;

	@ConfigOperation(title = "Subject della mail", name = "sendmailmessageoperation.subject", description = "Indica l'oggetto della mail, può essere costruito anche come ELExpression")
	private String subject = null;

	@ConfigOperation(title = "Indica la cartella dove salvare la mail inviata", name = "sendmailmessageoperation.sendfolder", description = "Indica la cartella dove salvare la mail inviata")
	private String folder = null;

	@ConfigOperation(title = "Indica se salvare la mail in uscita nella cartella configurata", name = "sendmailmessageoperation.savetofolder", description = "Indica se salvare la mail in uscita nella cartella configurata (default false)")
	private Boolean savetofolder = false;

	@ConfigOperation(title = "Indica se verificare se è già stata inviata una mail automatica allo stesso destinatario in un intervallo di tempo", name = "sendmailmessageoperation.checkalreadyreplied", description = "Indica se verificare se è già stata inviata una mail automatica allo stesso destinatario in un intervallo di tempo (default false)")
	private Boolean checkalreadyreplied = false;

	@ConfigOperation(title = "Indica il periodo (in giorni) in cui verificare se si è già inviata una mail automatica allo stesso destinatario", name = "sendmailmessageoperation.alreadyrepliedtime", description = "Indica il periodo (in giorni) in cui verificare se si è già inviata una mail automatica allo stesso destinatario (defaul 7 giorni)")
	private Integer alreadyrepliedtime = 7;

	@ConfigOperation(title = "Account da utilizzare per l'invio", name = "sendmailmessageoperation.sendfrom", description = "Account da utilizzare per l'invio")
	private String sendfrom = null;

	@Override
	public SendMailMessageBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione
			// corrente
			throw new InterruptedException();
		}

		SendMailMessageBean bean = new SendMailMessageBean();

		Boolean toSend = true;

		// Destinatario della mail è il mittente principale della mail
		String destinatario = "";
		if (StringUtils.isNotBlank(message.getMittenteWithPrincipalMail())) {
			destinatario = message.getMittenteWithPrincipalMail().trim();
		}
		
		if (StringUtils.isBlank(destinatario)) {
			bean.setSendok(false);
			return bean;
		}

		// per recuperare il mittente effettivo nel caso di errori su postecert
		if (StringUtils.startsWithIgnoreCase(destinatario, "posta-certificata@") && message.getPostecerteml() != null) {
			destinatario = message.getPostecerteml().getMittenteWithPrincipalMail().trim();
		}
		
		// se c'è per conto di è possibile avere " che danno problemi a javamail
		log.debug("SendMailMessage.destinatario: " + destinatario);
		if (destinatario.contains("Per conto di")) {
			destinatario = getEmailFromString(destinatario);
		}

		// Istanzio il context di velocity
		VelocityContext context = new VelocityContext();
		context.put("messageinfo", message);
		context.put("date", new DateTool());
		StringWriter writer = new StringWriter();
		VelocityEngine ve = new VelocityEngine();

		// Setto il subject
		StringWriter subjectwriter = new StringWriter();
		ve.evaluate(context, subjectwriter, "Error", subject);

		String oggetto = subjectwriter.toString();

		// devo verificare se è già stata spedita una mail allo stesso
		// destinatario
		if (checkalreadyreplied && FactoryMailBusiness.getInstance().exixstMailAlreadyReplied(idMailbox, idOperation, destinatario, alreadyrepliedtime)) {
			log.info("E' già stata spedita una mail di risposta automatica con oggetto " + oggetto + " e destinata a " + destinatario);
			toSend = false;
		}

		if (toSend) {
			// Istanzio il mail sender
			JavaMailSenderImpl mailsenderImpl = new JavaMailSenderImpl();
			if (getMailreceiver() != null) {
				mailsenderImpl.setJavaMailProperties(getMailreceiver().getConfigbean().getAccount().getAccountconfig());
				mailsenderImpl.setHost(getMailreceiver().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.SMTP_HOST.keyname()));
				mailsenderImpl.setPort(
						Integer.valueOf(getMailreceiver().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.SMTP_PORT.keyname())));
				mailsenderImpl
						.setUsername(getMailreceiver().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()));
				mailsenderImpl.setPassword(CryptographyUtil.decryptionAccountPasswordWithAES(getMailreceiver().getConfigbean().getAccount().getAccountconfig()));
			}
			if (getMailreceiverPop3() != null) {
				mailsenderImpl.setJavaMailProperties(getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig());
				mailsenderImpl.setHost(getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.SMTP_HOST.keyname()));
				mailsenderImpl.setPort(
						Integer.valueOf(getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.SMTP_PORT.keyname())));
				mailsenderImpl
						.setUsername(getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()));
				mailsenderImpl.setPassword(CryptographyUtil.decryptionAccountPasswordWithAES(getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig()));
			}
			// Creo il messaggio
			MimeMessage mimeMessage = mailsenderImpl.createMimeMessage();

			// Message multipart
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			String account = (getMailreceiver() != null ? getMailreceiver().getConfigbean().getAccount().getAccountAddress() : getMailreceiverPop3().getConfigbean().getAccount().getAccountAddress() );
			if (StringUtils.isNotBlank(sendfrom)) {
				account = sendfrom;
			}
			// Setto l'account
			helper.setFrom(account);

			// Data di invio
			helper.setSentDate(new Date());

			helper.setSubject(oggetto);
			helper.setTo(destinatario);

			// Setto il corpo della mail
			String directoryConfigValue = (getMailreceiver() != null ? getMailreceiver().getConfigbean().getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DIRECTORY.keyname()) : 
				getMailreceiverPop3().getConfigbean().getMailconfig().getProperty(MailBoxConfigKey.MAILBOX_DIRECTORY.keyname()));
			File directory = new File(EnvironmentVariableConfigManager.replaceEnvironmentVariable(directoryConfigValue));
			File templatefile = new File(directory + File.separator + "TEMPLATE" + File.separator + template);
			if (!templatefile.exists()) {
				throw new Exception("Il template " + templatefile.getAbsolutePath() + " non esiste!");
			}
			ve.evaluate(context, writer, "Error", FileUtils.readFileToString(templatefile, "UTF-8"));
			helper.setText(writer.toString(), true);

			// verifico se la mailbox è una mail ordinaria o certificata, in
			// quest'ultimo caso bisogna salvare la mail per mantenere le
			// relazioni
			// con le ricevute
			if ((getMailreceiver() != null && getMailreceiver().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_IS_PEC.keyname(), "false")
					.equalsIgnoreCase("true")) || 
					(getMailreceiverPop3() != null && getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_IS_PEC.keyname(), "false")
					.equalsIgnoreCase("true"))	) {
				// salvo e invio la mail
				MailSenderService mailSenderService = new MailSenderService();
				SenderBean senderBean = new SenderBean();
				senderBean.setAccount(account);
				senderBean.setAddressFrom(account);
				ArrayList<String> addressTo = new ArrayList<String>();
				addressTo.add(destinatario);
				senderBean.setAddressTo(addressTo);
				senderBean.setBody(writer.toString());
				senderBean.setIsHtml(true);
				senderBean.setIsPec(true);
				senderBean.setSubject(subjectwriter.toString());
				// Imposto ente da utilizzare per il salvataggio della mail
				SubjectBean sb = new SubjectBean();
				sb.setIdDominio(DaoUtils.getDominioToUse(idMailbox));
				SubjectUtil.subject.set(sb);
				ResultBean<EmailSentReferenceBean> result = mailSenderService.sendAndSave(senderBean);
				// Invio effettuato
				bean.setSendok(result.isInError());
			} else {
				// Invio la mail
				mailsenderImpl.send(mimeMessage);
				// Invio effettuato
				bean.setSendok(true);
			}

			// Controllo se devo salvare la mail nella casella delle inviate
			if (savetofolder) {
				if (org.apache.commons.lang3.StringUtils.isBlank(folder)) {
					log.warn("Folder di salvataggio mail non configurata");
					bean.setSaveok(false);
				} else {
					if (getMailreceiver()  != null ) 
						getMailreceiver().appendMessageToFolder(mimeMessage, folder);
					if (getMailreceiverPop3() != null) 
						getMailreceiverPop3().appendMessageToFolder(mimeMessage, folder);
					bean.setSaveok(true);
				}
			}
			bean.setReplyto(destinatario);
			bean.setSendfrom(account);
			bean.setAlreadyreply(false);
		} else {
			bean.setReplyto(destinatario);
			bean.setAlreadyreply(true);
			bean.setSaveok(false);
			bean.setSendok(false);
		}

		return bean;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public Boolean getSavetofolder() {
		return savetofolder;
	}

	public void setSavetofolder(Boolean savetofolder) {
		this.savetofolder = savetofolder;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Boolean getCheckalreadyreplied() {
		return checkalreadyreplied;
	}

	public void setCheckalreadyreplied(Boolean checkalreadyreplied) {
		this.checkalreadyreplied = checkalreadyreplied;
	}

	public Integer getAlreadyrepliedtime() {
		return alreadyrepliedtime;
	}

	public void setAlreadyrepliedtime(Integer alreadyrepliedtime) {
		this.alreadyrepliedtime = alreadyrepliedtime;
	}

	public String getSendfrom() {
		return sendfrom;
	}

	public void setSendfrom(String sendfrom) {
		this.sendfrom = sendfrom;
	}

	private String getEmailFromString (String s){
        Pattern pattern = Pattern.compile("\\s([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})\\s");
        Matcher matcher = pattern.matcher(s);
        if (!matcher.find()) 
        	return s;
        return matcher.group().trim();
    }
		
}