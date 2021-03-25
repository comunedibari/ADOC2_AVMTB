package it.eng.dm.common.mail.structure;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;

import org.apache.commons.lang3.StringUtils;



import it.eng.core.business.KeyGenerator;
import it.eng.dm.common.mail.daticert.Postacert;
import it.eng.dm.common.mail.utility.MailUtil;
import it.eng.dm.common.mail.utility.XmlUtil;

public class MailBodyParts {

	private final List<DataHandler> attachments = new Vector<DataHandler>();

	private final Map<String, ImageInfo> attachmentsImage = new HashMap<String, ImageInfo>();

	private final HashMap<String, BodyPart> mapBodyPart = new HashMap<String, BodyPart>();

	private InputStream postacertStream = null;

	public InputStream getPostacertStream() {
		return postacertStream;
	}

	public void setPostacertStream(InputStream postacertStream) {
		this.postacertStream = postacertStream;
	}

	public final List<DataHandler> getAttachments() {
		return this.attachments;
	}

	public BodyPart getBodyPart(final String keyContentType) {
		return this.mapBodyPart.get(keyContentType);
	}

	public final Map<String, ImageInfo> getAttachmentsImage() {
		return attachmentsImage;
	}

	public int getCountBodyParts() {
		return this.mapBodyPart.size();
	}

	public BodyPart getBodyPartByPartKey(final String uuid) throws MessagingException {
		final BodyPart bodyPiece = this.mapBodyPart.get(uuid);
		// final DataHandler dataHandler = bodyPiece.getDataHandler();
		return bodyPiece;
	}

	public Set<String> getKeysContentType() {
		return this.mapBodyPart.keySet();
	}

	public void putBodyPart(final BodyPart bodyPiece) throws MessagingException {
		this.mapBodyPart.put(UUID.randomUUID().toString(), bodyPiece);
	}

	public void setBodyPartsMime(MessageInfos info) throws Exception {
		final List<BodyPart> listBodyPart = new Vector<BodyPart>();
		final Set<String> listKeyContentType = getKeysContentType();
		final Iterator<String> iterKeyContentType = listKeyContentType.iterator();

		while (iterKeyContentType.hasNext()) {
			listBodyPart.add(getBodyPartByPartKey(iterKeyContentType.next()));
		}
		this.attachments.clear();
		for (int i = 0; i < listBodyPart.size(); i++) {

			BodyPart bodyPiece = listBodyPart.get(i);

			String contentdisposition = BodyPart.INLINE;
			try {
				if (bodyPiece.getDisposition() != null) {
					contentdisposition = bodyPiece.getDisposition();
				}
				// 14/07/2015 Jacopo Ravagnan: gestisco la parseException in modo da riuscire a processare l'email
				// effettuo una parserizzazione 'manuale' per ovviare ai problemi
			} catch (ParseException pe) {
				String contentGeneric = bodyPiece.getHeader("Content-Disposition")[0];
				contentdisposition = contentGeneric.substring(0, contentGeneric.indexOf(";"));
			}

			final DataHandler dataHandlerMulti = bodyPiece.getDataHandler();

			if (StringUtils.containsIgnoreCase(dataHandlerMulti.getContentType(), "multipart")) {
				final MimeMultipart multiPart = (MimeMultipart) dataHandlerMulti.getContent();
				internalExtract(multiPart, info);

			} else {
				final String name = MailUtil.getAttachmentName(dataHandlerMulti);

				if (StringUtils.equalsIgnoreCase(BodyPart.ATTACHMENT, contentdisposition)) {

					if ("postacert.eml".equalsIgnoreCase(name)) {
						final DataSource dataSourcePostaCert = dataHandlerMulti.getDataSource();
						this.postacertStream = dataSourcePostaCert.getInputStream();
					}

					if (bodyPiece instanceof MimePart) {
						ImageInfo img = new ImageInfo();

						img.setContentID(((MimePart) bodyPiece).getContentID());
						img.setFilename(name);
						img.setMimetype(bodyPiece.getContentType());
						img.setDisposition(contentdisposition);

						attachmentsImage.put(name, img);
					}

					this.attachments.add(dataHandlerMulti);

				} else if (StringUtils.containsIgnoreCase(dataHandlerMulti.getContentType(), "text/plain")) {
					String charset = MailUtil.getCharset(dataHandlerMulti);
					String encoding = MimeUtility.getEncoding(dataHandlerMulti);
					if (StringUtils.containsIgnoreCase(encoding, "base64")) {
						info.addBodyPart(MailUtil.streamToString(dataHandlerMulti.getInputStream(), charset), "text/plain");
					} else {
						InputStream stream = MimeUtility.decode(dataHandlerMulti.getInputStream(), encoding);
						info.addBodyPart(MailUtil.streamToString(stream, charset), "text/plain");
					}

				} else if (StringUtils.containsIgnoreCase(dataHandlerMulti.getContentType(), "text/html")) {
					String charset = MailUtil.getCharset(dataHandlerMulti);

					String encoding = MimeUtility.getEncoding(dataHandlerMulti);

					if (StringUtils.containsIgnoreCase(encoding, "base64")) {
						info.addBodyPart(MailUtil.streamToString(dataHandlerMulti.getInputStream(), charset), "text/html");
					} else {
						InputStream stream = MimeUtility.decode(dataHandlerMulti.getInputStream(), encoding);
						info.addBodyPart(MailUtil.streamToString(stream, charset), "text/html");
					}

				} else if ("daticert.xml".equalsIgnoreCase(name)) {
					final DataSource dataSourceCert = dataHandlerMulti.getDataSource();
					final InputStream idataCert = dataSourceCert.getInputStream();

					info.setDaticert((Postacert) XmlUtil.xmlToObject(idataCert));
					this.attachments.add(dataHandlerMulti);
				} else if ("postacert.eml".equalsIgnoreCase(name)) {
					final DataSource dataSourcePostaCert = dataHandlerMulti.getDataSource();
					this.postacertStream = dataSourcePostaCert.getInputStream();
					this.attachments.add(dataHandlerMulti);
				} else if (name != null) {
					this.attachments.add(dataHandlerMulti);
				}

			}
		}

	}

	private void internalExtract(MimeMultipart multiPart, MessageInfos info) throws Exception {

		BodyPart bodyPieceMime;
		for (int j = 0; j < multiPart.getCount(); j++) {
			bodyPieceMime = multiPart.getBodyPart(j);

			DataHandler dataHandler = bodyPieceMime.getDataHandler();

			if (StringUtils.containsIgnoreCase(bodyPieceMime.getContentType(), "text/plain")) {
				String charset = MailUtil.getCharset(bodyPieceMime.getDataHandler());
				if (charset == null) {
					charset = MimeUtility.getDefaultJavaCharset();
					if (charset == null) {
						charset = "ISO-8859-1";
					}
				}
				String encoding = MimeUtility.getEncoding(dataHandler);

				if (StringUtils.containsIgnoreCase(encoding, "base64")) {
					info.addBodyPart(MailUtil.streamToString(dataHandler.getInputStream(), charset), "text/plain");
				} else {
					InputStream stream = MimeUtility.decode(dataHandler.getInputStream(), encoding);
					info.addBodyPart(MailUtil.streamToString(stream, charset), "text/plain");
				}
			} else if (StringUtils.containsIgnoreCase(bodyPieceMime.getContentType(), "text/html")) {
				String charset = new ContentType(bodyPieceMime.getContentType()).getParameter("charset");
				if (charset == null) {
					charset = MimeUtility.getDefaultJavaCharset();
					if (charset == null) {
						charset = "ISO-8859-1";
					}
				}

				String encoding = MimeUtility.getEncoding(dataHandler);

				if (StringUtils.containsIgnoreCase(encoding, "base64")) {
					info.addBodyPart(MailUtil.streamToString(dataHandler.getInputStream(), charset), "text/html");
				} else {
					InputStream stream = MimeUtility.decode(dataHandler.getInputStream(), encoding);
					info.addBodyPart(MailUtil.streamToString(stream, charset), "text/html");
				}

			} else if (StringUtils.containsIgnoreCase(bodyPieceMime.getContentType(), "multipart")) {
				internalExtract((MimeMultipart) dataHandler.getContent(), info);
			} else if (StringUtils.containsIgnoreCase(bodyPieceMime.getContentType(), "image")) {
				if (bodyPieceMime instanceof MimePart) {
					MimePart mp = (MimePart) bodyPieceMime;
					String fileName = null;
					if (mp.getFileName() != null) {
						fileName = MimeUtility.decodeText(mp.getFileName());
					}
					// se il filename è nullo, creo un nome ad hoc
					else {
						fileName = KeyGenerator.gen();
					}
					ImageInfo img = new ImageInfo();

					img.setContentID(mp.getContentID());
					img.setFilename(fileName);
					img.setMimetype(mp.getContentType());
					img.setDisposition(mp.getDisposition());

					attachmentsImage.put(fileName, img);

				}

				this.attachments.add(dataHandler);
			} else {
				// Salvo gli eventuali oggetti come attachements;

				this.attachments.add(dataHandler);
			}
		}
	}

}