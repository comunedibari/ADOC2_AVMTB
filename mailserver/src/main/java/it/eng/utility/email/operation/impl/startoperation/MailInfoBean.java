package it.eng.utility.email.operation.impl.startoperation;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.daticert.Postacert;
import it.eng.aurigamailbusiness.structure.HeaderInfo;

/**
 * Bean contenente le informazioni dell'operazione di start
 * 
 * @author michele
 *
 */

@XmlRootElement
public class MailInfoBean {

	private HeaderInfo header;

	private Postacert daticert;

	public HeaderInfo getHeader() {
		return header;
	}

	public void setHeader(HeaderInfo header) {
		this.header = header;
	}

	public Postacert getDaticert() {
		return daticert;
	}

	public void setDaticert(Postacert daticert) {
		this.daticert = daticert;
	}

}