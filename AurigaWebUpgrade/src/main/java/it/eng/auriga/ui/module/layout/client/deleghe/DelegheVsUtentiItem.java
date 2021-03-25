package it.eng.auriga.ui.module.layout.client.deleghe;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DelegheVsUtentiItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DelegheVsUtentiCanvas lDelegheVsUtentiCanvas = new DelegheVsUtentiCanvas();		
		return lDelegheVsUtentiCanvas;
	}
	
}
