package it.eng.auriga.ui.module.layout.server.titolario;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

/**
 * 
 * @author DANCRIST
 *
 */

public class TitolarioXmlBeanDeserializationHelper extends DeserializationHelper {
	
	public TitolarioXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		TitolarioXmlBean bean = (TitolarioXmlBean) obj;
		
	}

}