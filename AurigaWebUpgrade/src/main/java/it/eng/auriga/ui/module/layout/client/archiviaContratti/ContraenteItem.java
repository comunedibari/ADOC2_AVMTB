package it.eng.auriga.ui.module.layout.client.archiviaContratti;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class ContraenteItem extends ReplicableItem{

	@Override
	public ReplicableCanvas getCanvasToReply() {
		ContraenteCanvas contraenteCanvas = new ContraenteCanvas();
		return contraenteCanvas;
	}
	

}
