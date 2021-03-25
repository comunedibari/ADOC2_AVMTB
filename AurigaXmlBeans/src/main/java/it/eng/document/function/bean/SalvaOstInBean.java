package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SalvaOstInBean implements Serializable {

	private String idProcess;
	private String idEventType;
	
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	public String getIdEventType() {
		return idEventType;
	}
	public void setIdEventType(String idEventType) {
		this.idEventType = idEventType;
	}
			
}
