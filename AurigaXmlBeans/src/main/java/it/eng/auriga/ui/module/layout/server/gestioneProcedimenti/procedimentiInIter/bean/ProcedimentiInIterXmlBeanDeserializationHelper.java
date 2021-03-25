package it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

/**
 * 
 * @author DANCRIST
 *
 */

public class ProcedimentiInIterXmlBeanDeserializationHelper extends DeserializationHelper {
	
	public ProcedimentiInIterXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		ProcedimentiInIterXmlBean bean = (ProcedimentiInIterXmlBean) obj;
		
	}

}