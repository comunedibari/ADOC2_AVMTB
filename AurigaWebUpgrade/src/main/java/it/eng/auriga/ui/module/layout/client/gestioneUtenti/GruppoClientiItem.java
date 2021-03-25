package it.eng.auriga.ui.module.layout.client.gestioneUtenti;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class GruppoClientiItem extends ReplicableItem {
	
	private String idGruppoClienti;
	
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		GruppoClientiCanvas lGruppoClientiCanvas = new GruppoClientiCanvas();
		return lGruppoClientiCanvas;
	}


	public String getIdGruppoClienti() {
		return idGruppoClienti;
	}


	public void setIdGruppoClienti(String idGruppoClienti) {
		this.idGruppoClienti = idGruppoClienti;
	}


	
	
}
