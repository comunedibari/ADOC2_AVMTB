package it.eng.auriga.module.business.formati.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FormatiServiceBean implements Serializable {   

	private static final long serialVersionUID = 6447783228571639511L;
	private String mimetype;
	private boolean convertibile;
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public boolean isConvertibile() {
		return convertibile;
	}
	public void setConvertibile(boolean convertibile) {
		this.convertibile = convertibile;
	}
	
	
}
