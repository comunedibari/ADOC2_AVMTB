package it.eng.auriga.ui.module.layout.server.registriNumerazione.datasource.bean;

import java.util.List;

public class ListaRegistriNumerazioneBean 
{
	private String idUo;
	private List<RegistriNumerazioneBean> listaRegistriNumerazione;
	
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public List<RegistriNumerazioneBean> getListaRegistriNumerazione() {
		return listaRegistriNumerazione;
	}
	public void setListaRegistriNumerazione(List<RegistriNumerazioneBean> listaRegistriNumerazione) {
		this.listaRegistriNumerazione = listaRegistriNumerazione;
	}
	
		
}
