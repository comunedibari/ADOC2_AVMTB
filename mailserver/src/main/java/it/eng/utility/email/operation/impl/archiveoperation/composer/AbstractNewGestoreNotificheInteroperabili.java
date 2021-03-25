package it.eng.utility.email.operation.impl.archiveoperation.composer;

import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.utility.email.operation.impl.interoperation.InterBean;

public abstract class AbstractNewGestoreNotificheInteroperabili {

	public abstract boolean process(EmailBean bean, InterBean busta);
}
