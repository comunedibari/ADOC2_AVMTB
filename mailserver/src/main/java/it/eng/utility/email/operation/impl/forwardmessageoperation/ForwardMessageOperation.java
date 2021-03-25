package it.eng.utility.email.operation.impl.forwardmessageoperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.utility.cryptography.CryptographyUtil;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.storage.StorageCenter;

@MessageOperation(description = "Effettua un inoltro del messaggio agli indirizzi configurati in ingresso", name = "ForwardMessageOperation")
public class ForwardMessageOperation extends AbstractMessageOperation<ForwardMessageBean> {

	@ConfigOperation(title = "Indirizzi mail fissi", name = "forwardmessageoperation.address.fixed", description = "Indica gli indirizzi di destinazione a cui inviare le mail, separati da ','")
	private String addressFixed = null;

	@ConfigOperation(title = "Indirizzi mail dinamici", name = "forwardmessageoperation.address.dynamic", description = "Indica gli indirizzi di destinazione a cui inviare le mail, recuperati trami espressione.")
	private String addressExpression = null;

	@Override
	public ForwardMessageBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		ForwardMessageBean bean = new ForwardMessageBean();
		String addresstolist = null;

		if (addressFixed != null) {
			addresstolist = addressFixed;
		}

		// Istanzio il mail sender
		JavaMailSenderImpl mailsender = new JavaMailSenderImpl();
		if (getMailreceiver() != null) {
			mailsender.setJavaMailProperties(getMailreceiver().getConfigbean().getAccount().getAccountconfig());
			mailsender.setHost(getMailreceiver().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.SMTP_HOST.keyname()));
			mailsender
					.setPort(Integer.valueOf(getMailreceiver().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.SMTP_PORT.keyname())));
			mailsender.setUsername(getMailreceiver().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()));
	
			// 27.05.2014 gestione crittografia password
			mailsender.setPassword(CryptographyUtil.decryptionAccountPasswordWithAES(getMailreceiver().getConfigbean().getAccount().getAccountconfig()));
		}
		if (getMailreceiverPop3() != null) {
			mailsender.setJavaMailProperties(getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig());
			mailsender.setHost(getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.SMTP_HOST.keyname()));
			mailsender
					.setPort(Integer.valueOf(getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.SMTP_PORT.keyname())));
			mailsender.setUsername(getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()));
	
			// 27.05.2014 gestione crittografia password
			mailsender.setPassword(CryptographyUtil.decryptionAccountPasswordWithAES(getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig()));
		}

		// Creo il messaggio
		MimeMessage forward = mailsender.createMimeMessage();

		// Subject
		forward.setSubject("I: " + message.getHeaderinfo().getSubject());

		if (addressExpression != null) {
			// controllo l'espressione
			JexlEngine jexl = new JexlEngine();
			JexlContext jc = new MapContext();
			if (!message.getOpresult().isEmpty()) {
				HashMap<String, Object> mappa = new HashMap<String, Object>();
				// Recupero tutte le operazioni fatte e salvo le ultime per tipo
				// Vengono gestiti anche i casi in cui il nome dell'operazione contiene '_'
				Set<String> keys = message.getOpresult().keySet();
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
					mappa.put(subkey, message.getOpresult().get(key));
				}
				jc.set("operation", mappa);
			}
			Expression e = jexl.createExpression(addressExpression);
			String dynamicaddress = (String) e.evaluate(jc);
			if (StringUtils.isNotBlank(dynamicaddress)) {
				if (addresstolist != null) {
					addresstolist += ',' + dynamicaddress;
				} else {
					addresstolist = dynamicaddress;
				}
			}
		}

		if (addresstolist != null) {

			if (Thread.currentThread().isInterrupted()) {
				// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
				throw new InterruptedException();
			}

			// Account from
			if (getMailreceiver() != null)
				forward.setFrom(new InternetAddress(getMailreceiver().getConfigbean().getAccount().getAccountAddress()));
			if (getMailreceiverPop3() != null)
				forward.setFrom(new InternetAddress(getMailreceiverPop3().getConfigbean().getAccount().getAccountAddress()));

			// Creao il nuovo messaggio da inoltrare
			Multipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			StorageCenter lStorageCenter = new StorageCenter();
			MimeMessage original;
			try {
				original = new MimeMessage(null, lStorageCenter.extract((getMailreceiver() != null ? getMailreceiver().getConfigbean().getMailboxid() : getMailreceiverPop3().getConfigbean().getMailboxid()), message.getUri()));
			} catch (Throwable e) {
				throw new Exception(e);
			}
			DataHandler dh = new DataHandler(original, "message/rfc822");
			messageBodyPart.setDataHandler(dh);
			multipart.addBodyPart(messageBodyPart);
			forward.setContent(multipart);

			String[] addresstoArray = addresstolist.split(",");

			Integer maxAddress = null;
			if (getMailreceiver() != null)
				maxAddress = new Integer(
					getMailreceiver().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.SMTP_MAX_ADDRESS.keyname(), "50"));
			if (getMailreceiverPop3() != null)
				maxAddress = new Integer(
					getMailreceiverPop3().getConfigbean().getAccount().getAccountconfig().getProperty(AccountConfigKey.SMTP_MAX_ADDRESS.keyname(), "50"));

			int group = addresstoArray.length / maxAddress;
			if (addresstoArray.length % maxAddress != 0) {
				group++;
			}

			for (int i = 0; i < group; i++) {

				if (Thread.currentThread().isInterrupted()) {
					// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
					throw new InterruptedException();
				}

				String[] addresses = ArrayUtils.subarray(addresstoArray, maxAddress * i, (maxAddress * (i + 1)));

				List<InternetAddress> internetaddress = new ArrayList<InternetAddress>();
				for (String addressto : addresses) {
					internetaddress.add(new InternetAddress(addressto));
				}

				forward.setRecipients(RecipientType.TO, internetaddress.toArray(new InternetAddress[0]));

				// Invio la mail
				mailsender.send(forward);
			}
		}
		bean.setAddresslist(addresstolist);
		bean.setForwardok(true);
		return bean;
	}

	public String getAddressFixed() {
		return addressFixed;
	}

	public void setAddressFixed(String addressFixed) {
		this.addressFixed = addressFixed;
	}

	public String getAddressExpression() {
		return addressExpression;
	}

	public void setAddressExpression(String addressExpression) {
		this.addressExpression = addressExpression;
	}
}