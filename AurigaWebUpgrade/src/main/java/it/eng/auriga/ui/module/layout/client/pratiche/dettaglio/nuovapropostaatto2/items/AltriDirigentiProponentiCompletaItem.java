package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

public class AltriDirigentiProponentiCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public AltriDirigentiProponentiCompletaItem() {
		super("dirigenteProponente", "dirigenteProponenteFromLoadDett", "codUoDirigenteProponente", "desDirigenteProponente", "flgDirigenteProponenteFirmatario", "motiviDirigenteProponente");
	}

	public String getAltriParamLoadCombo() {
		return null;
	}

	public boolean showFlgFirmatario() {
		return false;
	}
	
	public boolean showMotivi() {
		return false;
	}

	public boolean isRequiredMotivi() {
		return false;
	}

}