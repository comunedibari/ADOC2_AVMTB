package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DirigenteRespRegTecnicaItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DirigenteRespRegTecnicaCanvas lDirRespRegTecnicaCanvas = new DirigenteRespRegTecnicaCanvas(this);
		return lDirRespRegTecnicaCanvas;
	}
	
	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((DirigenteRespRegTecnicaCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public void manageOnChangedFlgDirRespRegTecnicaAncheRdP(boolean value) {
		
	}

	public void manageOnChangedFlgDirRespRegTecnicaAncheRUP(boolean value) {
		
	}
		
	public String getIdUdAttoDaAnn() {
		return null;
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public boolean showFlgAncheRdP() {
		return true;
	}
	
	public boolean showFlgAncheRUP() {
		return true;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public void clearFlgDirRespRegTecnicaAncheRUP() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			lReplicableCanvas.getForm()[0].setValue("flgDirRespRegTecnicaAncheRUP", false);
		}	
	}

}
