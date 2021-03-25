package it.eng.utility.email.operation.impl.milanosynchrodeleteoperation;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;

@MessageOperation(description = "Effettua la cancellazione del messaggio dal folder specificato in configurazione solo se il messaggio "
		+ "viene trovato sul DB del PecManager del Comune di Milano", name = "MilanoSyncroDeleteOperation")
public class MilanoSyncroDeleteOperation extends AbstractMessageOperation<MilanoSyncroDeleteBean> {

	@ConfigOperation(title = "Folder in cui sono contenuti i messaggi", name = "deletemessageoperation.folder", description = "Indica il folder da cui cancellare il messaggio se non cofigurato prende quello di default configurato sulla mailbox")
	private String folder = null;

	@Override
	public MilanoSyncroDeleteBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		MilanoSyncroDeleteBean bean = new MilanoSyncroDeleteBean();
		if (getMailreceiver() != null)
			getMailreceiver().syncroAndDeleteBean(message.getHeaderinfo().getMessageid(), folder);
		if (getMailreceiverPop3() != null)
			getMailreceiverPop3().syncroAndDeleteBean(message.getHeaderinfo().getMessageid(), folder);
		bean.setDeleteok(true);
		return bean;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

}
