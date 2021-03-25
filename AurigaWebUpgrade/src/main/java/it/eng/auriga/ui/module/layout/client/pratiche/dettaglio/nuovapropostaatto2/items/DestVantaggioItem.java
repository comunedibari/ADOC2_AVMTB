package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DestVantaggioItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DestVantaggioCanvas lDestVantaggioCanvas = new DestVantaggioCanvas(this);
		return lDestVantaggioCanvas;
	}
	
	public boolean isRequiredDestVantaggio() {
		return false;
	}
	
}
