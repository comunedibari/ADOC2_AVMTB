package it.eng.auriga.ui.module.layout.client.gestioneUtenti;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class SocietaUtentiItem extends ReplicableItem {
	
	private String idSocieta;
	
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		SocietaUtentiCanvas lSocietaUtentiCanvas = new SocietaUtentiCanvas();
		return lSocietaUtentiCanvas;
	}


	public String getIdSocieta() {
		return idSocieta;
	}


	public void setIdSocieta(String idSocieta) {
		this.idSocieta = idSocieta;
	}

	
}
