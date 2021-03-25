package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaRicercaImpegnoResponse extends ContabiliaBaseRicercaResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<ContabiliaImpegno> impegni;
	
	public List<ContabiliaImpegno> getImpegni() {
		return impegni;
	}
	
	public void setImpegni(List<ContabiliaImpegno> impegni) {
		this.impegni = impegni;
	}
	
	@Override
	public String toString() {
		return "ContabiliaRicercaImpegnoResponse [impegni=" + impegni + "]";
	}
	
}
