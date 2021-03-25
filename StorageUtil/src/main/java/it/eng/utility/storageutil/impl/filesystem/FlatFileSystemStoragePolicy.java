package it.eng.utility.storageutil.impl.filesystem;

import java.io.File;

import it.eng.utility.storageutil.exception.StorageException;

public class FlatFileSystemStoragePolicy implements FileSystemStoragePolicy {

	@Override
	public File getStorageFolder(String basePath, int nroMaxFiles) throws StorageException {
		File folder = new File(basePath);
		folder.mkdirs();
		return folder;
	}

}

