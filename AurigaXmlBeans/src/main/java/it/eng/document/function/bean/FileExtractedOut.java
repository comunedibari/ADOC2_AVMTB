package it.eng.document.function.bean;

import it.eng.core.annotation.Attachment;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileExtractedOut implements Serializable {

	@Attachment
	private File extracted;
	private String errorInExtract;

	public void setExtracted(File extracted) {
		this.extracted = extracted;
	}

	public File getExtracted() {
		return extracted;
	}

	public void setErrorInExtract(String errorInExtract) {
		this.errorInExtract = errorInExtract;
	}

	public String getErrorInExtract() {
		return errorInExtract;
	}

	
}
