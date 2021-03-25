package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class RespVistiConformitaItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		RespVistiConformitaCanvas lRespVistiConformitaCanvas = new RespVistiConformitaCanvas(this);
		return lRespVistiConformitaCanvas;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}

	public boolean showFlgFirmatario() {
		return true;
	}
	
	public boolean showMotivi() {
		return true;
	}
	
	public boolean isRequiredMotivi() {
		return true;
	}
	
}
