package it.eng.auriga.ui.module.layout.client.registriNumerazione;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class TipiDocAmmEscItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {		
		TipiDocAmmEscCanvas lTipiDocAmmEscCanvas = new TipiDocAmmEscCanvas();
		return lTipiDocAmmEscCanvas;
	}
}