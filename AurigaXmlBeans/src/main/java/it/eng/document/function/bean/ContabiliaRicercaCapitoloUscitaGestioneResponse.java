package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaRicercaCapitoloUscitaGestioneResponse extends ContabiliaBaseResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<ContabiliaCapitoloUscitaGestione> capitoliUscitaGestione;
	
	public List<ContabiliaCapitoloUscitaGestione> getCapitoliUscitaGestione() {
		return capitoliUscitaGestione;
	}
	
	public void setCapitoliUscitaGestione(List<ContabiliaCapitoloUscitaGestione> capitoliUscitaGestione) {
		this.capitoliUscitaGestione = capitoliUscitaGestione;
	}
	
	@Override
	public String toString() {
		return "ContabiliaRicercaCapitoloUscitaGestioneResponse [capitoliUscitaGestione=" + capitoliUscitaGestione
				+ "]";
	}
	
}