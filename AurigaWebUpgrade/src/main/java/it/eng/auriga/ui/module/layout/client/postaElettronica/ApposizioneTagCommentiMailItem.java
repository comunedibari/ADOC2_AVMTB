package it.eng.auriga.ui.module.layout.client.postaElettronica;

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class ApposizioneTagCommentiMailItem extends ReplicableItem {
	
	private Record detailRecord;

	public Record getDetailRecord() {
		return detailRecord;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		return new ApposizioneTagCommentiMailCanvas(this);
	}

}