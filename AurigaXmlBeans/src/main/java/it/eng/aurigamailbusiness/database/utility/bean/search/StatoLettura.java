package it.eng.aurigamailbusiness.database.utility.bean.search;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum StatoLettura implements Serializable{

	LETTA_DA_QUALCUNO(1),
	LETTA_DA_ME(2),
	NON_LETTA_DA_ME(0),
	NON_LETTA_DA_ALCUNO(-1);
	
	private int valore;
	
	private StatoLettura(int pIntValore){
		valore = pIntValore;
	}
	
	public int getValore(){
		return valore;
	}
}
