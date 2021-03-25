package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

public class DirigentiConcertoCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public DirigentiConcertoCompletaItem() {
		super("dirigenteConcerto", "dirigenteConcertoFromLoadDett", "codUoDirigenteConcerto", "desDirigenteConcerto", "flgDirigenteConcertoFirmatario");
	}

	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public boolean showFlgFirmatario() {
		return false;
	}
	
}
