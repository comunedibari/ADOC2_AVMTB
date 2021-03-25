package it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

import java.util.Map;

public class AnagraficaRubricaEmailXmlBeanDeserializationHelper extends DeserializationHelper{

	public AnagraficaRubricaEmailXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		AnagraficaRubricaEmailXmlBean currentBean = (AnagraficaRubricaEmailXmlBean)obj;
		
		if(currentBean.getTipoIndirizzo() != null){
			
			if(currentBean.getTipoIndirizzo().equals("0"))
				currentBean.setTipoIndirizzo("G");
			else 
				currentBean.setTipoIndirizzo("S");
		}
		
	}

}
