package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class ValoriDizionarioItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		ValoriDizionarioCanvas lValoriDizionarioCanvas = new ValoriDizionarioCanvas(this);
		return lValoriDizionarioCanvas;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}

}