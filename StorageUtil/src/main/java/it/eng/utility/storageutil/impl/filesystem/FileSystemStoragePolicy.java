package it.eng.utility.storageutil.impl.filesystem;

import it.eng.utility.storageutil.exception.StorageException;

import java.io.File;

public interface FileSystemStoragePolicy {

	public File getStorageFolder(String basePath, int nroMaxFiles) throws StorageException;
	
}
