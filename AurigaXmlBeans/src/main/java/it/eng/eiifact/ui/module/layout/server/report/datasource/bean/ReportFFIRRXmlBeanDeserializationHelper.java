package it.eng.eiifact.ui.module.layout.server.report.datasource.bean;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;
import java.util.Map;

public class ReportFFIRRXmlBeanDeserializationHelper extends DeserializationHelper{

	public ReportFFIRRXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		ReportFFIRRXmlBean currentBean = (ReportFFIRRXmlBean)obj;	
	}
}
