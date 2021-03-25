package it.eng.auriga.ui.module.layout.client.organigramma;

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public abstract class ContattiOrganigrammaItem extends ReplicableItem{
		
	@Override
	public ReplicableCanvas getCanvasToReply() {
		ContattiOrganigrammaCanvas lContattiOrganigrammaCanvas = new ContattiOrganigrammaCanvas(this);
		return lContattiOrganigrammaCanvas;
	}
	
	@Override
	public boolean hasDefaultValue() {
		return true;
	}
	
	@Override
	public Record getCanvasDefaultRecord() {
		Record record = new Record();
		record.setAttribute("tipo", "E");
		return record;
	}

	public abstract boolean isNewMode();
}