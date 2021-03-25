package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

public class AttoRichiestaXmlBean {

	@NumeroColonna(numero = "1")
	private String estremiRichiesta;

	
	public String getEstremiRichiesta() {
		return estremiRichiesta;
	}

	
	public void setEstremiRichiesta(String estremiRichiesta) {
		this.estremiRichiesta = estremiRichiesta;
	}

}
