package it.eng.auriga.ui.module.layout.client.postaElettronica;

public enum TipoItemLavorazione {

	NOTA_LIBERA("AT", "Nota libera"), FILE_ALLEGATO("F", "File allegato");

	private String value;
	private String descrizione;

	TipoItemLavorazione(String value, String descrizione) {
		this.value = value;
		this.descrizione = descrizione;
	}

	public String getValue() {
		return this.value;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

}