package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class CoordinatoriCompCircItem extends GroupReplicableItem {

	public CoordinatoriCompCircItem(String title) {
		super(title);
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		CoordinatoriCompCircCanvas lCoordinatoriCompCircCanvas = new CoordinatoriCompCircCanvas(this);
		return lCoordinatoriCompCircCanvas;
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((CoordinatoriCompCircCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}

}
