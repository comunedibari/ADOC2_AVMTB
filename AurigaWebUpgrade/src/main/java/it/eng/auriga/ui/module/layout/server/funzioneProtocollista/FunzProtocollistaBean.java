package it.eng.auriga.ui.module.layout.server.funzioneProtocollista;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class FunzProtocollistaBean {
	
	@NumeroColonna(numero = "1")
	private String idUo;
	
	@NumeroColonna(numero = "2")
	private String descUo;

	
	public String getIdUo() {
		return idUo;
	}
	
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getDescUo() {
		return descUo;
	}
	
	public void setDescUo(String descUo) {
		this.descUo = descUo;
	}

}