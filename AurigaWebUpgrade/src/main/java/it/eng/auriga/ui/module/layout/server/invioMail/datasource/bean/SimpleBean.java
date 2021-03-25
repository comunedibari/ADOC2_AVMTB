package it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean;

public class SimpleBean<T> {

	private T value;

	public void setValue(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

}
