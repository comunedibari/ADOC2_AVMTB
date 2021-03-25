package it.eng.aurigamailbusiness.database.utility;

public enum TParametersConfigKey {
	
	STRING_1("STRING#1"),
	ATTIVA_CIFRATURA_PWD("ATTIVA_CIFRATURA_PWD");
		
	private String name;
	
	private TParametersConfigKey(String name){
		this.name = name;
	}
	
	public String keyname(){
		return this.name;
	}
	
}