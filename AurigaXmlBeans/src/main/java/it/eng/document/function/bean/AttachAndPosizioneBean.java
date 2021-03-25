package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttachAndPosizioneBean implements Serializable{

	private Integer posizione;
	private String idAttachment;
	public Integer getPosizione() {
		return posizione;
	}
	public void setPosizione(Integer posizione) {
		this.posizione = posizione;
	}
	public String getIdAttachment() {
		return idAttachment;
	}
	public void setIdAttachment(String idAttachment) {
		this.idAttachment = idAttachment;
	}
}
