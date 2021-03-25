package it.eng.testrest;

import java.util.List;

public class DeleteMessageRestBean extends GenericOperationBean {

	private List<String> listIdMessageDeletedFromFolder; // lista id messaggi rimossi dalla folder
	private List<String> listIdMessageBackuped; // copiati nella folder di backup
	private Integer countMessageFolderInDb; // messaggi già scaricati in MAILBOX_MESSAGE
	private Integer countNewMessageFolder; // nuovi messaggi presenti nella folder e da scaricare
	private Integer countMessageFolder; // totale dei messaggi nella folder

	public List<String> getListIdMessageDeletedFromFolder() {
		return listIdMessageDeletedFromFolder;
	}

	public void setListIdMessageDeletedFromFolder(List<String> listIdMessageDeletedFromFolder) {
		this.listIdMessageDeletedFromFolder = listIdMessageDeletedFromFolder;
	}

	public Integer getCountMessageFolderInDb() {
		return countMessageFolderInDb;
	}

	public void setCountMessageFolderInDb(Integer countMessageFolderInDb) {
		this.countMessageFolderInDb = countMessageFolderInDb;
	}

	public Integer getCountNewMessageFolder() {
		return countNewMessageFolder;
	}

	public void setCountNewMessageFolder(Integer countNewMessageFolder) {
		this.countNewMessageFolder = countNewMessageFolder;
	}

	public Integer getCountMessageFolder() {
		return countMessageFolder;
	}

	public void setCountMessageFolder(Integer countMessageFolder) {
		this.countMessageFolder = countMessageFolder;
	}

	public List<String> getListIdMessageBackuped() {
		return listIdMessageBackuped;
	}

	public void setListIdMessageBackuped(List<String> listIdMessageBackuped) {
		this.listIdMessageBackuped = listIdMessageBackuped;
	}

}
