package it.eng.utility.email.service.sender;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean per l'invio della mail
 * @author michele
 */
@XmlRootElement
public class EmailSenderBean {

	/**
	 * Oggetto della mail
	 */
	private String subject;
	
	/**
	 * Body della mail
	 */
	private String body;
	
	/**
	 * Indica se il body è un html (default false)
	 */
	private Boolean isHtml;
	
	/**
	 * Lista degli attachments della mail
	 */
	private List<EmailSenderAttachmentsBean> attachments;
	
	/**
	 * Lista delgi indirizzi a cui spedire
	 */
	private List<String> addressTo;
	
	/**
	 * Liusta degli indirizzi in copia conoscenza
	 */
	private List<String> addressCc;
	
	/**
	 * Lista degli inidirizzi in copia conoscenza nascosta
	 */
	private List<String> addressBcc;
	
	/**
	 * Indirizzo mittente
	 */
	private String addressFrom;
	
	/**
	 * Account da cui prendere le configurazioni
	 */
	private String account;
	
	/**
	 * Folder in cui salvare la mail in uscita (Se null o vuoto non salva la mail nella webmail)
	 */
	private Boolean saveToSent;

	
	public void addAddressTo(String address){
		if(addressTo==null){
			addressTo = new ArrayList<String>();
		}
		addressTo.add(address);
	}
	
	public void addAddressCc(String address){
		if(addressCc==null){
			addressCc = new ArrayList<String>();
		}
		addressCc.add(address);
	}
	
	public void addAddressBcc(String address){
		if(addressBcc==null){
			addressBcc = new ArrayList<String>();
		}
		addressBcc.add(address);
	}
	
	public Boolean getSaveToSent() {
		return saveToSent;
	}

	public void setSaveToSent(Boolean saveToSent) {
		this.saveToSent = saveToSent;
	}

	public List<String> getAddressTo() {
		return addressTo;
	}

	public String getAddressFrom() {
		return addressFrom;
	}

	public List<String> getAddressCc() {
		return addressCc;
	}

	public void setAddressCc(List<String> addressCc) {
		this.addressCc = addressCc;
	}

	public List<String> getAddressBcc() {
		return addressBcc;
	}

	public void setAddressBcc(List<String> addressBcc) {
		this.addressBcc = addressBcc;
	}

	public void setAddressTo(List<String> addressTo) {
		this.addressTo = addressTo;
	}

	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Boolean getIsHtml() {
		return isHtml;
	}

	public void setIsHtml(Boolean isHtml) {
		this.isHtml = isHtml;
	}
		
	public List<EmailSenderAttachmentsBean> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<EmailSenderAttachmentsBean> attachments) {
		this.attachments = attachments;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}