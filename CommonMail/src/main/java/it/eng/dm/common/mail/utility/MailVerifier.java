package it.eng.dm.common.mail.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMESignedParser;

import com.sun.mail.util.DecodingException;

import it.eng.dm.common.mail.bean.MailAttachmentsBean;
import it.eng.dm.common.mail.daticert.Postacert;
import it.eng.dm.common.mail.structure.ImageInfo;
import it.eng.dm.common.mail.structure.MailBodyParts;
import it.eng.dm.common.mail.structure.MessageInfos;
import it.eng.dm.common.mail.structure.XTrasporto;

public final class MailVerifier {

	private static Logger logger = LogManager.getLogger(MailVerifier.class);

	public MailVerifier() {
		Provider bcprov = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
		if (bcprov == null) {
			bcprov = new BouncyCastleProvider();
			Security.addProvider(bcprov);
		}

	}

	@SuppressWarnings("unchecked")
	private Set<Certificate> verify(final SMIMESignedParser s) throws Exception {
		final Set<Certificate> certificates = new HashSet<Certificate>();
		final org.bouncycastle.util.Store certs = s.getCertificates();
		final SignerInformationStore signers = s.getSignerInfos();
		final Collection<SignerInformation> c = signers.getSigners();
		for (SignerInformation signer : c) {
			final Collection<X509Certificate> certCollection = (Collection<X509Certificate>) certs.getMatches(signer.getSID()); // .getCertificates(signer.getSID());
			final Object cert = certCollection.iterator().next();
			try {
				if (cert instanceof X509Certificate) {
					if (!signer.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider(BouncyCastleProvider.PROVIDER_NAME).build(
							(X509Certificate) cert))) { // cert.getPublicKey(),BouncyCastleProvider.PROVIDER_NAME)) {
						throw new Exception("signature invalid");
					}
					certificates.add((Certificate) cert);
				} else if (cert instanceof X509CertificateHolder) {
					X509Certificate certx509 = new JcaX509CertificateConverter().setProvider("BC").getCertificate(
							(X509CertificateHolder) cert);

					if (!signer.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider(BouncyCastleProvider.PROVIDER_NAME).build(
							certx509))) { // cert.getPublicKey(),BouncyCastleProvider.PROVIDER_NAME)) {
						throw new Exception("signature invalid");
					}
				}
			} catch (Exception e) {
				// Catch dell'eccezione verifica firma non verificata
			}
		}
		return certificates;
	}

	public MessageInfos verifyAnalize(MimeMessage msg, boolean isSpam) throws Exception {
		Postacert daticert = null;
		MailBodyParts bodyMessage = null;
		Set<Certificate> signatures = null;
		final MessageInfos messageinfo = new MessageInfos();
		MailUtil mailutil = MailUtil.getInstance(msg);
		// Enumeration lines = msg.getAllHeaderLines();
		// while (lines.hasMoreElements()){
		// log.debug(lines.nextElement());
		// }
		// Recupero e setto i valori dell'header della mail
		messageinfo.getHeaderinfo().setMessageid(mailutil.getMessageID());
		messageinfo.getHeaderinfo().setMittente(mailutil.getXMittente());
		messageinfo.getHeaderinfo().setRicevuta(mailutil.getXRicevuta());
		messageinfo.getHeaderinfo().setDestinataricc(mailutil.getAddressesCc());
		messageinfo.getHeaderinfo().setDestinatarito(mailutil.getAddressesTo());
		messageinfo.getHeaderinfo().setDestinataribcc(mailutil.getAddressesBcc());
		messageinfo.getHeaderinfo().setRiferimentomessageid(mailutil.getXRiferimentoMessageID());
		messageinfo.getHeaderinfo().setTiporicevuta(mailutil.getXTipoRicevuta());
		messageinfo.getHeaderinfo().setTrasporto(mailutil.getXTrasporto());
		messageinfo.getHeaderinfo().setVerficasicurezza(mailutil.getXVerificaSicurezza());
		messageinfo.getHeaderinfo().setSendDate(mailutil.getSendDate());
		messageinfo.getHeaderinfo().setRetrieveDate(mailutil.getReceiveDate());
		messageinfo.getHeaderinfo().setSubject(mailutil.getSubject());
		messageinfo.getHeaderinfo().setRichiestaLettura(mailutil.getRichiestaAvvenutaLettura() == null ? false : true);
		messageinfo.getHeaderinfo().setRichiestaRicevuta(mailutil.getRichiestaRicevuta() == null ? false : true);
		messageinfo.getHeaderinfo().setPriority(mailutil.getPriority());
		messageinfo.setIsDeliveryStatus(mailutil.IsDelivery());
		messageinfo.setIspam(isSpam);
		if (msg.isMimeType("multipart/signed")) {
			try {
				new SMIMESignedParser((MimeMultipart) msg.getContent());
			} catch (Throwable t) {
				t.printStackTrace();
			}
			final SMIMESignedParser s = new SMIMESignedParser((MimeMultipart) msg.getContent());
			if (messageinfo.getHeaderinfo().getTrasporto() == XTrasporto.POSTA_CERTIFICATA
					|| messageinfo.getHeaderinfo().getTrasporto() == null) {
				// Messaggio Pec di errore non estraggo il daticert.xml perchè non c'è
				daticert = MailVerifier.extractXMLCert(s);
				messageinfo.setDaticert(daticert);
			}
			// Effettuo un extract dei dati
			bodyMessage = MailVerifier.extractBodyMessage(s.getContent());
			// Verifico la firma
			signatures = verify(s);
			messageinfo.setSignatures(signatures);
			// Setto i dati sul bodypartsmime
			bodyMessage.setBodyPartsMime(messageinfo);
			// Estraggo gli attachments
			List<DataHandler> attachments = bodyMessage.getAttachments();

			for (DataHandler attachment : attachments) {
				MailAttachmentsBean attachmentbean = new MailAttachmentsBean();
				if (attachment.getContentType() != null) {
					attachmentbean.setMimetype(MimeUtility.decodeText(attachment.getContentType()));
				}
				attachmentbean.setFilename(MailUtil.getAttachmentName(attachment));
				if (!isSpam) {
					attachmentbean = processAttachment(attachment, attachmentbean);
				} else {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					IOUtils.copy(attachment.getInputStream(), output);
					output.close();
					attachmentbean.setData(output.toByteArray());
				}
				attachmentbean.setMessageid(messageinfo.getHeaderinfo().getMessageid());
				// Aggiungo il mimemessage
				messageinfo.getAttachments().add(attachmentbean);
			}
		} else {
			final String message = "MimeType unknown [" + msg.getContentType() + "]";
			logger.info(message);
			bodyMessage = new MailBodyParts();
			final DataHandler data = msg.getDataHandler();
			String charset = MailUtil.getCharset(data);
			if (StringUtils.contains(data.getContentType(), "multipart/related")) {
				BodyPart bodypartstream = new MimeBodyPart();
				DataHandler handler = new DataHandler(data.getDataSource());
				bodypartstream.setDataHandler(handler);
				bodyMessage.putBodyPart(bodypartstream);
			} else if (StringUtils.contains(data.getContentType(), "text/plain")) {
				BodyPart bodyparttextplain = new MimeBodyPart();
				String encoding = MimeUtility.getEncoding(data);
				try {
					InputStream stream = MimeUtility.decode(data.getInputStream(), encoding);
					bodyparttextplain.setText(MailUtil.streamToString(stream, charset));
				} catch (Throwable e) {
					bodyparttextplain.setText(MailUtil.streamToString(data.getInputStream(), charset));
				}
				bodyMessage.putBodyPart(bodyparttextplain);
			} else if (StringUtils.contains(data.getContentType(), "text/html")) {
				BodyPart bodyparttextplain = new MimeBodyPart();
				String encoding = MimeUtility.getEncoding(data);
				try {
					InputStream stream = MimeUtility.decode(data.getInputStream(), encoding);
					bodyparttextplain.setText(MailUtil.streamToString(stream, charset));
				} catch (Throwable e) {
					bodyparttextplain.setText(MailUtil.streamToString(data.getInputStream(), charset));
				}
				bodyMessage.putBodyPart(bodyparttextplain);
			} else if (data.getContent() instanceof InputStream) {
				BodyPart bodypartstream = new MimeBodyPart();
				DataHandler handler = new DataHandler(data.getDataSource());
				bodypartstream.setDataHandler(handler);
				bodyMessage.putBodyPart(bodypartstream);
			} else {
				Object obj = data.getContent();
				if (obj instanceof MimeMultipart) {
					final MimeMultipart multiPart = (MimeMultipart) data.getContent();
					for (int i = 0; i < multiPart.getCount(); i++) {
						final BodyPart bodyPiece = multiPart.getBodyPart(i);
						bodyMessage.putBodyPart(bodyPiece);
					}
				} else {
					if (obj != null) {
						BodyPart bodyparttextplain = new MimeBodyPart();
						bodyparttextplain.setText(obj.toString());
						bodyMessage.putBodyPart(bodyparttextplain);
					}
				}
			}
			bodyMessage.setBodyPartsMime(messageinfo);
			// Estraggo gli attachments
			List<DataHandler> attachments = bodyMessage.getAttachments();

			for (DataHandler attachment : attachments) {
				MailAttachmentsBean attachmentbean = new MailAttachmentsBean();
				attachmentbean.setMimetype(attachment.getContentType());
				attachmentbean.setFilename(MailUtil.getAttachmentName(attachment));

				if (!isSpam) {
					// Salvo il file sulla temporanea per il recupero
					try {
						attachmentbean = processAttachment(attachment, attachmentbean);
					} catch (Exception e) {
						if (e instanceof DecodingException) {
							logger.error("Errore nel decoding dell'allegato");
						} else {
							// Errore nel recupero delle informazioni
							throw e;
						}
					}
				} else {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					IOUtils.copy(attachment.getInputStream(), output);
					output.close();
					attachmentbean.setData(output.toByteArray());
				}
				attachmentbean.setMessageid(messageinfo.getHeaderinfo().getMessageid());

				if (StringUtils.contains(attachment.getContentType(), "image")) {
					Map<String, ImageInfo> mImages = bodyMessage.getAttachmentsImage();
					ImageInfo image = mImages.get(attachmentbean.getFilename());
					if (image != null) {
						attachmentbean.setContentID(image.getContentID());
						attachmentbean.setDisposition(image.getDisposition());
					}

				}

				// Aggiungo il mimemessage
				messageinfo.getAttachments().add(attachmentbean);
			}
		}
		// Controllo gli attachment della mail e ricontrollo i nomi
		int attachmentnum = 1;
		for (MailAttachmentsBean attachment : messageinfo.getAttachments()) {
			if (StringUtils.contains(attachment.getFilename(), "{0}")) {
				attachment.setFilename(MessageFormat.format(attachment.getFilename(), (attachmentnum++) + ""));
			}
		}
		// Verifico gli attachment
		if (bodyMessage != null) {
			for (DataHandler handler : bodyMessage.getAttachments()) {
				if (StringUtils.containsIgnoreCase(handler.getContentType(), "message/rfc822")) {
					try {
						messageinfo.getSubinfos().add(verifyAnalize(new MimeMessage(null, handler.getInputStream()), isSpam));
					} catch (Exception e) {
						if (e instanceof DecodingException) {
							logger.error("Errore nel decoding dell'allegato");
						} else {
							// Errore nel recupero delle informazioni
							throw e;
						}
					}
				}
			}
			if (bodyMessage.getPostacertStream() != null) {
				// Setto la postecert
				try {
					messageinfo.setPostecerteml(verifyAnalize(new MimeMessage(null, bodyMessage.getPostacertStream()), isSpam));
				} catch (Exception e) {
					if (e instanceof DecodingException) {
						logger.error("Errore nel decoding dell'allegato");
					} else {
						// Errore nel recupero delle informazioni
						throw e;
					}
				}
			}
		}
		return messageinfo;
	}

	/**
	 * copia l'attachment su un file temporaneo e ne assegna la dimensione
	 * 
	 * @param tempdir
	 * @param attachment
	 * @param attachmentbean
	 * @return
	 * @throws IOException
	 */
	private MailAttachmentsBean processAttachment(DataHandler attachment, MailAttachmentsBean attachmentbean) throws IOException {
		File temp = null;
		FileOutputStream output = null;
		try {
			temp = File.createTempFile("Attachments", ".tmp");
			output = new FileOutputStream(temp);
			IOUtils.copy(attachment.getInputStream(), output);
			output.close();
			attachmentbean.setSize(temp.length());
			attachmentbean.setFile(temp);
		} catch (IOException e) {
			// se non funziona l'IOUtils provo a fare una copia nella maniera classica
			InputStream input = null;
			try {
				input = attachment.getInputStream();
				output = new FileOutputStream(temp);
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buf)) > 0) {
					output.write(buf, 0, bytesRead);
				}
			} finally {
				input.close();
				output.close();
				attachmentbean.setSize(temp.length());
				attachmentbean.setFile(temp);
			}
		} finally {
			if (temp != null) {
				// FileUtil.deleteFile(temp);
			}
		}
		return attachmentbean;
	}

	private static MailBodyParts extractBodyMessage(final MimeBodyPart mimePart) throws Exception {
		final MailBodyParts bodyPartPieces = new MailBodyParts();
		final DataHandler data = mimePart.getDataHandler();
		final MimeMultipart multiPart = (MimeMultipart) data.getContent();
		for (int i = 0; i < multiPart.getCount(); i++) {
			final BodyPart bodyPiece = multiPart.getBodyPart(i);
			bodyPartPieces.putBodyPart(bodyPiece);
		}
		return bodyPartPieces;
	}

	private static Postacert extractXMLCert(final SMIMESignedParser s) throws Exception {
		final MimeBodyPart mimePart = s.getContent();
		final DataHandler data = mimePart.getDataHandler();
		final MimeMultipart multiPart = (MimeMultipart) data.getContent();
		if (multiPart.getCount() < 1) {
			throw new MessagingException("Missing attachments");
		}
		int count = multiPart.getCount();
		BodyPart bodyCert = null;
		for (int i = 0; i < count; i++) {
			bodyCert = multiPart.getBodyPart(i);
			if (bodyCert.getFileName() != null) {
				if (bodyCert.getFileName().equalsIgnoreCase("daticert.xml")) {
					final DataHandler dataCert = bodyCert.getDataHandler();
					final DataSource dataSourceCert = dataCert.getDataSource();
					final InputStream idataCert = dataSourceCert.getInputStream();
					return (Postacert) XmlUtil.xmlToObject(idataCert);
				}
			}
		}
		return null;
	}
}
