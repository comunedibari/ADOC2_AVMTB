package it.eng.auriga.ui.module.layout.server.common.datasource.bean;

import it.eng.document.XmlAttributiCustom;
import it.eng.jaxb.variabili.SezioneCache;

public class AttributiDinamiciXmlBean  {	
		
	@XmlAttributiCustom
	private SezioneCache sezioneCacheAttributiDinamici;
	
	public SezioneCache getSezioneCacheAttributiDinamici() {
		return sezioneCacheAttributiDinamici;
	}
	public void setSezioneCacheAttributiDinamici(SezioneCache sezioneCacheAttributiDinamici) {
		this.sezioneCacheAttributiDinamici = sezioneCacheAttributiDinamici;
	}
}
