package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class IstruttoreItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		
		IstruttoreCanvas lIstruttoreCanvas = new IstruttoreCanvas(this);
		return lIstruttoreCanvas;
	}
	
	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((IstruttoreCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
}