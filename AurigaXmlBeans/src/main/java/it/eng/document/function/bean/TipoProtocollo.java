package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TipoProtocollo implements Serializable, DBenum{

	PROTOCOLLO_GENERALE("PG"), PROTOCOLLO_INTERNO("I"), PROTOCOLLO_PARTICOLARE("PP"), REPERTORIO("R");
	
	private String dbValue;
	
	private TipoProtocollo(String value){
		dbValue = value;
	}
	
	public String toString(){
		return dbValue;
	}

	@Override
	public String getDbValue() {
		// TODO Auto-generated method stub
		return dbValue;
	}
}
