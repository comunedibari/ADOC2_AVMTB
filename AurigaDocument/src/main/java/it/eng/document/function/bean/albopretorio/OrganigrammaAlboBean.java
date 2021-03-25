package it.eng.document.function.bean.albopretorio;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class OrganigrammaAlboBean{

	@NumeroColonna(numero = "43")
	private String id;

	@NumeroColonna(numero = "3")
	private String descrizione;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
