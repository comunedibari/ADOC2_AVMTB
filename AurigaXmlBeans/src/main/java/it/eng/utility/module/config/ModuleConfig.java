package it.eng.utility.module.config;

/**
 * Bean di configurazione che contiene tutti i parametri globali del modulo
 * @author upescato
 *
 */

public class ModuleConfig {
	
	/**
	 * Identificativo del DB storage in cui verranno memorizzati i file
	 */
	private String idDbStorage;

	public String getIdDbStorage() {
		return idDbStorage;
	}

	public void setIdDbStorage(String idDbStorage) {
		this.idDbStorage = idDbStorage;
	}

}
