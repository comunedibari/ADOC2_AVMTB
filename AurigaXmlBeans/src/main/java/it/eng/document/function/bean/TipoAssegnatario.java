package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TipoAssegnatario implements Serializable{

	SCRIVANIA("SV"), UTENTE("UT"), UNITA_ORGANIZZATIVA("UO"), GRUPPO("G");
	
	private String dbValue;
	
	private TipoAssegnatario(String value){
		dbValue = value;
	}
	
	public String toString(){
		return dbValue;
	}
}
