package it.eng.utility.email.client;

import java.util.Properties;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.operation.impl.wserviceoperation.WSServiceBean;

/**
 * Interfaccia da implementare per il richiamo dei metodi di gestione mail
 * 
 * @author michele
 *
 */
public interface IMailOperation {

	/**
	 * Metodo che viene chiamato all'arrivo di una mail
	 * 
	 * @param bean
	 * @return
	 */
	public WSServiceBean serviceCall(MessageInfos messageinfos, Properties mailboxconfig);
}