package it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean;

import java.util.Map;

/**
 * Espone la configurazione dei modelli da utilizzare durante le istanze di processo relative agli att delle determine
 * @author massimo malvestio
 *
 */
public class AttiModelliCustomBean {

	private Map<String,String> configMap;

	public Map<String, String> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}
	
	
	
}
