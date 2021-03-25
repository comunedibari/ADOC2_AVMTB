package it.eng.utility.email.operation.impl.archiveoperation.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.FileUtil;
import it.eng.utility.email.operation.impl.archiveoperation.AbstractDirector;
import it.eng.utility.email.reader.ExtendImapMailReceiver;
import it.eng.utility.email.storage.StorageCenter;
import it.eng.utility.email.storage.StorageCenterArchive;
import it.eng.utility.storageutil.StorageService;

/**
 * Direttore di salvataggio della email
 * 
 * @author jravagnan
 * 
 */
public class StorageDirector extends AbstractDirector {

	StorageCenterArchive storageCenterArchive = new StorageCenterArchive();

	StorageCenter storageCenter = new StorageCenter();

	Logger log = LogManager.getLogger(StorageDirector.class);

	/**
	 * ritorna l'uri del file salvato
	 */
	@Override
	public String process(MessageInfos message, String messageId) throws Exception {
		log.debug("invocato lo storagedirector per il salvataggio della email per il message con id: " + message.getHeaderinfo().getMessageid());
		StorageService service = storageCenter.getGlobalStorage(messageId);
		log.debug("il path da cui leggo è il seguente(mail scaricate): " + message.getUri());
		StorageService serviceArchive = storageCenterArchive.getGlobalStorage(messageId);
		InputStream is = service.extract(message.getUri());
		String uri = serviceArchive.storeStream(is);
		log.debug("NEW: salvo il file archiviato nel seguente URI(mail archiviate): " + uri);
		return uri;
	}

	public void delete(String uri, String idCasella) throws Exception {
		StorageService service = storageCenter.getGlobalStorage(idCasella);
		service.delete(uri);
	}

	/**
	 * ottiene il file dall'uri
	 * 
	 * @param uri
	 * @param mailboxId
	 * @return
	 * @throws Exception
	 */
	public File retrieveFile(String uri, String mailboxId) throws Exception {
		InputStream is = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			file = File.createTempFile("temp", ".eml");
			fos = new FileOutputStream(file);
			StorageService service = storageCenter.getGlobalStorage(mailboxId);
			String configurazioni = service.getConfigurazioniStorage();
			log.debug("configurazione dello storage da cui vado a ricavare l'uri: " + configurazioni);
			is = service.extract(uri);
			byte[] buffer = new byte[16384];
			int byteCount = 0;
			while ((byteCount = is.read(buffer)) > 0) {
				fos.write(buffer, 0, byteCount);
			}
		} catch (Exception ex) {
			// chiudo tutto e poi cancello l'eventuale file
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception ex1) {
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception ex1) {
			}
			try {
				if (file != null && file.exists()) {
					FileUtil.deleteFile(file);
				}
			} catch (Exception ex1) {
			}
			throw ex;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception ex) {
				log.warn("Eccezione nella chiusura dello stream", ex);
			}
		}
		return file;
	}

}