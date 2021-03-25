package it.eng.document.function.bean.acta;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActaInputGetDestinatariSmistamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String aooCode;
	private String structurCode;
	
	public String getAooCode() {
		return aooCode;
	}
	
	public void setAooCode(String aooCode) {
		this.aooCode = aooCode;
	}
	
	public String getStructurCode() {
		return structurCode;
	}

	public void setStructurCode(String structurCode) {
		this.structurCode = structurCode;
	}

}
