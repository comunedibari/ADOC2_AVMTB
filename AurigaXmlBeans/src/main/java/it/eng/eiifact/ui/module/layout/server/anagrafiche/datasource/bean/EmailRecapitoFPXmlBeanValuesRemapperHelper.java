package it.eng.eiifact.ui.module.layout.server.anagrafiche.datasource.bean;

import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class EmailRecapitoFPXmlBeanValuesRemapperHelper extends DeserializationHelper {

	public EmailRecapitoFPXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		EmailRecapitoFPXmlBean currentBean = (EmailRecapitoFPXmlBean)obj;		
		currentBean.setDenominazione(riga.getColonna().get(4).getContent());
	}
}