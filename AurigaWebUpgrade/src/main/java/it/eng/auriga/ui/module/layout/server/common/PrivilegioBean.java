package it.eng.auriga.ui.module.layout.server.common;

import it.eng.document.NumeroColonna;

public class PrivilegioBean {
	
	@NumeroColonna(numero = "1")
	private String privilegio;

	public String getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(String privilegio) {
		this.privilegio = privilegio;
	}
}
