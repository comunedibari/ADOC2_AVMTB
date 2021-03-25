package it.eng.auriga.repository2.jaxws.webservices.util;

import java.io.Serializable;
import javax.activation.DataHandler;
import it.eng.document.function.bean.RebuildedFile;
import java.util.List;


public class WSAttachBean implements Serializable {


	DataHandler[] attachments;
 	List<RebuildedFile> listRebuildedFile;
 	
	public DataHandler[] getAttachments() {
		return attachments;
	}
	public void setAttachments(DataHandler[] attachments) {
		this.attachments = attachments;
	}
	public List<RebuildedFile> getListRebuildedFile() {
		return listRebuildedFile;
	}
	public void setListRebuildedFile(List<RebuildedFile> listRebuildedFile) {
		this.listRebuildedFile = listRebuildedFile;
	}

}
