package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

public class SedeAppuntamentoXmlBean {

	@NumeroColonna(numero = "1")
	private String sedeAppuntamento;

	public String getSedeAppuntamento() {
		return sedeAppuntamento;
	}

	public void setSedeAppuntamento(String sedeAppuntamento) {
		this.sedeAppuntamento = sedeAppuntamento;
	}

}
