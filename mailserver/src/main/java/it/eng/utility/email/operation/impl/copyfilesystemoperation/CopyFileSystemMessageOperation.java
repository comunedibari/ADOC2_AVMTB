package it.eng.utility.email.operation.impl.copyfilesystemoperation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.storage.StorageCenter;

@MessageOperation(description = "Effettua una copia del messaggio nella directory configurata", name = "CopyFileSystemMessageOperation")
public class CopyFileSystemMessageOperation extends AbstractMessageOperation<CopyFileSystemMessageBean> {

	private static Logger logger = LogManager.getLogger(CopyFileSystemMessageOperation.class);

	@ConfigOperation(title = "Directory di destinazione", name = "copyfilesystemmessageoperation.destinationdirectory", description = "Indica la directory di destinazione")
	private String destinationdirectory = null;

	@Override
	public CopyFileSystemMessageBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		CopyFileSystemMessageBean bean = new CopyFileSystemMessageBean();

		// recupero lo storage specifico dell'operazione
		configureStorage();

		if (storage == null) {
			throw new Exception("Storage per l'operation " + getName() + " non configurata!");
		}
		StorageCenter lStorageCenter = new StorageCenter();
		try {
			String uri = storage.storeStream(lStorageCenter.extract(( getMailreceiver() != null ? getMailreceiver().getConfigbean().getMailboxid() : 
				getMailreceiverPop3().getConfigbean().getMailboxid()), message.getUri()));
			bean.setUri(uri);
		} catch (Throwable e) {
			logger.warn("ErrorMessage: " + e.getLocalizedMessage(), e);
			// recupero retrocompatibilità
			// 17.03.2016
			// Diego: provo a recuperare il vecchio sistema di gestione, altrimenti lancio l'eccezione
			try {
				File destinationdirectoryfile = new File(this.destinationdirectory);
				if (!destinationdirectoryfile.exists()) {
					boolean create = destinationdirectoryfile.mkdirs();
					if (!create) {
						throw new Exception("Directory di destinazione " + destinationdirectoryfile.getAbsolutePath() + " non creata!");
					}
				}
				InputStream emlIS = lStorageCenter.extract((getMailreceiver() != null ? getMailreceiver().getConfigbean().getMailboxid() :
					getMailreceiverPop3().getConfigbean().getMailboxid()), message.getUri());
				File emlTmp = File.createTempFile("tmpEmailCopy", ".tmp");
				emlTmp.deleteOnExit();
				FileOutputStream output = null;
				try {
					output = new FileOutputStream(emlTmp);
					IOUtils.copy(emlIS, output);
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (Exception exc) {
							logger.warn("Errore nella chiusura dello stream", exc);
						}
					}
					if (emlIS != null) {
						try {
							emlIS.close();
						} catch (Exception exc) {
							logger.warn("Errore nella chiusura dello stream", exc);
						}
					}
				}
				// Copio il file nella directory configurata
				FileUtils.copyFileToDirectory(emlTmp, destinationdirectoryfile);
				// rinomino il file
				File eml = new File(destinationdirectoryfile + File.separator + emlTmp.getName());
				File rename = new File(destinationdirectoryfile + File.separator + message.getUri().substring(message.getUri().lastIndexOf("/")));
				eml.renameTo(rename);
				bean.setUri(rename.getAbsolutePath());
			} catch (Throwable e1) {
				logger.error("ErrorMessage: " + e1.getLocalizedMessage(), e1);
				throw new Exception(e1);
			}
		}

		bean.setCopyok(true);
		return bean;
	}

	public String getDestinationdirectory() {
		return destinationdirectory;
	}

	public void setDestinationdirectory(String destinationdirectory) {
		this.destinationdirectory = destinationdirectory;
	}

}
