package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean;

import java.util.List;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class VisualizzaEmlBean {

	private String idEmail;
	private String uri;
	private List<DettaglioPostaElettronicaAllegato> listaAllegati;
	private int nroAllegato;
	
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public List<DettaglioPostaElettronicaAllegato> getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(
			List<DettaglioPostaElettronicaAllegato> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	public int getNroAllegato() {
		return nroAllegato;
	}
	public void setNroAllegato(int nroAllegato) {
		this.nroAllegato = nroAllegato;
	}	
}