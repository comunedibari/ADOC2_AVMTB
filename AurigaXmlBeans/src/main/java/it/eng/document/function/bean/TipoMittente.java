package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public enum TipoMittente implements Serializable, DBenum {

	PERSONA_FISICA("1"), PERSONA_GIURIDICA("0");
	
	private String value;
	
	private TipoMittente(String value){
		this.value = value;
	}
	
	public String toString(){
		return value;
	}

	@Override
	public String getDbValue() {
		// TODO Auto-generated method stub
		return value;
	}

	
}
