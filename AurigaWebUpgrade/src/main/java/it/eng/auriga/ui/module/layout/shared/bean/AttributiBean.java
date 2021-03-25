package it.eng.auriga.ui.module.layout.shared.bean;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AttributiBean {

	private Map<String, LinkedHashMap<String, String>> attributiValueMap = new HashMap<String, LinkedHashMap<String,String>>();

	public void addAttributiValueMap(String nomeEntita, LinkedHashMap<String, String> valueMap){
		attributiValueMap.put(nomeEntita, valueMap);
	}
	
	public Map<String, LinkedHashMap<String, String>> getAttributiValueMap() {
		return attributiValueMap;
	}

	public void setAttributiValueMap(Map<String, LinkedHashMap<String, String>> attributiValueMap) {
		this.attributiValueMap = attributiValueMap;
	}
	
}
