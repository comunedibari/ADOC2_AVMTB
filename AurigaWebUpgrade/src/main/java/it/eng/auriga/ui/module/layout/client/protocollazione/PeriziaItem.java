package it.eng.auriga.ui.module.layout.client.protocollazione;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class PeriziaItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		PeriziaCanvas lPeriziaCanvas = new PeriziaCanvas();		
		return lPeriziaCanvas;
	}
}
