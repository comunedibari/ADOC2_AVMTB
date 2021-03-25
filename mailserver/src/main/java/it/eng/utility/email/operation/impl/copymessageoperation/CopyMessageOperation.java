package it.eng.utility.email.operation.impl.copymessageoperation;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;

@MessageOperation(description = "Effettua una copia del messaggio dal folder principale a quello configurato", name = "CopyMessageOperation")
public class CopyMessageOperation extends AbstractMessageOperation<CopyMessageBean> {

	@ConfigOperation(title = "Folder di destinazione", name = "copymessageoperation.destinationfolder", description = "Indica il folder di destinazione dove copiare la mail, se il folder non esiste viene creato automaticamente")
	private String destinationfolder = null;

	@Override
	public CopyMessageBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		if (destinationfolder == null) {
			throw new Exception("Parametro di configurazione copymessageoperation.destinationfolder non configurato!");
		}

		// Creo il folder, se esiste già non fa niente
		if (getMailreceiver() != null)
			getMailreceiver().createFolder(destinationfolder, true);
		if (getMailreceiverPop3() != null)
			getMailreceiverPop3().createFolder(destinationfolder, true);

		// Copio il messaggio
		CopyMessageBean bean = new CopyMessageBean();
		if (getMailreceiver() != null) {
			getMailreceiver().copy(message.getHeaderinfo().getMessageid(), message.getUid(), message.getUidValidity(), null, destinationfolder);			
		} else
			getMailreceiverPop3().copy(message.getHeaderinfo().getMessageid(), message.getUid(), message.getUidValidity(), null, destinationfolder);

		bean.setCopyok(true);
		return bean;
	}

	public String getDestinationfolder() {
		return destinationfolder;
	}

	public void setDestinationfolder(String destinationfolder) {
		this.destinationfolder = destinationfolder;
	}

}
