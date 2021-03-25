package it.eng.auriga.ui.module.layout.client.protocollazione;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AltriRiferimentiItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		AltriRiferimentiCanvas lAltriRiferimentiCanvas = new AltriRiferimentiCanvas();
		return lAltriRiferimentiCanvas;
	}

}
