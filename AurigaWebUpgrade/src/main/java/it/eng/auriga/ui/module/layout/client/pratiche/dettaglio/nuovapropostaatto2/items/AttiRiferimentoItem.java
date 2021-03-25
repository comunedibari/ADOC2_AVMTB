package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AttiRiferimentoItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		AttiRiferimentoCanvas lAttiRiferimentoCanvas = new AttiRiferimentoCanvas(this);
		return lAttiRiferimentoCanvas;
	}
	
	public boolean isRequiredAttoRiferimento() {
		return false;
	}

	public boolean isFromDeterminaAggiudicaProceduraGara() {
		return false;
	}
	
	public boolean isFromDeterminaRimodulazioneSpesaGaraAggiudicata() {
		return false;
	}
	
	public boolean isFromRatificaDeliberaUrgenza() {
		return false;
	}
				
	public boolean showFlgPresentaASistemaItem() {
		return false;
	}
	
	public String getTitleFlgPresentaASistemaItem() {
		return null;
	}
	
	public String getDefaultValueFlgPresentaASistemaItem() {
		return null;
	}
	
	public boolean isRequiredFlgPresentaASistemaItem() {
		return false;
	}
	
	public boolean isEditabileFlgPresentaASistemaItem() {
		return false;
	}
	
}
