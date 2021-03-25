package it.eng.utility.ui.module.layout.client.common.filter;

import com.smartgwt.client.types.FieldType;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class StringaRicercaEsatta extends CustomDataSourceField {

	public StringaRicercaEsatta() {
		super(FieldType.TEXT);
	}

	public StringaRicercaEsatta(String name) {
		super(name, FieldType.TEXT);
	}

	public StringaRicercaEsatta(String name, String title) {
		super(name, FieldType.TEXT, title);
	}

	public StringaRicercaEsatta(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public StringaRicercaEsatta(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}

	protected void init() {

		setAttribute("customType", "stringa_ricerca_esatta");

	}

}
