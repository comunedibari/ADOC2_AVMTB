package it.eng.auriga.ui.module.layout.client.attributiCustom;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class OpzioniListaSceltaAttributiCustomItem extends ReplicableItem{

	@Override
	public ReplicableCanvas getCanvasToReply() {
		OpzioniListaSceltaAttributoCustomCanvas lOpzioniListaSceltaAttributoCustomCanvas = new OpzioniListaSceltaAttributoCustomCanvas();
		return lOpzioniListaSceltaAttributoCustomCanvas;
	}
	
	public abstract boolean isNewMode();

}
