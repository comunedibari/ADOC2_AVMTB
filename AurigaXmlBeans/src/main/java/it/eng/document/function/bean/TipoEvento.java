package it.eng.document.function.bean;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

/**
 * Classe per la mappatura dei tipi evento per il job GAE di BNL
 * 
 * @author denbraga
 *
 */
@XmlRootElement
public class TipoEvento {

	@NumeroColonna(numero = "1")
	private String codTipoEvento;
	
	public String getCodTipoEvento() {
		return codTipoEvento;
	}
	public void setCodTipoEvento(String codTipoEvento) {
		this.codTipoEvento = codTipoEvento;
	}

}
