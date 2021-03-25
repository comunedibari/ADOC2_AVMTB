package it.eng.auriga.ui.module.layout.client.gestioneatti.delibere;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class AltriPresentiItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		AltriPresentiCanvas lAltriPresentiCanvas = new AltriPresentiCanvas(this);
		return lAltriPresentiCanvas;
	}
	
	public String getTipologiaSessione() {
		return null;
	}

}