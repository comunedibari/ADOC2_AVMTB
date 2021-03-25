package it.eng.dm.engine.manage.bean;


public enum TipoOperazione {
	
	RICERCA("ricerca"),
	INSERIMENTO("inserimento"),
	AGGIORNAMENTO("aggiornamento");
	
	private String value;
	
	private TipoOperazione(String value) {
		this.value = value;
	}
	
	public static TipoOperazione valueOfValue(String name){
		for(TipoOperazione stato:TipoOperazione.values()){
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
