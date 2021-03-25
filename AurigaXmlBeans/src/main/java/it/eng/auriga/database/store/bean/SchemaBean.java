package it.eng.auriga.database.store.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SchemaBean implements Serializable{

	private static final long serialVersionUID = -1656496629856134611L;
	private String schema;

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
}
