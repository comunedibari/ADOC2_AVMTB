package it.eng.auriga.ui.module.layout.client.anagrafiche;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.InizialiLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;

public class ClientiLayout extends InizialiLayout {
	
	public ClientiLayout() {
		this("anagrafiche_clienti", null, null, null);
	}
	
	public ClientiLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}
	
	public ClientiLayout(String nomeEntita, String finalita) {
		this(nomeEntita, finalita, null, null);
	}
	
	public ClientiLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null );
	}
	
	public ClientiLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super(nomeEntita, 
			  new GWTRestDataSource("AnagraficaClientiDataSource", "idSoggetto", FieldType.TEXT),  
			  //new ConfigurableFilter(nomeEntita),
			  
			  ( (nomeEntita != null && !"".equals(nomeEntita) && isNespresso() ) ? new ConfigurableFilter("anagrafiche_clienti_nespresso") : new ConfigurableFilter(nomeEntita) ) , 
					  
			  new ClientiList(nomeEntita) ,
			  new ClientiDetail(nomeEntita),
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
		
		return Layout.isPrivilegioAttivo("UT/AC;I");
		
	}
	
	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("UT/AC;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("UT/AC;FC");
	}	
		
	public static boolean isRecordAbilToMod(boolean flgDiSistema) {
		
		return !flgDiSistema && isAbilToMod();
	}
	
	public static boolean isRecordAbilToDel(boolean flgValido, boolean flgDiSistema) {
		
		return flgValido && !flgDiSistema && isAbilToDel();
	}		
	
	@Override
	public String getTipoEstremiRecord(Record record) {
		
		if(record.getAttributeAsString("tipo") != null && !"".equals(record.getAttributeAsString("tipo"))) {
			if("UP".equals(record.getAttributeAsString("tipo")) || "#AF".equals(record.getAttributeAsString("tipo"))) {
				return record.getAttributeAsString("cognome") + " " + record.getAttributeAsString("nome");
			} else {
				return record.getAttributeAsString("denominazione");	
			} 				
		}
		return super.getTipoEstremiRecord(record);
	}
	
	@Override
	public void onSaveButtonClick() {
		final Record record = new Record(detail.getValuesManager().getValues());
		if(detail.validate()) {
			DSOperationType saveOperationType = detail.getValuesManager().getSaveOperationType();			
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			DSCallback callback = new DSCallback() {								
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record savedRecord = response.getData()[0];
						if(savedRecord.getAttribute("flgIgnoreWarning") == null || savedRecord.getAttributeAsInt("flgIgnoreWarning") != 1) {
							try {
								list.manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {							
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										viewMode();		
										Layout.hideWaitPopup();
										Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(response.getData()[0])), "", MessageType.INFO));									
									}
								});	
							} catch(Exception e) {
								Layout.hideWaitPopup();
							}
						} else {
							detail.getValuesManager().setValue("flgIgnoreWarning", "1");
							Layout.hideWaitPopup();							
						}
						if(!fullScreenDetail) {
							reloadListAndSetCurrentRecord(savedRecord);
						}	
					} else {
						Layout.hideWaitPopup();
					}
				}
			};
			try {
				if((saveOperationType!=null && saveOperationType.equals(DSOperationType.ADD)) || record.getAttribute("idSoggetto")==null || record.getAttribute("idSoggetto").equals("")) {
					detail.getDataSource().addData(record, callback);
				} else {
					detail.getDataSource().updateData(record, callback);
				}
			} catch(Exception e) {
				Layout.hideWaitPopup();
			}
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	@Override
	public void manageOnClickSearchButton() {
		
		super.manageOnClickSearchButton();
		setIniziale(null);
	}
	
	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().clienti_detail_new_title();
	}
	
	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().clienti_detail_edit_title(getTipoEstremiRecord(record));		
	}
	
	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().clienti_detail_view_title(getTipoEstremiRecord(record));		
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
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");				
		if(isRecordAbilToDel(flgValido, flgDiSistema)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		if(isRecordAbilToMod(flgDiSistema)) {
			editButton.show();
		} else{
			editButton.hide();
		}		
		if(isLookup() && record.getAttributeAsBoolean("flgSelXFinalita")) {
			lookupButton.show();
		} else {
			lookupButton.hide();
		}
		altreOpButton.hide();		
	}
	
	@Override
	public void doLookup(Record record) {
		
		if(record.getAttributeAsBoolean("flgSelXFinalita")) {
			super.doLookup(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().recordNonSelezionabileXFinalita_message(), "", MessageType.WARNING));
		}
	}
	
	@Override
	public void editMode(boolean fromViewMode) {
		
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}
	
	@Override
	protected Record createMultiLookupRecord(Record record) {
		
		final Record newRecord = new Record();

		newRecord.setAttribute("id", record.getAttributeAsString("idSoggetto"));			
		String codiceRapido = record.getAttributeAsString("codiceRapido");
		String denominazione = record.getAttributeAsString("denominazione");
		if(denominazione == null || "".equals(denominazione)) {
			denominazione = record.getAttributeAsString("cognome") + " " + record.getAttributeAsString("nome");
		}
		newRecord.setAttribute("nome", codiceRapido != null && !"".equals(codiceRapido) ? codiceRapido + " - " + denominazione : denominazione);	
		newRecord.setAttribute("icona", "menu/clienti.png");		
					
		newRecord.setAttribute("cid", record.getAttributeAsString("cid"));	
		newRecord.setAttribute("billingAccount", record.getAttributeAsString("billingAccount"));	
		
		return newRecord;
	}
	
	@Override
	public void manageOnClickInizialeButton(ClickEvent event, String iniziale) {
		
		setIniziale(iniziale);		
		String parole = null;
		AdvancedCriteria criteria = getFilter().getCriteria();
		List<Criterion> criterionListWithoutSearchFulltext = new ArrayList<Criterion>();
		if(criteria != null && criteria.getCriteria() != null) {
			for(Criterion crit : criteria.getCriteria()) {						
				if("searchFulltext".equals(crit.getFieldName())) {
					Map value = JSOHelper.getAttributeAsMap(crit.getJsObj(), "value");
					parole = (String) value.get("parole");
				} else {
					criterionListWithoutSearchFulltext.add(crit);
				}
			}
		}
		final Criterion[] criteriasWithoutSearchFulltext = new Criterion[criterionListWithoutSearchFulltext.size()];
		for(int i = 0; i < criterionListWithoutSearchFulltext.size(); i++) {
			criteriasWithoutSearchFulltext[i] = criterionListWithoutSearchFulltext.get(i);
		}			
		if(parole != null && !"".equals(parole)) {
			SC.ask("Vuoi che sia ignorato il filtro per \"" + parole + "\"?", new BooleanCallback() {					
				@Override
				public void execute(Boolean value) {
					
					if(value) {
						doSearch(new AdvancedCriteria(OperatorId.AND, criteriasWithoutSearchFulltext));	
					} else {
						doSearch();		
					}
				}
			});					
		} else {
			doSearch();
		}
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		
		GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaClientiDataSource", "idSoggetto", FieldType.TEXT);
		return datasource;
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}
	
	static public boolean isNespresso() {		
		boolean ret = false;
		if (AurigaLayout.getParametroDB("SPEC_LABEL_GUI")!=null){
			String specLabelGUI = AurigaLayout.getParametroDB("SPEC_LABEL_GUI").toString();
			if (specLabelGUI.equalsIgnoreCase("NESPRESS")){
				ret = true;
			}
		}
        return ret;		
	}
}