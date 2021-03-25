package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.auriga.ui.module.layout.client.common.items.SelezionaValoriDizionarioItem;

public class RiferimentiNormativiItem extends SelezionaValoriDizionarioItem {

	@Override
	public String getDictionaryEntry() {
		return "RIFERIMENTI_NORMATIVI";
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
