package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DistribuzioneBean implements Serializable{
	
	@NumeroColonna(numero = "1")
	private String idLista;
	@NumeroColonna(numero = "4")
	private Flag perConoscenza;
	public String getIdLista() {
		return idLista;
	}
	public void setIdLista(String idLista) {
		this.idLista = idLista;
	}
	public Flag getPerConoscenza() {
		return perConoscenza;
	}
	public void setPerConoscenza(Flag perConoscenza) {
		this.perConoscenza = perConoscenza;
	}
	
	
}
