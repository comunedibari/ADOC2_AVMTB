package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.jaxb.variabili.Lista.Riga;

public class PostaElettronicaBeanDeserializationHelper  extends PostaElettronicaXmlBeanDeserializationHelper {

	public PostaElettronicaBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga objValues) throws Exception {
		
		super.remapValues(obj, objValues);
		
		PostaElettronicaBean bean = (PostaElettronicaBean)obj;
		
		AzioneDaFareBean azioneDaFareBean = new AzioneDaFareBean();
		azioneDaFareBean.setAzioneDaFare(bean.getAzioneDaFare());
		azioneDaFareBean.setCodAzioneDaFare(bean.getCodAzioneDaFare());
		azioneDaFareBean.setDettaglioAzioneDaFare(bean.getDettaglioAzioneDaFare());
		bean.setAzioneDaFareBean(azioneDaFareBean);
		
		if(StringUtils.isNotBlank(bean.getListEstremiRegProtAssociati()) && 
				StringUtils.isNotBlank(bean.getListIdUdProtAssociati())) {							
			String [] splitEstremiRegProtAssociati = String.valueOf(bean.getListEstremiRegProtAssociati()).split(";"); //colonna 46
			String [] splitIdUdProtAssociati = String.valueOf(bean.getListIdUdProtAssociati()).split(";"); //colonna 47
			Map<String, String> estremiRegProtAssociati = new HashMap<String, String>();						
			for(int k=0; k<splitIdUdProtAssociati.length;k++) {
				estremiRegProtAssociati.put(splitIdUdProtAssociati[k], splitEstremiRegProtAssociati[k]);
			}
			bean.setEstremiRegProtAssociati(estremiRegProtAssociati);								
			if(bean.getFlgStatoProt() != null && !"".equals(bean.getFlgStatoProt())) {										
				bean.setEstremiProtCollegati(bean.getListEstremiRegProtAssociati());													
			}

		}
		
		if(StringUtils.isNotBlank(bean.getListEstremiRegProtAssociati()) 
				&& StringUtils.isNotBlank(bean.getListIdUdProtAssociati())) {							
			String [] splitEstremiRegProtAssociati = String.valueOf(bean.getListEstremiRegProtAssociati()).split(";");
			String [] splitIdUdProtAssociati = String.valueOf(bean.getListIdUdProtAssociati()).split(";");
			Map<String, String> estremiRegProtAssociati = new HashMap<String, String>();													
			for(int k=0; k<splitIdUdProtAssociati.length;k++) {
				estremiRegProtAssociati.put(splitIdUdProtAssociati[k], splitEstremiRegProtAssociati[k]);
			}
			bean.setEstremiRegProtAssociati(estremiRegProtAssociati);
			bean.setEstremiDocInviato(bean.getListEstremiRegProtAssociati());														
		}
		
	}
	
}
