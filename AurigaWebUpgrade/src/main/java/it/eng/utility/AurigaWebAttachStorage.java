package it.eng.utility;

import java.io.File;

import it.eng.utility.module.config.StorageImplementation;

/**
 * helper per gestire gli attach tramite storageUri
 * 
 * @author mza
 *
 */
public class AurigaWebAttachStorage implements IAttachStorage {

	@Override
	public String storeTempFile(File file) throws Exception {
		return StorageImplementation.getStorage().store(file);
	}

	@Override
	public File extractTempFile(String storageUri) throws Exception {
		return StorageImplementation.getStorage().extractFile(storageUri);
	}
	
}

