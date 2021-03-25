package it.eng.eiifact.ui.module.layout.server.documenti.datasource.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class DocumentoXmlBeanValuesRemapperHelper extends DeserializationHelper {

	public DocumentoXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {

		DocumentoXmlBean currentBean = (DocumentoXmlBean)obj;
		
		String [] splits = currentBean.getSiglaFattura().split("#");
		currentBean.setSiglaFattura(splits[0].toString());	
		currentBean.setNumeroFattura(splits[1].toString());
		
		
		// ottavio : mostro sempre la lente
		currentBean.setAbilViewFilePrimario("1");
	}
}
