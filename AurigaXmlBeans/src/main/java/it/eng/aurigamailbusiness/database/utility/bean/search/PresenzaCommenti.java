package it.eng.aurigamailbusiness.database.utility.bean.search;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum PresenzaCommenti implements Serializable{

	PRESENTI_COMMENTI_DESTINATI_A_ME("SUT"),
	PRESENTI_COMMENTI_NON_PUBBLICI("SNP"),
	PRESENTI_COMMENTI("S");
	
	private String valore;
	
	private PresenzaCommenti(String val){
		valore = val;
	}
	
	public String getValore(){
		return valore;
	}
}
