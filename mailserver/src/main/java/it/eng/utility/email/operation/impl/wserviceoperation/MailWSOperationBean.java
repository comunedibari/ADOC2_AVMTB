package it.eng.utility.email.operation.impl.wserviceoperation;

import java.util.HashMap;
import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean di comunicazione REST dei servizi
 * @author michele
 *
 */
@XmlRootElement
public class MailWSOperationBean {
	
	/**
	 * Risultati delle operazioni
	 */
	private HashMap<String,String> resultoperation = new HashMap<String, String>();

	/**
	 * Operazione da chiamare
	 * @return
	 */
	private String operationname;
	
	/**
	 * mailboxurl
	 * @return
	 */
	private Properties mailboxproperties;
	
	
	/**
	 * Indica se è uno spam o meno
	 * @return
	 */
	private Boolean isSpam;
			
	public Boolean getIsSpam() {
		return isSpam;
	}

	public void setIsSpam(Boolean isSpam) {
		this.isSpam = isSpam;
	}

	public Properties getMailboxproperties() {
		return mailboxproperties;
	}

	public void setMailboxproperties(Properties mailboxproperties) {
		this.mailboxproperties = mailboxproperties;
	}

	/**
	 * Ente della casella
	 */
	private String codente;
		
	public String getCodente() {
		return codente;
	}

	public void setCodente(String codente) {
		this.codente = codente;
	}

	public String getOperationname() {
		return operationname;
	}

	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}

	public HashMap<String, String> getResultoperation() {
		return resultoperation;
	}

	public void setResultoperation(HashMap<String, String> resultoperation) {
		this.resultoperation = resultoperation;
	}


}