package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FolderAppartenenzaBean implements Serializable{

	@NumeroColonna(numero = "1")
	private BigDecimal idFolder;

	public void setIdFolder(BigDecimal idFolder) {
		this.idFolder = idFolder;
	}

	public BigDecimal getIdFolder() {
		return idFolder;
	}
}
