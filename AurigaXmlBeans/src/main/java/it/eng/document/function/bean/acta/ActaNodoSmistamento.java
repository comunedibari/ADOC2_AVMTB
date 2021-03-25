package it.eng.document.function.bean.acta;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActaNodoSmistamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idNodo;
	private String codiceNodo;

	public String getIdNodo() {
		return idNodo;
	}

	public void setIdNodo(String idNodo) {
		this.idNodo = idNodo;
	}

	public String getCodiceNodo() {
		return codiceNodo;
	}

	public void setCodiceNodo(String codiceNodo) {
		this.codiceNodo = codiceNodo;
	}

}
