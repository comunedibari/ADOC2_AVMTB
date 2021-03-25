package it.eng.utility.email.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import it.eng.aurigamailbusiness.database.mail.MailboxConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxConfigId;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.reader.config.MailBoxConfigKey;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;
import it.eng.utility.storageutil.impl.filesystem.util.EnvironmentVariableConfigManager;

/**
 * classe per l'archiviazione delle email
 * 
 * @author jravagnan
 *
 */
public class StorageCenterArchive {

	private static Logger mLogger = LogManager.getLogger(StorageCenter.class);
	private static final String PROGRAM_PREFIX = "MailArchive.";

	/**
	 * Restituisce lo storage di base da utilizzare per ogni singola mailBox. Il nome dell'utilizzatore è ottenuto concatenando "MailArchive." all'id della
	 * mailBox
	 * 
	 * @param idMailBox
	 *            L'id che individua ogni singola mailBox
	 * @return
	 */
	public StorageService getGlobalStorage(final String idMailBox) {
		return StorageServiceImpl.newInstance(new GenericStorageInfo() {

			public String getUtilizzatoreStorageId() {
				return PROGRAM_PREFIX + idMailBox;
			}
		});
	}

	/**
	 * Restituisce lo storage da utilizzare per la data operazione di ogni singola mailBox. Il nome dell'utilizzatore è ottenuto concatenando "MailArchive.",
	 * l'id della mailBox, ".", nome dell'operazione
	 * 
	 * @param idMailBox
	 *            L'id che individua ogni singola mailBox
	 * @param operationName
	 *            Il nome dell'operazione
	 * @return
	 */
	public StorageService getOperationStorage(final String idMailBox, final String operationName) {
		return StorageServiceImpl.newInstance(new GenericStorageInfo() {

			public String getUtilizzatoreStorageId() {
				return PROGRAM_PREFIX + idMailBox + "." + operationName;
			}
		});
	}

	public void delete(String idMailBox, String urlFile) throws Throwable {
		if (urlFile.startsWith("[")) {
			getGlobalStorage(idMailBox).delete(urlFile);
		} else {
			Session session = null;
			try {
				session = SubjectInitializer.getSession(idMailBox);
				MailboxConfigId config = new MailboxConfigId(MailBoxConfigKey.MAILBOX_DIRECTORY.keyname(), idMailBox);
				MailboxConfig configmailbox = (MailboxConfig) session.get(MailboxConfig.class, config);
				String basedirectory = EnvironmentVariableConfigManager.replaceEnvironmentVariable(configmailbox.getConfigValue());
				if (urlFile != null && basedirectory != null) {
					File file = new File(basedirectory, urlFile);
					boolean delete = file.delete();
					if (!delete) {
						mLogger.warn("Messaggio " + file.getAbsolutePath() + " non cancellato!");
					}
				}
			} catch (Throwable e) {
				throw e;
			} finally {
				if (session != null) {
					session.close();
					session = null;
				}
			}
		}
	}

	public InputStream extract(String idMailBox, String urlFile) throws Throwable {
		if (urlFile.startsWith("[")) {
			return getGlobalStorage(idMailBox).extract(urlFile);
		} else {
			Session session = null;
			try {
				session = SubjectInitializer.getSession(idMailBox);
				MailboxConfigId config = new MailboxConfigId(MailBoxConfigKey.MAILBOX_DIRECTORY.keyname(), idMailBox);
				MailboxConfig configmailbox = (MailboxConfig) session.get(MailboxConfig.class, config);
				String basedirectory = EnvironmentVariableConfigManager.replaceEnvironmentVariable(configmailbox.getConfigValue());
				if (urlFile != null && basedirectory != null) {
					File file = new File(basedirectory, urlFile);
					if (!file.exists()){
						return null;
					}
					return new FileInputStream(file);
				}
				return null;
			} catch (Throwable e) {
				throw e;
			} finally {
				if (session != null) {
					session.close();
					session = null;
				}
			}
		}
	}

}
