package it.eng.auriga.ui.module.layout.server.documenti.datasource.bean;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

import java.util.Map;

public class DocumentoBancoDoBrasilXmlDetailClientiBeanDeserializationHelper extends DeserializationHelper{

	public DocumentoBancoDoBrasilXmlDetailClientiBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		DocumentoBancoDoBrasilXmlDetailClientiBean currentBean = (DocumentoBancoDoBrasilXmlDetailClientiBean)obj;	
		
	}

}
