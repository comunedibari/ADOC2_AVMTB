package it.eng.utility.email.operation.impl.startoperation;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;

/**
 * Operazione di start
 * 
 * @author michele
 *
 */
@MessageOperation(description = "Operazione di controllo iniziale della mail", name = "StartOperation")
public class MailInfoOperation extends AbstractMessageOperation<MailInfoBean> {

	@Override
	public MailInfoBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		MailInfoBean bean = new MailInfoBean();

		bean.setDaticert(message.getDaticert());
		bean.setHeader(message.getHeaderinfo());

		return bean;
	}
}