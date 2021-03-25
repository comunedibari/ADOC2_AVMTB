package it.eng.utility.storageutil.impl.filesystem;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import it.eng.utility.storageutil.exception.StorageException;

public class NumericFileSystemStoragePolicy implements FileSystemStoragePolicy {

	private int numZeroPadding;
	
	public NumericFileSystemStoragePolicy(int numZeroPadding) {
		this.numZeroPadding = numZeroPadding;
	}
	
	@Override
	public File getStorageFolder(String basePath, int nroMaxFiles) throws StorageException {
		File baseFolder = new File(basePath);
		
		int progr = 1;
		File folder = null;
		while (true) {
			folder = getDirectoryPath(baseFolder, progr);
			if (folder.exists() && folder.isDirectory()) {
				if (folder.listFiles().length < nroMaxFiles) {
					break;
				} else {
					progr++;
				}	
			} else {
				folder.mkdirs();
				break;
			}
		}
		return folder;
	}

	private File getDirectoryPath(File baseFolder, int progr) {
		return new File(baseFolder, StringUtils.leftPad(String.valueOf(progr), numZeroPadding, "0"));
	}
}

