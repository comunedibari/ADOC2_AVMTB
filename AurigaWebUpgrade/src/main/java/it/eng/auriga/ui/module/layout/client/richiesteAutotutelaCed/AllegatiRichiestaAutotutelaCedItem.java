package it.eng.auriga.ui.module.layout.client.richiesteAutotutelaCed;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AllegatiRichiestaAutotutelaCedItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		AllegatiRichiestaAutotutelaCedCanvas lAllegatoCanvas = new AllegatiRichiestaAutotutelaCedCanvas();
		return lAllegatoCanvas;
	}

}
