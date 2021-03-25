package it.eng.auriga.ui.module.layout.client.postaElettronica;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class CronologiaOperazioniEffettuateDettEmailLayout extends CustomLayout {

	public CronologiaOperazioniEffettuateDettEmailLayout(GWTRestDataSource pGWTRestDataSource) {
		super("cronologia_operazioni_effettuate", 
				pGWTRestDataSource,
				null,
				new CronologiaOperazioniEffettuateDettEmailList("cronologia_operazioni_effettuate"),
				new CustomDetail("cronologia_operazioni_effettuate")
		);

		multiselectButton.hide();	
		newButton.hide();		
		refreshListButton.hide();
		topListToolStripSeparator.hide();
		
		this.setLookup(false);
	}		
	
	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}

}
