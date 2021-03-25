package it.eng.auriga.ui.module.layout.client.anagrafiche;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AltreDenominazioniSoggettoItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {		
		AltreDenominazioniSoggettoCanvas lAltreDenominazioniSoggettoCanvas = new AltreDenominazioniSoggettoCanvas();
		return lAltreDenominazioniSoggettoCanvas;
	}
	
}
