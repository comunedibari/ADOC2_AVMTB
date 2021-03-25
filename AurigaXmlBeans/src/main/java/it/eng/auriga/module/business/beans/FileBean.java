package it.eng.auriga.module.business.beans;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9178929876643277018L;
	
	private List<File> files;

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<File> getFiles() {
		return files;
	}

}
