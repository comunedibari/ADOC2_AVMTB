package it.eng.document.function.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RebuildedFileStored extends RebuildedFile {

	private String uriStorage;
	
	public String getUriStorage() {
		return uriStorage;
	}

	public void setUriStorage(String uriStorage) {
		this.uriStorage = uriStorage;
	}

	
}