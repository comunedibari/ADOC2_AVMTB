package it.eng.auriga.ui.module.layout.server.titolario;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

/**
 * 
 * @author DANCRIST
 *
 */

public class ClassificaXmlBeanDeserializationHelper extends DeserializationHelper {
	
	public ClassificaXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		ClassificaXmlBean bean = (ClassificaXmlBean) obj;
		
	}

}