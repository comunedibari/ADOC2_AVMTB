package it.eng.auriga.ui.module.layout.client.gestioneatti.delibere;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class ListaDatiPartecipantiSedutaItem extends GridItem {
	
	private ListaDatiPartecipantiSedutaItem instance = this;
	
	protected ListGridField idUser;
	protected ListGridField denominazione;
	protected ListGridField incarico;
	protected ListGridField ruolo;
	protected ListGridField flgPresenza;
	
	protected ListGridField[] formattedFields = null;
	protected List<String> controlFields;
	
	private String organoCollegiale;
	private String statoSeduta;

	public ListaDatiPartecipantiSedutaItem(String name, String organoCollegiale, String statoSeduta) {
		
		super(name, "lista_dati_partecipanti_seduta");
		
		this.organoCollegiale = organoCollegiale;
		this.statoSeduta = statoSeduta;
		
		setGridPkField("idUser");
		setShowPreference(true);
		setShowNewButton(true);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(!isOdGChiuso());  
		
		idUser = new ListGridField("idUser");
		idUser.setHidden(true);
		idUser.setCanHide(false);
		idUser.setCanSort(true);
		idUser.setCanEdit(false);
		
		denominazione = new ListGridField("denominazione", "Cognome e nome");
		denominazione.setCanSort(true);
		denominazione.setCanEdit(false);
		
		incarico = new ListGridField("incarico", "Incarico");
		incarico.setCanSort(true);
		incarico.setCanEdit(false);
		
		ruolo = new ListGridField("ruolo", "Ruolo in seduta");
		ruolo.setCanSort(true);
		ruolo.setCanEdit(!isOdGChiuso());		
		GWTRestDataSource tipoRuoloDS = new GWTRestDataSource("LoadComboTipoRuoloSedutaDataSource");
		tipoRuoloDS.addParam("tipo_sessione", organoCollegiale);		
		SelectItem tipoRuoloItem = new SelectItem("tipoRuolo");
		tipoRuoloItem.setValueField("key");
		tipoRuoloItem.setDisplayField("value");
		tipoRuoloItem.setOptionDataSource(tipoRuoloDS);
		tipoRuoloItem.setAutoFetchData(true);
		tipoRuoloItem.setAlwaysFetchMissingValues(true);
		tipoRuoloItem.setFetchMissingValues(true);
		tipoRuoloItem.setStartRow(false);
		tipoRuoloItem.setColSpan(1);
		tipoRuoloItem.setWidth(200);	
		tipoRuoloItem.setAllowEmptyValue(true);
		ruolo.setEditorProperties(tipoRuoloItem); 
				
		LinkedHashMap<String, String> flgPresenzaValueMap = new LinkedHashMap<String, String>();
		flgPresenzaValueMap.put("1", "presente");
		flgPresenzaValueMap.put("0", "assente");		
		flgPresenza = new ListGridField("flgPresenza", "Presenza");
		flgPresenza.setCanSort(true);
		flgPresenza.setCanEdit(!isOdGChiuso());
		flgPresenza.setValueMap(flgPresenzaValueMap);
		RadioGroupItem radioPresenzaItem = new RadioGroupItem("radioPresenza");
		radioPresenzaItem.setShowTitle(false);
		radioPresenzaItem.setValueMap(flgPresenzaValueMap);
		radioPresenzaItem.setDefaultValue("1");
		radioPresenzaItem.setVertical(true);
		radioPresenzaItem.setWrap(false);		
		flgPresenza.setEditorProperties(radioPresenzaItem);		
		
		setGridFields(
				idUser,
				denominazione,
				incarico,
				ruolo,
				flgPresenza
		);
	}
		
	@Override
	public ListGrid buildGrid() {
		
		ListGrid grid = super.buildGrid();

		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
			
		return grid;
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();	
		
		ToolStripButton addPartecipanteButton = new ToolStripButton();   
		addPartecipanteButton.setIcon("buttons/new.png");   
		addPartecipanteButton.setIconSize(16);
		addPartecipanteButton.setPrefix("Aggiungi partecipante");
		addPartecipanteButton.setPrompt("Aggiungi partecipante");
		addPartecipanteButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				PartecipanteMultiLookupRubrica lPartecipanteMultiLookupRubrica = new PartecipanteMultiLookupRubrica(null);				
				lPartecipanteMultiLookupRubrica.show(); 	     	
			}   
		});  
		buttons.add(addPartecipanteButton);
	
		ToolStripButton refreshButton = new ToolStripButton();   
		refreshButton.setIcon("buttons/refreshList.png");   
		refreshButton.setIconSize(16);
		refreshButton.setPrefix("Ricarica");
		refreshButton.setPrompt("Ricarica");
		refreshButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				onClickRefreshListButton();
			}   
		});  
		buttons.add(refreshButton);
			
		return buttons;
	}
	
	public void onClickRefreshListButton() {
		
		Record lRecord = new Record();
		lRecord.setAttribute("organoCollegiale", organoCollegiale);
		lRecord.setAttribute("idSeduta", getIdSeduta());
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
		lGWTRestDataSource.call(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				RecordList recordList = object.getAttributeAsRecordList("listaPresenzeOdg");
				setData(recordList);
			}
		});
	}
	
	private Boolean isOdGChiuso() {
		return statoSeduta != null && "OdG_chiuso".equalsIgnoreCase(statoSeduta);
	}
	
	public class PartecipanteMultiLookupRubrica extends LookupSoggettiPopup {

		private RecordList multiLookupList = new RecordList(); 
	
		public PartecipanteMultiLookupRubrica(Record record) {
			super(record, null, false);			
		}

		@Override
		public String getFinalita() {
			return "SEL_SOGG_EST";
		}
		
		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			Record recordToInsert = new Record();
			recordToInsert.setAttribute("idRubrica", record.getAttribute("idSoggetto"));
			recordToInsert.setAttribute("idUser", record.getAttribute("idUtente"));
			recordToInsert.setAttribute("denominazione", record.getAttribute("denominazione"));
			multiLookupList.add(recordToInsert);
			instance.addData(recordToInsert);
			
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			String idRubricaToRemove = record.getAttribute("id") != null ? record.getAttribute("id") : "";
			int posToRemove = -1;
			if (multiLookupList != null) {
				for (int i = 0; i < multiLookupList.getLength(); i++) {
					if (idRubricaToRemove.equalsIgnoreCase(multiLookupList.get(i).getAttribute("idRubrica"))) {
						posToRemove = i;
					}
				}
				if (posToRemove > -1) {
					multiLookupList.removeAt(posToRemove);
				}
				posToRemove = -1;
				if (instance.getData() != null) {
					RecordList listaPartecipanti = instance.getData();
					for (int i = 0; i < listaPartecipanti.getLength(); i++) {
						Record partecipante = listaPartecipanti.get(i);
						if (partecipante.getAttribute("idRubrica") != null && idRubricaToRemove.equalsIgnoreCase(partecipante.getAttribute("idRubrica"))) {
							posToRemove = i;
						}
					}					
					if (posToRemove > -1) {
						listaPartecipanti.removeAt(posToRemove);
					}
				}
			}
		}
	}
	
	public String getIdSeduta() {
		return null;
	}
}