package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.utility.ui.html.HtmlNormalizeUtil;
import it.eng.xml.DeserializationHelper;

public class PostaElettronicaXmlBeanDeserializationHelper extends DeserializationHelper {

	public PostaElettronicaXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		PostaElettronicaXmlBean bean = (PostaElettronicaXmlBean)obj; 
		
		bean.setCorpo(HtmlNormalizeUtil.getPlainXmlWithCarriageReturn(bean.getCorpo()));
		
	}
}
