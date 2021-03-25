package it.eng.auriga.ui.module.layout.client.caselleEmail;

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class FruitoriCasellaItem extends ReplicableItem{
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		FruitoriCasellaCanvas lFruitoriCasellaCanvas = new FruitoriCasellaCanvas(this);
		return lFruitoriCasellaCanvas;
	}

	@Override
	public Record getCanvasDefaultRecord() {
		if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			Record lRecord = new Record();	
			lRecord.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
			return lRecord;				
		}
		return null;
	}
	
}
