package it.eng.utility.email.operation.impl.archiveoperation.utils;

public enum TipoAccount {

	PEC("C"),
	ORDINARIA("O");
	
	
	private String value;
	
	private TipoAccount(String value) {
		this.value = value;
	}
	
	public static TipoAccount valueOfValue(String name){
		for(TipoAccount stato:TipoAccount.values()){
			if(stato.value.equals(name)){
				return stato;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
	
}
