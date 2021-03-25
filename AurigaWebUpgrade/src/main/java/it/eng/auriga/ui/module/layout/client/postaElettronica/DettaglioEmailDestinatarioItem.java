package it.eng.auriga.ui.module.layout.client.postaElettronica;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DettaglioEmailDestinatarioItem extends ReplicableItem {

	private String idEmail;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		
		return new DettaglioEmailDestinatarioCanvas();
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
}
