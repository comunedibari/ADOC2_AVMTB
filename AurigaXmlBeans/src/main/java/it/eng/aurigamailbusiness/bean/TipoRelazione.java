package it.eng.aurigamailbusiness.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TipoRelazione {

	DIRETTA("diretta"), 
	INVERSA("inversa");

	private String value;

	private TipoRelazione(String value) {
		this.value = value;
	}

	public static TipoRelazione valueOfValue(String name) {
		for (TipoRelazione stato : TipoRelazione.values()) {
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
