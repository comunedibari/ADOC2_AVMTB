package it.eng.auriga.ui.module.layout.client.gestioneUtenti;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AreeCommercialiItem extends ReplicableItem {
	
	private String idAreaCommerciali;
	
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		AreeCommercialiCanvas lAreeCommercialiCanvas = new AreeCommercialiCanvas();
		return lAreeCommercialiCanvas;
	}


	public String getIdAreaCommerciali() {
		return idAreaCommerciali;
	}


	public void setIdAreaCommerciali(String idAreaCommerciali) {
		this.idAreaCommerciali = idAreaCommerciali;
	}




	
	
}
