package it.eng.auriga.ui.module.layout.client.caselleEmail;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class UtentiCasellaItem extends ReplicableItem{
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		UtentiCasellaCanvas lUtentiCasellaCanvas = new UtentiCasellaCanvas(this);
		return lUtentiCasellaCanvas;
	}

}
