package it.eng.document.function.bean.editororganigramma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsFunzionigrammaBean {
	
	private List<ValuesTabellaFunzionigrammaBean> listTabellaOrganigramma = new ArrayList<>();
	
	private Map<String, String> mapCompetenze = new HashMap<>();
	

	public List<ValuesTabellaFunzionigrammaBean> getListTabellaOrganigramma() {
		return listTabellaOrganigramma;
	}

	public void setListTabellaOrganigramma(List<ValuesTabellaFunzionigrammaBean> listTabellaOrganigramma) {
		this.listTabellaOrganigramma = listTabellaOrganigramma;
	}

	public Map<String, String> getMapCompetenze() {
		return mapCompetenze;
	}

	public void setMapCompetenze(Map<String, String> mapCompetenze) {
		this.mapCompetenze = mapCompetenze;
	}
	
	
}
