package it.eng.auriga.ui.module.layout.server.sub_profili.datasource.bean;

import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class SubProfiliXmlBeanDeserializationHelper extends DeserializationHelper {

	public SubProfiliXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		SubProfiliXmlBean bean = (SubProfiliXmlBean)obj; 
	}
}