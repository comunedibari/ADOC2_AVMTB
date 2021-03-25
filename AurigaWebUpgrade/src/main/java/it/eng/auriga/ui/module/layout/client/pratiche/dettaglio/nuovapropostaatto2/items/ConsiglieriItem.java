package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class ConsiglieriItem extends GroupReplicableItem {

	public ConsiglieriItem(String title) {
		super(title);
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		ConsiglieriCanvas lConsiglieriCanvas = new ConsiglieriCanvas(this);
		return lConsiglieriCanvas;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public boolean showFlgFirmatario() {
		return false;
	}

}
