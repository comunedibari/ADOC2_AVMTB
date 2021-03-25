package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.PeriziaComboXmlBean;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;
import java.util.Map;

public class PeriziaComboXmlBeanDeserializationHelper extends DeserializationHelper{

	public PeriziaComboXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		PeriziaComboXmlBean currentBean = (PeriziaComboXmlBean)obj;
		currentBean.setIdCodice(currentBean.getCodice());
	}
}
