package it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean;

import it.eng.document.NumeroColonna;
import java.io.Serializable;


public class AssegnatarioOpBatchXmlBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String typeNodo;
	
	@NumeroColonna(numero = "2")
	private String idUo;
	
    @NumeroColonna(numero = "3")
	private String flgSottoUO;

    private boolean flgIncludiSottoUO;
    

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	

	public String getTypeNodo() {
		return typeNodo;
	}

	public void setTypeNodo(String typeNodo) {
		this.typeNodo = typeNodo;
	}

	public String getIdUo() {
		return idUo;
	}



	public String getFlgSottoUO() {
		return flgSottoUO;
	}



	public void setFlgSottoUO(String flgSottoUO) {
		this.flgSottoUO = flgSottoUO;
	}



	public boolean isFlgIncludiSottoUO() {
		return flgIncludiSottoUO;
	}



	public void setFlgIncludiSottoUO(boolean flgIncludiSottoUO) {
		this.flgIncludiSottoUO = flgIncludiSottoUO;
	}



	
}
