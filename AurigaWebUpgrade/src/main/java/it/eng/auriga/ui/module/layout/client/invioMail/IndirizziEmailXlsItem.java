package it.eng.auriga.ui.module.layout.client.invioMail;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class IndirizziEmailXlsItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		IndirizziEmailXlsCanvas dettagliXlsIndirizziEmailCanvas = new IndirizziEmailXlsCanvas();
		return dettagliXlsIndirizziEmailCanvas;
	}
	
	@Override
	public ReplicableCanvas onClickNewButton() {
		ReplicableCanvas lReplicableCanvas = super.onClickNewButton();
		return lReplicableCanvas;
	}
	
	public boolean isCasellaPec() {
		return false;
	}
}