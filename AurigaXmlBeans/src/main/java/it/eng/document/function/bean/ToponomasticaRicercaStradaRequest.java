package it.eng.document.function.bean;

import java.io.Serializable;

public class ToponomasticaRicercaStradaRequest extends ToponomasticaRicercaRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nomeVia;
	
	public String getNomeVia() {
		return nomeVia;
	}
	
	public void setNomeVia(String nomeVia) {
		this.nomeVia = nomeVia;
	}
	
}
