package it.eng.utility.email.client;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;

import org.reflections.Reflections;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.operation.impl.wserviceoperation.MailWSOperationBean;
import it.eng.utility.email.operation.impl.wserviceoperation.WSServiceBean;
import it.eng.utility.email.util.mail.MailVerifier;
import it.eng.utility.email.util.xml.XmlUtil;

/**
 * Classe generale per la registrazione dei metodi di invocazione
 * 
 * @author michele
 */
public class ServiceGroupObserver {

	private static HashMap<String, IMailOperation> mailoperation = new HashMap<String, IMailOperation>();

	private static File tempdir = null;

	/**
	 * Aggiunge un osservatore per una determinata operazione
	 * 
	 * @param observer
	 * @param action
	 */
	public static void addOperationRegistry(String operationname, IMailOperation operation) {
		// if(!mailoperation.containsKey(operationname)){
		mailoperation.put(operationname, operation);
		// }
	}

	/**
	 * Setta la directory temporana di lavoro
	 * 
	 * @param tempdir
	 * @throws Exception
	 */
	public static void setTempdir(File tempdirfile) throws Exception {
		tempdir = tempdirfile;

		Reflections reflection = new Reflections("it.eng.utility.email");

		Set<Class<?>> classesXML = reflection.getTypesAnnotatedWith(XmlRootElement.class);
		XmlUtil.setContext(JAXBContext.newInstance(classesXML.toArray(new Class[0])));

	}

	/**
	 * Chiamata al servizio
	 * 
	 * @param mail
	 * @throws Exception
	 */
	static WSServiceBean service(MimeMessage mime, MailWSOperationBean mail) throws Exception {
		if (mailoperation.containsKey(mail.getOperationname())) {
			if (tempdir == null) {
				throw new Exception("Directory di lavoro non configurata!");
			}
			if (!tempdir.exists()) {
				boolean create = tempdir.mkdirs();
				if (!create) {
					throw new Exception("Directory di lavoro " + tempdir.getAbsolutePath() + " non creata!");
				}
			}
			// Recupero e analizzo i valori della mail
			MailVerifier verifier = new MailVerifier();

			// Scrivo il file nella temporanea
			File mimefile = File.createTempFile("Mime", ".eml", tempdir);
			mime.writeTo(new FileOutputStream(mimefile));

			MessageInfos infos = verifier.verifyAnalize(mime, tempdir, mail.getIsSpam());

			// ATTENZIONE: devo commentare questo metodo non pi� esistente nella versione
			// di MessageInfos di mailserver 0.0.5
			// infos.setMime(mimefile);

			// Converto e setto i valori dei risultati di output delle elaborazioni
			LinkedHashMap<String, Object> opresult = new LinkedHashMap<String, Object>();
			if (mail.getResultoperation() != null) {
				Set<String> keys = mail.getResultoperation().keySet();
				for (String key : keys) {
					opresult.put(key, XmlUtil.xmlToObject(mail.getResultoperation().get(key)));
				}
			}
			infos.setOpresult(opresult);

			return mailoperation.get(mail.getOperationname()).serviceCall(infos, mail.getMailboxproperties());
		} else {
			throw new Exception("Operazione " + mail.getOperationname() + " non configurata!");
		}
	}
}