package it.eng.auriga.ui.module.layout.client.toponomastica;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

/**
 * 
 * @author cristiano
 *
 */
public class TrovaVieLayout extends CustomLayout {

	public TrovaVieLayout() {
		this(null, null, null);
	}

	public TrovaVieLayout(String comune, String finalita, Boolean flgSelezioneSingola) {
		
		super("trova_vie", getTrovaVieDataSource(comune), new ConfigurableFilter("trova_vie"),
				new TrovaVieList("trova_vie"), new CustomDetail("trova_vie"), finalita, flgSelezioneSingola, null);

		multiselectButton.hide();
		newButton.hide();
		editButton.hide();
		deleteButton.hide();
		altreOpButton.hide();
	}
	
	public static GWTRestDataSource getTrovaVieDataSource(String comune) {
		
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("TrovaVieDataSource", "codiceViarioToponimo", FieldType.TEXT);
		if(comune != null && !"".equals(comune)) {
			lGWTRestDataSource.addParam("codIstatComune", comune);
		}
		return lGWTRestDataSource;
	}

	public static boolean isAbilToIns() {
		
		return false;
	}

	public static boolean isAbilToMod() {
		
		return false;
	}

	public static boolean isAbilToDel() {
		
		return false;
	}

	@Override
	public void doLookup(Record record) {
		
		super.doLookup(record);
	}

	@Override
	public Boolean getDetailAuto() {
		
		return false;
	}

}
