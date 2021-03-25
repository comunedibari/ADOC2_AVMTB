package it.eng.document.configuration;

import it.eng.utility.filemanager.FileManagerConfig;

/**
 * Permette di definire valori di configurazione necessari a invocare il servizio WSOperazioniAurigaLottiDoc
 * 
 * @author Mattia Zanetti
 *
 */

public class OperazioniAurigaLottiDocConfigBean {

	private String defaultSchema;
	private FileManagerConfig fileManagerConfig;

	public String getDefaultSchema() {
		return defaultSchema;
	}

	public void setDefaultSchema(String defaultSchema) {
		this.defaultSchema = defaultSchema;
	}

	public FileManagerConfig getFileManagerConfig() {
		return fileManagerConfig;
	}

	public void setFileManagerConfig(FileManagerConfig fileManagerConfig) {
		this.fileManagerConfig = fileManagerConfig;
	}

}
