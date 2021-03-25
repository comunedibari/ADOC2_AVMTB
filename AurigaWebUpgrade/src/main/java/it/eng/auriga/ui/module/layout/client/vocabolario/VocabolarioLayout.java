package it.eng.auriga.ui.module.layout.client.vocabolario;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;


/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class VocabolarioLayout extends CustomLayout {
	
	public VocabolarioLayout(){
		this(null,null);
	}
	
	public VocabolarioLayout(String finalita, Boolean showOnlyDetail){
		super("vocabolario",
			   new GWTRestDataSource("VocabolarioDataSource", "valore", FieldType.TEXT),
			   new ConfigurableFilter("vocabolario"),
			   new VocabolarioList("vocabolario"),
			   new VocabolarioDetail("vocabolario"),
			   finalita,
			   showOnlyDetail
		);
		
		multiselectButton.hide();
		
		if (!isAbilToIns()) {
			newButton.hide();
		}
	}
	
	@Override
	public void manageOnClickSearchButton() {
		super.manageOnClickSearchButton();
	}
	
	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("DC/DIZ/UO;I");
	}

	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("DC/DIZ/UO;M");
	}
	
	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("DC/DIZ/UO;FC");
	}
	
	public static boolean isRecordAbilToMod(boolean flgAbilModifica) {
		return isAbilToMod() && flgAbilModifica;
	}

	public static boolean isRecordAbilToDel(boolean flgAbilEliminazione) {
		return isAbilToDel() && flgAbilEliminazione;
	}
	
	public static boolean isPartizionamentoVocabolarioAbilitato() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PARTIZ_DIZIONARIO_X_UO");
	}

	public static boolean isAbilAssegnareUoASoggetto() {
		return Layout.isPrivilegioAttivo("DC/DIZ;I");
	}

	public static boolean isAbilInserireModificareSoggInQualsiasiUo() {
		return Layout.isPrivilegioAttivo("DC/DIZ;M");
	}
	
	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().vocabolario_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().vocabolario_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().vocabolario_view_title(getTipoEstremiRecord(record));		
	}
	
	public String getTipoEstremiRecord(Record record) {		
		return record.getAttribute("valore");
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
		final boolean flgAbilEliminazione = record.getAttribute("flgAbilEliminazione") != null && record.getAttributeAsString("flgAbilEliminazione").equalsIgnoreCase("1");
		if(isRecordAbilToDel(flgAbilEliminazione)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		final boolean flgAbilModifica = record.getAttribute("flgAbilModifica") != null && record.getAttributeAsString("flgAbilModifica").equalsIgnoreCase("1");
		if(isRecordAbilToMod(flgAbilModifica)) {
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

}
