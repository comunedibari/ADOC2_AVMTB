package it.eng.auriga.ui.module.layout.client.protocollazione;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class SelezionaUOItem extends ReplicableItem {
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		SelezionaUOCanvas lSelezionaUOCanvas = new SelezionaUOCanvas(this);		
		return lSelezionaUOCanvas;
	}
	
	public int getSelectItemOrganigrammaWidth() {
		return 500;
	}
	
	public void manageChangedUoSelezionata() {

	}
	
	public class SelezionaUOMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<SelezionaUOCanvas> multiLookupList = new ArrayList<SelezionaUOCanvas>(); 
		
		public SelezionaUOMultiLookupOrganigramma(Record record) {
			super(record, false, 0);			
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			SelezionaUOCanvas lastCanvas = (SelezionaUOCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecord(record);
				multiLookupList.add(lastCanvas);
			} else {
				SelezionaUOCanvas canvas = (SelezionaUOCanvas) onClickNewButton();
				canvas.setFormValuesFromRecord(record);
				multiLookupList.add(canvas);
			}					
		}		
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(SelezionaUOCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("organigramma").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}	
		
		@Override
		public String getFinalita() {
			return null;
		}
		
	}
	
	@Override
	public boolean hasDefaultValue() {
		return true;
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
	
	public RecordList getValueAsRecordList() {
		RecordList value = new RecordList();
		for(ReplicableCanvas canvas : getAllCanvas()) {			
			value.add(canvas.getFormValuesAsRecord());
		}
		return value;
	}	
	
	public boolean showLookupOrganigrammaButton() {
		return true;
	}
	
	public String getFlgSoloValide() {
		return "true";
	}
	
}
