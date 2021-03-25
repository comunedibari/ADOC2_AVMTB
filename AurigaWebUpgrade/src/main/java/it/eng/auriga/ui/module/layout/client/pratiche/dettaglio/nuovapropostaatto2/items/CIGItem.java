package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class CIGItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		CIGCanvas lCIGCanvas = new CIGCanvas(this);
		return lCIGCanvas;
	}

}