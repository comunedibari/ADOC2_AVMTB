package it.eng.dm.common.mail.structure;

import java.io.IOException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;

import it.eng.dm.common.mail.bean.MailAttachmentsBean;
import it.eng.dm.common.mail.daticert.Postacert;
import it.eng.dm.common.mail.utility.XmlFieldUtil;

/**
 * Bean contenente le informazioni del MimeMessage
 * 
 * @author michele
 * 
 */
public class MessageInfos {
	
	public static String RETURN_RECEIPT_ATTACH_THUNDERBIRD = "MDNPart2.txt";
	public static String RETURN_RECEIPT_ATTACH_OUTLOOK = "message/disposition-notification";

	/**
	 * Indica se la mail Ã¨ una delivery
	 */
	private Boolean isdeliverystatus;

	/**
	 * Indica se la mail Ã¨ un probabile spam
	 */
	private Boolean ispam;

	/**
	 * Riferimento al file del messaggio
	 */
	// Da modificare
	// Adesso il riferimento interno Ã¨ l'uri dello storage
	private String uri;

	/**
	 * Cetificato
	 */
	private Set<Certificate> signatures;

	/**
	 * Daticert.xml
	 */
	private Postacert daticert;

	/**
	 * Body della mail
	 */
	private List<BodyMessageBean> bodyParts;

	/**
	 * Informazioni dell'header
	 */
	private HeaderInfo headerinfo;

	/**
	 * Postercert.eml contenuto nella PEC
	 */
	private MessageInfos postecerteml;

	/**
	 * Attachments della mail
	 */
	private List<MailAttachmentsBean> attachments;

	/**
	 * Eventuali sotto mail in attachments RFC822
	 */
	private List<MessageInfos> subinfos = new ArrayList<MessageInfos>();

	/**
	 * Operation results
	 */
	private LinkedHashMap<String, Object> opresult = new LinkedHashMap<String, Object>();

	/**
	 * 
	 */
	private String errorString = "";
	
	private List<String> allMessageIds;

	public List<String> getAllMessageIds() {
		return allMessageIds;
	}

	public void setAllMessageIds(List<String> allMessageIds) {
		this.allMessageIds = allMessageIds;
	}
	
	/**
	 * Ritorna il risultato delle operazioni con prefisso passato in ingresso
	 * 
	 * @param prefix
	 * @return
	 */
	public List<Object> getOpResultWithStartName(String prefix) {
		List<Object> retresult = new ArrayList<Object>();
		Set<String> keys = opresult.keySet();
		for (String key : keys) {
			if (StringUtils.startsWith(key, prefix)) {
				retresult.add(opresult.get(key));
			}
		}
		return retresult;
	}

	/**
	 * Ritorna il body della mail come stringa Se PEC gli attachments della mail inbustata postacert.eml Se non PEC gli attachment della
	 * mail
	 * 
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getBodyToStringWithPrincipalMail() throws Exception {
		String ret = "";

		if ((isPec() || isAnomaliaPec()) && (!isRicevuta()) && postecerteml != null) {
			if (postecerteml.getBodyParts() != null) {
				for (BodyMessageBean part : postecerteml.getBodyParts()) {
					ret += part.getBody();
				}
			}
		} else {
			if (getBodyParts() != null) {
				for (BodyMessageBean part : getBodyParts()) {
					ret += part.getBody();
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il body della mail come stringa Se PEC gli attachments della mail inbustata postacert.eml Se non PEC gli attachment della
	 * mail
	 * 
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getBodyTextPlainOnly() throws Exception {
		String ret = "";

		if ((isPec() || isAnomaliaPec()) && (!isRicevuta()) && postecerteml != null) {
			if (postecerteml.getBodyParts() != null) {
				for (BodyMessageBean part : postecerteml.getBodyParts()) {
					if (part.getType().equals("text/plain")) {
						ret += part.getBody();
					}
				}
			}
		} else {
			if (getBodyParts() != null) {
				for (BodyMessageBean part : getBodyParts()) {
					if (part.getType().equals("text/plain")) {
						ret += part.getBody();
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * ritorna il body della busta più esterna
	 * @return
	 * @throws Exception
	 */
	public String getBodyExternalPlain() throws Exception {
		String ret = "";
		if (getBodyParts() != null) {
			for (BodyMessageBean part : getBodyParts()) {
				if (part.getType().equals("text/plain")) {
					ret += part.getBody();
				}
			}
		}
		return ret;
	}
	
	/**
	 * ritorna il body della busta più esterna(se c'è) in HTML
	 * @return
	 * @throws Exception
	 */
	public String getBodyExternalHtml(){
		String ret = "";
		if (getBodyParts() != null) {
			for (BodyMessageBean part : getBodyParts()) {
				if (part.getType().equals("text/html")) {
					ret += part.getBody();
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il body della mail come stringa Se PEC gli attachments della mail inbustata postacert.eml Se non PEC gli attachment della
	 * mail
	 * 
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getBodyHtml() throws Exception {
		String ret = "";

		if (isPec() || isAnomaliaPec()) {
			if (postecerteml.getBodyParts() != null) {
				for (BodyMessageBean part : postecerteml.getBodyParts()) {
					if (part.getType().equals("text/html")) {
						ret += part.getBody();
					}
				}
			}
		} else {
			if (getBodyParts() != null) {
				for (BodyMessageBean part : getBodyParts()) {
					if (part.getType().equals("text/html")) {
						ret += part.getBody();
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna la lista dei desinatari in un'unica stringa
	 * 
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public String getDestinatartiToStringWithPrincipalMail(String separator) throws Exception {
		String ret = "";

		if ((isPec() || isAnomaliaPec()) && postecerteml != null) {
			if (postecerteml.headerinfo.getDestinatarito() != null) {
				for (String dest : postecerteml.headerinfo.getDestinatarito()) {
					ret += (StringUtils.remove(StringUtils.remove(dest, "<"), ">") + separator);
				}
			}
		} else {
			if (headerinfo.getDestinatarito() != null) {
				for (String dest : headerinfo.getDestinatarito()) {
					ret += (StringUtils.remove(StringUtils.remove(dest, "<"), ">") + separator);
				}
			}
		}
		if (ret.length() > separator.length()) {
			ret = ret.substring(0, ret.length() - separator.length());
		}
		return ret;
	}

	/**
	 * Ritorna la lista dei desinatari in CC in un'unica stringa
	 * 
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public String getDestinatartiCCStringWithPrincipalMail(String separator) throws Exception {
		String ret = "";

		if ((isPec() || isAnomaliaPec()) && postecerteml != null) {
			if (postecerteml.headerinfo.getDestinataricc() != null) {
				for (String dest : postecerteml.headerinfo.getDestinataricc()) {
					ret += (StringUtils.remove(StringUtils.remove(dest, "<"), ">") + separator);
				}
			}
		} else {
			if (headerinfo.getDestinataricc() != null) {
				for (String dest : headerinfo.getDestinataricc()) {
					ret += (StringUtils.remove(StringUtils.remove(dest, "<"), ">") + separator);
				}
			}
		}
		if (ret.length() > separator.length()) {
			ret = ret.substring(0, ret.length() - separator.length());
		}
		return ret;
	}

	/**
	 * Ritorna il mittente della mail sul messaggio principale
	 * 
	 * @return
	 */
	public String getMittenteWithPrincipalMail() {
		if (isPec() && !isRicevuta()) {
			if (getDaticert() != null) {
				return getDaticert().getIntestazione().getMittente();
			} else if (getHeaderinfo().getMittente() != null) {
				return getHeaderinfo().getMittente();
			}
		} else if (isAnomaliaPec()) {
			if (getPostecerteml().getHeaderinfo().getMittente() != null) {
				return getPostecerteml().getHeaderinfo().getMittente();
			} else if (getPostecerteml().getDaticert() != null) {
				return getPostecerteml().getDaticert().getIntestazione().getMittente();
			}
		} else if (getHeaderinfo().getRicevuta() != null
				&& (getHeaderinfo().getRicevuta() == XRicevuta.AVVENUTA_CONSEGNA
						|| getHeaderinfo().getRicevuta() == XRicevuta.ERRORE_CONSEGNA || getHeaderinfo().getRicevuta() == XRicevuta.PREAVVISO_ERRORE_CONSEGNA))
			return XmlFieldUtil.cleanField(getDaticert().getDati().getConsegna());
		return getHeaderinfo().getMittente();
	}

	/**
	 * Ritorna la prioritÃ  della mail
	 * 
	 * @return
	 */
	public String getPriority() {
		String ret = null;
		if ((isPec() || isAnomaliaPec()) && !isRicevuta() && postecerteml != null) {
			if (postecerteml.headerinfo.getPriority() != null) {
				ret = postecerteml.headerinfo.getPriority();
			}
		} else {
			if (headerinfo.getPriority() != null) {
				ret = headerinfo.getPriority();
			}
		}
		return ret;
	}

	/**
	 * Ritorna la richiesta di lettura della mail
	 * 
	 * @return
	 */
	public Boolean getRichiestaAvvenutaLettura() {
		if ((isPec() || isAnomaliaPec()) && !isRicevuta() && postecerteml != null) {
			if (postecerteml.headerinfo.getRichiestaLettura() != null) {
				return true;
			}
		} else {
			if (headerinfo.getRichiestaLettura() != null) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Indica se il messaggio Ã¨ un messaggio di PEC
	 * 
	 * @return
	 */
	public boolean isPec() {
		if (headerinfo.getTrasporto() == XTrasporto.POSTA_CERTIFICATA || isRicevuta()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Indica se la busta Ã¨ di tipo anomalia
	 * 
	 * @return
	 */
	public boolean isAnomaliaPec() {
		if (headerinfo.getTrasporto() == XTrasporto.ERRORE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Indica se il messaggio di PEC Ã¨ una ricevuta
	 * 
	 * @return
	 */
	public boolean isRicevuta() {
		if (headerinfo.getTrasporto() == null && (headerinfo.getRicevuta() != null || headerinfo.getTiporicevuta() != null)
				&& postecerteml != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ritorna tutti gli attachments linearizzati
	 * 
	 * @return
	 */
	public List<MailAttachmentsBean> getLinearAttachments() {
		List<MailAttachmentsBean> attachments = new ArrayList<MailAttachmentsBean>();
		extractAttachments(attachments, this);
		return attachments;
	}
	
	/**
	 * Ritorna gli attachment di primo livello
	 * 
	 * @return
	 */
	public List<MailAttachmentsBean> getDirectAttachments() {
		List<MailAttachmentsBean> attachments = new ArrayList<MailAttachmentsBean>();
		extractDirectAttachments(attachments, this);
		return attachments;
	}

	/**
	 * Ritorna gli attachments della mail principale Se PEC gli attachments della mail inbustata postacert.eml Se non PEC gli attachment
	 * della mail
	 * 
	 * @return
	 */
	public List<MailAttachmentsBean> getAttachmentsWithPrincipalMail() {
		List<MailAttachmentsBean> attachments = new ArrayList<MailAttachmentsBean>();
		if (isPec() || isAnomaliaPec() && postecerteml != null) {
			if (postecerteml != null && postecerteml.getAttachments() != null) {
				// Prendo gli attachment della mail postacert.eml
				attachments.addAll(postecerteml.getAttachments());
			}
		} else {
			// se è delivery non considero attachment
			if (!isDelivery()) {
				// Prendo gli attachment della mail principale
				attachments.addAll(getAttachments());
			}
		}
		return attachments;
	}

	/**
	 * Ritorna l'oggetto della mail principale Se PEC gli attachments della mail inbustata postacert.eml Se non PEC gli attachment della
	 * mail
	 * 
	 * @return
	 */
	public String getSubjectWithPrincipalMail() {
		if ((isPec() || isAnomaliaPec()) && !isRicevuta() && postecerteml != null) {
			// Prendo gli attachment della mail postacert.eml
			return postecerteml.getHeaderinfo().getSubject();
		} else {
			// Prendo gli attachment della mail principale
			return getHeaderinfo().getSubject();
		}
	}

	/**
	 * Estrae ricorsivamente gli attachments e li salva nella lista passata in ingresso
	 * 
	 * @param list
	 * @param infos
	 */
	private void extractAttachments(List<MailAttachmentsBean> list, MessageInfos infos) {
		if (infos != null) {
			if (infos.getAttachments() != null) {
				list.addAll(infos.getAttachments());
				if (infos.getSubinfos() != null) {
					for (MessageInfos info : infos.getSubinfos()) {
						extractAttachments(list, info);
					}
				}
			}
		}
	}
	
	private void extractDirectAttachments(List<MailAttachmentsBean> list, MessageInfos infos) {
		if (infos != null) {
			if (infos.getAttachments() != null) {
				list.addAll(infos.getAttachments());
			}
		}
	}


	/**
	 * Aggiungo un bodypart
	 * 
	 * @param bodypart
	 */
	public void addBodyPart(String body, String type) {
		if (bodyParts == null) {
			bodyParts = new ArrayList<BodyMessageBean>();
		}
		bodyParts.add(new BodyMessageBean(type, body));
	}

	public LinkedHashMap<String, Object> getOpresult() {
		return opresult;
	}

	public void setOpresult(LinkedHashMap<String, Object> opresult) {
		this.opresult = opresult;
	}

	public Set<Certificate> getSignatures() {
		return signatures;
	}

	public void setSignatures(Set<Certificate> signatures) {
		this.signatures = signatures;
	}

	public Postacert getDaticert() {
		return daticert;
	}

	public void setDaticert(Postacert daticert) {
		this.daticert = daticert;
	}

	public List<BodyMessageBean> getBodyParts() {
		return bodyParts;
	}

	public void setBodyParts(List<BodyMessageBean> bodyParts) {
		this.bodyParts = bodyParts;
	}

	public MessageInfos getPostecerteml() {
		return postecerteml;
	}

	public void setPostecerteml(MessageInfos postecerteml) {
		this.postecerteml = postecerteml;
	}

	public HeaderInfo getHeaderinfo() {
		if (headerinfo == null) {
			headerinfo = new HeaderInfo();
		}
		return headerinfo;
	}

	public void setHeaderinfo(HeaderInfo headerinfo) {
		this.headerinfo = headerinfo;
	}

	public List<MailAttachmentsBean> getAttachments() {
		if (attachments == null) {
			attachments = new ArrayList<MailAttachmentsBean>();
		}
		return attachments;
	}

	protected void setAttachments(List<MailAttachmentsBean> attachments) {
		this.attachments = attachments;
	}

	public List<MessageInfos> getSubinfos() {
		if (subinfos == null) {
			subinfos = new ArrayList<MessageInfos>();
		}
		return subinfos;
	}

	public void setSubinfos(List<MessageInfos> subinfos) {
		this.subinfos = subinfos;
	}

	public void setIspam(Boolean ispam) {
		this.ispam = ispam;
	}

	public Boolean isSpam() {
		if (this.ispam == null) {
			return false;
		} else {
			return this.ispam;
		}
	}

	public Boolean isDelivery() {
		if (this.isdeliverystatus == null) {
			return false;
		} else {
			return this.isdeliverystatus;
		}
	}

	public void setIsDeliveryStatus(Boolean isdeliverystatus) {
		this.isdeliverystatus = isdeliverystatus;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}
	
	
	public String getErrorString() {
		return errorString;
	}

	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}
	
	public Boolean isReturnReceipt() {
		try {
			// modifica 12/10/2014 cambio condizioni di verifica
			//if (!isDelivery() && postecerteml == null) {
			if (!isDelivery()) {
				if (getBodyTextPlainOnly().contains("Original-Message-ID:")) {
					return true;
				} else {
					List<MailAttachmentsBean> attachs = getAttachmentsWithPrincipalMail();
					for (MailAttachmentsBean bean : attachs) {
						if (bean.getFilename().equalsIgnoreCase(RETURN_RECEIPT_ATTACH_THUNDERBIRD)
								|| bean.getFilename().equalsIgnoreCase(RETURN_RECEIPT_ATTACH_OUTLOOK)) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

}