package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.auriga.ui.module.layout.client.protocollazione.SelezionaScrivaniaItem;

public class ResponsabileUnicoProvvedimentoItem extends SelezionaScrivaniaItem {
	
	@Override
	public int getSelectItemOrganigrammaWidth() {
		return 650;
	}

	@Override
	public void manageChangedScrivaniaSelezionata() {
		
	}
	
	@Override
	public Boolean getShowRemoveButton() {
		return false;
	};
	
}
