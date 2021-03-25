package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import org.apache.commons.lang3.StringUtils;

public class AltraViaProtBean extends IndirizzoSoggettoProtBean {

	
	/**
	 * 
	 * Viene verificato che i campi di IndirizzoSoggettoProtBean non siano tutti NULL dopo
	 * la serializzazione in ArchivioDatasource
	 */
	public Boolean isNull(){
//		return  getStato() == null && getNomeStato() == null && getFlgFuoriComune() == null && getCodToponimo() == null &&
//				getTipoToponimo() == null && getToponimo() == null && getIndirizzo() == null && getCivico() == null && 
//				getInterno() == null && getComune() == null && getNomeComune() == null && getCitta() == null && 
//				getProvincia() == null && getFrazione() == null && getCap() == null && getZona() == null
//				&& getComplementoIndirizzo() == null && getAppendici() == null;
		
		return StringUtils.isBlank(getToponimo()) && StringUtils.isBlank(getIndirizzo());
	}
	
}
