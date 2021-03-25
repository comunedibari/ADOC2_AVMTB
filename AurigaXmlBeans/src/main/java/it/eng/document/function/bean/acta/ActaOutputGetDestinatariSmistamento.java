package it.eng.document.function.bean.acta;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ActaOutputGetDestinatariSmistamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private ActaEsitoChiamata esitoChiamata;
	private List<ActaNodoSmistamento> nodoSmistamento;

	public List<ActaNodoSmistamento> getNodoSmistamento() {
		return nodoSmistamento;
	}

	public void setNodoSmistamento(List<ActaNodoSmistamento> nodoSmistamento) {
		this.nodoSmistamento = nodoSmistamento;
	}

	public ActaEsitoChiamata getEsitoChiamata() {
		return esitoChiamata;
	}

	public void setEsitoChiamata(ActaEsitoChiamata esitoChiamata) {
		this.esitoChiamata = esitoChiamata;
	}

}
