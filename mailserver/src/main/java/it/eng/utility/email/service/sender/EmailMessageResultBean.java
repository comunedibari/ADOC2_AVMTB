package it.eng.utility.email.service.sender;

import java.util.List;

/**
 * Bean contenente il messsageid e gli indirizzi a cui ha inviato
 * @author michele
 *
 */
public class EmailMessageResultBean {

	private String messageid;
	private List<String> addresslistto;
	private List<String> addresslistcc;
	private List<String> addresslistbcc;
	private byte[] mimemessage;	
	
	public byte[] getMimemessage() {
		return mimemessage;
	}
	public void setMimemessage(byte[] mimemessage) {
		this.mimemessage = mimemessage;
	}
	public String getMessageid() {
		return messageid;
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
	public List<String> getAddresslistto() {
		return addresslistto;
	}
	public void setAddresslistto(List<String> addresslistto) {
		this.addresslistto = addresslistto;
	}
	public List<String> getAddresslistcc() {
		return addresslistcc;
	}
	public void setAddresslistcc(List<String> addresslistcc) {
		this.addresslistcc = addresslistcc;
	}
	public List<String> getAddresslistbcc() {
		return addresslistbcc;
	}
	public void setAddresslistbcc(List<String> addresslistbcc) {
		this.addresslistbcc = addresslistbcc;
	}
	
}
