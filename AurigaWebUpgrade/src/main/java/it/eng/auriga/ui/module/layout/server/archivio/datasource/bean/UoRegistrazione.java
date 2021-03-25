package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

/**
 * 
 * @author DANCRIST
 *
 */

public class UoRegistrazione {
	
	@NumeroColonna(numero = "1")
	private String idUo;
	
	@NumeroColonna(numero = "2")
	private Flag flgIncludiSottoUo;
	
	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	
	public Flag getFlgIncludiSottoUo() {
		return flgIncludiSottoUo;
	}

	public void setFlgIncludiSottoUo(Flag flgIncludiSottoUo) {
		this.flgIncludiSottoUo = flgIncludiSottoUo;
	}

}