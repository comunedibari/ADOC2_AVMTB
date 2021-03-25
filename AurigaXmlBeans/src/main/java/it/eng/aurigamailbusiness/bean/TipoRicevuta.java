package it.eng.aurigamailbusiness.bean;

public enum TipoRicevuta {

	COMPLETA("completa"),
	SINTETICA("sintetica"),
	BREVE("breve");
	
	
	private String value;
	
	private TipoRicevuta(String value) {
		this.value = value;
	}
	
	public static TipoRicevuta valueOfValue(String name){
		for(TipoRicevuta stato:TipoRicevuta.values()){
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
