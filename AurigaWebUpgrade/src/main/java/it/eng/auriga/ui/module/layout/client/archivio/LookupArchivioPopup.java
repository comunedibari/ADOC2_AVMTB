package it.eng.auriga.ui.module.layout.client.archivio;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class LookupArchivioPopup extends ModalWindow {
	
	private LookupArchivioPopup _window;
	
	private ArchivioLayout portletLayout;
	
	public LookupArchivioPopup(Record filterValues, boolean flgSelezioneSingola){
		
		this(filterValues, null, flgSelezioneSingola);
		
	}		
	
	public LookupArchivioPopup(final Record filterValues, final String idRootNode, boolean flgSelezioneSingola) {
		this(filterValues, idRootNode, flgSelezioneSingola, false);
	}
	
	public LookupArchivioPopup(final Record filterValues, final String idRootNode, boolean flgSelezioneSingola, final boolean settaRicercaConFilterValues) {
		
		super("archivio", true);
		
		setTitle(getWindowTitle());  	 

		_window = this;
		
		portletLayout = new ArchivioLayout(getFinalita(), flgSelezioneSingola, null, idRootNode) {			
			
			@Override
			public boolean skipAutoSearchInizialeWithIdRootNode() {				
				return AurigaLayout.getParametroDBAsBoolean("INIBITA_AUTO_SEARCH_LOOKUP_FASC");
			}
			
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
					_window.setTitle(getWindowTitle()); 
				} 	
			}
			
			@Override
			public void setCriteriaAndFirstSearch(AdvancedCriteria criteria, boolean autoSearch) {
				LinkedHashMap<String, Criterion> mapCriterion = new LinkedHashMap<String, Criterion>();		
				if(criteria != null && criteria.getCriteria() != null) {
					for (int i = 0; i < criteria.getCriteria().length; i++) {
						Criterion criterion = criteria.getCriteria()[i];
						mapCriterion.put(criterion.getFieldName(), criterion);
					}				
				}
				if(filterValues != null && settaRicercaConFilterValues) {
					if(filterValues.getAttribute("flgUdFolder") != null && !"".equals(filterValues.getAttribute("flgUdFolder"))) {
						mapCriterion.put("flgUdFolder", new Criterion("flgUdFolder", OperatorId.IEQUALS, filterValues.getAttribute("flgUdFolder")));
					}
					if(filterValues.getAttribute("indirizzo") != null && !"".equals(filterValues.getAttribute("indirizzo"))) {
						mapCriterion.put("indirizzo", new Criterion("indirizzo", OperatorId.IEQUALS, filterValues.getAttribute("indirizzo")));
					}
				}				
				super.setCriteriaAndFirstSearch(new AdvancedCriteria(OperatorId.AND, mapCriterion.values().toArray(new Criterion[0])), (filterValues != null || autoSearch));
			}
			
			@Override
			public void setDefaultCriteriaAndFirstSearch(boolean autosearch) {
				if(filterValues != null && settaRicercaConFilterValues) {					
					setCriteriaAndFirstSearch(new AdvancedCriteria(), autosearch);
				} else {
					super.setDefaultCriteriaAndFirstSearch(autosearch);
				}				
			}
			

		};
		portletLayout.setLookup(true);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
   
        setIcon("menu/archivio.png");
                
	}
	
	public String getWindowTitle() {
		return I18NUtil.getMessages().protocollazione_detail_lookupArchivioButton_prompt();
	}
	
	public String getFinalita() {
		return null;
	}
	
	public abstract void manageLookupBack(Record record);	
	public abstract void manageMultiLookupBack(Record record);
	public abstract void manageMultiLookupUndo(Record record);
	
}