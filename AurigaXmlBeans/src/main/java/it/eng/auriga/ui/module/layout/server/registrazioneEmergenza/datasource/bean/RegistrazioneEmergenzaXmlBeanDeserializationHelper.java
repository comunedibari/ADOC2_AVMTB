package it.eng.auriga.ui.module.layout.server.registrazioneEmergenza.datasource.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class RegistrazioneEmergenzaXmlBeanDeserializationHelper extends DeserializationHelper {
	

	public RegistrazioneEmergenzaXmlBeanDeserializationHelper(Map<String, String> remapConditions) {

		super(remapConditions);
		
	}
	
	@Override
	public void remapValues(Object obj, Riga objValues) throws Exception {

		RegistrazioneEmergenzaXmlBean bean = (RegistrazioneEmergenzaXmlBean) obj;

	}
	

}
