package it.eng.utility.email.operation.impl.checkmessageinfolderoperation;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;

@MessageOperation(description = "Effettua una verifica del messaggio nella folder configurata", name = "CheckMessageInFolderOperation")
public class CheckMessageInFolderOperation extends AbstractMessageOperation<CheckMessageInFolderOperationBean> {

	@ConfigOperation(title = "Folder di destinazione", name = "checkmessageinfolderoperation.destinationfolder", description = "Indica il folder di destinazione")
	private String destinationfolder = null;

	@Override
	public CheckMessageInFolderOperationBean elaborate(MessageInfos message) throws Exception {

		if (destinationfolder == null) {
			throw new Exception("Parametro di configurazione checkmessageinfolderoperation.destinationfolder non configurato!");
		}

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		// Creo il folder, se esiste già non fa niente
		if (getMailreceiver() != null)
			getMailreceiver().createFolder(destinationfolder, true);
		if (getMailreceiverPop3() != null)
			getMailreceiverPop3().createFolder(destinationfolder, true);

		
		// Copio il messaggio
		CheckMessageInFolderOperationBean bean = new CheckMessageInFolderOperationBean();
		Boolean checkok = getMailreceiver() != null ? getMailreceiver().checkMessageInFolder(message.getHeaderinfo().getMessageid(), message.getUid(), message.getUidValidity(),
				destinationfolder) :
					getMailreceiverPop3().checkMessageInFolder(message.getHeaderinfo().getMessageid(), message.getUid(), message.getUidValidity(),
							destinationfolder);
		bean.setCheckok(checkok);
		return bean;
	}

	public String getDestinationfolder() {
		return destinationfolder;
	}

	public void setDestinationfolder(String destinationfolder) {
		this.destinationfolder = destinationfolder;
	}

}
