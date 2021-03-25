package it.eng.eiifact.ui.module.layout.server.documenti.datasource.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class DocumentoMonclerXmlBeanValuesRemapperHelper extends DeserializationHelper {

	public DocumentoMonclerXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {

		DocumentoMonclerXmlBean currentBean = (DocumentoMonclerXmlBean)obj;
		
		// ottavio : mostro sempre la lente
		currentBean.setAbilViewFilePrimario("1");
	}
}