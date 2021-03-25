package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaOutputElaboraAttiAmministrativi implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ContabiliaElaboraAttiAmministrativiResponse entry;
	
	public ContabiliaElaboraAttiAmministrativiResponse getEntry() {
		return entry;
	}
	
	public void setEntry(ContabiliaElaboraAttiAmministrativiResponse entry) {
		this.entry = entry;
	}
	
}
