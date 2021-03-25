package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;
import java.io.Serializable;


public class DestInvioNotifica implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String tipo;
	@NumeroColonna(numero = "2")
	private String id;
	@NumeroColonna(numero = "3")
	private Flag flgIncludiSottoUO;
	@NumeroColonna(numero = "4")
	private Flag flgIncludiScrivanie;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Flag getFlgIncludiSottoUO() {
		return flgIncludiSottoUO;
	}
	public void setFlgIncludiSottoUO(Flag flgIncludiSottoUO) {
		this.flgIncludiSottoUO = flgIncludiSottoUO;
	}
	public Flag getFlgIncludiScrivanie() {
		return flgIncludiScrivanie;
	}
	public void setFlgIncludiScrivanie(Flag flgIncludiScrivanie) {
		this.flgIncludiScrivanie = flgIncludiScrivanie;
	}
	
	
}
