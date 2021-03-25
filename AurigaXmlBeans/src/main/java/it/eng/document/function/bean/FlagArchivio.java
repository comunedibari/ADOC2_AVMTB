package it.eng.document.function.bean;

import java.io.Serializable;

public enum FlagArchivio implements Serializable, DBenum{
	ARCHIVIO("C"), ARCHIVIO_DEPOSITO("D"),  ARCHIVIO_STORICO("S"),  NOT_SETTED("");

	private String realValue;
	
	private FlagArchivio(String value){
		realValue = value;
	}
	@Override
	public String getDbValue() {
		return realValue+"";
	}

}
