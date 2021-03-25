package it.eng.utility.email.operation.impl.deletemessageoperation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.process.exception.PlannedOperation;
import it.eng.utility.email.scheduler.DeleteScheduler;

@MessageOperation(description = "Effettua la cancellazione del messaggio dal folder specificato in configurazione", name = "DeleteMessageOperation")
public class DeleteMessageOperation extends AbstractMessageOperation<DeleteMessageBean> {

	@ConfigOperation(title = "Folder in cui sono contenuti i messaggi", name = "deletemessageoperation.folder", description = "Indica il folder da cui cancellare il messaggio se non cofigurato prende quello di default configurato sulla mailbox")
	private String folder = null;

	private Logger log = LogManager.getLogger(DeleteMessageOperation.class);

	@Override
	public DeleteMessageBean elaborate(MessageInfos message) throws Exception, PlannedOperation {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		Boolean modeSynchro = true;

		try {

			// se è presente il bean significa che la cancellazione è schedulata
			DeleteScheduler deleteSchedulerBean = SpringAppContext.getContext().getBean(DeleteScheduler.class);

			if (deleteSchedulerBean.getMailboxDeleteConfig() == null || deleteSchedulerBean.getMailboxDeleteConfig().isEmpty()) {
				// nessuna configurazione specifica per alcune mailbox: tutte le mailbox attive saranno pulite in modalità asincrona
				modeSynchro = false;
				log.info("Cancellazione asincrona per tutte le mailbox");
			} else {
				// verifico se la mailbox non ha una configurazione specifica, in questo caso la mailbox sarà pulita in modalità sincrona
				for (int index = 0; index < deleteSchedulerBean.getMailboxDeleteConfig().size() && modeSynchro; index++) {
					if (deleteSchedulerBean.getMailboxDeleteConfig().get(index).getIdMailbox().equals(this.getIdMailbox())) {
						// trovata configurazione specifica
						modeSynchro = false;
					}
				}
			}

		} catch (NoSuchBeanDefinitionException e) {
			// nessuna configurazione per la cancellazione asincrona -> cancellazione sincrona
			modeSynchro = true;
		}

		if (modeSynchro) {
			// modalità di cancellazione sincrona per la mailbox
			if (getMailreceiver() != null)
				return getMailreceiver().deleteMsg(message.getHeaderinfo().getMessageid(), message.getUid(), message.getUidValidity(), folder);
			if (getMailreceiverPop3() != null)
				return getMailreceiverPop3().deleteMsg(message.getHeaderinfo().getMessageid(), message.getUid(), message.getUidValidity(), folder);
			return new DeleteMessageBean();
		} else {
			// ritorno un oggetto OperationScheduled, in modo da segnalare che stiamo affrontando un'operazione schedulata
			// modalità di cancellazione asincrona
			throw new PlannedOperation();
		}

	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}
}