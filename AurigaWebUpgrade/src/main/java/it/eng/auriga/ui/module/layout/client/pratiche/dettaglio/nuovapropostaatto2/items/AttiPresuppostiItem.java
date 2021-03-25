package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.auriga.ui.module.layout.client.common.items.SelezionaValoriDizionarioItem;

public class AttiPresuppostiItem extends SelezionaValoriDizionarioItem {
	
	@Override
	public String getDictionaryEntry() {
		return "ATTI_PRESUPPOSTI";
	}	
	
	@Override
	public boolean isRequiredStrInDes() {
		return false;
	}
	
	@Override
	public boolean getAddUnknownValues() {
		return true;
	}

}
