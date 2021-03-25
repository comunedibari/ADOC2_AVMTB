package it.eng.auriga.ui.module.layout.server.campionamentoAtti.datasource.bean;

import java.util.List;

/**
 * 
 * @author matzanin
 *
 */

public class RegoleCampionamentoAttiBean extends GruppoAttiCampionamentoBean {
	
	private List<GruppoAttiCampionamentoBean> listaGruppiAttiCampionamento;
	
	public List<GruppoAttiCampionamentoBean> getListaGruppiAttiCampionamento() {
		return listaGruppiAttiCampionamento;
	}
	public void setListaGruppiAttiCampionamento(List<GruppoAttiCampionamentoBean> listaGruppiAttiCampionamento) {
		this.listaGruppiAttiCampionamento = listaGruppiAttiCampionamento;
	}
	
}