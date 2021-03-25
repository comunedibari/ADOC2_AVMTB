package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

public class ResponsabileUnicoProvvedimentoCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public ResponsabileUnicoProvvedimentoCompletaItem() {
		super("responsabileUnicoProvvedimento", "responsabileUnicoProvvedimentoFromLoadDett", "codUoResponsabileUnicoProvvedimento", "desResponsabileUnicoProvvedimento");
	}

	@Override
	public void manageChangedScrivaniaSelezionata() {
		
	}
	
	@Override
	public Boolean getShowRemoveButton() {
		return false;
	};
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}	
	
}
