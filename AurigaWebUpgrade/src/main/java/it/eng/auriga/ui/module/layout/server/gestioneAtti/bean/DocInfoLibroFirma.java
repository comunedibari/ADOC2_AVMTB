package it.eng.auriga.ui.module.layout.server.gestioneAtti.bean;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class DocInfoLibroFirma {

	@NumeroColonna(numero = "1")
	private String idUd;
	@NumeroColonna(numero = "2")
	private String idDocType;
	@NumeroColonna(numero = "3")
	private String idProcess;

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}

}
