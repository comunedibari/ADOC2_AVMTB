package it.eng.auriga.ui.module.layout.client.anagrafiche;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class GruppiSoggettiLayout extends CustomLayout {	

	public GruppiSoggettiLayout() {
		this(null, null, null);
	}

	public GruppiSoggettiLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}
	
	public GruppiSoggettiLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {				
		super("gruppi_soggetti", 
				new GWTRestDataSource("AnagraficaGruppiSoggettiDataSource", "idGruppo", FieldType.TEXT),  
				new ConfigurableFilter("gruppi_soggetti"), 
				new GruppiSoggettiList("gruppi_soggetti") ,
				new GruppiSoggettiDetail("gruppi_soggetti"),
				finalita,
				flgSelezioneSingola,
				showOnlyDetail
		);
		multiselectButton.hide();	

		if (!isAbilToIns()) {
			newButton.hide();
		}

	}	

	public static boolean isAbilToIns() {
		
		return Layout.isPrivilegioAttivo("GRS/G;I");
	}
	
	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("GRS/G;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("GRS/G;FC");
	}	

	public static boolean isRecordAbilToMod(boolean flgLocked) {
		
		return !flgLocked && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgAttivo, boolean flgLocked) {
		
		return flgAttivo && !flgLocked && isAbilToDel();
	}	

	@Override
	public void manageOnClickSearchButton() {
		
		super.manageOnClickSearchButton();
	}

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().gruppisoggetti_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().gruppisoggetti_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().gruppisoggetti_detail_view_title(getTipoEstremiRecord(record));		
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		
		super.viewMode();
		Record record = new Record(detail.getValuesManager().getValues());
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equals("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");
		if(isRecordAbilToDel(flgValido, recProtetto)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		if(isRecordAbilToMod(recProtetto)) {
			editButton.show();
		} else{
			editButton.hide();
		}	
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		
		final Record newRecord = new Record();

		newRecord.setAttribute("id", record.getAttributeAsString("idGruppo"));			
		String codiceRapido = record.getAttributeAsString("codiceRapido");
		String nome = record.getAttributeAsString("nome");
		newRecord.setAttribute("nome", codiceRapido != null && !"".equals(codiceRapido) ? codiceRapido + " - " + nome : nome);	
		newRecord.setAttribute("icona", "menu/gruppi_soggetti.png");		
				
		return newRecord;
	}
	
}
