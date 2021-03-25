package it.eng.auriga.ui.module.layout.client.anagrafiche;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class LookupClientiPopup extends ModalWindow {

	public LookupClientiPopup _window;
	
	public ClientiLayout portletLayout;
	
	
	public LookupClientiPopup(String nomeEntita, final Record filterValues, final String tipoSoggetto, String finalita, boolean flgSelezioneSingola) {
		
		super(nomeEntita, true);
		
		setTitle(I18NUtil.getMessages().clienti_lookupSoggettiPopup_title());  	
		
		_window = this;
			
		if(filterValues != null) {
			settingsMenu.removeItem(separatorMenuItem);
			settingsMenu.removeItem(autoSearchMenuItem);
		}
				
		portletLayout = new ClientiLayout(nomeEntita, finalita, flgSelezioneSingola) {
			
			@Override
			public void lookupBack(Record selectedRecord) {
				
				manageLookupBack(selectedRecord);
				_window.markForDestroy();	
			}
			
			@Override
			public void multiLookupBack(Record record) {
							
				manageMultiLookupBack(record);
			}
			
			@Override
			public void multiLookupUndo(Record record) {
							
				manageMultiLookupUndo(record);
			}
			
			@Override
			public void showDetail() {
				
				super.showDetail();
				if(fullScreenDetail) {	
					String title = "";
					if(mode != null) {
						if(mode.equals("new")) {				
							title = getNewDetailTitle();
						} else if(mode.equals("edit")) {
							title = getEditDetailTitle();		
						} else if(mode.equals("view")) {
							title = getViewDetailTitle();
						}
					}
					_window.setTitle(title);											
				}
			}
			
			@Override
			public void hideDetail(boolean reloadList) {
				
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle(I18NUtil.getMessages().clienti_lookupSoggettiPopup_title()); 
				} 	
			}
			
			@Override
			public void setCriteriaAndFirstSearch(AdvancedCriteria criteria, boolean autoSearch) {

				LinkedHashMap<String, Criterion> mapCriterion = new LinkedHashMap<String, Criterion>();
				
				for (int i = 0; i < criteria.getCriteria().length; i++) {
					Criterion criterion = criteria.getCriteria()[i];
					mapCriterion.put(criterion.getFieldName(), criterion);
				}
				
				if(filterValues != null || (tipoSoggetto != null && !"".equals(tipoSoggetto)) || (getTipiAmmessi() != null && getTipiAmmessi().length > 0)) {					
//					Criterion lFulltextCriterion = new Criterion("searchFulltext", OperatorId.ALL_THE_WORDS);	
//					String parole = "";
//					if(filterValues != null) {	
//						if(filterValues.getAttribute("denominazioneSoggetto") != null && !"".equals(filterValues.getAttribute("denominazioneSoggetto"))) {
//							parole += filterValues.getAttribute("denominazioneSoggetto") + " ";
//						}
//						if(filterValues.getAttribute("cognomeSoggetto") != null && !"".equals(filterValues.getAttribute("cognomeSoggetto"))) {
//							parole += filterValues.getAttribute("cognomeSoggetto") + " ";
//						}
//						if(filterValues.getAttribute("nomeSoggetto") != null && !"".equals(filterValues.getAttribute("nomeSoggetto"))) {
//							parole += filterValues.getAttribute("nomeSoggetto") + " ";
//						}
//						if(filterValues.getAttribute("codfiscaleSoggetto") != null && !"".equals(filterValues.getAttribute("codfiscaleSoggetto"))) {
//							parole += filterValues.getAttribute("codfiscaleSoggetto") + " ";
//						}		
//						parole = parole.trim();										
//					}
//					Map value = new HashMap();
//					value.put("parole", parole);
//					int size = Layout.getAttributiValueMap(getNomeEntita()).keySet().size();
//					value.put("attributi", Layout.getAttributiValueMap(getNomeEntita()).keySet().toArray(new String[size]));
//					JSOHelper.setAttribute(lFulltextCriterion.getJsObj(), "value", value);					
//					mapCriterion.put("searchFulltext", lFulltextCriterion);
					
					if(filterValues != null) {	
						String strInDenominazione = "";
						if(filterValues.getAttribute("denominazioneSoggetto") != null && !"".equals(filterValues.getAttribute("denominazioneSoggetto"))) {
							strInDenominazione += filterValues.getAttribute("denominazioneSoggetto") + " ";
						}
						if(filterValues.getAttribute("cognomeSoggetto") != null && !"".equals(filterValues.getAttribute("cognomeSoggetto"))) {
							strInDenominazione += filterValues.getAttribute("cognomeSoggetto") + " ";
						}
						if(filterValues.getAttribute("nomeSoggetto") != null && !"".equals(filterValues.getAttribute("nomeSoggetto"))) {
							strInDenominazione += filterValues.getAttribute("nomeSoggetto") + " ";
						}							
						strInDenominazione = strInDenominazione.trim();
						mapCriterion.put("strInDenominazione", new Criterion("strInDenominazione", OperatorId.WORDS_START_WITH, strInDenominazione));	
					}	
					
					if(tipoSoggetto != null && !"".equals(tipoSoggetto)) {
//						if("AOOI".equals(tipoSoggetto)) {
//							mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.EQUALS, "AOO;AOOI"));							
//						} else if("UOI".equals(tipoSoggetto)) {
//							mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.EQUALS, "UO;UOI"));							
//						} else if("UP".equals(tipoSoggetto)) {
//							mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.EQUALS, "UP"));							
//						} else if("PA".equals(tipoSoggetto)) {
//							mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.EQUALS, new String[]{"PA","AOO;AOOE"}));							
//						} else if("PF".equals(tipoSoggetto)) {
//							mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.NOT_EQUAL, new String[]{"AOO;AOOI","AOO;AOOE","UO;UOI","UO;UOE","UP","PA"}));							
//							mapCriterion.put("flgFisicaGiuridica", new Criterion("flgFisicaGiuridica", OperatorId.EQUALS, "F"));
//						} else if("PG".equals(tipoSoggetto)) {
//							mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.NOT_EQUAL, new String[]{"AOO;AOOI","AOO;AOOE","UO;UOI","UO;UOE","UP","PA"}));
//							mapCriterion.put("flgFisicaGiuridica", new Criterion("flgFisicaGiuridica", OperatorId.EQUALS, "G"));
//						}   	
						mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.EQUALS, getTipologiaFromTipoSoggetto(tipoSoggetto)));													
					} else if(getTipiAmmessi() != null) {
						String[] tipologieAmmesse = new String[getTipiAmmessi().length];
						for(int i = 0; i < getTipiAmmessi().length; i++) {
							tipologieAmmesse[i] = getTipologiaFromTipoSoggetto(getTipiAmmessi()[i]);
						} 
						mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.EQUALS, tipologieAmmesse));
					}													
				}
				
				super.setCriteriaAndFirstSearch(new AdvancedCriteria(OperatorId.AND, mapCriterion.values().toArray(new Criterion[0])), (filterValues != null || autoSearch));
			}
			
		};
		
		portletLayout.setLookup(true);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);        
         
        setIcon("menu/clienti.png");
                
	}
	
	public String getTipologiaFromTipoSoggetto(String tipoSoggetto) {
		if("PA".equals(tipoSoggetto)) {
			return "#APA";							
		} else if("AOOI".equals(tipoSoggetto)) {
			return "#IAMM";							
		} else if("UOI".equals(tipoSoggetto)) {
			return "UO;UOI";							
		} else if("UP".equals(tipoSoggetto)) {
			return "UP";							
		} else if("PF".equals(tipoSoggetto)) {
			return "#AF";		
		} else if("PG".equals(tipoSoggetto)) {
			return "#AG";		
		}   
		return null;
	}
	
	public String[] getTipiAmmessi() {
		return null;
	}
 
	public abstract void manageLookupBack(Record record);	
	public abstract void manageMultiLookupBack(Record record);
	public abstract void manageMultiLookupUndo(Record record);
	
}
