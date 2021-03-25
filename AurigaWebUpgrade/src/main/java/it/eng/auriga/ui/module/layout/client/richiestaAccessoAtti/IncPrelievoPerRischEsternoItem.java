package it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class IncPrelievoPerRischEsternoItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		IncPrelievoPerRichEsternoCanvas lIncPrelievoPerRichEsternoCanvas = new IncPrelievoPerRichEsternoCanvas();		
		return lIncPrelievoPerRichEsternoCanvas;
	}


}
