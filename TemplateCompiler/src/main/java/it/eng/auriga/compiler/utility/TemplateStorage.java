package it.eng.auriga.compiler.utility;

import java.io.File;
import java.io.InputStream;

/**
 * @author Federico Cacco
 *  
 * Interfaccia metodi di utilità per l'accesso ai file durante la creazione del docuento a partire dal template. 
 * 
 * Viene implementata in AurigaWeb da TemplateStorageAurigaWebImpl e AurigaBusiness da TemplateStorageAurigaBusinessImpl
 *
 */
public interface TemplateStorage {
	
	public File extractFile (String storageTemplateUri) throws Exception;
	
	public InputStream extract (String storageTemplateUri) throws Exception;
	
	public File getRealFile (String uri) throws Exception;
	
	public String store(File fileToStore, String... params) throws Exception;
	
}


