package it.eng.utility.email.operation;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.reader.ExtendImapMailReceiver;
import it.eng.utility.email.reader.ExtendPOP3MailReceiver;
import it.eng.utility.email.storage.StorageCenter;
import it.eng.utility.storageutil.StorageService;

/**
 * Interfaccia da implementare per il processamento dell'operazione
 * 
 * @author michele
 *
 */
public abstract class AbstractMessageOperation<E> {

	/**
	 * Elabora il messaggio. In caso di errore restituisce una eccezione <br>
	 * che fa bloccare l'elaborazione del MimeMessage
	 * 
	 * @param message
	 */
	public abstract E elaborate(MessageInfos message) throws Exception;

	/**
	 * Mail receiver della mailbox
	 */
	protected ExtendImapMailReceiver mailreceiver;
	protected ExtendPOP3MailReceiver mailreceiverPop3;
	

	protected StorageService storage;

	protected String idMailbox;

	protected String idOperation;

	/**
	 * Tentativo corrente di esecuzione dell'operazione
	 */

	protected Short currentNumTryOperation;

	/**
	 * Numero massimo di tentativi dell'esecuzione dell'operazione
	 */

	protected Short maxTryOperation;

	public ExtendImapMailReceiver getMailreceiver() {
		return mailreceiver;
	}

	public void setMailreceiver(ExtendImapMailReceiver mailreceiver) {
		this.mailreceiver = mailreceiver;
		// configureStorage(); le singole operazioni che necessitano di uno
		// storage specifico
		// invocano il metodo, altrimenti verrebbe ricalcolata la mappa degli
		// utilizzatori per
		// ricercare uno storage che non è presente per quell'operazione
	}

	protected String getName() {
		MessageOperation lMessageOperation = getClass().getAnnotation(MessageOperation.class);
		return lMessageOperation.name();
	}

	protected void configureStorage() {
		StorageCenter lStorageCenter = new StorageCenter();
		MessageOperation lMessageOperation = getClass().getAnnotation(MessageOperation.class);
		String operationName = lMessageOperation.name();
		if (mailreceiver != null)
			storage = lStorageCenter.getOperationStorage(mailreceiver.getConfigbean().getMailboxid(), operationName);
		if (mailreceiverPop3 != null)
			storage = lStorageCenter.getOperationStorage(mailreceiverPop3.getConfigbean().getMailboxid(), operationName);

	}

	public String getIdMailbox() {
		return idMailbox;
	}

	public void setIdMailbox(String idMailbox) {
		this.idMailbox = idMailbox;
	}

	public String getIdOperation() {
		return idOperation;
	}

	public void setIdOperation(String idOperation) {
		this.idOperation = idOperation;
	}

	public StorageService getStorage() {
		return storage;
	}

	public Short getCurrentNumTryOperation() {
		return currentNumTryOperation;
	}

	public void setCurrentNumTryOperation(Short currentNumTryOperation) {
		this.currentNumTryOperation = currentNumTryOperation;
	}

	public Short getMaxTryOperation() {
		return maxTryOperation;
	}

	public void setMaxTryOperation(Short maxTryOperation) {
		this.maxTryOperation = maxTryOperation;
	}

	public ExtendPOP3MailReceiver getMailreceiverPop3() {
		return mailreceiverPop3;
	}

	public void setMailreceiverPop3(ExtendPOP3MailReceiver mailreceiverPop3) {
		this.mailreceiverPop3 = mailreceiverPop3;
	}
	

}