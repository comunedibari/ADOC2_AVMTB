package it.eng.utility.email.operation.impl.archiveoperation;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.reader.ExtendImapMailReceiver;

/**
 * classe astratta di riferimento per le elaborazioni che riguardano l'archiviazione della email
 * 
 * @author jravagnan
 *
 */
public abstract class AbstractDirector {

	public abstract String process(MessageInfos message, String messageId) throws Exception;

}
