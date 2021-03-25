package it.eng.auriga.ui.module.layout.server.registriNumerazione.datasource.bean;

import it.eng.document.NumeroColonna;

public class TipiDocAmmEscBean {

	@NumeroColonna(numero = "1")
	private String idTipoDocumento;
	
	@NumeroColonna(numero = "2")
	private String descTipoDocumento;
	
	@NumeroColonna(numero = "3")
	private Integer flgElimina;
	
	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getDescTipoDocumento() {
		return descTipoDocumento;
	}

	public void setDescTipoDocumento(String descTipoDocumento) {
		this.descTipoDocumento = descTipoDocumento;
	}

	public Integer getFlgElimina() {
		return flgElimina;
	}

	public void setFlgElimina(Integer flgElimina) {
		this.flgElimina = flgElimina;
	}
}