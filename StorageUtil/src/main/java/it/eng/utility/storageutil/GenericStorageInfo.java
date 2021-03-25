package it.eng.utility.storageutil;

/**
 * Interfaccia utilizzata per creare una nuova istanza dello StorageService 
 * 
 * @author Mattia Zanin
 *
 */
public interface GenericStorageInfo {
	
	/**
     * Metodo da implementare che ritorna la stringa relativa all'id dell'utilizzatore dello storage
     *
     */
	public String getUtilizzatoreStorageId();
	
}
