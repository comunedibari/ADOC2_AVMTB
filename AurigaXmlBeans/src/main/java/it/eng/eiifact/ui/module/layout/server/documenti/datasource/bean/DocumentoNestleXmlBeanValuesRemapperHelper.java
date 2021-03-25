package it.eng.eiifact.ui.module.layout.server.documenti.datasource.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class DocumentoNestleXmlBeanValuesRemapperHelper extends DeserializationHelper {

	public DocumentoNestleXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {

		DocumentoNestleXmlBean currentBean = (DocumentoNestleXmlBean)obj;
		
		String [] splits = currentBean.getSiglaDocumento().split("#");
		currentBean.setSiglaDocumento(splits[0].toString());	
		currentBean.setNumeroDocumento(splits[1].toString());
		
		// ottavio : mostro sempre la lente
		currentBean.setAbilViewFilePrimario("1");
	}
}