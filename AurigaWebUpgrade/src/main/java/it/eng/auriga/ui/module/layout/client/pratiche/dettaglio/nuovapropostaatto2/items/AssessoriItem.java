package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class AssessoriItem extends GroupReplicableItem {

	public AssessoriItem(String title) {
		super(title);		
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		AssessoriCanvas lAssessoriCanvas = new AssessoriCanvas(this);
		return lAssessoriCanvas;
	}
	
	public boolean selectUniqueValueAfterChangedParams() {
		return false;
	}

	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((AssessoriCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}	
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public boolean showFlgFirmatario() {
		return false;
	}

}
