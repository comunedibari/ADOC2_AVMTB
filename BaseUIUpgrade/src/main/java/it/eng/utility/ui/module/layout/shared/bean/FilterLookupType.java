package it.eng.utility.ui.module.layout.shared.bean;

public enum FilterLookupType {

	rubrica_soggetti("rubrica_soggetti"), indirizzi_viario("indirizzi_viario");

	private String value;

	FilterLookupType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
