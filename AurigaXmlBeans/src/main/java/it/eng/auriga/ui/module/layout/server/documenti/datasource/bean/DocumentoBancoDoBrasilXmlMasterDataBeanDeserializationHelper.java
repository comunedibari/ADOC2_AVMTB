package it.eng.auriga.ui.module.layout.server.documenti.datasource.bean;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

import java.util.Map;

public class DocumentoBancoDoBrasilXmlMasterDataBeanDeserializationHelper extends DeserializationHelper{

	public DocumentoBancoDoBrasilXmlMasterDataBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		DocumentoBancoDoBrasilXmlMasterDataBean currentBean = (DocumentoBancoDoBrasilXmlMasterDataBean)obj;	
		
	}

}
