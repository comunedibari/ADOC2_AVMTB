package it.eng.auriga.repository2.lucene;

public enum FilterType {

	ID_SP_AOO("ID_SP_AOO"), 
	ID_PIANO_CLASSIF("ID_PIANO_CLASSIF"), 
	ID_ORGANIGRAMMA("ID_ORGANIGRAMMA"),
	TIPO_ALBO_ELENCO("TIPO_ALBO_ELENCO");

	private String value;

	private FilterType(String value) {
		this.value = value;
	}

	public static FilterType valueOfValue(String name) {
		for (FilterType stato : FilterType.values()) {
			if (stato.value.equals(name)) {
				return stato;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}
	

}
