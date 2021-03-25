package it.eng.auriga.ui.module.layout.client.anagrafiche;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

/**
 * 
 * @author DANCRIST
 *
 */

public class MembriGruppoUdLayout extends CustomLayout {	
	
	public MembriGruppoUdLayout(String flgAssCondDest, GWTRestDataSource pGWTRestDataSource) {
		super("membri_gruppo_ud", 
				pGWTRestDataSource,
				null,
				new MembriGruppoUdList("membri_gruppo_ud", flgAssCondDest) ,
				new CustomDetail("membri_gruppo_ud")
		);

		multiselectButton.hide();	
		newButton.hide();		
		refreshListButton.show();
		topListToolStripSeparator.hide();
		
		this.setLookup(false);
	}		
	
	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}
	
}