package it.eng.auriga.ui.module.layout.client.gestioneUtenti;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;


public class PuntoVenditaItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {		
		PuntoVenditaCanvas lPuntoVenditaCanvas = new PuntoVenditaCanvas();
		return lPuntoVenditaCanvas;
	}
	
}
