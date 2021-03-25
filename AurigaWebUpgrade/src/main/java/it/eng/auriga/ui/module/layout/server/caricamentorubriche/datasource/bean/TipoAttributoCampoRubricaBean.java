package it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean;

import it.eng.document.NumeroColonna;

public class TipoAttributoCampoRubricaBean {

	@NumeroColonna(numero = "1")
	private String key;

	@NumeroColonna(numero = "2")
	private String value;

	@NumeroColonna(numero = "3")
	private String type;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
