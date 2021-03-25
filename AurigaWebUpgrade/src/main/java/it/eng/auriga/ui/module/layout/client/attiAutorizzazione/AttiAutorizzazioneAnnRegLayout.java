package it.eng.auriga.ui.module.layout.client.attiAutorizzazione;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.types.FieldType;

public class AttiAutorizzazioneAnnRegLayout extends CustomLayout {
	
	public AttiAutorizzazioneAnnRegLayout() {
		this(null);
	}

	public AttiAutorizzazioneAnnRegLayout(GWTRestDataSource pGWTRestDataSource) {
		
		super("atti_autorizzazione_ann_reg", 
				pGWTRestDataSource != null ? pGWTRestDataSource : new GWTRestDataSource("AttiAutorizzazioneAnnRegDatasource", true, "idAtto", FieldType.TEXT),
				null,
				new AttiAutorizzazioneAnnRegList("atti_autorizzazione_ann_reg"),
				new AttiAutorizzazioneAnnRegDetail("atti_autorizzazione_ann_reg")
		);
		
		multiselectButton.hide();
		refreshListButton.hide();
		topListToolStripSeparator.hide();		
	}	
	
	@Override
	public boolean isForcedToAutoSearch() {
		return true;
	}
	
	public static boolean isRecordAbilToMod(boolean isAttoChiuso) {
		return !isAttoChiuso; 
	}	
	
	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();		
		deleteButton.hide();
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();		
	}

}
