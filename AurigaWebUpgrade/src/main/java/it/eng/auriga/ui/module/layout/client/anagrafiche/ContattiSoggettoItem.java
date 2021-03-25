package it.eng.auriga.ui.module.layout.client.anagrafiche;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class ContattiSoggettoItem extends ReplicableItem{
		
	@Override
	public ReplicableCanvas getCanvasToReply() {
		ContattiSoggettoCanvas lContattiSoggettoCanvas = new ContattiSoggettoCanvas();
		return lContattiSoggettoCanvas;
	}	
	
	public abstract boolean isNewMode();
	
}
