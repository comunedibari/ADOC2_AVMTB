package it.eng.auriga.ui.module.layout.client.gestioneatti;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class UfficioLiquidatoreItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		UfficioLiquidatoreCanvas lUfficioLiquidatoreCanvas = new UfficioLiquidatoreCanvas(this);
		return lUfficioLiquidatoreCanvas;
	}

}
