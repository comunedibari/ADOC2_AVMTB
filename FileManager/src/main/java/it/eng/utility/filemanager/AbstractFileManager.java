package it.eng.utility.filemanager;

public abstract class AbstractFileManager implements FileManager {

	protected FileManagerConfig config;

	public AbstractFileManager(FileManagerConfig config) {
		this.config = config;
	}

	@Override
	public void setConfig(FileManagerConfig config) throws FileManagerException {
		this.config = config;
		refreshConfig();
	}

	protected abstract void refreshConfig() throws FileManagerException;
}
