package it.eng.auriga.ui.module.layout.server.registriNumerazione.datasource.bean;

import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class RegistriNumerazioneXmlBeanDeserializationHelper extends DeserializationHelper {

	public RegistriNumerazioneXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		RegistriNumerazioneXmlBean bean = (RegistriNumerazioneXmlBean)obj; 
	}
}