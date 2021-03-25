package it.eng.aurigamailbusiness.bean.restrepresentation.row;

import it.eng.document.NumeroColonna;

public class RigaIdEmailInoltrata {

	@NumeroColonna(numero = "1")
	private String idMailInoltrata;
	
	@NumeroColonna(numero = "2")
	private String flgMailStatoLavAperta;

	public String getIdMailInoltrata() {
		return idMailInoltrata;
	}

	public void setIdMailInoltrata(String idMailInoltrata) {
		this.idMailInoltrata = idMailInoltrata;
	}
	
	public String getFlgMailStatoLavAperta() {
		return flgMailStatoLavAperta;
	}

	public void setFlgMailStatoLavAperta(String flgMailStatoLavAperta) {
		this.flgMailStatoLavAperta = flgMailStatoLavAperta;
	}
	
	

}
