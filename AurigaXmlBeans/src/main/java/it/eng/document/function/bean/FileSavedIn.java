package it.eng.document.function.bean;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.annotation.Attachment;

@XmlRootElement
public class FileSavedIn implements Serializable{
	
	@Attachment
	private File saved;
	
	private String errorInSave;
	
	public File getSaved() {
		return saved;
	}
	
	public void setSaved(File saved) {
		this.saved = saved;
	}

	public String getErrorInSave() {
		return errorInSave;
	}
	
	public void setErrorInSave(String errorInSave) {
		this.errorInSave = errorInSave;
	}

	
}
