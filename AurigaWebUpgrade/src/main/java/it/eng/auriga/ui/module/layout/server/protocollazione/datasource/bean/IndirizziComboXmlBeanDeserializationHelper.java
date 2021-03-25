package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;


import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;
import java.util.Map;

public class IndirizziComboXmlBeanDeserializationHelper extends DeserializationHelper{

	public IndirizziComboXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		LoadComboIndirizzoSoggettoBean currentBean = (LoadComboIndirizzoSoggettoBean)obj;
		
		if (currentBean.getIndirizzoDisplay() == null)			
		    currentBean.setIndirizzoDisplay("");
	}
}
