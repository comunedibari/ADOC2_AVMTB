package it.eng.aurigamailbusiness.structure;

public enum XTipoRicevuta {

    COMPLETA("completa"),
	BREVE("breve"),
	SINTETICA("sintetica");

    private String value;
    
    private XTipoRicevuta(String value) {
		this.value = value;
	}
        
	public static XTipoRicevuta valueOfValue(String name){
		for(XTipoRicevuta tiporicevuta:XTipoRicevuta.values()){
			if(tiporicevuta.value.equals(name)){
				return tiporicevuta;
			}
		}
		return null;
	}
	
	
	public String getValue() {
		return value;
	}
	
}
