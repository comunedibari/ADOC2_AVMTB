package it.eng.auriga.ui.module.layout.server.gestioneAtti.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

/**
 * 
 * @author DANCRIST
 *
 */

public class AttiCompletiXmlBeanDeserializationHelper extends DeserializationHelper {
	
	public AttiCompletiXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		AttiCompletiXmlBean bean = (AttiCompletiXmlBean) obj;
		
	}

}