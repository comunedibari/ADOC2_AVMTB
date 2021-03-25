package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

public class AltriDirRespRegTecnicaCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public AltriDirRespRegTecnicaCompletaItem() {
		super("dirigenteRespRegTecnica", "dirigenteRespRegTecnicaFromLoadDett", "codUoDirigenteRespRegTecnica", "desDirigenteRespRegTecnica", "flgDirigenteRespRegTecnicaFirmatario");
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public boolean showFlgFirmatario() {
		return false;
	}

}
