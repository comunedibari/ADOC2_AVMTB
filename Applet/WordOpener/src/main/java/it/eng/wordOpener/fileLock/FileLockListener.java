package it.eng.wordOpener.fileLock;




public interface FileLockListener {

	public void fileUnlocked();
	
	public void error(Exception e);
}
