package it.eng.auriga.ui.module.layout.server.documenti.datasource.bean;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

import java.util.Map;

public class DocumentoNespressoXmlBeanDeserializationHelper extends DeserializationHelper{

	public DocumentoNespressoXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		DocumentoNespressoXmlBean currentBean = (DocumentoNespressoXmlBean)obj;	
		
		String [] splits = currentBean.getSiglaFattura().split("#");
		currentBean.setSiglaFattura(splits[0].toString());	
		currentBean.setNumeroFattura(splits[1].toString());

		
	}

}
