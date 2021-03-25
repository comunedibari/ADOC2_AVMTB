package it.eng.dm.common.mail.utility;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharSetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.common.XMLChar;
import org.mozilla.universalchardet.UniversalDetector;


import it.eng.dm.common.mail.structure.XRicevuta;
import it.eng.dm.common.mail.structure.XTipoRicevuta;
import it.eng.dm.common.mail.structure.XTrasporto;
import it.eng.dm.common.mail.structure.XVerificaSicurezza;

/**
 * Classe di utilita'  per la mail
 * (sunto della classe più estesa presente su mailserver)
 * 
 * @author jravagnan
 * 
 */
public class MailUtil {

	MimeMessage message;

	/**
	 * Converte lo stream in stringa
	 * 
	 * @param stream
	 * @param encoding
	 * @return
	 */
	public static String streamToString(InputStream stream, String encoding) throws Exception {
		InputStream input = null;
		String ret = null;
		try {
			input = IOUtils.toBufferedInputStream(stream);
			ret = IOUtils.toString(input, encoding);
		} catch (Throwable e) {
			// Tento con il canale di default
			ret = IOUtils.toString(input, "ISO-8859-1");
		}
		return ret;
	}

	/**
	 * Ritorna gli indirizzi destinatari
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public List<String> getAddressesTo() throws MessagingException {
		Address[] addressarray = getRecipients(RecipientType.TO);
		List<String> addresslist = new ArrayList<String>();
		if (addressarray != null) {
			for (Address address : addressarray) {

				if (address instanceof InternetAddress) {
					addresslist.add(((InternetAddress) address).getAddress());
				} else {
					addresslist.add((StringUtils.remove(StringUtils.remove(address.toString(), "<"), ">")));
				}

			}
		}
		return addresslist;
	}

	/**
	 * Ritorna gli indirizzi destinatari in CC
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public List<String> getAddressesCc() throws MessagingException {
		List<String> addresslist = new ArrayList<String>();
		Address[] addressarray = getRecipients(RecipientType.CC);
		if (addressarray != null) {
			for (Address address : addressarray) {
				if (address instanceof InternetAddress) {
					addresslist.add(((InternetAddress) address).getAddress());
				} else {
					addresslist.add((StringUtils.remove(StringUtils.remove(address.toString(), "<"), ">")));
				}
			}
		}
		return addresslist;
	}

	/**
	 * Ritorna gli indirizzi destinatari in BCC
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public List<String> getAddressesBcc() throws MessagingException {
		Address[] addressarray = getRecipients(RecipientType.BCC);
		List<String> addresslist = new ArrayList<String>();
		if (addressarray != null) {
			for (Address address : addressarray) {
				if (address instanceof InternetAddress) {
					addresslist.add(((InternetAddress) address).getAddress());
				} else {
					addresslist.add((StringUtils.remove(StringUtils.remove(address.toString(), "<"), ">")));
				}
			}
		}
		return addresslist;
	}
	
	/**
	 * Recupera il charset dal datahandler
	 * @param dataHandler
	 * @return
	 * @throws Exception
	 */
	public static String getCharset(DataHandler dataHandler) throws Exception{
		String charset = null;
		try{
				charset = new ContentType(dataHandler.getContentType()).getParameter("charset");
				if(charset == null){
					//Provo a recuperarlo dallo stream
					byte[] buf = new byte[4096];
					UniversalDetector detector = new UniversalDetector(null);
				
					InputStream fis = new ByteArrayInputStream(IOUtils.toByteArray(dataHandler.getInputStream()));
					
				    // (2)
				    int nread;
				    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
				      detector.handleData(buf, 0, nread);
				    }
				    // (3)
				    detector.dataEnd();
		
				    // (4)
				    charset = detector.getDetectedCharset();
				   
				    // (5)
				    detector.reset();
				   			
					if(charset==null){
						charset = MimeUtility.getDefaultJavaCharset();
						if(charset==null){
							charset = "ISO-8859-1";
						}
					}
				}else{
					//RM Aggiunto per rimuovere i caratteri di escape del charset
					charset = CharSetUtils.delete(charset, "_$");
					charset = StringUtils.remove(charset, "ESC");
				}
			}catch(Exception e){
				charset = "ISO-8859-1";
			}
		return charset;
	}

	/**
	 * Ritorna un'istanza della mail
	 * 
	 * @param message
	 * @return
	 */
	public static MailUtil getInstance(MimeMessage message) {
		MailUtil util = new MailUtil();
		util.message = message;
		return util;
	}

	/**
	 * Ritorna il message id
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getMessageID() throws MessagingException {
		if (message.getMessageID() == null) {
			return null;
		} else {
			return (StringUtils.remove(StringUtils.remove(message.getMessageID(), "<"), ">"));
		}
	}

	/**
	 * Ritorna il mittente della mail
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getXMittente() throws MessagingException {
		String addressMittente = message.getHeader("X-Mittente", null);
		if (addressMittente == null) {
			if (message.getSender() != null) {
				addressMittente = ((InternetAddress) message.getSender()).getAddress();
			}
			if (addressMittente == null) {
				addressMittente = message.getHeader("From", null);
				if (addressMittente != null) {
					try {
						addressMittente = InternetAddress.parse(addressMittente)[0].getAddress();
					} catch (Exception e) {
						// Warning non sono riuscito a parserizzare l'indirizzo
					}
				}

			}
		}
		return addressMittente;
	}

	/**
	 * Ritorna la priorità del messaggio, dove 1 HIGH 3 NORMAL 5 LOW
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getPriority() throws MessagingException {
		String priority = message.getHeader("X-Priority", null);
		if (priority != null) {
			priority = StringUtils.remove(StringUtils.remove(priority, "<"), ">");
		}
		return priority;
	}

	/**
	 * Ritorna la richiesta di ricevuta lettura se presente
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getRichiestaAvvenutaLettura() throws MessagingException {
		String richiestaRicevuta = message.getHeader("Disposition-Notification-To", null);
		if (richiestaRicevuta != null) {
			richiestaRicevuta = StringUtils.remove(StringUtils.remove(richiestaRicevuta, "<"), ">");
		}
		return richiestaRicevuta;
	}

	/**
	 * Ritorna la richiesta di ricezione della mail
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getRichiestaRicevuta() throws MessagingException {
		String richiestaRicevuta = message.getHeader("Return-Receipt-To", null);
		if (richiestaRicevuta != null) {
			richiestaRicevuta = StringUtils.remove(StringUtils.remove(richiestaRicevuta, "<"), ">");
		}
		return richiestaRicevuta;
	}

	/**
	 * Ritorna il riferimento al message-id
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getXRiferimentoMessageID() throws MessagingException {
		String msgidrif = message.getHeader("X-Riferimento-Message-ID", null);
		if (msgidrif != null) {
			msgidrif = StringUtils.remove(StringUtils.remove(msgidrif, "<"), ">");
		}
		return msgidrif;
	}

	/**
	 * Ritorna il tipo di trasporto
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public XTrasporto getXTrasporto() throws MessagingException {
		return XTrasporto.valueOfValue(message.getHeader("X-Trasporto", null));
	}

	/**
	 * Ritorna la ricevuta
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public XRicevuta getXRicevuta() throws MessagingException {
		return XRicevuta.valueOfValue(message.getHeader("X-Ricevuta", null));
	}

	/**
	 * Ritorna il tipo di ricevuta
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public XTipoRicevuta getXTipoRicevuta() throws MessagingException {
		return XTipoRicevuta.valueOfValue(message.getHeader("X-TipoRicevuta", null));
	}

	/**
	 * Ritorna la verifica di sicurezza
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public XVerificaSicurezza getXVerificaSicurezza() throws MessagingException {
		return XVerificaSicurezza.valueOfValue(message.getHeader("X-VerificaSicurezza", null));
	}

	/**
	 * Recupera la data di ricezione
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public Date getReceiveDate() throws MessagingException {
		return message.getReceivedDate();
	}

	/**
	 * Recupera la data di invio
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public Date getSendDate() throws MessagingException {
		return message.getSentDate();
	}

	/**
	 * Ritorna l'oggetto della mail
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getSubject() throws Exception {
		if (message.getSubject() != null) {
			return MimeUtility.decodeText(message.getSubject());
		}
		return "";
	}

	/**
	 * Recupera il nome del file in attachments dal DataHandler
	 * 
	 * @param dataHandler
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	/**
	 * Recupera il nome del file in attachments dal DataHandler
	 * @param dataHandler
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getAttachmentName(final DataHandler dataHandler) throws UnsupportedEncodingException {
		String name = dataHandler.getName();
		if(name==null){		
			if(dataHandler.getDataSource()!=null) {
				name = dataHandler.getDataSource().getName();
			}
		}	
		if(StringUtils.isBlank(name)){
			name = dataHandler.getContentType();
			if(StringUtils.containsIgnoreCase(name, "message/rfc822")){
				//Controllo se riesco a recuperare il nome
				String[] splits = StringUtils.split(name, ";");
				for(String split:splits){
					if(StringUtils.containsIgnoreCase(split,"name=")){
						name = StringUtils.split(split, "=")[1];
						break;
					}
				}
				
				if(!StringUtils.containsIgnoreCase(name, "postacert.eml")){
					//l'attachments è una mail la inserisco con un nome fisso
					name = "Attachment_{0}.eml";	
				}
				
								
			}else if(StringUtils.containsIgnoreCase(name, "Message/Delivery-Status")){
				name = "DeliveryStatus_{0}.txt";
			} else{
				String[] splits = StringUtils.split(name, ";");
				for(String split:splits){
					if(StringUtils.containsIgnoreCase(split,"name=")){
						name = StringUtils.split(split, "=")[1];
						break;
					}
				}
			}
		}
		name = MimeUtility.decodeText(name);
		name = StringUtils.remove(name, "\"");
		if(name==null){
			name = "Attachments_{0}";
		}
		StringBuilder lStringBuilder = new StringBuilder();
		for (char c : name.toCharArray()){
			if (XMLChar.isValid(c)){
				lStringBuilder.append(c);
			}
		}
		return lStringBuilder.toString();
	}
	

	/**
	 * Recupera gli indirizzi in strict mode a false
	 * 
	 * @param type
	 * @return
	 * @throws MessagingException
	 */
	private Address[] getRecipients(Message.RecipientType type) throws MessagingException {
		try {
			return message.getRecipients(type);
		} catch (Exception e) {
			String s = message.getHeader(type.toString(), ",");
			return (s == null) ? null : InternetAddress.parseHeader(s, false);
		}
	}

	/**
	 * Verifica se la mail Ã¨ una delivery, controlla solamente la parte dell'header
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public Boolean IsDelivery() throws MessagingException {
		if (message.getContentType() != null) {
			return StringUtils.containsIgnoreCase(message.getContentType(), "report-type=delivery-status");
		} else {
			return false;
		}
	}

	/**
	 * Controllo se il messaggio Ã¨ una PEC
	 * 
	 * @param message
	 * @return
	 */
	public static boolean isPec(Message message) {
		try {
			if (XTrasporto.valueOfValue(message.getHeader("X-Trasporto")[0]) == XTrasporto.POSTA_CERTIFICATA) {
				return true;
			} else {
				return false;
			}
		} catch (Throwable e) {
			return false;
		}
	}
	
	public List<String> getAllMessageId() throws Exception {
		List<String> linee = IOUtils.readLines(message.getInputStream());
		List<String> messageIds = new ArrayList<String>();
		for (String lString : linee){
			if (lString.toLowerCase().startsWith("Message-Id:".toLowerCase())){
				String messageId = lString.substring("Message-Id:".length()).trim();
				messageId = StringUtils.remove(StringUtils.remove(messageId, "<"),">");
				messageIds.add(messageId);
			}
		}
		return messageIds;
	}
	
	

}