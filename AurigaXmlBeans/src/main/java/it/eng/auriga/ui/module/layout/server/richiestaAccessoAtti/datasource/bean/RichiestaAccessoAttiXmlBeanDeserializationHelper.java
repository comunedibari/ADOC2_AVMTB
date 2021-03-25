package it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class RichiestaAccessoAttiXmlBeanDeserializationHelper extends DeserializationHelper {

	protected String idNode;

	public RichiestaAccessoAttiXmlBeanDeserializationHelper(Map<String, String> remapConditions) {

		super(remapConditions);
		
		idNode = (String) remapConditions.get("idNode");
		
	}
	
	@Override
	public void remapValues(Object obj, Riga objValues) throws Exception {

		RichiestaAccessoAttiXmlBean bean = (RichiestaAccessoAttiXmlBean) obj;

	}
}