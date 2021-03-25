package it.eng.utility.email.operation.impl.forwardmessageoperation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean contenente l'informazione di riusciuta copia del messaggio
 * @author michele
 *
 */
@XmlRootElement
public class ForwardMessageBean {

	private Boolean forwardok;
	private String addresslist;
	
	public String getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(String addresslist) {
		this.addresslist = addresslist;
	}

	public Boolean getForwardok() {
		return forwardok;
	}

	public void setForwardok(Boolean forwardok) {
		this.forwardok = forwardok;
	}
	
}