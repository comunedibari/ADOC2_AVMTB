package it.eng.auriga.ui.module.layout.client.organigramma;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

/**
 * 
 * @author Antonio Peluso
 *
 */

public abstract class CentriCostoOrganigrammaItem extends ReplicableItem{
		
	@Override
	public ReplicableCanvas getCanvasToReply() {
		CentriCostoOrganigrammaCanvas lCentriCostoOrganigrammaCanvas = new CentriCostoOrganigrammaCanvas(this);
		return lCentriCostoOrganigrammaCanvas;
	}

	public abstract boolean isNewMode();
}