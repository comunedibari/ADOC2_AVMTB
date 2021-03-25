package it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;

public class RichiestaAccessoAttiBeanDeserializationHelper extends RichiestaAccessoAttiXmlBeanDeserializationHelper {

	public RichiestaAccessoAttiBeanDeserializationHelper(Map<String, String> remapConditions) {

		super(remapConditions);
		
	}
	
	@Override
	public void remapValues(Object obj, Riga objValues) throws Exception {

		super.remapValues(obj, objValues);
		
		RichiestaAccessoAttiBean bean = (RichiestaAccessoAttiBean) obj;

		bean.setPrelievoEffettuato(bean.getStatoPrelievo() != null && "effettuato".equals(bean.getStatoPrelievo()));
	}
	
}
