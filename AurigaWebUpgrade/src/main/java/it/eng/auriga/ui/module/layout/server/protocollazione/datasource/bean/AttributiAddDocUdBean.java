package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.XmlAttributiCustom;
import it.eng.jaxb.variabili.SezioneCache;

/**
 * 
 * @author DANCRIST
 *
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AttributiAddDocUdBean implements Serializable {

	private static final long serialVersionUID = 8827729163041008691L;
	
	@XmlAttributiCustom
	private SezioneCache sezioneCacheAttributiDinamici;

	public SezioneCache getSezioneCacheAttributiDinamici() {
		return sezioneCacheAttributiDinamici;
	}

	public void setSezioneCacheAttributiDinamici(SezioneCache sezioneCacheAttributiDinamici) {
		this.sezioneCacheAttributiDinamici = sezioneCacheAttributiDinamici;
	}
}